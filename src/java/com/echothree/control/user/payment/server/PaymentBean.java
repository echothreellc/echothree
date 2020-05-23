// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
        return new CreateBillingAccountRoleTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Billing Account Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createBillingAccountRoleTypeDescription(UserVisitPK userVisitPK, CreateBillingAccountRoleTypeDescriptionForm form) {
        return new CreateBillingAccountRoleTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Payment Method Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentMethodType(UserVisitPK userVisitPK, CreatePaymentMethodTypeForm form) {
        return new CreatePaymentMethodTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentMethodTypes(UserVisitPK userVisitPK, GetPaymentMethodTypesForm form) {
        return new GetPaymentMethodTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentMethodType(UserVisitPK userVisitPK, GetPaymentMethodTypeForm form) {
        return new GetPaymentMethodTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentMethodTypeChoices(UserVisitPK userVisitPK, GetPaymentMethodTypeChoicesForm form) {
        return new GetPaymentMethodTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultPaymentMethodType(UserVisitPK userVisitPK, SetDefaultPaymentMethodTypeForm form) {
        return new SetDefaultPaymentMethodTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPaymentMethodType(UserVisitPK userVisitPK, EditPaymentMethodTypeForm form) {
        return new EditPaymentMethodTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePaymentMethodType(UserVisitPK userVisitPK, DeletePaymentMethodTypeForm form) {
        return new DeletePaymentMethodTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Payment Method Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentMethodTypeDescription(UserVisitPK userVisitPK, CreatePaymentMethodTypeDescriptionForm form) {
        return new CreatePaymentMethodTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentMethodTypeDescriptions(UserVisitPK userVisitPK, GetPaymentMethodTypeDescriptionsForm form) {
        return new GetPaymentMethodTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPaymentMethodTypeDescription(UserVisitPK userVisitPK, EditPaymentMethodTypeDescriptionForm form) {
        return new EditPaymentMethodTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePaymentMethodTypeDescription(UserVisitPK userVisitPK, DeletePaymentMethodTypeDescriptionForm form) {
        return new DeletePaymentMethodTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Payment Method Type Party Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPaymentMethodTypePartyType(UserVisitPK userVisitPK, CreatePaymentMethodTypePartyTypeForm form) {
        return new CreatePaymentMethodTypePartyTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorType(UserVisitPK userVisitPK, CreatePaymentProcessorTypeForm form) {
        return new CreatePaymentProcessorTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentProcessorTypes(UserVisitPK userVisitPK, GetPaymentProcessorTypesForm form) {
        return new GetPaymentProcessorTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentProcessorType(UserVisitPK userVisitPK, GetPaymentProcessorTypeForm form) {
        return new GetPaymentProcessorTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentProcessorTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeChoicesForm form) {
        return new GetPaymentProcessorTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultPaymentProcessorType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeForm form) {
        return new SetDefaultPaymentProcessorTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPaymentProcessorType(UserVisitPK userVisitPK, EditPaymentProcessorTypeForm form) {
        return new EditPaymentProcessorTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePaymentProcessorType(UserVisitPK userVisitPK, DeletePaymentProcessorTypeForm form) {
        return new DeletePaymentProcessorTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeDescriptionForm form) {
        return new CreatePaymentProcessorTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentProcessorTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeDescriptionsForm form) {
        return new GetPaymentProcessorTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPaymentProcessorTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeDescriptionForm form) {
        return new EditPaymentProcessorTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePaymentProcessorTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeDescriptionForm form) {
        return new DeletePaymentProcessorTypeDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeTypeForm form) {
        return new CreatePaymentProcessorTypeCodeTypeCommand(userVisitPK, form).run();
    }

    //    @Override
    //    public CommandResult getPaymentProcessorTypeCodeTypes(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypesForm form) {
    //        return new GetPaymentProcessorTypeCodeTypesCommand(userVisitPK, form).run();
    //    }
    //
    //    @Override
    //    public CommandResult getPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeForm form) {
    //        return new GetPaymentProcessorTypeCodeTypeCommand(userVisitPK, form).run();
    //    }
    //
    //    @Override
    //    public CommandResult getPaymentProcessorTypeCodeTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeChoicesForm form) {
    //        return new GetPaymentProcessorTypeCodeTypeChoicesCommand(userVisitPK, form).run();
    //    }
    //
    //    @Override
    //    public CommandResult setDefaultPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeCodeTypeForm form) {
    //        return new SetDefaultPaymentProcessorTypeCodeTypeCommand(userVisitPK, form).run();
    //    }
    //
    //    @Override
    //    public CommandResult editPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeTypeForm form) {
    //        return new EditPaymentProcessorTypeCodeTypeCommand(userVisitPK, form).run();
    //    }
    //
    //    @Override
    //    public CommandResult deletePaymentProcessorTypeCodeType(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeTypeForm form) {
    //        return new DeletePaymentProcessorTypeCodeTypeCommand(userVisitPK, form).run();
    //    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeTypeDescriptionForm form) {
        return new CreatePaymentProcessorTypeCodeTypeDescriptionCommand(userVisitPK, form).run();
    }

    //    @Override
    //    public CommandResult getPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeDescriptionForm form) {
    //        return new GetPaymentProcessorTypeCodeTypeDescriptionCommand(userVisitPK, form).run();
    //    }
    //
    //    @Override
    //    public CommandResult getPaymentProcessorTypeCodeTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeDescriptionsForm form) {
    //        return new GetPaymentProcessorTypeCodeTypeDescriptionsCommand(userVisitPK, form).run();
    //    }
    //
    //    @Override
    //    public CommandResult editPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeTypeDescriptionForm form) {
    //        return new EditPaymentProcessorTypeCodeTypeDescriptionCommand(userVisitPK, form).run();
    //    }
    //
    //    @Override
    //    public CommandResult deletePaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeTypeDescriptionForm form) {
    //        return new DeletePaymentProcessorTypeCodeTypeDescriptionCommand(userVisitPK, form).run();
    //    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Codes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeCode(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeForm form) {
        return new CreatePaymentProcessorTypeCodeCommand(userVisitPK, form).run();
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeCodes(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodesForm form) {
//        return new GetPaymentProcessorTypeCodesCommand(userVisitPK, form).run();
//    }
//
//    @Override
//    public CommandResult getPaymentProcessorTypeCode(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeForm form) {
//        return new GetPaymentProcessorTypeCodeCommand(userVisitPK, form).run();
//    }
//
//    @Override
//    public CommandResult getPaymentProcessorTypeCodeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeChoicesForm form) {
//        return new GetPaymentProcessorTypeCodeChoicesCommand(userVisitPK, form).run();
//    }
//
//    @Override
//    public CommandResult setDefaultPaymentProcessorTypeCode(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeCodeForm form) {
//        return new SetDefaultPaymentProcessorTypeCodeCommand(userVisitPK, form).run();
//    }
//
//    @Override
//    public CommandResult editPaymentProcessorTypeCode(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeForm form) {
//        return new EditPaymentProcessorTypeCodeCommand(userVisitPK, form).run();
//    }
//
//    @Override
//    public CommandResult deletePaymentProcessorTypeCode(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeForm form) {
//        return new DeletePaymentProcessorTypeCodeCommand(userVisitPK, form).run();
//    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeDescriptionForm form) {
        return new CreatePaymentProcessorTypeCodeDescriptionCommand(userVisitPK, form).run();
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeDescriptionForm form) {
//        return new GetPaymentProcessorTypeCodeDescriptionCommand(userVisitPK, form).run();
//    }
//
//    @Override
//    public CommandResult getPaymentProcessorTypeCodeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeDescriptionsForm form) {
//        return new GetPaymentProcessorTypeCodeDescriptionsCommand(userVisitPK, form).run();
//    }
//
//    @Override
//    public CommandResult editPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeDescriptionForm form) {
//        return new EditPaymentProcessorTypeCodeDescriptionCommand(userVisitPK, form).run();
//    }
//
//    @Override
//    public CommandResult deletePaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeDescriptionForm form) {
//        return new DeletePaymentProcessorTypeCodeDescriptionCommand(userVisitPK, form).run();
//    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Actions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeAction(UserVisitPK userVisitPK, CreatePaymentProcessorTypeActionForm form) {
        return new CreatePaymentProcessorTypeActionCommand(userVisitPK, form).run();
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeActions(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionsForm form) {
//        return new GetPaymentProcessorTypeActionsCommand(userVisitPK, form).run();
//    }
//
//    @Override
//    public CommandResult getPaymentProcessorTypeAction(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionForm form) {
//        return new GetPaymentProcessorTypeActionCommand(userVisitPK, form).run();
//    }
//
//    @Override
//    public CommandResult getPaymentProcessorTypeActionChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionChoicesForm form) {
//        return new GetPaymentProcessorTypeActionChoicesCommand(userVisitPK, form).run();
//    }
//
//    @Override
//    public CommandResult setDefaultPaymentProcessorTypeAction(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeActionForm form) {
//        return new SetDefaultPaymentProcessorTypeActionCommand(userVisitPK, form).run();
//    }
//
//    @Override
//    public CommandResult editPaymentProcessorTypeAction(UserVisitPK userVisitPK, EditPaymentProcessorTypeActionForm form) {
//        return new EditPaymentProcessorTypeActionCommand(userVisitPK, form).run();
//    }
//
//    @Override
//    public CommandResult deletePaymentProcessorTypeAction(UserVisitPK userVisitPK, DeletePaymentProcessorTypeActionForm form) {
//        return new DeletePaymentProcessorTypeActionCommand(userVisitPK, form).run();
//    }

    // -------------------------------------------------------------------------
    //   Payment Processors
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPaymentProcessor(UserVisitPK userVisitPK, CreatePaymentProcessorForm form) {
        return new CreatePaymentProcessorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPaymentProcessor(UserVisitPK userVisitPK, EditPaymentProcessorForm form) {
        return new EditPaymentProcessorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPaymentProcessors(UserVisitPK userVisitPK, GetPaymentProcessorsForm form) {
        return new GetPaymentProcessorsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPaymentProcessor(UserVisitPK userVisitPK, GetPaymentProcessorForm form) {
        return new GetPaymentProcessorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPaymentProcessorChoices(UserVisitPK userVisitPK, GetPaymentProcessorChoicesForm form) {
        return new GetPaymentProcessorChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultPaymentProcessor(UserVisitPK userVisitPK, SetDefaultPaymentProcessorForm form) {
        return new SetDefaultPaymentProcessorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePaymentProcessor(UserVisitPK userVisitPK, DeletePaymentProcessorForm form) {
        return new DeletePaymentProcessorCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorDescription(UserVisitPK userVisitPK, CreatePaymentProcessorDescriptionForm form) {
        return new CreatePaymentProcessorDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPaymentProcessorDescription(UserVisitPK userVisitPK, EditPaymentProcessorDescriptionForm form) {
        return new EditPaymentProcessorDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentProcessorDescription(UserVisitPK userVisitPK, GetPaymentProcessorDescriptionForm form) {
        return new GetPaymentProcessorDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentProcessorDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorDescriptionsForm form) {
        return new GetPaymentProcessorDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePaymentProcessorDescription(UserVisitPK userVisitPK, DeletePaymentProcessorDescriptionForm form) {
        return new DeletePaymentProcessorDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Actions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorAction(UserVisitPK userVisitPK, CreatePaymentProcessorActionForm form) {
        return new CreatePaymentProcessorActionCommand(userVisitPK, form).run();
    }

//    @Override
//    public CommandResult getPaymentProcessorAction(UserVisitPK userVisitPK, GetPaymentProcessorActionForm form) {
//        return new GetPaymentProcessorActionCommand(userVisitPK, form).run();
//    }
//
//    @Override
//    public CommandResult getPaymentProcessorActions(UserVisitPK userVisitPK, GetPaymentProcessorActionsForm form) {
//        return new GetPaymentProcessorActionsCommand(userVisitPK, form).run();
//    }
//
//    @Override
//    public CommandResult deletePaymentProcessorAction(UserVisitPK userVisitPK, DeletePaymentProcessorActionForm form) {
//        return new DeletePaymentProcessorActionCommand(userVisitPK, form).run();
//    }

    // -------------------------------------------------------------------------
    //   Payment Processor Transactions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getPaymentProcessorTransactions(UserVisitPK userVisitPK, GetPaymentProcessorTransactionsForm form) {
        return new GetPaymentProcessorTransactionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentProcessorTransaction(UserVisitPK userVisitPK, GetPaymentProcessorTransactionForm form) {
        return new GetPaymentProcessorTransactionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Payment Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPaymentMethod(UserVisitPK userVisitPK, CreatePaymentMethodForm form) {
        return new CreatePaymentMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPaymentMethod(UserVisitPK userVisitPK, EditPaymentMethodForm form) {
        return new EditPaymentMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPaymentMethods(UserVisitPK userVisitPK, GetPaymentMethodsForm form) {
        return new GetPaymentMethodsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPaymentMethod(UserVisitPK userVisitPK, GetPaymentMethodForm form) {
        return new GetPaymentMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPaymentMethodChoices(UserVisitPK userVisitPK, GetPaymentMethodChoicesForm form) {
        return new GetPaymentMethodChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultPaymentMethod(UserVisitPK userVisitPK, SetDefaultPaymentMethodForm form) {
        return new SetDefaultPaymentMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePaymentMethod(UserVisitPK userVisitPK, DeletePaymentMethodForm form) {
        return new DeletePaymentMethodCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Payment Method Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPaymentMethodDescription(UserVisitPK userVisitPK, CreatePaymentMethodDescriptionForm form) {
        return new CreatePaymentMethodDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPaymentMethodDescription(UserVisitPK userVisitPK, EditPaymentMethodDescriptionForm form) {
        return new EditPaymentMethodDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPaymentMethodDescription(UserVisitPK userVisitPK, GetPaymentMethodDescriptionForm form) {
        return new GetPaymentMethodDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentMethodDescriptions(UserVisitPK userVisitPK, GetPaymentMethodDescriptionsForm form) {
        return new GetPaymentMethodDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePaymentMethodDescription(UserVisitPK userVisitPK, DeletePaymentMethodDescriptionForm form) {
        return new DeletePaymentMethodDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Payment Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyPaymentMethod(UserVisitPK userVisitPK, CreatePartyPaymentMethodForm form) {
        return new CreatePartyPaymentMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPartyPaymentMethod(UserVisitPK userVisitPK, EditPartyPaymentMethodForm form) {
        return new EditPartyPaymentMethodCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyPaymentMethod(UserVisitPK userVisitPK, GetPartyPaymentMethodForm form) {
        return new GetPartyPaymentMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartyPaymentMethods(UserVisitPK userVisitPK, GetPartyPaymentMethodsForm form) {
        return new GetPartyPaymentMethodsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyPaymentMethodChoices(UserVisitPK userVisitPK, GetPartyPaymentMethodChoicesForm form) {
        return new GetPartyPaymentMethodChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultPartyPaymentMethod(UserVisitPK userVisitPK, SetDefaultPartyPaymentMethodForm form) {
        return new SetDefaultPartyPaymentMethodCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePartyPaymentMethod(UserVisitPK userVisitPK, DeletePartyPaymentMethodForm form) {
        return new DeletePartyPaymentMethodCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Action Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorActionType(UserVisitPK userVisitPK, CreatePaymentProcessorActionTypeForm form) {
        return new CreatePaymentProcessorActionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentProcessorActionTypes(UserVisitPK userVisitPK, GetPaymentProcessorActionTypesForm form) {
        return new GetPaymentProcessorActionTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentProcessorActionType(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeForm form) {
        return new GetPaymentProcessorActionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentProcessorActionTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeChoicesForm form) {
        return new GetPaymentProcessorActionTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultPaymentProcessorActionType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorActionTypeForm form) {
        return new SetDefaultPaymentProcessorActionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPaymentProcessorActionType(UserVisitPK userVisitPK, EditPaymentProcessorActionTypeForm form) {
        return new EditPaymentProcessorActionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePaymentProcessorActionType(UserVisitPK userVisitPK, DeletePaymentProcessorActionTypeForm form) {
        return new DeletePaymentProcessorActionTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Action Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorActionTypeDescriptionForm form) {
        return new CreatePaymentProcessorActionTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentProcessorActionTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeDescriptionsForm form) {
        return new GetPaymentProcessorActionTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorActionTypeDescriptionForm form) {
        return new EditPaymentProcessorActionTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorActionTypeDescriptionForm form) {
        return new DeletePaymentProcessorActionTypeDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Result Codes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorResultCode(UserVisitPK userVisitPK, CreatePaymentProcessorResultCodeForm form) {
        return new CreatePaymentProcessorResultCodeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentProcessorResultCodes(UserVisitPK userVisitPK, GetPaymentProcessorResultCodesForm form) {
        return new GetPaymentProcessorResultCodesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentProcessorResultCode(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeForm form) {
        return new GetPaymentProcessorResultCodeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentProcessorResultCodeChoices(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeChoicesForm form) {
        return new GetPaymentProcessorResultCodeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultPaymentProcessorResultCode(UserVisitPK userVisitPK, SetDefaultPaymentProcessorResultCodeForm form) {
        return new SetDefaultPaymentProcessorResultCodeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPaymentProcessorResultCode(UserVisitPK userVisitPK, EditPaymentProcessorResultCodeForm form) {
        return new EditPaymentProcessorResultCodeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePaymentProcessorResultCode(UserVisitPK userVisitPK, DeletePaymentProcessorResultCodeForm form) {
        return new DeletePaymentProcessorResultCodeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Result Code Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorResultCodeDescriptionForm form) {
        return new CreatePaymentProcessorResultCodeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPaymentProcessorResultCodeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeDescriptionsForm form) {
        return new GetPaymentProcessorResultCodeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, EditPaymentProcessorResultCodeDescriptionForm form) {
        return new EditPaymentProcessorResultCodeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorResultCodeDescriptionForm form) {
        return new DeletePaymentProcessorResultCodeDescriptionCommand(userVisitPK, form).run();
    }

}
