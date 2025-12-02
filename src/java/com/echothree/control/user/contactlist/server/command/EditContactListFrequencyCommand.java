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

package com.echothree.control.user.contactlist.server.command;

import com.echothree.control.user.contactlist.common.edit.ContactListEditFactory;
import com.echothree.control.user.contactlist.common.edit.ContactListFrequencyEdit;
import com.echothree.control.user.contactlist.common.form.EditContactListFrequencyForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.EditContactListFrequencyResult;
import com.echothree.control.user.contactlist.common.spec.ContactListFrequencySpec;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactListFrequency;
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
public class EditContactListFrequencyCommand
        extends BaseAbstractEditCommand<ContactListFrequencySpec, ContactListFrequencyEdit, EditContactListFrequencyResult, ContactListFrequency, ContactListFrequency> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactListFrequency.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactListFrequencyName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactListFrequencyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditContactListFrequencyCommand */
    public EditContactListFrequencyCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditContactListFrequencyResult getResult() {
        return ContactListResultFactory.getEditContactListFrequencyResult();
    }

    @Override
    public ContactListFrequencyEdit getEdit() {
        return ContactListEditFactory.getContactListFrequencyEdit();
    }

    @Override
    public ContactListFrequency getEntity(EditContactListFrequencyResult result) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        ContactListFrequency contactListFrequency;
        var contactListFrequencyName = spec.getContactListFrequencyName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            contactListFrequency = contactListControl.getContactListFrequencyByName(contactListFrequencyName);
        } else { // EditMode.UPDATE
            contactListFrequency = contactListControl.getContactListFrequencyByNameForUpdate(contactListFrequencyName);
        }

        if(contactListFrequency == null) {
            addExecutionError(ExecutionErrors.UnknownContactListFrequencyName.name(), contactListFrequencyName);
        }

        return contactListFrequency;
    }

    @Override
    public ContactListFrequency getLockEntity(ContactListFrequency contactListFrequency) {
        return contactListFrequency;
    }

    @Override
    public void fillInResult(EditContactListFrequencyResult result, ContactListFrequency contactListFrequency) {
        var contactListControl = Session.getModelController(ContactListControl.class);

        result.setContactListFrequency(contactListControl.getContactListFrequencyTransfer(getUserVisit(), contactListFrequency));
    }

    @Override
    public void doLock(ContactListFrequencyEdit edit, ContactListFrequency contactListFrequency) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var contactListFrequencyDescription = contactListControl.getContactListFrequencyDescription(contactListFrequency, getPreferredLanguage());
        var contactListFrequencyDetail = contactListFrequency.getLastDetail();

        edit.setContactListFrequencyName(contactListFrequencyDetail.getContactListFrequencyName());
        edit.setIsDefault(contactListFrequencyDetail.getIsDefault().toString());
        edit.setSortOrder(contactListFrequencyDetail.getSortOrder().toString());

        if(contactListFrequencyDescription != null) {
            edit.setDescription(contactListFrequencyDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ContactListFrequency contactListFrequency) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var contactListFrequencyName = edit.getContactListFrequencyName();
        var duplicateContactListFrequency = contactListControl.getContactListFrequencyByName(contactListFrequencyName);

        if(duplicateContactListFrequency != null && !contactListFrequency.equals(duplicateContactListFrequency)) {
            addExecutionError(ExecutionErrors.DuplicateContactListFrequencyName.name(), contactListFrequencyName);
        }
    }

    @Override
    public void doUpdate(ContactListFrequency contactListFrequency) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var partyPK = getPartyPK();
        var contactListFrequencyDetailValue = contactListControl.getContactListFrequencyDetailValueForUpdate(contactListFrequency);
        var contactListFrequencyDescription = contactListControl.getContactListFrequencyDescriptionForUpdate(contactListFrequency, getPreferredLanguage());
        var description = edit.getDescription();

        contactListFrequencyDetailValue.setContactListFrequencyName(edit.getContactListFrequencyName());
        contactListFrequencyDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        contactListFrequencyDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        contactListControl.updateContactListFrequencyFromValue(contactListFrequencyDetailValue, partyPK);

        if(contactListFrequencyDescription == null && description != null) {
            contactListControl.createContactListFrequencyDescription(contactListFrequency, getPreferredLanguage(), description, partyPK);
        } else if(contactListFrequencyDescription != null && description == null) {
            contactListControl.deleteContactListFrequencyDescription(contactListFrequencyDescription, partyPK);
        } else if(contactListFrequencyDescription != null && description != null) {
            var contactListFrequencyDescriptionValue = contactListControl.getContactListFrequencyDescriptionValue(contactListFrequencyDescription);

            contactListFrequencyDescriptionValue.setDescription(description);
            contactListControl.updateContactListFrequencyDescriptionFromValue(contactListFrequencyDescriptionValue, partyPK);
        }
    }

}
