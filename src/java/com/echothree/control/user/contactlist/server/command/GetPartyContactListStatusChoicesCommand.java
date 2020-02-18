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

import com.echothree.control.user.contactlist.common.form.GetPartyContactListStatusChoicesForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.GetPartyContactListStatusChoicesResult;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactList;
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

public class GetPartyContactListStatusChoicesCommand
        extends BaseSimpleCommand<GetPartyContactListStatusChoicesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PartyContactListStatus.name(), SecurityRoles.Choices.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultPartyContactListStatusChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetPartyContactListStatusChoicesCommand */
    public GetPartyContactListStatusChoicesCommand(UserVisitPK userVisitPK, GetPartyContactListStatusChoicesForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        GetPartyContactListStatusChoicesResult result = ContactListResultFactory.getGetPartyContactListStatusChoicesResult();
        String partyName = form.getPartyName();
        String contactListName = form.getContactListName();
        int parameterCount = (partyName == null ? 0 : 1) + (contactListName == null ? 0 : 1);

        if(parameterCount == 0 || parameterCount == 2) {
            Party party = partyName == null ? null : partyControl.getPartyByName(partyName);

            if(partyName == null || party != null) {
                var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
                ContactList contactList = contactListName == null ? null : contactListControl.getContactListByName(contactListName);

                if(contactListName == null || contactList != null) {
                    PartyContactList partyContactList = party == null && contactList == null ? null : contactListControl.getPartyContactList(party, contactList);

                    if((party == null && contactList == null) || partyContactList != null) {
                        String defaultPartyContactListStatusChoice = form.getDefaultPartyContactListStatusChoice();
                        boolean allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());

                        result.setPartyContactListStatusChoices(contactListControl.getPartyContactListStatusChoices(defaultPartyContactListStatusChoice,
                                getPreferredLanguage(), allowNullChoice, partyContactList, getPartyPK()));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPartyContactList.name(), partyName, contactListName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownContactListName.name(), contactListName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        return result;
    }
    
}
