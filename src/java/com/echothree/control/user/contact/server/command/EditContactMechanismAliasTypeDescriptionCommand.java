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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.edit.ContactEditFactory;
import com.echothree.control.user.contact.common.edit.ContactMechanismAliasTypeDescriptionEdit;
import com.echothree.control.user.contact.common.form.EditContactMechanismAliasTypeDescriptionForm;
import com.echothree.control.user.contact.common.result.ContactResultFactory;
import com.echothree.control.user.contact.common.result.EditContactMechanismAliasTypeDescriptionResult;
import com.echothree.control.user.contact.common.spec.ContactMechanismAliasTypeDescriptionSpec;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasType;
import com.echothree.model.data.contact.server.entity.ContactMechanismAliasTypeDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditContactMechanismAliasTypeDescriptionCommand
        extends BaseAbstractEditCommand<ContactMechanismAliasTypeDescriptionSpec, ContactMechanismAliasTypeDescriptionEdit, EditContactMechanismAliasTypeDescriptionResult, ContactMechanismAliasTypeDescription, ContactMechanismAliasType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactMechanismAliasType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactMechanismAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditContactMechanismAliasTypeDescriptionCommand */
    public EditContactMechanismAliasTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditContactMechanismAliasTypeDescriptionResult getResult() {
        return ContactResultFactory.getEditContactMechanismAliasTypeDescriptionResult();
    }

    @Override
    public ContactMechanismAliasTypeDescriptionEdit getEdit() {
        return ContactEditFactory.getContactMechanismAliasTypeDescriptionEdit();
    }

    @Override
    public ContactMechanismAliasTypeDescription getEntity(EditContactMechanismAliasTypeDescriptionResult result) {
        var contactControl = Session.getModelController(ContactControl.class);
        ContactMechanismAliasTypeDescription contactMechanismAliasTypeDescription = null;
        var contactMechanismAliasTypeName = spec.getContactMechanismAliasTypeName();
        var contactMechanismAliasType = contactControl.getContactMechanismAliasTypeByName(contactMechanismAliasTypeName);

        if(contactMechanismAliasType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    contactMechanismAliasTypeDescription = contactControl.getContactMechanismAliasTypeDescription(contactMechanismAliasType, language);
                } else { // EditMode.UPDATE
                    contactMechanismAliasTypeDescription = contactControl.getContactMechanismAliasTypeDescriptionForUpdate(contactMechanismAliasType, language);
                }

                if(contactMechanismAliasTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownContactMechanismAliasTypeDescription.name(), contactMechanismAliasTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContactMechanismAliasTypeName.name(), contactMechanismAliasTypeName);
        }

        return contactMechanismAliasTypeDescription;
    }

    @Override
    public ContactMechanismAliasType getLockEntity(ContactMechanismAliasTypeDescription contactMechanismAliasTypeDescription) {
        return contactMechanismAliasTypeDescription.getContactMechanismAliasType();
    }

    @Override
    public void fillInResult(EditContactMechanismAliasTypeDescriptionResult result, ContactMechanismAliasTypeDescription contactMechanismAliasTypeDescription) {
        var contactControl = Session.getModelController(ContactControl.class);

        result.setContactMechanismAliasTypeDescription(contactControl.getContactMechanismAliasTypeDescriptionTransfer(getUserVisit(), contactMechanismAliasTypeDescription));
    }

    @Override
    public void doLock(ContactMechanismAliasTypeDescriptionEdit edit, ContactMechanismAliasTypeDescription contactMechanismAliasTypeDescription) {
        edit.setDescription(contactMechanismAliasTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ContactMechanismAliasTypeDescription contactMechanismAliasTypeDescription) {
        var contactControl = Session.getModelController(ContactControl.class);
        var contactMechanismAliasTypeDescriptionValue = contactControl.getContactMechanismAliasTypeDescriptionValue(contactMechanismAliasTypeDescription);
        
        contactMechanismAliasTypeDescriptionValue.setDescription(edit.getDescription());
        
        contactControl.updateContactMechanismAliasTypeDescriptionFromValue(contactMechanismAliasTypeDescriptionValue, getPartyPK());
    }

    
}
