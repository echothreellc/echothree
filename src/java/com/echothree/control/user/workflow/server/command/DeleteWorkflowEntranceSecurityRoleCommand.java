// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.workflow.common.form.DeleteWorkflowEntranceSecurityRoleForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteWorkflowEntranceSecurityRoleCommand
        extends BaseSimpleCommand<DeleteWorkflowEntranceSecurityRoleForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.WorkflowEntrance.name(), SecurityRoles.SecurityRole.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SecurityRoleName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteWorkflowEntranceSecurityRoleCommand */
    public DeleteWorkflowEntranceSecurityRoleCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowName = form.getWorkflowName();
        var workflow = workflowControl.getWorkflowByName(workflowName);
        
        if(workflow != null) {
            var workflowEntranceName = form.getWorkflowEntranceName();
            var workflowEntrance = workflowControl.getWorkflowEntranceByName(workflow, workflowEntranceName);
            
            if(workflowEntrance != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var partyTypeName = form.getPartyTypeName();
                var partyType = partyControl.getPartyTypeByName(partyTypeName);
                
                if(partyType != null) {
                    var workflowEntrancePartyType = workflowControl.getWorkflowEntrancePartyType(workflowEntrance, partyType);
                    
                    if(workflowEntrancePartyType != null) {
                        var securityRoleGroup = workflow.getLastDetail().getSecurityRoleGroup();

                        if(securityRoleGroup != null) {
                            var securityControl = Session.getModelController(SecurityControl.class);
                            var securityRoleName = form.getSecurityRoleName();
                            var securityRole = securityControl.getSecurityRoleByName(securityRoleGroup, securityRoleName);
                            
                            if(securityRole != null) {
                                var workflowEntranceSecurityRole = workflowControl.getWorkflowEntranceSecurityRoleForUpdate(workflowEntrancePartyType, securityRole);
                                
                                if(workflowEntranceSecurityRole != null) {
                                    workflowControl.deleteWorkflowEntranceSecurityRole(workflowEntranceSecurityRole, getPartyPK());
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownWorkflowEntranceSecurityRole.name(), workflowName,
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
