// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.workflow.common.edit.WorkflowDestinationEdit;
import com.echothree.control.user.workflow.common.edit.WorkflowEditFactory;
import com.echothree.control.user.workflow.common.form.EditWorkflowDestinationForm;
import com.echothree.control.user.workflow.common.result.EditWorkflowDestinationResult;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.common.spec.WorkflowDestinationSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationDescription;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationDetail;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.value.WorkflowDestinationDescriptionValue;
import com.echothree.model.data.workflow.server.value.WorkflowDestinationDetailValue;
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

public class EditWorkflowDestinationCommand
        extends BaseEditCommand<WorkflowDestinationSpec, WorkflowDestinationEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.WorkflowDestination.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowDestinationName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowDestinationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditWorkflowDestinationCommand */
    public EditWorkflowDestinationCommand(UserVisitPK userVisitPK, EditWorkflowDestinationForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        EditWorkflowDestinationResult result = WorkflowResultFactory.getEditWorkflowDestinationResult();
        String workflowName = spec.getWorkflowName();
        Workflow workflow = workflowControl.getWorkflowByName(workflowName);
        
        if(workflow != null) {
            String workflowStepName = spec.getWorkflowStepName();
            WorkflowStep workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);
            
            if(workflowStep != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    String workflowDestinationName = spec.getWorkflowDestinationName();
                    WorkflowDestination workflowDestination = workflowControl.getWorkflowDestinationByName(workflowStep, workflowDestinationName);

                    if(workflowDestination != null) {
                        if(editMode.equals(EditMode.LOCK)) {
                            if(lockEntity(workflowDestination)) {
                                WorkflowDestinationDescription workflowDestinationDescription = workflowControl.getWorkflowDestinationDescription(workflowDestination, getPreferredLanguage());
                                WorkflowDestinationEdit edit = WorkflowEditFactory.getWorkflowDestinationEdit();
                                WorkflowDestinationDetail workflowDestinationDetail = workflowDestination.getLastDetail();

                                result.setWorkflowDestination(workflowControl.getWorkflowDestinationTransfer(getUserVisit(), workflowDestination));

                                result.setEdit(edit);
                                edit.setWorkflowDestinationName(workflowDestinationDetail.getWorkflowDestinationName());
                                edit.setIsDefault(workflowDestinationDetail.getIsDefault().toString());
                                edit.setSortOrder(workflowDestinationDetail.getSortOrder().toString());

                                if(workflowDestinationDescription != null) {
                                    edit.setDescription(workflowDestinationDescription.getDescription());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }

                            result.setEntityLock(getEntityLockTransfer(workflowDestination));
                        } else { // EditMode.ABANDON
                            unlockEntity(workflowDestination);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownWorkflowDestinationName.name(), workflowName, workflowStepName, workflowDestinationName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    String workflowDestinationName = spec.getWorkflowDestinationName();
                    WorkflowDestination workflowDestination = workflowControl.getWorkflowDestinationByNameForUpdate(workflowStep, workflowDestinationName);

                    if(workflowDestination != null) {
                        workflowDestinationName = edit.getWorkflowDestinationName();
                        WorkflowDestination duplicateWorkflowDestination = workflowControl.getWorkflowDestinationByName(workflowStep, workflowDestinationName);

                        if(duplicateWorkflowDestination == null || workflowDestination.equals(duplicateWorkflowDestination)) {
                            if(lockEntityForUpdate(workflowDestination)) {
                                try {
                                    PartyPK partyPK = getPartyPK();
                                    WorkflowDestinationDetailValue workflowDestinationDetailValue = workflowControl.getWorkflowDestinationDetailValueForUpdate(workflowDestination);
                                    WorkflowDestinationDescription workflowDestinationDescription = workflowControl.getWorkflowDestinationDescriptionForUpdate(workflowDestination, getPreferredLanguage());
                                    String description = edit.getDescription();

                                    workflowDestinationDetailValue.setWorkflowDestinationName(workflowDestinationName);
                                    workflowDestinationDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    workflowDestinationDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

                                    workflowControl.updateWorkflowDestinationFromValue(workflowDestinationDetailValue, partyPK);

                                    if(workflowDestinationDescription == null && description != null) {
                                        workflowControl.createWorkflowDestinationDescription(workflowDestination, getPreferredLanguage(), description, partyPK);
                                    } else if(workflowDestinationDescription != null && description == null) {
                                        workflowControl.deleteWorkflowDestinationDescription(workflowDestinationDescription, partyPK);
                                    } else if(workflowDestinationDescription != null && description != null) {
                                        WorkflowDestinationDescriptionValue workflowDestinationDescriptionValue = workflowControl.getWorkflowDestinationDescriptionValue(workflowDestinationDescription);

                                        workflowDestinationDescriptionValue.setDescription(description);
                                        workflowControl.updateWorkflowDestinationDescriptionFromValue(workflowDestinationDescriptionValue, partyPK);
                                    }
                                } finally {
                                    unlockEntity(workflowDestination);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateWorkflowDestinationName.name(), workflowName, workflowStepName, workflowDestinationName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownWorkflowDestinationName.name(), workflowName, workflowStepName, workflowDestinationName);
                    }

                    if(hasExecutionErrors()) {
                        result.setWorkflowDestination(workflowControl.getWorkflowDestinationTransfer(getUserVisit(), workflowDestination));
                        result.setEntityLock(getEntityLockTransfer(workflowDestination));
                    }
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
