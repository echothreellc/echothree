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

import com.echothree.control.user.contactlist.common.edit.ContactListEditFactory;
import com.echothree.control.user.contactlist.common.edit.CustomerTypeContactListGroupEdit;
import com.echothree.control.user.contactlist.common.form.EditCustomerTypeContactListGroupForm;
import com.echothree.control.user.contactlist.common.result.ContactListResultFactory;
import com.echothree.control.user.contactlist.common.result.EditCustomerTypeContactListGroupResult;
import com.echothree.control.user.contactlist.common.spec.CustomerTypeContactListGroupSpec;
import com.echothree.model.control.contactlist.server.control.ContactListControl;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.contactlist.server.entity.ContactListGroup;
import com.echothree.model.data.contactlist.server.entity.CustomerTypeContactListGroup;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditCustomerTypeContactListGroupCommand
        extends BaseAbstractEditCommand<CustomerTypeContactListGroupSpec, CustomerTypeContactListGroupEdit, EditCustomerTypeContactListGroupResult, CustomerTypeContactListGroup, ContactListGroup> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ContactListGroup.name(), SecurityRoles.CustomerTypeContactListGroup.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactListGroupName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AddWhenCreated", FieldType.BOOLEAN, true, null, null)
                ));
    }

    /** Creates a new instance of EditCustomerTypeContactListGroupCommand */
    public EditCustomerTypeContactListGroupCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditCustomerTypeContactListGroupResult getResult() {
        return ContactListResultFactory.getEditCustomerTypeContactListGroupResult();
    }

    @Override
    public CustomerTypeContactListGroupEdit getEdit() {
        return ContactListEditFactory.getCustomerTypeContactListGroupEdit();
    }

    @Override
    public CustomerTypeContactListGroup getEntity(EditCustomerTypeContactListGroupResult result) {
        var customerControl = Session.getModelController(CustomerControl.class);
        CustomerTypeContactListGroup customerTypeContactListGroup = null;
        var customerTypeName = spec.getCustomerTypeName();
        var customerType = customerControl.getCustomerTypeByName(customerTypeName);

        if(customerType != null) {
            var contactListControl = Session.getModelController(ContactListControl.class);
            var contactListGroupName = spec.getContactListGroupName();
            var contactListGroup = contactListControl.getContactListGroupByName(contactListGroupName);

            if(contactListGroup != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    customerTypeContactListGroup = contactListControl.getCustomerTypeContactListGroup(customerType, contactListGroup);
                } else { // EditMode.UPDATE
                    customerTypeContactListGroup = contactListControl.getCustomerTypeContactListGroupForUpdate(customerType, contactListGroup);
                }

                if(customerTypeContactListGroup == null) {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypeContactListGroup.name(), customerTypeName, contactListGroupName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContactListGroupName.name(), contactListGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
        }

        return customerTypeContactListGroup;
    }

    @Override
    public ContactListGroup getLockEntity(CustomerTypeContactListGroup customerTypeContactListGroup) {
        return customerTypeContactListGroup.getContactListGroup();
    }

    @Override
    public void fillInResult(EditCustomerTypeContactListGroupResult result, CustomerTypeContactListGroup customerTypeContactListGroup) {
        var contactListControl = Session.getModelController(ContactListControl.class);

        result.setCustomerTypeContactListGroup(contactListControl.getCustomerTypeContactListGroupTransfer(getUserVisit(), customerTypeContactListGroup));
    }

    @Override
    public void doLock(CustomerTypeContactListGroupEdit edit, CustomerTypeContactListGroup customerTypeContactListGroup) {
        edit.setAddWhenCreated(customerTypeContactListGroup.getAddWhenCreated().toString());
    }

    @Override
    public void doUpdate(CustomerTypeContactListGroup customerTypeContactListGroup) {
        var contactListControl = Session.getModelController(ContactListControl.class);
        var customerTypeContactListGroupValue = contactListControl.getCustomerTypeContactListGroupValue(customerTypeContactListGroup);

        customerTypeContactListGroupValue.setAddWhenCreated(Boolean.valueOf(edit.getAddWhenCreated()));

        contactListControl.updateCustomerTypeContactListGroupFromValue(customerTypeContactListGroupValue, getPartyPK());
    }

}
