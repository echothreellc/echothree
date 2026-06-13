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

import com.echothree.control.user.security.common.form.GetPartySecurityRoleTemplatesForm;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplate;
import com.echothree.model.data.security.server.factory.PartySecurityRoleTemplateFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartySecurityRoleTemplatesCommand
        extends BasePaginatedMultipleEntitiesCommand<PartySecurityRoleTemplate, GetPartySecurityRoleTemplatesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartySecurityRoleTemplate.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    @Inject
    SecurityControl securityControl;
    
    /** Creates a new instance of GetPartySecurityRoleTemplatesCommand */
    public GetPartySecurityRoleTemplatesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected void handleForm() {
        // No form fields to handle
    }

    @Override
    protected Long getTotalEntities() {
        return securityControl.countPartySecurityRoleTemplates();
    }

    @Override
    protected Collection<PartySecurityRoleTemplate> getEntities() {
        return securityControl.getPartySecurityRoleTemplates();
    }

    @Override
    protected BaseResult getResult(Collection<PartySecurityRoleTemplate> entities) {
        var result = SecurityResultFactory.getGetPartySecurityRoleTemplatesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(session.hasLimit(PartySecurityRoleTemplateFactory.class)) {
                result.setPartySecurityRoleTemplateCount(getTotalEntities());
            }

            result.setPartySecurityRoleTemplates(securityControl.getPartySecurityRoleTemplateTransfers(userVisit, entities));
        }

        return result;
    }
    
}
