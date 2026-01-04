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

import com.echothree.control.user.customer.common.form.SetDefaultCustomerTypePaymentMethodForm;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class SetDefaultCustomerTypePaymentMethodCommand
        extends BaseSimpleCommand<SetDefaultCustomerTypePaymentMethodForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetDefaultCustomerTypePaymentMethodCommand */
    public SetDefaultCustomerTypePaymentMethodCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var customerControl = Session.getModelController(CustomerControl.class);
        var customerTypeName = form.getCustomerTypeName();
        var customerType = customerControl.getCustomerTypeByName(customerTypeName);
        
        if(customerType != null) {
            var paymentMethodControl = Session.getModelController(PaymentMethodControl.class);
            var paymentMethodName = form.getPaymentMethodName();
            var paymentMethod = paymentMethodControl.getPaymentMethodByName(paymentMethodName);
            
            if(paymentMethod != null) {
                var customerTypePaymentMethodValue = customerControl.getCustomerTypePaymentMethodValueForUpdate(customerType,
                        paymentMethod);
                
                if(customerTypePaymentMethodValue != null) {
                    customerTypePaymentMethodValue.setIsDefault(true);
                    customerControl.updateCustomerTypePaymentMethodFromValue(customerTypePaymentMethodValue, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypePaymentMethod.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPaymentMethodName.name(), paymentMethodName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
        }
        
        return null;
    }
    
}
