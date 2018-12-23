// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.contactlist.server;

import com.echothree.control.user.contactlist.common.ContactListRemote;
import com.echothree.control.user.contactlist.common.form.*;
import com.echothree.control.user.contactlist.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class ContactListBean
        extends ContactListFormsImpl
        implements ContactListRemote, ContactListLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "ContactListBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Contact List Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createContactListType(UserVisitPK userVisitPK, CreateContactListTypeForm form) {
        return new CreateContactListTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListTypes(UserVisitPK userVisitPK, GetContactListTypesForm form) {
        return new GetContactListTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListType(UserVisitPK userVisitPK, GetContactListTypeForm form) {
        return new GetContactListTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListTypeChoices(UserVisitPK userVisitPK, GetContactListTypeChoicesForm form) {
        return new GetContactListTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultContactListType(UserVisitPK userVisitPK, SetDefaultContactListTypeForm form) {
        return new SetDefaultContactListTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editContactListType(UserVisitPK userVisitPK, EditContactListTypeForm form) {
        return new EditContactListTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteContactListType(UserVisitPK userVisitPK, DeleteContactListTypeForm form) {
        return new DeleteContactListTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Contact List Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createContactListTypeDescription(UserVisitPK userVisitPK, CreateContactListTypeDescriptionForm form) {
        return new CreateContactListTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListTypeDescriptions(UserVisitPK userVisitPK, GetContactListTypeDescriptionsForm form) {
        return new GetContactListTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListTypeDescription(UserVisitPK userVisitPK, GetContactListTypeDescriptionForm form) {
        return new GetContactListTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editContactListTypeDescription(UserVisitPK userVisitPK, EditContactListTypeDescriptionForm form) {
        return new EditContactListTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteContactListTypeDescription(UserVisitPK userVisitPK, DeleteContactListTypeDescriptionForm form) {
        return new DeleteContactListTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Contact List Groups
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactListGroup(UserVisitPK userVisitPK, CreateContactListGroupForm form) {
        return new CreateContactListGroupCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListGroups(UserVisitPK userVisitPK, GetContactListGroupsForm form) {
        return new GetContactListGroupsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListGroup(UserVisitPK userVisitPK, GetContactListGroupForm form) {
        return new GetContactListGroupCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListGroupChoices(UserVisitPK userVisitPK, GetContactListGroupChoicesForm form) {
        return new GetContactListGroupChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultContactListGroup(UserVisitPK userVisitPK, SetDefaultContactListGroupForm form) {
        return new SetDefaultContactListGroupCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editContactListGroup(UserVisitPK userVisitPK, EditContactListGroupForm form) {
        return new EditContactListGroupCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteContactListGroup(UserVisitPK userVisitPK, DeleteContactListGroupForm form) {
        return new DeleteContactListGroupCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Contact List Group Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactListGroupDescription(UserVisitPK userVisitPK, CreateContactListGroupDescriptionForm form) {
        return new CreateContactListGroupDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListGroupDescriptions(UserVisitPK userVisitPK, GetContactListGroupDescriptionsForm form) {
        return new GetContactListGroupDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListGroupDescription(UserVisitPK userVisitPK, GetContactListGroupDescriptionForm form) {
        return new GetContactListGroupDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editContactListGroupDescription(UserVisitPK userVisitPK, EditContactListGroupDescriptionForm form) {
        return new EditContactListGroupDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteContactListGroupDescription(UserVisitPK userVisitPK, DeleteContactListGroupDescriptionForm form) {
        return new DeleteContactListGroupDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Contact List Frequencies
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactListFrequency(UserVisitPK userVisitPK, CreateContactListFrequencyForm form) {
        return new CreateContactListFrequencyCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListFrequencies(UserVisitPK userVisitPK, GetContactListFrequenciesForm form) {
        return new GetContactListFrequenciesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListFrequency(UserVisitPK userVisitPK, GetContactListFrequencyForm form) {
        return new GetContactListFrequencyCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListFrequencyChoices(UserVisitPK userVisitPK, GetContactListFrequencyChoicesForm form) {
        return new GetContactListFrequencyChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultContactListFrequency(UserVisitPK userVisitPK, SetDefaultContactListFrequencyForm form) {
        return new SetDefaultContactListFrequencyCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editContactListFrequency(UserVisitPK userVisitPK, EditContactListFrequencyForm form) {
        return new EditContactListFrequencyCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteContactListFrequency(UserVisitPK userVisitPK, DeleteContactListFrequencyForm form) {
        return new DeleteContactListFrequencyCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Contact List Frequency Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactListFrequencyDescription(UserVisitPK userVisitPK, CreateContactListFrequencyDescriptionForm form) {
        return new CreateContactListFrequencyDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListFrequencyDescriptions(UserVisitPK userVisitPK, GetContactListFrequencyDescriptionsForm form) {
        return new GetContactListFrequencyDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListFrequencyDescription(UserVisitPK userVisitPK, GetContactListFrequencyDescriptionForm form) {
        return new GetContactListFrequencyDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editContactListFrequencyDescription(UserVisitPK userVisitPK, EditContactListFrequencyDescriptionForm form) {
        return new EditContactListFrequencyDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteContactListFrequencyDescription(UserVisitPK userVisitPK, DeleteContactListFrequencyDescriptionForm form) {
        return new DeleteContactListFrequencyDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Contact Lists
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactList(UserVisitPK userVisitPK, CreateContactListForm form) {
        return new CreateContactListCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactLists(UserVisitPK userVisitPK, GetContactListsForm form) {
        return new GetContactListsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactList(UserVisitPK userVisitPK, GetContactListForm form) {
        return new GetContactListCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListChoices(UserVisitPK userVisitPK, GetContactListChoicesForm form) {
        return new GetContactListChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultContactList(UserVisitPK userVisitPK, SetDefaultContactListForm form) {
        return new SetDefaultContactListCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editContactList(UserVisitPK userVisitPK, EditContactListForm form) {
        return new EditContactListCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteContactList(UserVisitPK userVisitPK, DeleteContactListForm form) {
        return new DeleteContactListCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Contact List Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactListDescription(UserVisitPK userVisitPK, CreateContactListDescriptionForm form) {
        return new CreateContactListDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListDescriptions(UserVisitPK userVisitPK, GetContactListDescriptionsForm form) {
        return new GetContactListDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContactListDescription(UserVisitPK userVisitPK, GetContactListDescriptionForm form) {
        return new GetContactListDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editContactListDescription(UserVisitPK userVisitPK, EditContactListDescriptionForm form) {
        return new EditContactListDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteContactListDescription(UserVisitPK userVisitPK, DeleteContactListDescriptionForm form) {
        return new DeleteContactListDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Party Type Contact Lists
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeContactList(UserVisitPK userVisitPK, CreatePartyTypeContactListForm form) {
        return new CreatePartyTypeContactListCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartyTypeContactLists(UserVisitPK userVisitPK, GetPartyTypeContactListsForm form) {
        return new GetPartyTypeContactListsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyTypeContactList(UserVisitPK userVisitPK, GetPartyTypeContactListForm form) {
        return new GetPartyTypeContactListCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPartyTypeContactList(UserVisitPK userVisitPK, EditPartyTypeContactListForm form) {
        return new EditPartyTypeContactListCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartyTypeContactList(UserVisitPK userVisitPK, DeletePartyTypeContactListForm form) {
        return new DeletePartyTypeContactListCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Party Type Contact List Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeContactListGroup(UserVisitPK userVisitPK, CreatePartyTypeContactListGroupForm form) {
        return new CreatePartyTypeContactListGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartyTypeContactListGroups(UserVisitPK userVisitPK, GetPartyTypeContactListGroupsForm form) {
        return new GetPartyTypeContactListGroupsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyTypeContactListGroup(UserVisitPK userVisitPK, GetPartyTypeContactListGroupForm form) {
        return new GetPartyTypeContactListGroupCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPartyTypeContactListGroup(UserVisitPK userVisitPK, EditPartyTypeContactListGroupForm form) {
        return new EditPartyTypeContactListGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartyTypeContactListGroup(UserVisitPK userVisitPK, DeletePartyTypeContactListGroupForm form) {
        return new DeletePartyTypeContactListGroupCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Customer Type Contact Lists
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createCustomerTypeContactList(UserVisitPK userVisitPK, CreateCustomerTypeContactListForm form) {
        return new CreateCustomerTypeContactListCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCustomerTypeContactLists(UserVisitPK userVisitPK, GetCustomerTypeContactListsForm form) {
        return new GetCustomerTypeContactListsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCustomerTypeContactList(UserVisitPK userVisitPK, GetCustomerTypeContactListForm form) {
        return new GetCustomerTypeContactListCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editCustomerTypeContactList(UserVisitPK userVisitPK, EditCustomerTypeContactListForm form) {
        return new EditCustomerTypeContactListCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteCustomerTypeContactList(UserVisitPK userVisitPK, DeleteCustomerTypeContactListForm form) {
        return new DeleteCustomerTypeContactListCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Customer Type Contact List Groups
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createCustomerTypeContactListGroup(UserVisitPK userVisitPK, CreateCustomerTypeContactListGroupForm form) {
        return new CreateCustomerTypeContactListGroupCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCustomerTypeContactListGroups(UserVisitPK userVisitPK, GetCustomerTypeContactListGroupsForm form) {
        return new GetCustomerTypeContactListGroupsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCustomerTypeContactListGroup(UserVisitPK userVisitPK, GetCustomerTypeContactListGroupForm form) {
        return new GetCustomerTypeContactListGroupCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editCustomerTypeContactListGroup(UserVisitPK userVisitPK, EditCustomerTypeContactListGroupForm form) {
        return new EditCustomerTypeContactListGroupCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteCustomerTypeContactListGroup(UserVisitPK userVisitPK, DeleteCustomerTypeContactListGroupForm form) {
        return new DeleteCustomerTypeContactListGroupCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Contact List Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactListContactMechanismPurpose(UserVisitPK userVisitPK, CreateContactListContactMechanismPurposeForm form) {
        return new CreateContactListContactMechanismPurposeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContactListContactMechanismPurposeChoices(UserVisitPK userVisitPK, GetContactListContactMechanismPurposeChoicesForm form) {
        return new GetContactListContactMechanismPurposeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContactListContactMechanismPurpose(UserVisitPK userVisitPK, GetContactListContactMechanismPurposeForm form) {
        return new GetContactListContactMechanismPurposeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContactListContactMechanismPurposes(UserVisitPK userVisitPK, GetContactListContactMechanismPurposesForm form) {
        return new GetContactListContactMechanismPurposesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultContactListContactMechanismPurpose(UserVisitPK userVisitPK, SetDefaultContactListContactMechanismPurposeForm form) {
        return new SetDefaultContactListContactMechanismPurposeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editContactListContactMechanismPurpose(UserVisitPK userVisitPK, EditContactListContactMechanismPurposeForm form) {
        return new EditContactListContactMechanismPurposeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteContactListContactMechanismPurpose(UserVisitPK userVisitPK, DeleteContactListContactMechanismPurposeForm form) {
        return new DeleteContactListContactMechanismPurposeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Lists
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyContactList(UserVisitPK userVisitPK, CreatePartyContactListForm form) {
        return new CreatePartyContactListCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartyContactLists(UserVisitPK userVisitPK, GetPartyContactListsForm form) {
        return new GetPartyContactListsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyContactList(UserVisitPK userVisitPK, GetPartyContactListForm form) {
        return new GetPartyContactListCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyContactListStatusChoices(UserVisitPK userVisitPK, GetPartyContactListStatusChoicesForm form) {
        return new GetPartyContactListStatusChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setPartyContactListStatus(UserVisitPK userVisitPK, SetPartyContactListStatusForm form) {
        return new SetPartyContactListStatusCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPartyContactList(UserVisitPK userVisitPK, EditPartyContactListForm form) {
        return new EditPartyContactListCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartyContactList(UserVisitPK userVisitPK, DeletePartyContactListForm form) {
        return new DeletePartyContactListCommand(userVisitPK, form).run();
    }
    
}
