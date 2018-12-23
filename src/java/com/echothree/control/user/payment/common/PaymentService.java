// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.payment.common;

import com.echothree.control.user.payment.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface PaymentService
        extends PaymentForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Billing Account Role Type Types
    // --------------------------------------------------------------------------------
    
    CommandResult createBillingAccountRoleType(UserVisitPK userVisitPK, CreateBillingAccountRoleTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Billing Account Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createBillingAccountRoleTypeDescription(UserVisitPK userVisitPK, CreateBillingAccountRoleTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Payment Method Types
    // -------------------------------------------------------------------------
    
    CommandResult createPaymentMethodType(UserVisitPK userVisitPK, CreatePaymentMethodTypeForm form);
    
    CommandResult getPaymentMethodTypeChoices(UserVisitPK userVisitPK, GetPaymentMethodTypeChoicesForm form);
    
    CommandResult getPaymentMethodType(UserVisitPK userVisitPK, GetPaymentMethodTypeForm form);
    
    CommandResult getPaymentMethodTypes(UserVisitPK userVisitPK, GetPaymentMethodTypesForm form);
    
    // -------------------------------------------------------------------------
    //   Payment Method Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createPaymentMethodTypeDescription(UserVisitPK userVisitPK, CreatePaymentMethodTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Payment Method Type Party Types
    // -------------------------------------------------------------------------
    
    CommandResult createPaymentMethodTypePartyType(UserVisitPK userVisitPK, CreatePaymentMethodTypePartyTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Payment Processor Types
    // -------------------------------------------------------------------------
    
    CommandResult createPaymentProcessorType(UserVisitPK userVisitPK, CreatePaymentProcessorTypeForm form);
    
    CommandResult getPaymentProcessorTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeChoicesForm form);
    
    CommandResult getPaymentProcessorType(UserVisitPK userVisitPK, GetPaymentProcessorTypeForm form);
    
    CommandResult getPaymentProcessorTypes(UserVisitPK userVisitPK, GetPaymentProcessorTypesForm form);
    
    // -------------------------------------------------------------------------
    //   Payment Processor Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createPaymentProcessorTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Payment Processors
    // -------------------------------------------------------------------------
    
    CommandResult createPaymentProcessor(UserVisitPK userVisitPK, CreatePaymentProcessorForm form);
    
    CommandResult editPaymentProcessor(UserVisitPK userVisitPK, EditPaymentProcessorForm form);
    
    CommandResult getPaymentProcessors(UserVisitPK userVisitPK, GetPaymentProcessorsForm form);
    
    CommandResult getPaymentProcessor(UserVisitPK userVisitPK, GetPaymentProcessorForm form);
    
    CommandResult getPaymentProcessorChoices(UserVisitPK userVisitPK, GetPaymentProcessorChoicesForm form);
    
    CommandResult setDefaultPaymentProcessor(UserVisitPK userVisitPK, SetDefaultPaymentProcessorForm form);
    
    CommandResult deletePaymentProcessor(UserVisitPK userVisitPK, DeletePaymentProcessorForm form);
    
    // -------------------------------------------------------------------------
    //   Payment Processor Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createPaymentProcessorDescription(UserVisitPK userVisitPK, CreatePaymentProcessorDescriptionForm form);
    
    CommandResult editPaymentProcessorDescription(UserVisitPK userVisitPK, EditPaymentProcessorDescriptionForm form);

    CommandResult getPaymentProcessorDescription(UserVisitPK userVisitPK, GetPaymentProcessorDescriptionForm form);

    CommandResult getPaymentProcessorDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorDescriptionsForm form);
    
    CommandResult deletePaymentProcessorDescription(UserVisitPK userVisitPK, DeletePaymentProcessorDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Payment Methods
    // -------------------------------------------------------------------------
    
    CommandResult createPaymentMethod(UserVisitPK userVisitPK, CreatePaymentMethodForm form);
    
    CommandResult editPaymentMethod(UserVisitPK userVisitPK, EditPaymentMethodForm form);
    
    CommandResult getPaymentMethods(UserVisitPK userVisitPK, GetPaymentMethodsForm form);
    
    CommandResult getPaymentMethod(UserVisitPK userVisitPK, GetPaymentMethodForm form);
    
    CommandResult getPaymentMethodChoices(UserVisitPK userVisitPK, GetPaymentMethodChoicesForm form);
    
    CommandResult setDefaultPaymentMethod(UserVisitPK userVisitPK, SetDefaultPaymentMethodForm form);
    
    CommandResult deletePaymentMethod(UserVisitPK userVisitPK, DeletePaymentMethodForm form);
    
    // -------------------------------------------------------------------------
    //   Payment Method Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createPaymentMethodDescription(UserVisitPK userVisitPK, CreatePaymentMethodDescriptionForm form);
    
    CommandResult editPaymentMethodDescription(UserVisitPK userVisitPK, EditPaymentMethodDescriptionForm form);
    
    CommandResult getPaymentMethodDescription(UserVisitPK userVisitPK, GetPaymentMethodDescriptionForm form);

    CommandResult getPaymentMethodDescriptions(UserVisitPK userVisitPK, GetPaymentMethodDescriptionsForm form);

    CommandResult deletePaymentMethodDescription(UserVisitPK userVisitPK, DeletePaymentMethodDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Party Payment Methods
    // -------------------------------------------------------------------------
    
    CommandResult createPartyPaymentMethod(UserVisitPK userVisitPK, CreatePartyPaymentMethodForm form);

    CommandResult editPartyPaymentMethod(UserVisitPK userVisitPK, EditPartyPaymentMethodForm form);

    CommandResult getPartyPaymentMethod(UserVisitPK userVisitPK, GetPartyPaymentMethodForm form);

    CommandResult getPartyPaymentMethods(UserVisitPK userVisitPK, GetPartyPaymentMethodsForm form);

    CommandResult getPartyPaymentMethodChoices(UserVisitPK userVisitPK, GetPartyPaymentMethodChoicesForm form);

    CommandResult setDefaultPartyPaymentMethod(UserVisitPK userVisitPK, SetDefaultPartyPaymentMethodForm form);

    CommandResult deletePartyPaymentMethod(UserVisitPK userVisitPK, DeletePartyPaymentMethodForm form);

}
