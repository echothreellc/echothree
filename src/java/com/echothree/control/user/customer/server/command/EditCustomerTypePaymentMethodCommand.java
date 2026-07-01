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
import com.echothree.control.user.customer.common.edit.CustomerTypePaymentMethodEdit;
import com.echothree.control.user.customer.common.result.CustomerResultFactory;
import com.echothree.control.user.customer.common.result.EditCustomerTypePaymentMethodResult;
import com.echothree.control.user.customer.common.spec.CustomerTypePaymentMethodSpec;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.customer.server.logic.CustomerTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.payment.server.logic.PaymentMethodLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypePaymentMethod;
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
public class EditCustomerTypePaymentMethodCommand
        extends BaseAbstractEditCommand<CustomerTypePaymentMethodSpec, CustomerTypePaymentMethodEdit, EditCustomerTypePaymentMethodResult, CustomerTypePaymentMethod, CustomerType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CustomerTypePaymentMethod.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, true, null, null)
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
    PaymentMethodLogic paymentMethodLogic;

    /** Creates a new instance of EditCustomerTypePaymentMethodCommand */
    public EditCustomerTypePaymentMethodCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditCustomerTypePaymentMethodResult getResult() {
        return CustomerResultFactory.getEditCustomerTypePaymentMethodResult();
    }

    @Override
    public CustomerTypePaymentMethodEdit getEdit() {
        return CustomerEditFactory.getCustomerTypePaymentMethodEdit();
    }

    @Override
    public CustomerTypePaymentMethod getEntity(EditCustomerTypePaymentMethodResult result) {
        CustomerTypePaymentMethod customerTypePaymentMethod = null;
        var customerTypeName = spec.getCustomerTypeName();
        var customerType = customerTypeLogic.getCustomerTypeByName(this, customerTypeName);

        if(!hasExecutionErrors()) {
            var paymentMethodName = spec.getPaymentMethodName();
            var paymentMethod = paymentMethodLogic.getPaymentMethodByName(this, paymentMethodName);

            if(!hasExecutionErrors()) {
                customerTypePaymentMethod = customerControl.getCustomerTypePaymentMethod(customerType, paymentMethod, editModeToEntityPermission(editMode));

                if(customerTypePaymentMethod == null) {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypePaymentMethod.name(), customerTypeName, paymentMethodName);
                }
            }
        }

        return customerTypePaymentMethod;
    }

    @Override
    public CustomerType getLockEntity(CustomerTypePaymentMethod customerTypePaymentMethod) {
        return customerTypePaymentMethod.getCustomerType();
    }

    @Override
    public void fillInResult(EditCustomerTypePaymentMethodResult result, CustomerTypePaymentMethod customerTypePaymentMethod) {
        result.setCustomerTypePaymentMethod(customerControl.getCustomerTypePaymentMethodTransfer(getUserVisit(), customerTypePaymentMethod));
    }

    @Override
    public void doLock(CustomerTypePaymentMethodEdit edit, CustomerTypePaymentMethod customerTypePaymentMethod) {
        edit.setDefaultSelectionPriority(customerTypePaymentMethod.getDefaultSelectionPriority().toString());
        edit.setIsDefault(customerTypePaymentMethod.getIsDefault().toString());
        edit.setSortOrder(customerTypePaymentMethod.getSortOrder().toString());
    }

    @Override
    public void doUpdate(CustomerTypePaymentMethod customerTypePaymentMethod) {
        var customerTypePaymentMethodValue = customerControl.getCustomerTypePaymentMethodValue(customerTypePaymentMethod);
        
        customerTypePaymentMethodValue.setDefaultSelectionPriority(Integer.valueOf(edit.getDefaultSelectionPriority()));
        customerTypePaymentMethodValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        customerTypePaymentMethodValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
        
        customerControl.updateCustomerTypePaymentMethodFromValue(customerTypePaymentMethodValue, getPartyPK());
    }
    
}
