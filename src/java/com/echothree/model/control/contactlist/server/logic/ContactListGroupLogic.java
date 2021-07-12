// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.control.contactlist.common.transfer.ContactListGroupTransfer;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.data.contactlist.server.entity.ContactListGroup;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class ContactListGroupLogic
    extends BaseLogic {
    
    private ContactListGroupLogic() {
        super();
    }
    
    private static class ContactListLogicHolder {
        static ContactListGroupLogic instance = new ContactListGroupLogic();
    }
    
    public static ContactListGroupLogic getInstance() {
        return ContactListLogicHolder.instance;
    }

    public ContactListGroup getContactListGroupByName(final ExecutionErrorAccumulator eea, final String contactListGroupName,
            final EntityPermission entityPermission) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        ContactListGroup contactListGroup = contactListControl.getContactListGroupByName(contactListGroupName, entityPermission);

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
            final ContactListGroupUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        ContactListGroup contactListGroup = null;
        var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
        String contactListGroupName = universalSpec.getContactListGroupName();
        int parameterCount = (contactListGroupName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0:
                if(allowDefault) {
                    contactListGroup = contactListControl.getDefaultContactListGroup(entityPermission);

                    if(contactListGroup == null) {
                        handleExecutionError(UnknownDefaultContactListGroupException.class, eea, ExecutionErrors.UnknownDefaultContactListGroup.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
                break;
            case 1:
                if(contactListGroupName == null) {
                    EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHOTHREE.name(), EntityTypes.ContactListGroup.name());

                    if(!eea.hasExecutionErrors()) {
                        contactListGroup = contactListControl.getContactListGroupByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    contactListGroup = getContactListGroupByName(eea, contactListGroupName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return contactListGroup;
    }

    public ContactListGroup getContactListGroupByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContactListGroupUniversalSpec universalSpec, boolean allowDefault) {
        return getContactListGroupByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ContactListGroup getContactListGroupByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ContactListGroupUniversalSpec universalSpec, boolean allowDefault) {
        return getContactListGroupByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public ContactListGroupTransfer getContactListGroupTransfer(UserVisit userVisit, ContactListGroup contactListGroup) {
        var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);

        return contactListControl.getContactListGroupTransfer(userVisit, contactListGroup);
    }

    public List<ContactListGroupTransfer> getContactListGroupTransfers(UserVisit userVisit) {
        var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);

        return contactListControl.getContactListGroupTransfers(userVisit);
    }

    public boolean hasContactListGroupAccess(final Party executingParty, final ContactListGroup contactListGroup) {
        var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
        var partyType = executingParty.getLastDetail().getPartyType();
        var partyTypeName = partyType.getPartyTypeName();

        // Employees may do anything they want with list groups.
        var hasAccess = partyTypeName.equals(PartyTypes.EMPLOYEE.name());

        // If the Contact List Group Group that the Contact List Group is in has been explicitly given access to the Party Type,
        // then allow access.
        if(!hasAccess) {
            hasAccess = contactListControl.partyTypeContactListGroupExists(partyType, contactListGroup);
        }

        // If access hasn't been granted, allow access if the Party Type has been explicitly given access to the
        // Contact List Group, or if the Contact List Group has no further restrictions.
        if(!hasAccess) {
            hasAccess = contactListControl.partyTypeContactListGroupExists(partyType, contactListGroup)
                    || contactListControl.countPartyTypeContactListGroupsByContactListGroup(contactListGroup) == 0;
        }

        // Customers have some special checks based on Customer Type, if access still has not yet been granted.
        if(!hasAccess && partyTypeName.equals(PartyTypes.CUSTOMER.name())) {
            var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
            var customerType = customerControl.getCustomer(executingParty).getCustomerType();

            // If the Contact List Group is in has been explicitly given access to the Party Type, then allow access.
            hasAccess = contactListControl.customerTypeContactListGroupExists(customerType, contactListGroup);

            // If access hasn't been granted, allow access if the Customer Type has been explicitly given access to the
            // Contact List Group, or if the Contact List Group has no further restrictions.
            if(!hasAccess) {
                hasAccess = contactListControl.customerTypeContactListGroupExists(customerType, contactListGroup)
                        || contactListControl.countCustomerTypeContactListGroupsByContactListGroup(contactListGroup) == 0;
            }
        }

        // If the executingParty has access to any of the ContactLists in the ContactListGroup, then they have access to
        // the ContactListGroup.
        if(!hasAccess) {
            hasAccess = contactListControl.getContactListsByContactListGroup(contactListGroup).stream()
                    .map(contactList -> ContactListLogic.getInstance().hasContactListAccess(executingParty, contactList))
                    .reduce(Boolean::logicalOr)
                    .orElse(false);
        }

        return hasAccess;
    }

}
