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

import com.echothree.control.user.contactlist.common.form.SetPartyContactListStatusForm;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.PartyContactList;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetPartyContactListStatusCommand
        extends BaseSimpleCommand<SetPartyContactListStatusForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PartyContactListStatus.name(), SecurityRoles.Choices.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyContactListStatusChoice", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetPartyContactListStatusCommand */
    public SetPartyContactListStatusCommand(UserVisitPK userVisitPK, SetPartyContactListStatusForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        String partyName = form.getPartyName();
        Party party = partyControl.getPartyByName(partyName);

        if(party != null) {
            ContactListControl contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
            String contactListName = form.getContactListName();
            ContactList contactList = contactListControl.getContactListByName(contactListName);

            if(contactList != null) {
                PartyContactList partyContactList = contactListControl.getPartyContactList(party, contactList);

                if(partyContactList != null) {
                    String partyContactListStatusChoice = form.getPartyContactListStatusChoice();
                    PartyPK updatedBy = getPartyPK();

                    contactListControl.setPartyContactListStatus(this, partyContactList, partyContactListStatusChoice, updatedBy);
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyContactList.name(), partyName, contactListName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContactListName.name(), contactListName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return null;
    }

}
