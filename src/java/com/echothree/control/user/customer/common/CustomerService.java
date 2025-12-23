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

package com.echothree.control.user.customer.common;

import com.echothree.control.user.customer.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface CustomerService
        extends CustomerForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Customers
    // -------------------------------------------------------------------------

    CommandResult getCustomers(UserVisitPK userVisitPK, GetCustomersForm form);

    CommandResult getCustomer(UserVisitPK userVisitPK, GetCustomerForm form);

    CommandResult editCustomer(UserVisitPK userVisitPK, EditCustomerForm form);
    
    CommandResult getCustomerStatusChoices(UserVisitPK userVisitPK, GetCustomerStatusChoicesForm form);
    
    CommandResult setCustomerStatus(UserVisitPK userVisitPK, SetCustomerStatusForm form);
    
    CommandResult getCustomerCreditStatusChoices(UserVisitPK userVisitPK, GetCustomerCreditStatusChoicesForm form);
    
    CommandResult setCustomerCreditStatus(UserVisitPK userVisitPK, SetCustomerCreditStatusForm form);
    
    // -------------------------------------------------------------------------
    //   Customer Types
    // -------------------------------------------------------------------------
    
    CommandResult createCustomerType(UserVisitPK userVisitPK, CreateCustomerTypeForm form);
    
    CommandResult getCustomerTypes(UserVisitPK userVisitPK, GetCustomerTypesForm form);
    
    CommandResult getCustomerType(UserVisitPK userVisitPK, GetCustomerTypeForm form);
    
    CommandResult getCustomerTypeChoices(UserVisitPK userVisitPK, GetCustomerTypeChoicesForm form);
    
    CommandResult setDefaultCustomerType(UserVisitPK userVisitPK, SetDefaultCustomerTypeForm form);
    
    CommandResult editCustomerType(UserVisitPK userVisitPK, EditCustomerTypeForm form);
    
    CommandResult deleteCustomerType(UserVisitPK userVisitPK, DeleteCustomerTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Customer Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createCustomerTypeDescription(UserVisitPK userVisitPK, CreateCustomerTypeDescriptionForm form);
    
    CommandResult getCustomerTypeDescriptions(UserVisitPK userVisitPK, GetCustomerTypeDescriptionsForm form);
    
    CommandResult editCustomerTypeDescription(UserVisitPK userVisitPK, EditCustomerTypeDescriptionForm form);
    
    CommandResult deleteCustomerTypeDescription(UserVisitPK userVisitPK, DeleteCustomerTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Customer Type Payment Methods
    // -------------------------------------------------------------------------
    
    CommandResult createCustomerTypePaymentMethod(UserVisitPK userVisitPK, CreateCustomerTypePaymentMethodForm form);
    
    CommandResult getCustomerTypePaymentMethod(UserVisitPK userVisitPK, GetCustomerTypePaymentMethodForm form);
    
    CommandResult getCustomerTypePaymentMethods(UserVisitPK userVisitPK, GetCustomerTypePaymentMethodsForm form);
    
    CommandResult setDefaultCustomerTypePaymentMethod(UserVisitPK userVisitPK, SetDefaultCustomerTypePaymentMethodForm form);
    
    CommandResult editCustomerTypePaymentMethod(UserVisitPK userVisitPK, EditCustomerTypePaymentMethodForm form);
    
    CommandResult deleteCustomerTypePaymentMethod(UserVisitPK userVisitPK, DeleteCustomerTypePaymentMethodForm form);
    
    // -------------------------------------------------------------------------
    //   Customer Type Shipping Methods
    // -------------------------------------------------------------------------
    
    CommandResult createCustomerTypeShippingMethod(UserVisitPK userVisitPK, CreateCustomerTypeShippingMethodForm form);
    
    CommandResult getCustomerTypeShippingMethod(UserVisitPK userVisitPK, GetCustomerTypeShippingMethodForm form);
    
    CommandResult getCustomerTypeShippingMethods(UserVisitPK userVisitPK, GetCustomerTypeShippingMethodsForm form);
    
    CommandResult setDefaultCustomerTypeShippingMethod(UserVisitPK userVisitPK, SetDefaultCustomerTypeShippingMethodForm form);
    
    CommandResult editCustomerTypeShippingMethod(UserVisitPK userVisitPK, EditCustomerTypeShippingMethodForm form);
    
    CommandResult deleteCustomerTypeShippingMethod(UserVisitPK userVisitPK, DeleteCustomerTypeShippingMethodForm form);
    
}
