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

package com.echothree.model.control.party.server.logic;

import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.party.common.exception.CannotDeleteNameSuffixInUseException;
import com.echothree.model.control.party.common.exception.UnknownNameSuffixIdException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.payment.server.control.PartyPaymentMethodControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class NameSuffixLogic
        extends BaseLogic {

    protected NameSuffixLogic() {
        super();
    }

    public static NameSuffixLogic getInstance() {
        return CDI.current().select(NameSuffixLogic.class).get();
    }

    private NameSuffix getNameSuffixById(final ExecutionErrorAccumulator eea, final String nameSuffixId,
            final EntityPermission entityPermission) {
        var partyControl = Session.getModelController(PartyControl.class);
        var nameSuffix = partyControl.convertNameSuffixIdToEntity(nameSuffixId, entityPermission);

        if(nameSuffix == null) {
            handleExecutionError(UnknownNameSuffixIdException.class, eea, ExecutionErrors.UnknownNameSuffixId.name(), nameSuffixId);
        }

        return nameSuffix;
    }

    public NameSuffix getNameSuffixById(final ExecutionErrorAccumulator eea, final String nameSuffixId) {
        return getNameSuffixById(eea, nameSuffixId, EntityPermission.READ_ONLY);
    }

    public NameSuffix getNameSuffixByIdForUpdate(final ExecutionErrorAccumulator eea, final String nameSuffixId) {
        return getNameSuffixById(eea, nameSuffixId, EntityPermission.READ_WRITE);
    }

    public void deleteNameSuffix(final ExecutionErrorAccumulator eea, final NameSuffix nameSuffix, final PartyPK deletedBy) {
        var contactControl = Session.getModelController(ContactControl.class);
        var partyControl = Session.getModelController(PartyControl.class);
        var partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);

        // Check if the NameSuffix is in-use by any PartyPaymentMethodCreditCard, ContactPostalAddress or Person.
        if(partyPaymentMethodControl.countPartyPaymentMethodCreditCardsByNameSuffix(nameSuffix) != 0
                || contactControl.countContactPostalAddressesByNameSuffix(nameSuffix) != 0
                || partyControl.countPeopleByNameSuffix(nameSuffix) != 0) {
            handleExecutionError(CannotDeleteNameSuffixInUseException.class, eea, ExecutionErrors.CannotDeleteNameSuffixInUse.name(),
                    nameSuffix.getLastDetail().getNameSuffixPK().getEntityId().toString());
        }

        if(!eea.hasExecutionErrors()) {
            partyControl.deleteNameSuffix(nameSuffix, deletedBy);
        }
    }

    public void deleteNameSuffix(final ExecutionErrorAccumulator eea, final String nameSuffixId, final PartyPK deletedBy) {
        var nameSuffix = getNameSuffixByIdForUpdate(eea, nameSuffixId);

        if(!eea.hasExecutionErrors()) {
            deleteNameSuffix(eea, nameSuffix, deletedBy);
        }
    }

}
