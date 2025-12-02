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

package com.echothree.control.user.customer.server.command;

import com.echothree.control.user.customer.common.edit.CustomerEditFactory;
import com.echothree.control.user.customer.common.edit.CustomerTypePaymentMethodEdit;
import com.echothree.control.user.customer.common.form.EditCustomerTypePaymentMethodForm;
import com.echothree.control.user.customer.common.result.CustomerResultFactory;
import com.echothree.control.user.customer.common.result.EditCustomerTypePaymentMethodResult;
import com.echothree.control.user.customer.common.spec.CustomerTypePaymentMethodSpec;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypePaymentMethod;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditCustomerTypePaymentMethodCommand
        extends BaseAbstractEditCommand<CustomerTypePaymentMethodSpec, CustomerTypePaymentMethodEdit, EditCustomerTypePaymentMethodResult, CustomerTypePaymentMethod, CustomerType> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DefaultSelectionPriority", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditCustomerTypePaymentMethodCommand */
    public EditCustomerTypePaymentMethodCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        var customerControl = Session.getModelController(CustomerControl.class);
        CustomerTypePaymentMethod customerTypePaymentMethod = null;
        var customerTypeName = spec.getCustomerTypeName();
        var customerType = customerControl.getCustomerTypeByName(customerTypeName);

        if(customerType != null) {
            var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
            var paymentMethodName = spec.getPaymentMethodName();
            var paymentMethod = paymentMethodControl.getPaymentMethodByName(paymentMethodName);

            if(paymentMethod != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    customerTypePaymentMethod = customerControl.getCustomerTypePaymentMethod(customerType, paymentMethod);
                } else { // EditMode.UPDATE
                    customerTypePaymentMethod = customerControl.getCustomerTypePaymentMethodForUpdate(customerType, paymentMethod);
                }

                if(customerTypePaymentMethod == null) {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypePaymentMethod.name(), customerTypeName, paymentMethodName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPaymentMethodName.name(), paymentMethodName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
        }

        return customerTypePaymentMethod;
    }

    @Override
    public CustomerType getLockEntity(CustomerTypePaymentMethod customerTypePaymentMethod) {
        return customerTypePaymentMethod.getCustomerType();
    }

    @Override
    public void fillInResult(EditCustomerTypePaymentMethodResult result, CustomerTypePaymentMethod customerTypePaymentMethod) {
        var customerControl = Session.getModelController(CustomerControl.class);

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
        var customerControl = Session.getModelController(CustomerControl.class);
        var customerTypePaymentMethodValue = customerControl.getCustomerTypePaymentMethodValue(customerTypePaymentMethod);
        
        customerTypePaymentMethodValue.setDefaultSelectionPriority(Integer.valueOf(edit.getDefaultSelectionPriority()));
        customerTypePaymentMethodValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        customerTypePaymentMethodValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
        
        customerControl.updateCustomerTypePaymentMethodFromValue(customerTypePaymentMethodValue, getPartyPK());
    }
    
}
