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

package com.echothree.model.control.contact.server.logic;

import com.echothree.model.control.contact.common.exception.CannotDeleteContactMechanismInUseException;
import com.echothree.model.control.contact.common.exception.UnknownContactMechanismNameException;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.payment.server.control.PartyPaymentMethodControl;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ContactMechanismLogic
    extends BaseLogic {

    protected ContactMechanismLogic() {
        super();
    }

    public static ContactMechanismLogic getInstance() {
        return CDI.current().select(ContactMechanismLogic.class).get();
    }
    
    public ContactMechanism getContactMechanismByName(final ExecutionErrorAccumulator eea, final String contactMechanismName,
            final EntityPermission entityPermission) {
        var contactControl = Session.getModelController(ContactControl.class);
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

    public void deleteContactMechanism(final ExecutionErrorAccumulator eea, final ContactMechanism contactMechanism,
            final PartyPK deletedBy) {
        var contactControl = Session.getModelController(ContactControl.class);
        var partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);
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

        if(!eea.hasExecutionErrors()) {
            contactControl.deleteContactMechanism(contactMechanism, deletedBy);
        }
    }

    public void deleteContactMechanism(final ExecutionErrorAccumulator eea, final String contactMechanismName,
            final PartyPK deletedBy) {
        var contactMechanism = getContactMechanismByNameForUpdate(eea, contactMechanismName);

        if(!eea.hasExecutionErrors()) {
            deleteContactMechanism(eea, contactMechanism, deletedBy);
        }
    }

}
