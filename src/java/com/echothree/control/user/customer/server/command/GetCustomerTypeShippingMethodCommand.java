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

import com.echothree.control.user.customer.common.form.GetCustomerTypeShippingMethodForm;
import com.echothree.control.user.customer.common.result.CustomerResultFactory;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.customer.server.logic.CustomerTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.shipping.server.logic.ShippingMethodLogic;
import com.echothree.model.data.customer.server.entity.CustomerTypeShippingMethod;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetCustomerTypeShippingMethodCommand
        extends BaseSingleEntityCommand<CustomerTypeShippingMethod, GetCustomerTypeShippingMethodForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CustomerTypeShippingMethod.name(), SecurityRoles.Review.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    CustomerControl customerControl;

    @Inject
    CustomerTypeLogic customerTypeLogic;

    @Inject
    ShippingMethodLogic shippingMethodLogic;

    /** Creates a new instance of GetCustomerTypeShippingMethodCommand */
    public GetCustomerTypeShippingMethodCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected CustomerTypeShippingMethod getEntity() {
        CustomerTypeShippingMethod customerTypeShippingMethod = null;
        var customerTypeName = form.getCustomerTypeName();
        var customerType = customerTypeLogic.getCustomerTypeByName(this, customerTypeName);

        if(!hasExecutionErrors()) {
            var shippingMethodName = form.getShippingMethodName();
            var shippingMethod = shippingMethodLogic.getShippingMethodByName(this, shippingMethodName);

            if(!hasExecutionErrors()) {
                customerTypeShippingMethod = customerControl.getCustomerTypeShippingMethod(customerType, shippingMethod);

                if(customerTypeShippingMethod == null) {
                    addExecutionError(ExecutionErrors.UnknownCustomerTypeShippingMethod.name(), customerTypeName, shippingMethodName);
                }
            }
        }

        return customerTypeShippingMethod;
    }

    @Override
    protected BaseResult getResult(CustomerTypeShippingMethod customerTypeShippingMethod) {
        var result = CustomerResultFactory.getGetCustomerTypeShippingMethodResult();

        if(customerTypeShippingMethod != null) {
            result.setCustomerTypeShippingMethod(customerControl.getCustomerTypeShippingMethodTransfer(getUserVisit(), customerTypeShippingMethod));
        }

        return result;
    }

}
