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

package com.echothree.control.user.security.server.command;

import com.echothree.control.user.security.common.form.CreatePartySecurityRoleTemplateRoleForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.security.server.logic.PartySecurityRoleTemplateLogic;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplate;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateRole;
import com.echothree.model.data.security.server.entity.SecurityRole;
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

public class CreatePartySecurityRoleTemplateRoleCommand
        extends BaseSimpleCommand<CreatePartySecurityRoleTemplateRoleForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartySecurityRoleTemplateRole.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartySecurityRoleTemplateName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SecurityRoleGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SecurityRoleName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePartySecurityRoleTemplateRoleCommand */
    public CreatePartySecurityRoleTemplateRoleCommand(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateRoleForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var securityControl = Session.getModelController(SecurityControl.class);
        String partySecurityRoleTemplateName = form.getPartySecurityRoleTemplateName();
        PartySecurityRoleTemplate partySecurityRoleTemplate = securityControl.getPartySecurityRoleTemplateByName(partySecurityRoleTemplateName);
        
        if(partySecurityRoleTemplate != null) {
            String securityRoleGroupName = form.getSecurityRoleGroupName();
            SecurityRoleGroup securityRoleGroup = securityControl.getSecurityRoleGroupByName(securityRoleGroupName);
            
            if(securityRoleGroup != null) {
                String securityRoleName = form.getSecurityRoleName();

                if(securityRoleName == null) {
                    List<SecurityRole> securityRoles = securityControl.getSecurityRoles(securityRoleGroup);

                    // Pass 1: Check for duplicates.
                    for(SecurityRole securityRole: securityRoles) {
                        PartySecurityRoleTemplateRole partySecurityRoleTemplateRole = securityControl.getPartySecurityRoleTemplateRole(partySecurityRoleTemplate,
                                securityRole);

                        if(partySecurityRoleTemplateRole != null) {
                            addExecutionError(ExecutionErrors.DuplicatePartySecurityRoleTemplateRole.name(), partySecurityRoleTemplateName, securityRoleGroupName,
                                    securityRole.getLastDetail().getSecurityRoleName());
                            break;
                        }
                    }

                    // Pass 2: Add Security Roles if there were no errors.
                    if(!hasExecutionErrors()) {
                        securityRoles.stream().forEach((securityRole) -> {
                            PartySecurityRoleTemplateLogic.getInstance().createPartySecurityRoleTemplateRole(partySecurityRoleTemplate, securityRole, getPartyPK());
                        });
                    }
                } else {
                    SecurityRole securityRole = securityControl.getSecurityRoleByName(securityRoleGroup, securityRoleName);

                    if(securityRole != null) {
                        PartySecurityRoleTemplateRole partySecurityRoleTemplateRole = securityControl.getPartySecurityRoleTemplateRole(partySecurityRoleTemplate,
                                securityRole);

                        if(partySecurityRoleTemplateRole == null) {
                            PartySecurityRoleTemplateLogic.getInstance().createPartySecurityRoleTemplateRole(partySecurityRoleTemplate, securityRole, getPartyPK());
                        } else {
                            addExecutionError(ExecutionErrors.DuplicatePartySecurityRoleTemplateRole.name(), partySecurityRoleTemplateName, securityRoleGroupName,
                                    securityRoleName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSecurityRoleName.name(), securityRoleGroupName, securityRoleName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSecurityRoleGroupName.name(), securityRoleGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartySecurityRoleTemplateName.name(), partySecurityRoleTemplateName);
        }
        
        return null;
    }
    
}
