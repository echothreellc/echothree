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

import com.echothree.control.user.contactlist.common.spec.ContactListGroupUniversalSpec;
import com.echothree.model.control.contactlist.common.exception.UnknownContactListGroupNameException;
import com.echothree.model.control.contactlist.common.exception.UnknownDefaultContactListGroupException;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.contactlist.server.entity.ContactListGroup;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class ContactListGroupLogic
    extends BaseLogic {

    protected ContactListGroupLogic() {
        super();
    }

    public static ContactListGroupLogic getInstance() {
        return CDI.current().select(ContactListGroupLogic.class).get();
    }
    
    @Inject
    ContactListControl contactListControl;

    public ContactListGroup getContactListGroupByName(final ExecutionErrorAccumulator eea, final String contactListGroupName,
            final EntityPermission entityPermission) {
        var contactListGroup = contactListControl.getContactListGroupByName(contactListGroupName, entityPermission);

        if(contactListGroup == null) {
            handleExecutionError(UnknownContactListGroupNameException.class, eea, ExecutionErrors.UnknownContactListGroupName.name(), contactListGroupName);
        }

        return contactListGroup;
    }

    public ContactListGroup getContactListGroupByName(final ExecutionErrorAccumulator eea, final String contactListGroupName) {
        return getContactListGroupByName(eea, contactListGroupName, EntityPermission.READ_ONLY);
    }

    public ContactListGroup getContactListGroupByNameForUpdate(final ExecutionErrorAccumulator eea, final String contactListGroupName) {
        return getContactListGroupByName(eea, contactListGroupName, EntityPermission.READ_WRITE);
    }

    public ContactListGroup getContactListGroupByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContactListGroupUniversalSpec spec, final boolean allowDefault, final EntityPermission entityPermission) {
        ContactListGroup contactListGroup = null;
        var contactListGroupName = spec.getContactListGroupName();
        var parameterCount = (contactListGroupName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(spec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    contactListGroup = contactListControl.getDefaultContactListGroup(entityPermission);

                    if(contactListGroup == null) {
                        handleExecutionError(UnknownDefaultContactListGroupException.class, eea, ExecutionErrors.UnknownDefaultContactListGroup.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(contactListGroupName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, spec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ContactListGroup.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        contactListGroup = contactListControl.getContactListGroupByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    contactListGroup = getContactListGroupByName(eea, contactListGroupName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return contactListGroup;
    }

    public ContactListGroup getContactListGroupByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContactListGroupUniversalSpec spec, final boolean allowDefault) {
        return getContactListGroupByUniversalSpec(eea, spec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ContactListGroup getContactListGroupByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ContactListGroupUniversalSpec spec, final boolean allowDefault) {
        return getContactListGroupByUniversalSpec(eea, spec, allowDefault, EntityPermission.READ_WRITE);
    }
    
}
