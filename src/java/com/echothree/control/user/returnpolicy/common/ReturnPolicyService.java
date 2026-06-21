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
import com.echothree.control.user.returnpolicy.common.result.*;
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

    CommandResult<GetPartyReturnPolicyResult> getPartyReturnPolicy(UserVisitPK userVisitPK, GetPartyReturnPolicyForm form);

    CommandResult<GetPartyReturnPoliciesResult> getPartyReturnPolicies(UserVisitPK userVisitPK, GetPartyReturnPoliciesForm form);

    CommandResult<GetPartyReturnPolicyStatusChoicesResult> getPartyReturnPolicyStatusChoices(UserVisitPK userVisitPK, GetPartyReturnPolicyStatusChoicesForm form);

    CommandResult<?> setPartyReturnPolicyStatus(UserVisitPK userVisitPK, SetPartyReturnPolicyStatusForm form);

    CommandResult<?> deletePartyReturnPolicy(UserVisitPK userVisitPK, DeletePartyReturnPolicyForm form);

    // -------------------------------------------------------------------------
    //   Return Kinds
    // -------------------------------------------------------------------------
    
    CommandResult<?> createReturnKind(UserVisitPK userVisitPK, CreateReturnKindForm form);
    
    CommandResult<GetReturnKindsResult> getReturnKinds(UserVisitPK userVisitPK, GetReturnKindsForm form);
    
    CommandResult<GetReturnKindResult> getReturnKind(UserVisitPK userVisitPK, GetReturnKindForm form);
    
    CommandResult<GetReturnKindChoicesResult> getReturnKindChoices(UserVisitPK userVisitPK, GetReturnKindChoicesForm form);
    
    CommandResult<?> setDefaultReturnKind(UserVisitPK userVisitPK, SetDefaultReturnKindForm form);
    
    CommandResult<EditReturnKindResult> editReturnKind(UserVisitPK userVisitPK, EditReturnKindForm form);
    
    CommandResult<?> deleteReturnKind(UserVisitPK userVisitPK, DeleteReturnKindForm form);
    
    // -------------------------------------------------------------------------
    //   Return Kind Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createReturnKindDescription(UserVisitPK userVisitPK, CreateReturnKindDescriptionForm form);
    
    CommandResult<GetReturnKindDescriptionsResult> getReturnKindDescriptions(UserVisitPK userVisitPK, GetReturnKindDescriptionsForm form);

    CommandResult<GetReturnKindDescriptionResult> getReturnKindDescription(UserVisitPK userVisitPK, GetReturnKindDescriptionForm form);

    CommandResult<EditReturnKindDescriptionResult> editReturnKindDescription(UserVisitPK userVisitPK, EditReturnKindDescriptionForm form);
    
    CommandResult<?> deleteReturnKindDescription(UserVisitPK userVisitPK, DeleteReturnKindDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Return Policies
    // -------------------------------------------------------------------------
    
    CommandResult<?> createReturnPolicy(UserVisitPK userVisitPK, CreateReturnPolicyForm form);
    
    CommandResult<GetReturnPoliciesResult> getReturnPolicies(UserVisitPK userVisitPK, GetReturnPoliciesForm form);
    
    CommandResult<GetReturnPolicyResult> getReturnPolicy(UserVisitPK userVisitPK, GetReturnPolicyForm form);
    
    CommandResult<GetReturnPolicyChoicesResult> getReturnPolicyChoices(UserVisitPK userVisitPK, GetReturnPolicyChoicesForm form);
    
    CommandResult<?> setDefaultReturnPolicy(UserVisitPK userVisitPK, SetDefaultReturnPolicyForm form);
    
    CommandResult<EditReturnPolicyResult> editReturnPolicy(UserVisitPK userVisitPK, EditReturnPolicyForm form);
    
    CommandResult<?> deleteReturnPolicy(UserVisitPK userVisitPK, DeleteReturnPolicyForm form);
    
    // -------------------------------------------------------------------------
    //   Return Policy Translations
    // -------------------------------------------------------------------------
    
    CommandResult<?> createReturnPolicyTranslation(UserVisitPK userVisitPK, CreateReturnPolicyTranslationForm form);
    
    CommandResult<GetReturnPolicyTranslationResult> getReturnPolicyTranslation(UserVisitPK userVisitPK, GetReturnPolicyTranslationForm form);

    CommandResult<GetReturnPolicyTranslationsResult> getReturnPolicyTranslations(UserVisitPK userVisitPK, GetReturnPolicyTranslationsForm form);

    CommandResult<EditReturnPolicyTranslationResult> editReturnPolicyTranslation(UserVisitPK userVisitPK, EditReturnPolicyTranslationForm form);
    
    CommandResult<?> deleteReturnPolicyTranslation(UserVisitPK userVisitPK, DeleteReturnPolicyTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Return Policy Reasons
    // -------------------------------------------------------------------------
    
    CommandResult<?> createReturnPolicyReason(UserVisitPK userVisitPK, CreateReturnPolicyReasonForm form);
    
    CommandResult<GetReturnPolicyReasonsResult> getReturnPolicyReasons(UserVisitPK userVisitPK, GetReturnPolicyReasonsForm form);
    
    CommandResult<?> setDefaultReturnPolicyReason(UserVisitPK userVisitPK, SetDefaultReturnPolicyReasonForm form);
    
    CommandResult<EditReturnPolicyReasonResult> editReturnPolicyReason(UserVisitPK userVisitPK, EditReturnPolicyReasonForm form);
    
    CommandResult<?> deleteReturnPolicyReason(UserVisitPK userVisitPK, DeleteReturnPolicyReasonForm form);
    
    // -------------------------------------------------------------------------
    //   Return Reasons
    // -------------------------------------------------------------------------
    
    CommandResult<?> createReturnReason(UserVisitPK userVisitPK, CreateReturnReasonForm form);
    
    CommandResult<GetReturnReasonsResult> getReturnReasons(UserVisitPK userVisitPK, GetReturnReasonsForm form);
    
    CommandResult<GetReturnReasonResult> getReturnReason(UserVisitPK userVisitPK, GetReturnReasonForm form);
    
    CommandResult<GetReturnReasonChoicesResult> getReturnReasonChoices(UserVisitPK userVisitPK, GetReturnReasonChoicesForm form);
    
    CommandResult<?> setDefaultReturnReason(UserVisitPK userVisitPK, SetDefaultReturnReasonForm form);
    
    CommandResult<EditReturnReasonResult> editReturnReason(UserVisitPK userVisitPK, EditReturnReasonForm form);
    
    CommandResult<?> deleteReturnReason(UserVisitPK userVisitPK, DeleteReturnReasonForm form);
    
    // -------------------------------------------------------------------------
    //   Return Reason Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createReturnReasonDescription(UserVisitPK userVisitPK, CreateReturnReasonDescriptionForm form);
    
    CommandResult<GetReturnReasonDescriptionsResult> getReturnReasonDescriptions(UserVisitPK userVisitPK, GetReturnReasonDescriptionsForm form);
    
    CommandResult<EditReturnReasonDescriptionResult> editReturnReasonDescription(UserVisitPK userVisitPK, EditReturnReasonDescriptionForm form);
    
    CommandResult<?> deleteReturnReasonDescription(UserVisitPK userVisitPK, DeleteReturnReasonDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Return Reason Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createReturnReasonType(UserVisitPK userVisitPK, CreateReturnReasonTypeForm form);
    
    CommandResult<GetReturnReasonTypesResult> getReturnReasonTypes(UserVisitPK userVisitPK, GetReturnReasonTypesForm form);
    
    CommandResult<?> setDefaultReturnReasonType(UserVisitPK userVisitPK, SetDefaultReturnReasonTypeForm form);
    
    CommandResult<EditReturnReasonTypeResult> editReturnReasonType(UserVisitPK userVisitPK, EditReturnReasonTypeForm form);
    
    CommandResult<?> deleteReturnReasonType(UserVisitPK userVisitPK, DeleteReturnReasonTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Return Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createReturnType(UserVisitPK userVisitPK, CreateReturnTypeForm form);
    
    CommandResult<GetReturnTypesResult> getReturnTypes(UserVisitPK userVisitPK, GetReturnTypesForm form);
    
    CommandResult<GetReturnTypeResult> getReturnType(UserVisitPK userVisitPK, GetReturnTypeForm form);
    
    CommandResult<GetReturnTypeChoicesResult> getReturnTypeChoices(UserVisitPK userVisitPK, GetReturnTypeChoicesForm form);
    
    CommandResult<?> setDefaultReturnType(UserVisitPK userVisitPK, SetDefaultReturnTypeForm form);
    
    CommandResult<EditReturnTypeResult> editReturnType(UserVisitPK userVisitPK, EditReturnTypeForm form);
    
    CommandResult<?> deleteReturnType(UserVisitPK userVisitPK, DeleteReturnTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Return Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createReturnTypeDescription(UserVisitPK userVisitPK, CreateReturnTypeDescriptionForm form);
    
    CommandResult<GetReturnTypeDescriptionsResult> getReturnTypeDescriptions(UserVisitPK userVisitPK, GetReturnTypeDescriptionsForm form);

    CommandResult<GetReturnTypeDescriptionResult> getReturnTypeDescription(UserVisitPK userVisitPK, GetReturnTypeDescriptionForm form);

    CommandResult<EditReturnTypeDescriptionResult> editReturnTypeDescription(UserVisitPK userVisitPK, EditReturnTypeDescriptionForm form);
    
    CommandResult<?> deleteReturnTypeDescription(UserVisitPK userVisitPK, DeleteReturnTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Return Type Shipping Methods
    // -------------------------------------------------------------------------
    
    CommandResult<?> createReturnTypeShippingMethod(UserVisitPK userVisitPK, CreateReturnTypeShippingMethodForm form);
    
    CommandResult<GetReturnTypeShippingMethodsResult> getReturnTypeShippingMethods(UserVisitPK userVisitPK, GetReturnTypeShippingMethodsForm form);
    
    CommandResult<?> setDefaultReturnTypeShippingMethod(UserVisitPK userVisitPK, SetDefaultReturnTypeShippingMethodForm form);
    
    CommandResult<EditReturnTypeShippingMethodResult> editReturnTypeShippingMethod(UserVisitPK userVisitPK, EditReturnTypeShippingMethodForm form);
    
    CommandResult<?> deleteReturnTypeShippingMethod(UserVisitPK userVisitPK, DeleteReturnTypeShippingMethodForm form);
    
}
