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

import com.echothree.control.user.contact.common.spec.ContactMechanismUniversalSpec;
import com.echothree.model.control.contact.common.exception.CannotDeleteContactMechanismInUseException;
import com.echothree.model.control.contact.common.exception.UnknownContactMechanismNameException;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.payment.server.control.PartyPaymentMethodControl;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class ContactMechanismLogic
    extends BaseLogic {

    @Inject
    ContactControl contactControl;

    @Inject
    PartyPaymentMethodControl partyPaymentMethodControl;

    protected ContactMechanismLogic() {
        super();
    }

    public static ContactMechanismLogic getInstance() {
        return CDI.current().select(ContactMechanismLogic.class).get();
    }

    public ContactMechanism getContactMechanismByName(final ExecutionErrorAccumulator eea, final String contactMechanismName,
            final EntityPermission entityPermission) {
        var contactMechanism = contactControl.getContactMechanismByName(contactMechanismName, entityPermission);

        if(contactMechanism == null) {
            handleExecutionError(UnknownContactMechanismNameException.class, eea, ExecutionErrors.UnknownContactMechanismName.name(), contactMechanismName);
        }

        return contactMechanism;
    }

    public ContactMechanism getContactMechanismByName(final ExecutionErrorAccumulator eea, final String contactMechanismName) {
        return getContactMechanismByName(eea, contactMechanismName, EntityPermission.READ_ONLY);
    }

    public ContactMechanism getContactMechanismByNameForUpdate(final ExecutionErrorAccumulator eea, final String contactMechanismName) {
        return getContactMechanismByName(eea, contactMechanismName, EntityPermission.READ_WRITE);
    }

    public ContactMechanism getContactMechanismByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContactMechanismUniversalSpec universalSpec, final EntityPermission entityPermission) {
        ContactMechanism contactMechanism = null;
        var contactMechanismName = universalSpec.getContactMechanismName();
        var parameterCount = (contactMechanismName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(contactMechanismName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ContactMechanism.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        contactMechanism = contactControl.getContactMechanismByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    contactMechanism = getContactMechanismByName(eea, contactMechanismName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return contactMechanism;
    }

    public ContactMechanism getContactMechanismByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContactMechanismUniversalSpec universalSpec) {
        return getContactMechanismByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public ContactMechanism getContactMechanismByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ContactMechanismUniversalSpec universalSpec) {
        return getContactMechanismByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public void deleteContactMechanism(final ExecutionErrorAccumulator eea, final ContactMechanism contactMechanism,
            final PartyPK deletedBy) {
        var cannotDeleteContactMechanismInUse = false;

        // Check if the ContactMechanism is in-use by any PartyPaymentMethodCreditCard.
        var partyContactMechanisms = contactControl.getPartyContactMechanismsByContactMechanism(contactMechanism);
        for(var partyContactMechanism : partyContactMechanisms) {
            if(partyPaymentMethodControl.countPartyPaymentMethodCreditCardsByIssuerPartyContactMechanism(partyContactMechanism) != 0
                    || partyPaymentMethodControl.countPartyPaymentMethodCreditCardsByBillingPartyContactMechanism(partyContactMechanism) != 0) {
                cannotDeleteContactMechanismInUse = true;
                break;
            }
        }

        if(cannotDeleteContactMechanismInUse) {
            handleExecutionError(CannotDeleteContactMechanismInUseException.class, eea, ExecutionErrors.CannotDeleteContactMechanismInUse.name(),
                    contactMechanism.getLastDetail().getContactMechanismName());
        }

        if(eea == null || !eea.hasExecutionErrors()) {
            contactControl.deleteContactMechanism(contactMechanism, deletedBy);
        }
    }

    public void deleteContactMechanism(final ExecutionErrorAccumulator eea, final String contactMechanismName,
            final PartyPK deletedBy) {
        var contactMechanism = getContactMechanismByNameForUpdate(eea, contactMechanismName);

        if(eea == null || !eea.hasExecutionErrors()) {
            deleteContactMechanism(eea, contactMechanism, deletedBy);
        }
    }

}
