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

import com.echothree.control.user.workflow.common.form.CreateWorkflowDestinationPartyTypeForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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

public class CreateWorkflowDestinationPartyTypeCommand
        extends BaseSimpleCommand<CreateWorkflowDestinationPartyTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.WorkflowDestination.name(), SecurityRoles.PartyType.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowDestinationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateWorkflowDestinationPartyTypeCommand */
    public CreateWorkflowDestinationPartyTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowName = form.getWorkflowName();
        var workflow = workflowControl.getWorkflowByName(workflowName);
        
        if(workflow != null) {
            var workflowStepName = form.getWorkflowStepName();
            var workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);
            
            if(workflowStep != null) {
                var workflowDestinationName = form.getWorkflowDestinationName();
                var workflowDestination = workflowControl.getWorkflowDestinationByName(workflowStep, workflowDestinationName);
                
                if(workflowDestination != null) {
                    var partyControl = Session.getModelController(PartyControl.class);
                    var partyTypeName = form.getPartyTypeName();
                    var partyType = partyControl.getPartyTypeByName(partyTypeName);
                    
                    if(partyType != null) {
                        var workflowDestinationPartyType = workflowControl.getWorkflowDestinationPartyType(workflowDestination, partyType);
                        
                        if(workflowDestinationPartyType == null) {
                            workflowControl.createWorkflowDestinationPartyType(workflowDestination, partyType, getPartyPK());
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateWorkflowDestinationPartyType.name(), workflowName,
                                    workflowStepName, workflowDestinationName, partyTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWorkflowDestinationName.name(), workflowName, workflowStepName,
                            workflowDestinationName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWorkflowStepName.name(), workflowName, workflowStepName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }
        
        return null;
    }
    
}
