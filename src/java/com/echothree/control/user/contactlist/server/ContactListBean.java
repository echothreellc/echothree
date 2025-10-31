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
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateContactListTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListTypes(UserVisitPK userVisitPK, GetContactListTypesForm form) {
        return CDI.current().select(GetContactListTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListType(UserVisitPK userVisitPK, GetContactListTypeForm form) {
        return CDI.current().select(GetContactListTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListTypeChoices(UserVisitPK userVisitPK, GetContactListTypeChoicesForm form) {
        return CDI.current().select(GetContactListTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultContactListType(UserVisitPK userVisitPK, SetDefaultContactListTypeForm form) {
        return CDI.current().select(SetDefaultContactListTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContactListType(UserVisitPK userVisitPK, EditContactListTypeForm form) {
        return CDI.current().select(EditContactListTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactListType(UserVisitPK userVisitPK, DeleteContactListTypeForm form) {
        return CDI.current().select(DeleteContactListTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Contact List Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createContactListTypeDescription(UserVisitPK userVisitPK, CreateContactListTypeDescriptionForm form) {
        return CDI.current().select(CreateContactListTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListTypeDescriptions(UserVisitPK userVisitPK, GetContactListTypeDescriptionsForm form) {
        return CDI.current().select(GetContactListTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListTypeDescription(UserVisitPK userVisitPK, GetContactListTypeDescriptionForm form) {
        return CDI.current().select(GetContactListTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContactListTypeDescription(UserVisitPK userVisitPK, EditContactListTypeDescriptionForm form) {
        return CDI.current().select(EditContactListTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactListTypeDescription(UserVisitPK userVisitPK, DeleteContactListTypeDescriptionForm form) {
        return CDI.current().select(DeleteContactListTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Groups
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactListGroup(UserVisitPK userVisitPK, CreateContactListGroupForm form) {
        return CDI.current().select(CreateContactListGroupCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListGroups(UserVisitPK userVisitPK, GetContactListGroupsForm form) {
        return CDI.current().select(GetContactListGroupsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListGroup(UserVisitPK userVisitPK, GetContactListGroupForm form) {
        return CDI.current().select(GetContactListGroupCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListGroupChoices(UserVisitPK userVisitPK, GetContactListGroupChoicesForm form) {
        return CDI.current().select(GetContactListGroupChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultContactListGroup(UserVisitPK userVisitPK, SetDefaultContactListGroupForm form) {
        return CDI.current().select(SetDefaultContactListGroupCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContactListGroup(UserVisitPK userVisitPK, EditContactListGroupForm form) {
        return CDI.current().select(EditContactListGroupCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactListGroup(UserVisitPK userVisitPK, DeleteContactListGroupForm form) {
        return CDI.current().select(DeleteContactListGroupCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Group Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactListGroupDescription(UserVisitPK userVisitPK, CreateContactListGroupDescriptionForm form) {
        return CDI.current().select(CreateContactListGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListGroupDescriptions(UserVisitPK userVisitPK, GetContactListGroupDescriptionsForm form) {
        return CDI.current().select(GetContactListGroupDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListGroupDescription(UserVisitPK userVisitPK, GetContactListGroupDescriptionForm form) {
        return CDI.current().select(GetContactListGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContactListGroupDescription(UserVisitPK userVisitPK, EditContactListGroupDescriptionForm form) {
        return CDI.current().select(EditContactListGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactListGroupDescription(UserVisitPK userVisitPK, DeleteContactListGroupDescriptionForm form) {
        return CDI.current().select(DeleteContactListGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Frequencies
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactListFrequency(UserVisitPK userVisitPK, CreateContactListFrequencyForm form) {
        return CDI.current().select(CreateContactListFrequencyCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListFrequencies(UserVisitPK userVisitPK, GetContactListFrequenciesForm form) {
        return CDI.current().select(GetContactListFrequenciesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListFrequency(UserVisitPK userVisitPK, GetContactListFrequencyForm form) {
        return CDI.current().select(GetContactListFrequencyCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListFrequencyChoices(UserVisitPK userVisitPK, GetContactListFrequencyChoicesForm form) {
        return CDI.current().select(GetContactListFrequencyChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultContactListFrequency(UserVisitPK userVisitPK, SetDefaultContactListFrequencyForm form) {
        return CDI.current().select(SetDefaultContactListFrequencyCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContactListFrequency(UserVisitPK userVisitPK, EditContactListFrequencyForm form) {
        return CDI.current().select(EditContactListFrequencyCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactListFrequency(UserVisitPK userVisitPK, DeleteContactListFrequencyForm form) {
        return CDI.current().select(DeleteContactListFrequencyCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Frequency Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactListFrequencyDescription(UserVisitPK userVisitPK, CreateContactListFrequencyDescriptionForm form) {
        return CDI.current().select(CreateContactListFrequencyDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListFrequencyDescriptions(UserVisitPK userVisitPK, GetContactListFrequencyDescriptionsForm form) {
        return CDI.current().select(GetContactListFrequencyDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListFrequencyDescription(UserVisitPK userVisitPK, GetContactListFrequencyDescriptionForm form) {
        return CDI.current().select(GetContactListFrequencyDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContactListFrequencyDescription(UserVisitPK userVisitPK, EditContactListFrequencyDescriptionForm form) {
        return CDI.current().select(EditContactListFrequencyDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactListFrequencyDescription(UserVisitPK userVisitPK, DeleteContactListFrequencyDescriptionForm form) {
        return CDI.current().select(DeleteContactListFrequencyDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Contact Lists
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactList(UserVisitPK userVisitPK, CreateContactListForm form) {
        return CDI.current().select(CreateContactListCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactLists(UserVisitPK userVisitPK, GetContactListsForm form) {
        return CDI.current().select(GetContactListsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactList(UserVisitPK userVisitPK, GetContactListForm form) {
        return CDI.current().select(GetContactListCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListChoices(UserVisitPK userVisitPK, GetContactListChoicesForm form) {
        return CDI.current().select(GetContactListChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultContactList(UserVisitPK userVisitPK, SetDefaultContactListForm form) {
        return CDI.current().select(SetDefaultContactListCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContactList(UserVisitPK userVisitPK, EditContactListForm form) {
        return CDI.current().select(EditContactListCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactList(UserVisitPK userVisitPK, DeleteContactListForm form) {
        return CDI.current().select(DeleteContactListCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createContactListDescription(UserVisitPK userVisitPK, CreateContactListDescriptionForm form) {
        return CDI.current().select(CreateContactListDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListDescriptions(UserVisitPK userVisitPK, GetContactListDescriptionsForm form) {
        return CDI.current().select(GetContactListDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactListDescription(UserVisitPK userVisitPK, GetContactListDescriptionForm form) {
        return CDI.current().select(GetContactListDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContactListDescription(UserVisitPK userVisitPK, EditContactListDescriptionForm form) {
        return CDI.current().select(EditContactListDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactListDescription(UserVisitPK userVisitPK, DeleteContactListDescriptionForm form) {
        return CDI.current().select(DeleteContactListDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Type Contact Lists
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeContactList(UserVisitPK userVisitPK, CreatePartyTypeContactListForm form) {
        return CDI.current().select(CreatePartyTypeContactListCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyTypeContactLists(UserVisitPK userVisitPK, GetPartyTypeContactListsForm form) {
        return CDI.current().select(GetPartyTypeContactListsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyTypeContactList(UserVisitPK userVisitPK, GetPartyTypeContactListForm form) {
        return CDI.current().select(GetPartyTypeContactListCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyTypeContactList(UserVisitPK userVisitPK, EditPartyTypeContactListForm form) {
        return CDI.current().select(EditPartyTypeContactListCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyTypeContactList(UserVisitPK userVisitPK, DeletePartyTypeContactListForm form) {
        return CDI.current().select(DeletePartyTypeContactListCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Type Contact List Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyTypeContactListGroup(UserVisitPK userVisitPK, CreatePartyTypeContactListGroupForm form) {
        return CDI.current().select(CreatePartyTypeContactListGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyTypeContactListGroups(UserVisitPK userVisitPK, GetPartyTypeContactListGroupsForm form) {
        return CDI.current().select(GetPartyTypeContactListGroupsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyTypeContactListGroup(UserVisitPK userVisitPK, GetPartyTypeContactListGroupForm form) {
        return CDI.current().select(GetPartyTypeContactListGroupCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyTypeContactListGroup(UserVisitPK userVisitPK, EditPartyTypeContactListGroupForm form) {
        return CDI.current().select(EditPartyTypeContactListGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyTypeContactListGroup(UserVisitPK userVisitPK, DeletePartyTypeContactListGroupForm form) {
        return CDI.current().select(DeletePartyTypeContactListGroupCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Customer Type Contact Lists
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createCustomerTypeContactList(UserVisitPK userVisitPK, CreateCustomerTypeContactListForm form) {
        return CDI.current().select(CreateCustomerTypeContactListCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCustomerTypeContactLists(UserVisitPK userVisitPK, GetCustomerTypeContactListsForm form) {
        return CDI.current().select(GetCustomerTypeContactListsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCustomerTypeContactList(UserVisitPK userVisitPK, GetCustomerTypeContactListForm form) {
        return CDI.current().select(GetCustomerTypeContactListCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editCustomerTypeContactList(UserVisitPK userVisitPK, EditCustomerTypeContactListForm form) {
        return CDI.current().select(EditCustomerTypeContactListCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteCustomerTypeContactList(UserVisitPK userVisitPK, DeleteCustomerTypeContactListForm form) {
        return CDI.current().select(DeleteCustomerTypeContactListCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Customer Type Contact List Groups
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createCustomerTypeContactListGroup(UserVisitPK userVisitPK, CreateCustomerTypeContactListGroupForm form) {
        return CDI.current().select(CreateCustomerTypeContactListGroupCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCustomerTypeContactListGroups(UserVisitPK userVisitPK, GetCustomerTypeContactListGroupsForm form) {
        return CDI.current().select(GetCustomerTypeContactListGroupsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCustomerTypeContactListGroup(UserVisitPK userVisitPK, GetCustomerTypeContactListGroupForm form) {
        return CDI.current().select(GetCustomerTypeContactListGroupCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editCustomerTypeContactListGroup(UserVisitPK userVisitPK, EditCustomerTypeContactListGroupForm form) {
        return CDI.current().select(EditCustomerTypeContactListGroupCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteCustomerTypeContactListGroup(UserVisitPK userVisitPK, DeleteCustomerTypeContactListGroupForm form) {
        return CDI.current().select(DeleteCustomerTypeContactListGroupCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Contact List Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactListContactMechanismPurpose(UserVisitPK userVisitPK, CreateContactListContactMechanismPurposeForm form) {
        return CDI.current().select(CreateContactListContactMechanismPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactListContactMechanismPurposeChoices(UserVisitPK userVisitPK, GetContactListContactMechanismPurposeChoicesForm form) {
        return CDI.current().select(GetContactListContactMechanismPurposeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactListContactMechanismPurpose(UserVisitPK userVisitPK, GetContactListContactMechanismPurposeForm form) {
        return CDI.current().select(GetContactListContactMechanismPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactListContactMechanismPurposes(UserVisitPK userVisitPK, GetContactListContactMechanismPurposesForm form) {
        return CDI.current().select(GetContactListContactMechanismPurposesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultContactListContactMechanismPurpose(UserVisitPK userVisitPK, SetDefaultContactListContactMechanismPurposeForm form) {
        return CDI.current().select(SetDefaultContactListContactMechanismPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContactListContactMechanismPurpose(UserVisitPK userVisitPK, EditContactListContactMechanismPurposeForm form) {
        return CDI.current().select(EditContactListContactMechanismPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContactListContactMechanismPurpose(UserVisitPK userVisitPK, DeleteContactListContactMechanismPurposeForm form) {
        return CDI.current().select(DeleteContactListContactMechanismPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Lists
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyContactList(UserVisitPK userVisitPK, CreatePartyContactListForm form) {
        return CDI.current().select(CreatePartyContactListCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyContactLists(UserVisitPK userVisitPK, GetPartyContactListsForm form) {
        return CDI.current().select(GetPartyContactListsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyContactList(UserVisitPK userVisitPK, GetPartyContactListForm form) {
        return CDI.current().select(GetPartyContactListCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyContactListStatusChoices(UserVisitPK userVisitPK, GetPartyContactListStatusChoicesForm form) {
        return CDI.current().select(GetPartyContactListStatusChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setPartyContactListStatus(UserVisitPK userVisitPK, SetPartyContactListStatusForm form) {
        return CDI.current().select(SetPartyContactListStatusCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyContactList(UserVisitPK userVisitPK, EditPartyContactListForm form) {
        return CDI.current().select(EditPartyContactListCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyContactList(UserVisitPK userVisitPK, DeletePartyContactListForm form) {
        return CDI.current().select(DeletePartyContactListCommand.class).get().run(userVisitPK, form);
    }
    
}
