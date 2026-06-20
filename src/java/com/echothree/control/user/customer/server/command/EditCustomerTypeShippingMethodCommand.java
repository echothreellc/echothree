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

package com.echothree.control.user.customer.server.command;

import com.echothree.control.user.customer.common.edit.CustomerEditFactory;
import com.echothree.control.user.customer.common.edit.CustomerTypeShippingMethodEdit;
import com.echothree.control.user.customer.common.result.CustomerResultFactory;
import com.echothree.control.user.customer.common.result.EditCustomerTypeShippingMethodResult;
import com.echothree.control.user.customer.common.spec.CustomerTypeShippingMethodSpec;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.customer.server.logic.CustomerTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipping.server.logic.ShippingMethodLogic;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypeShippingMethod;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditCustomerTypeShippingMethodCommand
        extends BaseAbstractEditCommand<CustomerTypeShippingMethodSpec, CustomerTypeShippingMethodEdit, EditCustomerTypeShippingMethodResult, CustomerTypeShippingMethod, CustomerType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CustomerTypeShippingMethod.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("DefaultSelectionPriority", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }

    @Inject
    CustomerControl customerControl;

    @Inject
    CustomerTypeLogic customerTypeLogic;

    @Inject
    ShippingMethodLogic shippingMethodLogic;

    /** Creates a new instance of EditCustomerTypeShippingMethodCommand */
    public EditCustomerTypeShippingMethodCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCustomerTypeShippingMethodResult getResult() {
        return CustomerResultFactory.getEditCustomerTypeShippingMethodResult();
    }

    @Override
    public CustomerTypeShippingMethodEdit getEdit() {
        return CustomerEditFactory.getCustomerTypeShippingMethodEdit();
    }

    @Override
    public CustomerTypeShippingMethod getEntity(EditCustomerTypeShippingMethodResult result) {
        CustomerTypeShippingMethod customerTypeShippingMethod = null;
        var customerTypeName = spec.getCustomerTypeName();
        var customerType = customerTypeLogic.getCustomerTypeByName(this, customerTypeName);

        if(!hasExecutionErrors()) {
            var shippingMethodName = spec.getShippingMethodName();
            var shippingMethod = shippingMethodLogic.getShippingMethodByName(this, shippingMethodName);

            if(!hasExecutionErrors()) {
                customerTypeShippingMethod = customerControl.getCustomerTypeShippingMethod(customerType, shippingMethod, editModeToEntityPermission(editMode));

                if(customerTypeShippingMethod == null) {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypeShippingMethod.name(), customerTypeName, shippingMethodName);
                }
            }
        }

        return customerTypeShippingMethod;
    }

    @Override
    public CustomerType getLockEntity(CustomerTypeShippingMethod customerTypeShippingMethod) {
        return customerTypeShippingMethod.getCustomerType();
    }

    @Override
    public void fillInResult(EditCustomerTypeShippingMethodResult result, CustomerTypeShippingMethod customerTypeShippingMethod) {
        result.setCustomerTypeShippingMethod(customerControl.getCustomerTypeShippingMethodTransfer(getUserVisit(), customerTypeShippingMethod));
    }

    @Override
    public void doLock(CustomerTypeShippingMethodEdit edit, CustomerTypeShippingMethod customerTypeShippingMethod) {
        edit.setDefaultSelectionPriority(customerTypeShippingMethod.getDefaultSelectionPriority().toString());
        edit.setIsDefault(customerTypeShippingMethod.getIsDefault().toString());
        edit.setSortOrder(customerTypeShippingMethod.getSortOrder().toString());
    }

    @Override
    public void doUpdate(CustomerTypeShippingMethod customerTypeShippingMethod) {
        var customerTypeShippingMethodValue = customerControl.getCustomerTypeShippingMethodValue(customerTypeShippingMethod);
        
        customerTypeShippingMethodValue.setDefaultSelectionPriority(Integer.valueOf(edit.getDefaultSelectionPriority()));
        customerTypeShippingMethodValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        customerTypeShippingMethodValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
        
        customerControl.updateCustomerTypeShippingMethodFromValue(customerTypeShippingMethodValue, getPartyPK());
    }
    
}
