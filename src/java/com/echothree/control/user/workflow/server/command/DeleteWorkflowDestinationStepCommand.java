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

import com.echothree.control.user.workflow.common.form.DeleteWorkflowDestinationStepForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationStep;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
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

public class DeleteWorkflowDestinationStepCommand
        extends BaseSimpleCommand<DeleteWorkflowDestinationStepForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.WorkflowDestination.name(), SecurityRoles.WorkflowStep.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowDestinationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DestinationWorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DestinationWorkflowStepName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteWorkflowDestinationStepCommand */
    public DeleteWorkflowDestinationStepCommand(UserVisitPK userVisitPK, DeleteWorkflowDestinationStepForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        String workflowName = form.getWorkflowName();
        Workflow workflow = workflowControl.getWorkflowByName(workflowName);
        
        if(workflow != null) {
            String workflowStepName = form.getWorkflowStepName();
            WorkflowStep workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);
            
            if(workflowStep != null) {
                String workflowDestinationName = form.getWorkflowDestinationName();
                WorkflowDestination workflowDestination = workflowControl.getWorkflowDestinationByName(workflowStep, workflowDestinationName);
                
                if(workflowDestination != null) {
                    String destinationWorkflowName = form.getDestinationWorkflowName();
                    Workflow destinationWorkflow = workflowControl.getWorkflowByName(destinationWorkflowName);
                    
                    if(destinationWorkflow != null) {
                        String destinationWorkflowStepName = form.getDestinationWorkflowStepName();
                        WorkflowStep destinationWorkflowStep = workflowControl.getWorkflowStepByName(destinationWorkflow, destinationWorkflowStepName);
                        
                        if(destinationWorkflowStep != null) {
                            WorkflowDestinationStep workflowDestinationStep = workflowControl.getWorkflowDestinationStepForUpdate(workflowDestination, destinationWorkflowStep);
                            
                            if(workflowDestinationStep != null) {
                                workflowControl.deleteWorkflowDestinationStep(workflowDestinationStep, getPartyPK());
                            } else {
                                addExecutionError(ExecutionErrors.UnknownWorkflowDestinationStep.name(), workflowName, workflowStepName, workflowDestinationName,
                                        destinationWorkflowName, destinationWorkflowStepName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownDestinationWorkflowStepName.name(), destinationWorkflowName, destinationWorkflowStepName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownDestinationWorkflowName.name(), destinationWorkflowName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWorkflowDestinationName.name(), workflowName, workflowStepName, workflowDestinationName);
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
