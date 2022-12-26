// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.control.user.customer.server;

import com.echothree.control.user.customer.common.CustomerRemote;
import com.echothree.control.user.customer.common.form.*;
import com.echothree.control.user.customer.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class CustomerBean
        extends CustomerFormsImpl
        implements CustomerRemote, CustomerLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "CustomerBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Customers
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getCustomers(UserVisitPK userVisitPK, GetCustomersForm form) {
        return new GetCustomersCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCustomer(UserVisitPK userVisitPK, GetCustomerForm form) {
        return new GetCustomerCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editCustomer(UserVisitPK userVisitPK, EditCustomerForm form) {
        return new EditCustomerCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCustomerStatusChoices(UserVisitPK userVisitPK, GetCustomerStatusChoicesForm form) {
        return new GetCustomerStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setCustomerStatus(UserVisitPK userVisitPK, SetCustomerStatusForm form) {
        return new SetCustomerStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCustomerCreditStatusChoices(UserVisitPK userVisitPK, GetCustomerCreditStatusChoicesForm form) {
        return new GetCustomerCreditStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setCustomerCreditStatus(UserVisitPK userVisitPK, SetCustomerCreditStatusForm form) {
        return new SetCustomerCreditStatusCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Customer Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomerType(UserVisitPK userVisitPK, CreateCustomerTypeForm form) {
        return new CreateCustomerTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCustomerTypes(UserVisitPK userVisitPK, GetCustomerTypesForm form) {
        return new GetCustomerTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCustomerType(UserVisitPK userVisitPK, GetCustomerTypeForm form) {
        return new GetCustomerTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCustomerTypeChoices(UserVisitPK userVisitPK, GetCustomerTypeChoicesForm form) {
        return new GetCustomerTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCustomerType(UserVisitPK userVisitPK, SetDefaultCustomerTypeForm form) {
        return new SetDefaultCustomerTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCustomerType(UserVisitPK userVisitPK, EditCustomerTypeForm form) {
        return new EditCustomerTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCustomerType(UserVisitPK userVisitPK, DeleteCustomerTypeForm form) {
        return new DeleteCustomerTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Customer Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomerTypeDescription(UserVisitPK userVisitPK, CreateCustomerTypeDescriptionForm form) {
        return new CreateCustomerTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCustomerTypeDescriptions(UserVisitPK userVisitPK, GetCustomerTypeDescriptionsForm form) {
        return new GetCustomerTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCustomerTypeDescription(UserVisitPK userVisitPK, EditCustomerTypeDescriptionForm form) {
        return new EditCustomerTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCustomerTypeDescription(UserVisitPK userVisitPK, DeleteCustomerTypeDescriptionForm form) {
        return new DeleteCustomerTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Customer Type Payment Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomerTypePaymentMethod(UserVisitPK userVisitPK, CreateCustomerTypePaymentMethodForm form) {
        return new CreateCustomerTypePaymentMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCustomerTypePaymentMethod(UserVisitPK userVisitPK, GetCustomerTypePaymentMethodForm form) {
        return new GetCustomerTypePaymentMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCustomerTypePaymentMethods(UserVisitPK userVisitPK, GetCustomerTypePaymentMethodsForm form) {
        return new GetCustomerTypePaymentMethodsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCustomerTypePaymentMethod(UserVisitPK userVisitPK, SetDefaultCustomerTypePaymentMethodForm form) {
        return new SetDefaultCustomerTypePaymentMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCustomerTypePaymentMethod(UserVisitPK userVisitPK, EditCustomerTypePaymentMethodForm form) {
        return new EditCustomerTypePaymentMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCustomerTypePaymentMethod(UserVisitPK userVisitPK, DeleteCustomerTypePaymentMethodForm form) {
        return new DeleteCustomerTypePaymentMethodCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Customer Type Shipping Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomerTypeShippingMethod(UserVisitPK userVisitPK, CreateCustomerTypeShippingMethodForm form) {
        return new CreateCustomerTypeShippingMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCustomerTypeShippingMethod(UserVisitPK userVisitPK, GetCustomerTypeShippingMethodForm form) {
        return new GetCustomerTypeShippingMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCustomerTypeShippingMethods(UserVisitPK userVisitPK, GetCustomerTypeShippingMethodsForm form) {
        return new GetCustomerTypeShippingMethodsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCustomerTypeShippingMethod(UserVisitPK userVisitPK, SetDefaultCustomerTypeShippingMethodForm form) {
        return new SetDefaultCustomerTypeShippingMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCustomerTypeShippingMethod(UserVisitPK userVisitPK, EditCustomerTypeShippingMethodForm form) {
        return new EditCustomerTypeShippingMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCustomerTypeShippingMethod(UserVisitPK userVisitPK, DeleteCustomerTypeShippingMethodForm form) {
        return new DeleteCustomerTypeShippingMethodCommand(userVisitPK, form).run();
    }
    
}
