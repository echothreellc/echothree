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
import com.echothree.control.user.customer.common.edit.CustomerTypeShippingMethodEdit;
import com.echothree.control.user.customer.common.form.EditCustomerTypeShippingMethodForm;
import com.echothree.control.user.customer.common.result.CustomerResultFactory;
import com.echothree.control.user.customer.common.result.EditCustomerTypeShippingMethodResult;
import com.echothree.control.user.customer.common.spec.CustomerTypeShippingMethodSpec;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypeShippingMethod;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditCustomerTypeShippingMethodCommand
        extends BaseAbstractEditCommand<CustomerTypeShippingMethodSpec, CustomerTypeShippingMethodEdit, EditCustomerTypeShippingMethodResult, CustomerTypeShippingMethod, CustomerType> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DefaultSelectionPriority", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditCustomerTypeShippingMethodCommand */
    public EditCustomerTypeShippingMethodCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        var customerControl = Session.getModelController(CustomerControl.class);
        CustomerTypeShippingMethod customerTypeShippingMethod = null;
        var customerTypeName = spec.getCustomerTypeName();
        var customerType = customerControl.getCustomerTypeByName(customerTypeName);

        if(customerType != null) {
            var shippingControl = Session.getModelController(ShippingControl.class);
            var shippingMethodName = spec.getShippingMethodName();
            var shippingMethod = shippingControl.getShippingMethodByName(shippingMethodName);

            if(shippingMethod != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    customerTypeShippingMethod = customerControl.getCustomerTypeShippingMethod(customerType, shippingMethod);
                } else { // EditMode.UPDATE
                    customerTypeShippingMethod = customerControl.getCustomerTypeShippingMethodForUpdate(customerType, shippingMethod);
                }

                if(customerTypeShippingMethod == null) {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypeShippingMethod.name(), customerTypeName, shippingMethodName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownShippingMethodName.name(), shippingMethodName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
        }

        return customerTypeShippingMethod;
    }

    @Override
    public CustomerType getLockEntity(CustomerTypeShippingMethod customerTypeShippingMethod) {
        return customerTypeShippingMethod.getCustomerType();
    }

    @Override
    public void fillInResult(EditCustomerTypeShippingMethodResult result, CustomerTypeShippingMethod customerTypeShippingMethod) {
        var customerControl = Session.getModelController(CustomerControl.class);

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
        var customerControl = Session.getModelController(CustomerControl.class);
        var customerTypeShippingMethodValue = customerControl.getCustomerTypeShippingMethodValue(customerTypeShippingMethod);
        
        customerTypeShippingMethodValue.setDefaultSelectionPriority(Integer.valueOf(edit.getDefaultSelectionPriority()));
        customerTypeShippingMethodValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        customerTypeShippingMethodValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
        
        customerControl.updateCustomerTypeShippingMethodFromValue(customerTypeShippingMethodValue, getPartyPK());
    }
    
}
