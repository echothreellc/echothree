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

import com.echothree.control.user.contact.common.spec.PartyContactMechanismUniversalSpec;
import com.echothree.model.control.contact.common.exception.UnknownPartyContactMechanismException;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PartyContactMechanismLogic
    extends BaseLogic {

    protected PartyContactMechanismLogic() {
        super();
    }

    @Inject
    ContactControl contactControl;

    @Inject
    ContactMechanismLogic contactMechanismLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    PartyLogic partyLogic;

    public PartyContactMechanism getPartyContactMechanism(final ExecutionErrorAccumulator eea, final Party party,
            final ContactMechanism contactMechanism, final EntityPermission entityPermission) {
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

    public PartyContactMechanism getPartyContactMechanismByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PartyContactMechanismUniversalSpec universalSpec, final EntityPermission entityPermission) {
        PartyContactMechanism partyContactMechanism = null;
        var partyName = universalSpec.getPartyName();
        var contactMechanismName = universalSpec.getContactMechanismName();
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        var parameterCount = (partyName == null ? 0 : 1) + (contactMechanismName == null ? 0 : 1) + possibleEntitySpecs;

        switch(parameterCount) {
            case 1 -> {
                if(possibleEntitySpecs == 1) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.PartyContactMechanism.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        partyContactMechanism = contactControl.getPartyContactMechanismByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 2 -> {
                if(partyName != null && contactMechanismName != null) {
                    var party = partyLogic.getPartyByName(eea, partyName);

                    if(eea == null || !eea.hasExecutionErrors()) {
                        var contactMechanism = contactMechanismLogic.getContactMechanismByName(eea, contactMechanismName);

                        if(eea == null || !eea.hasExecutionErrors()) {
                            partyContactMechanism = getPartyContactMechanism(eea, party, contactMechanism, entityPermission);
                        }
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return partyContactMechanism;
    }

    public PartyContactMechanism getPartyContactMechanismByUniversalSpec(final ExecutionErrorAccumulator eea,
            final PartyContactMechanismUniversalSpec universalSpec) {
        return getPartyContactMechanismByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public PartyContactMechanism getPartyContactMechanismByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final PartyContactMechanismUniversalSpec universalSpec) {
        return getPartyContactMechanismByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
