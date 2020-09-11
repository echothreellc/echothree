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

package com.echothree.control.user.contactlist.server.command;

import com.echothree.control.user.contactlist.common.edit.ContactListEditFactory;
import com.echothree.control.user.contactlist.common.edit.ContactListGroupEdit;
import com.echothree.control.user.contactlist.common.form.EditContactListGroupForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.EditContactListGroupResult;
import com.echothree.control.user.contactlist.common.spec.ContactListGroupSpec;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactListGroup;
import com.echothree.model.data.contactlist.server.entity.ContactListGroupDescription;
import com.echothree.model.data.contactlist.server.entity.ContactListGroupDetail;
import com.echothree.model.data.contactlist.server.value.ContactListGroupDescriptionValue;
import com.echothree.model.data.contactlist.server.value.ContactListGroupDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class EditContactListGroupCommand
        extends BaseAbstractEditCommand<ContactListGroupSpec, ContactListGroupEdit, EditContactListGroupResult, ContactListGroup, ContactListGroup> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactListGroup.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactListGroupName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContactListGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditContactListGroupCommand */
    public EditContactListGroupCommand(UserVisitPK userVisitPK, EditContactListGroupForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditContactListGroupResult getResult() {
        return ContactListResultFactory.getEditContactListGroupResult();
    }

    @Override
    public ContactListGroupEdit getEdit() {
        return ContactListEditFactory.getContactListGroupEdit();
    }

    @Override
    public ContactListGroup getEntity(EditContactListGroupResult result) {
        var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
        ContactListGroup contactListGroup = null;
        String contactListGroupName = spec.getContactListGroupName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            contactListGroup = contactListControl.getContactListGroupByName(contactListGroupName);
        } else { // EditMode.UPDATE
            contactListGroup = contactListControl.getContactListGroupByNameForUpdate(contactListGroupName);
        }

        if(contactListGroup == null) {
            addExecutionError(ExecutionErrors.UnknownContactListGroupName.name(), contactListGroupName);
        }

        return contactListGroup;
    }

    @Override
    public ContactListGroup getLockEntity(ContactListGroup contactListGroup) {
        return contactListGroup;
    }

    @Override
    public void fillInResult(EditContactListGroupResult result, ContactListGroup contactListGroup) {
        var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);

        result.setContactListGroup(contactListControl.getContactListGroupTransfer(getUserVisit(), contactListGroup));
    }

    @Override
    public void doLock(ContactListGroupEdit edit, ContactListGroup contactListGroup) {
        var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
        ContactListGroupDescription contactListGroupDescription = contactListControl.getContactListGroupDescription(contactListGroup, getPreferredLanguage());
        ContactListGroupDetail contactListGroupDetail = contactListGroup.getLastDetail();

        edit.setContactListGroupName(contactListGroupDetail.getContactListGroupName());
        edit.setIsDefault(contactListGroupDetail.getIsDefault().toString());
        edit.setSortOrder(contactListGroupDetail.getSortOrder().toString());

        if(contactListGroupDescription != null) {
            edit.setDescription(contactListGroupDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ContactListGroup contactListGroup) {
        var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
        String contactListGroupName = edit.getContactListGroupName();
        ContactListGroup duplicateContactListGroup = contactListControl.getContactListGroupByName(contactListGroupName);

        if(duplicateContactListGroup != null && !contactListGroup.equals(duplicateContactListGroup)) {
            addExecutionError(ExecutionErrors.DuplicateContactListGroupName.name(), contactListGroupName);
        }
    }

    @Override
    public void doUpdate(ContactListGroup contactListGroup) {
        var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
        var partyPK = getPartyPK();
        ContactListGroupDetailValue contactListGroupDetailValue = contactListControl.getContactListGroupDetailValueForUpdate(contactListGroup);
        ContactListGroupDescription contactListGroupDescription = contactListControl.getContactListGroupDescriptionForUpdate(contactListGroup, getPreferredLanguage());
        String description = edit.getDescription();

        contactListGroupDetailValue.setContactListGroupName(edit.getContactListGroupName());
        contactListGroupDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        contactListGroupDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        contactListControl.updateContactListGroupFromValue(contactListGroupDetailValue, partyPK);

        if(contactListGroupDescription == null && description != null) {
            contactListControl.createContactListGroupDescription(contactListGroup, getPreferredLanguage(), description, partyPK);
        } else if(contactListGroupDescription != null && description == null) {
            contactListControl.deleteContactListGroupDescription(contactListGroupDescription, partyPK);
        } else if(contactListGroupDescription != null && description != null) {
            ContactListGroupDescriptionValue contactListGroupDescriptionValue = contactListControl.getContactListGroupDescriptionValue(contactListGroupDescription);

            contactListGroupDescriptionValue.setDescription(description);
            contactListControl.updateContactListGroupDescriptionFromValue(contactListGroupDescriptionValue, partyPK);
        }
    }

}
