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

package com.echothree.model.control.contact.server.logic;

import com.echothree.model.control.contact.common.exception.UnknownPartyContactMechanismException;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PartyContactMechanismLogic
    extends BaseLogic {

    protected PartyContactMechanismLogic() {
        super();
    }

    public static PartyContactMechanismLogic getInstance() {
        return CDI.current().select(PartyContactMechanismLogic.class).get();
    }

    public PartyContactMechanism getPartyContactMechanism(final ExecutionErrorAccumulator eea, final Party party,
            final ContactMechanism contactMechanism, final EntityPermission entityPermission) {
        var contactControl = Session.getModelController(ContactControl.class);
        var partyContactMechanism = contactControl.getPartyContactMechanism(party, contactMechanism, entityPermission);

        if(partyContactMechanism == null) {
            handleExecutionError(UnknownPartyContactMechanismException.class, eea, ExecutionErrors.UnknownPartyContactMechanism.name(),
                    party.getLastDetail().getPartyName(), contactMechanism.getLastDetail().getContactMechanismName());
        }

        return partyContactMechanism;
    }
    
    public PartyContactMechanism getPartyContactMechanism(final ExecutionErrorAccumulator eea, final Party party,
            final ContactMechanism contactMechanism) {
        return getPartyContactMechanism(eea, party, contactMechanism, EntityPermission.READ_ONLY);
    }

    public PartyContactMechanism getPartyContactMechanismForUpdate(final ExecutionErrorAccumulator eea, final Party party,
            final ContactMechanism contactMechanism) {
        return getPartyContactMechanism(eea, party, contactMechanism, EntityPermission.READ_WRITE);
    }

}
