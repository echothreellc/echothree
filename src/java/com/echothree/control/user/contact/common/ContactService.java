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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface ContactService
        extends ContactForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Types
    // --------------------------------------------------------------------------------
    
    CommandResult createContactMechanismType(UserVisitPK userVisitPK, CreateContactMechanismTypeForm form);
    
    CommandResult getContactMechanismTypes(UserVisitPK userVisitPK, GetContactMechanismTypesForm form);
    
    CommandResult getContactMechanismTypeChoices(UserVisitPK userVisitPK, GetContactMechanismTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createContactMechanismTypeDescription(UserVisitPK userVisitPK, CreateContactMechanismTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Alias Types
    // --------------------------------------------------------------------------------
    
    CommandResult createContactMechanismAliasType(UserVisitPK userVisitPK, CreateContactMechanismAliasTypeForm form);
    
    CommandResult getContactMechanismAliasTypes(UserVisitPK userVisitPK, GetContactMechanismAliasTypesForm form);
    
    CommandResult getContactMechanismAliasTypeChoices(UserVisitPK userVisitPK, GetContactMechanismAliasTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createContactMechanismAliasTypeDescription(UserVisitPK userVisitPK, CreateContactMechanismAliasTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    CommandResult createContactMechanismPurpose(UserVisitPK userVisitPK, CreateContactMechanismPurposeForm form);

    CommandResult getContactMechanismPurpose(UserVisitPK userVisitPK, GetContactMechanismPurposeForm form);

    CommandResult getContactMechanismPurposes(UserVisitPK userVisitPK, GetContactMechanismPurposesForm form);

    CommandResult getContactMechanismPurposeChoices(UserVisitPK userVisitPK, GetContactMechanismPurposeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Purpose Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createContactMechanismPurposeDescription(UserVisitPK userVisitPK, CreateContactMechanismPurposeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanisms
    // --------------------------------------------------------------------------------
    
    CommandResult getContactMechanismChoices(UserVisitPK userVisitPK, GetContactMechanismChoicesForm form);
    
    CommandResult getEmailAddressStatusChoices(UserVisitPK userVisitPK, GetEmailAddressStatusChoicesForm form);
    
    CommandResult setEmailAddressStatus(UserVisitPK userVisitPK, SetEmailAddressStatusForm form);

    CommandResult getEmailAddressVerificationChoices(UserVisitPK userVisitPK, GetEmailAddressVerificationChoicesForm form);
    
    CommandResult setEmailAddressVerification(UserVisitPK userVisitPK, SetEmailAddressVerificationForm form);

    CommandResult getPostalAddressStatusChoices(UserVisitPK userVisitPK, GetPostalAddressStatusChoicesForm form);
    
    CommandResult setPostalAddressStatus(UserVisitPK userVisitPK, SetPostalAddressStatusForm form);

    CommandResult getTelephoneStatusChoices(UserVisitPK userVisitPK, GetTelephoneStatusChoicesForm form);

    CommandResult setTelephoneStatus(UserVisitPK userVisitPK, SetTelephoneStatusForm form);

    CommandResult getWebAddressStatusChoices(UserVisitPK userVisitPK, GetWebAddressStatusChoicesForm form);

    CommandResult setWebAddressStatus(UserVisitPK userVisitPK, SetWebAddressStatusForm form);

    CommandResult getContactMechanism(UserVisitPK userVisitPK, GetContactMechanismForm form);

    CommandResult deleteContactMechanism(UserVisitPK userVisitPK, DeleteContactMechanismForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Aliases
    // --------------------------------------------------------------------------------
    
    CommandResult createContactMechanismAlias(UserVisitPK userVisitPK, CreateContactMechanismAliasForm form);
    
    CommandResult deleteContactMechanismAlias(UserVisitPK userVisitPK, DeleteContactMechanismAliasForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Aliases
    // --------------------------------------------------------------------------------
    
    CommandResult createPartyContactMechanismAlias(UserVisitPK userVisitPK, CreatePartyContactMechanismAliasForm form);
    
    CommandResult deletePartyContactMechanismAlias(UserVisitPK userVisitPK, DeletePartyContactMechanismAliasForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Email Addresses
    // --------------------------------------------------------------------------------
    
    CommandResult createContactEmailAddress(UserVisitPK userVisitPK, CreateContactEmailAddressForm form);
    
    CommandResult editContactEmailAddress(UserVisitPK userVisitPK, EditContactEmailAddressForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Postal Addresses
    // --------------------------------------------------------------------------------
    
    CommandResult createContactPostalAddress(UserVisitPK userVisitPK, CreateContactPostalAddressForm form);
    
    CommandResult editContactPostalAddress(UserVisitPK userVisitPK, EditContactPostalAddressForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Telephones
    // --------------------------------------------------------------------------------
    
    CommandResult createContactTelephone(UserVisitPK userVisitPK, CreateContactTelephoneForm form);
    
    CommandResult editContactTelephone(UserVisitPK userVisitPK, EditContactTelephoneForm form);
    
    // --------------------------------------------------------------------------------
    //   Contact Web Addresses
    // --------------------------------------------------------------------------------
    
    CommandResult createContactWebAddress(UserVisitPK userVisitPK, CreateContactWebAddressForm form);
    
    CommandResult editContactWebAddress(UserVisitPK userVisitPK, EditContactWebAddressForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    CommandResult createPartyContactMechanismPurpose(UserVisitPK userVisitPK, CreatePartyContactMechanismPurposeForm form);
    
    CommandResult setDefaultPartyContactMechanismPurpose(UserVisitPK userVisitPK, SetDefaultPartyContactMechanismPurposeForm form);
    
    CommandResult deletePartyContactMechanismPurpose(UserVisitPK userVisitPK, DeletePartyContactMechanismPurposeForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Relationships
    // --------------------------------------------------------------------------------
    
    CommandResult createPartyContactMechanismRelationship(UserVisitPK userVisitPK, CreatePartyContactMechanismRelationshipForm form);
    
    CommandResult deletePartyContactMechanismRelationship(UserVisitPK userVisitPK, DeletePartyContactMechanismRelationshipForm form);
    
    // --------------------------------------------------------------------------------
    //   Postal Address Element Types
    // --------------------------------------------------------------------------------
    
    CommandResult createPostalAddressElementType(UserVisitPK userVisitPK, CreatePostalAddressElementTypeForm form);
    
    CommandResult getPostalAddressElementTypeChoices(UserVisitPK userVisitPK, GetPostalAddressElementTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Postal Address Element Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createPostalAddressElementTypeDescription(UserVisitPK userVisitPK, CreatePostalAddressElementTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Postal Address Formats
    // -------------------------------------------------------------------------
    
    CommandResult createPostalAddressFormat(UserVisitPK userVisitPK, CreatePostalAddressFormatForm form);
    
    CommandResult getPostalAddressFormat(UserVisitPK userVisitPK, GetPostalAddressFormatForm form);
    
    CommandResult getPostalAddressFormats(UserVisitPK userVisitPK, GetPostalAddressFormatsForm form);
    
    CommandResult getPostalAddressFormatChoices(UserVisitPK userVisitPK, GetPostalAddressFormatChoicesForm form);
    
    CommandResult setDefaultPostalAddressFormat(UserVisitPK userVisitPK, SetDefaultPostalAddressFormatForm form);
    
    CommandResult editPostalAddressFormat(UserVisitPK userVisitPK, EditPostalAddressFormatForm form);
    
    CommandResult deletePostalAddressFormat(UserVisitPK userVisitPK, DeletePostalAddressFormatForm form);
    
    // -------------------------------------------------------------------------
    //   Postal Address Format Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createPostalAddressFormatDescription(UserVisitPK userVisitPK, CreatePostalAddressFormatDescriptionForm form);
    
    CommandResult getPostalAddressFormatDescriptions(UserVisitPK userVisitPK, GetPostalAddressFormatDescriptionsForm form);
    
    CommandResult editPostalAddressFormatDescription(UserVisitPK userVisitPK, EditPostalAddressFormatDescriptionForm form);
    
    CommandResult deletePostalAddressFormatDescription(UserVisitPK userVisitPK, DeletePostalAddressFormatDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Postal Address Lines
    // --------------------------------------------------------------------------------
    
    CommandResult createPostalAddressLine(UserVisitPK userVisitPK, CreatePostalAddressLineForm form);
    
    CommandResult getPostalAddressLines(UserVisitPK userVisitPK, GetPostalAddressLinesForm form);
    
    CommandResult editPostalAddressLine(UserVisitPK userVisitPK, EditPostalAddressLineForm form);
    
    CommandResult deletePostalAddressLine(UserVisitPK userVisitPK, DeletePostalAddressLineForm form);
    
    // --------------------------------------------------------------------------------
    //   Postal Address Line Elements
    // --------------------------------------------------------------------------------
    
    CommandResult createPostalAddressLineElement(UserVisitPK userVisitPK, CreatePostalAddressLineElementForm form);
    
    CommandResult getPostalAddressLineElements(UserVisitPK userVisitPK, GetPostalAddressLineElementsForm form);
    
    CommandResult editPostalAddressLineElement(UserVisitPK userVisitPK, EditPostalAddressLineElementForm form);
    
    CommandResult deletePostalAddressLineElement(UserVisitPK userVisitPK, DeletePostalAddressLineElementForm form);
    
}
