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
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateContactMechanismTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactMechanismTypes(UserVisitPK userVisitPK, GetContactMechanismTypesForm form) {
        return CDI.current().select(GetContactMechanismTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactMechanismTypeChoices(UserVisitPK userVisitPK, GetContactMechanismTypeChoicesForm form) {
        return CDI.current().select(GetContactMechanismTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismTypeDescription(UserVisitPK userVisitPK, CreateContactMechanismTypeDescriptionForm form) {
        return CDI.current().select(CreateContactMechanismTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Alias Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismAliasType(UserVisitPK userVisitPK, CreateContactMechanismAliasTypeForm form) {
        return CDI.current().select(CreateContactMechanismAliasTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactMechanismAliasTypes(UserVisitPK userVisitPK, GetContactMechanismAliasTypesForm form) {
        return CDI.current().select(GetContactMechanismAliasTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactMechanismAliasTypeChoices(UserVisitPK userVisitPK, GetContactMechanismAliasTypeChoicesForm form) {
        return CDI.current().select(GetContactMechanismAliasTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismAliasTypeDescription(UserVisitPK userVisitPK, CreateContactMechanismAliasTypeDescriptionForm form) {
        return CDI.current().select(CreateContactMechanismAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismPurpose(UserVisitPK userVisitPK, CreateContactMechanismPurposeForm form) {
        return CDI.current().select(CreateContactMechanismPurposeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactMechanismPurpose(UserVisitPK userVisitPK, GetContactMechanismPurposeForm form) {
        return CDI.current().select(GetContactMechanismPurposeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactMechanismPurposes(UserVisitPK userVisitPK, GetContactMechanismPurposesForm form) {
        return CDI.current().select(GetContactMechanismPurposesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContactMechanismPurposeChoices(UserVisitPK userVisitPK, GetContactMechanismPurposeChoicesForm form) {
        return CDI.current().select(GetContactMechanismPurposeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Purpose Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismPurposeDescription(UserVisitPK userVisitPK, CreateContactMechanismPurposeDescriptionForm form) {
        return CDI.current().select(CreateContactMechanismPurposeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanisms
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getContactMechanismChoices(UserVisitPK userVisitPK, GetContactMechanismChoicesForm form) {
        return CDI.current().select(GetContactMechanismChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEmailAddressStatusChoices(UserVisitPK userVisitPK, GetEmailAddressStatusChoicesForm form) {
        return CDI.current().select(GetEmailAddressStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setEmailAddressStatus(UserVisitPK userVisitPK, SetEmailAddressStatusForm form) {
        return CDI.current().select(SetEmailAddressStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEmailAddressVerificationChoices(UserVisitPK userVisitPK, GetEmailAddressVerificationChoicesForm form) {
        return CDI.current().select(GetEmailAddressVerificationChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setEmailAddressVerification(UserVisitPK userVisitPK, SetEmailAddressVerificationForm form) {
        return CDI.current().select(SetEmailAddressVerificationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPostalAddressStatusChoices(UserVisitPK userVisitPK, GetPostalAddressStatusChoicesForm form) {
        return CDI.current().select(GetPostalAddressStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setPostalAddressStatus(UserVisitPK userVisitPK, SetPostalAddressStatusForm form) {
        return CDI.current().select(SetPostalAddressStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTelephoneStatusChoices(UserVisitPK userVisitPK, GetTelephoneStatusChoicesForm form) {
        return CDI.current().select(GetTelephoneStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setTelephoneStatus(UserVisitPK userVisitPK, SetTelephoneStatusForm form) {
        return CDI.current().select(SetTelephoneStatusCommand.class).get().run(userVisitPK, form);
    }
    

    @Override
    public CommandResult getWebAddressStatusChoices(UserVisitPK userVisitPK, GetWebAddressStatusChoicesForm form) {
        return CDI.current().select(GetWebAddressStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setWebAddressStatus(UserVisitPK userVisitPK, SetWebAddressStatusForm form) {
        return CDI.current().select(SetWebAddressStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContactMechanism(UserVisitPK userVisitPK, GetContactMechanismForm form) {
        return CDI.current().select(GetContactMechanismCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContactMechanism(UserVisitPK userVisitPK, DeleteContactMechanismForm form) {
        return CDI.current().select(DeleteContactMechanismCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Mechanism Aliases
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactMechanismAlias(UserVisitPK userVisitPK, CreateContactMechanismAliasForm form) {
        return CDI.current().select(CreateContactMechanismAliasCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContactMechanismAlias(UserVisitPK userVisitPK, DeleteContactMechanismAliasForm form) {
        return CDI.current().select(DeleteContactMechanismAliasCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Aliases
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyContactMechanismAlias(UserVisitPK userVisitPK, CreatePartyContactMechanismAliasForm form) {
        return CDI.current().select(CreatePartyContactMechanismAliasCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyContactMechanismAlias(UserVisitPK userVisitPK, DeletePartyContactMechanismAliasForm form) {
        return CDI.current().select(DeletePartyContactMechanismAliasCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Email Addresses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactEmailAddress(UserVisitPK userVisitPK, CreateContactEmailAddressForm form) {
        return CDI.current().select(CreateContactEmailAddressCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContactEmailAddress(UserVisitPK userVisitPK, EditContactEmailAddressForm form) {
        return CDI.current().select(EditContactEmailAddressCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Postal Addresses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactPostalAddress(UserVisitPK userVisitPK, CreateContactPostalAddressForm form) {
        return CDI.current().select(CreateContactPostalAddressCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContactPostalAddress(UserVisitPK userVisitPK, EditContactPostalAddressForm form) {
        return CDI.current().select(EditContactPostalAddressCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Telephones
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactTelephone(UserVisitPK userVisitPK, CreateContactTelephoneForm form) {
        return CDI.current().select(CreateContactTelephoneCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContactTelephone(UserVisitPK userVisitPK, EditContactTelephoneForm form) {
        return CDI.current().select(EditContactTelephoneCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Contact Web Addresses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContactWebAddress(UserVisitPK userVisitPK, CreateContactWebAddressForm form) {
        return CDI.current().select(CreateContactWebAddressCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContactWebAddress(UserVisitPK userVisitPK, EditContactWebAddressForm form) {
        return CDI.current().select(EditContactWebAddressCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyContactMechanismPurpose(UserVisitPK userVisitPK, CreatePartyContactMechanismPurposeForm form) {
        return CDI.current().select(CreatePartyContactMechanismPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultPartyContactMechanismPurpose(UserVisitPK userVisitPK, SetDefaultPartyContactMechanismPurposeForm form) {
        return CDI.current().select(SetDefaultPartyContactMechanismPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyContactMechanismPurpose(UserVisitPK userVisitPK, DeletePartyContactMechanismPurposeForm form) {
        return CDI.current().select(DeletePartyContactMechanismPurposeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Party Contact Mechanism Relationships
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyContactMechanismRelationship(UserVisitPK userVisitPK, CreatePartyContactMechanismRelationshipForm form) {
        return CDI.current().select(CreatePartyContactMechanismRelationshipCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyContactMechanismRelationship(UserVisitPK userVisitPK, DeletePartyContactMechanismRelationshipForm form) {
        return CDI.current().select(DeletePartyContactMechanismRelationshipCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Element Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressElementType(UserVisitPK userVisitPK, CreatePostalAddressElementTypeForm form) {
        return CDI.current().select(CreatePostalAddressElementTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPostalAddressElementTypeChoices(UserVisitPK userVisitPK, GetPostalAddressElementTypeChoicesForm form) {
        return CDI.current().select(GetPostalAddressElementTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Element Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressElementTypeDescription(UserVisitPK userVisitPK, CreatePostalAddressElementTypeDescriptionForm form) {
        return CDI.current().select(CreatePostalAddressElementTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Postal Address Formats
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressFormat(UserVisitPK userVisitPK, CreatePostalAddressFormatForm form) {
        return CDI.current().select(CreatePostalAddressFormatCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPostalAddressFormat(UserVisitPK userVisitPK, GetPostalAddressFormatForm form) {
        return CDI.current().select(GetPostalAddressFormatCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPostalAddressFormats(UserVisitPK userVisitPK, GetPostalAddressFormatsForm form) {
        return CDI.current().select(GetPostalAddressFormatsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPostalAddressFormatChoices(UserVisitPK userVisitPK, GetPostalAddressFormatChoicesForm form) {
        return CDI.current().select(GetPostalAddressFormatChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultPostalAddressFormat(UserVisitPK userVisitPK, SetDefaultPostalAddressFormatForm form) {
        return CDI.current().select(SetDefaultPostalAddressFormatCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPostalAddressFormat(UserVisitPK userVisitPK, EditPostalAddressFormatForm form) {
        return CDI.current().select(EditPostalAddressFormatCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePostalAddressFormat(UserVisitPK userVisitPK, DeletePostalAddressFormatForm form) {
        return CDI.current().select(DeletePostalAddressFormatCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Postal Address Format Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressFormatDescription(UserVisitPK userVisitPK, CreatePostalAddressFormatDescriptionForm form) {
        return CDI.current().select(CreatePostalAddressFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPostalAddressFormatDescriptions(UserVisitPK userVisitPK, GetPostalAddressFormatDescriptionsForm form) {
        return CDI.current().select(GetPostalAddressFormatDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPostalAddressFormatDescription(UserVisitPK userVisitPK, EditPostalAddressFormatDescriptionForm form) {
        return CDI.current().select(EditPostalAddressFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePostalAddressFormatDescription(UserVisitPK userVisitPK, DeletePostalAddressFormatDescriptionForm form) {
        return CDI.current().select(DeletePostalAddressFormatDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Lines
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressLine(UserVisitPK userVisitPK, CreatePostalAddressLineForm form) {
        return CDI.current().select(CreatePostalAddressLineCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPostalAddressLines(UserVisitPK userVisitPK, GetPostalAddressLinesForm form) {
        return CDI.current().select(GetPostalAddressLinesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPostalAddressLine(UserVisitPK userVisitPK, EditPostalAddressLineForm form) {
        return CDI.current().select(EditPostalAddressLineCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePostalAddressLine(UserVisitPK userVisitPK, DeletePostalAddressLineForm form) {
        return CDI.current().select(DeletePostalAddressLineCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Postal Address Line Elements
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPostalAddressLineElement(UserVisitPK userVisitPK, CreatePostalAddressLineElementForm form) {
        return CDI.current().select(CreatePostalAddressLineElementCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPostalAddressLineElements(UserVisitPK userVisitPK, GetPostalAddressLineElementsForm form) {
        return CDI.current().select(GetPostalAddressLineElementsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPostalAddressLineElement(UserVisitPK userVisitPK, EditPostalAddressLineElementForm form) {
        return CDI.current().select(EditPostalAddressLineElementCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePostalAddressLineElement(UserVisitPK userVisitPK, DeletePostalAddressLineElementForm form) {
        return CDI.current().select(DeletePostalAddressLineElementCommand.class).get().run(userVisitPK, form);
    }
    
}
