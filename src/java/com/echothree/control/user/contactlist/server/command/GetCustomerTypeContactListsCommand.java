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

import com.echothree.control.user.contactlist.common.form.GetCustomerTypeContactListsForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.contactlist.server.logic.ContactListLogic;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.customer.server.logic.CustomerTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactList;
import com.echothree.model.data.contactlist.server.factory.CustomerTypeContactListFactory;
import com.echothree.model.data.customer.server.entity.CustomerType;
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
public class GetCustomerTypeContactListsCommand
        extends BasePaginatedMultipleEntitiesCommand<CustomerTypeContactList, GetCustomerTypeContactListsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactList.name(), SecurityRoles.CustomerTypeContactList.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    ContactListControl contactListControl;

    @Inject
    CustomerControl customerControl;

    @Inject
    ContactListLogic contactListLogic;

    @Inject
    CustomerTypeLogic customerTypeLogic;
    
    /** Creates a new instance of GetCustomerTypeContactListsCommand */
    public GetCustomerTypeContactListsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    private CustomerType customerType;
    private ContactList contactList;

    @Override
    protected void handleForm() {
        var customerTypeName = form.getCustomerTypeName();
        var contactListName = form.getContactListName();
        var parameterCount = (customerTypeName != null ? 1 : 0) + (contactListName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(customerTypeName != null) {
                customerType = customerTypeLogic.getCustomerTypeByName(this, customerTypeName);
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
            if(customerType != null) {
                total = contactListControl.countCustomerTypeContactListsByCustomerType(customerType);
            } else {
                total = contactListControl.countCustomerTypeContactListsByContactList(contactList);
            }
        }

        return total;
    }

    @Override
    protected Collection<CustomerTypeContactList> getEntities() {
        Collection<CustomerTypeContactList> entities = null;

        if(!hasExecutionErrors()) {
            if(customerType != null) {
                entities = contactListControl.getCustomerTypeContactListsByCustomerType(customerType);
            } else {
                entities = contactListControl.getCustomerTypeContactListsByContactList(contactList);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<CustomerTypeContactList> entities) {
        var result = ContactListResultFactory.getGetCustomerTypeContactListsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(customerType != null) {
                result.setCustomerType(customerControl.getCustomerTypeTransfer(userVisit, customerType));
            } else {
                result.setContactList(contactListControl.getContactListTransfer(userVisit, contactList));
            }

            if(session.hasLimit(CustomerTypeContactListFactory.class)) {
                result.setCustomerTypeContactListCount(getTotalEntities());
            }

            result.setCustomerTypeContactLists(contactListControl.getCustomerTypeContactListTransfers(userVisit, entities));
        }

        return result;
    }
    
}
