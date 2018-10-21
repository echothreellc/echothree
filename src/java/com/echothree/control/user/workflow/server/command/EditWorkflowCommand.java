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

import com.echothree.control.user.workflow.remote.edit.WorkflowEdit;
import com.echothree.control.user.workflow.remote.edit.WorkflowEditFactory;
import com.echothree.control.user.workflow.remote.form.EditWorkflowForm;
import com.echothree.control.user.workflow.remote.result.EditWorkflowResult;
import com.echothree.control.user.workflow.remote.result.WorkflowResultFactory;
import com.echothree.control.user.workflow.remote.spec.WorkflowSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.SecurityControl;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDescription;
import com.echothree.model.data.workflow.server.entity.WorkflowDetail;
import com.echothree.model.data.workflow.server.entity.WorkflowType;
import com.echothree.model.data.workflow.server.value.WorkflowDescriptionValue;
import com.echothree.model.data.workflow.server.value.WorkflowDetailValue;
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

public class EditWorkflowCommand
        extends BaseEditCommand<WorkflowSpec, WorkflowEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.Workflow.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SecurityRoleGroupName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditWorkflowCommand */
    public EditWorkflowCommand(UserVisitPK userVisitPK, EditWorkflowForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        EditWorkflowResult result = WorkflowResultFactory.getEditWorkflowResult();
        
        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            String workflowName = spec.getWorkflowName();
            Workflow workflow = workflowControl.getWorkflowByName(workflowName);
            
            if(workflow != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    if(lockEntity(workflow)) {
                        WorkflowDescription workflowDescription = workflowControl.getWorkflowDescription(workflow, getPreferredLanguage());
                        WorkflowEdit edit = WorkflowEditFactory.getWorkflowEdit();
                        WorkflowDetail workflowDetail = workflow.getLastDetail();
                        SelectorType selectorType = workflowDetail.getSelectorType();
                        SelectorKind selectorKind = selectorType == null ? null : selectorType.getLastDetail().getSelectorKind();
                        SecurityRoleGroup securityRoleGroup = workflowDetail.getSecurityRoleGroup();

                        result.setWorkflow(workflowControl.getWorkflowTransfer(getUserVisit(), workflow));

                        result.setEdit(edit);
                        edit.setWorkflowName(workflowDetail.getWorkflowName());
                        edit.setWorkflowTypeName(workflowDetail.getWorkflowType().getWorkflowTypeName());
                        edit.setSelectorKindName(selectorKind == null ? null : selectorKind.getLastDetail().getSelectorKindName());
                        edit.setSelectorTypeName(selectorType == null ? null : selectorType.getLastDetail().getSelectorTypeName());
                        edit.setSecurityRoleGroupName(securityRoleGroup == null ? null : securityRoleGroup.getLastDetail().getSecurityRoleGroupName());
                        edit.setSortOrder(workflowDetail.getSortOrder().toString());

                        if(workflowDescription != null) {
                            edit.setDescription(workflowDescription.getDescription());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }

                    result.setEntityLock(getEntityLockTransfer(workflow));
                } else { // EditMode.ABANDON
                    unlockEntity(workflow);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String workflowName = spec.getWorkflowName();
            Workflow workflow = workflowControl.getWorkflowByNameForUpdate(workflowName);
            
            if(workflow != null) {
                workflowName = edit.getWorkflowName();
                Workflow duplicateWorkflow = workflowControl.getWorkflowByName(workflowName);
                
                if(duplicateWorkflow == null || workflow.equals(duplicateWorkflow)) {
                    String workflowTypeName = edit.getWorkflowTypeName();
                    WorkflowType workflowType = workflowControl.getWorkflowTypeByName(workflowTypeName);

                    if(workflowType != null) {
                        SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);
                        String selectorKindName = edit.getSelectorKindName();
                        String selectorTypeName = edit.getSelectorTypeName();
                        int parameterCount = (selectorKindName == null? 0: 1) + (selectorTypeName == null? 0: 1);

                        if(parameterCount == 0 || parameterCount == 2) {
                            SelectorKind selectorKind = selectorKindName == null? null: selectorControl.getSelectorKindByName(selectorKindName);

                            if(selectorKindName == null || selectorKind != null) {
                                SelectorType selectorType = selectorTypeName == null? null: selectorControl.getSelectorTypeByName(selectorKind, selectorTypeName);

                                if(selectorTypeName == null || selectorType != null) {
                                    SecurityControl securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
                                    String securityRoleGroupName = edit.getSecurityRoleGroupName();
                                    SecurityRoleGroup securityRoleGroup = securityRoleGroupName == null? null: securityControl.getSecurityRoleGroupByName(securityRoleGroupName);

                                    if(securityRoleGroupName == null || securityRoleGroup != null) {
                                        if(lockEntityForUpdate(workflow)) {
                                            try {
                                                PartyPK partyPK = getPartyPK();
                                                WorkflowDetailValue workflowDetailValue = workflowControl.getWorkflowDetailValueForUpdate(workflow);
                                                WorkflowDescription workflowDescription = workflowControl.getWorkflowDescriptionForUpdate(workflow, getPreferredLanguage());
                                                String description = edit.getDescription();
                                                
                                                workflowDetailValue.setWorkflowName(workflowName);
                                                workflowDetailValue.setWorkflowTypePK(workflowType.getPrimaryKey());
                                                workflowDetailValue.setSelectorTypePK(selectorType == null? null: selectorType.getPrimaryKey());
                                                workflowDetailValue.setSecurityRoleGroupPK(securityRoleGroup == null? null: securityRoleGroup.getPrimaryKey());
                                                workflowDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                                
                                                workflowControl.updateWorkflowFromValue(workflowDetailValue, partyPK);
                                                
                                                if(workflowDescription == null && description != null) {
                                                    workflowControl.createWorkflowDescription(workflow, getPreferredLanguage(), description, partyPK);
                                                } else if(workflowDescription != null && description == null) {
                                                    workflowControl.deleteWorkflowDescription(workflowDescription, partyPK);
                                                } else if(workflowDescription != null && description != null) {
                                                    WorkflowDescriptionValue workflowDescriptionValue = workflowControl.getWorkflowDescriptionValue(workflowDescription);
                                                    
                                                    workflowDescriptionValue.setDescription(description);
                                                    workflowControl.updateWorkflowDescriptionFromValue(workflowDescriptionValue, partyPK);
                                                }
                                            } finally {
                                                unlockEntity(workflow);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownSecurityRoleGroupName.name(), securityRoleGroupName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), selectorKindName, selectorTypeName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), selectorKindName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownWorkflowTypeName.name(), workflowTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateWorkflowName.name(), workflowName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
            }
            
            if(hasExecutionErrors()) {
                result.setWorkflow(workflowControl.getWorkflowTransfer(getUserVisit(), workflow));
                result.setEntityLock(getEntityLockTransfer(workflow));
            }
        }
        
        return result;
    }
    
}
