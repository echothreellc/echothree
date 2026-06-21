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
import com.echothree.util.common.command.VoidResult;

public interface PaymentService
        extends PaymentForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Billing Account Role Type Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createBillingAccountRoleType(UserVisitPK userVisitPK, CreateBillingAccountRoleTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Billing Account Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createBillingAccountRoleTypeDescription(UserVisitPK userVisitPK, CreateBillingAccountRoleTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Payment Method Types
    // -------------------------------------------------------------------------

    CommandResult<CreatePaymentMethodTypeResult> createPaymentMethodType(UserVisitPK userVisitPK, CreatePaymentMethodTypeForm form);

    CommandResult<GetPaymentMethodTypesResult> getPaymentMethodTypes(UserVisitPK userVisitPK, GetPaymentMethodTypesForm form);

    CommandResult<GetPaymentMethodTypeResult> getPaymentMethodType(UserVisitPK userVisitPK, GetPaymentMethodTypeForm form);

    CommandResult<GetPaymentMethodTypeChoicesResult> getPaymentMethodTypeChoices(UserVisitPK userVisitPK, GetPaymentMethodTypeChoicesForm form);

    CommandResult<VoidResult> setDefaultPaymentMethodType(UserVisitPK userVisitPK, SetDefaultPaymentMethodTypeForm form);

    CommandResult<EditPaymentMethodTypeResult> editPaymentMethodType(UserVisitPK userVisitPK, EditPaymentMethodTypeForm form);

    CommandResult<VoidResult> deletePaymentMethodType(UserVisitPK userVisitPK, DeletePaymentMethodTypeForm form);

    // -------------------------------------------------------------------------
    //   Payment Method Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createPaymentMethodTypeDescription(UserVisitPK userVisitPK, CreatePaymentMethodTypeDescriptionForm form);

    CommandResult<GetPaymentMethodTypeDescriptionsResult> getPaymentMethodTypeDescriptions(UserVisitPK userVisitPK, GetPaymentMethodTypeDescriptionsForm form);

    CommandResult<EditPaymentMethodTypeDescriptionResult> editPaymentMethodTypeDescription(UserVisitPK userVisitPK, EditPaymentMethodTypeDescriptionForm form);

    CommandResult<VoidResult> deletePaymentMethodTypeDescription(UserVisitPK userVisitPK, DeletePaymentMethodTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Payment Method Type Party Types
    // -------------------------------------------------------------------------

    CommandResult<CreatePaymentMethodTypePartyTypeResult> createPaymentMethodTypePartyType(UserVisitPK userVisitPK, CreatePaymentMethodTypePartyTypeForm form);

//    CommandResult<GetPaymentMethodTypePartyTypesResult> getPaymentMethodTypePartyTypes(UserVisitPK userVisitPK, GetPaymentMethodTypePartyTypesForm form);
//
//    CommandResult<GetPaymentMethodTypePartyTypeResult> getPaymentMethodTypePartyType(UserVisitPK userVisitPK, GetPaymentMethodTypePartyTypeForm form);
//
//    CommandResult<GetPaymentMethodTypePartyTypeChoicesResult> getPaymentMethodTypePartyTypeChoices(UserVisitPK userVisitPK, GetPaymentMethodTypePartyTypeChoicesForm form);
//
//    CommandResult<NoResult> setDefaultPaymentMethodTypePartyType(UserVisitPK userVisitPK, SetDefaultPaymentMethodTypePartyTypeForm form);
//
//    CommandResult<EditPaymentMethodTypePartyTypeResult> editPaymentMethodTypePartyType(UserVisitPK userVisitPK, EditPaymentMethodTypePartyTypeForm form);
//
//    CommandResult<NoResult> deletePaymentMethodTypePartyType(UserVisitPK userVisitPK, DeletePaymentMethodTypePartyTypeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Types
    // -------------------------------------------------------------------------

    CommandResult<CreatePaymentProcessorTypeResult> createPaymentProcessorType(UserVisitPK userVisitPK, CreatePaymentProcessorTypeForm form);

    CommandResult<GetPaymentProcessorTypesResult> getPaymentProcessorTypes(UserVisitPK userVisitPK, GetPaymentProcessorTypesForm form);

    CommandResult<GetPaymentProcessorTypeResult> getPaymentProcessorType(UserVisitPK userVisitPK, GetPaymentProcessorTypeForm form);

    CommandResult<GetPaymentProcessorTypeChoicesResult> getPaymentProcessorTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeChoicesForm form);

    CommandResult<VoidResult> setDefaultPaymentProcessorType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeForm form);

    CommandResult<EditPaymentProcessorTypeResult> editPaymentProcessorType(UserVisitPK userVisitPK, EditPaymentProcessorTypeForm form);

    CommandResult<VoidResult> deletePaymentProcessorType(UserVisitPK userVisitPK, DeletePaymentProcessorTypeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createPaymentProcessorTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeDescriptionForm form);

    CommandResult<GetPaymentProcessorTypeDescriptionsResult> getPaymentProcessorTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeDescriptionsForm form);

    CommandResult<EditPaymentProcessorTypeDescriptionResult> editPaymentProcessorTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeDescriptionForm form);

    CommandResult<VoidResult> deletePaymentProcessorTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Types
    // -------------------------------------------------------------------------

    CommandResult<CreatePaymentProcessorTypeCodeTypeResult> createPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeTypeForm form);

//    CommandResult<GetPaymentProcessorTypeCodeTypesResult> getPaymentProcessorTypeCodeTypes(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypesForm form);

    CommandResult<GetPaymentProcessorTypeCodeTypeResult> getPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeForm form);

//    CommandResult<GetPaymentProcessorTypeCodeTypeChoicesResult> getPaymentProcessorTypeCodeTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeChoicesForm form);
//
//    CommandResult<NoResult> setDefaultPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeCodeTypeForm form);
//
//    CommandResult<EditPaymentProcessorTypeCodeTypeResult> editPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeTypeForm form);
//
//    CommandResult<NoResult> deletePaymentProcessorTypeCodeType(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeTypeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeTypeDescriptionForm form);

//    CommandResult<GetPaymentProcessorTypeCodeTypeDescriptionResult> getPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeDescriptionForm form);
//
//    CommandResult<GetPaymentProcessorTypeCodeTypeDescriptionsResult> getPaymentProcessorTypeCodeTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeDescriptionsForm form);
//
//    CommandResult<EditPaymentProcessorTypeCodeTypeDescriptionResult> editPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeTypeDescriptionForm form);
//
//    CommandResult<NoResult> deletePaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Codes
    // -------------------------------------------------------------------------

    CommandResult<CreatePaymentProcessorTypeCodeResult> createPaymentProcessorTypeCode(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeForm form);

//    CommandResult<GetPaymentProcessorTypeCodesResult> getPaymentProcessorTypeCodes(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodesForm form);

    CommandResult<GetPaymentProcessorTypeCodeResult> getPaymentProcessorTypeCode(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeForm form);

//    CommandResult<GetPaymentProcessorTypeCodeChoicesResult> getPaymentProcessorTypeCodeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeChoicesForm form);
//
//    CommandResult<NoResult> setDefaultPaymentProcessorTypeCode(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeCodeForm form);
//
//    CommandResult<EditPaymentProcessorTypeCodeResult> editPaymentProcessorTypeCode(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeForm form);
//
//    CommandResult<NoResult> deletePaymentProcessorTypeCode(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeDescriptionForm form);

//    CommandResult<GetPaymentProcessorTypeCodeDescriptionResult> getPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeDescriptionForm form);
//
//    CommandResult<GetPaymentProcessorTypeCodeDescriptionsResult> getPaymentProcessorTypeCodeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeDescriptionsForm form);
//
//    CommandResult<EditPaymentProcessorTypeCodeDescriptionResult> editPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeDescriptionForm form);
//
//    CommandResult<NoResult> deletePaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Type Actions
    // -------------------------------------------------------------------------

    CommandResult<CreatePaymentProcessorTypeActionResult> createPaymentProcessorTypeAction(UserVisitPK userVisitPK, CreatePaymentProcessorTypeActionForm form);

//    CommandResult<GetPaymentProcessorTypeActionsResult> getPaymentProcessorTypeActions(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionsForm form);
//
//    CommandResult<GetPaymentProcessorTypeActionResult> getPaymentProcessorTypeAction(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionForm form);
//
//    CommandResult<GetPaymentProcessorTypeActionChoicesResult> getPaymentProcessorTypeActionChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionChoicesForm form);
//
//    CommandResult<NoResult> setDefaultPaymentProcessorTypeAction(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeActionForm form);
//
//    CommandResult<EditPaymentProcessorTypeActionResult> editPaymentProcessorTypeAction(UserVisitPK userVisitPK, EditPaymentProcessorTypeActionForm form);
//
//    CommandResult<NoResult> deletePaymentProcessorTypeAction(UserVisitPK userVisitPK, DeletePaymentProcessorTypeActionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processors
    // -------------------------------------------------------------------------
    
    CommandResult<CreatePaymentProcessorResult> createPaymentProcessor(UserVisitPK userVisitPK, CreatePaymentProcessorForm form);
    
    CommandResult<EditPaymentProcessorResult> editPaymentProcessor(UserVisitPK userVisitPK, EditPaymentProcessorForm form);
    
    CommandResult<GetPaymentProcessorsResult> getPaymentProcessors(UserVisitPK userVisitPK, GetPaymentProcessorsForm form);
    
    CommandResult<GetPaymentProcessorResult> getPaymentProcessor(UserVisitPK userVisitPK, GetPaymentProcessorForm form);
    
    CommandResult<GetPaymentProcessorChoicesResult> getPaymentProcessorChoices(UserVisitPK userVisitPK, GetPaymentProcessorChoicesForm form);
    
    CommandResult<VoidResult> setDefaultPaymentProcessor(UserVisitPK userVisitPK, SetDefaultPaymentProcessorForm form);
    
    CommandResult<VoidResult> deletePaymentProcessor(UserVisitPK userVisitPK, DeletePaymentProcessorForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createPaymentProcessorDescription(UserVisitPK userVisitPK, CreatePaymentProcessorDescriptionForm form);

    CommandResult<EditPaymentProcessorDescriptionResult> editPaymentProcessorDescription(UserVisitPK userVisitPK, EditPaymentProcessorDescriptionForm form);

    CommandResult<GetPaymentProcessorDescriptionResult> getPaymentProcessorDescription(UserVisitPK userVisitPK, GetPaymentProcessorDescriptionForm form);

    CommandResult<GetPaymentProcessorDescriptionsResult> getPaymentProcessorDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorDescriptionsForm form);

    CommandResult<VoidResult> deletePaymentProcessorDescription(UserVisitPK userVisitPK, DeletePaymentProcessorDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Actions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createPaymentProcessorAction(UserVisitPK userVisitPK, CreatePaymentProcessorActionForm form);

//    CommandResult<GetPaymentProcessorActionResult> getPaymentProcessorAction(UserVisitPK userVisitPK, GetPaymentProcessorActionForm form);
//
//    CommandResult<GetPaymentProcessorActionsResult> getPaymentProcessorActions(UserVisitPK userVisitPK, GetPaymentProcessorActionsForm form);
//
//    CommandResult<NoResult> deletePaymentProcessorAction(UserVisitPK userVisitPK, DeletePaymentProcessorActionForm form);

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
    
    CommandResult<CreatePaymentMethodResult> createPaymentMethod(UserVisitPK userVisitPK, CreatePaymentMethodForm form);
    
    CommandResult<EditPaymentMethodResult> editPaymentMethod(UserVisitPK userVisitPK, EditPaymentMethodForm form);
    
    CommandResult<GetPaymentMethodsResult> getPaymentMethods(UserVisitPK userVisitPK, GetPaymentMethodsForm form);
    
    CommandResult<GetPaymentMethodResult> getPaymentMethod(UserVisitPK userVisitPK, GetPaymentMethodForm form);
    
    CommandResult<GetPaymentMethodChoicesResult> getPaymentMethodChoices(UserVisitPK userVisitPK, GetPaymentMethodChoicesForm form);
    
    CommandResult<VoidResult> setDefaultPaymentMethod(UserVisitPK userVisitPK, SetDefaultPaymentMethodForm form);
    
    CommandResult<VoidResult> deletePaymentMethod(UserVisitPK userVisitPK, DeletePaymentMethodForm form);
    
    // -------------------------------------------------------------------------
    //   Payment Method Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createPaymentMethodDescription(UserVisitPK userVisitPK, CreatePaymentMethodDescriptionForm form);
    
    CommandResult<EditPaymentMethodDescriptionResult> editPaymentMethodDescription(UserVisitPK userVisitPK, EditPaymentMethodDescriptionForm form);
    
    CommandResult<GetPaymentMethodDescriptionResult> getPaymentMethodDescription(UserVisitPK userVisitPK, GetPaymentMethodDescriptionForm form);

    CommandResult<GetPaymentMethodDescriptionsResult> getPaymentMethodDescriptions(UserVisitPK userVisitPK, GetPaymentMethodDescriptionsForm form);

    CommandResult<VoidResult> deletePaymentMethodDescription(UserVisitPK userVisitPK, DeletePaymentMethodDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Party Payment Methods
    // -------------------------------------------------------------------------
    
    CommandResult<CreatePartyPaymentMethodResult> createPartyPaymentMethod(UserVisitPK userVisitPK, CreatePartyPaymentMethodForm form);

    CommandResult<EditPartyPaymentMethodResult> editPartyPaymentMethod(UserVisitPK userVisitPK, EditPartyPaymentMethodForm form);

    CommandResult<GetPartyPaymentMethodResult> getPartyPaymentMethod(UserVisitPK userVisitPK, GetPartyPaymentMethodForm form);

    CommandResult<GetPartyPaymentMethodsResult> getPartyPaymentMethods(UserVisitPK userVisitPK, GetPartyPaymentMethodsForm form);

    CommandResult<GetPartyPaymentMethodChoicesResult> getPartyPaymentMethodChoices(UserVisitPK userVisitPK, GetPartyPaymentMethodChoicesForm form);

    CommandResult<VoidResult> setDefaultPartyPaymentMethod(UserVisitPK userVisitPK, SetDefaultPartyPaymentMethodForm form);

    CommandResult<VoidResult> deletePartyPaymentMethod(UserVisitPK userVisitPK, DeletePartyPaymentMethodForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Action Types
    // -------------------------------------------------------------------------

    CommandResult<CreatePaymentProcessorActionTypeResult> createPaymentProcessorActionType(UserVisitPK userVisitPK, CreatePaymentProcessorActionTypeForm form);

    CommandResult<GetPaymentProcessorActionTypesResult> getPaymentProcessorActionTypes(UserVisitPK userVisitPK, GetPaymentProcessorActionTypesForm form);

    CommandResult<GetPaymentProcessorActionTypeResult> getPaymentProcessorActionType(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeForm form);

    CommandResult<GetPaymentProcessorActionTypeChoicesResult> getPaymentProcessorActionTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeChoicesForm form);

    CommandResult<VoidResult> setDefaultPaymentProcessorActionType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorActionTypeForm form);

    CommandResult<EditPaymentProcessorActionTypeResult> editPaymentProcessorActionType(UserVisitPK userVisitPK, EditPaymentProcessorActionTypeForm form);

    CommandResult<VoidResult> deletePaymentProcessorActionType(UserVisitPK userVisitPK, DeletePaymentProcessorActionTypeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Action Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createPaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorActionTypeDescriptionForm form);

    CommandResult<GetPaymentProcessorActionTypeDescriptionsResult> getPaymentProcessorActionTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeDescriptionsForm form);

    CommandResult<EditPaymentProcessorActionTypeDescriptionResult> editPaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorActionTypeDescriptionForm form);

    CommandResult<VoidResult> deletePaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorActionTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Result Codes
    // -------------------------------------------------------------------------

    CommandResult<CreatePaymentProcessorResultCodeResult> createPaymentProcessorResultCode(UserVisitPK userVisitPK, CreatePaymentProcessorResultCodeForm form);

    CommandResult<GetPaymentProcessorResultCodesResult> getPaymentProcessorResultCodes(UserVisitPK userVisitPK, GetPaymentProcessorResultCodesForm form);

    CommandResult<GetPaymentProcessorResultCodeResult> getPaymentProcessorResultCode(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeForm form);

    CommandResult<GetPaymentProcessorResultCodeChoicesResult> getPaymentProcessorResultCodeChoices(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeChoicesForm form);

    CommandResult<VoidResult> setDefaultPaymentProcessorResultCode(UserVisitPK userVisitPK, SetDefaultPaymentProcessorResultCodeForm form);

    CommandResult<EditPaymentProcessorResultCodeResult> editPaymentProcessorResultCode(UserVisitPK userVisitPK, EditPaymentProcessorResultCodeForm form);

    CommandResult<VoidResult> deletePaymentProcessorResultCode(UserVisitPK userVisitPK, DeletePaymentProcessorResultCodeForm form);

    // -------------------------------------------------------------------------
    //   Payment Processor Result Code Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createPaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorResultCodeDescriptionForm form);

    CommandResult<GetPaymentProcessorResultCodeDescriptionsResult> getPaymentProcessorResultCodeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeDescriptionsForm form);

    CommandResult<EditPaymentProcessorResultCodeDescriptionResult> editPaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, EditPaymentProcessorResultCodeDescriptionForm form);

    CommandResult<VoidResult> deletePaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorResultCodeDescriptionForm form);

}
