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

package com.echothree.control.user.contact.common;

import com.echothree.control.user.contact.common.form.*;
import com.echothree.control.user.contact.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface ContactService
        extends ContactForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContactMechanismType(UserVisitPK userVisitPK, CreateContactMechanismTypeForm form);
    
    CommandResult<GetContactMechanismTypesResult> getContactMechanismTypes(UserVisitPK userVisitPK, GetContactMechanismTypesForm form);
    
    CommandResult<GetContactMechanismTypeChoicesResult> getContactMechanismTypeChoices(UserVisitPK userVisitPK, GetContactMechanismTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContactMechanismTypeDescription(UserVisitPK userVisitPK, CreateContactMechanismTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Alias Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContactMechanismAliasType(UserVisitPK userVisitPK, CreateContactMechanismAliasTypeForm form);
    
    CommandResult<GetContactMechanismAliasTypesResult> getContactMechanismAliasTypes(UserVisitPK userVisitPK, GetContactMechanismAliasTypesForm form);
    
    CommandResult<GetContactMechanismAliasTypeChoicesResult> getContactMechanismAliasTypeChoices(UserVisitPK userVisitPK, GetContactMechanismAliasTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContactMechanismAliasTypeDescription(UserVisitPK userVisitPK, CreateContactMechanismAliasTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContactMechanismPurpose(UserVisitPK userVisitPK, CreateContactMechanismPurposeForm form);

    CommandResult<GetContactMechanismPurposeResult> getContactMechanismPurpose(UserVisitPK userVisitPK, GetContactMechanismPurposeForm form);

    CommandResult<GetContactMechanismPurposesResult> getContactMechanismPurposes(UserVisitPK userVisitPK, GetContactMechanismPurposesForm form);

    CommandResult<GetContactMechanismPurposeChoicesResult> getContactMechanismPurposeChoices(UserVisitPK userVisitPK, GetContactMechanismPurposeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Purpose Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContactMechanismPurposeDescription(UserVisitPK userVisitPK, CreateContactMechanismPurposeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanisms
    // --------------------------------------------------------------------------------
    
    CommandResult<GetContactMechanismChoicesResult> getContactMechanismChoices(UserVisitPK userVisitPK, GetContactMechanismChoicesForm form);
    
    CommandResult<GetEmailAddressStatusChoicesResult> getEmailAddressStatusChoices(UserVisitPK userVisitPK, GetEmailAddressStatusChoicesForm form);
    
    CommandResult<VoidResult> setEmailAddressStatus(UserVisitPK userVisitPK, SetEmailAddressStatusForm form);

    CommandResult<GetEmailAddressVerificationChoicesResult> getEmailAddressVerificationChoices(UserVisitPK userVisitPK, GetEmailAddressVerificationChoicesForm form);
    
    CommandResult<VoidResult> setEmailAddressVerification(UserVisitPK userVisitPK, SetEmailAddressVerificationForm form);

    CommandResult<GetPostalAddressStatusChoicesResult> getPostalAddressStatusChoices(UserVisitPK userVisitPK, GetPostalAddressStatusChoicesForm form);
    
    CommandResult<VoidResult> setPostalAddressStatus(UserVisitPK userVisitPK, SetPostalAddressStatusForm form);

    CommandResult<GetTelephoneStatusChoicesResult> getTelephoneStatusChoices(UserVisitPK userVisitPK, GetTelephoneStatusChoicesForm form);

    CommandResult<VoidResult> setTelephoneStatus(UserVisitPK userVisitPK, SetTelephoneStatusForm form);

    CommandResult<GetWebAddressStatusChoicesResult> getWebAddressStatusChoices(UserVisitPK userVisitPK, GetWebAddressStatusChoicesForm form);

    CommandResult<VoidResult> setWebAddressStatus(UserVisitPK userVisitPK, SetWebAddressStatusForm form);

    CommandResult<GetContactMechanismResult> getContactMechanism(UserVisitPK userVisitPK, GetContactMechanismForm form);

    CommandResult<GetContactMechanismsResult> getContactMechanisms(UserVisitPK userVisitPK, GetContactMechanismsForm form);

    CommandResult<VoidResult> deleteContactMechanism(UserVisitPK userVisitPK, DeleteContactMechanismForm form);

    // --------------------------------------------------------------------------------
    //   Party Contact Mechanisms
    // --------------------------------------------------------------------------------

    CommandResult<GetPartyContactMechanismResult> getPartyContactMechanism(UserVisitPK userVisitPK, GetPartyContactMechanismForm form);

    CommandResult<GetPartyContactMechanismsResult> getPartyContactMechanisms(UserVisitPK userVisitPK, GetPartyContactMechanismsForm form);

    // --------------------------------------------------------------------------------
    //   Contact Mechanism Aliases
    // --------------------------------------------------------------------------------

    CommandResult<VoidResult> createContactMechanismAlias(UserVisitPK userVisitPK, CreateContactMechanismAliasForm form);
    
    CommandResult<VoidResult> deleteContactMechanismAlias(UserVisitPK userVisitPK, DeleteContactMechanismAliasForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Aliases
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyContactMechanismAlias(UserVisitPK userVisitPK, CreatePartyContactMechanismAliasForm form);
    
    CommandResult<VoidResult> deletePartyContactMechanismAlias(UserVisitPK userVisitPK, DeletePartyContactMechanismAliasForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Email Addresses
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateContactEmailAddressResult> createContactEmailAddress(UserVisitPK userVisitPK, CreateContactEmailAddressForm form);
    
    CommandResult<EditContactEmailAddressResult> editContactEmailAddress(UserVisitPK userVisitPK, EditContactEmailAddressForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Postal Addresses
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateContactPostalAddressResult> createContactPostalAddress(UserVisitPK userVisitPK, CreateContactPostalAddressForm form);
    
    CommandResult<EditContactPostalAddressResult> editContactPostalAddress(UserVisitPK userVisitPK, EditContactPostalAddressForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Telephones
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateContactTelephoneResult> createContactTelephone(UserVisitPK userVisitPK, CreateContactTelephoneForm form);
    
    CommandResult<EditContactTelephoneResult> editContactTelephone(UserVisitPK userVisitPK, EditContactTelephoneForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Web Addresses
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateContactWebAddressResult> createContactWebAddress(UserVisitPK userVisitPK, CreateContactWebAddressForm form);
    
    CommandResult<EditContactWebAddressResult> editContactWebAddress(UserVisitPK userVisitPK, EditContactWebAddressForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyContactMechanismPurpose(UserVisitPK userVisitPK, CreatePartyContactMechanismPurposeForm form);
    
    CommandResult<VoidResult> setDefaultPartyContactMechanismPurpose(UserVisitPK userVisitPK, SetDefaultPartyContactMechanismPurposeForm form);
    
    CommandResult<VoidResult> deletePartyContactMechanismPurpose(UserVisitPK userVisitPK, DeletePartyContactMechanismPurposeForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Relationships
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPartyContactMechanismRelationship(UserVisitPK userVisitPK, CreatePartyContactMechanismRelationshipForm form);
    
    CommandResult<VoidResult> deletePartyContactMechanismRelationship(UserVisitPK userVisitPK, DeletePartyContactMechanismRelationshipForm form);
    
    // --------------------------------------------------------------------------------
    //   Postal Address Element Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPostalAddressElementType(UserVisitPK userVisitPK, CreatePostalAddressElementTypeForm form);
    
    CommandResult<GetPostalAddressElementTypeChoicesResult> getPostalAddressElementTypeChoices(UserVisitPK userVisitPK, GetPostalAddressElementTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Postal Address Element Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPostalAddressElementTypeDescription(UserVisitPK userVisitPK, CreatePostalAddressElementTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Postal Address Formats
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPostalAddressFormat(UserVisitPK userVisitPK, CreatePostalAddressFormatForm form);
    
    CommandResult<GetPostalAddressFormatResult> getPostalAddressFormat(UserVisitPK userVisitPK, GetPostalAddressFormatForm form);
    
    CommandResult<GetPostalAddressFormatsResult> getPostalAddressFormats(UserVisitPK userVisitPK, GetPostalAddressFormatsForm form);
    
    CommandResult<GetPostalAddressFormatChoicesResult> getPostalAddressFormatChoices(UserVisitPK userVisitPK, GetPostalAddressFormatChoicesForm form);
    
    CommandResult<VoidResult> setDefaultPostalAddressFormat(UserVisitPK userVisitPK, SetDefaultPostalAddressFormatForm form);
    
    CommandResult<EditPostalAddressFormatResult> editPostalAddressFormat(UserVisitPK userVisitPK, EditPostalAddressFormatForm form);
    
    CommandResult<VoidResult> deletePostalAddressFormat(UserVisitPK userVisitPK, DeletePostalAddressFormatForm form);
    
    // -------------------------------------------------------------------------
    //   Postal Address Format Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPostalAddressFormatDescription(UserVisitPK userVisitPK, CreatePostalAddressFormatDescriptionForm form);
    
    CommandResult<GetPostalAddressFormatDescriptionsResult> getPostalAddressFormatDescriptions(UserVisitPK userVisitPK, GetPostalAddressFormatDescriptionsForm form);
    
    CommandResult<EditPostalAddressFormatDescriptionResult> editPostalAddressFormatDescription(UserVisitPK userVisitPK, EditPostalAddressFormatDescriptionForm form);
    
    CommandResult<VoidResult> deletePostalAddressFormatDescription(UserVisitPK userVisitPK, DeletePostalAddressFormatDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Postal Address Lines
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPostalAddressLine(UserVisitPK userVisitPK, CreatePostalAddressLineForm form);
    
    CommandResult<GetPostalAddressLinesResult> getPostalAddressLines(UserVisitPK userVisitPK, GetPostalAddressLinesForm form);
    
    CommandResult<EditPostalAddressLineResult> editPostalAddressLine(UserVisitPK userVisitPK, EditPostalAddressLineForm form);
    
    CommandResult<VoidResult> deletePostalAddressLine(UserVisitPK userVisitPK, DeletePostalAddressLineForm form);
    
    // --------------------------------------------------------------------------------
    //   Postal Address Line Elements
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPostalAddressLineElement(UserVisitPK userVisitPK, CreatePostalAddressLineElementForm form);
    
    CommandResult<GetPostalAddressLineElementsResult> getPostalAddressLineElements(UserVisitPK userVisitPK, GetPostalAddressLineElementsForm form);
    
    CommandResult<EditPostalAddressLineElementResult> editPostalAddressLineElement(UserVisitPK userVisitPK, EditPostalAddressLineElementForm form);
    
    CommandResult<VoidResult> deletePostalAddressLineElement(UserVisitPK userVisitPK, DeletePostalAddressLineElementForm form);
    
}
