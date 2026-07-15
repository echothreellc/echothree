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

import com.echothree.control.user.contact.common.spec.ContactMechanismAliasTypeUniversalSpec;
import com.echothree.model.control.contact.common.exception.UnknownContactMechanismAliasTypeNameException;
import com.echothree.model.control.contact.common.exception.UnknownDefaultContactMechanismAliasTypeException;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class ContactMechanismAliasTypeLogic
        extends BaseLogic {

    protected ContactMechanismAliasTypeLogic() {
        super();
    }

    public static ContactMechanismAliasTypeLogic getInstance() {
        return CDI.current().select(ContactMechanismAliasTypeLogic.class).get();
    }

    @Inject
    ContactControl contactControl;

    public ContactMechanismAliasType getContactMechanismAliasTypeByName(final ExecutionErrorAccumulator eea, final String contactMechanismAliasTypeName,
            final EntityPermission entityPermission) {
        var contactMechanismAliasType = contactControl.getContactMechanismAliasTypeByName(contactMechanismAliasTypeName, entityPermission);

        if(contactMechanismAliasType == null) {
            handleExecutionError(UnknownContactMechanismAliasTypeNameException.class, eea, ExecutionErrors.UnknownContactMechanismAliasTypeName.name(), contactMechanismAliasTypeName);
        }

        return contactMechanismAliasType;
    }

    public ContactMechanismAliasType getContactMechanismAliasTypeByName(final ExecutionErrorAccumulator eea, final String contactMechanismAliasTypeName) {
        return getContactMechanismAliasTypeByName(eea, contactMechanismAliasTypeName, EntityPermission.READ_ONLY);
    }

    public ContactMechanismAliasType getContactMechanismAliasTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String contactMechanismAliasTypeName) {
        return getContactMechanismAliasTypeByName(eea, contactMechanismAliasTypeName, EntityPermission.READ_WRITE);
    }

    public ContactMechanismAliasType getContactMechanismAliasTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContactMechanismAliasTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        ContactMechanismAliasType contactMechanismAliasType = null;
        var contactMechanismAliasTypeName = universalSpec.getContactMechanismAliasTypeName();
        var parameterCount = (contactMechanismAliasTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    contactMechanismAliasType = contactControl.getDefaultContactMechanismAliasType(entityPermission);

                    if(contactMechanismAliasType == null) {
                        handleExecutionError(UnknownDefaultContactMechanismAliasTypeException.class, eea, ExecutionErrors.UnknownDefaultContactMechanismAliasType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(contactMechanismAliasTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ContactMechanismAliasType.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        contactMechanismAliasType = contactControl.getContactMechanismAliasTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    contactMechanismAliasType = getContactMechanismAliasTypeByName(eea, contactMechanismAliasTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return contactMechanismAliasType;
    }

    public ContactMechanismAliasType getContactMechanismAliasTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContactMechanismAliasTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getContactMechanismAliasTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ContactMechanismAliasType getContactMechanismAliasTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ContactMechanismAliasTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getContactMechanismAliasTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

}
