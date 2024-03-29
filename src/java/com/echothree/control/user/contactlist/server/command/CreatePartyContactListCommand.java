// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.control.user.contactlist.common.form.CreatePartyContactListForm;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.contactlist.server.logic.ContactListLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.ContactListContactMechanismPurpose;
import com.echothree.model.data.contactlist.server.entity.PartyContactList;
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

public class CreatePartyContactListCommand
        extends BaseSimpleCommand<CreatePartyContactListForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyContactList.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PreferredContactMechanismPurposeName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePartyContactListCommand */
    public CreatePartyContactListCommand(UserVisitPK userVisitPK, CreatePartyContactListForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        String partyName = form.getPartyName();
        Party party = partyControl.getPartyByName(partyName);
        
        if(party != null) {
            var contactListControl = Session.getModelController(ContactListControl.class);
            String contactListName = form.getContactListName();
            ContactList contactList = contactListControl.getContactListByName(contactListName);
            
            if(contactList != null) {
                PartyContactList partyContactList = contactListControl.getPartyContactList(party, contactList);
                
                if(partyContactList == null) {
                    var contactControl = Session.getModelController(ContactControl.class);
                    String preferredContactMechanismPurposeName = form.getPreferredContactMechanismPurposeName();
                    ContactMechanismPurpose preferredContactMechanismPurpose = preferredContactMechanismPurposeName == null ? null : contactControl.getContactMechanismPurposeByName(preferredContactMechanismPurposeName);

                    if(preferredContactMechanismPurposeName == null || preferredContactMechanismPurpose != null) {
                        ContactListContactMechanismPurpose preferredContactListContactMechanismPurpose = preferredContactMechanismPurpose == null ? null : contactListControl.getContactListContactMechanismPurpose(contactList, preferredContactMechanismPurpose);

                        if(preferredContactMechanismPurpose == null || preferredContactListContactMechanismPurpose != null) {
                            // ExecutionErrorAccumulator is passed in as null so that an Exception will be thrown if there is an error.
                            ContactListLogic.getInstance().addContactListToParty(null, party, contactList, preferredContactListContactMechanismPurpose, getPartyPK());
                        } else {
                            addExecutionError(ExecutionErrors.UnknownContactListContactMechanismPurpose.name(), contactListName, preferredContactMechanismPurposeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownContactMechanismPurposeName.name(), preferredContactMechanismPurposeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicatePartyContactList.name(), partyName, contactListName);
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
