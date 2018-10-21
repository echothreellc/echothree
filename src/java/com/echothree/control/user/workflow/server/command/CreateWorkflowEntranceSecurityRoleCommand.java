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

import com.echothree.control.user.workflow.remote.form.CreateWorkflowEntranceSecurityRoleForm;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.server.SecurityControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.security.server.entity.SecurityRole;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrancePartyType;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceSecurityRole;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateWorkflowEntranceSecurityRoleCommand
        extends BaseSimpleCommand<CreateWorkflowEntranceSecurityRoleForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SecurityRoleName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateWorkflowEntranceSecurityRoleCommand */
    public CreateWorkflowEntranceSecurityRoleCommand(UserVisitPK userVisitPK, CreateWorkflowEntranceSecurityRoleForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
        String workflowName = form.getWorkflowName();
        Workflow workflow = workflowControl.getWorkflowByName(workflowName);
        
        if(workflow != null) {
            String workflowEntranceName = form.getWorkflowEntranceName();
            WorkflowEntrance workflowEntrance = workflowControl.getWorkflowEntranceByName(workflow, workflowEntranceName);
            
            if(workflowEntrance != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String partyTypeName = form.getPartyTypeName();
                PartyType partyType = partyControl.getPartyTypeByName(partyTypeName);
                
                if(partyType != null) {
                    WorkflowEntrancePartyType workflowEntrancePartyType = workflowControl.getWorkflowEntrancePartyType(workflowEntrance, partyType);
                    
                    if(workflowEntrancePartyType != null) {
                        SecurityRoleGroup securityRoleGroup = workflow.getLastDetail().getSecurityRoleGroup();

                        if(securityRoleGroup != null) {
                            SecurityControl securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
                            String securityRoleName = form.getSecurityRoleName();
                            SecurityRole securityRole = securityControl.getSecurityRoleByName(securityRoleGroup, securityRoleName);
                            
                            if(securityRole != null) {
                                WorkflowEntranceSecurityRole workflowEntranceSecurityRole = workflowControl.getWorkflowEntranceSecurityRole(workflowEntrancePartyType, securityRole);
                                
                                if(workflowEntranceSecurityRole == null) {
                                    workflowControl.createWorkflowEntranceSecurityRole(workflowEntrancePartyType, securityRole, getPartyPK());
                                } else {
                                    addExecutionError(ExecutionErrors.DuplicateWorkflowEntranceSecurityRole.name(), workflowName,
                                            workflowEntranceName, partyTypeName, securityRoleName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownSecurityRoleName.name(),
                                        securityRoleGroup.getLastDetail().getSecurityRoleGroupName(), securityRoleName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.WorkflowMissingSecurityRoleGroup.name(), workflowName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownWorkflowEntrancePartyType.name(), workflowName, workflowEntranceName,
                                partyTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWorkflowEntranceName.name(), workflowEntranceName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }
        
        return null;
    }
    
}
