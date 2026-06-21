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
import com.echothree.control.user.contactlist.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface ContactListService
        extends ContactListForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Contact List Types
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createContactListType(UserVisitPK userVisitPK, CreateContactListTypeForm form);

    CommandResult<GetContactListTypesResult> getContactListTypes(UserVisitPK userVisitPK, GetContactListTypesForm form);

    CommandResult<GetContactListTypeResult> getContactListType(UserVisitPK userVisitPK, GetContactListTypeForm form);

    CommandResult<GetContactListTypeChoicesResult> getContactListTypeChoices(UserVisitPK userVisitPK, GetContactListTypeChoicesForm form);

    CommandResult<VoidResult> setDefaultContactListType(UserVisitPK userVisitPK, SetDefaultContactListTypeForm form);

    CommandResult<EditContactListTypeResult> editContactListType(UserVisitPK userVisitPK, EditContactListTypeForm form);

    CommandResult<VoidResult> deleteContactListType(UserVisitPK userVisitPK, DeleteContactListTypeForm form);

    // -------------------------------------------------------------------------
    //   Contact List Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createContactListTypeDescription(UserVisitPK userVisitPK, CreateContactListTypeDescriptionForm form);

    CommandResult<GetContactListTypeDescriptionsResult> getContactListTypeDescriptions(UserVisitPK userVisitPK, GetContactListTypeDescriptionsForm form);

    CommandResult<GetContactListTypeDescriptionResult> getContactListTypeDescription(UserVisitPK userVisitPK, GetContactListTypeDescriptionForm form);

    CommandResult<EditContactListTypeDescriptionResult> editContactListTypeDescription(UserVisitPK userVisitPK, EditContactListTypeDescriptionForm form);

    CommandResult<VoidResult> deleteContactListTypeDescription(UserVisitPK userVisitPK, DeleteContactListTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Contact List Groups
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createContactListGroup(UserVisitPK userVisitPK, CreateContactListGroupForm form);

    CommandResult<GetContactListGroupsResult> getContactListGroups(UserVisitPK userVisitPK, GetContactListGroupsForm form);

    CommandResult<GetContactListGroupResult> getContactListGroup(UserVisitPK userVisitPK, GetContactListGroupForm form);

    CommandResult<GetContactListGroupChoicesResult> getContactListGroupChoices(UserVisitPK userVisitPK, GetContactListGroupChoicesForm form);

    CommandResult<VoidResult> setDefaultContactListGroup(UserVisitPK userVisitPK, SetDefaultContactListGroupForm form);

    CommandResult<EditContactListGroupResult> editContactListGroup(UserVisitPK userVisitPK, EditContactListGroupForm form);

    CommandResult<VoidResult> deleteContactListGroup(UserVisitPK userVisitPK, DeleteContactListGroupForm form);

    // --------------------------------------------------------------------------------
    //   Contact List Group Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createContactListGroupDescription(UserVisitPK userVisitPK, CreateContactListGroupDescriptionForm form);

    CommandResult<GetContactListGroupDescriptionsResult> getContactListGroupDescriptions(UserVisitPK userVisitPK, GetContactListGroupDescriptionsForm form);

    CommandResult<GetContactListGroupDescriptionResult> getContactListGroupDescription(UserVisitPK userVisitPK, GetContactListGroupDescriptionForm form);

    CommandResult<EditContactListGroupDescriptionResult> editContactListGroupDescription(UserVisitPK userVisitPK, EditContactListGroupDescriptionForm form);

    CommandResult<VoidResult> deleteContactListGroupDescription(UserVisitPK userVisitPK, DeleteContactListGroupDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Contact List Frequencies
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createContactListFrequency(UserVisitPK userVisitPK, CreateContactListFrequencyForm form);

    CommandResult<GetContactListFrequenciesResult> getContactListFrequencies(UserVisitPK userVisitPK, GetContactListFrequenciesForm form);

    CommandResult<GetContactListFrequencyResult> getContactListFrequency(UserVisitPK userVisitPK, GetContactListFrequencyForm form);

    CommandResult<GetContactListFrequencyChoicesResult> getContactListFrequencyChoices(UserVisitPK userVisitPK, GetContactListFrequencyChoicesForm form);

    CommandResult<VoidResult> setDefaultContactListFrequency(UserVisitPK userVisitPK, SetDefaultContactListFrequencyForm form);

    CommandResult<EditContactListFrequencyResult> editContactListFrequency(UserVisitPK userVisitPK, EditContactListFrequencyForm form);

    CommandResult<VoidResult> deleteContactListFrequency(UserVisitPK userVisitPK, DeleteContactListFrequencyForm form);

    // --------------------------------------------------------------------------------
    //   Contact List Frequency Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createContactListFrequencyDescription(UserVisitPK userVisitPK, CreateContactListFrequencyDescriptionForm form);

    CommandResult<GetContactListFrequencyDescriptionsResult> getContactListFrequencyDescriptions(UserVisitPK userVisitPK, GetContactListFrequencyDescriptionsForm form);

    CommandResult<GetContactListFrequencyDescriptionResult> getContactListFrequencyDescription(UserVisitPK userVisitPK, GetContactListFrequencyDescriptionForm form);

    CommandResult<EditContactListFrequencyDescriptionResult> editContactListFrequencyDescription(UserVisitPK userVisitPK, EditContactListFrequencyDescriptionForm form);

    CommandResult<VoidResult> deleteContactListFrequencyDescription(UserVisitPK userVisitPK, DeleteContactListFrequencyDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Contact Lists
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createContactList(UserVisitPK userVisitPK, CreateContactListForm form);

    CommandResult<GetContactListsResult> getContactLists(UserVisitPK userVisitPK, GetContactListsForm form);

    CommandResult<GetContactListResult> getContactList(UserVisitPK userVisitPK, GetContactListForm form);

    CommandResult<GetContactListChoicesResult> getContactListChoices(UserVisitPK userVisitPK, GetContactListChoicesForm form);

    CommandResult<VoidResult> setDefaultContactList(UserVisitPK userVisitPK, SetDefaultContactListForm form);

    CommandResult<EditContactListResult> editContactList(UserVisitPK userVisitPK, EditContactListForm form);

    CommandResult<VoidResult> deleteContactList(UserVisitPK userVisitPK, DeleteContactListForm form);

    // --------------------------------------------------------------------------------
    //   Contact List Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createContactListDescription(UserVisitPK userVisitPK, CreateContactListDescriptionForm form);

    CommandResult<GetContactListDescriptionsResult> getContactListDescriptions(UserVisitPK userVisitPK, GetContactListDescriptionsForm form);

    CommandResult<GetContactListDescriptionResult> getContactListDescription(UserVisitPK userVisitPK, GetContactListDescriptionForm form);

    CommandResult<EditContactListDescriptionResult> editContactListDescription(UserVisitPK userVisitPK, EditContactListDescriptionForm form);

    CommandResult<VoidResult> deleteContactListDescription(UserVisitPK userVisitPK, DeleteContactListDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Party Type Contact Lists
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyTypeContactList(UserVisitPK userVisitPK, CreatePartyTypeContactListForm form);
    
    CommandResult<GetPartyTypeContactListsResult> getPartyTypeContactLists(UserVisitPK userVisitPK, GetPartyTypeContactListsForm form);

    CommandResult<GetPartyTypeContactListResult> getPartyTypeContactList(UserVisitPK userVisitPK, GetPartyTypeContactListForm form);

    CommandResult<EditPartyTypeContactListResult> editPartyTypeContactList(UserVisitPK userVisitPK, EditPartyTypeContactListForm form);
    
    CommandResult<VoidResult> deletePartyTypeContactList(UserVisitPK userVisitPK, DeletePartyTypeContactListForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Type Contact List Groups
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyTypeContactListGroup(UserVisitPK userVisitPK, CreatePartyTypeContactListGroupForm form);
    
    CommandResult<GetPartyTypeContactListGroupsResult> getPartyTypeContactListGroups(UserVisitPK userVisitPK, GetPartyTypeContactListGroupsForm form);

    CommandResult<GetPartyTypeContactListGroupResult> getPartyTypeContactListGroup(UserVisitPK userVisitPK, GetPartyTypeContactListGroupForm form);

    CommandResult<EditPartyTypeContactListGroupResult> editPartyTypeContactListGroup(UserVisitPK userVisitPK, EditPartyTypeContactListGroupForm form);
    
    CommandResult<VoidResult> deletePartyTypeContactListGroup(UserVisitPK userVisitPK, DeletePartyTypeContactListGroupForm form);
    
    // --------------------------------------------------------------------------------
    //   Customer Type Contact Lists
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createCustomerTypeContactList(UserVisitPK userVisitPK, CreateCustomerTypeContactListForm form);

    CommandResult<GetCustomerTypeContactListsResult> getCustomerTypeContactLists(UserVisitPK userVisitPK, GetCustomerTypeContactListsForm form);

    CommandResult<GetCustomerTypeContactListResult> getCustomerTypeContactList(UserVisitPK userVisitPK, GetCustomerTypeContactListForm form);

    CommandResult<EditCustomerTypeContactListResult> editCustomerTypeContactList(UserVisitPK userVisitPK, EditCustomerTypeContactListForm form);

    CommandResult<VoidResult> deleteCustomerTypeContactList(UserVisitPK userVisitPK, DeleteCustomerTypeContactListForm form);

    // --------------------------------------------------------------------------------
    //   Customer Type Contact List Groups
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createCustomerTypeContactListGroup(UserVisitPK userVisitPK, CreateCustomerTypeContactListGroupForm form);

    CommandResult<GetCustomerTypeContactListGroupsResult> getCustomerTypeContactListGroups(UserVisitPK userVisitPK, GetCustomerTypeContactListGroupsForm form);

    CommandResult<GetCustomerTypeContactListGroupResult> getCustomerTypeContactListGroup(UserVisitPK userVisitPK, GetCustomerTypeContactListGroupForm form);

    CommandResult<EditCustomerTypeContactListGroupResult> editCustomerTypeContactListGroup(UserVisitPK userVisitPK, EditCustomerTypeContactListGroupForm form);

    CommandResult<VoidResult> deleteCustomerTypeContactListGroup(UserVisitPK userVisitPK, DeleteCustomerTypeContactListGroupForm form);

    // --------------------------------------------------------------------------------
    //   Contact List Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContactListContactMechanismPurpose(UserVisitPK userVisitPK, CreateContactListContactMechanismPurposeForm form);
    
    CommandResult<GetContactListContactMechanismPurposeChoicesResult> getContactListContactMechanismPurposeChoices(UserVisitPK userVisitPK, GetContactListContactMechanismPurposeChoicesForm form);
    
    CommandResult<GetContactListContactMechanismPurposeResult> getContactListContactMechanismPurpose(UserVisitPK userVisitPK, GetContactListContactMechanismPurposeForm form);
    
    CommandResult<GetContactListContactMechanismPurposesResult> getContactListContactMechanismPurposes(UserVisitPK userVisitPK, GetContactListContactMechanismPurposesForm form);
    
    CommandResult<VoidResult> setDefaultContactListContactMechanismPurpose(UserVisitPK userVisitPK, SetDefaultContactListContactMechanismPurposeForm form);
    
    CommandResult<EditContactListContactMechanismPurposeResult> editContactListContactMechanismPurpose(UserVisitPK userVisitPK, EditContactListContactMechanismPurposeForm form);
    
    CommandResult<VoidResult> deleteContactListContactMechanismPurpose(UserVisitPK userVisitPK, DeleteContactListContactMechanismPurposeForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Contact Lists
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyContactList(UserVisitPK userVisitPK, CreatePartyContactListForm form);

    CommandResult<GetPartyContactListsResult> getPartyContactLists(UserVisitPK userVisitPK, GetPartyContactListsForm form);

    CommandResult<GetPartyContactListResult> getPartyContactList(UserVisitPK userVisitPK, GetPartyContactListForm form);

    CommandResult<GetPartyContactListStatusChoicesResult> getPartyContactListStatusChoices(UserVisitPK userVisitPK, GetPartyContactListStatusChoicesForm form);

    CommandResult<VoidResult> setPartyContactListStatus(UserVisitPK userVisitPK, SetPartyContactListStatusForm form);

    CommandResult<EditPartyContactListResult> editPartyContactList(UserVisitPK userVisitPK, EditPartyContactListForm form);
    
    CommandResult<VoidResult> deletePartyContactList(UserVisitPK userVisitPK, DeletePartyContactListForm form);
    
}
