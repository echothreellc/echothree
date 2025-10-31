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
import com.echothree.control.user.contactlist.common.edit.PartyTypeContactListEdit;
import com.echothree.control.user.contactlist.common.form.EditPartyTypeContactListForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.EditPartyTypeContactListResult;
import com.echothree.control.user.contactlist.common.spec.PartyTypeContactListSpec;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.PartyTypeContactList;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditPartyTypeContactListCommand
        extends BaseAbstractEditCommand<PartyTypeContactListSpec, PartyTypeContactListEdit, EditPartyTypeContactListResult, PartyTypeContactList, ContactList> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactList.name(), SecurityRoles.PartyTypeContactList.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AddWhenCreated", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyTypeContactListCommand */
    public EditPartyTypeContactListCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPartyTypeContactListResult getResult() {
        return ContactListResultFactory.getEditPartyTypeContactListResult();
    }

    @Override
    public PartyTypeContactListEdit getEdit() {
        return ContactListEditFactory.getPartyTypeContactListEdit();
    }

    @Override
    public PartyTypeContactList getEntity(EditPartyTypeContactListResult result) {
        var partyControl = Session.getModelController(PartyControl.class);
        PartyTypeContactList partyTypeContactList = null;
        var partyTypeName = spec.getPartyTypeName();
        var partyType = partyControl.getPartyTypeByName(partyTypeName);

        if(partyType != null) {
            var contactListControl = Session.getModelController(ContactListControl.class);
            var contactListName = spec.getContactListName();
            var contactList = contactListControl.getContactListByName(contactListName);

            if(contactList != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    partyTypeContactList = contactListControl.getPartyTypeContactList(partyType, contactList);
                } else { // EditMode.UPDATE
                    partyTypeContactList = contactListControl.getPartyTypeContactListForUpdate(partyType, contactList);
                }

                if(partyTypeContactList == null) {
                    addExecutionError(ExecutionErrors.UnknownPartyTypeContactList.name(), partyTypeName, contactListName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContactListName.name(), contactListName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyTypeName.name(), partyTypeName);
        }

        return partyTypeContactList;
    }

    @Override
    public ContactList getLockEntity(PartyTypeContactList partyTypeContactList) {
        return partyTypeContactList.getContactList();
    }

    @Override
    public void fillInResult(EditPartyTypeContactListResult result, PartyTypeContactList partyTypeContactList) {
        var contactListControl = Session.getModelController(ContactListControl.class);

        result.setPartyTypeContactList(contactListControl.getPartyTypeContactListTransfer(getUserVisit(), partyTypeContactList));
    }

    @Override
    public void doLock(PartyTypeContactListEdit edit, PartyTypeContactList partyTypeContactList) {
        edit.setAddWhenCreated(partyTypeContactList.getAddWhenCreated().toString());
    }

    @Override
    public void doUpdate(PartyTypeContactList partyTypeContactList) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var partyTypeContactListValue = contactListControl.getPartyTypeContactListValue(partyTypeContactList);

        partyTypeContactListValue.setAddWhenCreated(Boolean.valueOf(edit.getAddWhenCreated()));

        contactListControl.updatePartyTypeContactListFromValue(partyTypeContactListValue, getPartyPK());
    }
    
}
