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

import com.echothree.control.user.contactlist.common.form.GetPartyContactListStatusChoicesForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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
import javax.enterprise.context.Dependent;

@Dependent
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
    public GetPartyContactListStatusChoicesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = ContactListResultFactory.getGetPartyContactListStatusChoicesResult();
        var partyName = form.getPartyName();
        var contactListName = form.getContactListName();
        var parameterCount = (partyName == null ? 0 : 1) + (contactListName == null ? 0 : 1);

        if(parameterCount == 0 || parameterCount == 2) {
            var party = partyName == null ? null : partyControl.getPartyByName(partyName);

            if(partyName == null || party != null) {
                var contactListControl = Session.getModelController(ContactListControl.class);
                var contactList = contactListName == null ? null : contactListControl.getContactListByName(contactListName);

                if(contactListName == null || contactList != null) {
                    var partyContactList = party == null && contactList == null ? null : contactListControl.getPartyContactList(party, contactList);

                    if((party == null && contactList == null) || partyContactList != null) {
                        var defaultPartyContactListStatusChoice = form.getDefaultPartyContactListStatusChoice();
                        var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());

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
