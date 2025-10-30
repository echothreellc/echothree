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

import com.echothree.control.user.contactlist.common.edit.ContactListEditFactory;
import com.echothree.control.user.contactlist.common.edit.CustomerTypeContactListEdit;
import com.echothree.control.user.contactlist.common.form.EditCustomerTypeContactListForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.EditCustomerTypeContactListResult;
import com.echothree.control.user.contactlist.common.spec.CustomerTypeContactListSpec;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactList;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditCustomerTypeContactListCommand
        extends BaseAbstractEditCommand<CustomerTypeContactListSpec, CustomerTypeContactListEdit, EditCustomerTypeContactListResult, CustomerTypeContactList, ContactList> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactList.name(), SecurityRoles.CustomerTypeContactList.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AddWhenCreated", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditCustomerTypeContactListCommand */
    public EditCustomerTypeContactListCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCustomerTypeContactListResult getResult() {
        return ContactListResultFactory.getEditCustomerTypeContactListResult();
    }

    @Override
    public CustomerTypeContactListEdit getEdit() {
        return ContactListEditFactory.getCustomerTypeContactListEdit();
    }

    @Override
    public CustomerTypeContactList getEntity(EditCustomerTypeContactListResult result) {
        var customerControl = Session.getModelController(CustomerControl.class);
        CustomerTypeContactList customerTypeContactList = null;
        var customerTypeName = spec.getCustomerTypeName();
        var customerType = customerControl.getCustomerTypeByName(customerTypeName);

        if(customerType != null) {
            var contactListControl = Session.getModelController(ContactListControl.class);
            var contactListName = spec.getContactListName();
            var contactList = contactListControl.getContactListByName(contactListName);

            if(contactList != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    customerTypeContactList = contactListControl.getCustomerTypeContactList(customerType, contactList);
                } else { // EditMode.UPDATE
                    customerTypeContactList = contactListControl.getCustomerTypeContactListForUpdate(customerType, contactList);
                }

                if(customerTypeContactList == null) {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypeContactList.name(), customerTypeName, contactListName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContactListName.name(), contactListName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
        }

        return customerTypeContactList;
    }

    @Override
    public ContactList getLockEntity(CustomerTypeContactList customerTypeContactList) {
        return customerTypeContactList.getContactList();
    }

    @Override
    public void fillInResult(EditCustomerTypeContactListResult result, CustomerTypeContactList customerTypeContactList) {
        var contactListControl = Session.getModelController(ContactListControl.class);

        result.setCustomerTypeContactList(contactListControl.getCustomerTypeContactListTransfer(getUserVisit(), customerTypeContactList));
    }

    @Override
    public void doLock(CustomerTypeContactListEdit edit, CustomerTypeContactList customerTypeContactList) {
        edit.setAddWhenCreated(customerTypeContactList.getAddWhenCreated().toString());
    }

    @Override
    public void doUpdate(CustomerTypeContactList customerTypeContactList) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var customerTypeContactListValue = contactListControl.getCustomerTypeContactListValue(customerTypeContactList);

        customerTypeContactListValue.setAddWhenCreated(Boolean.valueOf(edit.getAddWhenCreated()));

        contactListControl.updateCustomerTypeContactListFromValue(customerTypeContactListValue, getPartyPK());
    }
    
}
