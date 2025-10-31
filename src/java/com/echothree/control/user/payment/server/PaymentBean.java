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
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateBillingAccountRoleTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Billing Account Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createBillingAccountRoleTypeDescription(UserVisitPK userVisitPK, CreateBillingAccountRoleTypeDescriptionForm form) {
        return CDI.current().select(CreateBillingAccountRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Payment Method Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentMethodType(UserVisitPK userVisitPK, CreatePaymentMethodTypeForm form) {
        return CDI.current().select(CreatePaymentMethodTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentMethodTypes(UserVisitPK userVisitPK, GetPaymentMethodTypesForm form) {
        return CDI.current().select(GetPaymentMethodTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentMethodType(UserVisitPK userVisitPK, GetPaymentMethodTypeForm form) {
        return CDI.current().select(GetPaymentMethodTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentMethodTypeChoices(UserVisitPK userVisitPK, GetPaymentMethodTypeChoicesForm form) {
        return CDI.current().select(GetPaymentMethodTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPaymentMethodType(UserVisitPK userVisitPK, SetDefaultPaymentMethodTypeForm form) {
        return CDI.current().select(SetDefaultPaymentMethodTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentMethodType(UserVisitPK userVisitPK, EditPaymentMethodTypeForm form) {
        return CDI.current().select(EditPaymentMethodTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentMethodType(UserVisitPK userVisitPK, DeletePaymentMethodTypeForm form) {
        return CDI.current().select(DeletePaymentMethodTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Method Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentMethodTypeDescription(UserVisitPK userVisitPK, CreatePaymentMethodTypeDescriptionForm form) {
        return CDI.current().select(CreatePaymentMethodTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentMethodTypeDescriptions(UserVisitPK userVisitPK, GetPaymentMethodTypeDescriptionsForm form) {
        return CDI.current().select(GetPaymentMethodTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentMethodTypeDescription(UserVisitPK userVisitPK, EditPaymentMethodTypeDescriptionForm form) {
        return CDI.current().select(EditPaymentMethodTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentMethodTypeDescription(UserVisitPK userVisitPK, DeletePaymentMethodTypeDescriptionForm form) {
        return CDI.current().select(DeletePaymentMethodTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Method Type PartyTypes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentMethodTypePartyType(UserVisitPK userVisitPK, CreatePaymentMethodTypePartyTypeForm form) {
        return CDI.current().select(CreatePaymentMethodTypePartyTypeCommand.class).get().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentMethodTypePartyTypes(UserVisitPK userVisitPK, GetPaymentMethodTypePartyTypesForm form) {
//        return CDI.current().select(GetPaymentMethodTypePartyTypesCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult getPaymentMethodTypePartyType(UserVisitPK userVisitPK, GetPaymentMethodTypePartyTypeForm form) {
//        return CDI.current().select(GetPaymentMethodTypePartyTypeCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult getPaymentMethodTypePartyTypeChoices(UserVisitPK userVisitPK, GetPaymentMethodTypePartyTypeChoicesForm form) {
//        return CDI.current().select(GetPaymentMethodTypePartyTypeChoicesCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult setDefaultPaymentMethodTypePartyType(UserVisitPK userVisitPK, SetDefaultPaymentMethodTypePartyTypeForm form) {
//        return CDI.current().select(SetDefaultPaymentMethodTypePartyTypeCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult editPaymentMethodTypePartyType(UserVisitPK userVisitPK, EditPaymentMethodTypePartyTypeForm form) {
//        return CDI.current().select(EditPaymentMethodTypePartyTypeCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult deletePaymentMethodTypePartyType(UserVisitPK userVisitPK, DeletePaymentMethodTypePartyTypeForm form) {
//        return CDI.current().select(DeletePaymentMethodTypePartyTypeCommand.class).get().run(userVisitPK, form);
//    }

    // -------------------------------------------------------------------------
    //   Payment Processor Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorType(UserVisitPK userVisitPK, CreatePaymentProcessorTypeForm form) {
        return CDI.current().select(CreatePaymentProcessorTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorTypes(UserVisitPK userVisitPK, GetPaymentProcessorTypesForm form) {
        return CDI.current().select(GetPaymentProcessorTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorType(UserVisitPK userVisitPK, GetPaymentProcessorTypeForm form) {
        return CDI.current().select(GetPaymentProcessorTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeChoicesForm form) {
        return CDI.current().select(GetPaymentProcessorTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPaymentProcessorType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeForm form) {
        return CDI.current().select(SetDefaultPaymentProcessorTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentProcessorType(UserVisitPK userVisitPK, EditPaymentProcessorTypeForm form) {
        return CDI.current().select(EditPaymentProcessorTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentProcessorType(UserVisitPK userVisitPK, DeletePaymentProcessorTypeForm form) {
        return CDI.current().select(DeletePaymentProcessorTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeDescriptionForm form) {
        return CDI.current().select(CreatePaymentProcessorTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeDescriptionsForm form) {
        return CDI.current().select(GetPaymentProcessorTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentProcessorTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeDescriptionForm form) {
        return CDI.current().select(EditPaymentProcessorTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentProcessorTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeDescriptionForm form) {
        return CDI.current().select(DeletePaymentProcessorTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeTypeForm form) {
        return CDI.current().select(CreatePaymentProcessorTypeCodeTypeCommand.class).get().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeCodeTypes(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypesForm form) {
//        return CDI.current().select(GetPaymentProcessorTypeCodeTypesCommand.class).get().run(userVisitPK, form);
//    }

    @Override
    public CommandResult getPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeForm form) {
        return CDI.current().select(GetPaymentProcessorTypeCodeTypeCommand.class).get().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeCodeTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeChoicesForm form) {
//        return CDI.current().select(GetPaymentProcessorTypeCodeTypeChoicesCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult setDefaultPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeCodeTypeForm form) {
//        return CDI.current().select(SetDefaultPaymentProcessorTypeCodeTypeCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult editPaymentProcessorTypeCodeType(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeTypeForm form) {
//        return CDI.current().select(EditPaymentProcessorTypeCodeTypeCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult deletePaymentProcessorTypeCodeType(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeTypeForm form) {
//        return CDI.current().select(DeletePaymentProcessorTypeCodeTypeCommand.class).get().run(userVisitPK, form);
//    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeTypeDescriptionForm form) {
        return CDI.current().select(CreatePaymentProcessorTypeCodeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeDescriptionForm form) {
//        return CDI.current().select(GetPaymentProcessorTypeCodeTypeDescriptionCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult getPaymentProcessorTypeCodeTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeTypeDescriptionsForm form) {
//        return CDI.current().select(GetPaymentProcessorTypeCodeTypeDescriptionsCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult editPaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeTypeDescriptionForm form) {
//        return CDI.current().select(EditPaymentProcessorTypeCodeTypeDescriptionCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult deletePaymentProcessorTypeCodeTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeTypeDescriptionForm form) {
//        return CDI.current().select(DeletePaymentProcessorTypeCodeTypeDescriptionCommand.class).get().run(userVisitPK, form);
//    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Codes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeCode(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeForm form) {
        return CDI.current().select(CreatePaymentProcessorTypeCodeCommand.class).get().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeCodes(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodesForm form) {
//        return CDI.current().select(GetPaymentProcessorTypeCodesCommand.class).get().run(userVisitPK, form);
//    }

    @Override
    public CommandResult getPaymentProcessorTypeCode(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeForm form) {
        return CDI.current().select(GetPaymentProcessorTypeCodeCommand.class).get().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeCodeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeChoicesForm form) {
//        return CDI.current().select(GetPaymentProcessorTypeCodeChoicesCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult setDefaultPaymentProcessorTypeCode(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeCodeForm form) {
//        return CDI.current().select(SetDefaultPaymentProcessorTypeCodeCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult editPaymentProcessorTypeCode(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeForm form) {
//        return CDI.current().select(EditPaymentProcessorTypeCodeCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult deletePaymentProcessorTypeCode(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeForm form) {
//        return CDI.current().select(DeletePaymentProcessorTypeCodeCommand.class).get().run(userVisitPK, form);
//    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Code Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeCodeDescriptionForm form) {
        return CDI.current().select(CreatePaymentProcessorTypeCodeDescriptionCommand.class).get().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeDescriptionForm form) {
//        return CDI.current().select(GetPaymentProcessorTypeCodeDescriptionCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult getPaymentProcessorTypeCodeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorTypeCodeDescriptionsForm form) {
//        return CDI.current().select(GetPaymentProcessorTypeCodeDescriptionsCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult editPaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, EditPaymentProcessorTypeCodeDescriptionForm form) {
//        return CDI.current().select(EditPaymentProcessorTypeCodeDescriptionCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult deletePaymentProcessorTypeCodeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorTypeCodeDescriptionForm form) {
//        return CDI.current().select(DeletePaymentProcessorTypeCodeDescriptionCommand.class).get().run(userVisitPK, form);
//    }

    // -------------------------------------------------------------------------
    //   Payment Processor Type Actions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorTypeAction(UserVisitPK userVisitPK, CreatePaymentProcessorTypeActionForm form) {
        return CDI.current().select(CreatePaymentProcessorTypeActionCommand.class).get().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentProcessorTypeActions(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionsForm form) {
//        return CDI.current().select(GetPaymentProcessorTypeActionsCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult getPaymentProcessorTypeAction(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionForm form) {
//        return CDI.current().select(GetPaymentProcessorTypeActionCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult getPaymentProcessorTypeActionChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeActionChoicesForm form) {
//        return CDI.current().select(GetPaymentProcessorTypeActionChoicesCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult setDefaultPaymentProcessorTypeAction(UserVisitPK userVisitPK, SetDefaultPaymentProcessorTypeActionForm form) {
//        return CDI.current().select(SetDefaultPaymentProcessorTypeActionCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult editPaymentProcessorTypeAction(UserVisitPK userVisitPK, EditPaymentProcessorTypeActionForm form) {
//        return CDI.current().select(EditPaymentProcessorTypeActionCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult deletePaymentProcessorTypeAction(UserVisitPK userVisitPK, DeletePaymentProcessorTypeActionForm form) {
//        return CDI.current().select(DeletePaymentProcessorTypeActionCommand.class).get().run(userVisitPK, form);
//    }

    // -------------------------------------------------------------------------
    //   Payment Processors
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPaymentProcessor(UserVisitPK userVisitPK, CreatePaymentProcessorForm form) {
        return CDI.current().select(CreatePaymentProcessorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPaymentProcessor(UserVisitPK userVisitPK, EditPaymentProcessorForm form) {
        return CDI.current().select(EditPaymentProcessorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPaymentProcessors(UserVisitPK userVisitPK, GetPaymentProcessorsForm form) {
        return CDI.current().select(GetPaymentProcessorsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPaymentProcessor(UserVisitPK userVisitPK, GetPaymentProcessorForm form) {
        return CDI.current().select(GetPaymentProcessorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPaymentProcessorChoices(UserVisitPK userVisitPK, GetPaymentProcessorChoicesForm form) {
        return CDI.current().select(GetPaymentProcessorChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultPaymentProcessor(UserVisitPK userVisitPK, SetDefaultPaymentProcessorForm form) {
        return CDI.current().select(SetDefaultPaymentProcessorCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePaymentProcessor(UserVisitPK userVisitPK, DeletePaymentProcessorForm form) {
        return CDI.current().select(DeletePaymentProcessorCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorDescription(UserVisitPK userVisitPK, CreatePaymentProcessorDescriptionForm form) {
        return CDI.current().select(CreatePaymentProcessorDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentProcessorDescription(UserVisitPK userVisitPK, EditPaymentProcessorDescriptionForm form) {
        return CDI.current().select(EditPaymentProcessorDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorDescription(UserVisitPK userVisitPK, GetPaymentProcessorDescriptionForm form) {
        return CDI.current().select(GetPaymentProcessorDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorDescriptionsForm form) {
        return CDI.current().select(GetPaymentProcessorDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentProcessorDescription(UserVisitPK userVisitPK, DeletePaymentProcessorDescriptionForm form) {
        return CDI.current().select(DeletePaymentProcessorDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Actions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorAction(UserVisitPK userVisitPK, CreatePaymentProcessorActionForm form) {
        return CDI.current().select(CreatePaymentProcessorActionCommand.class).get().run(userVisitPK, form);
    }

//    @Override
//    public CommandResult getPaymentProcessorAction(UserVisitPK userVisitPK, GetPaymentProcessorActionForm form) {
//        return CDI.current().select(GetPaymentProcessorActionCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult getPaymentProcessorActions(UserVisitPK userVisitPK, GetPaymentProcessorActionsForm form) {
//        return CDI.current().select(GetPaymentProcessorActionsCommand.class).get().run(userVisitPK, form);
//    }
//
//    @Override
//    public CommandResult deletePaymentProcessorAction(UserVisitPK userVisitPK, DeletePaymentProcessorActionForm form) {
//        return CDI.current().select(DeletePaymentProcessorActionCommand.class).get().run(userVisitPK, form);
//    }

    // -------------------------------------------------------------------------
    //   Payment Processor Transactions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getPaymentProcessorTransactions(UserVisitPK userVisitPK, GetPaymentProcessorTransactionsForm form) {
        return CDI.current().select(GetPaymentProcessorTransactionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorTransaction(UserVisitPK userVisitPK, GetPaymentProcessorTransactionForm form) {
        return CDI.current().select(GetPaymentProcessorTransactionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Transaction Codes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getPaymentProcessorTransactionCodes(UserVisitPK userVisitPK, GetPaymentProcessorTransactionCodesForm form) {
        return CDI.current().select(GetPaymentProcessorTransactionCodesCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPaymentMethod(UserVisitPK userVisitPK, CreatePaymentMethodForm form) {
        return CDI.current().select(CreatePaymentMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPaymentMethod(UserVisitPK userVisitPK, EditPaymentMethodForm form) {
        return CDI.current().select(EditPaymentMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPaymentMethods(UserVisitPK userVisitPK, GetPaymentMethodsForm form) {
        return CDI.current().select(GetPaymentMethodsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPaymentMethod(UserVisitPK userVisitPK, GetPaymentMethodForm form) {
        return CDI.current().select(GetPaymentMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPaymentMethodChoices(UserVisitPK userVisitPK, GetPaymentMethodChoicesForm form) {
        return CDI.current().select(GetPaymentMethodChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultPaymentMethod(UserVisitPK userVisitPK, SetDefaultPaymentMethodForm form) {
        return CDI.current().select(SetDefaultPaymentMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePaymentMethod(UserVisitPK userVisitPK, DeletePaymentMethodForm form) {
        return CDI.current().select(DeletePaymentMethodCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Payment Method Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPaymentMethodDescription(UserVisitPK userVisitPK, CreatePaymentMethodDescriptionForm form) {
        return CDI.current().select(CreatePaymentMethodDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPaymentMethodDescription(UserVisitPK userVisitPK, EditPaymentMethodDescriptionForm form) {
        return CDI.current().select(EditPaymentMethodDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPaymentMethodDescription(UserVisitPK userVisitPK, GetPaymentMethodDescriptionForm form) {
        return CDI.current().select(GetPaymentMethodDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentMethodDescriptions(UserVisitPK userVisitPK, GetPaymentMethodDescriptionsForm form) {
        return CDI.current().select(GetPaymentMethodDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentMethodDescription(UserVisitPK userVisitPK, DeletePaymentMethodDescriptionForm form) {
        return CDI.current().select(DeletePaymentMethodDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Payment Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyPaymentMethod(UserVisitPK userVisitPK, CreatePartyPaymentMethodForm form) {
        return CDI.current().select(CreatePartyPaymentMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPartyPaymentMethod(UserVisitPK userVisitPK, EditPartyPaymentMethodForm form) {
        return CDI.current().select(EditPartyPaymentMethodCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyPaymentMethod(UserVisitPK userVisitPK, GetPartyPaymentMethodForm form) {
        return CDI.current().select(GetPartyPaymentMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyPaymentMethods(UserVisitPK userVisitPK, GetPartyPaymentMethodsForm form) {
        return CDI.current().select(GetPartyPaymentMethodsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyPaymentMethodChoices(UserVisitPK userVisitPK, GetPartyPaymentMethodChoicesForm form) {
        return CDI.current().select(GetPartyPaymentMethodChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPartyPaymentMethod(UserVisitPK userVisitPK, SetDefaultPartyPaymentMethodForm form) {
        return CDI.current().select(SetDefaultPartyPaymentMethodCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyPaymentMethod(UserVisitPK userVisitPK, DeletePartyPaymentMethodForm form) {
        return CDI.current().select(DeletePartyPaymentMethodCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Action Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorActionType(UserVisitPK userVisitPK, CreatePaymentProcessorActionTypeForm form) {
        return CDI.current().select(CreatePaymentProcessorActionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorActionTypes(UserVisitPK userVisitPK, GetPaymentProcessorActionTypesForm form) {
        return CDI.current().select(GetPaymentProcessorActionTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorActionType(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeForm form) {
        return CDI.current().select(GetPaymentProcessorActionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorActionTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeChoicesForm form) {
        return CDI.current().select(GetPaymentProcessorActionTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPaymentProcessorActionType(UserVisitPK userVisitPK, SetDefaultPaymentProcessorActionTypeForm form) {
        return CDI.current().select(SetDefaultPaymentProcessorActionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentProcessorActionType(UserVisitPK userVisitPK, EditPaymentProcessorActionTypeForm form) {
        return CDI.current().select(EditPaymentProcessorActionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentProcessorActionType(UserVisitPK userVisitPK, DeletePaymentProcessorActionTypeForm form) {
        return CDI.current().select(DeletePaymentProcessorActionTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Action Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorActionTypeDescriptionForm form) {
        return CDI.current().select(CreatePaymentProcessorActionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorActionTypeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorActionTypeDescriptionsForm form) {
        return CDI.current().select(GetPaymentProcessorActionTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, EditPaymentProcessorActionTypeDescriptionForm form) {
        return CDI.current().select(EditPaymentProcessorActionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentProcessorActionTypeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorActionTypeDescriptionForm form) {
        return CDI.current().select(DeletePaymentProcessorActionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Result Codes
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorResultCode(UserVisitPK userVisitPK, CreatePaymentProcessorResultCodeForm form) {
        return CDI.current().select(CreatePaymentProcessorResultCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorResultCodes(UserVisitPK userVisitPK, GetPaymentProcessorResultCodesForm form) {
        return CDI.current().select(GetPaymentProcessorResultCodesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorResultCode(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeForm form) {
        return CDI.current().select(GetPaymentProcessorResultCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorResultCodeChoices(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeChoicesForm form) {
        return CDI.current().select(GetPaymentProcessorResultCodeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPaymentProcessorResultCode(UserVisitPK userVisitPK, SetDefaultPaymentProcessorResultCodeForm form) {
        return CDI.current().select(SetDefaultPaymentProcessorResultCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentProcessorResultCode(UserVisitPK userVisitPK, EditPaymentProcessorResultCodeForm form) {
        return CDI.current().select(EditPaymentProcessorResultCodeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentProcessorResultCode(UserVisitPK userVisitPK, DeletePaymentProcessorResultCodeForm form) {
        return CDI.current().select(DeletePaymentProcessorResultCodeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Payment Processor Result Code Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorResultCodeDescriptionForm form) {
        return CDI.current().select(CreatePaymentProcessorResultCodeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPaymentProcessorResultCodeDescriptions(UserVisitPK userVisitPK, GetPaymentProcessorResultCodeDescriptionsForm form) {
        return CDI.current().select(GetPaymentProcessorResultCodeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, EditPaymentProcessorResultCodeDescriptionForm form) {
        return CDI.current().select(EditPaymentProcessorResultCodeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePaymentProcessorResultCodeDescription(UserVisitPK userVisitPK, DeletePaymentProcessorResultCodeDescriptionForm form) {
        return CDI.current().select(DeletePaymentProcessorResultCodeDescriptionCommand.class).get().run(userVisitPK, form);
    }

}
