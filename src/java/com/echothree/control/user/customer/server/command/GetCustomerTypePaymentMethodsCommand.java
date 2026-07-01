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

import com.echothree.control.user.customer.common.form.GetCustomerTypePaymentMethodsForm;
import com.echothree.control.user.customer.common.result.CustomerResultFactory;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.customer.server.logic.CustomerTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.server.control.PaymentMethodControl;
import com.echothree.model.control.payment.server.logic.PaymentMethodLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.customer.server.entity.CustomerTypePaymentMethod;
import com.echothree.model.data.customer.server.factory.CustomerTypePaymentMethodFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetCustomerTypePaymentMethodsCommand
        extends BasePaginatedMultipleEntitiesCommand<CustomerTypePaymentMethod, GetCustomerTypePaymentMethodsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CustomerTypePaymentMethod.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    CustomerControl customerControl;

    @Inject
    CustomerTypeLogic customerTypeLogic;

    @Inject
    PaymentMethodControl paymentMethodControl;

    @Inject
    PaymentMethodLogic paymentMethodLogic;

    /** Creates a new instance of GetCustomerTypePaymentMethodsCommand */
    public GetCustomerTypePaymentMethodsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    CustomerType customerType;

    @Override
    protected void handleForm() {
        customerType = customerTypeLogic.getCustomerTypeByName(this, form.getCustomerTypeName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : customerControl.countCustomerTypePaymentMethodsByCustomerType(customerType);
    }

    @Override
    protected Collection<CustomerTypePaymentMethod> getEntities() {
        return hasExecutionErrors() ? null : customerControl.getCustomerTypePaymentMethodsByCustomerType(customerType);
    }

    @Override
    protected BaseResult getResult(Collection<CustomerTypePaymentMethod> entities) {
        var result = CustomerResultFactory.getGetCustomerTypePaymentMethodsResult();
        
        if(entities != null) {
            var userVisit = getUserVisit();

            result.setCustomerType(customerControl.getCustomerTypeTransfer(userVisit, customerType));

            if(session.hasLimit(CustomerTypePaymentMethodFactory.class)) {
                result.setCustomerTypePaymentMethodCount(getTotalEntities());
            }

            result.setCustomerTypePaymentMethods(customerControl.getCustomerTypePaymentMethodTransfers(userVisit, entities));
        }
        
        return result;
    }
    
}
