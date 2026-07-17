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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.form.GetPartyContactMechanismsForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contact.server.factory.PartyContactMechanismFactory;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyContactMechanismsCommand
        extends BasePaginatedMultipleEntitiesCommand<PartyContactMechanism, GetPartyContactMechanismsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    ContactControl contactControl;
    @Inject
    PartyControl partyControl;
    @Inject
    PartyLogic partyLogic;

    private Party party;

    /** Creates a new instance of GetPartyContactMechanismsCommand */
    public GetPartyContactMechanismsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        party = partyLogic.getPartyByName(this, form.getPartyName());
    }

    @Override
    protected Long getTotalEntities() {
        return party == null ? null : contactControl.countPartyContactMechanismsByParty(party);
    }

    @Override
    protected Collection<PartyContactMechanism> getEntities() {
        return party == null ? null : contactControl.getPartyContactMechanismsByParty(party);
    }

    @Override
    protected BaseResult getResult(Collection<PartyContactMechanism> entities) {
        var result = ContactResultFactory.getGetPartyContactMechanismsResult();

        if(entities != null) {
            if(session.hasLimit(PartyContactMechanismFactory.class)) {
                result.setPartyContactMechanismCount(getTotalEntities());
            }

            result.setParty(partyControl.getPartyTransfer(getUserVisit(), party));
            result.setPartyContactMechanisms(contactControl.getPartyContactMechanismTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
