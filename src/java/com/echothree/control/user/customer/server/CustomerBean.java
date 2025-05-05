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
        return new GetCustomersCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCustomer(UserVisitPK userVisitPK, GetCustomerForm form) {
        return new GetCustomerCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editCustomer(UserVisitPK userVisitPK, EditCustomerForm form) {
        return new EditCustomerCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerStatusChoices(UserVisitPK userVisitPK, GetCustomerStatusChoicesForm form) {
        return new GetCustomerStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setCustomerStatus(UserVisitPK userVisitPK, SetCustomerStatusForm form) {
        return new SetCustomerStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerCreditStatusChoices(UserVisitPK userVisitPK, GetCustomerCreditStatusChoicesForm form) {
        return new GetCustomerCreditStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setCustomerCreditStatus(UserVisitPK userVisitPK, SetCustomerCreditStatusForm form) {
        return new SetCustomerCreditStatusCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Customer Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomerType(UserVisitPK userVisitPK, CreateCustomerTypeForm form) {
        return new CreateCustomerTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerTypes(UserVisitPK userVisitPK, GetCustomerTypesForm form) {
        return new GetCustomerTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerType(UserVisitPK userVisitPK, GetCustomerTypeForm form) {
        return new GetCustomerTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerTypeChoices(UserVisitPK userVisitPK, GetCustomerTypeChoicesForm form) {
        return new GetCustomerTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCustomerType(UserVisitPK userVisitPK, SetDefaultCustomerTypeForm form) {
        return new SetDefaultCustomerTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCustomerType(UserVisitPK userVisitPK, EditCustomerTypeForm form) {
        return new EditCustomerTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCustomerType(UserVisitPK userVisitPK, DeleteCustomerTypeForm form) {
        return new DeleteCustomerTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Customer Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomerTypeDescription(UserVisitPK userVisitPK, CreateCustomerTypeDescriptionForm form) {
        return new CreateCustomerTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerTypeDescriptions(UserVisitPK userVisitPK, GetCustomerTypeDescriptionsForm form) {
        return new GetCustomerTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCustomerTypeDescription(UserVisitPK userVisitPK, EditCustomerTypeDescriptionForm form) {
        return new EditCustomerTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCustomerTypeDescription(UserVisitPK userVisitPK, DeleteCustomerTypeDescriptionForm form) {
        return new DeleteCustomerTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Customer Type Payment Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomerTypePaymentMethod(UserVisitPK userVisitPK, CreateCustomerTypePaymentMethodForm form) {
        return new CreateCustomerTypePaymentMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerTypePaymentMethod(UserVisitPK userVisitPK, GetCustomerTypePaymentMethodForm form) {
        return new GetCustomerTypePaymentMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerTypePaymentMethods(UserVisitPK userVisitPK, GetCustomerTypePaymentMethodsForm form) {
        return new GetCustomerTypePaymentMethodsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCustomerTypePaymentMethod(UserVisitPK userVisitPK, SetDefaultCustomerTypePaymentMethodForm form) {
        return new SetDefaultCustomerTypePaymentMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCustomerTypePaymentMethod(UserVisitPK userVisitPK, EditCustomerTypePaymentMethodForm form) {
        return new EditCustomerTypePaymentMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCustomerTypePaymentMethod(UserVisitPK userVisitPK, DeleteCustomerTypePaymentMethodForm form) {
        return new DeleteCustomerTypePaymentMethodCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Customer Type Shipping Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomerTypeShippingMethod(UserVisitPK userVisitPK, CreateCustomerTypeShippingMethodForm form) {
        return new CreateCustomerTypeShippingMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerTypeShippingMethod(UserVisitPK userVisitPK, GetCustomerTypeShippingMethodForm form) {
        return new GetCustomerTypeShippingMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerTypeShippingMethods(UserVisitPK userVisitPK, GetCustomerTypeShippingMethodsForm form) {
        return new GetCustomerTypeShippingMethodsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCustomerTypeShippingMethod(UserVisitPK userVisitPK, SetDefaultCustomerTypeShippingMethodForm form) {
        return new SetDefaultCustomerTypeShippingMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCustomerTypeShippingMethod(UserVisitPK userVisitPK, EditCustomerTypeShippingMethodForm form) {
        return new EditCustomerTypeShippingMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCustomerTypeShippingMethod(UserVisitPK userVisitPK, DeleteCustomerTypeShippingMethodForm form) {
        return new DeleteCustomerTypeShippingMethodCommand().run(userVisitPK, form);
    }
    
}
