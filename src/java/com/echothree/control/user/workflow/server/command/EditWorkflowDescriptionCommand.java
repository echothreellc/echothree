// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.workflow.common.edit.WorkflowDescriptionEdit;
import com.echothree.control.user.workflow.common.edit.WorkflowEditFactory;
import com.echothree.control.user.workflow.common.form.EditWorkflowDescriptionForm;
import com.echothree.control.user.workflow.common.result.EditWorkflowDescriptionResult;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.common.spec.WorkflowDescriptionSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDescription;
import com.echothree.model.data.workflow.server.value.WorkflowDescriptionValue;
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

public class EditWorkflowDescriptionCommand
        extends BaseEditCommand<WorkflowDescriptionSpec, WorkflowDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Workflow.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditWorkflowDescriptionCommand */
    public EditWorkflowDescriptionCommand(UserVisitPK userVisitPK, EditWorkflowDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        EditWorkflowDescriptionResult result = WorkflowResultFactory.getEditWorkflowDescriptionResult();
        String workflowName = spec.getWorkflowName();
        Workflow workflow = workflowControl.getWorkflowByName(workflowName);
        
        if(workflow != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    WorkflowDescription workflowDescription = workflowControl.getWorkflowDescription(workflow, language);
                    
                    if(workflowDescription != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            result.setWorkflowDescription(workflowControl.getWorkflowDescriptionTransfer(getUserVisit(), workflowDescription));

                            if(lockEntity(workflow)) {
                                WorkflowDescriptionEdit edit = WorkflowEditFactory.getWorkflowDescriptionEdit();

                                result.setEdit(edit);
                                edit.setDescription(workflowDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }

                            result.setEntityLock(getEntityLockTransfer(workflow));
                        } else { // EditMode.ABANDON
                            unlockEntity(workflow);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownWorkflowDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    WorkflowDescriptionValue workflowDescriptionValue = workflowControl.getWorkflowDescriptionValueForUpdate(workflow, language);
                    
                    if(workflowDescriptionValue != null) {
                        if(lockEntityForUpdate(workflow)) {
                            try {
                                String description = edit.getDescription();
                                
                                workflowDescriptionValue.setDescription(description);
                                
                                workflowControl.updateWorkflowDescriptionFromValue(workflowDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(workflow);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownWorkflowDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }
        
        return result;
    }
    
}
