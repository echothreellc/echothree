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
import com.echothree.control.user.contactlist.common.edit.PartyContactListEdit;
import com.echothree.control.user.contactlist.common.form.EditPartyContactListForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.EditPartyContactListResult;
import com.echothree.control.user.contactlist.common.spec.PartyContactListSpec;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.ContactListContactMechanismPurpose;
import com.echothree.model.data.contactlist.server.entity.PartyContactList;
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

public class EditPartyContactListCommand
        extends BaseAbstractEditCommand<PartyContactListSpec, PartyContactListEdit, EditPartyContactListResult, PartyContactList, PartyContactList> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyContactList.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PreferredContactMechanismPurposeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }

    /** Creates a new instance of EditContactListCommand */
    public EditPartyContactListCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPartyContactListResult getResult() {
        return ContactListResultFactory.getEditPartyContactListResult();
    }

    @Override
    public PartyContactListEdit getEdit() {
        return ContactListEditFactory.getPartyContactListEdit();
    }

    ContactList contactList;
    
    @Override
    public PartyContactList getEntity(EditPartyContactListResult result) {
        var partyControl = Session.getModelController(PartyControl.class);
        PartyContactList partyContactList = null;
        var partyName = spec.getPartyName();
        var party = partyControl.getPartyByName(partyName);
        
        if(party != null) {
            var contactListControl = Session.getModelController(ContactListControl.class);
            var contactListName = spec.getContactListName();
            
            contactList = contactListControl.getContactListByName(contactListName);
            
            if(contactList != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    partyContactList = contactListControl.getPartyContactList(party, contactList);
                } else { // EditMode.UPDATE
                    partyContactList = contactListControl.getPartyContactListForUpdate(party, contactList);
                }

                if(partyContactList == null) {
                    addExecutionError(ExecutionErrors.UnknownPartyContactList.name(), partyName, contactListName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContactListName.name(), contactListName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return partyContactList;
    }

    @Override
    public PartyContactList getLockEntity(PartyContactList partyContactList) {
        return partyContactList;
    }

    @Override
    public void fillInResult(EditPartyContactListResult result, PartyContactList partyContactList) {
        var contactListControl = Session.getModelController(ContactListControl.class);

        result.setPartyContactList(contactListControl.getPartyContactListTransfer(getUserVisit(), partyContactList));
    }

    ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose;
    
    @Override
    public void doLock(PartyContactListEdit edit, PartyContactList partyContactList) {
        var partyContactListDetail = partyContactList.getLastDetail();
        
        preferredContactListContactMechanismPurpose = partyContactListDetail.getPreferredContactListContactMechanismPurpose();
        
        edit.setPreferredContactMechanismPurposeName(preferredContactListContactMechanismPurpose == null ? null : preferredContactListContactMechanismPurpose.getLastDetail().getContactMechanismPurpose().getContactMechanismPurposeName());
    }

    @Override
    public void canUpdate(PartyContactList partyContactList) {
        var contactControl = Session.getModelController(ContactControl.class);
        var preferredContactMechanismPurposeName = edit.getPreferredContactMechanismPurposeName();
        var preferredContactMechanismPurpose = preferredContactMechanismPurposeName == null ? null : contactControl.getContactMechanismPurposeByName(preferredContactMechanismPurposeName);

        if(preferredContactMechanismPurposeName == null || preferredContactMechanismPurpose != null) {
            var contactListControl = Session.getModelController(ContactListControl.class);
            
            preferredContactListContactMechanismPurpose = preferredContactMechanismPurpose == null ? null : contactListControl.getContactListContactMechanismPurpose(contactList, preferredContactMechanismPurpose);

            if(preferredContactMechanismPurpose != null && preferredContactListContactMechanismPurpose == null) {
                addExecutionError(ExecutionErrors.UnknownContactListContactMechanismPurpose.name(), contactList.getLastDetail().getContactListName(),
                        preferredContactMechanismPurposeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownContactMechanismPurposeName.name(), preferredContactMechanismPurposeName);
        }
    }

    @Override
    public void doUpdate(PartyContactList partyContactList) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var partyPK = getPartyPK();
        var partyContactListDetailValue = contactListControl.getPartyContactListDetailValueForUpdate(partyContactList);

        partyContactListDetailValue.setPreferredContactListContactMechanismPurposePK(preferredContactListContactMechanismPurpose == null ? null : preferredContactListContactMechanismPurpose.getPrimaryKey());

        contactListControl.updatePartyContactListFromValue(partyContactListDetailValue, partyPK);
    }

}
