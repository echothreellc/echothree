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

package com.echothree.control.user.customer.server.command;

import com.echothree.control.user.customer.common.form.CreateCustomerTypeShippingMethodForm;
import com.echothree.model.control.customer.server.CustomerControl;
import com.echothree.model.control.shipping.server.ShippingControl;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypeShippingMethod;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
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

public class CreateCustomerTypeShippingMethodCommand
        extends BaseSimpleCommand<CreateCustomerTypeShippingMethodForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DefaultSelectionPriority", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateCustomerTypeShippingMethodCommand */
    public CreateCustomerTypeShippingMethodCommand(UserVisitPK userVisitPK, CreateCustomerTypeShippingMethodForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        CustomerControl customerControl = (CustomerControl)Session.getModelController(CustomerControl.class);
        String customerTypeName = form.getCustomerTypeName();
        CustomerType customerType = customerControl.getCustomerTypeByName(customerTypeName);
        
        if(customerType != null) {
            ShippingControl shippingControl = (ShippingControl)Session.getModelController(ShippingControl.class);
            String shippingMethodName = form.getShippingMethodName();
            ShippingMethod shippingMethod = shippingControl.getShippingMethodByName(shippingMethodName);
            
            if(shippingMethod != null) {
                CustomerTypeShippingMethod customerTypeShippingMethod = customerControl.getCustomerTypeShippingMethod(customerType,
                        shippingMethod);
                
                if(customerTypeShippingMethod == null) {
                    Integer defaultSelectionPriority = Integer.valueOf(form.getDefaultSelectionPriority());
                    Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                    Integer sortOrder = Integer.valueOf(form.getSortOrder());
                    
                    customerControl.createCustomerTypeShippingMethod(customerType, shippingMethod, defaultSelectionPriority, isDefault, sortOrder, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.DuplicateCustomerTypeShippingMethod.name());
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
