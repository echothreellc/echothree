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

import com.echothree.control.user.workflow.common.edit.WorkflowEditFactory;
import com.echothree.control.user.workflow.common.edit.WorkflowEntranceEdit;
import com.echothree.control.user.workflow.common.form.EditWorkflowEntranceForm;
import com.echothree.control.user.workflow.common.result.EditWorkflowEntranceResult;
import com.echothree.control.user.workflow.common.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.common.spec.WorkflowEntranceSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceDescription;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceDetail;
import com.echothree.model.data.workflow.server.value.WorkflowEntranceDescriptionValue;
import com.echothree.model.data.workflow.server.value.WorkflowEntranceDetailValue;
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

public class EditWorkflowEntranceCommand
        extends BaseEditCommand<WorkflowEntranceSpec, WorkflowEntranceEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.WorkflowEntrance.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditWorkflowEntranceCommand */
    public EditWorkflowEntranceCommand(UserVisitPK userVisitPK, EditWorkflowEntranceForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        EditWorkflowEntranceResult result = WorkflowResultFactory.getEditWorkflowEntranceResult();
        String workflowName = spec.getWorkflowName();
        Workflow workflow = workflowControl.getWorkflowByName(workflowName);
        
        if(workflow != null) {
            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                String workflowEntranceName = spec.getWorkflowEntranceName();
                WorkflowEntrance workflowEntrance = workflowControl.getWorkflowEntranceByName(workflow, workflowEntranceName);

                if(workflowEntrance != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        if(lockEntity(workflowEntrance)) {
                            WorkflowEntranceDescription workflowEntranceDescription = workflowControl.getWorkflowEntranceDescription(workflowEntrance, getPreferredLanguage());
                            WorkflowEntranceEdit edit = WorkflowEditFactory.getWorkflowEntranceEdit();
                            WorkflowEntranceDetail workflowEntranceDetail = workflowEntrance.getLastDetail();

                            result.setWorkflowEntrance(workflowControl.getWorkflowEntranceTransfer(getUserVisit(), workflowEntrance));

                            result.setEdit(edit);
                            edit.setWorkflowEntranceName(workflowEntranceDetail.getWorkflowEntranceName());
                            edit.setIsDefault(workflowEntranceDetail.getIsDefault().toString());
                            edit.setSortOrder(workflowEntranceDetail.getSortOrder().toString());

                            if(workflowEntranceDescription != null) {
                                edit.setDescription(workflowEntranceDescription.getDescription());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }

                        result.setEntityLock(getEntityLockTransfer(workflowEntrance));
                    } else { // EditMode.ABANDON
                        unlockEntity(workflowEntrance);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWorkflowEntranceName.name(), workflowName, workflowEntranceName);
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                String workflowEntranceName = spec.getWorkflowEntranceName();
                WorkflowEntrance workflowEntrance = workflowControl.getWorkflowEntranceByNameForUpdate(workflow, workflowEntranceName);

                if(workflowEntrance != null) {
                    workflowEntranceName = edit.getWorkflowEntranceName();
                    WorkflowEntrance duplicateWorkflowEntrance = workflowControl.getWorkflowEntranceByName(workflow, workflowEntranceName);

                    if(duplicateWorkflowEntrance == null || workflowEntrance.equals(duplicateWorkflowEntrance)) {
                        if(lockEntityForUpdate(workflowEntrance)) {
                            try {
                                PartyPK partyPK = getPartyPK();
                                WorkflowEntranceDetailValue workflowEntranceDetailValue = workflowControl.getWorkflowEntranceDetailValueForUpdate(workflowEntrance);
                                WorkflowEntranceDescription workflowEntranceDescription = workflowControl.getWorkflowEntranceDescriptionForUpdate(workflowEntrance, getPreferredLanguage());
                                String description = edit.getDescription();

                                workflowEntranceDetailValue.setWorkflowEntranceName(workflowEntranceName);
                                workflowEntranceDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                workflowEntranceDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

                                workflowControl.updateWorkflowEntranceFromValue(workflowEntranceDetailValue, partyPK);

                                if(workflowEntranceDescription == null && description != null) {
                                    workflowControl.createWorkflowEntranceDescription(workflowEntrance, getPreferredLanguage(), description, partyPK);
                                } else if(workflowEntranceDescription != null && description == null) {
                                    workflowControl.deleteWorkflowEntranceDescription(workflowEntranceDescription, partyPK);
                                } else if(workflowEntranceDescription != null && description != null) {
                                    WorkflowEntranceDescriptionValue workflowEntranceDescriptionValue = workflowControl.getWorkflowEntranceDescriptionValue(workflowEntranceDescription);

                                    workflowEntranceDescriptionValue.setDescription(description);
                                    workflowControl.updateWorkflowEntranceDescriptionFromValue(workflowEntranceDescriptionValue, partyPK);
                                }
                            } finally {
                                unlockEntity(workflowEntrance);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateWorkflowEntranceName.name(), workflowName, workflowEntranceName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWorkflowEntranceName.name(), workflowName, workflowEntranceName);
                }

                if(hasExecutionErrors()) {
                    result.setWorkflowEntrance(workflowControl.getWorkflowEntranceTransfer(getUserVisit(), workflowEntrance));
                    result.setEntityLock(getEntityLockTransfer(workflowEntrance));
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }
        
        return result;
    }
    
}
