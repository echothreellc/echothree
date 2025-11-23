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

import com.echothree.control.user.workflow.common.form.CreateWorkflowDestinationSelectorForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.server.control.SelectorControl;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateWorkflowDestinationSelectorCommand
        extends BaseSimpleCommand<CreateWorkflowDestinationSelectorForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.WorkflowDestination.name(), SecurityRoles.Selector.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkflowDestinationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateWorkflowDestinationSelectorCommand */
    public CreateWorkflowDestinationSelectorCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflowName = form.getWorkflowName();
        var workflow = workflowControl.getWorkflowByName(workflowName);
        
        if(workflow != null) {
            var selectorType = workflow.getLastDetail().getSelectorType();
            
            if(selectorType != null) {
                var workflowStepName = form.getWorkflowStepName();
                var workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);
                
                if(workflowStep != null) {
                    var workflowDestinationName = form.getWorkflowDestinationName();
                    var workflowDestination = workflowControl.getWorkflowDestinationByName(workflowStep, workflowDestinationName);
                    
                    if(workflowDestination != null) {
                        var selectorControl = Session.getModelController(SelectorControl.class);
                        var selectorName = form.getSelectorName();
                        var selector = selectorControl.getSelectorByName(workflow.getLastDetail().getSelectorType(), selectorName);
                        
                        if(selector != null) {
                            var workflowDestinationSelector = workflowControl.getWorkflowDestinationSelector(workflowDestination, selector);
                            
                            if(workflowDestinationSelector == null) {
                                workflowControl.createWorkflowDestinationSelector(workflowDestination, selector, getPartyPK());
                            } else {
                                addExecutionError(ExecutionErrors.DuplicateWorkflowDestinationSelector.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownSelectorName.name(), selectorName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownWorkflowDestinationName.name(), workflowDestinationName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWorkflowStepName.name(), workflowStepName);
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidWorkflow.name());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
        }
        
        return null;
    }
    
}
