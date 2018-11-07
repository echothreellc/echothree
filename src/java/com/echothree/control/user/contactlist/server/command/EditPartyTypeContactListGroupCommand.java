// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.control.user.contactlist.common.edit.PartyTypeContactListGroupEdit;
import com.echothree.control.user.contactlist.common.form.EditPartyTypeContactListGroupForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.EditPartyTypeContactListGroupResult;
import com.echothree.control.user.contactlist.common.spec.PartyTypeContactListGroupSpec;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactListGroup;
import com.echothree.model.data.contactlist.server.entity.PartyTypeContactListGroup;
import com.echothree.model.data.contactlist.server.value.PartyTypeContactListGroupValue;
import com.echothree.model.data.party.server.entity.PartyType;
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

public class EditPartyTypeContactListGroupCommand
        extends BaseAbstractEditCommand<PartyTypeContactListGroupSpec, PartyTypeContactListGroupEdit, EditPartyTypeContactListGroupResult, PartyTypeContactListGroup, ContactListGroup> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactListGroup.name(), SecurityRoles.PartyTypeContactListGroup.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListGroupName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AddWhenCreated", FieldType.BOOLEAN, true, null, null)
                ));
    }

    /** Creates a new instance of EditPartyTypeContactListGroupCommand */
    public EditPartyTypeContactListGroupCommand(UserVisitPK userVisitPK, EditPartyTypeContactListGroupForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPartyTypeContactListGroupResult getResult() {
        return ContactListResultFactory.getEditPartyTypeContactListGroupResult();
    }

    @Override
    public PartyTypeContactListGroupEdit getEdit() {
        return ContactListEditFactory.getPartyTypeContactListGroupEdit();
    }

    @Override
    public PartyTypeContactListGroup getEntity(EditPartyTypeContactListGroupResult result) {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        PartyTypeContactListGroup partyTypeContactListGroup = null;
        String partyTypeName = spec.getPartyTypeName();
        PartyType partyType = partyControl.getPartyTypeByName(partyTypeName);

        if(partyType != null) {
            ContactListControl contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
            String contactListGroupName = spec.getContactListGroupName();
            ContactListGroup contactListGroup = contactListControl.getContactListGroupByName(contactListGroupName);

            if(contactListGroup != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    partyTypeContactListGroup = contactListControl.getPartyTypeContactListGroup(partyType, contactListGroup);
                } else { // EditMode.UPDATE
                    partyTypeContactListGroup = contactListControl.getPartyTypeContactListGroupForUpdate(partyType, contactListGroup);
                }

                if(partyTypeContactListGroup == null) {
                    addExecutionError(ExecutionErrors.UnknownPartyTypeContactListGroup.name(), partyTypeName, contactListGroupName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContactListGroupName.name(), contactListGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
        }

        return partyTypeContactListGroup;
    }

    @Override
    public ContactListGroup getLockEntity(PartyTypeContactListGroup partyTypeContactListGroup) {
        return partyTypeContactListGroup.getContactListGroup();
    }

    @Override
    public void fillInResult(EditPartyTypeContactListGroupResult result, PartyTypeContactListGroup partyTypeContactListGroup) {
        ContactListControl contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);

        result.setPartyTypeContactListGroup(contactListControl.getPartyTypeContactListGroupTransfer(getUserVisit(), partyTypeContactListGroup));
    }

    @Override
    public void doLock(PartyTypeContactListGroupEdit edit, PartyTypeContactListGroup partyTypeContactListGroup) {
        edit.setAddWhenCreated(partyTypeContactListGroup.getAddWhenCreated().toString());
    }

    @Override
    public void doUpdate(PartyTypeContactListGroup partyTypeContactListGroup) {
        ContactListControl contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
        PartyTypeContactListGroupValue partyTypeContactListGroupValue = contactListControl.getPartyTypeContactListGroupValue(partyTypeContactListGroup);

        partyTypeContactListGroupValue.setAddWhenCreated(Boolean.valueOf(edit.getAddWhenCreated()));

        contactListControl.updatePartyTypeContactListGroupFromValue(partyTypeContactListGroupValue, getPartyPK());
    }

}
