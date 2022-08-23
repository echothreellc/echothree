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

import com.echothree.control.user.workflow.common.edit.WorkflowEditFactory;
import com.echothree.control.user.workflow.common.edit.WorkflowStepEdit;
import com.echothree.control.user.workflow.common.form.EditWorkflowStepForm;
import com.echothree.control.user.workflow.common.result.EditWorkflowStepResult;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.common.spec.WorkflowStepSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStepDescription;
import com.echothree.model.data.workflow.server.entity.WorkflowStepDetail;
import com.echothree.model.data.workflow.server.entity.WorkflowStepType;
import com.echothree.model.data.workflow.server.value.WorkflowStepDescriptionValue;
import com.echothree.model.data.workflow.server.value.WorkflowStepDetailValue;
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

public class EditWorkflowStepCommand
        extends BaseEditCommand<WorkflowStepSpec, WorkflowStepEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.WorkflowStep.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditWorkflowStepCommand */
    public EditWorkflowStepCommand(UserVisitPK userVisitPK, EditWorkflowStepForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        EditWorkflowStepResult result = WorkflowResultFactory.getEditWorkflowStepResult();
        String workflowName = spec.getWorkflowName();
        var workflow = workflowControl.getWorkflowByName(workflowName);
        
        if(workflow != null) {
            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                String workflowStepName = spec.getWorkflowStepName();
                var workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);

                if(workflowStep != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        if(lockEntity(workflowStep)) {
                            WorkflowStepDescription workflowStepDescription = workflowControl.getWorkflowStepDescription(workflowStep, getPreferredLanguage());
                            WorkflowStepEdit edit = WorkflowEditFactory.getWorkflowStepEdit();
                            WorkflowStepDetail workflowStepDetail = workflowStep.getLastDetail();

                            result.setWorkflowStep(workflowControl.getWorkflowStepTransfer(getUserVisit(), workflowStep));

                            result.setEdit(edit);
                            edit.setWorkflowStepName(workflowStepDetail.getWorkflowStepName());
                            edit.setWorkflowStepTypeName(workflowStepDetail.getWorkflowStepType().getWorkflowStepTypeName());
                            edit.setIsDefault(workflowStepDetail.getIsDefault().toString());
                            edit.setSortOrder(workflowStepDetail.getSortOrder().toString());

                            if(workflowStepDescription != null) {
                                edit.setDescription(workflowStepDescription.getDescription());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }

                        result.setEntityLock(getEntityLockTransfer(workflowStep));
                    } else { // EditMode.ABANDON
                        unlockEntity(workflowStep);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWorkflowStepName.name(), workflowName, workflowStepName);
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                String workflowStepName = spec.getWorkflowStepName();
                var workflowStep = workflowControl.getWorkflowStepByNameForUpdate(workflow, workflowStepName);

                if(workflowStep != null) {
                    workflowStepName = edit.getWorkflowStepName();
                    WorkflowStep duplicateWorkflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);

                    if(duplicateWorkflowStep == null || workflowStep.equals(duplicateWorkflowStep)) {
                        String workflowStepTypeName = edit.getWorkflowStepTypeName();
                        WorkflowStepType workflowStepType = workflowControl.getWorkflowStepTypeByName(workflowStepTypeName);

                        if(workflowStepType != null) {
                            if(lockEntityForUpdate(workflowStep)) {
                                try {
                                    var partyPK = getPartyPK();
                                    WorkflowStepDetailValue workflowStepDetailValue = workflowControl.getWorkflowStepDetailValueForUpdate(workflowStep);
                                    WorkflowStepDescription workflowStepDescription = workflowControl.getWorkflowStepDescriptionForUpdate(workflowStep, getPreferredLanguage());
                                    String description = edit.getDescription();

                                    workflowStepDetailValue.setWorkflowStepName(workflowStepName);
                                    workflowStepDetailValue.setWorkflowStepTypePK(workflowStepType.getPrimaryKey());
                                    workflowStepDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    workflowStepDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

                                    workflowControl.updateWorkflowStepFromValue(workflowStepDetailValue, partyPK);

                                    if(workflowStepDescription == null && description != null) {
                                        workflowControl.createWorkflowStepDescription(workflowStep, getPreferredLanguage(), description, partyPK);
                                    } else if(workflowStepDescription != null && description == null) {
                                        workflowControl.deleteWorkflowStepDescription(workflowStepDescription, partyPK);
                                    } else if(workflowStepDescription != null && description != null) {
                                        WorkflowStepDescriptionValue workflowStepDescriptionValue = workflowControl.getWorkflowStepDescriptionValue(workflowStepDescription);

                                        workflowStepDescriptionValue.setDescription(description);
                                        workflowControl.updateWorkflowStepDescriptionFromValue(workflowStepDescriptionValue, partyPK);
                                    }
                                } finally {
                                    unlockEntity(workflowStep);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWorkflowStepTypeName.name(), workflowStepTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateWorkflowStepName.name(), workflowName, workflowStepName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWorkflowStepName.name(), workflowName, workflowStepName);
                }

                if(hasExecutionErrors()) {
                    result.setWorkflowStep(workflowControl.getWorkflowStepTransfer(getUserVisit(), workflowStep));
                    result.setEntityLock(getEntityLockTransfer(workflowStep));
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }
        
        return result;
    }
    
}
