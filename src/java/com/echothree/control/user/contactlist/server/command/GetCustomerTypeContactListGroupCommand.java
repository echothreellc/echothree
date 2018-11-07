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

import com.echothree.control.user.contactlist.common.form.GetCustomerTypeContactListGroupForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.GetCustomerTypeContactListGroupResult;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactListGroup;
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactListGroup;
import com.echothree.model.data.customer.server.entity.CustomerType;
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

public class GetCustomerTypeContactListGroupCommand
        extends BaseSimpleCommand<GetCustomerTypeContactListGroupForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ContactListGroup.name(), SecurityRoles.CustomerTypeContactListGroup.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListGroupName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetCustomerTypeContactListGroupCommand */
    public GetCustomerTypeContactListGroupCommand(UserVisitPK userVisitPK, GetCustomerTypeContactListGroupForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CustomerControl customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
        GetCustomerTypeContactListGroupResult result = ContactListResultFactory.getGetCustomerTypeContactListGroupResult();
        String customerTypeName = form.getCustomerTypeName();
        CustomerType customerType = customerControl.getCustomerTypeByName(customerTypeName);
        
        if(customerType != null) {
            ContactListControl contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
            String contactListGroupName = form.getContactListGroupName();
            ContactListGroup contactListGroup = contactListControl.getContactListGroupByName(contactListGroupName);
            
            if(contactListGroup != null) {
                CustomerTypeContactListGroup customerTypeContactListGroup = contactListControl.getCustomerTypeContactListGroup(customerType, contactListGroup);
                
                if(customerTypeContactListGroup != null) {
                    result.setCustomerTypeContactListGroup(contactListControl.getCustomerTypeContactListGroupTransfer(getUserVisit(), customerTypeContactListGroup));
                } else {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypeContactListGroup.name(), customerTypeName, contactListGroupName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContactListGroupName.name(), contactListGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
        }
        
        return result;
    }
    
}
