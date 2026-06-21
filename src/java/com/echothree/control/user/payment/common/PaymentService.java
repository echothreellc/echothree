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
import com.echothree.control.user.payment.common.result.*;
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
    
    CommandResult<?> createBillingAccountRoleType(UserVisitPK userVisitPK, CreateBillingAccountRoleTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Billing Account Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createBillingAccountRoleTypeDescription(UserVisitPK userVisitPK, CreateBillingAccountRoleTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Payment Method Types
    // -------------------------------------------------------------------------

    CommandResult<?> createPaymentMethodType(UserVisitPK userVisitPK, CreatePaymentMethodTypeForm form);

    CommandResult<GetPaymentMethodTypesResult> getPaymentMethodTypes(UserVisitPK userVisitPK, GetPaymentMethodTypesForm form);

    CommandResult<GetPaymentMethodTypeResult> getPaymentMethodType(UserVisitPK userVisitPK, GetPaymentMethodTypeForm form);

    CommandResult<GetPaymentMethodTypeChoicesResult> getPaymentMethodTypeChoices(UserVisitPK userVisitPK, GetPaymentMethodTypeChoicesForm form);

    CommandResult<?> setDefaultPaymentMethodType(UserVisitPK userVisitPK, SetDefaultPaymentMethodTypeForm form);

    CommandResult<EditPaymentMethodTypeResult> editPaymentMethodType(UserVisitPK userVisitPK, EditPaymentMethodTypeForm form);

    CommandResult<?> deletePaymentMethodType(UserVisitPK userVisitPK, DeletePaymentMethodTypeForm form);

    // -------------------------------------------------------------------------
    //   Payment Method Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createPaymentMethodTypeDescription(UserVisitPK userVisitPK, CreatePaymentMethodTypeDescriptionForm form);

    CommandResult<GetPaymentMethodTypeDescriptionsResult> getPaymentMethodTypeDescriptions(UserVisitPK userVisitPK, GetPaymentMethodTypeDescriptionsForm form);

    CommandResult<EditPaymentMethodTypeDescriptionResult> editPaymentMethodTypeDescription(UserVisitPK userVisitPK, EditPaymentMethodTypeDescriptionForm form);

    CommandResult<?> deletePaymentMethodTypeDescription(UserVisitPK userVisitPK, DeletePaymentMethodTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Payment Method Type Party Types
    // -------------------------------------------------------------------------

    CommandResult<?> createPaymentMethodTypePartyType(UserVisitPK userVisitPK, CreatePaymentMethodTypePartyTypeForm form);

//    CommandResult<GetPaymentMethodTypePartyTypesResult> getPaymentMethodTypePartyTypes(UserVisitPK userVisitPK, GetPaymentMethodTypePartyTypesForm form);
//
//    CommandResult<GetPaymentMethodTypePartyTypeResult> getPaymentMethodTypePartyType(UserVisitPK userVisitPK, GetPaymentMethodTypePartyTypeForm form);
//
//    CommandResult<GetPaymentMethodTypePartyTypeChoicesResult> getPaymentMethodTypePartyTypeChoices(UserVisitPK userVisitPK, GetPaymentMethodTypePartyTypeChoicesForm form);
//
//    CommandResult<?> setDefaultPaymentMethodTypePartyType(UserVisitPK userVisitPK, SetDefaultPaymentMethodTypePartyTypeForm form);
//
//    CommandResult<EditPaymentMethodTypePartyTypeResult> editPaymentMethodTypePartyType(UserVisitPK userVisitPK, EditPaymentMethodTypePartyTypeForm form);
//
//    CommandResult<?> deletePaymentMethodTypePartyType(UserVisitPK userVisitPK, DeletePaymentMethodTypePartyTypeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Types
    // -------------------------------------------------------------------------

    CommandResult<?> createPaymentProcessorType(UserVisitPK userVisitPK, CreatePaymentProcessorTypeForm form);

    CommandResult<GetPaymentProcessorTypesResult> getPaymentProcessorTypes(UserVisitPK userVisitPK, GetPaymentProcessorTypesForm form);

    CommandResult<GetPaymentProcessorTypeResult> getPaymentProcessorType(UserVisitPK userVisitPK, GetPaymentProcessorTypeForm form);

    CommandResult<GetPaymentProcessorTypeChoicesResult> getPaymentProcessorTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeChoicesForm form);

    CommandResult<?> setDefaultPaymentProcessorType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeForm form);

    CommandResult<EditPaymentProcessorTypeResult> editPaymentProcessorType(UserVisitPK userVisitPK, EditPaymentProcessorTypeForm form);

    CommandResult<?> deletePaymentProcessorType(UserVisitPK userVisitPK, DeletePaymentProcessorTypeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createPaymentProcessorTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeDescriptionForm form);

    CommandResult<GetPaymentProcessorTypeDescriptionsResult> getPaymentProcessorTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeDescriptionsForm form);

    CommandResult<EditPaymentProcessorTypeDescriptionResult> editPaymentProcessorTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeDescriptionForm form);

    CommandResult<?> deletePaymentProcessorTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Types
    // -------------------------------------------------------------------------

    CommandResult<?> createPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeTypeForm form);

//    CommandResult<GetPaymentProcessorTypeCodeTypesResult> getPaymentProcessorTypeCodeTypes(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypesForm form);

    CommandResult<GetPaymentProcessorTypeCodeTypeResult> getPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeForm form);

//    CommandResult<GetPaymentProcessorTypeCodeTypeChoicesResult> getPaymentProcessorTypeCodeTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeChoicesForm form);
//
//    CommandResult<?> setDefaultPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeCodeTypeForm form);
//
//    CommandResult<EditPaymentProcessorTypeCodeTypeResult> editPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeTypeForm form);
//
//    CommandResult<?> deletePaymentProcessorTypeCodeType(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeTypeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeTypeDescriptionForm form);

//    CommandResult<GetPaymentProcessorTypeCodeTypeDescriptionResult> getPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeDescriptionForm form);
//
//    CommandResult<GetPaymentProcessorTypeCodeTypeDescriptionsResult> getPaymentProcessorTypeCodeTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeDescriptionsForm form);
//
//    CommandResult<EditPaymentProcessorTypeCodeTypeDescriptionResult> editPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeTypeDescriptionForm form);
//
//    CommandResult<?> deletePaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Codes
    // -------------------------------------------------------------------------

    CommandResult<?> createPaymentProcessorTypeCode(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeForm form);

//    CommandResult<GetPaymentProcessorTypeCodesResult> getPaymentProcessorTypeCodes(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodesForm form);

    CommandResult<GetPaymentProcessorTypeCodeResult> getPaymentProcessorTypeCode(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeForm form);

//    CommandResult<GetPaymentProcessorTypeCodeChoicesResult> getPaymentProcessorTypeCodeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeChoicesForm form);
//
//    CommandResult<?> setDefaultPaymentProcessorTypeCode(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeCodeForm form);
//
//    CommandResult<EditPaymentProcessorTypeCodeResult> editPaymentProcessorTypeCode(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeForm form);
//
//    CommandResult<?> deletePaymentProcessorTypeCode(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeDescriptionForm form);

//    CommandResult<GetPaymentProcessorTypeCodeDescriptionResult> getPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeDescriptionForm form);
//
//    CommandResult<GetPaymentProcessorTypeCodeDescriptionsResult> getPaymentProcessorTypeCodeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeDescriptionsForm form);
//
//    CommandResult<EditPaymentProcessorTypeCodeDescriptionResult> editPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeDescriptionForm form);
//
//    CommandResult<?> deletePaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Actions
    // -------------------------------------------------------------------------

    CommandResult<?> createPaymentProcessorTypeAction(UserVisitPK userVisitPK, CreatePaymentProcessorTypeActionForm form);

//    CommandResult<GetPaymentProcessorTypeActionsResult> getPaymentProcessorTypeActions(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionsForm form);
//
//    CommandResult<GetPaymentProcessorTypeActionResult> getPaymentProcessorTypeAction(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionForm form);
//
//    CommandResult<GetPaymentProcessorTypeActionChoicesResult> getPaymentProcessorTypeActionChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionChoicesForm form);
//
//    CommandResult<?> setDefaultPaymentProcessorTypeAction(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeActionForm form);
//
//    CommandResult<EditPaymentProcessorTypeActionResult> editPaymentProcessorTypeAction(UserVisitPK userVisitPK, EditPaymentProcessorTypeActionForm form);
//
//    CommandResult<?> deletePaymentProcessorTypeAction(UserVisitPK userVisitPK, DeletePaymentProcessorTypeActionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processors
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPaymentProcessor(UserVisitPK userVisitPK, CreatePaymentProcessorForm form);
    
    CommandResult<EditPaymentProcessorResult> editPaymentProcessor(UserVisitPK userVisitPK, EditPaymentProcessorForm form);
    
    CommandResult<GetPaymentProcessorsResult> getPaymentProcessors(UserVisitPK userVisitPK, GetPaymentProcessorsForm form);
    
    CommandResult<GetPaymentProcessorResult> getPaymentProcessor(UserVisitPK userVisitPK, GetPaymentProcessorForm form);
    
    CommandResult<GetPaymentProcessorChoicesResult> getPaymentProcessorChoices(UserVisitPK userVisitPK, GetPaymentProcessorChoicesForm form);
    
    CommandResult<?> setDefaultPaymentProcessor(UserVisitPK userVisitPK, SetDefaultPaymentProcessorForm form);
    
    CommandResult<?> deletePaymentProcessor(UserVisitPK userVisitPK, DeletePaymentProcessorForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createPaymentProcessorDescription(UserVisitPK userVisitPK, CreatePaymentProcessorDescriptionForm form);

    CommandResult<EditPaymentProcessorDescriptionResult> editPaymentProcessorDescription(UserVisitPK userVisitPK, EditPaymentProcessorDescriptionForm form);

    CommandResult<GetPaymentProcessorDescriptionResult> getPaymentProcessorDescription(UserVisitPK userVisitPK, GetPaymentProcessorDescriptionForm form);

    CommandResult<GetPaymentProcessorDescriptionsResult> getPaymentProcessorDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorDescriptionsForm form);

    CommandResult<?> deletePaymentProcessorDescription(UserVisitPK userVisitPK, DeletePaymentProcessorDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Actions
    // -------------------------------------------------------------------------

    CommandResult<?> createPaymentProcessorAction(UserVisitPK userVisitPK, CreatePaymentProcessorActionForm form);

//    CommandResult<GetPaymentProcessorActionResult> getPaymentProcessorAction(UserVisitPK userVisitPK, GetPaymentProcessorActionForm form);
//
//    CommandResult<GetPaymentProcessorActionsResult> getPaymentProcessorActions(UserVisitPK userVisitPK, GetPaymentProcessorActionsForm form);
//
//    CommandResult<?> deletePaymentProcessorAction(UserVisitPK userVisitPK, DeletePaymentProcessorActionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Transactions
    // -------------------------------------------------------------------------

    CommandResult<GetPaymentProcessorTransactionsResult> getPaymentProcessorTransactions(UserVisitPK userVisitPK, GetPaymentProcessorTransactionsForm form);

    CommandResult<GetPaymentProcessorTransactionResult> getPaymentProcessorTransaction(UserVisitPK userVisitPK, GetPaymentProcessorTransactionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Transaction Codes
    // -------------------------------------------------------------------------

    CommandResult<GetPaymentProcessorTransactionCodesResult> getPaymentProcessorTransactionCodes(UserVisitPK userVisitPK, GetPaymentProcessorTransactionCodesForm form);

    // -------------------------------------------------------------------------
    //   Payment Methods
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPaymentMethod(UserVisitPK userVisitPK, CreatePaymentMethodForm form);
    
    CommandResult<EditPaymentMethodResult> editPaymentMethod(UserVisitPK userVisitPK, EditPaymentMethodForm form);
    
    CommandResult<GetPaymentMethodsResult> getPaymentMethods(UserVisitPK userVisitPK, GetPaymentMethodsForm form);
    
    CommandResult<GetPaymentMethodResult> getPaymentMethod(UserVisitPK userVisitPK, GetPaymentMethodForm form);
    
    CommandResult<GetPaymentMethodChoicesResult> getPaymentMethodChoices(UserVisitPK userVisitPK, GetPaymentMethodChoicesForm form);
    
    CommandResult<?> setDefaultPaymentMethod(UserVisitPK userVisitPK, SetDefaultPaymentMethodForm form);
    
    CommandResult<?> deletePaymentMethod(UserVisitPK userVisitPK, DeletePaymentMethodForm form);
    
    // -------------------------------------------------------------------------
    //   Payment Method Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPaymentMethodDescription(UserVisitPK userVisitPK, CreatePaymentMethodDescriptionForm form);
    
    CommandResult<EditPaymentMethodDescriptionResult> editPaymentMethodDescription(UserVisitPK userVisitPK, EditPaymentMethodDescriptionForm form);
    
    CommandResult<GetPaymentMethodDescriptionResult> getPaymentMethodDescription(UserVisitPK userVisitPK, GetPaymentMethodDescriptionForm form);

    CommandResult<GetPaymentMethodDescriptionsResult> getPaymentMethodDescriptions(UserVisitPK userVisitPK, GetPaymentMethodDescriptionsForm form);

    CommandResult<?> deletePaymentMethodDescription(UserVisitPK userVisitPK, DeletePaymentMethodDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Party Payment Methods
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPartyPaymentMethod(UserVisitPK userVisitPK, CreatePartyPaymentMethodForm form);

    CommandResult<EditPartyPaymentMethodResult> editPartyPaymentMethod(UserVisitPK userVisitPK, EditPartyPaymentMethodForm form);

    CommandResult<GetPartyPaymentMethodResult> getPartyPaymentMethod(UserVisitPK userVisitPK, GetPartyPaymentMethodForm form);

    CommandResult<GetPartyPaymentMethodsResult> getPartyPaymentMethods(UserVisitPK userVisitPK, GetPartyPaymentMethodsForm form);

    CommandResult<GetPartyPaymentMethodChoicesResult> getPartyPaymentMethodChoices(UserVisitPK userVisitPK, GetPartyPaymentMethodChoicesForm form);

    CommandResult<?> setDefaultPartyPaymentMethod(UserVisitPK userVisitPK, SetDefaultPartyPaymentMethodForm form);

    CommandResult<?> deletePartyPaymentMethod(UserVisitPK userVisitPK, DeletePartyPaymentMethodForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Action Types
    // -------------------------------------------------------------------------

    CommandResult<?> createPaymentProcessorActionType(UserVisitPK userVisitPK, CreatePaymentProcessorActionTypeForm form);

    CommandResult<GetPaymentProcessorActionTypesResult> getPaymentProcessorActionTypes(UserVisitPK userVisitPK, GetPaymentProcessorActionTypesForm form);

    CommandResult<GetPaymentProcessorActionTypeResult> getPaymentProcessorActionType(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeForm form);

    CommandResult<GetPaymentProcessorActionTypeChoicesResult> getPaymentProcessorActionTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeChoicesForm form);

    CommandResult<?> setDefaultPaymentProcessorActionType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorActionTypeForm form);

    CommandResult<EditPaymentProcessorActionTypeResult> editPaymentProcessorActionType(UserVisitPK userVisitPK, EditPaymentProcessorActionTypeForm form);

    CommandResult<?> deletePaymentProcessorActionType(UserVisitPK userVisitPK, DeletePaymentProcessorActionTypeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Action Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createPaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorActionTypeDescriptionForm form);

    CommandResult<GetPaymentProcessorActionTypeDescriptionsResult> getPaymentProcessorActionTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeDescriptionsForm form);

    CommandResult<EditPaymentProcessorActionTypeDescriptionResult> editPaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorActionTypeDescriptionForm form);

    CommandResult<?> deletePaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorActionTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Result Codes
    // -------------------------------------------------------------------------

    CommandResult<?> createPaymentProcessorResultCode(UserVisitPK userVisitPK, CreatePaymentProcessorResultCodeForm form);

    CommandResult<GetPaymentProcessorResultCodesResult> getPaymentProcessorResultCodes(UserVisitPK userVisitPK, GetPaymentProcessorResultCodesForm form);

    CommandResult<GetPaymentProcessorResultCodeResult> getPaymentProcessorResultCode(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeForm form);

    CommandResult<GetPaymentProcessorResultCodeChoicesResult> getPaymentProcessorResultCodeChoices(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeChoicesForm form);

    CommandResult<?> setDefaultPaymentProcessorResultCode(UserVisitPK userVisitPK, SetDefaultPaymentProcessorResultCodeForm form);

    CommandResult<EditPaymentProcessorResultCodeResult> editPaymentProcessorResultCode(UserVisitPK userVisitPK, EditPaymentProcessorResultCodeForm form);

    CommandResult<?> deletePaymentProcessorResultCode(UserVisitPK userVisitPK, DeletePaymentProcessorResultCodeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Result Code Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createPaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorResultCodeDescriptionForm form);

    CommandResult<GetPaymentProcessorResultCodeDescriptionsResult> getPaymentProcessorResultCodeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeDescriptionsForm form);

    CommandResult<EditPaymentProcessorResultCodeDescriptionResult> editPaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, EditPaymentProcessorResultCodeDescriptionForm form);

    CommandResult<?> deletePaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorResultCodeDescriptionForm form);

}
