// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
        return new CreateContactMechanismTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContactMechanismTypes(UserVisitPK userVisitPK, GetContactMechanismTypesForm form) {
        return new GetContactMechanismTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContactMechanismTypeChoices(UserVisitPK userVisitPK, GetContactMechanismTypeChoicesForm form) {
        return new GetContactMechanismTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismTypeDescription(UserVisitPK userVisitPK, CreateContactMechanismTypeDescriptionForm form) {
        return new CreateContactMechanismTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Alias Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismAliasType(UserVisitPK userVisitPK, CreateContactMechanismAliasTypeForm form) {
        return new CreateContactMechanismAliasTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContactMechanismAliasTypes(UserVisitPK userVisitPK, GetContactMechanismAliasTypesForm form) {
        return new GetContactMechanismAliasTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContactMechanismAliasTypeChoices(UserVisitPK userVisitPK, GetContactMechanismAliasTypeChoicesForm form) {
        return new GetContactMechanismAliasTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismAliasTypeDescription(UserVisitPK userVisitPK, CreateContactMechanismAliasTypeDescriptionForm form) {
        return new CreateContactMechanismAliasTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismPurpose(UserVisitPK userVisitPK, CreateContactMechanismPurposeForm form) {
        return new CreateContactMechanismPurposeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContactMechanismPurposes(UserVisitPK userVisitPK, GetContactMechanismPurposesForm form) {
        return new GetContactMechanismPurposesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContactMechanismPurposeChoices(UserVisitPK userVisitPK, GetContactMechanismPurposeChoicesForm form) {
        return new GetContactMechanismPurposeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Purpose Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismPurposeDescription(UserVisitPK userVisitPK, CreateContactMechanismPurposeDescriptionForm form) {
        return new CreateContactMechanismPurposeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanisms
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getContactMechanismChoices(UserVisitPK userVisitPK, GetContactMechanismChoicesForm form) {
        return new GetContactMechanismChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEmailAddressStatusChoices(UserVisitPK userVisitPK, GetEmailAddressStatusChoicesForm form) {
        return new GetEmailAddressStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setEmailAddressStatus(UserVisitPK userVisitPK, SetEmailAddressStatusForm form) {
        return new SetEmailAddressStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEmailAddressVerificationChoices(UserVisitPK userVisitPK, GetEmailAddressVerificationChoicesForm form) {
        return new GetEmailAddressVerificationChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setEmailAddressVerification(UserVisitPK userVisitPK, SetEmailAddressVerificationForm form) {
        return new SetEmailAddressVerificationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPostalAddressStatusChoices(UserVisitPK userVisitPK, GetPostalAddressStatusChoicesForm form) {
        return new GetPostalAddressStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setPostalAddressStatus(UserVisitPK userVisitPK, SetPostalAddressStatusForm form) {
        return new SetPostalAddressStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTelephoneStatusChoices(UserVisitPK userVisitPK, GetTelephoneStatusChoicesForm form) {
        return new GetTelephoneStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setTelephoneStatus(UserVisitPK userVisitPK, SetTelephoneStatusForm form) {
        return new SetTelephoneStatusCommand(userVisitPK, form).run();
    }
    

    @Override
    public CommandResult getWebAddressStatusChoices(UserVisitPK userVisitPK, GetWebAddressStatusChoicesForm form) {
        return new GetWebAddressStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setWebAddressStatus(UserVisitPK userVisitPK, SetWebAddressStatusForm form) {
        return new SetWebAddressStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContactMechanism(UserVisitPK userVisitPK, GetContactMechanismForm form) {
        return new GetContactMechanismCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteContactMechanism(UserVisitPK userVisitPK, DeleteContactMechanismForm form) {
        return new DeleteContactMechanismCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Aliases
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismAlias(UserVisitPK userVisitPK, CreateContactMechanismAliasForm form) {
        return new CreateContactMechanismAliasCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteContactMechanismAlias(UserVisitPK userVisitPK, DeleteContactMechanismAliasForm form) {
        return new DeleteContactMechanismAliasCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Aliases
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyContactMechanismAlias(UserVisitPK userVisitPK, CreatePartyContactMechanismAliasForm form) {
        return new CreatePartyContactMechanismAliasCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartyContactMechanismAlias(UserVisitPK userVisitPK, DeletePartyContactMechanismAliasForm form) {
        return new DeletePartyContactMechanismAliasCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Email Addresses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactEmailAddress(UserVisitPK userVisitPK, CreateContactEmailAddressForm form) {
        return new CreateContactEmailAddressCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editContactEmailAddress(UserVisitPK userVisitPK, EditContactEmailAddressForm form) {
        return new EditContactEmailAddressCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Postal Addresses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactPostalAddress(UserVisitPK userVisitPK, CreateContactPostalAddressForm form) {
        return new CreateContactPostalAddressCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editContactPostalAddress(UserVisitPK userVisitPK, EditContactPostalAddressForm form) {
        return new EditContactPostalAddressCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Telephones
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactTelephone(UserVisitPK userVisitPK, CreateContactTelephoneForm form) {
        return new CreateContactTelephoneCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editContactTelephone(UserVisitPK userVisitPK, EditContactTelephoneForm form) {
        return new EditContactTelephoneCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Web Addresses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactWebAddress(UserVisitPK userVisitPK, CreateContactWebAddressForm form) {
        return new CreateContactWebAddressCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editContactWebAddress(UserVisitPK userVisitPK, EditContactWebAddressForm form) {
        return new EditContactWebAddressCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyContactMechanismPurpose(UserVisitPK userVisitPK, CreatePartyContactMechanismPurposeForm form) {
        return new CreatePartyContactMechanismPurposeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultPartyContactMechanismPurpose(UserVisitPK userVisitPK, SetDefaultPartyContactMechanismPurposeForm form) {
        return new SetDefaultPartyContactMechanismPurposeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartyContactMechanismPurpose(UserVisitPK userVisitPK, DeletePartyContactMechanismPurposeForm form) {
        return new DeletePartyContactMechanismPurposeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Relationships
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyContactMechanismRelationship(UserVisitPK userVisitPK, CreatePartyContactMechanismRelationshipForm form) {
        return new CreatePartyContactMechanismRelationshipCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartyContactMechanismRelationship(UserVisitPK userVisitPK, DeletePartyContactMechanismRelationshipForm form) {
        return new DeletePartyContactMechanismRelationshipCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Element Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressElementType(UserVisitPK userVisitPK, CreatePostalAddressElementTypeForm form) {
        return new CreatePostalAddressElementTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPostalAddressElementTypeChoices(UserVisitPK userVisitPK, GetPostalAddressElementTypeChoicesForm form) {
        return new GetPostalAddressElementTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Element Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressElementTypeDescription(UserVisitPK userVisitPK, CreatePostalAddressElementTypeDescriptionForm form) {
        return new CreatePostalAddressElementTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Postal Address Formats
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressFormat(UserVisitPK userVisitPK, CreatePostalAddressFormatForm form) {
        return new CreatePostalAddressFormatCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPostalAddressFormat(UserVisitPK userVisitPK, GetPostalAddressFormatForm form) {
        return new GetPostalAddressFormatCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPostalAddressFormats(UserVisitPK userVisitPK, GetPostalAddressFormatsForm form) {
        return new GetPostalAddressFormatsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPostalAddressFormatChoices(UserVisitPK userVisitPK, GetPostalAddressFormatChoicesForm form) {
        return new GetPostalAddressFormatChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultPostalAddressFormat(UserVisitPK userVisitPK, SetDefaultPostalAddressFormatForm form) {
        return new SetDefaultPostalAddressFormatCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPostalAddressFormat(UserVisitPK userVisitPK, EditPostalAddressFormatForm form) {
        return new EditPostalAddressFormatCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePostalAddressFormat(UserVisitPK userVisitPK, DeletePostalAddressFormatForm form) {
        return new DeletePostalAddressFormatCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Postal Address Format Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressFormatDescription(UserVisitPK userVisitPK, CreatePostalAddressFormatDescriptionForm form) {
        return new CreatePostalAddressFormatDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPostalAddressFormatDescriptions(UserVisitPK userVisitPK, GetPostalAddressFormatDescriptionsForm form) {
        return new GetPostalAddressFormatDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPostalAddressFormatDescription(UserVisitPK userVisitPK, EditPostalAddressFormatDescriptionForm form) {
        return new EditPostalAddressFormatDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePostalAddressFormatDescription(UserVisitPK userVisitPK, DeletePostalAddressFormatDescriptionForm form) {
        return new DeletePostalAddressFormatDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Lines
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressLine(UserVisitPK userVisitPK, CreatePostalAddressLineForm form) {
        return new CreatePostalAddressLineCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPostalAddressLines(UserVisitPK userVisitPK, GetPostalAddressLinesForm form) {
        return new GetPostalAddressLinesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPostalAddressLine(UserVisitPK userVisitPK, EditPostalAddressLineForm form) {
        return new EditPostalAddressLineCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePostalAddressLine(UserVisitPK userVisitPK, DeletePostalAddressLineForm form) {
        return new DeletePostalAddressLineCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Line Elements
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressLineElement(UserVisitPK userVisitPK, CreatePostalAddressLineElementForm form) {
        return new CreatePostalAddressLineElementCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPostalAddressLineElements(UserVisitPK userVisitPK, GetPostalAddressLineElementsForm form) {
        return new GetPostalAddressLineElementsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPostalAddressLineElement(UserVisitPK userVisitPK, EditPostalAddressLineElementForm form) {
        return new EditPostalAddressLineElementCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePostalAddressLineElement(UserVisitPK userVisitPK, DeletePostalAddressLineElementForm form) {
        return new DeletePostalAddressLineElementCommand(userVisitPK, form).run();
    }
    
}
