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

import com.echothree.model.control.contactlist.common.exception.UnknownContactListContactMechanismPurposeException;
import com.echothree.model.control.contactlist.common.exception.UnknownContactListNameException;
import com.echothree.model.control.contactlist.common.workflow.PartyContactListStatusConstants;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.ContactListContactMechanismPurpose;
import com.echothree.model.data.contactlist.server.entity.PartyContactList;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ContactListLogic
    extends BaseLogic {

    protected ContactListLogic() {
        super();
    }

    public static ContactListLogic getInstance() {
        return CDI.current().select(ContactListLogic.class).get();
    }
    
    public ContactList getContactListByName(final ExecutionErrorAccumulator eea, final String contactListName) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var contactList = contactListControl.getContactListByName(contactListName);

        if(contactList == null) {
            handleExecutionError(UnknownContactListNameException.class, eea, ExecutionErrors.UnknownContactListName.name(), contactListName);
        }

        return contactList;
    }
    
    public ContactListContactMechanismPurpose getContactListContactMechanismPurpose(final ExecutionErrorAccumulator eea, final ContactList contactList,
            final ContactMechanismPurpose contactMechanismPurpose) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var contactListContactMechanismPurpose = contactListControl.getContactListContactMechanismPurpose(contactList, contactMechanismPurpose);
        
        if(contactListContactMechanismPurpose == null) {
            handleExecutionError(UnknownContactListContactMechanismPurposeException.class, eea, ExecutionErrors.UnknownContactListContactMechanismPurpose.name(),
                    contactList.getLastDetail().getContactListName(), contactMechanismPurpose.getContactMechanismPurposeName());
        }
        
        return contactListContactMechanismPurpose;
    }
    
    public boolean isPartyOnContactList(final Party party, final ContactList contactList) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        
        return contactListControl.getPartyContactList(party, contactList) != null;
    }
    
    public void addContactListToParty(final ExecutionErrorAccumulator eea, final Party party, final ContactList contactList,
            final ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose, final BasePK createdBy) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var partyContactList = contactListControl.createPartyContactList(party, contactList, preferredContactListContactMechanismPurpose, createdBy);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyContactList.getPrimaryKey());
        var workflowEntrance = contactList.getLastDetail().getDefaultPartyContactListStatus();
        var workflowEntranceName = workflowEntrance.getLastDetail().getWorkflowEntranceName();
        
        workflowControl.addEntityToWorkflow(workflowEntrance, entityInstance, null, null, createdBy);
        
        if(workflowEntranceName.equals(PartyContactListStatusConstants.WorkflowEntrance_NEW_AWAITING_VERIFICATION)) {
            ContactListChainLogic.getInstance().createContactListConfirmationChainInstance(eea, party, partyContactList, createdBy);
        } else if(workflowEntranceName.equals(PartyContactListStatusConstants.WorkflowEntrance_NEW_ACTIVE)) {
            ContactListChainLogic.getInstance().createContactListSubscribeChainInstance(eea, party, partyContactList, createdBy);
        }
    }
    
    public void removeContactListFromParty(final ExecutionErrorAccumulator eea, final PartyContactList partyContactList, final BasePK deletedBy) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(partyContactList.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(PartyContactListStatusConstants.Workflow_PARTY_CONTACT_LIST_STATUS, entityInstance);
        var workflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
        
        if(workflowStepName.equals(PartyContactListStatusConstants.WorkflowStep_ACTIVE)) {
            ContactListChainLogic.getInstance().createContactListUnsubscribeChainInstance(eea, partyContactList.getLastDetail().getParty(), partyContactList, deletedBy);
        }
        
        contactListControl.deletePartyContactList(partyContactList, deletedBy);
        workflowControl.deleteWorkflowEntityStatus(workflowEntityStatus, deletedBy);
    }
    
    public void setupInitialContactLists(final ExecutionErrorAccumulator eea, final Party party, final BasePK createdBy) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var partyType = party.getLastDetail().getPartyType();
        Set<ContactList> contactLists = new HashSet<>();

        contactListControl.getPartyTypeContactListsByPartyType(partyType).stream().filter((partyTypeContactList) -> partyTypeContactList.getAddWhenCreated()).map((partyTypeContactList) -> partyTypeContactList.getContactList()).forEach((contactList) -> {
            contactLists.add(contactList);
        });
        contactListControl.getPartyTypeContactListGroupsByPartyType(partyType).stream().filter((partyTypeContactListGroup) -> partyTypeContactListGroup.getAddWhenCreated()).forEach((partyTypeContactListGroup) -> {
            contactLists.addAll(contactListControl.getContactListsByContactListGroup(partyTypeContactListGroup.getContactListGroup()));
        });

        if(PartyLogic.getInstance().isPartyType(party, PartyTypes.CUSTOMER.name())) {
            var customerControl = Session.getModelController(CustomerControl.class);
            var customerType = customerControl.getCustomer(party).getCustomerType();

            contactListControl.getCustomerTypeContactListsByCustomerType(customerType).stream().filter((customerTypeContactList) -> customerTypeContactList.getAddWhenCreated()).map((customerTypeContactList) -> customerTypeContactList.getContactList()).forEach((contactList) -> {
                contactLists.add(contactList);
            });
            contactListControl.getCustomerTypeContactListGroupsByCustomerType(customerType).stream().filter((customerTypeContactListGroup) -> customerTypeContactListGroup.getAddWhenCreated()).forEach((customerTypeContactListGroup) -> {
                contactLists.addAll(contactListControl.getContactListsByContactListGroup(customerTypeContactListGroup.getContactListGroup()));
            });
        }

        if(!hasExecutionErrors(eea)) {
            contactLists.forEach((contactList) -> {
                addContactListToParty(eea, party, contactList, null, createdBy);
            });
        }
    }
    
}
