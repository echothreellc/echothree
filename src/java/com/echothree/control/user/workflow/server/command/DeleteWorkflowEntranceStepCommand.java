// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.control.user.workflow.common.form.DeleteWorkflowEntranceStepForm;
import com.echothree.model.control.party.common.PartyTypes;
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
import javax.enterprise.context.Dependent;

@Dependent
public class DeleteWorkflowEntranceStepCommand
        extends BaseSimpleCommand<DeleteWorkflowEntranceStepForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.WorkflowEntrance.name(), SecurityRoles.WorkflowStep.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntranceWorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntranceWorkflowStepName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteWorkflowEntranceStepCommand */
    public DeleteWorkflowEntranceStepCommand() {
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
                var entranceWorkflowName = form.getEntranceWorkflowName();
                var entranceWorkflow = workflowControl.getWorkflowByName(entranceWorkflowName);
                
                if(entranceWorkflow != null) {
                    var entranceWorkflowStepName = form.getEntranceWorkflowStepName();
                    var entranceWorkflowStep = workflowControl.getWorkflowStepByName(entranceWorkflow, entranceWorkflowStepName);
                    
                    if(entranceWorkflowStep != null) {
                        var workflowEntranceStep = workflowControl.getWorkflowEntranceStepForUpdate(workflowEntrance, entranceWorkflowStep);
                        
                        if(workflowEntranceStep != null) {
                            workflowControl.deleteWorkflowEntranceStep(workflowEntranceStep, getPartyPK());
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWorkflowEntranceStep.name(), workflowName, workflowEntranceName, entranceWorkflowName,
                                    entranceWorkflowStepName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEntranceWorkflowStepName.name(), entranceWorkflowName, entranceWorkflowStepName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntranceWorkflowName.name(), entranceWorkflowName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWorkflowEntranceName.name(), workflowName, workflowEntranceName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }
        
        return null;
    }
    
}
