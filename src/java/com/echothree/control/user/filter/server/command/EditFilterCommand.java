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
import com.echothree.control.user.filter.common.form.EditFilterForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterSpec;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.common.SelectorKinds;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.Selector;
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
import javax.enterprise.context.Dependent;

@Dependent
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
    public EditFilterCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var filterControl = Session.getModelController(FilterControl.class);
        var result = FilterResultFactory.getEditFilterResult();
        var filterKindName = spec.getFilterKindName();
        var filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            var filterTypeName = spec.getFilterTypeName();
            var filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);
            
            if(filterType != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var filterName = spec.getFilterName();
                    var filter = filterControl.getFilterByName(filterType, filterName);
                    
                    if(filter != null) {
                        result.setFilter(filterControl.getFilterTransfer(getUserVisit(), filter));
                        
                        if(lockEntity(filter)) {
                            var filterDescription = filterControl.getFilterDescription(filter, getPreferredLanguage());
                            var edit = FilterEditFactory.getFilterEdit();
                            var filterDetail = filter.getLastDetail();
                            var filterItemSelector = filterDetail.getFilterItemSelector();
                            
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
                    var filterName = spec.getFilterName();
                    var filter = filterControl.getFilterByNameForUpdate(filterType, filterName);
                    
                    if(filter != null) {
                        filterName = edit.getFilterName();
                        var duplicateFilter = filterControl.getFilterByName(filterType, filterName);
                        
                        if(duplicateFilter == null || filter.equals(duplicateFilter)) {
                            var initialFilterAdjustmentName = edit.getInitialFilterAdjustmentName();
                            var initialFilterAdjustment = initialFilterAdjustmentName == null? null: filterControl.getFilterAdjustmentByName(filterKind, initialFilterAdjustmentName);
                            
                            if(initialFilterAdjustmentName == null || initialFilterAdjustment != null) {
                                var filterItemSelectorName = edit.getFilterItemSelectorName();
                                Selector filterItemSelector = null;
                                
                                if(filterItemSelectorName != null) {
                                    var selectorControl = Session.getModelController(SelectorControl.class);
                                    var selectorKind = selectorControl.getSelectorKindByName(SelectorKinds.ITEM.name());

                                    if(selectorKind != null) {
                                        var selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorTypes.FILTER.name());
                                        
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
                                            var filterDetailValue = filterControl.getFilterDetailValueForUpdate(filter);
                                            var filterDescription = filterControl.getFilterDescriptionForUpdate(filter, getPreferredLanguage());
                                            var description = edit.getDescription();
                                            
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
                                                var filterDescriptionValue = filterControl.getFilterDescriptionValue(filterDescription);
                                                
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
