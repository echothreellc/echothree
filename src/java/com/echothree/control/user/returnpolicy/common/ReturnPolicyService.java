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

package com.echothree.control.user.returnpolicy.common;

import com.echothree.control.user.returnpolicy.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface ReturnPolicyService
        extends ReturnPolicyForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Party Return Policies
    // -------------------------------------------------------------------------

    CommandResult getPartyReturnPolicy(UserVisitPK userVisitPK, GetPartyReturnPolicyForm form);

    CommandResult getPartyReturnPolicies(UserVisitPK userVisitPK, GetPartyReturnPoliciesForm form);

    CommandResult getPartyReturnPolicyStatusChoices(UserVisitPK userVisitPK, GetPartyReturnPolicyStatusChoicesForm form);

    CommandResult setPartyReturnPolicyStatus(UserVisitPK userVisitPK, SetPartyReturnPolicyStatusForm form);

    CommandResult deletePartyReturnPolicy(UserVisitPK userVisitPK, DeletePartyReturnPolicyForm form);

    // -------------------------------------------------------------------------
    //   Return Kinds
    // -------------------------------------------------------------------------
    
    CommandResult createReturnKind(UserVisitPK userVisitPK, CreateReturnKindForm form);
    
    CommandResult getReturnKinds(UserVisitPK userVisitPK, GetReturnKindsForm form);
    
    CommandResult getReturnKind(UserVisitPK userVisitPK, GetReturnKindForm form);
    
    CommandResult getReturnKindChoices(UserVisitPK userVisitPK, GetReturnKindChoicesForm form);
    
    CommandResult setDefaultReturnKind(UserVisitPK userVisitPK, SetDefaultReturnKindForm form);
    
    CommandResult editReturnKind(UserVisitPK userVisitPK, EditReturnKindForm form);
    
    CommandResult deleteReturnKind(UserVisitPK userVisitPK, DeleteReturnKindForm form);
    
    // -------------------------------------------------------------------------
    //   Return Kind Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createReturnKindDescription(UserVisitPK userVisitPK, CreateReturnKindDescriptionForm form);
    
    CommandResult getReturnKindDescriptions(UserVisitPK userVisitPK, GetReturnKindDescriptionsForm form);

    CommandResult getReturnKindDescription(UserVisitPK userVisitPK, GetReturnKindDescriptionForm form);

    CommandResult editReturnKindDescription(UserVisitPK userVisitPK, EditReturnKindDescriptionForm form);
    
    CommandResult deleteReturnKindDescription(UserVisitPK userVisitPK, DeleteReturnKindDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Return Policies
    // -------------------------------------------------------------------------
    
    CommandResult createReturnPolicy(UserVisitPK userVisitPK, CreateReturnPolicyForm form);
    
    CommandResult getReturnPolicies(UserVisitPK userVisitPK, GetReturnPoliciesForm form);
    
    CommandResult getReturnPolicy(UserVisitPK userVisitPK, GetReturnPolicyForm form);
    
    CommandResult getReturnPolicyChoices(UserVisitPK userVisitPK, GetReturnPolicyChoicesForm form);
    
    CommandResult setDefaultReturnPolicy(UserVisitPK userVisitPK, SetDefaultReturnPolicyForm form);
    
    CommandResult editReturnPolicy(UserVisitPK userVisitPK, EditReturnPolicyForm form);
    
    CommandResult deleteReturnPolicy(UserVisitPK userVisitPK, DeleteReturnPolicyForm form);
    
    // -------------------------------------------------------------------------
    //   Return Policy Translations
    // -------------------------------------------------------------------------
    
    CommandResult createReturnPolicyTranslation(UserVisitPK userVisitPK, CreateReturnPolicyTranslationForm form);
    
    CommandResult getReturnPolicyTranslation(UserVisitPK userVisitPK, GetReturnPolicyTranslationForm form);

    CommandResult getReturnPolicyTranslations(UserVisitPK userVisitPK, GetReturnPolicyTranslationsForm form);

    CommandResult editReturnPolicyTranslation(UserVisitPK userVisitPK, EditReturnPolicyTranslationForm form);
    
    CommandResult deleteReturnPolicyTranslation(UserVisitPK userVisitPK, DeleteReturnPolicyTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Return Policy Reasons
    // -------------------------------------------------------------------------
    
    CommandResult createReturnPolicyReason(UserVisitPK userVisitPK, CreateReturnPolicyReasonForm form);
    
    CommandResult getReturnPolicyReasons(UserVisitPK userVisitPK, GetReturnPolicyReasonsForm form);
    
    CommandResult setDefaultReturnPolicyReason(UserVisitPK userVisitPK, SetDefaultReturnPolicyReasonForm form);
    
    CommandResult editReturnPolicyReason(UserVisitPK userVisitPK, EditReturnPolicyReasonForm form);
    
    CommandResult deleteReturnPolicyReason(UserVisitPK userVisitPK, DeleteReturnPolicyReasonForm form);
    
    // -------------------------------------------------------------------------
    //   Return Reasons
    // -------------------------------------------------------------------------
    
    CommandResult createReturnReason(UserVisitPK userVisitPK, CreateReturnReasonForm form);
    
    CommandResult getReturnReasons(UserVisitPK userVisitPK, GetReturnReasonsForm form);
    
    CommandResult getReturnReason(UserVisitPK userVisitPK, GetReturnReasonForm form);
    
    CommandResult getReturnReasonChoices(UserVisitPK userVisitPK, GetReturnReasonChoicesForm form);
    
    CommandResult setDefaultReturnReason(UserVisitPK userVisitPK, SetDefaultReturnReasonForm form);
    
    CommandResult editReturnReason(UserVisitPK userVisitPK, EditReturnReasonForm form);
    
    CommandResult deleteReturnReason(UserVisitPK userVisitPK, DeleteReturnReasonForm form);
    
    // -------------------------------------------------------------------------
    //   Return Reason Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createReturnReasonDescription(UserVisitPK userVisitPK, CreateReturnReasonDescriptionForm form);
    
    CommandResult getReturnReasonDescriptions(UserVisitPK userVisitPK, GetReturnReasonDescriptionsForm form);
    
    CommandResult editReturnReasonDescription(UserVisitPK userVisitPK, EditReturnReasonDescriptionForm form);
    
    CommandResult deleteReturnReasonDescription(UserVisitPK userVisitPK, DeleteReturnReasonDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Return Reason Types
    // -------------------------------------------------------------------------
    
    CommandResult createReturnReasonType(UserVisitPK userVisitPK, CreateReturnReasonTypeForm form);
    
    CommandResult getReturnReasonTypes(UserVisitPK userVisitPK, GetReturnReasonTypesForm form);
    
    CommandResult setDefaultReturnReasonType(UserVisitPK userVisitPK, SetDefaultReturnReasonTypeForm form);
    
    CommandResult editReturnReasonType(UserVisitPK userVisitPK, EditReturnReasonTypeForm form);
    
    CommandResult deleteReturnReasonType(UserVisitPK userVisitPK, DeleteReturnReasonTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Return Types
    // -------------------------------------------------------------------------
    
    CommandResult createReturnType(UserVisitPK userVisitPK, CreateReturnTypeForm form);
    
    CommandResult getReturnTypes(UserVisitPK userVisitPK, GetReturnTypesForm form);
    
    CommandResult getReturnType(UserVisitPK userVisitPK, GetReturnTypeForm form);
    
    CommandResult getReturnTypeChoices(UserVisitPK userVisitPK, GetReturnTypeChoicesForm form);
    
    CommandResult setDefaultReturnType(UserVisitPK userVisitPK, SetDefaultReturnTypeForm form);
    
    CommandResult editReturnType(UserVisitPK userVisitPK, EditReturnTypeForm form);
    
    CommandResult deleteReturnType(UserVisitPK userVisitPK, DeleteReturnTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Return Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createReturnTypeDescription(UserVisitPK userVisitPK, CreateReturnTypeDescriptionForm form);
    
    CommandResult getReturnTypeDescriptions(UserVisitPK userVisitPK, GetReturnTypeDescriptionsForm form);

    CommandResult getReturnTypeDescription(UserVisitPK userVisitPK, GetReturnTypeDescriptionForm form);

    CommandResult editReturnTypeDescription(UserVisitPK userVisitPK, EditReturnTypeDescriptionForm form);
    
    CommandResult deleteReturnTypeDescription(UserVisitPK userVisitPK, DeleteReturnTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Return Type Shipping Methods
    // -------------------------------------------------------------------------
    
    CommandResult createReturnTypeShippingMethod(UserVisitPK userVisitPK, CreateReturnTypeShippingMethodForm form);
    
    CommandResult getReturnTypeShippingMethods(UserVisitPK userVisitPK, GetReturnTypeShippingMethodsForm form);
    
    CommandResult setDefaultReturnTypeShippingMethod(UserVisitPK userVisitPK, SetDefaultReturnTypeShippingMethodForm form);
    
    CommandResult editReturnTypeShippingMethod(UserVisitPK userVisitPK, EditReturnTypeShippingMethodForm form);
    
    CommandResult deleteReturnTypeShippingMethod(UserVisitPK userVisitPK, DeleteReturnTypeShippingMethodForm form);
    
}
