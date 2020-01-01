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

import com.echothree.control.user.contactlist.common.form.GetCustomerTypeContactListGroupsForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.GetCustomerTypeContactListGroupsResult;
import com.echothree.model.control.contactlist.server.ContactListControl;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactListGroup;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
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

public class GetCustomerTypeContactListGroupsCommand
        extends BaseSimpleCommand<GetCustomerTypeContactListGroupsForm> {
    
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
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ContactListGroupName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetCustomerTypeContactListGroupsCommand */
    public GetCustomerTypeContactListGroupsCommand(UserVisitPK userVisitPK, GetCustomerTypeContactListGroupsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        GetCustomerTypeContactListGroupsResult result = ContactListResultFactory.getGetCustomerTypeContactListGroupsResult();
        String customerTypeName = form.getCustomerTypeName();
        String contactListGroupName = form.getContactListGroupName();
        int parameterCount = (customerTypeName != null? 1: 0) + (contactListGroupName != null? 1: 0);
        
        if(parameterCount == 1) {
            var contactListControl = (ContactListControl)Session.getModelController(ContactListControl.class);
            UserVisit userVisit = getUserVisit();
            
            if(customerTypeName != null) {
                var customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
                CustomerType customerType = customerControl.getCustomerTypeByName(customerTypeName);
                
                if(customerType != null) {
                    result.setCustomerType(customerControl.getCustomerTypeTransfer(userVisit, customerType));
                    result.setCustomerTypeContactListGroups(contactListControl.getCustomerTypeContactListGroupTransfersByCustomerType(userVisit, customerType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
                }
            } else if(contactListGroupName != null) {
                ContactListGroup contactListGroup = contactListControl.getContactListGroupByName(contactListGroupName);
                
                if(contactListGroup != null) {
                    result.setContactListGroup(contactListControl.getContactListGroupTransfer(userVisit, contactListGroup));
                    result.setCustomerTypeContactListGroups(contactListControl.getCustomerTypeContactListGroupTransfersByContactListGroup(userVisit, contactListGroup));
                } else {
                    addExecutionError(ExecutionErrors.UnknownContactListGroupName.name(), contactListGroupName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
