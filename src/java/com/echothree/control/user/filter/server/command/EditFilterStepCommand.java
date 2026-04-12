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

import com.echothree.control.user.filter.common.edit.FilterEditFactory;
import com.echothree.control.user.filter.common.edit.FilterStepEdit;
import com.echothree.control.user.filter.common.result.EditFilterStepResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterStepSpec;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterStep;
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
public class EditFilterStepCommand
        extends BaseAbstractEditCommand<FilterStepSpec, FilterStepEdit, EditFilterStepResult, FilterStep, FilterStep> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Filter.name(), SecurityRoles.FilterStep.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterStepName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditFilterStepCommand */
    public EditFilterStepCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Inject
    FilterControl filterControl;

    @Inject
    SelectorControl selectorControl;

    @Override
    public EditFilterStepResult getResult() {
        return FilterResultFactory.getEditFilterStepResult();
    }

    @Override
    public FilterStepEdit getEdit() {
        return FilterEditFactory.getFilterStepEdit();
    }

    Filter filter;

    @Override
    public FilterStep getEntity(EditFilterStepResult result) {
        FilterStep filterStep = null;
        var filterKindName = spec.getFilterKindName();
        var filterKind = filterControl.getFilterKindByName(filterKindName);

        if(filterKind != null) {
            var filterTypeName = spec.getFilterTypeName();
            var filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);

            if(filterType != null) {
                var filterName = spec.getFilterName();
                filter = filterControl.getFilterByName(filterType, filterName);

                if(filter != null) {
                    var filterStepName = spec.getFilterStepName();

                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        filterStep = filterControl.getFilterStepByName(filter, filterStepName);
                    } else { // EditMode.UPDATE
                        filterStep = filterControl.getFilterStepByNameForUpdate(filter, filterStepName);
                    }

                    if(filterStep == null) {
                        addExecutionError(ExecutionErrors.UnknownFilterStepName.name(), filterStepName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownFilterName.name(), filterName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFilterTypeName.name(), filterTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }

        return filterStep;
    }

    @Override
    public FilterStep getLockEntity(FilterStep filterStep) {
        return filterStep;
    }

    @Override
    public void fillInResult(EditFilterStepResult result, FilterStep filterStep) {
        result.setFilterStep(filterControl.getFilterStepTransfer(getUserVisit(), filterStep));
    }

    @Override
    public void doLock(FilterStepEdit edit, FilterStep filterStep) {
        var filterStepDescription = filterControl.getFilterStepDescription(filterStep, getPreferredLanguage());
        var filterStepDetail = filterStep.getLastDetail();
        var filterItemSelector = filterStepDetail.getFilterItemSelector();

        edit.setFilterStepName(filterStepDetail.getFilterStepName());
        edit.setFilterItemSelectorName(filterItemSelector == null? null: filterItemSelector.getLastDetail().getSelectorName());

        if(filterStepDescription != null) {
            edit.setDescription(filterStepDescription.getDescription());
        }
    }

    Selector filterItemSelector;

    @Override
    public void canUpdate(FilterStep filterStep) {
        var filterStepName = edit.getFilterStepName();
        var duplicateFilterStep = filterControl.getFilterStepByName(filter, filterStepName);

        if(duplicateFilterStep == null || filterStep.equals(duplicateFilterStep)) {
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
                        addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorTypes.FILTER.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorKinds.ITEM.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateFilterStepName.name(), filterStepName);
        }
    }

    @Override
    public void doUpdate(FilterStep filterStep) {
        var partyPK = getPartyPK();
        var filterStepDetailValue = filterControl.getFilterStepDetailValueForUpdate(filterStep);
        var filterStepDescription = filterControl.getFilterStepDescriptionForUpdate(filterStep, getPreferredLanguage());
        var description = edit.getDescription();

        filterStepDetailValue.setFilterStepName(edit.getFilterStepName());
        filterStepDetailValue.setFilterItemSelectorPK(filterItemSelector == null? null: filterItemSelector.getPrimaryKey());

        filterControl.updateFilterStepFromValue(filterStepDetailValue, partyPK);

        if(filterStepDescription == null && description != null) {
            filterControl.createFilterStepDescription(filterStep, getPreferredLanguage(), description, partyPK);
        } else if(filterStepDescription != null && description == null) {
            filterControl.deleteFilterStepDescription(filterStepDescription, partyPK);
        } else if(filterStepDescription != null && description != null) {
            var filterStepDescriptionValue = filterControl.getFilterStepDescriptionValue(filterStepDescription);

            filterStepDescriptionValue.setDescription(description);
            filterControl.updateFilterStepDescriptionFromValue(filterStepDescriptionValue, partyPK);
        }
    }

}
