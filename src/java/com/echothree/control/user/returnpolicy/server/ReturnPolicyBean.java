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

package com.echothree.control.user.returnpolicy.server;

import com.echothree.control.user.returnpolicy.common.ReturnPolicyRemote;
import com.echothree.control.user.returnpolicy.common.form.*;
import com.echothree.control.user.returnpolicy.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class ReturnPolicyBean
        extends ReturnPolicyFormsImpl
        implements ReturnPolicyRemote, ReturnPolicyLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "ReturnPolicyBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Party Return Policies
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getPartyReturnPolicy(UserVisitPK userVisitPK, GetPartyReturnPolicyForm form) {
        return new GetPartyReturnPolicyCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyReturnPolicies(UserVisitPK userVisitPK, GetPartyReturnPoliciesForm form) {
        return new GetPartyReturnPoliciesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyReturnPolicyStatusChoices(UserVisitPK userVisitPK, GetPartyReturnPolicyStatusChoicesForm form) {
        return new GetPartyReturnPolicyStatusChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setPartyReturnPolicyStatus(UserVisitPK userVisitPK, SetPartyReturnPolicyStatusForm form) {
        return new SetPartyReturnPolicyStatusCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePartyReturnPolicy(UserVisitPK userVisitPK, DeletePartyReturnPolicyForm form) {
        return new DeletePartyReturnPolicyCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Return Kinds
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnKind(UserVisitPK userVisitPK, CreateReturnKindForm form) {
        return new CreateReturnKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnKinds(UserVisitPK userVisitPK, GetReturnKindsForm form) {
        return new GetReturnKindsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnKind(UserVisitPK userVisitPK, GetReturnKindForm form) {
        return new GetReturnKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnKindChoices(UserVisitPK userVisitPK, GetReturnKindChoicesForm form) {
        return new GetReturnKindChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultReturnKind(UserVisitPK userVisitPK, SetDefaultReturnKindForm form) {
        return new SetDefaultReturnKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editReturnKind(UserVisitPK userVisitPK, EditReturnKindForm form) {
        return new EditReturnKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteReturnKind(UserVisitPK userVisitPK, DeleteReturnKindForm form) {
        return new DeleteReturnKindCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Return Kind Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnKindDescription(UserVisitPK userVisitPK, CreateReturnKindDescriptionForm form) {
        return new CreateReturnKindDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnKindDescriptions(UserVisitPK userVisitPK, GetReturnKindDescriptionsForm form) {
        return new GetReturnKindDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getReturnKindDescription(UserVisitPK userVisitPK, GetReturnKindDescriptionForm form) {
        return new GetReturnKindDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editReturnKindDescription(UserVisitPK userVisitPK, EditReturnKindDescriptionForm form) {
        return new EditReturnKindDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteReturnKindDescription(UserVisitPK userVisitPK, DeleteReturnKindDescriptionForm form) {
        return new DeleteReturnKindDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Return Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnPolicy(UserVisitPK userVisitPK, CreateReturnPolicyForm form) {
        return new CreateReturnPolicyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnPolicies(UserVisitPK userVisitPK, GetReturnPoliciesForm form) {
        return new GetReturnPoliciesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnPolicy(UserVisitPK userVisitPK, GetReturnPolicyForm form) {
        return new GetReturnPolicyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnPolicyChoices(UserVisitPK userVisitPK, GetReturnPolicyChoicesForm form) {
        return new GetReturnPolicyChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultReturnPolicy(UserVisitPK userVisitPK, SetDefaultReturnPolicyForm form) {
        return new SetDefaultReturnPolicyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editReturnPolicy(UserVisitPK userVisitPK, EditReturnPolicyForm form) {
        return new EditReturnPolicyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteReturnPolicy(UserVisitPK userVisitPK, DeleteReturnPolicyForm form) {
        return new DeleteReturnPolicyCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Return Policy Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnPolicyTranslation(UserVisitPK userVisitPK, CreateReturnPolicyTranslationForm form) {
        return new CreateReturnPolicyTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnPolicyTranslation(UserVisitPK userVisitPK, GetReturnPolicyTranslationForm form) {
        return new GetReturnPolicyTranslationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getReturnPolicyTranslations(UserVisitPK userVisitPK, GetReturnPolicyTranslationsForm form) {
        return new GetReturnPolicyTranslationsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editReturnPolicyTranslation(UserVisitPK userVisitPK, EditReturnPolicyTranslationForm form) {
        return new EditReturnPolicyTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteReturnPolicyTranslation(UserVisitPK userVisitPK, DeleteReturnPolicyTranslationForm form) {
        return new DeleteReturnPolicyTranslationCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Return Policy Reasons
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnPolicyReason(UserVisitPK userVisitPK, CreateReturnPolicyReasonForm form) {
        return new CreateReturnPolicyReasonCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnPolicyReasons(UserVisitPK userVisitPK, GetReturnPolicyReasonsForm form) {
        return new GetReturnPolicyReasonsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultReturnPolicyReason(UserVisitPK userVisitPK, SetDefaultReturnPolicyReasonForm form) {
        return new SetDefaultReturnPolicyReasonCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editReturnPolicyReason(UserVisitPK userVisitPK, EditReturnPolicyReasonForm form) {
        return new EditReturnPolicyReasonCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteReturnPolicyReason(UserVisitPK userVisitPK, DeleteReturnPolicyReasonForm form) {
        return new DeleteReturnPolicyReasonCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Return Reasons
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnReason(UserVisitPK userVisitPK, CreateReturnReasonForm form) {
        return new CreateReturnReasonCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnReasons(UserVisitPK userVisitPK, GetReturnReasonsForm form) {
        return new GetReturnReasonsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnReason(UserVisitPK userVisitPK, GetReturnReasonForm form) {
        return new GetReturnReasonCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnReasonChoices(UserVisitPK userVisitPK, GetReturnReasonChoicesForm form) {
        return new GetReturnReasonChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultReturnReason(UserVisitPK userVisitPK, SetDefaultReturnReasonForm form) {
        return new SetDefaultReturnReasonCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editReturnReason(UserVisitPK userVisitPK, EditReturnReasonForm form) {
        return new EditReturnReasonCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteReturnReason(UserVisitPK userVisitPK, DeleteReturnReasonForm form) {
        return new DeleteReturnReasonCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Return Reason Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnReasonDescription(UserVisitPK userVisitPK, CreateReturnReasonDescriptionForm form) {
        return new CreateReturnReasonDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnReasonDescriptions(UserVisitPK userVisitPK, GetReturnReasonDescriptionsForm form) {
        return new GetReturnReasonDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editReturnReasonDescription(UserVisitPK userVisitPK, EditReturnReasonDescriptionForm form) {
        return new EditReturnReasonDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteReturnReasonDescription(UserVisitPK userVisitPK, DeleteReturnReasonDescriptionForm form) {
        return new DeleteReturnReasonDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Return Reason Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnReasonType(UserVisitPK userVisitPK, CreateReturnReasonTypeForm form) {
        return new CreateReturnReasonTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnReasonTypes(UserVisitPK userVisitPK, GetReturnReasonTypesForm form) {
        return new GetReturnReasonTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultReturnReasonType(UserVisitPK userVisitPK, SetDefaultReturnReasonTypeForm form) {
        return new SetDefaultReturnReasonTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editReturnReasonType(UserVisitPK userVisitPK, EditReturnReasonTypeForm form) {
        return new EditReturnReasonTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteReturnReasonType(UserVisitPK userVisitPK, DeleteReturnReasonTypeForm form) {
        return new DeleteReturnReasonTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Return Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnType(UserVisitPK userVisitPK, CreateReturnTypeForm form) {
        return new CreateReturnTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnTypes(UserVisitPK userVisitPK, GetReturnTypesForm form) {
        return new GetReturnTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnType(UserVisitPK userVisitPK, GetReturnTypeForm form) {
        return new GetReturnTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnTypeChoices(UserVisitPK userVisitPK, GetReturnTypeChoicesForm form) {
        return new GetReturnTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultReturnType(UserVisitPK userVisitPK, SetDefaultReturnTypeForm form) {
        return new SetDefaultReturnTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editReturnType(UserVisitPK userVisitPK, EditReturnTypeForm form) {
        return new EditReturnTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteReturnType(UserVisitPK userVisitPK, DeleteReturnTypeForm form) {
        return new DeleteReturnTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Return Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnTypeDescription(UserVisitPK userVisitPK, CreateReturnTypeDescriptionForm form) {
        return new CreateReturnTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnTypeDescriptions(UserVisitPK userVisitPK, GetReturnTypeDescriptionsForm form) {
        return new GetReturnTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getReturnTypeDescription(UserVisitPK userVisitPK, GetReturnTypeDescriptionForm form) {
        return new GetReturnTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editReturnTypeDescription(UserVisitPK userVisitPK, EditReturnTypeDescriptionForm form) {
        return new EditReturnTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteReturnTypeDescription(UserVisitPK userVisitPK, DeleteReturnTypeDescriptionForm form) {
        return new DeleteReturnTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Return Type Shipping Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnTypeShippingMethod(UserVisitPK userVisitPK, CreateReturnTypeShippingMethodForm form) {
        return new CreateReturnTypeShippingMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getReturnTypeShippingMethods(UserVisitPK userVisitPK, GetReturnTypeShippingMethodsForm form) {
        return new GetReturnTypeShippingMethodsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultReturnTypeShippingMethod(UserVisitPK userVisitPK, SetDefaultReturnTypeShippingMethodForm form) {
        return new SetDefaultReturnTypeShippingMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editReturnTypeShippingMethod(UserVisitPK userVisitPK, EditReturnTypeShippingMethodForm form) {
        return new EditReturnTypeShippingMethodCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteReturnTypeShippingMethod(UserVisitPK userVisitPK, DeleteReturnTypeShippingMethodForm form) {
        return new DeleteReturnTypeShippingMethodCommand(userVisitPK, form).run();
    }
    
}
