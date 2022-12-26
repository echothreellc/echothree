// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.control.user.filter.common.form.EditFilterForm;
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
import com.echothree.model.data.filter.server.entity.FilterDescription;
import com.echothree.model.data.filter.server.entity.FilterDetail;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.filter.server.value.FilterDescriptionValue;
import com.echothree.model.data.filter.server.value.FilterDetailValue;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class EditFilterCommand
        extends BaseEditCommand<FilterSpec, FilterEdit> {

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
    public EditFilterCommand(UserVisitPK userVisitPK, EditFilterForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var filterControl = Session.getModelController(FilterControl.class);
        EditFilterResult result = FilterResultFactory.getEditFilterResult();
        String filterKindName = spec.getFilterKindName();
        FilterKind filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            String filterTypeName = spec.getFilterTypeName();
            FilterType filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);
            
            if(filterType != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    String filterName = spec.getFilterName();
                    Filter filter = filterControl.getFilterByName(filterType, filterName);
                    
                    if(filter != null) {
                        result.setFilter(filterControl.getFilterTransfer(getUserVisit(), filter));
                        
                        if(lockEntity(filter)) {
                            FilterDescription filterDescription = filterControl.getFilterDescription(filter, getPreferredLanguage());
                            FilterEdit edit = FilterEditFactory.getFilterEdit();
                            FilterDetail filterDetail = filter.getLastDetail();
                            Selector filterItemSelector = filterDetail.getFilterItemSelector();
                            
                            result.setEdit(edit);
                            edit.setFilterName(filterDetail.getFilterName());
                            edit.setInitialFilterAdjustmentName(filterDetail.getInitialFilterAdjustment().getLastDetail().getFilterAdjustmentName());
                            edit.setFilterItemSelectorName(filterItemSelector == null? null: filterItemSelector.getLastDetail().getSelectorName());
                            edit.setIsDefault(filterDetail.getIsDefault().toString());
                            edit.setSortOrder(filterDetail.getSortOrder().toString());
                            
                            if(filterDescription != null) {
                                edit.setDescription(filterDescription.getDescription());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(filter));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownFilterName.name(), filterName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    String filterName = spec.getFilterName();
                    Filter filter = filterControl.getFilterByNameForUpdate(filterType, filterName);
                    
                    if(filter != null) {
                        filterName = edit.getFilterName();
                        Filter duplicateFilter = filterControl.getFilterByName(filterType, filterName);
                        
                        if(duplicateFilter == null || filter.equals(duplicateFilter)) {
                            String initialFilterAdjustmentName = edit.getInitialFilterAdjustmentName();
                            FilterAdjustment initialFilterAdjustment = initialFilterAdjustmentName == null? null: filterControl.getFilterAdjustmentByName(filterKind, initialFilterAdjustmentName);
                            
                            if(initialFilterAdjustmentName == null || initialFilterAdjustment != null) {
                                String filterItemSelectorName = edit.getFilterItemSelectorName();
                                Selector filterItemSelector = null;
                                
                                if(filterItemSelectorName != null) {
                                    var selectorControl = Session.getModelController(SelectorControl.class);
                                    SelectorKind selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.ITEM.name());

                                    if(selectorKind != null) {
                                        SelectorType selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorTypes.FILTER.name());
                                        
                                        if(selectorType != null) {
                                            filterItemSelector = selectorControl.getSelectorByName(selectorType, filterItemSelectorName);
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorTypes.FILTER.name());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorKinds.ITEM.name());
                                    }
                                }
                                
                                if(filterItemSelectorName == null || filterItemSelector != null) {
                                    if(lockEntityForUpdate(filter)) {
                                        try {
                                            var partyPK = getPartyPK();
                                            FilterDetailValue filterDetailValue = filterControl.getFilterDetailValueForUpdate(filter);
                                            FilterDescription filterDescription = filterControl.getFilterDescriptionForUpdate(filter, getPreferredLanguage());
                                            String description = edit.getDescription();
                                            
                                            filterDetailValue.setFilterName(edit.getFilterName());
                                            filterDetailValue.setInitialFilterAdjustmentPK(initialFilterAdjustment.getPrimaryKey());
                                            filterDetailValue.setFilterItemSelectorPK(filterItemSelector == null? null: filterItemSelector.getPrimaryKey());
                                            filterDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                            filterDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                            
                                            filterControl.updateFilterFromValue(filterDetailValue, partyPK);
                                            
                                            if(filterDescription == null && description != null) {
                                                filterControl.createFilterDescription(filter, getPreferredLanguage(), description, partyPK);
                                            } else if(filterDescription != null && description == null) {
                                                filterControl.deleteFilterDescription(filterDescription, partyPK);
                                            } else if(filterDescription != null && description != null) {
                                                FilterDescriptionValue filterDescriptionValue = filterControl.getFilterDescriptionValue(filterDescription);
                                                
                                                filterDescriptionValue.setDescription(description);
                                                filterControl.updateFilterDescriptionFromValue(filterDescriptionValue, partyPK);
                                            }
                                        } finally {
                                            unlockEntity(filter);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownFilterItemSelectorName.name(), filterItemSelectorName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownInitialFilterAdjustmentName.name(), initialFilterAdjustmentName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateFilterName.name(), filterName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownFilterName.name(), filterName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFilterTypeName.name(), filterTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownFilterKindName.name(), filterKindName);
        }
        
        return result;
    }
    
}
