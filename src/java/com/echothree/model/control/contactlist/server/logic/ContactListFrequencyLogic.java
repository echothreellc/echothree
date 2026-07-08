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

package com.echothree.model.control.contactlist.server.logic;

import com.echothree.control.user.contactlist.common.spec.ContactListFrequencyUniversalSpec;
import com.echothree.model.control.contactlist.common.exception.UnknownContactListFrequencyNameException;
import com.echothree.model.control.contactlist.common.exception.UnknownDefaultContactListFrequencyException;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequency;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class ContactListFrequencyLogic
    extends BaseLogic {

    protected ContactListFrequencyLogic() {
        super();
    }

    public static ContactListFrequencyLogic getInstance() {
        return CDI.current().select(ContactListFrequencyLogic.class).get();
    }
    
    @Inject
    ContactListControl contactListControl;

    public ContactListFrequency getContactListFrequencyByName(final ExecutionErrorAccumulator eea, final String contactListFrequencyName,
            final EntityPermission entityPermission) {
        var contactListFrequency = contactListControl.getContactListFrequencyByName(contactListFrequencyName, entityPermission);

        if(contactListFrequency == null) {
            handleExecutionError(UnknownContactListFrequencyNameException.class, eea, ExecutionErrors.UnknownContactListFrequencyName.name(),
                    contactListFrequencyName);
        }

        return contactListFrequency;
    }

    public ContactListFrequency getContactListFrequencyByName(final ExecutionErrorAccumulator eea, final String contactListFrequencyName) {
        return getContactListFrequencyByName(eea, contactListFrequencyName, EntityPermission.READ_ONLY);
    }

    public ContactListFrequency getContactListFrequencyByNameForUpdate(final ExecutionErrorAccumulator eea, final String contactListFrequencyName) {
        return getContactListFrequencyByName(eea, contactListFrequencyName, EntityPermission.READ_WRITE);
    }

    public ContactListFrequency getContactListFrequencyByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContactListFrequencyUniversalSpec universalSpec, final boolean allowDefault, final EntityPermission entityPermission) {
        ContactListFrequency contactListFrequency = null;
        var contactListFrequencyName = universalSpec.getContactListFrequencyName();
        var parameterCount = (contactListFrequencyName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    contactListFrequency = contactListControl.getDefaultContactListFrequency(entityPermission);

                    if(contactListFrequency == null) {
                        handleExecutionError(UnknownDefaultContactListFrequencyException.class, eea, ExecutionErrors.UnknownDefaultContactListFrequency.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(contactListFrequencyName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ContactListFrequency.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        contactListFrequency = contactListControl.getContactListFrequencyByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    contactListFrequency = getContactListFrequencyByName(eea, contactListFrequencyName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return contactListFrequency;
    }

    public ContactListFrequency getContactListFrequencyByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContactListFrequencyUniversalSpec universalSpec, final boolean allowDefault) {
        return getContactListFrequencyByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ContactListFrequency getContactListFrequencyByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ContactListFrequencyUniversalSpec universalSpec, final boolean allowDefault) {
        return getContactListFrequencyByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }
    
}
