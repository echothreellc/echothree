// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.control.user.filter.common.edit.FilterStepElementEdit;
import com.echothree.control.user.filter.common.form.EditFilterStepElementForm;
import com.echothree.control.user.filter.common.result.EditFilterStepElementResult;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterStepElementSpec;
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
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterStepElement;
import com.echothree.model.data.filter.server.entity.FilterStepElementDescription;
import com.echothree.model.data.filter.server.entity.FilterStepElementDetail;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.filter.server.value.FilterStepElementDescriptionValue;
import com.echothree.model.data.filter.server.value.FilterStepElementDetailValue;
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

public class EditFilterStepElementCommand
        extends BaseEditCommand<FilterStepElementSpec, FilterStepElementEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Filter.name(), SecurityRoles.FilterStepElement.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterStepElementName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("FilterStepElementName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FilterItemSelectorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("FilterAdjustmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
        );
    }
    
    /** Creates a new instance of EditFilterStepElementCommand */
    public EditFilterStepElementCommand(UserVisitPK userVisitPK, EditFilterStepElementForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var filterControl = Session.getModelController(FilterControl.class);
        EditFilterStepElementResult result = FilterResultFactory.getEditFilterStepElementResult();
        String filterKindName = spec.getFilterKindName();
        FilterKind filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            String filterTypeName = spec.getFilterTypeName();
            FilterType filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);
            
            if(filterType != null) {
                String filterName = spec.getFilterName();
                Filter filter = filterControl.getFilterByName(filterType, filterName);
                
                if(filter != null) {
                    String filterStepName = spec.getFilterStepName();
                    FilterStep filterStep = filterControl.getFilterStepByName(filter, filterStepName);
                    
                    if(filterStep != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            String filterStepElementName = spec.getFilterStepElementName();
                            FilterStepElement filterStepElement = filterControl.getFilterStepElementByName(filterStep, filterStepElementName);
                            
                            if(filterStepElement != null) {
                                result.setFilterStepElement(filterControl.getFilterStepElementTransfer(getUserVisit(), filterStepElement));
                                
                                if(lockEntity(filterStepElement)) {
                                    FilterStepElementDescription filterStepElementDescription = filterControl.getFilterStepElementDescription(filterStepElement, getPreferredLanguage());
                                    FilterStepElementEdit edit = FilterEditFactory.getFilterStepElementEdit();
                                    FilterStepElementDetail filterStepElementDetail = filterStepElement.getLastDetail();
                                    Selector filterItemSelector = filterStepElementDetail.getFilterItemSelector();
                                    
                                    result.setEdit(edit);
                                    edit.setFilterStepElementName(filterStepElementDetail.getFilterStepElementName());
                                    edit.setFilterItemSelectorName(filterItemSelector == null? null: filterItemSelector.getLastDetail().getSelectorName());
                                    edit.setFilterAdjustmentName(filterStepElementDetail.getFilterAdjustment().getLastDetail().getFilterAdjustmentName());
                                    
                                    if(filterStepElementDescription != null) {
                                        edit.setDescription(filterStepElementDescription.getDescription());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                }
                                
                                result.setEntityLock(getEntityLockTransfer(filterStepElement));
                            } else {
                                addExecutionError(ExecutionErrors.UnknownFilterStepElementName.name(), filterStepElementName);
                            }
                        } else if(editMode.equals(EditMode.UPDATE)) {
                            String filterStepElementName = spec.getFilterStepElementName();
                            FilterStepElement filterStepElement = filterControl.getFilterStepElementByNameForUpdate(filterStep, filterStepElementName);
                            
                            if(filterStepElement != null) {
                                filterStepElementName = edit.getFilterStepElementName();
                                FilterStepElement duplicateFilterStepElement = filterControl.getFilterStepElementByName(filterStep, filterStepElementName);
                                
                                if(duplicateFilterStepElement == null || filterStepElement.equals(duplicateFilterStepElement)) {
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
                                        String filterAdjustmentName = edit.getFilterAdjustmentName();
                                        FilterAdjustment filterAdjustment = filterAdjustmentName == null? null: filterControl.getFilterAdjustmentByName(filterKind, filterAdjustmentName);
                                        
                                        if(filterAdjustmentName == null || filterAdjustment != null) {
                                            if(lockEntityForUpdate(filterStepElement)) {
                                                try {
                                                    var partyPK = getPartyPK();
                                                    FilterStepElementDetailValue filterStepElementDetailValue = filterControl.getFilterStepElementDetailValueForUpdate(filterStepElement);
                                                    FilterStepElementDescription filterStepElementDescription = filterControl.getFilterStepElementDescriptionForUpdate(filterStepElement, getPreferredLanguage());
                                                    String description = edit.getDescription();
                                                    
                                                    filterStepElementDetailValue.setFilterStepElementName(edit.getFilterStepElementName());
                                                    filterStepElementDetailValue.setFilterItemSelectorPK(filterItemSelector == null? null: filterItemSelector.getPrimaryKey());
                                                    filterStepElementDetailValue.setFilterAdjustmentPK(filterAdjustment.getPrimaryKey());
                                                    
                                                    filterControl.updateFilterStepElementFromValue(filterStepElementDetailValue, partyPK);
                                                    
                                                    if(filterStepElementDescription == null && description != null) {
                                                        filterControl.createFilterStepElementDescription(filterStepElement, getPreferredLanguage(), description, partyPK);
                                                    } else if(filterStepElementDescription != null && description == null) {
                                                        filterControl.deleteFilterStepElementDescription(filterStepElementDescription, partyPK);
                                                    } else if(filterStepElementDescription != null && description != null) {
                                                        FilterStepElementDescriptionValue filterStepElementDescriptionValue = filterControl.getFilterStepElementDescriptionValue(filterStepElementDescription);
                                                        
                                                        filterStepElementDescriptionValue.setDescription(description);
                                                        filterControl.updateFilterStepElementDescriptionFromValue(filterStepElementDescriptionValue, partyPK);
                                                    }
                                                } finally {
                                                    unlockEntity(filterStepElement);
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownFilterAdjustmentName.name(), filterAdjustmentName);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownFilterItemSelectorName.name(), filterItemSelectorName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.DuplicateFilterStepElementName.name(), filterStepElementName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownFilterStepElementName.name(), filterStepElementName);
                            }
                        }
                    } else {
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
        
        return result;
    }
    
}
