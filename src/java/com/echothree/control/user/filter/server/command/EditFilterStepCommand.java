// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.control.user.filter.common.form.EditFilterStepForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterStepSpec;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditFilterStepCommand
        extends BaseEditCommand<FilterStepSpec, FilterStepEdit> {

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
    
    @Override
    protected BaseResult execute() {
        var filterControl = Session.getModelController(FilterControl.class);
        var result = FilterResultFactory.getEditFilterStepResult();
        var filterKindName = spec.getFilterKindName();
        var filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            var filterTypeName = spec.getFilterTypeName();
            var filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);
            
            if(filterType != null) {
                var filterName = spec.getFilterName();
                var filter = filterControl.getFilterByName(filterType, filterName);
                
                if(filter != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        var filterStepName = spec.getFilterStepName();
                        var filterStep = filterControl.getFilterStepByName(filter, filterStepName);
                        
                        if(filterStep != null) {
                            result.setFilterStep(filterControl.getFilterStepTransfer(getUserVisit(), filterStep));
                            
                            if(lockEntity(filterStep)) {
                                var filterStepDescription = filterControl.getFilterStepDescription(filterStep, getPreferredLanguage());
                                var edit = FilterEditFactory.getFilterStepEdit();
                                var filterStepDetail = filterStep.getLastDetail();
                                var filterItemSelector = filterStepDetail.getFilterItemSelector();
                                
                                result.setEdit(edit);
                                edit.setFilterStepName(filterStepDetail.getFilterStepName());
                                edit.setFilterItemSelectorName(filterItemSelector == null? null: filterItemSelector.getLastDetail().getSelectorName());
                                
                                if(filterStepDescription != null) {
                                    edit.setDescription(filterStepDescription.getDescription());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(filterStep));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownFilterStepName.name(), filterStepName);
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        var filterStepName = spec.getFilterStepName();
                        var filterStep = filterControl.getFilterStepByNameForUpdate(filter, filterStepName);
                        
                        if(filterStep != null) {
                            filterStepName = edit.getFilterStepName();
                            var duplicateFilterStep = filterControl.getFilterStepByName(filter, filterStepName);
                            
                            if(duplicateFilterStep == null || filterStep.equals(duplicateFilterStep)) {
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
                                    if(lockEntityForUpdate(filterStep)) {
                                        try {
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
                                        } finally {
                                            unlockEntity(filterStep);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownFilterItemSelectorName.name(), filterItemSelectorName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.DuplicateFilterStepName.name(), filterStepName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownFilterStepName.name(), filterStepName);
                        }
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
