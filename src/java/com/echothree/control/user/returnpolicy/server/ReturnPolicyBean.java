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

package com.echothree.control.user.returnpolicy.server;

import com.echothree.control.user.returnpolicy.common.ReturnPolicyRemote;
import com.echothree.control.user.returnpolicy.common.form.*;
import com.echothree.control.user.returnpolicy.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(GetPartyReturnPolicyCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyReturnPolicies(UserVisitPK userVisitPK, GetPartyReturnPoliciesForm form) {
        return CDI.current().select(GetPartyReturnPoliciesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyReturnPolicyStatusChoices(UserVisitPK userVisitPK, GetPartyReturnPolicyStatusChoicesForm form) {
        return CDI.current().select(GetPartyReturnPolicyStatusChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setPartyReturnPolicyStatus(UserVisitPK userVisitPK, SetPartyReturnPolicyStatusForm form) {
        return CDI.current().select(SetPartyReturnPolicyStatusCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyReturnPolicy(UserVisitPK userVisitPK, DeletePartyReturnPolicyForm form) {
        return CDI.current().select(DeletePartyReturnPolicyCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Return Kinds
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnKind(UserVisitPK userVisitPK, CreateReturnKindForm form) {
        return CDI.current().select(CreateReturnKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnKinds(UserVisitPK userVisitPK, GetReturnKindsForm form) {
        return CDI.current().select(GetReturnKindsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnKind(UserVisitPK userVisitPK, GetReturnKindForm form) {
        return CDI.current().select(GetReturnKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnKindChoices(UserVisitPK userVisitPK, GetReturnKindChoicesForm form) {
        return CDI.current().select(GetReturnKindChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultReturnKind(UserVisitPK userVisitPK, SetDefaultReturnKindForm form) {
        return CDI.current().select(SetDefaultReturnKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editReturnKind(UserVisitPK userVisitPK, EditReturnKindForm form) {
        return CDI.current().select(EditReturnKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnKind(UserVisitPK userVisitPK, DeleteReturnKindForm form) {
        return CDI.current().select(DeleteReturnKindCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Kind Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnKindDescription(UserVisitPK userVisitPK, CreateReturnKindDescriptionForm form) {
        return CDI.current().select(CreateReturnKindDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnKindDescriptions(UserVisitPK userVisitPK, GetReturnKindDescriptionsForm form) {
        return CDI.current().select(GetReturnKindDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getReturnKindDescription(UserVisitPK userVisitPK, GetReturnKindDescriptionForm form) {
        return CDI.current().select(GetReturnKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editReturnKindDescription(UserVisitPK userVisitPK, EditReturnKindDescriptionForm form) {
        return CDI.current().select(EditReturnKindDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnKindDescription(UserVisitPK userVisitPK, DeleteReturnKindDescriptionForm form) {
        return CDI.current().select(DeleteReturnKindDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnPolicy(UserVisitPK userVisitPK, CreateReturnPolicyForm form) {
        return CDI.current().select(CreateReturnPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnPolicies(UserVisitPK userVisitPK, GetReturnPoliciesForm form) {
        return CDI.current().select(GetReturnPoliciesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnPolicy(UserVisitPK userVisitPK, GetReturnPolicyForm form) {
        return CDI.current().select(GetReturnPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnPolicyChoices(UserVisitPK userVisitPK, GetReturnPolicyChoicesForm form) {
        return CDI.current().select(GetReturnPolicyChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultReturnPolicy(UserVisitPK userVisitPK, SetDefaultReturnPolicyForm form) {
        return CDI.current().select(SetDefaultReturnPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editReturnPolicy(UserVisitPK userVisitPK, EditReturnPolicyForm form) {
        return CDI.current().select(EditReturnPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnPolicy(UserVisitPK userVisitPK, DeleteReturnPolicyForm form) {
        return CDI.current().select(DeleteReturnPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Policy Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnPolicyTranslation(UserVisitPK userVisitPK, CreateReturnPolicyTranslationForm form) {
        return CDI.current().select(CreateReturnPolicyTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnPolicyTranslation(UserVisitPK userVisitPK, GetReturnPolicyTranslationForm form) {
        return CDI.current().select(GetReturnPolicyTranslationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getReturnPolicyTranslations(UserVisitPK userVisitPK, GetReturnPolicyTranslationsForm form) {
        return CDI.current().select(GetReturnPolicyTranslationsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editReturnPolicyTranslation(UserVisitPK userVisitPK, EditReturnPolicyTranslationForm form) {
        return CDI.current().select(EditReturnPolicyTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnPolicyTranslation(UserVisitPK userVisitPK, DeleteReturnPolicyTranslationForm form) {
        return CDI.current().select(DeleteReturnPolicyTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Policy Reasons
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnPolicyReason(UserVisitPK userVisitPK, CreateReturnPolicyReasonForm form) {
        return CDI.current().select(CreateReturnPolicyReasonCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnPolicyReasons(UserVisitPK userVisitPK, GetReturnPolicyReasonsForm form) {
        return CDI.current().select(GetReturnPolicyReasonsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultReturnPolicyReason(UserVisitPK userVisitPK, SetDefaultReturnPolicyReasonForm form) {
        return CDI.current().select(SetDefaultReturnPolicyReasonCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editReturnPolicyReason(UserVisitPK userVisitPK, EditReturnPolicyReasonForm form) {
        return CDI.current().select(EditReturnPolicyReasonCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnPolicyReason(UserVisitPK userVisitPK, DeleteReturnPolicyReasonForm form) {
        return CDI.current().select(DeleteReturnPolicyReasonCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Reasons
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnReason(UserVisitPK userVisitPK, CreateReturnReasonForm form) {
        return CDI.current().select(CreateReturnReasonCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnReasons(UserVisitPK userVisitPK, GetReturnReasonsForm form) {
        return CDI.current().select(GetReturnReasonsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnReason(UserVisitPK userVisitPK, GetReturnReasonForm form) {
        return CDI.current().select(GetReturnReasonCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnReasonChoices(UserVisitPK userVisitPK, GetReturnReasonChoicesForm form) {
        return CDI.current().select(GetReturnReasonChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultReturnReason(UserVisitPK userVisitPK, SetDefaultReturnReasonForm form) {
        return CDI.current().select(SetDefaultReturnReasonCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editReturnReason(UserVisitPK userVisitPK, EditReturnReasonForm form) {
        return CDI.current().select(EditReturnReasonCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnReason(UserVisitPK userVisitPK, DeleteReturnReasonForm form) {
        return CDI.current().select(DeleteReturnReasonCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Reason Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnReasonDescription(UserVisitPK userVisitPK, CreateReturnReasonDescriptionForm form) {
        return CDI.current().select(CreateReturnReasonDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnReasonDescriptions(UserVisitPK userVisitPK, GetReturnReasonDescriptionsForm form) {
        return CDI.current().select(GetReturnReasonDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editReturnReasonDescription(UserVisitPK userVisitPK, EditReturnReasonDescriptionForm form) {
        return CDI.current().select(EditReturnReasonDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnReasonDescription(UserVisitPK userVisitPK, DeleteReturnReasonDescriptionForm form) {
        return CDI.current().select(DeleteReturnReasonDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Reason Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnReasonType(UserVisitPK userVisitPK, CreateReturnReasonTypeForm form) {
        return CDI.current().select(CreateReturnReasonTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnReasonTypes(UserVisitPK userVisitPK, GetReturnReasonTypesForm form) {
        return CDI.current().select(GetReturnReasonTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultReturnReasonType(UserVisitPK userVisitPK, SetDefaultReturnReasonTypeForm form) {
        return CDI.current().select(SetDefaultReturnReasonTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editReturnReasonType(UserVisitPK userVisitPK, EditReturnReasonTypeForm form) {
        return CDI.current().select(EditReturnReasonTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnReasonType(UserVisitPK userVisitPK, DeleteReturnReasonTypeForm form) {
        return CDI.current().select(DeleteReturnReasonTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnType(UserVisitPK userVisitPK, CreateReturnTypeForm form) {
        return CDI.current().select(CreateReturnTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnTypes(UserVisitPK userVisitPK, GetReturnTypesForm form) {
        return CDI.current().select(GetReturnTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnType(UserVisitPK userVisitPK, GetReturnTypeForm form) {
        return CDI.current().select(GetReturnTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnTypeChoices(UserVisitPK userVisitPK, GetReturnTypeChoicesForm form) {
        return CDI.current().select(GetReturnTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultReturnType(UserVisitPK userVisitPK, SetDefaultReturnTypeForm form) {
        return CDI.current().select(SetDefaultReturnTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editReturnType(UserVisitPK userVisitPK, EditReturnTypeForm form) {
        return CDI.current().select(EditReturnTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnType(UserVisitPK userVisitPK, DeleteReturnTypeForm form) {
        return CDI.current().select(DeleteReturnTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnTypeDescription(UserVisitPK userVisitPK, CreateReturnTypeDescriptionForm form) {
        return CDI.current().select(CreateReturnTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnTypeDescriptions(UserVisitPK userVisitPK, GetReturnTypeDescriptionsForm form) {
        return CDI.current().select(GetReturnTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getReturnTypeDescription(UserVisitPK userVisitPK, GetReturnTypeDescriptionForm form) {
        return CDI.current().select(GetReturnTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editReturnTypeDescription(UserVisitPK userVisitPK, EditReturnTypeDescriptionForm form) {
        return CDI.current().select(EditReturnTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnTypeDescription(UserVisitPK userVisitPK, DeleteReturnTypeDescriptionForm form) {
        return CDI.current().select(DeleteReturnTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Return Type Shipping Methods
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createReturnTypeShippingMethod(UserVisitPK userVisitPK, CreateReturnTypeShippingMethodForm form) {
        return CDI.current().select(CreateReturnTypeShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getReturnTypeShippingMethods(UserVisitPK userVisitPK, GetReturnTypeShippingMethodsForm form) {
        return CDI.current().select(GetReturnTypeShippingMethodsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultReturnTypeShippingMethod(UserVisitPK userVisitPK, SetDefaultReturnTypeShippingMethodForm form) {
        return CDI.current().select(SetDefaultReturnTypeShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editReturnTypeShippingMethod(UserVisitPK userVisitPK, EditReturnTypeShippingMethodForm form) {
        return CDI.current().select(EditReturnTypeShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteReturnTypeShippingMethod(UserVisitPK userVisitPK, DeleteReturnTypeShippingMethodForm form) {
        return CDI.current().select(DeleteReturnTypeShippingMethodCommand.class).get().run(userVisitPK, form);
    }
    
}
