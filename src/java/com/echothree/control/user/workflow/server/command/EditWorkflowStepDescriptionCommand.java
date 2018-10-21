// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.workflow.remote.edit.WorkflowEditFactory;
import com.echothree.control.user.workflow.remote.edit.WorkflowStepDescriptionEdit;
import com.echothree.control.user.workflow.remote.form.EditWorkflowStepDescriptionForm;
import com.echothree.control.user.workflow.remote.result.EditWorkflowStepDescriptionResult;
import com.echothree.control.user.workflow.remote.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.remote.spec.WorkflowStepDescriptionSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStepDescription;
import com.echothree.model.data.workflow.server.value.WorkflowStepDescriptionValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditWorkflowStepDescriptionCommand
        extends BaseEditCommand<WorkflowStepDescriptionSpec, WorkflowStepDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.WorkflowStep.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditWorkflowStepDescriptionCommand */
    public EditWorkflowStepDescriptionCommand(UserVisitPK userVisitPK, EditWorkflowStepDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        EditWorkflowStepDescriptionResult result = WorkflowResultFactory.getEditWorkflowStepDescriptionResult();
        String workflowName = spec.getWorkflowName();
        Workflow workflow = workflowControl.getWorkflowByName(workflowName);
        
        if(workflow != null) {
            String workflowStepName = spec.getWorkflowStepName();
            WorkflowStep workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);
            
            if(workflowStep != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        WorkflowStepDescription workflowStepDescription = workflowControl.getWorkflowStepDescription(workflowStep, language);
                        
                        if(workflowStepDescription != null) {
                            if(editMode.equals(EditMode.LOCK)) {
                                result.setWorkflowStepDescription(workflowControl.getWorkflowStepDescriptionTransfer(getUserVisit(), workflowStepDescription));

                                if(lockEntity(workflowStep)) {
                                    WorkflowStepDescriptionEdit edit = WorkflowEditFactory.getWorkflowStepDescriptionEdit();

                                    result.setEdit(edit);
                                    edit.setDescription(workflowStepDescription.getDescription());
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                }

                                result.setEntityLock(getEntityLockTransfer(workflowStep));
                            } else { // EditMode.ABANDON
                                unlockEntity(workflowStep);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWorkflowStepDescription.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        WorkflowStepDescriptionValue workflowStepDescriptionValue = workflowControl.getWorkflowStepDescriptionValueForUpdate(workflowStep, language);
                        
                        if(workflowStepDescriptionValue != null) {
                            if(lockEntityForUpdate(workflowStep)) {
                                try {
                                    String description = edit.getDescription();
                                    
                                    workflowStepDescriptionValue.setDescription(description);
                                    
                                    workflowControl.updateWorkflowStepDescriptionFromValue(workflowStepDescriptionValue, getPartyPK());
                                } finally {
                                    unlockEntity(workflowStep);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWorkflowStepDescription.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
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
