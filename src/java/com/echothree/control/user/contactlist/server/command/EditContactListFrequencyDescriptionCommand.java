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

package com.echothree.control.user.contactlist.server.command;

import com.echothree.control.user.contactlist.common.edit.ContactListEditFactory;
import com.echothree.control.user.contactlist.common.edit.ContactListFrequencyDescriptionEdit;
import com.echothree.control.user.contactlist.common.form.EditContactListFrequencyDescriptionForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.EditContactListFrequencyDescriptionResult;
import com.echothree.control.user.contactlist.common.spec.ContactListFrequencyDescriptionSpec;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequency;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequencyDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditContactListFrequencyDescriptionCommand
        extends BaseAbstractEditCommand<ContactListFrequencyDescriptionSpec, ContactListFrequencyDescriptionEdit, EditContactListFrequencyDescriptionResult, ContactListFrequencyDescription, ContactListFrequency> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactListFrequency.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactListFrequencyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditContactListFrequencyDescriptionCommand */
    public EditContactListFrequencyDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditContactListFrequencyDescriptionResult getResult() {
        return ContactListResultFactory.getEditContactListFrequencyDescriptionResult();
    }

    @Override
    public ContactListFrequencyDescriptionEdit getEdit() {
        return ContactListEditFactory.getContactListFrequencyDescriptionEdit();
    }

    @Override
    public ContactListFrequencyDescription getEntity(EditContactListFrequencyDescriptionResult result) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        ContactListFrequencyDescription contactListFrequencyDescription = null;
        var contactListFrequencyName = spec.getContactListFrequencyName();
        var contactListFrequency = contactListControl.getContactListFrequencyByName(contactListFrequencyName);

        if(contactListFrequency != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    contactListFrequencyDescription = contactListControl.getContactListFrequencyDescription(contactListFrequency, language);
                } else { // EditMode.UPDATE
                    contactListFrequencyDescription = contactListControl.getContactListFrequencyDescriptionForUpdate(contactListFrequency, language);
                }

                if(contactListFrequencyDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownContactListFrequencyDescription.name(), contactListFrequencyName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContactListFrequencyName.name(), contactListFrequencyName);
        }

        return contactListFrequencyDescription;
    }

    @Override
    public ContactListFrequency getLockEntity(ContactListFrequencyDescription contactListFrequencyDescription) {
        return contactListFrequencyDescription.getContactListFrequency();
    }

    @Override
    public void fillInResult(EditContactListFrequencyDescriptionResult result, ContactListFrequencyDescription contactListFrequencyDescription) {
        var contactListControl = Session.getModelController(ContactListControl.class);

        result.setContactListFrequencyDescription(contactListControl.getContactListFrequencyDescriptionTransfer(getUserVisit(), contactListFrequencyDescription));
    }

    @Override
    public void doLock(ContactListFrequencyDescriptionEdit edit, ContactListFrequencyDescription contactListFrequencyDescription) {
        edit.setDescription(contactListFrequencyDescription.getDescription());
    }

    @Override
    public void doUpdate(ContactListFrequencyDescription contactListFrequencyDescription) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var contactListFrequencyDescriptionValue = contactListControl.getContactListFrequencyDescriptionValue(contactListFrequencyDescription);

        contactListFrequencyDescriptionValue.setDescription(edit.getDescription());

        contactListControl.updateContactListFrequencyDescriptionFromValue(contactListFrequencyDescriptionValue, getPartyPK());
    }

}
