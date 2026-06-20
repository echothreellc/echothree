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

import com.echothree.control.user.customer.common.form.CreateCustomerTypePaymentMethodForm;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.customer.server.logic.CustomerTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.payment.server.logic.PaymentMethodLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class CreateCustomerTypePaymentMethodCommand
        extends BaseSimpleCommand<CreateCustomerTypePaymentMethodForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.CustomerTypePaymentMethod.name(), SecurityRoles.Create.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CustomerTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PaymentMethodName", FieldType.ENTITY_NAME, true, null, null),
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

    /** Creates a new instance of CreateCustomerTypePaymentMethodCommand */
    public CreateCustomerTypePaymentMethodCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var customerTypeName = form.getCustomerTypeName();
        var customerType = customerTypeLogic.getCustomerTypeByName(this, customerTypeName);
        
        if(!hasExecutionErrors()) {
            var paymentMethodName = form.getPaymentMethodName();
            var paymentMethod = paymentMethodLogic.getPaymentMethodByName(this, paymentMethodName);
            
            if(!hasExecutionErrors()) {
                var customerTypePaymentMethod = customerControl.getCustomerTypePaymentMethod(customerType,
                        paymentMethod);
                
                if(customerTypePaymentMethod == null) {
                    var defaultSelectionPriority = Integer.valueOf(form.getDefaultSelectionPriority());
                    var isDefault = Boolean.valueOf(form.getIsDefault());
                    var sortOrder = Integer.valueOf(form.getSortOrder());
                    
                    customerControl.createCustomerTypePaymentMethod(customerType, paymentMethod, defaultSelectionPriority, isDefault, sortOrder, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.DuplicateCustomerTypePaymentMethod.name(), customerTypeName, paymentMethodName);
                }
            }
        }
        
        return null;
    }
    
}
