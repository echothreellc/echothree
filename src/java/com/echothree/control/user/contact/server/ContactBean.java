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

package com.echothree.control.user.contact.server;

import com.echothree.control.user.contact.common.ContactRemote;
import com.echothree.control.user.contact.common.form.*;
import com.echothree.control.user.contact.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class ContactBean
        extends ContactFormsImpl
        implements ContactRemote, ContactLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "ContactBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismType(UserVisitPK userVisitPK, CreateContactMechanismTypeForm form) {
        return new CreateContactMechanismTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactMechanismTypes(UserVisitPK userVisitPK, GetContactMechanismTypesForm form) {
        return new GetContactMechanismTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactMechanismTypeChoices(UserVisitPK userVisitPK, GetContactMechanismTypeChoicesForm form) {
        return new GetContactMechanismTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismTypeDescription(UserVisitPK userVisitPK, CreateContactMechanismTypeDescriptionForm form) {
        return new CreateContactMechanismTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Alias Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismAliasType(UserVisitPK userVisitPK, CreateContactMechanismAliasTypeForm form) {
        return new CreateContactMechanismAliasTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactMechanismAliasTypes(UserVisitPK userVisitPK, GetContactMechanismAliasTypesForm form) {
        return new GetContactMechanismAliasTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactMechanismAliasTypeChoices(UserVisitPK userVisitPK, GetContactMechanismAliasTypeChoicesForm form) {
        return new GetContactMechanismAliasTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismAliasTypeDescription(UserVisitPK userVisitPK, CreateContactMechanismAliasTypeDescriptionForm form) {
        return new CreateContactMechanismAliasTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismPurpose(UserVisitPK userVisitPK, CreateContactMechanismPurposeForm form) {
        return new CreateContactMechanismPurposeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactMechanismPurpose(UserVisitPK userVisitPK, GetContactMechanismPurposeForm form) {
        return new GetContactMechanismPurposeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactMechanismPurposes(UserVisitPK userVisitPK, GetContactMechanismPurposesForm form) {
        return new GetContactMechanismPurposesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactMechanismPurposeChoices(UserVisitPK userVisitPK, GetContactMechanismPurposeChoicesForm form) {
        return new GetContactMechanismPurposeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Purpose Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismPurposeDescription(UserVisitPK userVisitPK, CreateContactMechanismPurposeDescriptionForm form) {
        return new CreateContactMechanismPurposeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanisms
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getContactMechanismChoices(UserVisitPK userVisitPK, GetContactMechanismChoicesForm form) {
        return new GetContactMechanismChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEmailAddressStatusChoices(UserVisitPK userVisitPK, GetEmailAddressStatusChoicesForm form) {
        return new GetEmailAddressStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setEmailAddressStatus(UserVisitPK userVisitPK, SetEmailAddressStatusForm form) {
        return new SetEmailAddressStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEmailAddressVerificationChoices(UserVisitPK userVisitPK, GetEmailAddressVerificationChoicesForm form) {
        return new GetEmailAddressVerificationChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setEmailAddressVerification(UserVisitPK userVisitPK, SetEmailAddressVerificationForm form) {
        return new SetEmailAddressVerificationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPostalAddressStatusChoices(UserVisitPK userVisitPK, GetPostalAddressStatusChoicesForm form) {
        return new GetPostalAddressStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setPostalAddressStatus(UserVisitPK userVisitPK, SetPostalAddressStatusForm form) {
        return new SetPostalAddressStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTelephoneStatusChoices(UserVisitPK userVisitPK, GetTelephoneStatusChoicesForm form) {
        return new GetTelephoneStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setTelephoneStatus(UserVisitPK userVisitPK, SetTelephoneStatusForm form) {
        return new SetTelephoneStatusCommand().run(userVisitPK, form);
    }
    

    @Override
    public CommandResult getWebAddressStatusChoices(UserVisitPK userVisitPK, GetWebAddressStatusChoicesForm form) {
        return new GetWebAddressStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setWebAddressStatus(UserVisitPK userVisitPK, SetWebAddressStatusForm form) {
        return new SetWebAddressStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactMechanism(UserVisitPK userVisitPK, GetContactMechanismForm form) {
        return new GetContactMechanismCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactMechanism(UserVisitPK userVisitPK, DeleteContactMechanismForm form) {
        return new DeleteContactMechanismCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Aliases
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismAlias(UserVisitPK userVisitPK, CreateContactMechanismAliasForm form) {
        return new CreateContactMechanismAliasCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContactMechanismAlias(UserVisitPK userVisitPK, DeleteContactMechanismAliasForm form) {
        return new DeleteContactMechanismAliasCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Aliases
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyContactMechanismAlias(UserVisitPK userVisitPK, CreatePartyContactMechanismAliasForm form) {
        return new CreatePartyContactMechanismAliasCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyContactMechanismAlias(UserVisitPK userVisitPK, DeletePartyContactMechanismAliasForm form) {
        return new DeletePartyContactMechanismAliasCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Email Addresses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactEmailAddress(UserVisitPK userVisitPK, CreateContactEmailAddressForm form) {
        return new CreateContactEmailAddressCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContactEmailAddress(UserVisitPK userVisitPK, EditContactEmailAddressForm form) {
        return new EditContactEmailAddressCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Postal Addresses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactPostalAddress(UserVisitPK userVisitPK, CreateContactPostalAddressForm form) {
        return new CreateContactPostalAddressCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContactPostalAddress(UserVisitPK userVisitPK, EditContactPostalAddressForm form) {
        return new EditContactPostalAddressCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Telephones
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactTelephone(UserVisitPK userVisitPK, CreateContactTelephoneForm form) {
        return new CreateContactTelephoneCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContactTelephone(UserVisitPK userVisitPK, EditContactTelephoneForm form) {
        return new EditContactTelephoneCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Web Addresses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactWebAddress(UserVisitPK userVisitPK, CreateContactWebAddressForm form) {
        return new CreateContactWebAddressCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContactWebAddress(UserVisitPK userVisitPK, EditContactWebAddressForm form) {
        return new EditContactWebAddressCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyContactMechanismPurpose(UserVisitPK userVisitPK, CreatePartyContactMechanismPurposeForm form) {
        return new CreatePartyContactMechanismPurposeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultPartyContactMechanismPurpose(UserVisitPK userVisitPK, SetDefaultPartyContactMechanismPurposeForm form) {
        return new SetDefaultPartyContactMechanismPurposeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyContactMechanismPurpose(UserVisitPK userVisitPK, DeletePartyContactMechanismPurposeForm form) {
        return new DeletePartyContactMechanismPurposeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Relationships
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyContactMechanismRelationship(UserVisitPK userVisitPK, CreatePartyContactMechanismRelationshipForm form) {
        return new CreatePartyContactMechanismRelationshipCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyContactMechanismRelationship(UserVisitPK userVisitPK, DeletePartyContactMechanismRelationshipForm form) {
        return new DeletePartyContactMechanismRelationshipCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Element Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressElementType(UserVisitPK userVisitPK, CreatePostalAddressElementTypeForm form) {
        return new CreatePostalAddressElementTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPostalAddressElementTypeChoices(UserVisitPK userVisitPK, GetPostalAddressElementTypeChoicesForm form) {
        return new GetPostalAddressElementTypeChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Element Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressElementTypeDescription(UserVisitPK userVisitPK, CreatePostalAddressElementTypeDescriptionForm form) {
        return new CreatePostalAddressElementTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Postal Address Formats
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressFormat(UserVisitPK userVisitPK, CreatePostalAddressFormatForm form) {
        return new CreatePostalAddressFormatCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPostalAddressFormat(UserVisitPK userVisitPK, GetPostalAddressFormatForm form) {
        return new GetPostalAddressFormatCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPostalAddressFormats(UserVisitPK userVisitPK, GetPostalAddressFormatsForm form) {
        return new GetPostalAddressFormatsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPostalAddressFormatChoices(UserVisitPK userVisitPK, GetPostalAddressFormatChoicesForm form) {
        return new GetPostalAddressFormatChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultPostalAddressFormat(UserVisitPK userVisitPK, SetDefaultPostalAddressFormatForm form) {
        return new SetDefaultPostalAddressFormatCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPostalAddressFormat(UserVisitPK userVisitPK, EditPostalAddressFormatForm form) {
        return new EditPostalAddressFormatCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePostalAddressFormat(UserVisitPK userVisitPK, DeletePostalAddressFormatForm form) {
        return new DeletePostalAddressFormatCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Postal Address Format Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressFormatDescription(UserVisitPK userVisitPK, CreatePostalAddressFormatDescriptionForm form) {
        return new CreatePostalAddressFormatDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPostalAddressFormatDescriptions(UserVisitPK userVisitPK, GetPostalAddressFormatDescriptionsForm form) {
        return new GetPostalAddressFormatDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPostalAddressFormatDescription(UserVisitPK userVisitPK, EditPostalAddressFormatDescriptionForm form) {
        return new EditPostalAddressFormatDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePostalAddressFormatDescription(UserVisitPK userVisitPK, DeletePostalAddressFormatDescriptionForm form) {
        return new DeletePostalAddressFormatDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Lines
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressLine(UserVisitPK userVisitPK, CreatePostalAddressLineForm form) {
        return new CreatePostalAddressLineCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPostalAddressLines(UserVisitPK userVisitPK, GetPostalAddressLinesForm form) {
        return new GetPostalAddressLinesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPostalAddressLine(UserVisitPK userVisitPK, EditPostalAddressLineForm form) {
        return new EditPostalAddressLineCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePostalAddressLine(UserVisitPK userVisitPK, DeletePostalAddressLineForm form) {
        return new DeletePostalAddressLineCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Line Elements
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressLineElement(UserVisitPK userVisitPK, CreatePostalAddressLineElementForm form) {
        return new CreatePostalAddressLineElementCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPostalAddressLineElements(UserVisitPK userVisitPK, GetPostalAddressLineElementsForm form) {
        return new GetPostalAddressLineElementsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPostalAddressLineElement(UserVisitPK userVisitPK, EditPostalAddressLineElementForm form) {
        return new EditPostalAddressLineElementCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePostalAddressLineElement(UserVisitPK userVisitPK, DeletePostalAddressLineElementForm form) {
        return new DeletePostalAddressLineElementCommand().run(userVisitPK, form);
    }
    
}
