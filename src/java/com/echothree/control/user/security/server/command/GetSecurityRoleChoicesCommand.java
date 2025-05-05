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

package com.echothree.control.user.security.server.command;

import com.echothree.control.user.security.common.form.GetSecurityRoleChoicesForm;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
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

public class GetSecurityRoleChoicesCommand
        extends BaseSimpleCommand<GetSecurityRoleChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.SecurityRole.name(), SecurityRoles.Choices.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SecurityRoleGroupName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultSecurityRoleChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetSecurityRoleChoicesCommand */
    public GetSecurityRoleChoicesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = SecurityResultFactory.getGetSecurityRoleChoicesResult();
        var securityRoleGroupName = form.getSecurityRoleGroupName();
        var workflowName = form.getWorkflowName();
        var parameterCount = (securityRoleGroupName == null ? 0 : 1) + (workflowName == null ? 0 : 1);
        
        if(parameterCount == 1) {
            var securityControl = Session.getModelController(SecurityControl.class);
            SecurityRoleGroup securityRoleGroup = null;
            
            if(securityRoleGroupName != null) {
                securityRoleGroup = securityControl.getSecurityRoleGroupByName(securityRoleGroupName);

                if(securityRoleGroup == null) {
                    addExecutionError(ExecutionErrors.UnknownSecurityRoleGroupName.name(), securityRoleGroupName);
                }
            } else {
                var workflowControl = Session.getModelController(WorkflowControl.class);
                var workflow = workflowControl.getWorkflowByName(workflowName);

                if(workflow != null) {
                    securityRoleGroup = workflow.getLastDetail().getSecurityRoleGroup();
                    
                    if(securityRoleGroup == null) {
                        addExecutionError(ExecutionErrors.WorkflowMissingSecurityRoleGroup.name(), workflowName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
                }
            }
            
            if(!hasExecutionErrors()) {
                var defaultSecurityRoleChoice = form.getDefaultSecurityRoleChoice();
                var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());

                result.setSecurityRoleChoices(securityControl.getSecurityRoleChoices(defaultSecurityRoleChoice, getPreferredLanguage(), allowNullChoice,
                        securityRoleGroup));
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
