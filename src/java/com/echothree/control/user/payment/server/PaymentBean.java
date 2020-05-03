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
    public CommandResult getPaymentMethodTypeChoices(UserVisitPK userVisitPK, GetPaymentMethodTypeChoicesForm form) {
        return new GetPaymentMethodTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPaymentMethodType(UserVisitPK userVisitPK, GetPaymentMethodTypeForm form) {
        return new GetPaymentMethodTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPaymentMethodTypes(UserVisitPK userVisitPK, GetPaymentMethodTypesForm form) {
        return new GetPaymentMethodTypesCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Payment Method Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPaymentMethodTypeDescription(UserVisitPK userVisitPK, CreatePaymentMethodTypeDescriptionForm form) {
        return new CreatePaymentMethodTypeDescriptionCommand(userVisitPK, form).run();
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
    public CommandResult getPaymentProcessorTypeChoices(UserVisitPK userVisitPK, GetPaymentProcessorTypeChoicesForm form) {
        return new GetPaymentProcessorTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPaymentProcessorType(UserVisitPK userVisitPK, GetPaymentProcessorTypeForm form) {
        return new GetPaymentProcessorTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPaymentProcessorTypes(UserVisitPK userVisitPK, GetPaymentProcessorTypesForm form) {
        return new GetPaymentProcessorTypesCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Payment Processor Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPaymentProcessorTypeDescription(UserVisitPK userVisitPK, CreatePaymentProcessorTypeDescriptionForm form) {
        return new CreatePaymentProcessorTypeDescriptionCommand(userVisitPK, form).run();
    }
    
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

}
