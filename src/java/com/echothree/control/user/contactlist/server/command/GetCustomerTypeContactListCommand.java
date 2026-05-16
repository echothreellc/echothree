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

import com.echothree.control.user.contactlist.common.form.GetCustomerTypeContactListForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.contactlist.server.logic.ContactListLogic;
import com.echothree.model.control.customer.server.logic.CustomerTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetCustomerTypeContactListCommand
        extends BaseSimpleCommand<GetCustomerTypeContactListForm> {
    
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
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetCustomerTypeContactListCommand */
    public GetCustomerTypeContactListCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Inject
    ContactListControl contactListControl;

    @Inject
    ContactListLogic contactListLogic;

    @Inject
    CustomerTypeLogic customerTypeLogic;
    
    @Override
    protected BaseResult execute() {
        var result = ContactListResultFactory.getGetCustomerTypeContactListResult();
        var customerTypeName = form.getCustomerTypeName();
        var customerType = customerTypeLogic.getCustomerTypeByName(this, customerTypeName);
        
        if(!hasExecutionErrors()) {
            var contactListName = form.getContactListName();
            var contactList = contactListLogic.getContactListByName(this, contactListName);
            
            if(!hasExecutionErrors()) {
                var customerTypeContactList = contactListControl.getCustomerTypeContactList(customerType, contactList);
                
                if(customerTypeContactList != null) {
                    result.setCustomerTypeContactList(contactListControl.getCustomerTypeContactListTransfer(getUserVisit(), customerTypeContactList));
                } else {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypeContactList.name(), customerTypeName, contactListName);
                }
            }
        }
        
        return result;
    }
    
}
