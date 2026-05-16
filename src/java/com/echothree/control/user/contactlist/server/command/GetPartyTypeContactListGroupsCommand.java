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

import com.echothree.control.user.contactlist.common.form.GetPartyTypeContactListGroupsForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.contactlist.server.logic.ContactListGroupLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyTypeLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactListGroup;
import com.echothree.model.data.contactlist.server.entity.PartyTypeContactListGroup;
import com.echothree.model.data.contactlist.server.factory.PartyTypeContactListGroupFactory;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyTypeContactListGroupsCommand
        extends BasePaginatedMultipleEntitiesCommand<PartyTypeContactListGroup, GetPartyTypeContactListGroupsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactListGroup.name(), SecurityRoles.PartyTypeContactListGroup.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactListGroupName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    @Inject
    ContactListControl contactListControl;

    @Inject
    PartyControl partyControl;

    @Inject
    ContactListGroupLogic contactListGroupLogic;

    @Inject
    PartyTypeLogic partyTypeLogic;

    /** Creates a new instance of GetPartyTypeContactListGroupsCommand */
    public GetPartyTypeContactListGroupsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    PartyType partyType;
    ContactListGroup contactListGroup;

    @Override
    protected void handleForm() {
        var partyTypeName = form.getPartyTypeName();
        var contactListGroupName = form.getContactListGroupName();
        var parameterCount = (partyTypeName != null ? 1 : 0) + (contactListGroupName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(partyTypeName != null) {
                partyType = partyTypeLogic.getPartyTypeByName(this, partyTypeName);
            } else {
                contactListGroup = contactListGroupLogic.getContactListGroupByName(this, contactListGroupName);
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
                total = contactListControl.countPartyTypeContactListGroupsByPartyType(partyType);
            } else {
                total = contactListControl.countPartyTypeContactListGroupsByContactListGroup(contactListGroup);
            }
        }

        return total;
    }

    @Override
    protected Collection<PartyTypeContactListGroup> getEntities() {
        Collection<PartyTypeContactListGroup> entities = null;

        if(!hasExecutionErrors()) {
            if(partyType != null) {
                entities = contactListControl.getPartyTypeContactListGroupsByPartyType(partyType);
            } else {
                entities = contactListControl.getPartyTypeContactListGroupsByContactListGroup(contactListGroup);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<PartyTypeContactListGroup> entities) {
        var result = ContactListResultFactory.getGetPartyTypeContactListGroupsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(session.hasLimit(PartyTypeContactListGroupFactory.class)) {
                result.setPartyTypeContactListGroupCount(getTotalEntities());
            }

            if(partyType != null) {
                result.setPartyType(partyControl.getPartyTypeTransfer(userVisit, partyType));
            } else {
                result.setContactListGroup(contactListControl.getContactListGroupTransfer(userVisit, contactListGroup));
            }

            result.setPartyTypeContactListGroups(contactListControl.getPartyTypeContactListGroupTransfers(userVisit, entities));
        }

        return result;
    }
    
}
