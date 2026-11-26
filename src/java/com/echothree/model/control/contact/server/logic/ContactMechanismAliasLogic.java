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

import com.echothree.model.control.contact.common.exception.UnknownContactMechanismAliasException;
import com.echothree.model.control.contact.common.exception.UnknownContactMechanismAliasTypeNameException;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.data.contact.server.entity.ContactMechanismAlias;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasType;
import com.echothree.util.common.exception.BaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ContactMechanismAliasLogic
        extends BaseLogic {

    @Inject
    ContactControl contactControl;

    @Inject
    ContactMechanismAliasTypeLogic contactMechanismAliasTypeLogic;

    protected ContactMechanismAliasLogic() {
        super();
    }

    public ContactMechanismAlias getContactMechanismAliasByAlias(final Class<? extends BaseException> unknownException,
            final ExecutionErrors unknownExecutionError, final ExecutionErrorAccumulator eea, final ContactMechanismAliasType contactMechanismAliasType,
            final String alias, final EntityPermission entityPermission) {
        var contactMechanismAlias = contactControl.getContactMechanismAliasByAlias(contactMechanismAliasType, alias, entityPermission);

        if(contactMechanismAlias == null) {
            handleExecutionError(unknownException, eea, unknownExecutionError.name(),
                    contactMechanismAliasType.getLastDetail().getContactMechanismAliasTypeName(), alias);
        }

        return contactMechanismAlias;
    }

    public ContactMechanismAlias getContactMechanismAliasByAlias(final ExecutionErrorAccumulator eea,
            final ContactMechanismAliasType contactMechanismAliasType, final String alias, final EntityPermission entityPermission) {
        return getContactMechanismAliasByAlias(UnknownContactMechanismAliasException.class, ExecutionErrors.UnknownContactMechanismAlias, eea,
                contactMechanismAliasType, alias, entityPermission);
    }

    public ContactMechanismAlias getContactMechanismAliasByAlias(final Class<? extends BaseException> unknownAliasTypeException,
            final ExecutionErrors unknownAliasTypeExecutionError, final Class<? extends BaseException> unknownAliasException,
            final ExecutionErrors unknownAliasExecutionError, final ExecutionErrorAccumulator eea, final String contactMechanismAliasTypeName,
            final String alias, final EntityPermission entityPermission) {
        var contactMechanismAliasType = contactMechanismAliasTypeLogic.getContactMechanismAliasTypeByName(
                unknownAliasTypeException, unknownAliasTypeExecutionError, eea, contactMechanismAliasTypeName);
        ContactMechanismAlias contactMechanismAlias = null;

        if(!hasExecutionErrors(eea)) {
            contactMechanismAlias = getContactMechanismAliasByAlias(unknownAliasException, unknownAliasExecutionError, eea,
                    contactMechanismAliasType, alias, entityPermission);
        }

        return contactMechanismAlias;
    }

    public ContactMechanismAlias getContactMechanismAliasByAlias(final ExecutionErrorAccumulator eea,
            final String contactMechanismAliasTypeName, final String alias, final EntityPermission entityPermission) {
        return getContactMechanismAliasByAlias(UnknownContactMechanismAliasTypeNameException.class, ExecutionErrors.UnknownContactMechanismAliasTypeName,
                UnknownContactMechanismAliasException.class, ExecutionErrors.UnknownContactMechanismAlias, eea,
                contactMechanismAliasTypeName, alias, entityPermission);
    }

    public ContactMechanismAlias getContactMechanismAliasByAlias(final ExecutionErrorAccumulator eea,
            final String contactMechanismAliasTypeName, final String alias) {
        return getContactMechanismAliasByAlias(eea, contactMechanismAliasTypeName, alias, EntityPermission.READ_ONLY);
    }

    public ContactMechanismAlias getContactMechanismAliasByAliasForUpdate(final ExecutionErrorAccumulator eea,
            final String contactMechanismAliasTypeName, final String alias) {
        return getContactMechanismAliasByAlias(eea, contactMechanismAliasTypeName, alias, EntityPermission.READ_WRITE);
    }

}
