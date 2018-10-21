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
import com.echothree.control.user.workflow.remote.edit.WorkflowEntranceDescriptionEdit;
import com.echothree.control.user.workflow.remote.form.EditWorkflowEntranceDescriptionForm;
import com.echothree.control.user.workflow.remote.result.EditWorkflowEntranceDescriptionResult;
import com.echothree.control.user.workflow.remote.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.remote.spec.WorkflowEntranceDescriptionSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceDescription;
import com.echothree.model.data.workflow.server.value.WorkflowEntranceDescriptionValue;
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

public class EditWorkflowEntranceDescriptionCommand
        extends BaseEditCommand<WorkflowEntranceDescriptionSpec, WorkflowEntranceDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.WorkflowEntrance.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditWorkflowEntranceDescriptionCommand */
    public EditWorkflowEntranceDescriptionCommand(UserVisitPK userVisitPK, EditWorkflowEntranceDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        EditWorkflowEntranceDescriptionResult result = WorkflowResultFactory.getEditWorkflowEntranceDescriptionResult();
        String workflowName = spec.getWorkflowName();
        Workflow workflow = workflowControl.getWorkflowByName(workflowName);
        
        if(workflow != null) {
            String workflowEntranceName = spec.getWorkflowEntranceName();
            WorkflowEntrance workflowEntrance = workflowControl.getWorkflowEntranceByName(workflow, workflowEntranceName);
            
            if(workflowEntrance != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        WorkflowEntranceDescription workflowEntranceDescription = workflowControl.getWorkflowEntranceDescription(workflowEntrance, language);
                        
                        if(workflowEntranceDescription != null) {
                            if(editMode.equals(EditMode.LOCK)) {
                                result.setWorkflowEntranceDescription(workflowControl.getWorkflowEntranceDescriptionTransfer(getUserVisit(), workflowEntranceDescription));

                                if(lockEntity(workflowEntrance)) {
                                    WorkflowEntranceDescriptionEdit edit = WorkflowEditFactory.getWorkflowEntranceDescriptionEdit();

                                    result.setEdit(edit);
                                    edit.setDescription(workflowEntranceDescription.getDescription());
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                }

                                result.setEntityLock(getEntityLockTransfer(workflowEntrance));
                            } else { // EditMode.ABANDON
                                unlockEntity(workflowEntrance);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWorkflowEntranceDescription.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        WorkflowEntranceDescriptionValue workflowEntranceDescriptionValue = workflowControl.getWorkflowEntranceDescriptionValueForUpdate(workflowEntrance, language);
                        
                        if(workflowEntranceDescriptionValue != null) {
                            if(lockEntityForUpdate(workflowEntrance)) {
                                try {
                                    String description = edit.getDescription();
                                    
                                    workflowEntranceDescriptionValue.setDescription(description);
                                    
                                    workflowControl.updateWorkflowEntranceDescriptionFromValue(workflowEntranceDescriptionValue, getPartyPK());
                                } finally {
                                    unlockEntity(workflowEntrance);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWorkflowEntranceDescription.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWorkflowEntranceName.name(), workflowEntranceName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }
        
        return result;
    }
    
}
