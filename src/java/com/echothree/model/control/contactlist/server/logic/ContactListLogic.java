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

import com.echothree.control.user.contactlist.common.spec.ContactListUniversalSpec;
import com.echothree.model.control.contactlist.common.exception.UnknownContactListContactMechanismPurposeException;
import com.echothree.model.control.contactlist.common.exception.UnknownContactListNameException;
import com.echothree.model.control.contactlist.common.exception.UnknownDefaultContactListException;
import com.echothree.model.control.contactlist.common.workflow.PartyContactListStatusConstants;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.ContactListContactMechanismPurpose;
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactList;
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactListGroup;
import com.echothree.model.data.contactlist.server.entity.PartyContactList;
import com.echothree.model.data.contactlist.server.entity.PartyTypeContactList;
import com.echothree.model.data.contactlist.server.entity.PartyTypeContactListGroup;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class ContactListLogic
    extends BaseLogic {

    protected ContactListLogic() {
        super();
    }

    public static ContactListLogic getInstance() {
        return CDI.current().select(ContactListLogic.class).get();
    }

    @Inject
    ContactListControl contactListControl;

    @Inject
    CustomerControl customerControl;

    @Inject
    EntityInstanceControl entityInstanceControl;

    @Inject
    WorkflowControl workflowControl;

    @Inject
    ContactListChainLogic contactListChainLogic;

    @Inject
    PartyLogic partyLogic;
    
    public ContactList getContactListByName(final ExecutionErrorAccumulator eea, final String contactListName,
            final EntityPermission entityPermission) {
        var contactList = contactListControl.getContactListByName(contactListName, entityPermission);

        if(contactList == null) {
            handleExecutionError(UnknownContactListNameException.class, eea, ExecutionErrors.UnknownContactListName.name(), contactListName);
        }

        return contactList;
    }

    public ContactList getContactListByName(final ExecutionErrorAccumulator eea, final String contactListName) {
        return getContactListByName(eea, contactListName, EntityPermission.READ_ONLY);
    }

    public ContactList getContactListByNameForUpdate(final ExecutionErrorAccumulator eea, final String contactListName) {
        return getContactListByName(eea, contactListName, EntityPermission.READ_WRITE);
    }

    public ContactList getContactListByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContactListUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        ContactList contactList = null;
        var contactListName = universalSpec.getContactListName();
        var parameterCount = (contactListName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    contactList = contactListControl.getDefaultContactList(entityPermission);

                    if(contactList == null) {
                        handleExecutionError(UnknownDefaultContactListException.class, eea, ExecutionErrors.UnknownDefaultContactList.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(contactListName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ContactList.name());

                    if(!eea.hasExecutionErrors()) {
                        contactList = contactListControl.getContactListByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    contactList = getContactListByName(eea, contactListName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return contactList;
    }

    public ContactList getContactListByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ContactListUniversalSpec universalSpec, boolean allowDefault) {
        return getContactListByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ContactList getContactListByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ContactListUniversalSpec universalSpec, boolean allowDefault) {
        return getContactListByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public ContactListContactMechanismPurpose getContactListContactMechanismPurpose(final ExecutionErrorAccumulator eea, final ContactList contactList,
            final ContactMechanismPurpose contactMechanismPurpose) {
        var contactListContactMechanismPurpose = contactListControl.getContactListContactMechanismPurpose(contactList, contactMechanismPurpose);
        
        if(contactListContactMechanismPurpose == null) {
            handleExecutionError(UnknownContactListContactMechanismPurposeException.class, eea, ExecutionErrors.UnknownContactListContactMechanismPurpose.name(),
                    contactList.getLastDetail().getContactListName(), contactMechanismPurpose.getContactMechanismPurposeName());
        }
        
        return contactListContactMechanismPurpose;
    }
    
    public boolean isPartyOnContactList(final Party party, final ContactList contactList) {
        return contactListControl.getPartyContactList(party, contactList) != null;
    }
    
    public void addContactListToParty(final ExecutionErrorAccumulator eea, final Party party, final ContactList contactList,
            final ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose, final BasePK createdBy) {
        var partyContactList = contactListControl.createPartyContactList(party, contactList, preferredContactListContactMechanismPurpose, createdBy);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyContactList.getPrimaryKey());
        var workflowEntrance = contactList.getLastDetail().getDefaultPartyContactListStatus();
        var workflowEntranceName = workflowEntrance.getLastDetail().getWorkflowEntranceName();
        
        workflowControl.addEntityToWorkflow(workflowEntrance, entityInstance, null, null, createdBy);
        
        if(workflowEntranceName.equals(PartyContactListStatusConstants.WorkflowEntrance_NEW_AWAITING_VERIFICATION)) {
            contactListChainLogic.createContactListConfirmationChainInstance(eea, party, partyContactList, createdBy);
        } else if(workflowEntranceName.equals(PartyContactListStatusConstants.WorkflowEntrance_NEW_ACTIVE)) {
            contactListChainLogic.createContactListSubscribeChainInstance(eea, party, partyContactList, createdBy);
        }
    }
    
    public void removeContactListFromParty(final ExecutionErrorAccumulator eea, final PartyContactList partyContactList, final BasePK deletedBy) {
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyContactList.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PartyContactListStatusConstants.Workflow_PARTY_CONTACT_LIST_STATUS, entityInstance);
        var workflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
        
        if(workflowStepName.equals(PartyContactListStatusConstants.WorkflowStep_ACTIVE)) {
            contactListChainLogic.createContactListUnsubscribeChainInstance(eea, partyContactList.getLastDetail().getParty(), partyContactList, deletedBy);
        }
        
        contactListControl.deletePartyContactList(partyContactList, deletedBy);
        workflowControl.deleteWorkflowEntityStatus(workflowEntityStatus, deletedBy);
    }
    
    public void setupInitialContactLists(final ExecutionErrorAccumulator eea, final Party party, final BasePK createdBy) {
        var partyType = party.getLastDetail().getPartyType();
        Set<ContactList> contactLists = new HashSet<>();

        contactListControl.getPartyTypeContactListsByPartyType(partyType)
                .stream()
                .filter(PartyTypeContactList::getAddWhenCreated)
                .map(PartyTypeContactList::getContactList)
                .forEach(contactLists::add);

        contactListControl.getPartyTypeContactListGroupsByPartyType(partyType)
                .stream()
                .filter(PartyTypeContactListGroup::getAddWhenCreated)
                .forEach((partyTypeContactListGroup) ->
                        contactLists.addAll(contactListControl.getContactListsByContactListGroup(partyTypeContactListGroup.getContactListGroup()))
                );

        if(partyLogic.isPartyType(party, PartyTypes.CUSTOMER.name())) {
            var customerType = customerControl.getCustomer(party).getCustomerType();

            contactListControl.getCustomerTypeContactListsByCustomerType(customerType)
                    .stream()
                    .filter(CustomerTypeContactList::getAddWhenCreated)
                    .map(CustomerTypeContactList::getContactList)
                    .forEach(contactLists::add);

            contactListControl.getCustomerTypeContactListGroupsByCustomerType(customerType)
                    .stream()
                    .filter(CustomerTypeContactListGroup::getAddWhenCreated)
                    .forEach((customerTypeContactListGroup) ->
                            contactLists.addAll(contactListControl.getContactListsByContactListGroup(customerTypeContactListGroup.getContactListGroup()))
                    );
        }

        if(!hasExecutionErrors(eea)) {
            contactLists.forEach((contactList) -> {
                addContactListToParty(eea, party, contactList, null, createdBy);
            });
        }
    }
    
}
