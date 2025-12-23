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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.GetDivisionsForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.CompanyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.model.data.party.server.factory.PartyDivisionFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetDivisionsCommand
        extends BaseMultipleEntitiesCommand<PartyDivision, GetDivisionsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Division.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetDivisionsCommand */
    public GetDivisionsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }


    PartyCompany partyCompany;

    @Override
    protected Collection<PartyDivision> getEntities() {
        var companyName = form.getCompanyName();
        Collection<PartyDivision> partyDivisions = null;

        partyCompany = CompanyLogic.getInstance().getPartyCompanyByName(this, companyName, null, null, true);

        if(!hasExecutionErrors()) {
            var partyControl = Session.getModelController(PartyControl.class);

            partyDivisions = partyControl.getDivisionsByCompany(partyCompany.getParty());
        }

        return partyDivisions;
    }

    @Override
    protected BaseResult getResult(Collection<PartyDivision> entities) {
        var result = PartyResultFactory.getGetDivisionsResult();

        if(entities != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var userVisit = getUserVisit();

            if(session.hasLimit(PartyDivisionFactory.class)) {
                result.setDivisionCount(partyControl.countPartyDivisions(partyCompany.getParty()));
            }

            result.setCompany(partyControl.getCompanyTransfer(userVisit, partyCompany));
            result.setDivisions(partyControl.getDivisionTransfers(userVisit, entities));
        }

        return result;
    }

}
