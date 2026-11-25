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

package com.echothree.model.control.letter.server.logic;

import com.echothree.control.user.letter.common.spec.LetterSourceUniversalSpec;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.common.exception.InvalidContactMechanismTypeException;
import com.echothree.model.control.contact.common.exception.UnknownContactMechanismAliasException;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contact.server.logic.ContactMechanismAliasTypeLogic;
import com.echothree.model.control.contact.server.logic.ContactMechanismLogic;
import com.echothree.model.control.contact.server.logic.PartyContactMechanismLogic;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.letter.common.exception.UnknownDefaultLetterSourceException;
import com.echothree.model.control.letter.common.exception.UnknownLetterSourceNameException;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.data.contact.server.entity.ContactMechanism;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.letter.server.entity.LetterSource;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LetterSourceLogic
        extends BaseLogic {

    @Inject
    ContactControl contactControl;

    @Inject
    LetterControl letterControl;

    @Inject
    ContactMechanismAliasTypeLogic contactMechanismAliasTypeLogic;

    @Inject
    ContactMechanismLogic contactMechanismLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    PartyContactMechanismLogic partyContactMechanismLogic;

    protected LetterSourceLogic() {
        super();
    }

    public LetterSource getLetterSourceByName(final ExecutionErrorAccumulator eea,
            final String letterSourceName, final EntityPermission entityPermission) {
        var letterSource = letterControl.getLetterSourceByName(letterSourceName, entityPermission);

        if(letterSource == null) {
            handleExecutionError(UnknownLetterSourceNameException.class, eea,
                    ExecutionErrors.UnknownLetterSourceName.name(), letterSourceName);
        }

        return letterSource;
    }

    public LetterSource getLetterSourceByName(final ExecutionErrorAccumulator eea,
            final String letterSourceName) {
        return getLetterSourceByName(eea, letterSourceName, EntityPermission.READ_ONLY);
    }

    public LetterSource getLetterSourceByNameForUpdate(final ExecutionErrorAccumulator eea,
            final String letterSourceName) {
        return getLetterSourceByName(eea, letterSourceName, EntityPermission.READ_WRITE);
    }

    public LetterSource getLetterSourceByUniversalSpec(final ExecutionErrorAccumulator eea, final LetterSourceUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        LetterSource letterSource = null;
        var letterSourceName = universalSpec.getLetterSourceName();
        var parameterCount = (letterSourceName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    letterSource = letterControl.getDefaultLetterSource(entityPermission);

                    if(letterSource == null) {
                        handleExecutionError(UnknownDefaultLetterSourceException.class, eea, ExecutionErrors.UnknownDefaultLetterSource.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(letterSourceName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.LetterSource.name());

                    if(!hasExecutionErrors(eea)) {
                        letterSource = letterControl.getLetterSourceByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    letterSource = getLetterSourceByName(eea, letterSourceName, entityPermission);
                }
            }
            default -> handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return letterSource;
    }

    public LetterSource getLetterSourceByUniversalSpec(final ExecutionErrorAccumulator eea, final LetterSourceUniversalSpec universalSpec,
            final boolean allowDefault) {
        return getLetterSourceByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public LetterSource getLetterSourceByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final LetterSourceUniversalSpec universalSpec,
            final boolean allowDefault) {
        return getLetterSourceByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public PartyContactMechanism getLetterSourcePartyContactMechanism(final ExecutionErrorAccumulator eea, final Party companyParty,
            final String contactMechanismName, final String contactMechanismAliasTypeName, final String alias,
            final ContactMechanismTypes expectedContactMechanismType) {
        ContactMechanism contactMechanism = null;
        PartyContactMechanism partyContactMechanism = null;

        if(contactMechanismName != null && contactMechanismAliasTypeName == null && alias == null) {
            contactMechanism = contactMechanismLogic.getContactMechanismByName(eea, contactMechanismName);
        } else if(contactMechanismName == null && contactMechanismAliasTypeName != null && alias != null) {
            var contactMechanismAliasType = contactMechanismAliasTypeLogic.getContactMechanismAliasTypeByName(eea, contactMechanismAliasTypeName);

            if(!hasExecutionErrors(eea)) {
                var contactMechanismAlias = contactControl.getContactMechanismAliasByAlias(contactMechanismAliasType, alias);

                if(contactMechanismAlias == null) {
                    handleExecutionError(UnknownContactMechanismAliasException.class, eea, ExecutionErrors.UnknownContactMechanismAlias.name(),
                            contactMechanismAliasTypeName, alias);
                } else {
                    contactMechanism = contactMechanismAlias.getContactMechanism();
                }
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        if(!hasExecutionErrors(eea)) {
            var contactMechanismTypeName = contactMechanism.getLastDetail().getContactMechanismType().getContactMechanismTypeName();

            if(expectedContactMechanismType.name().equals(contactMechanismTypeName)) {
                partyContactMechanism = partyContactMechanismLogic.getPartyContactMechanism(eea, companyParty, contactMechanism);
            } else {
                handleExecutionError(InvalidContactMechanismTypeException.class, eea, ExecutionErrors.InvalidContactMechanismType.name(),
                        contactMechanismTypeName);
            }
        }

        return partyContactMechanism;
    }

}
