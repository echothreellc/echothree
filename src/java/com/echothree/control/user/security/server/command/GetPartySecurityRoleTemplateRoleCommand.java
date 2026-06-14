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
import com.echothree.model.control.security.server.logic.PartySecurityRoleTemplateLogic;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateRole;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartySecurityRoleTemplateRoleCommand
        extends BaseSingleEntityCommand<PartySecurityRoleTemplateRole, GetPartySecurityRoleTemplateRoleForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartySecurityRoleTemplateRole.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartySecurityRoleTemplateName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SecurityRoleGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SecurityRoleName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    SecurityControl securityControl;

    @Inject
    PartySecurityRoleTemplateLogic partySecurityRoleTemplateLogic;

    /** Creates a new instance of GetPartySecurityRoleTemplateRoleCommand */
    public GetPartySecurityRoleTemplateRoleCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected PartySecurityRoleTemplateRole getEntity() {
        var partySecurityRoleTemplate = partySecurityRoleTemplateLogic.getPartySecurityRoleTemplateByName(this, form.getPartySecurityRoleTemplateName());
        PartySecurityRoleTemplateRole partySecurityRoleTemplateRole = null;

        if(!hasExecutionErrors()) {
            var securityRole = securityRoleLogic.getSecurityRoleByName(this, form.getSecurityRoleGroupName(), form.getSecurityRoleName());

            if(!hasExecutionErrors()) {
                partySecurityRoleTemplateRole = securityControl.getPartySecurityRoleTemplateRole(partySecurityRoleTemplate, securityRole);

                if(partySecurityRoleTemplateRole == null) {
                    addExecutionError(com.echothree.util.common.message.ExecutionErrors.UnknownPartySecurityRoleTemplateRole.name(),
                            partySecurityRoleTemplate.getLastDetail().getPartySecurityRoleTemplateName(),
                            securityRole.getLastDetail().getSecurityRoleGroup().getLastDetail().getSecurityRoleGroupName(),
                            securityRole.getLastDetail().getSecurityRoleName());
                }
            }
        }

        return partySecurityRoleTemplateRole;
    }

    @Override
    protected BaseResult getResult(PartySecurityRoleTemplateRole partySecurityRoleTemplateRole) {
        var result = SecurityResultFactory.getGetPartySecurityRoleTemplateRoleResult();

        if(partySecurityRoleTemplateRole != null) {
            result.setPartySecurityRoleTemplateRole(securityControl.getPartySecurityRoleTemplateRoleTransfer(getUserVisit(), partySecurityRoleTemplateRole));
        }

        return result;
    }
    
}
