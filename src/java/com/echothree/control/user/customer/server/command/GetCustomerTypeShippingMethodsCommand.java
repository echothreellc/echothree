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

import com.echothree.control.user.customer.common.form.GetCustomerTypeShippingMethodsForm;
import com.echothree.control.user.customer.common.result.CustomerResultFactory;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.customer.server.logic.CustomerTypeLogic;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypeShippingMethod;
import com.echothree.model.data.customer.server.factory.CustomerTypeShippingMethodFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetCustomerTypeShippingMethodsCommand
        extends BasePaginatedMultipleEntitiesCommand<CustomerTypeShippingMethod, GetCustomerTypeShippingMethodsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    CustomerControl customerControl;

    @Inject
    CustomerTypeLogic customerTypeLogic;

    /** Creates a new instance of GetCustomerTypeShippingMethodsCommand */
    public GetCustomerTypeShippingMethodsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    CustomerType customerType;

    @Override
    protected void handleForm() {
        customerType = customerTypeLogic.getCustomerTypeByName(this, form.getCustomerTypeName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : customerControl.countCustomerTypeShippingMethodsByCustomerType(customerType);
    }

    @Override
    protected Collection<CustomerTypeShippingMethod> getEntities() {
        return hasExecutionErrors() ? null : customerControl.getCustomerTypeShippingMethodsByCustomerType(customerType);
    }

    @Override
    protected BaseResult getResult(Collection<CustomerTypeShippingMethod> entities) {
        var result = CustomerResultFactory.getGetCustomerTypeShippingMethodsResult();

        if(entities != null) {
            result.setCustomerType(customerControl.getCustomerTypeTransfer(getUserVisit(), customerType));

            if(session.hasLimit(CustomerTypeShippingMethodFactory.class)) {
                result.setCustomerTypeShippingMethodCount(getTotalEntities());
            }

            result.setCustomerTypeShippingMethods(customerControl.getCustomerTypeShippingMethodTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
