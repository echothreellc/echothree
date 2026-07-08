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

import com.echothree.control.user.contactlist.common.spec.ContactListTypeUniversalSpec;
import com.echothree.model.control.contactlist.common.exception.UnknownContactListTypeNameException;
import com.echothree.model.control.contactlist.common.exception.UnknownDefaultContactListTypeException;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.contactlist.server.entity.ContactListType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ContactListTypeLogic
    extends BaseLogic {

    protected ContactListTypeLogic() {
        super();
    }

    @Inject
    ContactListControl contactListControl;

    public ContactListType getContactListTypeByName(final ExecutionErrorAccumulator eea, final String contactListTypeName,
            final EntityPermission entityPermission) {
        var contactListType = contactListControl.getContactListTypeByName(contactListTypeName, entityPermission);

        if(contactListType == null) {
            handleExecutionError(UnknownContactListTypeNameException.class, eea, ExecutionErrors.UnknownContactListTypeName.name(), contactListTypeName);
        }

        return contactListType;
    }

    public ContactListType getContactListTypeByName(final ExecutionErrorAccumulator eea, final String contactListTypeName) {
        return getContactListTypeByName(eea, contactListTypeName, EntityPermission.READ_ONLY);
    }

    public ContactListType getContactListTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String contactListTypeName) {
        return getContactListTypeByName(eea, contactListTypeName, EntityPermission.READ_WRITE);
    }

    public ContactListType getContactListTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContactListTypeUniversalSpec spec, final boolean allowDefault, final EntityPermission entityPermission) {
        ContactListType contactListType = null;
        var contactListTypeName = spec.getContactListTypeName();
        var parameterCount = (contactListTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(spec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    contactListType = contactListControl.getDefaultContactListType(entityPermission);

                    if(contactListType == null) {
                        handleExecutionError(UnknownDefaultContactListTypeException.class, eea, ExecutionErrors.UnknownDefaultContactListType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(contactListTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, spec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ContactListType.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        contactListType = contactListControl.getContactListTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    contactListType = getContactListTypeByName(eea, contactListTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return contactListType;
    }

    public ContactListType getContactListTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContactListTypeUniversalSpec spec, final boolean allowDefault) {
        return getContactListTypeByUniversalSpec(eea, spec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ContactListType getContactListTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ContactListTypeUniversalSpec spec, final boolean allowDefault) {
        return getContactListTypeByUniversalSpec(eea, spec, allowDefault, EntityPermission.READ_WRITE);
    }
    
}
