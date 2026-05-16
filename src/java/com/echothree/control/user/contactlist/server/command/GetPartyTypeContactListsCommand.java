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

import com.echothree.control.user.contactlist.common.form.GetPartyTypeContactListsForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.contactlist.server.logic.ContactListLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.PartyTypeContactList;
import com.echothree.model.data.contactlist.server.factory.PartyTypeContactListFactory;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyTypeContactListsCommand
        extends BasePaginatedMultipleEntitiesCommand<PartyTypeContactList, GetPartyTypeContactListsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactList.name(), SecurityRoles.PartyTypeContactList.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    ContactListControl contactListControl;

    @Inject
    PartyControl partyControl;

    @Inject
    ContactListLogic contactListLogic;

    @Inject
    PartyLogic partyLogic;
    
    /** Creates a new instance of GetPartyTypeContactListsCommand */
    public GetPartyTypeContactListsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private PartyType partyType;
    private ContactList contactList;

    @Override
    protected void handleForm() {
        var partyTypeName = form.getPartyTypeName();
        var contactListName = form.getContactListName();
        var parameterCount = (partyTypeName != null ? 1 : 0) + (contactListName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(partyTypeName != null) {
                partyType = partyLogic.getPartyTypeByName(this, partyTypeName);
            } else {
                contactList = contactListLogic.getContactListByName(this, contactListName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(partyType != null) {
                total = contactListControl.countPartyTypeContactListsByPartyType(partyType);
            } else {
                total = contactListControl.countPartyTypeContactListsByContactList(contactList);
            }
        }

        return total;
    }

    @Override
    protected Collection<PartyTypeContactList> getEntities() {
        Collection<PartyTypeContactList> entities = null;

        if(!hasExecutionErrors()) {
            if(partyType != null) {
                entities = contactListControl.getPartyTypeContactListsByPartyType(partyType);
            } else {
                entities = contactListControl.getPartyTypeContactListsByContactList(contactList);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<PartyTypeContactList> entities) {
        var result = ContactListResultFactory.getGetPartyTypeContactListsResult();

        if(!hasExecutionErrors()) {
            var userVisit = getUserVisit();

            if(session.hasLimit(PartyTypeContactListFactory.class)) {
                result.setPartyTypeContactListCount(getTotalEntities());
            }

            if(partyType != null) {
                result.setPartyType(partyControl.getPartyTypeTransfer(userVisit, partyType));
            } else {
                result.setContactList(contactListControl.getContactListTransfer(userVisit, contactList));
            }

            result.setPartyTypeContactLists(contactListControl.getPartyTypeContactListTransfers(userVisit, entities));
        }

        return result;
    }
    
}
