// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
        return new CreateContactListTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListTypes(UserVisitPK userVisitPK, GetContactListTypesForm form) {
        return new GetContactListTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListType(UserVisitPK userVisitPK, GetContactListTypeForm form) {
        return new GetContactListTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListTypeChoices(UserVisitPK userVisitPK, GetContactListTypeChoicesForm form) {
        return new GetContactListTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultContactListType(UserVisitPK userVisitPK, SetDefaultContactListTypeForm form) {
        return new SetDefaultContactListTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContactListType(UserVisitPK userVisitPK, EditContactListTypeForm form) {
        return new EditContactListTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactListType(UserVisitPK userVisitPK, DeleteContactListTypeForm form) {
        return new DeleteContactListTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Contact List Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createContactListTypeDescription(UserVisitPK userVisitPK, CreateContactListTypeDescriptionForm form) {
        return new CreateContactListTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListTypeDescriptions(UserVisitPK userVisitPK, GetContactListTypeDescriptionsForm form) {
        return new GetContactListTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListTypeDescription(UserVisitPK userVisitPK, GetContactListTypeDescriptionForm form) {
        return new GetContactListTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContactListTypeDescription(UserVisitPK userVisitPK, EditContactListTypeDescriptionForm form) {
        return new EditContactListTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactListTypeDescription(UserVisitPK userVisitPK, DeleteContactListTypeDescriptionForm form) {
        return new DeleteContactListTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Groups
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactListGroup(UserVisitPK userVisitPK, CreateContactListGroupForm form) {
        return new CreateContactListGroupCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListGroups(UserVisitPK userVisitPK, GetContactListGroupsForm form) {
        return new GetContactListGroupsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListGroup(UserVisitPK userVisitPK, GetContactListGroupForm form) {
        return new GetContactListGroupCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListGroupChoices(UserVisitPK userVisitPK, GetContactListGroupChoicesForm form) {
        return new GetContactListGroupChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultContactListGroup(UserVisitPK userVisitPK, SetDefaultContactListGroupForm form) {
        return new SetDefaultContactListGroupCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContactListGroup(UserVisitPK userVisitPK, EditContactListGroupForm form) {
        return new EditContactListGroupCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactListGroup(UserVisitPK userVisitPK, DeleteContactListGroupForm form) {
        return new DeleteContactListGroupCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Group Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactListGroupDescription(UserVisitPK userVisitPK, CreateContactListGroupDescriptionForm form) {
        return new CreateContactListGroupDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListGroupDescriptions(UserVisitPK userVisitPK, GetContactListGroupDescriptionsForm form) {
        return new GetContactListGroupDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListGroupDescription(UserVisitPK userVisitPK, GetContactListGroupDescriptionForm form) {
        return new GetContactListGroupDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContactListGroupDescription(UserVisitPK userVisitPK, EditContactListGroupDescriptionForm form) {
        return new EditContactListGroupDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactListGroupDescription(UserVisitPK userVisitPK, DeleteContactListGroupDescriptionForm form) {
        return new DeleteContactListGroupDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Frequencies
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactListFrequency(UserVisitPK userVisitPK, CreateContactListFrequencyForm form) {
        return new CreateContactListFrequencyCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListFrequencies(UserVisitPK userVisitPK, GetContactListFrequenciesForm form) {
        return new GetContactListFrequenciesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListFrequency(UserVisitPK userVisitPK, GetContactListFrequencyForm form) {
        return new GetContactListFrequencyCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListFrequencyChoices(UserVisitPK userVisitPK, GetContactListFrequencyChoicesForm form) {
        return new GetContactListFrequencyChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultContactListFrequency(UserVisitPK userVisitPK, SetDefaultContactListFrequencyForm form) {
        return new SetDefaultContactListFrequencyCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContactListFrequency(UserVisitPK userVisitPK, EditContactListFrequencyForm form) {
        return new EditContactListFrequencyCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactListFrequency(UserVisitPK userVisitPK, DeleteContactListFrequencyForm form) {
        return new DeleteContactListFrequencyCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Frequency Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactListFrequencyDescription(UserVisitPK userVisitPK, CreateContactListFrequencyDescriptionForm form) {
        return new CreateContactListFrequencyDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListFrequencyDescriptions(UserVisitPK userVisitPK, GetContactListFrequencyDescriptionsForm form) {
        return new GetContactListFrequencyDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListFrequencyDescription(UserVisitPK userVisitPK, GetContactListFrequencyDescriptionForm form) {
        return new GetContactListFrequencyDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContactListFrequencyDescription(UserVisitPK userVisitPK, EditContactListFrequencyDescriptionForm form) {
        return new EditContactListFrequencyDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactListFrequencyDescription(UserVisitPK userVisitPK, DeleteContactListFrequencyDescriptionForm form) {
        return new DeleteContactListFrequencyDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Contact Lists
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactList(UserVisitPK userVisitPK, CreateContactListForm form) {
        return new CreateContactListCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactLists(UserVisitPK userVisitPK, GetContactListsForm form) {
        return new GetContactListsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactList(UserVisitPK userVisitPK, GetContactListForm form) {
        return new GetContactListCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListChoices(UserVisitPK userVisitPK, GetContactListChoicesForm form) {
        return new GetContactListChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultContactList(UserVisitPK userVisitPK, SetDefaultContactListForm form) {
        return new SetDefaultContactListCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContactList(UserVisitPK userVisitPK, EditContactListForm form) {
        return new EditContactListCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactList(UserVisitPK userVisitPK, DeleteContactListForm form) {
        return new DeleteContactListCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactListDescription(UserVisitPK userVisitPK, CreateContactListDescriptionForm form) {
        return new CreateContactListDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListDescriptions(UserVisitPK userVisitPK, GetContactListDescriptionsForm form) {
        return new GetContactListDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListDescription(UserVisitPK userVisitPK, GetContactListDescriptionForm form) {
        return new GetContactListDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContactListDescription(UserVisitPK userVisitPK, EditContactListDescriptionForm form) {
        return new EditContactListDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactListDescription(UserVisitPK userVisitPK, DeleteContactListDescriptionForm form) {
        return new DeleteContactListDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Type Contact Lists
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeContactList(UserVisitPK userVisitPK, CreatePartyTypeContactListForm form) {
        return new CreatePartyTypeContactListCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyTypeContactLists(UserVisitPK userVisitPK, GetPartyTypeContactListsForm form) {
        return new GetPartyTypeContactListsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyTypeContactList(UserVisitPK userVisitPK, GetPartyTypeContactListForm form) {
        return new GetPartyTypeContactListCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyTypeContactList(UserVisitPK userVisitPK, EditPartyTypeContactListForm form) {
        return new EditPartyTypeContactListCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyTypeContactList(UserVisitPK userVisitPK, DeletePartyTypeContactListForm form) {
        return new DeletePartyTypeContactListCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Type Contact List Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeContactListGroup(UserVisitPK userVisitPK, CreatePartyTypeContactListGroupForm form) {
        return new CreatePartyTypeContactListGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyTypeContactListGroups(UserVisitPK userVisitPK, GetPartyTypeContactListGroupsForm form) {
        return new GetPartyTypeContactListGroupsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyTypeContactListGroup(UserVisitPK userVisitPK, GetPartyTypeContactListGroupForm form) {
        return new GetPartyTypeContactListGroupCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyTypeContactListGroup(UserVisitPK userVisitPK, EditPartyTypeContactListGroupForm form) {
        return new EditPartyTypeContactListGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyTypeContactListGroup(UserVisitPK userVisitPK, DeletePartyTypeContactListGroupForm form) {
        return new DeletePartyTypeContactListGroupCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Customer Type Contact Lists
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createCustomerTypeContactList(UserVisitPK userVisitPK, CreateCustomerTypeContactListForm form) {
        return new CreateCustomerTypeContactListCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCustomerTypeContactLists(UserVisitPK userVisitPK, GetCustomerTypeContactListsForm form) {
        return new GetCustomerTypeContactListsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCustomerTypeContactList(UserVisitPK userVisitPK, GetCustomerTypeContactListForm form) {
        return new GetCustomerTypeContactListCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editCustomerTypeContactList(UserVisitPK userVisitPK, EditCustomerTypeContactListForm form) {
        return new EditCustomerTypeContactListCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteCustomerTypeContactList(UserVisitPK userVisitPK, DeleteCustomerTypeContactListForm form) {
        return new DeleteCustomerTypeContactListCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Customer Type Contact List Groups
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createCustomerTypeContactListGroup(UserVisitPK userVisitPK, CreateCustomerTypeContactListGroupForm form) {
        return new CreateCustomerTypeContactListGroupCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCustomerTypeContactListGroups(UserVisitPK userVisitPK, GetCustomerTypeContactListGroupsForm form) {
        return new GetCustomerTypeContactListGroupsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCustomerTypeContactListGroup(UserVisitPK userVisitPK, GetCustomerTypeContactListGroupForm form) {
        return new GetCustomerTypeContactListGroupCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editCustomerTypeContactListGroup(UserVisitPK userVisitPK, EditCustomerTypeContactListGroupForm form) {
        return new EditCustomerTypeContactListGroupCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteCustomerTypeContactListGroup(UserVisitPK userVisitPK, DeleteCustomerTypeContactListGroupForm form) {
        return new DeleteCustomerTypeContactListGroupCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactListContactMechanismPurpose(UserVisitPK userVisitPK, CreateContactListContactMechanismPurposeForm form) {
        return new CreateContactListContactMechanismPurposeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactListContactMechanismPurposeChoices(UserVisitPK userVisitPK, GetContactListContactMechanismPurposeChoicesForm form) {
        return new GetContactListContactMechanismPurposeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactListContactMechanismPurpose(UserVisitPK userVisitPK, GetContactListContactMechanismPurposeForm form) {
        return new GetContactListContactMechanismPurposeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactListContactMechanismPurposes(UserVisitPK userVisitPK, GetContactListContactMechanismPurposesForm form) {
        return new GetContactListContactMechanismPurposesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultContactListContactMechanismPurpose(UserVisitPK userVisitPK, SetDefaultContactListContactMechanismPurposeForm form) {
        return new SetDefaultContactListContactMechanismPurposeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContactListContactMechanismPurpose(UserVisitPK userVisitPK, EditContactListContactMechanismPurposeForm form) {
        return new EditContactListContactMechanismPurposeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContactListContactMechanismPurpose(UserVisitPK userVisitPK, DeleteContactListContactMechanismPurposeForm form) {
        return new DeleteContactListContactMechanismPurposeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Lists
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyContactList(UserVisitPK userVisitPK, CreatePartyContactListForm form) {
        return new CreatePartyContactListCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyContactLists(UserVisitPK userVisitPK, GetPartyContactListsForm form) {
        return new GetPartyContactListsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyContactList(UserVisitPK userVisitPK, GetPartyContactListForm form) {
        return new GetPartyContactListCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyContactListStatusChoices(UserVisitPK userVisitPK, GetPartyContactListStatusChoicesForm form) {
        return new GetPartyContactListStatusChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setPartyContactListStatus(UserVisitPK userVisitPK, SetPartyContactListStatusForm form) {
        return new SetPartyContactListStatusCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyContactList(UserVisitPK userVisitPK, EditPartyContactListForm form) {
        return new EditPartyContactListCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyContactList(UserVisitPK userVisitPK, DeletePartyContactListForm form) {
        return new DeletePartyContactListCommand().run(userVisitPK, form);
    }
    
}
