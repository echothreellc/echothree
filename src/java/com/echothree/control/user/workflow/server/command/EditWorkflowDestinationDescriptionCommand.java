// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.workflow.server.command;

import com.echothree.control.user.workflow.common.edit.WorkflowDestinationDescriptionEdit;
import com.echothree.control.user.workflow.common.edit.WorkflowEditFactory;
import com.echothree.control.user.workflow.common.form.EditWorkflowDestinationDescriptionForm;
import com.echothree.control.user.workflow.common.result.EditWorkflowDestinationDescriptionResult;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.common.spec.WorkflowDestinationDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationDescription;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.value.WorkflowDestinationDescriptionValue;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditWorkflowDestinationDescriptionCommand
        extends BaseEditCommand<WorkflowDestinationDescriptionSpec, WorkflowDestinationDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.WorkflowDestination.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowDestinationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditWorkflowDestinationDescriptionCommand */
    public EditWorkflowDestinationDescriptionCommand(UserVisitPK userVisitPK, EditWorkflowDestinationDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        EditWorkflowDestinationDescriptionResult result = WorkflowResultFactory.getEditWorkflowDestinationDescriptionResult();
        String workflowName = spec.getWorkflowName();
        var workflow = workflowControl.getWorkflowByName(workflowName);
        
        if(workflow != null) {
            String workflowStepName = spec.getWorkflowStepName();
            var workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);
            
            if(workflowStep != null) {
                String workflowDestinationName = spec.getWorkflowDestinationName();
                WorkflowDestination workflowDestination = workflowControl.getWorkflowDestinationByName(workflowStep, workflowDestinationName);
                
                if(workflowDestination != null) {
                    var partyControl = Session.getModelController(PartyControl.class);
                    String languageIsoName = spec.getLanguageIsoName();
                    Language language = partyControl.getLanguageByIsoName(languageIsoName);
                    
                    if(language != null) {
                        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                            WorkflowDestinationDescription workflowDestinationDescription = workflowControl.getWorkflowDestinationDescription(workflowDestination, language);
                            
                            if(workflowDestinationDescription != null) {
                                if(editMode.equals(EditMode.LOCK)) {
                                    result.setWorkflowDestinationDescription(workflowControl.getWorkflowDestinationDescriptionTransfer(getUserVisit(), workflowDestinationDescription));

                                    if(lockEntity(workflowDestination)) {
                                        WorkflowDestinationDescriptionEdit edit = WorkflowEditFactory.getWorkflowDestinationDescriptionEdit();

                                        result.setEdit(edit);
                                        edit.setDescription(workflowDestinationDescription.getDescription());
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                    }

                                    result.setEntityLock(getEntityLockTransfer(workflowDestination));
                                } else { // EditMode.ABANDON
                                    unlockEntity(workflowDestination);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownWorkflowDestinationDescription.name());
                            }
                        } else if(editMode.equals(EditMode.UPDATE)) {
                            WorkflowDestinationDescriptionValue workflowDestinationDescriptionValue = workflowControl.getWorkflowDestinationDescriptionValueForUpdate(workflowDestination, language);
                            
                            if(workflowDestinationDescriptionValue != null) {
                                if(lockEntityForUpdate(workflowDestination)) {
                                    try {
                                        String description = edit.getDescription();
                                        
                                        workflowDestinationDescriptionValue.setDescription(description);
                                        
                                        workflowControl.updateWorkflowDestinationDescriptionFromValue(workflowDestinationDescriptionValue, getPartyPK());
                                    } finally {
                                        unlockEntity(workflowDestination);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownWorkflowDestinationDescription.name());
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWorkflowDestinationName.name(), workflowDestinationName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWorkflowStepName.name(), workflowStepName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }
        
        return result;
    }
    
}
