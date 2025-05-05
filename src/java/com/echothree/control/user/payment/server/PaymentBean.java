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

package com.echothree.control.user.payment.server;

import com.echothree.control.user.payment.common.PaymentRemote;
import com.echothree.control.user.payment.common.form.*;
import com.echothree.control.user.payment.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class PaymentBean
        extends PaymentFormsImpl
        implements PaymentRemote, PaymentLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "PaymentBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Billing Account Role Type Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createBillingAccountRoleType(UserVisitPK userVisitPK, CreateBillingAccountRoleTypeForm form) {
        return new CreateBillingAccountRoleTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Billing Account Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createBillingAccountRoleTypeDescription(UserVisitPK userVisitPK, CreateBillingAccountRoleTypeDescriptionForm form) {
        return new CreateBillingAccountRoleTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Payment Method Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentMethodType(UserVisitPK userVisitPK, CreatePaymentMethodTypeForm form) {
        return new CreatePaymentMethodTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentMethodTypes(UserVisitPK userVisitPK, GetPaymentMethodTypesForm form) {
        return new GetPaymentMethodTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentMethodType(UserVisitPK userVisitPK, GetPaymentMethodTypeForm form) {
        return new GetPaymentMethodTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentMethodTypeChoices(UserVisitPK userVisitPK, GetPaymentMethodTypeChoicesForm form) {
        return new GetPaymentMethodTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPaymentMethodType(UserVisitPK userVisitPK, SetDefaultPaymentMethodTypeForm form) {
        return new SetDefaultPaymentMethodTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentMethodType(UserVisitPK userVisitPK, EditPaymentMethodTypeForm form) {
        return new EditPaymentMethodTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentMethodType(UserVisitPK userVisitPK, DeletePaymentMethodTypeForm form) {
        return new DeletePaymentMethodTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Method Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentMethodTypeDescription(UserVisitPK userVisitPK, CreatePaymentMethodTypeDescriptionForm form) {
        return new CreatePaymentMethodTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentMethodTypeDescriptions(UserVisitPK userVisitPK, GetPaymentMethodTypeDescriptionsForm form) {
        return new GetPaymentMethodTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentMethodTypeDescription(UserVisitPK userVisitPK, EditPaymentMethodTypeDescriptionForm form) {
        return new EditPaymentMethodTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentMethodTypeDescription(UserVisitPK userVisitPK, DeletePaymentMethodTypeDescriptionForm form) {
        return new DeletePaymentMethodTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Method Type PartyTypes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentMethodTypePartyType(UserVisitPK userVisitPK, CreatePaymentMethodTypePartyTypeForm form) {
        return new CreatePaymentMethodTypePartyTypeCommand().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentMethodTypePartyTypes(UserVisitPK userVisitPK, GetPaymentMethodTypePartyTypesForm form) {
//        return new GetPaymentMethodTypePartyTypesCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult getPaymentMethodTypePartyType(UserVisitPK userVisitPK, GetPaymentMethodTypePartyTypeForm form) {
//        return new GetPaymentMethodTypePartyTypeCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult getPaymentMethodTypePartyTypeChoices(UserVisitPK userVisitPK, GetPaymentMethodTypePartyTypeChoicesForm form) {
//        return new GetPaymentMethodTypePartyTypeChoicesCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult setDefaultPaymentMethodTypePartyType(UserVisitPK userVisitPK, SetDefaultPaymentMethodTypePartyTypeForm form) {
//        return new SetDefaultPaymentMethodTypePartyTypeCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult editPaymentMethodTypePartyType(UserVisitPK userVisitPK, EditPaymentMethodTypePartyTypeForm form) {
//        return new EditPaymentMethodTypePartyTypeCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult deletePaymentMethodTypePartyType(UserVisitPK userVisitPK, DeletePaymentMethodTypePartyTypeForm form) {
//        return new DeletePaymentMethodTypePartyTypeCommand().run(userVisitPK, form);
//    }

    // -------------------------------------------------------------------------
    //   Payment Processor Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorType(UserVisitPK userVisitPK, CreatePaymentProcessorTypeForm form) {
        return new CreatePaymentProcessorTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorTypes(UserVisitPK userVisitPK, GetPaymentProcessorTypesForm form) {
        return new GetPaymentProcessorTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorType(UserVisitPK userVisitPK, GetPaymentProcessorTypeForm form) {
        return new GetPaymentProcessorTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeChoicesForm form) {
        return new GetPaymentProcessorTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPaymentProcessorType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeForm form) {
        return new SetDefaultPaymentProcessorTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentProcessorType(UserVisitPK userVisitPK, EditPaymentProcessorTypeForm form) {
        return new EditPaymentProcessorTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentProcessorType(UserVisitPK userVisitPK, DeletePaymentProcessorTypeForm form) {
        return new DeletePaymentProcessorTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeDescriptionForm form) {
        return new CreatePaymentProcessorTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeDescriptionsForm form) {
        return new GetPaymentProcessorTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentProcessorTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeDescriptionForm form) {
        return new EditPaymentProcessorTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentProcessorTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeDescriptionForm form) {
        return new DeletePaymentProcessorTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeTypeForm form) {
        return new CreatePaymentProcessorTypeCodeTypeCommand().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeCodeTypes(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypesForm form) {
//        return new GetPaymentProcessorTypeCodeTypesCommand().run(userVisitPK, form);
//    }

    @Override
    public CommandResult getPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeForm form) {
        return new GetPaymentProcessorTypeCodeTypeCommand().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeCodeTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeChoicesForm form) {
//        return new GetPaymentProcessorTypeCodeTypeChoicesCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult setDefaultPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeCodeTypeForm form) {
//        return new SetDefaultPaymentProcessorTypeCodeTypeCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult editPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeTypeForm form) {
//        return new EditPaymentProcessorTypeCodeTypeCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult deletePaymentProcessorTypeCodeType(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeTypeForm form) {
//        return new DeletePaymentProcessorTypeCodeTypeCommand().run(userVisitPK, form);
//    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeTypeDescriptionForm form) {
        return new CreatePaymentProcessorTypeCodeTypeDescriptionCommand().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeDescriptionForm form) {
//        return new GetPaymentProcessorTypeCodeTypeDescriptionCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult getPaymentProcessorTypeCodeTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeDescriptionsForm form) {
//        return new GetPaymentProcessorTypeCodeTypeDescriptionsCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult editPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeTypeDescriptionForm form) {
//        return new EditPaymentProcessorTypeCodeTypeDescriptionCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult deletePaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeTypeDescriptionForm form) {
//        return new DeletePaymentProcessorTypeCodeTypeDescriptionCommand().run(userVisitPK, form);
//    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Codes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeCode(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeForm form) {
        return new CreatePaymentProcessorTypeCodeCommand().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeCodes(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodesForm form) {
//        return new GetPaymentProcessorTypeCodesCommand().run(userVisitPK, form);
//    }

    @Override
    public CommandResult getPaymentProcessorTypeCode(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeForm form) {
        return new GetPaymentProcessorTypeCodeCommand().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeCodeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeChoicesForm form) {
//        return new GetPaymentProcessorTypeCodeChoicesCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult setDefaultPaymentProcessorTypeCode(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeCodeForm form) {
//        return new SetDefaultPaymentProcessorTypeCodeCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult editPaymentProcessorTypeCode(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeForm form) {
//        return new EditPaymentProcessorTypeCodeCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult deletePaymentProcessorTypeCode(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeForm form) {
//        return new DeletePaymentProcessorTypeCodeCommand().run(userVisitPK, form);
//    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeDescriptionForm form) {
        return new CreatePaymentProcessorTypeCodeDescriptionCommand().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeDescriptionForm form) {
//        return new GetPaymentProcessorTypeCodeDescriptionCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult getPaymentProcessorTypeCodeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeDescriptionsForm form) {
//        return new GetPaymentProcessorTypeCodeDescriptionsCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult editPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeDescriptionForm form) {
//        return new EditPaymentProcessorTypeCodeDescriptionCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult deletePaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeDescriptionForm form) {
//        return new DeletePaymentProcessorTypeCodeDescriptionCommand().run(userVisitPK, form);
//    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Actions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeAction(UserVisitPK userVisitPK, CreatePaymentProcessorTypeActionForm form) {
        return new CreatePaymentProcessorTypeActionCommand().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeActions(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionsForm form) {
//        return new GetPaymentProcessorTypeActionsCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult getPaymentProcessorTypeAction(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionForm form) {
//        return new GetPaymentProcessorTypeActionCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult getPaymentProcessorTypeActionChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionChoicesForm form) {
//        return new GetPaymentProcessorTypeActionChoicesCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult setDefaultPaymentProcessorTypeAction(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeActionForm form) {
//        return new SetDefaultPaymentProcessorTypeActionCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult editPaymentProcessorTypeAction(UserVisitPK userVisitPK, EditPaymentProcessorTypeActionForm form) {
//        return new EditPaymentProcessorTypeActionCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult deletePaymentProcessorTypeAction(UserVisitPK userVisitPK, DeletePaymentProcessorTypeActionForm form) {
//        return new DeletePaymentProcessorTypeActionCommand().run(userVisitPK, form);
//    }

    // -------------------------------------------------------------------------
    //   Payment Processors
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPaymentProcessor(UserVisitPK userVisitPK, CreatePaymentProcessorForm form) {
        return new CreatePaymentProcessorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPaymentProcessor(UserVisitPK userVisitPK, EditPaymentProcessorForm form) {
        return new EditPaymentProcessorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPaymentProcessors(UserVisitPK userVisitPK, GetPaymentProcessorsForm form) {
        return new GetPaymentProcessorsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPaymentProcessor(UserVisitPK userVisitPK, GetPaymentProcessorForm form) {
        return new GetPaymentProcessorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPaymentProcessorChoices(UserVisitPK userVisitPK, GetPaymentProcessorChoicesForm form) {
        return new GetPaymentProcessorChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultPaymentProcessor(UserVisitPK userVisitPK, SetDefaultPaymentProcessorForm form) {
        return new SetDefaultPaymentProcessorCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePaymentProcessor(UserVisitPK userVisitPK, DeletePaymentProcessorForm form) {
        return new DeletePaymentProcessorCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorDescription(UserVisitPK userVisitPK, CreatePaymentProcessorDescriptionForm form) {
        return new CreatePaymentProcessorDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentProcessorDescription(UserVisitPK userVisitPK, EditPaymentProcessorDescriptionForm form) {
        return new EditPaymentProcessorDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorDescription(UserVisitPK userVisitPK, GetPaymentProcessorDescriptionForm form) {
        return new GetPaymentProcessorDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorDescriptionsForm form) {
        return new GetPaymentProcessorDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentProcessorDescription(UserVisitPK userVisitPK, DeletePaymentProcessorDescriptionForm form) {
        return new DeletePaymentProcessorDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Actions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorAction(UserVisitPK userVisitPK, CreatePaymentProcessorActionForm form) {
        return new CreatePaymentProcessorActionCommand().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentProcessorAction(UserVisitPK userVisitPK, GetPaymentProcessorActionForm form) {
//        return new GetPaymentProcessorActionCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult getPaymentProcessorActions(UserVisitPK userVisitPK, GetPaymentProcessorActionsForm form) {
//        return new GetPaymentProcessorActionsCommand().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult deletePaymentProcessorAction(UserVisitPK userVisitPK, DeletePaymentProcessorActionForm form) {
//        return new DeletePaymentProcessorActionCommand().run(userVisitPK, form);
//    }

    // -------------------------------------------------------------------------
    //   Payment Processor Transactions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getPaymentProcessorTransactions(UserVisitPK userVisitPK, GetPaymentProcessorTransactionsForm form) {
        return new GetPaymentProcessorTransactionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorTransaction(UserVisitPK userVisitPK, GetPaymentProcessorTransactionForm form) {
        return new GetPaymentProcessorTransactionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Transaction Codes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getPaymentProcessorTransactionCodes(UserVisitPK userVisitPK, GetPaymentProcessorTransactionCodesForm form) {
        return new GetPaymentProcessorTransactionCodesCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPaymentMethod(UserVisitPK userVisitPK, CreatePaymentMethodForm form) {
        return new CreatePaymentMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPaymentMethod(UserVisitPK userVisitPK, EditPaymentMethodForm form) {
        return new EditPaymentMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPaymentMethods(UserVisitPK userVisitPK, GetPaymentMethodsForm form) {
        return new GetPaymentMethodsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPaymentMethod(UserVisitPK userVisitPK, GetPaymentMethodForm form) {
        return new GetPaymentMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPaymentMethodChoices(UserVisitPK userVisitPK, GetPaymentMethodChoicesForm form) {
        return new GetPaymentMethodChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultPaymentMethod(UserVisitPK userVisitPK, SetDefaultPaymentMethodForm form) {
        return new SetDefaultPaymentMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePaymentMethod(UserVisitPK userVisitPK, DeletePaymentMethodForm form) {
        return new DeletePaymentMethodCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Payment Method Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPaymentMethodDescription(UserVisitPK userVisitPK, CreatePaymentMethodDescriptionForm form) {
        return new CreatePaymentMethodDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPaymentMethodDescription(UserVisitPK userVisitPK, EditPaymentMethodDescriptionForm form) {
        return new EditPaymentMethodDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPaymentMethodDescription(UserVisitPK userVisitPK, GetPaymentMethodDescriptionForm form) {
        return new GetPaymentMethodDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentMethodDescriptions(UserVisitPK userVisitPK, GetPaymentMethodDescriptionsForm form) {
        return new GetPaymentMethodDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentMethodDescription(UserVisitPK userVisitPK, DeletePaymentMethodDescriptionForm form) {
        return new DeletePaymentMethodDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Payment Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyPaymentMethod(UserVisitPK userVisitPK, CreatePartyPaymentMethodForm form) {
        return new CreatePartyPaymentMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPartyPaymentMethod(UserVisitPK userVisitPK, EditPartyPaymentMethodForm form) {
        return new EditPartyPaymentMethodCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyPaymentMethod(UserVisitPK userVisitPK, GetPartyPaymentMethodForm form) {
        return new GetPartyPaymentMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyPaymentMethods(UserVisitPK userVisitPK, GetPartyPaymentMethodsForm form) {
        return new GetPartyPaymentMethodsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyPaymentMethodChoices(UserVisitPK userVisitPK, GetPartyPaymentMethodChoicesForm form) {
        return new GetPartyPaymentMethodChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPartyPaymentMethod(UserVisitPK userVisitPK, SetDefaultPartyPaymentMethodForm form) {
        return new SetDefaultPartyPaymentMethodCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyPaymentMethod(UserVisitPK userVisitPK, DeletePartyPaymentMethodForm form) {
        return new DeletePartyPaymentMethodCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Action Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorActionType(UserVisitPK userVisitPK, CreatePaymentProcessorActionTypeForm form) {
        return new CreatePaymentProcessorActionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorActionTypes(UserVisitPK userVisitPK, GetPaymentProcessorActionTypesForm form) {
        return new GetPaymentProcessorActionTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorActionType(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeForm form) {
        return new GetPaymentProcessorActionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorActionTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeChoicesForm form) {
        return new GetPaymentProcessorActionTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPaymentProcessorActionType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorActionTypeForm form) {
        return new SetDefaultPaymentProcessorActionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentProcessorActionType(UserVisitPK userVisitPK, EditPaymentProcessorActionTypeForm form) {
        return new EditPaymentProcessorActionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentProcessorActionType(UserVisitPK userVisitPK, DeletePaymentProcessorActionTypeForm form) {
        return new DeletePaymentProcessorActionTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Action Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorActionTypeDescriptionForm form) {
        return new CreatePaymentProcessorActionTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorActionTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeDescriptionsForm form) {
        return new GetPaymentProcessorActionTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorActionTypeDescriptionForm form) {
        return new EditPaymentProcessorActionTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorActionTypeDescriptionForm form) {
        return new DeletePaymentProcessorActionTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Result Codes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorResultCode(UserVisitPK userVisitPK, CreatePaymentProcessorResultCodeForm form) {
        return new CreatePaymentProcessorResultCodeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorResultCodes(UserVisitPK userVisitPK, GetPaymentProcessorResultCodesForm form) {
        return new GetPaymentProcessorResultCodesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorResultCode(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeForm form) {
        return new GetPaymentProcessorResultCodeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorResultCodeChoices(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeChoicesForm form) {
        return new GetPaymentProcessorResultCodeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPaymentProcessorResultCode(UserVisitPK userVisitPK, SetDefaultPaymentProcessorResultCodeForm form) {
        return new SetDefaultPaymentProcessorResultCodeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentProcessorResultCode(UserVisitPK userVisitPK, EditPaymentProcessorResultCodeForm form) {
        return new EditPaymentProcessorResultCodeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentProcessorResultCode(UserVisitPK userVisitPK, DeletePaymentProcessorResultCodeForm form) {
        return new DeletePaymentProcessorResultCodeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Result Code Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorResultCodeDescriptionForm form) {
        return new CreatePaymentProcessorResultCodeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorResultCodeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeDescriptionsForm form) {
        return new GetPaymentProcessorResultCodeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, EditPaymentProcessorResultCodeDescriptionForm form) {
        return new EditPaymentProcessorResultCodeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorResultCodeDescriptionForm form) {
        return new DeletePaymentProcessorResultCodeDescriptionCommand().run(userVisitPK, form);
    }

}
