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
import com.echothree.model.control.party.common.exception.CannotDeletePersonalTitleInUseException;
import com.echothree.model.control.party.common.exception.UnknownPersonalTitleIdException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.payment.server.control.PartyPaymentMethodControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PersonalTitleLogic
        extends BaseLogic {

    protected PersonalTitleLogic() {
        super();
    }

    public static PersonalTitleLogic getInstance() {
        return CDI.current().select(PersonalTitleLogic.class).get();
    }

    private PersonalTitle getPersonalTitleById(final ExecutionErrorAccumulator eea, final String personalTitleId,
            final EntityPermission entityPermission) {
        var partyControl = Session.getModelController(PartyControl.class);
        var personalTitle = partyControl.convertPersonalTitleIdToEntity(personalTitleId, entityPermission);

        if(personalTitle == null) {
            handleExecutionError(UnknownPersonalTitleIdException.class, eea, ExecutionErrors.UnknownPersonalTitleId.name(), personalTitleId);
        }

        return personalTitle;
    }

    public PersonalTitle getPersonalTitleById(final ExecutionErrorAccumulator eea, final String personalTitleId) {
        return getPersonalTitleById(eea, personalTitleId, EntityPermission.READ_ONLY);
    }

    public PersonalTitle getPersonalTitleByIdForUpdate(final ExecutionErrorAccumulator eea, final String personalTitleId) {
        return getPersonalTitleById(eea, personalTitleId, EntityPermission.READ_WRITE);
    }

    public void deletePersonalTitle(final ExecutionErrorAccumulator eea, final PersonalTitle personalTitle, final PartyPK deletedBy) {
        var contactControl = Session.getModelController(ContactControl.class);
        var partyControl = Session.getModelController(PartyControl.class);
        var partyPaymentMethodControl = Session.getModelController(PartyPaymentMethodControl.class);

        // Check if the PersonalTitle is in-use by any PartyPaymentMethodCreditCard, ContactPostalAddress or Person.
        if(partyPaymentMethodControl.countPartyPaymentMethodCreditCardsByPersonalTitle(personalTitle) != 0
                || contactControl.countContactPostalAddressesByPersonalTitle(personalTitle) != 0
                || partyControl.countPeopleByPersonalTitle(personalTitle) != 0) {
            handleExecutionError(CannotDeletePersonalTitleInUseException.class, eea, ExecutionErrors.CannotDeletePersonalTitleInUse.name(),
                    personalTitle.getLastDetail().getPersonalTitlePK().getEntityId().toString());
        }

        if(!eea.hasExecutionErrors()) {
            partyControl.deletePersonalTitle(personalTitle, deletedBy);
        }
    }

    public void deletePersonalTitle(final ExecutionErrorAccumulator eea, final String personalTitleId, final PartyPK deletedBy) {
        var personalTitle = getPersonalTitleByIdForUpdate(eea, personalTitleId);

        if(!eea.hasExecutionErrors()) {
            deletePersonalTitle(eea, personalTitle, deletedBy);
        }
    }

}
