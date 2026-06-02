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

package com.echothree.control.user.carrier.server.command;

import com.echothree.control.user.carrier.common.form.GetPartyCarriersForm;
import com.echothree.control.user.carrier.common.result.CarrierResultFactory;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.carrier.server.entity.PartyCarrier;
import com.echothree.model.data.carrier.server.factory.PartyCarrierFactory;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyCarriersCommand
        extends BasePaginatedMultipleEntitiesCommand<PartyCarrier, GetPartyCarriersForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyCarrier.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.HOST_NAME, true, null, null)
        );
    }
    
    @Inject
    CarrierControl carrierControl;

    @Inject
    PartyControl partyControl;

    @Inject
    PartyLogic partyLogic;

    /** Creates a new instance of GetPartyCarriersCommand */
    public GetPartyCarriersCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    Party party;

    @Override
    protected void handleForm() {
        party = partyLogic.getPartyByName(this, form.getPartyName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : carrierControl.countPartyCarriersByParty(party);
    }

    @Override
    protected Collection<PartyCarrier> getEntities() {
        return hasExecutionErrors() ? null : carrierControl.getPartyCarriersByParty(party);
    }

    @Override
    protected BaseResult getResult(Collection<PartyCarrier> entities) {
        var result = CarrierResultFactory.getGetPartyCarriersResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setParty(partyControl.getPartyTransfer(userVisit, party));

            if(session.hasLimit(PartyCarrierFactory.class)) {
                result.setPartyCarrierCount(getTotalEntities());
            }

            result.setPartyCarriers(carrierControl.getPartyCarrierTransfersByParty(userVisit, entities));
        }

        return result;
    }
    
}
