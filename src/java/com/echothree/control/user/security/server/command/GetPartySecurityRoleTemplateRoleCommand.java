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

package com.echothree.control.user.security.server.command;

import com.echothree.control.user.security.common.form.GetPartySecurityRoleTemplateRoleForm;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
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
public class GetPartySecurityRoleTemplateRoleCommand
        extends BaseSimpleCommand<GetPartySecurityRoleTemplateRoleForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartySecurityRoleTemplateRole.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartySecurityRoleTemplateName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SecurityRoleGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SecurityRoleName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetPartySecurityRoleTemplateRoleCommand */
    public GetPartySecurityRoleTemplateRoleCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var securityControl = Session.getModelController(SecurityControl.class);
        var result = SecurityResultFactory.getGetPartySecurityRoleTemplateRoleResult();
        var partySecurityRoleTemplateName = form.getPartySecurityRoleTemplateName();
        var partySecurityRoleTemplate = securityControl.getPartySecurityRoleTemplateByName(partySecurityRoleTemplateName);
        
        if(partySecurityRoleTemplate != null) {
            var securityRoleGroupName = form.getSecurityRoleGroupName();
            var securityRoleGroup = securityControl.getSecurityRoleGroupByName(securityRoleGroupName);
            
            if(securityRoleGroup != null) {
                var securityRoleName = form.getSecurityRoleName();
                var securityRole = securityControl.getSecurityRoleByName(securityRoleGroup, securityRoleName);
                
                if(securityRole != null) {
                    var partySecurityRoleTemplateRole = securityControl.getPartySecurityRoleTemplateRole(partySecurityRoleTemplate,
                            securityRole);
                    
                    if(partySecurityRoleTemplateRole != null) {
                        result.setPartySecurityRoleTemplateRole(securityControl.getPartySecurityRoleTemplateRoleTransfer(getUserVisit(),
                                partySecurityRoleTemplateRole));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPartySecurityRoleTemplateRole.name(), partySecurityRoleTemplateName, securityRoleGroupName,
                                securityRoleName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSecurityRoleName.name(), securityRoleGroupName, securityRoleName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSecurityRoleGroupName.name(), securityRoleGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartySecurityRoleTemplateName.name(), partySecurityRoleTemplateName);
        }
        
        return result;
    }
    
}
