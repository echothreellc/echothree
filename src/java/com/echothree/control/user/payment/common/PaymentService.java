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

    CommandResult getPaymentMethodTypes(UserVisitPK userVisitPK, GetPaymentMethodTypesForm form);

    CommandResult getPaymentMethodType(UserVisitPK userVisitPK, GetPaymentMethodTypeForm form);

    CommandResult getPaymentMethodTypeChoices(UserVisitPK userVisitPK, GetPaymentMethodTypeChoicesForm form);

    CommandResult setDefaultPaymentMethodType(UserVisitPK userVisitPK, SetDefaultPaymentMethodTypeForm form);

    CommandResult editPaymentMethodType(UserVisitPK userVisitPK, EditPaymentMethodTypeForm form);

    CommandResult deletePaymentMethodType(UserVisitPK userVisitPK, DeletePaymentMethodTypeForm form);

    // -------------------------------------------------------------------------
    //   Payment Method Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createPaymentMethodTypeDescription(UserVisitPK userVisitPK, CreatePaymentMethodTypeDescriptionForm form);

    CommandResult getPaymentMethodTypeDescriptions(UserVisitPK userVisitPK, GetPaymentMethodTypeDescriptionsForm form);

    CommandResult editPaymentMethodTypeDescription(UserVisitPK userVisitPK, EditPaymentMethodTypeDescriptionForm form);

    CommandResult deletePaymentMethodTypeDescription(UserVisitPK userVisitPK, DeletePaymentMethodTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Payment Method Type Party Types
    // -------------------------------------------------------------------------

    CommandResult createPaymentMethodTypePartyType(UserVisitPK userVisitPK, CreatePaymentMethodTypePartyTypeForm form);

//    CommandResult getPaymentMethodTypePartyTypes(UserVisitPK userVisitPK, GetPaymentMethodTypePartyTypesForm form);
//
//    CommandResult getPaymentMethodTypePartyType(UserVisitPK userVisitPK, GetPaymentMethodTypePartyTypeForm form);
//
//    CommandResult getPaymentMethodTypePartyTypeChoices(UserVisitPK userVisitPK, GetPaymentMethodTypePartyTypeChoicesForm form);
//
//    CommandResult setDefaultPaymentMethodTypePartyType(UserVisitPK userVisitPK, SetDefaultPaymentMethodTypePartyTypeForm form);
//
//    CommandResult editPaymentMethodTypePartyType(UserVisitPK userVisitPK, EditPaymentMethodTypePartyTypeForm form);
//
//    CommandResult deletePaymentMethodTypePartyType(UserVisitPK userVisitPK, DeletePaymentMethodTypePartyTypeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Types
    // -------------------------------------------------------------------------

    CommandResult createPaymentProcessorType(UserVisitPK userVisitPK, CreatePaymentProcessorTypeForm form);

    CommandResult getPaymentProcessorTypes(UserVisitPK userVisitPK, GetPaymentProcessorTypesForm form);

    CommandResult getPaymentProcessorType(UserVisitPK userVisitPK, GetPaymentProcessorTypeForm form);

    CommandResult getPaymentProcessorTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeChoicesForm form);

    CommandResult setDefaultPaymentProcessorType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeForm form);

    CommandResult editPaymentProcessorType(UserVisitPK userVisitPK, EditPaymentProcessorTypeForm form);

    CommandResult deletePaymentProcessorType(UserVisitPK userVisitPK, DeletePaymentProcessorTypeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createPaymentProcessorTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeDescriptionForm form);

    CommandResult getPaymentProcessorTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeDescriptionsForm form);

    CommandResult editPaymentProcessorTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeDescriptionForm form);

    CommandResult deletePaymentProcessorTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Types
    // -------------------------------------------------------------------------

    CommandResult createPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeTypeForm form);

//    CommandResult getPaymentProcessorTypeCodeTypes(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypesForm form);

    CommandResult getPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeForm form);

//    CommandResult getPaymentProcessorTypeCodeTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeChoicesForm form);
//
//    CommandResult setDefaultPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeCodeTypeForm form);
//
//    CommandResult editPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeTypeForm form);
//
//    CommandResult deletePaymentProcessorTypeCodeType(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeTypeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeTypeDescriptionForm form);

//    CommandResult getPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeDescriptionForm form);
//
//    CommandResult getPaymentProcessorTypeCodeTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeDescriptionsForm form);
//
//    CommandResult editPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeTypeDescriptionForm form);
//
//    CommandResult deletePaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Codes
    // -------------------------------------------------------------------------

    CommandResult createPaymentProcessorTypeCode(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeForm form);

//    CommandResult getPaymentProcessorTypeCodes(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodesForm form);

    CommandResult getPaymentProcessorTypeCode(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeForm form);

//    CommandResult getPaymentProcessorTypeCodeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeChoicesForm form);
//
//    CommandResult setDefaultPaymentProcessorTypeCode(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeCodeForm form);
//
//    CommandResult editPaymentProcessorTypeCode(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeForm form);
//
//    CommandResult deletePaymentProcessorTypeCode(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Descriptions
    // -------------------------------------------------------------------------

    CommandResult createPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeDescriptionForm form);

//    CommandResult getPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeDescriptionForm form);
//
//    CommandResult getPaymentProcessorTypeCodeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeDescriptionsForm form);
//
//    CommandResult editPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeDescriptionForm form);
//
//    CommandResult deletePaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Actions
    // -------------------------------------------------------------------------

    CommandResult createPaymentProcessorTypeAction(UserVisitPK userVisitPK, CreatePaymentProcessorTypeActionForm form);

//    CommandResult getPaymentProcessorTypeActions(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionsForm form);
//
//    CommandResult getPaymentProcessorTypeAction(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionForm form);
//
//    CommandResult getPaymentProcessorTypeActionChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionChoicesForm form);
//
//    CommandResult setDefaultPaymentProcessorTypeAction(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeActionForm form);
//
//    CommandResult editPaymentProcessorTypeAction(UserVisitPK userVisitPK, EditPaymentProcessorTypeActionForm form);
//
//    CommandResult deletePaymentProcessorTypeAction(UserVisitPK userVisitPK, DeletePaymentProcessorTypeActionForm form);

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
    //   Payment Processor Actions
    // -------------------------------------------------------------------------

    CommandResult createPaymentProcessorAction(UserVisitPK userVisitPK, CreatePaymentProcessorActionForm form);

//    CommandResult getPaymentProcessorAction(UserVisitPK userVisitPK, GetPaymentProcessorActionForm form);
//
//    CommandResult getPaymentProcessorActions(UserVisitPK userVisitPK, GetPaymentProcessorActionsForm form);
//
//    CommandResult deletePaymentProcessorAction(UserVisitPK userVisitPK, DeletePaymentProcessorActionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Transactions
    // -------------------------------------------------------------------------

    CommandResult getPaymentProcessorTransactions(UserVisitPK userVisitPK, GetPaymentProcessorTransactionsForm form);

    CommandResult getPaymentProcessorTransaction(UserVisitPK userVisitPK, GetPaymentProcessorTransactionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Transaction Codes
    // -------------------------------------------------------------------------

    CommandResult getPaymentProcessorTransactionCodes(UserVisitPK userVisitPK, GetPaymentProcessorTransactionCodesForm form);

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

    // -------------------------------------------------------------------------
    //   Payment Processor Action Types
    // -------------------------------------------------------------------------

    CommandResult createPaymentProcessorActionType(UserVisitPK userVisitPK, CreatePaymentProcessorActionTypeForm form);

    CommandResult getPaymentProcessorActionTypes(UserVisitPK userVisitPK, GetPaymentProcessorActionTypesForm form);

    CommandResult getPaymentProcessorActionType(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeForm form);

    CommandResult getPaymentProcessorActionTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeChoicesForm form);

    CommandResult setDefaultPaymentProcessorActionType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorActionTypeForm form);

    CommandResult editPaymentProcessorActionType(UserVisitPK userVisitPK, EditPaymentProcessorActionTypeForm form);

    CommandResult deletePaymentProcessorActionType(UserVisitPK userVisitPK, DeletePaymentProcessorActionTypeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Action Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createPaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorActionTypeDescriptionForm form);

    CommandResult getPaymentProcessorActionTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeDescriptionsForm form);

    CommandResult editPaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorActionTypeDescriptionForm form);

    CommandResult deletePaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorActionTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Result Codes
    // -------------------------------------------------------------------------

    CommandResult createPaymentProcessorResultCode(UserVisitPK userVisitPK, CreatePaymentProcessorResultCodeForm form);

    CommandResult getPaymentProcessorResultCodes(UserVisitPK userVisitPK, GetPaymentProcessorResultCodesForm form);

    CommandResult getPaymentProcessorResultCode(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeForm form);

    CommandResult getPaymentProcessorResultCodeChoices(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeChoicesForm form);

    CommandResult setDefaultPaymentProcessorResultCode(UserVisitPK userVisitPK, SetDefaultPaymentProcessorResultCodeForm form);

    CommandResult editPaymentProcessorResultCode(UserVisitPK userVisitPK, EditPaymentProcessorResultCodeForm form);

    CommandResult deletePaymentProcessorResultCode(UserVisitPK userVisitPK, DeletePaymentProcessorResultCodeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Result Code Descriptions
    // -------------------------------------------------------------------------

    CommandResult createPaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorResultCodeDescriptionForm form);

    CommandResult getPaymentProcessorResultCodeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeDescriptionsForm form);

    CommandResult editPaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, EditPaymentProcessorResultCodeDescriptionForm form);

    CommandResult deletePaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorResultCodeDescriptionForm form);

}
