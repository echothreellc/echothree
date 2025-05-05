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
        return new GetPartyReturnPolicyCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyReturnPolicies(UserVisitPK userVisitPK, GetPartyReturnPoliciesForm form) {
        return new GetPartyReturnPoliciesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyReturnPolicyStatusChoices(UserVisitPK userVisitPK, GetPartyReturnPolicyStatusChoicesForm form) {
        return new GetPartyReturnPolicyStatusChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setPartyReturnPolicyStatus(UserVisitPK userVisitPK, SetPartyReturnPolicyStatusForm form) {
        return new SetPartyReturnPolicyStatusCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyReturnPolicy(UserVisitPK userVisitPK, DeletePartyReturnPolicyForm form) {
        return new DeletePartyReturnPolicyCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Return Kinds
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnKind(UserVisitPK userVisitPK, CreateReturnKindForm form) {
        return new CreateReturnKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnKinds(UserVisitPK userVisitPK, GetReturnKindsForm form) {
        return new GetReturnKindsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnKind(UserVisitPK userVisitPK, GetReturnKindForm form) {
        return new GetReturnKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnKindChoices(UserVisitPK userVisitPK, GetReturnKindChoicesForm form) {
        return new GetReturnKindChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultReturnKind(UserVisitPK userVisitPK, SetDefaultReturnKindForm form) {
        return new SetDefaultReturnKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editReturnKind(UserVisitPK userVisitPK, EditReturnKindForm form) {
        return new EditReturnKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnKind(UserVisitPK userVisitPK, DeleteReturnKindForm form) {
        return new DeleteReturnKindCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Kind Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnKindDescription(UserVisitPK userVisitPK, CreateReturnKindDescriptionForm form) {
        return new CreateReturnKindDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnKindDescriptions(UserVisitPK userVisitPK, GetReturnKindDescriptionsForm form) {
        return new GetReturnKindDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getReturnKindDescription(UserVisitPK userVisitPK, GetReturnKindDescriptionForm form) {
        return new GetReturnKindDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editReturnKindDescription(UserVisitPK userVisitPK, EditReturnKindDescriptionForm form) {
        return new EditReturnKindDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnKindDescription(UserVisitPK userVisitPK, DeleteReturnKindDescriptionForm form) {
        return new DeleteReturnKindDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnPolicy(UserVisitPK userVisitPK, CreateReturnPolicyForm form) {
        return new CreateReturnPolicyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnPolicies(UserVisitPK userVisitPK, GetReturnPoliciesForm form) {
        return new GetReturnPoliciesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnPolicy(UserVisitPK userVisitPK, GetReturnPolicyForm form) {
        return new GetReturnPolicyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnPolicyChoices(UserVisitPK userVisitPK, GetReturnPolicyChoicesForm form) {
        return new GetReturnPolicyChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultReturnPolicy(UserVisitPK userVisitPK, SetDefaultReturnPolicyForm form) {
        return new SetDefaultReturnPolicyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editReturnPolicy(UserVisitPK userVisitPK, EditReturnPolicyForm form) {
        return new EditReturnPolicyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnPolicy(UserVisitPK userVisitPK, DeleteReturnPolicyForm form) {
        return new DeleteReturnPolicyCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Policy Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnPolicyTranslation(UserVisitPK userVisitPK, CreateReturnPolicyTranslationForm form) {
        return new CreateReturnPolicyTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnPolicyTranslation(UserVisitPK userVisitPK, GetReturnPolicyTranslationForm form) {
        return new GetReturnPolicyTranslationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getReturnPolicyTranslations(UserVisitPK userVisitPK, GetReturnPolicyTranslationsForm form) {
        return new GetReturnPolicyTranslationsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editReturnPolicyTranslation(UserVisitPK userVisitPK, EditReturnPolicyTranslationForm form) {
        return new EditReturnPolicyTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnPolicyTranslation(UserVisitPK userVisitPK, DeleteReturnPolicyTranslationForm form) {
        return new DeleteReturnPolicyTranslationCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Policy Reasons
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnPolicyReason(UserVisitPK userVisitPK, CreateReturnPolicyReasonForm form) {
        return new CreateReturnPolicyReasonCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnPolicyReasons(UserVisitPK userVisitPK, GetReturnPolicyReasonsForm form) {
        return new GetReturnPolicyReasonsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultReturnPolicyReason(UserVisitPK userVisitPK, SetDefaultReturnPolicyReasonForm form) {
        return new SetDefaultReturnPolicyReasonCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editReturnPolicyReason(UserVisitPK userVisitPK, EditReturnPolicyReasonForm form) {
        return new EditReturnPolicyReasonCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnPolicyReason(UserVisitPK userVisitPK, DeleteReturnPolicyReasonForm form) {
        return new DeleteReturnPolicyReasonCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Reasons
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnReason(UserVisitPK userVisitPK, CreateReturnReasonForm form) {
        return new CreateReturnReasonCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnReasons(UserVisitPK userVisitPK, GetReturnReasonsForm form) {
        return new GetReturnReasonsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnReason(UserVisitPK userVisitPK, GetReturnReasonForm form) {
        return new GetReturnReasonCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnReasonChoices(UserVisitPK userVisitPK, GetReturnReasonChoicesForm form) {
        return new GetReturnReasonChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultReturnReason(UserVisitPK userVisitPK, SetDefaultReturnReasonForm form) {
        return new SetDefaultReturnReasonCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editReturnReason(UserVisitPK userVisitPK, EditReturnReasonForm form) {
        return new EditReturnReasonCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnReason(UserVisitPK userVisitPK, DeleteReturnReasonForm form) {
        return new DeleteReturnReasonCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Reason Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnReasonDescription(UserVisitPK userVisitPK, CreateReturnReasonDescriptionForm form) {
        return new CreateReturnReasonDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnReasonDescriptions(UserVisitPK userVisitPK, GetReturnReasonDescriptionsForm form) {
        return new GetReturnReasonDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editReturnReasonDescription(UserVisitPK userVisitPK, EditReturnReasonDescriptionForm form) {
        return new EditReturnReasonDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnReasonDescription(UserVisitPK userVisitPK, DeleteReturnReasonDescriptionForm form) {
        return new DeleteReturnReasonDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Reason Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnReasonType(UserVisitPK userVisitPK, CreateReturnReasonTypeForm form) {
        return new CreateReturnReasonTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnReasonTypes(UserVisitPK userVisitPK, GetReturnReasonTypesForm form) {
        return new GetReturnReasonTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultReturnReasonType(UserVisitPK userVisitPK, SetDefaultReturnReasonTypeForm form) {
        return new SetDefaultReturnReasonTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editReturnReasonType(UserVisitPK userVisitPK, EditReturnReasonTypeForm form) {
        return new EditReturnReasonTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnReasonType(UserVisitPK userVisitPK, DeleteReturnReasonTypeForm form) {
        return new DeleteReturnReasonTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnType(UserVisitPK userVisitPK, CreateReturnTypeForm form) {
        return new CreateReturnTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnTypes(UserVisitPK userVisitPK, GetReturnTypesForm form) {
        return new GetReturnTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnType(UserVisitPK userVisitPK, GetReturnTypeForm form) {
        return new GetReturnTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnTypeChoices(UserVisitPK userVisitPK, GetReturnTypeChoicesForm form) {
        return new GetReturnTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultReturnType(UserVisitPK userVisitPK, SetDefaultReturnTypeForm form) {
        return new SetDefaultReturnTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editReturnType(UserVisitPK userVisitPK, EditReturnTypeForm form) {
        return new EditReturnTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnType(UserVisitPK userVisitPK, DeleteReturnTypeForm form) {
        return new DeleteReturnTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnTypeDescription(UserVisitPK userVisitPK, CreateReturnTypeDescriptionForm form) {
        return new CreateReturnTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnTypeDescriptions(UserVisitPK userVisitPK, GetReturnTypeDescriptionsForm form) {
        return new GetReturnTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getReturnTypeDescription(UserVisitPK userVisitPK, GetReturnTypeDescriptionForm form) {
        return new GetReturnTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editReturnTypeDescription(UserVisitPK userVisitPK, EditReturnTypeDescriptionForm form) {
        return new EditReturnTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnTypeDescription(UserVisitPK userVisitPK, DeleteReturnTypeDescriptionForm form) {
        return new DeleteReturnTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Type Shipping Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnTypeShippingMethod(UserVisitPK userVisitPK, CreateReturnTypeShippingMethodForm form) {
        return new CreateReturnTypeShippingMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnTypeShippingMethods(UserVisitPK userVisitPK, GetReturnTypeShippingMethodsForm form) {
        return new GetReturnTypeShippingMethodsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultReturnTypeShippingMethod(UserVisitPK userVisitPK, SetDefaultReturnTypeShippingMethodForm form) {
        return new SetDefaultReturnTypeShippingMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editReturnTypeShippingMethod(UserVisitPK userVisitPK, EditReturnTypeShippingMethodForm form) {
        return new EditReturnTypeShippingMethodCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnTypeShippingMethod(UserVisitPK userVisitPK, DeleteReturnTypeShippingMethodForm form) {
        return new DeleteReturnTypeShippingMethodCommand().run(userVisitPK, form);
    }
    
}
