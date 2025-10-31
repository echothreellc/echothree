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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.GetPartyTypesForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.factory.PartyTypeFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetPartyTypesCommand
        extends BaseMultipleEntitiesCommand<PartyType, GetPartyTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyType.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of();
    }

    /** Creates a new instance of GetPartyTypesCommand */
    public GetPartyTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected Collection<PartyType> getEntities() {
        var partyControl = Session.getModelController(PartyControl.class);

        return partyControl.getPartyTypes();
    }

    @Override
    protected BaseResult getResult(Collection<PartyType> entities) {
        var result = PartyResultFactory.getGetPartyTypesResult();

        if(entities != null) {
            var partyControl = Session.getModelController(PartyControl.class);

            if(session.hasLimit(PartyTypeFactory.class)) {
                result.setPartyTypeCount(partyControl.countPartyTypes());
            }

            result.setPartyTypes(partyControl.getPartyTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
