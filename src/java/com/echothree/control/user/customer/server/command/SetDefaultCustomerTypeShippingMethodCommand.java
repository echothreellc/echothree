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

import com.echothree.control.user.customer.common.form.SetDefaultCustomerTypeShippingMethodForm;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetDefaultCustomerTypeShippingMethodCommand
        extends BaseSimpleCommand<SetDefaultCustomerTypeShippingMethodForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetDefaultCustomerTypeShippingMethodCommand */
    public SetDefaultCustomerTypeShippingMethodCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var customerControl = Session.getModelController(CustomerControl.class);
        var customerTypeName = form.getCustomerTypeName();
        var customerType = customerControl.getCustomerTypeByName(customerTypeName);
        
        if(customerType != null) {
            var shippingControl = Session.getModelController(ShippingControl.class);
            var shippingMethodName = form.getShippingMethodName();
            var shippingMethod = shippingControl.getShippingMethodByName(shippingMethodName);
            
            if(shippingMethod != null) {
                var customerTypeShippingMethodValue = customerControl.getCustomerTypeShippingMethodValueForUpdate(customerType,
                        shippingMethod);
                
                if(customerTypeShippingMethodValue != null) {
                    customerTypeShippingMethodValue.setIsDefault(true);
                    customerControl.updateCustomerTypeShippingMethodFromValue(customerTypeShippingMethodValue, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypeShippingMethod.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownShippingMethodName.name(), shippingMethodName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
        }
        
        return null;
    }
    
}
