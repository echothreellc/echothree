// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.control.user.filter.server.command;

import com.echothree.control.user.filter.common.edit.FilterEdit;
import com.echothree.control.user.filter.common.edit.FilterEditFactory;
import com.echothree.control.user.filter.common.result.EditFilterResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterSpec;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditFilterCommand
        extends BaseAbstractEditCommand<FilterSpec, FilterEdit, EditFilterResult, Filter, Filter> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Filter.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InitialFilterAdjustmentName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("FilterItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditFilterCommand */
    public EditFilterCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Inject
    FilterControl filterControl;

    @Inject
    SelectorControl selectorControl;

    @Override
    public EditFilterResult getResult() {
        return FilterResultFactory.getEditFilterResult();
    }

    @Override
    public FilterEdit getEdit() {
        return FilterEditFactory.getFilterEdit();
    }

    FilterKind filterKind;
    FilterType filterType;

    @Override
    public Filter getEntity(EditFilterResult result) {
        Filter filter = null;
        var filterKindName = spec.getFilterKindName();

        filterKind = filterControl.getFilterKindByName(filterKindName);

        if(filterKind != null) {
            var filterTypeName = spec.getFilterTypeName();

            filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);

            if(filterType != null) {
                var filterName = spec.getFilterName();

                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    filter = filterControl.getFilterByName(filterType, filterName);
                } else { // EditMode.UPDATE
                    filter = filterControl.getFilterByNameForUpdate(filterType, filterName);
                }

                if(filter == null) {
                    addExecutionError(ExecutionErrors.UnknownFilterName.name(), filterName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFilterTypeName.name(), filterTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }

        return filter;
    }

    @Override
    public Filter getLockEntity(Filter filter) {
        return filter;
    }

    @Override
    public void fillInResult(EditFilterResult result, Filter filter) {
        result.setFilter(filterControl.getFilterTransfer(getUserVisit(), filter));
    }

    @Override
    public void doLock(FilterEdit edit, Filter filter) {
        var filterDescription = filterControl.getFilterDescription(filter, getPreferredLanguage());
        var filterDetail = filter.getLastDetail();
        var filterItemSelector = filterDetail.getFilterItemSelector();

        edit.setFilterName(filterDetail.getFilterName());
        edit.setInitialFilterAdjustmentName(filterDetail.getInitialFilterAdjustment().getLastDetail().getFilterAdjustmentName());
        edit.setFilterItemSelectorName(filterItemSelector == null? null: filterItemSelector.getLastDetail().getSelectorName());
        edit.setIsDefault(filterDetail.getIsDefault().toString());
        edit.setSortOrder(filterDetail.getSortOrder().toString());

        if(filterDescription != null) {
            edit.setDescription(filterDescription.getDescription());
        }
    }

    FilterAdjustment initialFilterAdjustment;
    Selector filterItemSelector;

    @Override
    public void canUpdate(Filter filter) {
        var filterName = edit.getFilterName();
        var duplicateFilter = filterControl.getFilterByName(filterType, filterName);

        if(duplicateFilter != null && !filter.equals(duplicateFilter)) {
            addExecutionError(ExecutionErrors.DuplicateFilterName.name(), filterName);
        } else {
            var initialFilterAdjustmentName = edit.getInitialFilterAdjustmentName();

            initialFilterAdjustment = initialFilterAdjustmentName == null? null: filterControl.getFilterAdjustmentByName(filterKind, initialFilterAdjustmentName);

            if(initialFilterAdjustmentName != null && initialFilterAdjustment == null) {
                addExecutionError(ExecutionErrors.UnknownInitialFilterAdjustmentName.name(), initialFilterAdjustmentName);
            } else {
                var filterItemSelectorName = edit.getFilterItemSelectorName();

                if(filterItemSelectorName != null) {
                    var selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.ITEM.name());

                    if(selectorKind != null) {
                        var selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorTypes.FILTER.name());

                        if(selectorType != null) {
                            filterItemSelector = selectorControl.getSelectorByName(selectorType, filterItemSelectorName);

                            if(filterItemSelector == null) {
                                addExecutionError(ExecutionErrors.UnknownFilterItemSelectorName.name(), filterItemSelectorName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorKinds.ITEM.name(), SelectorTypes.FILTER.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorKinds.ITEM.name());
                    }
                }
            }
        }
    }

    @Override
    public void doUpdate(Filter filter) {
        var partyPK = getPartyPK();
        var filterDetailValue = filterControl.getFilterDetailValueForUpdate(filter);
        var filterDescription = filterControl.getFilterDescriptionForUpdate(filter, getPreferredLanguage());
        var description = edit.getDescription();

        filterDetailValue.setFilterName(edit.getFilterName());
        filterDetailValue.setInitialFilterAdjustmentPK(initialFilterAdjustment == null? null: initialFilterAdjustment.getPrimaryKey());
        filterDetailValue.setFilterItemSelectorPK(filterItemSelector == null? null: filterItemSelector.getPrimaryKey());
        filterDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        filterDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        filterControl.updateFilterFromValue(filterDetailValue, partyPK);

        if(filterDescription == null && description != null) {
            filterControl.createFilterDescription(filter, getPreferredLanguage(), description, partyPK);
        } else if(filterDescription != null && description == null) {
            filterControl.deleteFilterDescription(filterDescription, partyPK);
        } else if(filterDescription != null && description != null) {
            var filterDescriptionValue = filterControl.getFilterDescriptionValue(filterDescription);

            filterDescriptionValue.setDescription(description);
            filterControl.updateFilterDescriptionFromValue(filterDescriptionValue, partyPK);
        }
    }

}
