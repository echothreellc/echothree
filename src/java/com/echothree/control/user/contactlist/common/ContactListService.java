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

package com.echothree.control.user.contactlist.common;

import com.echothree.control.user.contactlist.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface ContactListService
        extends ContactListForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Contact List Types
    // -------------------------------------------------------------------------

    CommandResult createContactListType(UserVisitPK userVisitPK, CreateContactListTypeForm form);

    CommandResult getContactListTypes(UserVisitPK userVisitPK, GetContactListTypesForm form);

    CommandResult getContactListType(UserVisitPK userVisitPK, GetContactListTypeForm form);

    CommandResult getContactListTypeChoices(UserVisitPK userVisitPK, GetContactListTypeChoicesForm form);

    CommandResult setDefaultContactListType(UserVisitPK userVisitPK, SetDefaultContactListTypeForm form);

    CommandResult editContactListType(UserVisitPK userVisitPK, EditContactListTypeForm form);

    CommandResult deleteContactListType(UserVisitPK userVisitPK, DeleteContactListTypeForm form);

    // -------------------------------------------------------------------------
    //   Contact List Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createContactListTypeDescription(UserVisitPK userVisitPK, CreateContactListTypeDescriptionForm form);

    CommandResult getContactListTypeDescriptions(UserVisitPK userVisitPK, GetContactListTypeDescriptionsForm form);

    CommandResult getContactListTypeDescription(UserVisitPK userVisitPK, GetContactListTypeDescriptionForm form);

    CommandResult editContactListTypeDescription(UserVisitPK userVisitPK, EditContactListTypeDescriptionForm form);

    CommandResult deleteContactListTypeDescription(UserVisitPK userVisitPK, DeleteContactListTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Contact List Groups
    // --------------------------------------------------------------------------------

    CommandResult createContactListGroup(UserVisitPK userVisitPK, CreateContactListGroupForm form);

    CommandResult getContactListGroups(UserVisitPK userVisitPK, GetContactListGroupsForm form);

    CommandResult getContactListGroup(UserVisitPK userVisitPK, GetContactListGroupForm form);

    CommandResult getContactListGroupChoices(UserVisitPK userVisitPK, GetContactListGroupChoicesForm form);

    CommandResult setDefaultContactListGroup(UserVisitPK userVisitPK, SetDefaultContactListGroupForm form);

    CommandResult editContactListGroup(UserVisitPK userVisitPK, EditContactListGroupForm form);

    CommandResult deleteContactListGroup(UserVisitPK userVisitPK, DeleteContactListGroupForm form);

    // --------------------------------------------------------------------------------
    //   Contact List Group Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createContactListGroupDescription(UserVisitPK userVisitPK, CreateContactListGroupDescriptionForm form);

    CommandResult getContactListGroupDescriptions(UserVisitPK userVisitPK, GetContactListGroupDescriptionsForm form);

    CommandResult getContactListGroupDescription(UserVisitPK userVisitPK, GetContactListGroupDescriptionForm form);

    CommandResult editContactListGroupDescription(UserVisitPK userVisitPK, EditContactListGroupDescriptionForm form);

    CommandResult deleteContactListGroupDescription(UserVisitPK userVisitPK, DeleteContactListGroupDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Contact List Frequencies
    // --------------------------------------------------------------------------------

    CommandResult createContactListFrequency(UserVisitPK userVisitPK, CreateContactListFrequencyForm form);

    CommandResult getContactListFrequencies(UserVisitPK userVisitPK, GetContactListFrequenciesForm form);

    CommandResult getContactListFrequency(UserVisitPK userVisitPK, GetContactListFrequencyForm form);

    CommandResult getContactListFrequencyChoices(UserVisitPK userVisitPK, GetContactListFrequencyChoicesForm form);

    CommandResult setDefaultContactListFrequency(UserVisitPK userVisitPK, SetDefaultContactListFrequencyForm form);

    CommandResult editContactListFrequency(UserVisitPK userVisitPK, EditContactListFrequencyForm form);

    CommandResult deleteContactListFrequency(UserVisitPK userVisitPK, DeleteContactListFrequencyForm form);

    // --------------------------------------------------------------------------------
    //   Contact List Frequency Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createContactListFrequencyDescription(UserVisitPK userVisitPK, CreateContactListFrequencyDescriptionForm form);

    CommandResult getContactListFrequencyDescriptions(UserVisitPK userVisitPK, GetContactListFrequencyDescriptionsForm form);

    CommandResult getContactListFrequencyDescription(UserVisitPK userVisitPK, GetContactListFrequencyDescriptionForm form);

    CommandResult editContactListFrequencyDescription(UserVisitPK userVisitPK, EditContactListFrequencyDescriptionForm form);

    CommandResult deleteContactListFrequencyDescription(UserVisitPK userVisitPK, DeleteContactListFrequencyDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Contact Lists
    // --------------------------------------------------------------------------------

    CommandResult createContactList(UserVisitPK userVisitPK, CreateContactListForm form);

    CommandResult getContactLists(UserVisitPK userVisitPK, GetContactListsForm form);

    CommandResult getContactList(UserVisitPK userVisitPK, GetContactListForm form);

    CommandResult getContactListChoices(UserVisitPK userVisitPK, GetContactListChoicesForm form);

    CommandResult setDefaultContactList(UserVisitPK userVisitPK, SetDefaultContactListForm form);

    CommandResult editContactList(UserVisitPK userVisitPK, EditContactListForm form);

    CommandResult deleteContactList(UserVisitPK userVisitPK, DeleteContactListForm form);

    // --------------------------------------------------------------------------------
    //   Contact List Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createContactListDescription(UserVisitPK userVisitPK, CreateContactListDescriptionForm form);

    CommandResult getContactListDescriptions(UserVisitPK userVisitPK, GetContactListDescriptionsForm form);

    CommandResult getContactListDescription(UserVisitPK userVisitPK, GetContactListDescriptionForm form);

    CommandResult editContactListDescription(UserVisitPK userVisitPK, EditContactListDescriptionForm form);

    CommandResult deleteContactListDescription(UserVisitPK userVisitPK, DeleteContactListDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Party Type Contact Lists
    // --------------------------------------------------------------------------------
    
    CommandResult createPartyTypeContactList(UserVisitPK userVisitPK, CreatePartyTypeContactListForm form);
    
    CommandResult getPartyTypeContactLists(UserVisitPK userVisitPK, GetPartyTypeContactListsForm form);

    CommandResult getPartyTypeContactList(UserVisitPK userVisitPK, GetPartyTypeContactListForm form);

    CommandResult editPartyTypeContactList(UserVisitPK userVisitPK, EditPartyTypeContactListForm form);
    
    CommandResult deletePartyTypeContactList(UserVisitPK userVisitPK, DeletePartyTypeContactListForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Type Contact List Groups
    // --------------------------------------------------------------------------------
    
    CommandResult createPartyTypeContactListGroup(UserVisitPK userVisitPK, CreatePartyTypeContactListGroupForm form);
    
    CommandResult getPartyTypeContactListGroups(UserVisitPK userVisitPK, GetPartyTypeContactListGroupsForm form);

    CommandResult getPartyTypeContactListGroup(UserVisitPK userVisitPK, GetPartyTypeContactListGroupForm form);

    CommandResult editPartyTypeContactListGroup(UserVisitPK userVisitPK, EditPartyTypeContactListGroupForm form);
    
    CommandResult deletePartyTypeContactListGroup(UserVisitPK userVisitPK, DeletePartyTypeContactListGroupForm form);
    
    // --------------------------------------------------------------------------------
    //   Customer Type Contact Lists
    // --------------------------------------------------------------------------------

    CommandResult createCustomerTypeContactList(UserVisitPK userVisitPK, CreateCustomerTypeContactListForm form);

    CommandResult getCustomerTypeContactLists(UserVisitPK userVisitPK, GetCustomerTypeContactListsForm form);

    CommandResult getCustomerTypeContactList(UserVisitPK userVisitPK, GetCustomerTypeContactListForm form);

    CommandResult editCustomerTypeContactList(UserVisitPK userVisitPK, EditCustomerTypeContactListForm form);

    CommandResult deleteCustomerTypeContactList(UserVisitPK userVisitPK, DeleteCustomerTypeContactListForm form);

    // --------------------------------------------------------------------------------
    //   Customer Type Contact List Groups
    // --------------------------------------------------------------------------------

    CommandResult createCustomerTypeContactListGroup(UserVisitPK userVisitPK, CreateCustomerTypeContactListGroupForm form);

    CommandResult getCustomerTypeContactListGroups(UserVisitPK userVisitPK, GetCustomerTypeContactListGroupsForm form);

    CommandResult getCustomerTypeContactListGroup(UserVisitPK userVisitPK, GetCustomerTypeContactListGroupForm form);

    CommandResult editCustomerTypeContactListGroup(UserVisitPK userVisitPK, EditCustomerTypeContactListGroupForm form);

    CommandResult deleteCustomerTypeContactListGroup(UserVisitPK userVisitPK, DeleteCustomerTypeContactListGroupForm form);

    // --------------------------------------------------------------------------------
    //   Contact List Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    CommandResult createContactListContactMechanismPurpose(UserVisitPK userVisitPK, CreateContactListContactMechanismPurposeForm form);
    
    CommandResult getContactListContactMechanismPurposeChoices(UserVisitPK userVisitPK, GetContactListContactMechanismPurposeChoicesForm form);
    
    CommandResult getContactListContactMechanismPurpose(UserVisitPK userVisitPK, GetContactListContactMechanismPurposeForm form);
    
    CommandResult getContactListContactMechanismPurposes(UserVisitPK userVisitPK, GetContactListContactMechanismPurposesForm form);
    
    CommandResult setDefaultContactListContactMechanismPurpose(UserVisitPK userVisitPK, SetDefaultContactListContactMechanismPurposeForm form);
    
    CommandResult editContactListContactMechanismPurpose(UserVisitPK userVisitPK, EditContactListContactMechanismPurposeForm form);
    
    CommandResult deleteContactListContactMechanismPurpose(UserVisitPK userVisitPK, DeleteContactListContactMechanismPurposeForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Contact Lists
    // --------------------------------------------------------------------------------
    
    CommandResult createPartyContactList(UserVisitPK userVisitPK, CreatePartyContactListForm form);

    CommandResult getPartyContactLists(UserVisitPK userVisitPK, GetPartyContactListsForm form);

    CommandResult getPartyContactList(UserVisitPK userVisitPK, GetPartyContactListForm form);

    CommandResult getPartyContactListStatusChoices(UserVisitPK userVisitPK, GetPartyContactListStatusChoicesForm form);

    CommandResult setPartyContactListStatus(UserVisitPK userVisitPK, SetPartyContactListStatusForm form);

    CommandResult editPartyContactList(UserVisitPK userVisitPK, EditPartyContactListForm form);
    
    CommandResult deletePartyContactList(UserVisitPK userVisitPK, DeletePartyContactListForm form);
    
}
