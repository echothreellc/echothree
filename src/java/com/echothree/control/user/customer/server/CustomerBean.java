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
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(GetCustomersCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCustomer(UserVisitPK userVisitPK, GetCustomerForm form) {
        return CDI.current().select(GetCustomerCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editCustomer(UserVisitPK userVisitPK, EditCustomerForm form) {
        return CDI.current().select(EditCustomerCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerStatusChoices(UserVisitPK userVisitPK, GetCustomerStatusChoicesForm form) {
        return CDI.current().select(GetCustomerStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setCustomerStatus(UserVisitPK userVisitPK, SetCustomerStatusForm form) {
        return CDI.current().select(SetCustomerStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerCreditStatusChoices(UserVisitPK userVisitPK, GetCustomerCreditStatusChoicesForm form) {
        return CDI.current().select(GetCustomerCreditStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setCustomerCreditStatus(UserVisitPK userVisitPK, SetCustomerCreditStatusForm form) {
        return CDI.current().select(SetCustomerCreditStatusCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Customer Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomerType(UserVisitPK userVisitPK, CreateCustomerTypeForm form) {
        return CDI.current().select(CreateCustomerTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerTypes(UserVisitPK userVisitPK, GetCustomerTypesForm form) {
        return CDI.current().select(GetCustomerTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerType(UserVisitPK userVisitPK, GetCustomerTypeForm form) {
        return CDI.current().select(GetCustomerTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerTypeChoices(UserVisitPK userVisitPK, GetCustomerTypeChoicesForm form) {
        return CDI.current().select(GetCustomerTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCustomerType(UserVisitPK userVisitPK, SetDefaultCustomerTypeForm form) {
        return CDI.current().select(SetDefaultCustomerTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCustomerType(UserVisitPK userVisitPK, EditCustomerTypeForm form) {
        return CDI.current().select(EditCustomerTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCustomerType(UserVisitPK userVisitPK, DeleteCustomerTypeForm form) {
        return CDI.current().select(DeleteCustomerTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Customer Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomerTypeDescription(UserVisitPK userVisitPK, CreateCustomerTypeDescriptionForm form) {
        return CDI.current().select(CreateCustomerTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerTypeDescriptions(UserVisitPK userVisitPK, GetCustomerTypeDescriptionsForm form) {
        return CDI.current().select(GetCustomerTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCustomerTypeDescription(UserVisitPK userVisitPK, EditCustomerTypeDescriptionForm form) {
        return CDI.current().select(EditCustomerTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCustomerTypeDescription(UserVisitPK userVisitPK, DeleteCustomerTypeDescriptionForm form) {
        return CDI.current().select(DeleteCustomerTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Customer Type Payment Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomerTypePaymentMethod(UserVisitPK userVisitPK, CreateCustomerTypePaymentMethodForm form) {
        return CDI.current().select(CreateCustomerTypePaymentMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerTypePaymentMethod(UserVisitPK userVisitPK, GetCustomerTypePaymentMethodForm form) {
        return CDI.current().select(GetCustomerTypePaymentMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerTypePaymentMethods(UserVisitPK userVisitPK, GetCustomerTypePaymentMethodsForm form) {
        return CDI.current().select(GetCustomerTypePaymentMethodsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCustomerTypePaymentMethod(UserVisitPK userVisitPK, SetDefaultCustomerTypePaymentMethodForm form) {
        return CDI.current().select(SetDefaultCustomerTypePaymentMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCustomerTypePaymentMethod(UserVisitPK userVisitPK, EditCustomerTypePaymentMethodForm form) {
        return CDI.current().select(EditCustomerTypePaymentMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCustomerTypePaymentMethod(UserVisitPK userVisitPK, DeleteCustomerTypePaymentMethodForm form) {
        return CDI.current().select(DeleteCustomerTypePaymentMethodCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Customer Type Shipping Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCustomerTypeShippingMethod(UserVisitPK userVisitPK, CreateCustomerTypeShippingMethodForm form) {
        return CDI.current().select(CreateCustomerTypeShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerTypeShippingMethod(UserVisitPK userVisitPK, GetCustomerTypeShippingMethodForm form) {
        return CDI.current().select(GetCustomerTypeShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCustomerTypeShippingMethods(UserVisitPK userVisitPK, GetCustomerTypeShippingMethodsForm form) {
        return CDI.current().select(GetCustomerTypeShippingMethodsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCustomerTypeShippingMethod(UserVisitPK userVisitPK, SetDefaultCustomerTypeShippingMethodForm form) {
        return CDI.current().select(SetDefaultCustomerTypeShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCustomerTypeShippingMethod(UserVisitPK userVisitPK, EditCustomerTypeShippingMethodForm form) {
        return CDI.current().select(EditCustomerTypeShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCustomerTypeShippingMethod(UserVisitPK userVisitPK, DeleteCustomerTypeShippingMethodForm form) {
        return CDI.current().select(DeleteCustomerTypeShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
}
