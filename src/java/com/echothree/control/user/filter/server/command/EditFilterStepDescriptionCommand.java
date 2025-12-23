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
import com.echothree.control.user.filter.common.edit.FilterStepDescriptionEdit;
import com.echothree.control.user.filter.common.form.EditFilterStepDescriptionForm;
import com.echothree.control.user.filter.common.result.FilterResultFactory;
import com.echothree.control.user.filter.common.spec.FilterStepDescriptionSpec;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditFilterStepDescriptionCommand
        extends BaseEditCommand<FilterStepDescriptionSpec, FilterStepDescriptionEdit> {

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
                new FieldDefinition("FilterStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditFilterStepDescriptionCommand */
    public EditFilterStepDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var filterControl = Session.getModelController(FilterControl.class);
        var result = FilterResultFactory.getEditFilterStepDescriptionResult();
        var filterKindName = spec.getFilterKindName();
        var filterKind = filterControl.getFilterKindByName(filterKindName);
        
        if(filterKind != null) {
            var filterTypeName = spec.getFilterTypeName();
            var filterType = filterControl.getFilterTypeByName(filterKind, filterTypeName);
            
            if(filterType != null) {
                var filterName = spec.getFilterName();
                var filter = filterControl.getFilterByName(filterType, filterName);
                
                if(filter != null) {
                    var filterStepName = spec.getFilterStepName();
                    var filterStep = filterControl.getFilterStepByName(filter, filterStepName);
                    
                    if(filterStep != null) {
                        var partyControl = Session.getModelController(PartyControl.class);
                        var languageIsoName = spec.getLanguageIsoName();
                        var language = partyControl.getLanguageByIsoName(languageIsoName);
                        
                        if(language != null) {
                            if(editMode.equals(EditMode.LOCK)) {
                                var filterStepDescription = filterControl.getFilterStepDescription(filterStep, language);
                                
                                if(filterStepDescription != null) {
                                    result.setFilterStepDescription(filterControl.getFilterStepDescriptionTransfer(getUserVisit(), filterStepDescription));
                                    
                                    if(lockEntity(filterStep)) {
                                        var edit = FilterEditFactory.getFilterStepDescriptionEdit();
                                        
                                        result.setEdit(edit);
                                        edit.setDescription(filterStepDescription.getDescription());
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                    }
                                    
                                    result.setEntityLock(getEntityLockTransfer(filterStep));
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownFilterStepDescription.name());
                                }
                            } else if(editMode.equals(EditMode.UPDATE)) {
                                var filterStepDescriptionValue = filterControl.getFilterStepDescriptionValueForUpdate(filterStep, language);
                                
                                if(filterStepDescriptionValue != null) {
                                    if(lockEntityForUpdate(filterStep)) {
                                        try {
                                            var description = edit.getDescription();
                                            
                                            filterStepDescriptionValue.setDescription(description);
                                            
                                            filterControl.updateFilterStepDescriptionFromValue(filterStepDescriptionValue, getPartyPK());
                                        } finally {
                                            unlockEntity(filterStep);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownFilterStepDescription.name());
                                }
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
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
