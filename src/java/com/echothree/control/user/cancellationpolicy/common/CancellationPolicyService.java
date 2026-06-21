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

package com.echothree.control.user.cancellationpolicy.common;

import com.echothree.control.user.cancellationpolicy.common.form.*;
import com.echothree.control.user.cancellationpolicy.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface CancellationPolicyService
        extends CancellationPolicyForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Party Cancellation Policies
    // -------------------------------------------------------------------------
    
    CommandResult<GetPartyCancellationPolicyResult> getPartyCancellationPolicy(UserVisitPK userVisitPK, GetPartyCancellationPolicyForm form);

    CommandResult<GetPartyCancellationPoliciesResult> getPartyCancellationPolicies(UserVisitPK userVisitPK, GetPartyCancellationPoliciesForm form);

    CommandResult<GetPartyCancellationPolicyStatusChoicesResult> getPartyCancellationPolicyStatusChoices(UserVisitPK userVisitPK, GetPartyCancellationPolicyStatusChoicesForm form);

    CommandResult<VoidResult> setPartyCancellationPolicyStatus(UserVisitPK userVisitPK, SetPartyCancellationPolicyStatusForm form);

    CommandResult<VoidResult> deletePartyCancellationPolicy(UserVisitPK userVisitPK, DeletePartyCancellationPolicyForm form);

    // -------------------------------------------------------------------------
    //   Cancellation Kinds
    // -------------------------------------------------------------------------
    
    CommandResult<CreateCancellationKindResult> createCancellationKind(UserVisitPK userVisitPK, CreateCancellationKindForm form);
    
    CommandResult<GetCancellationKindsResult> getCancellationKinds(UserVisitPK userVisitPK, GetCancellationKindsForm form);
    
    CommandResult<GetCancellationKindResult> getCancellationKind(UserVisitPK userVisitPK, GetCancellationKindForm form);
    
    CommandResult<GetCancellationKindChoicesResult> getCancellationKindChoices(UserVisitPK userVisitPK, GetCancellationKindChoicesForm form);
    
    CommandResult<VoidResult> setDefaultCancellationKind(UserVisitPK userVisitPK, SetDefaultCancellationKindForm form);
    
    CommandResult<EditCancellationKindResult> editCancellationKind(UserVisitPK userVisitPK, EditCancellationKindForm form);
    
    CommandResult<VoidResult> deleteCancellationKind(UserVisitPK userVisitPK, DeleteCancellationKindForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Kind Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCancellationKindDescription(UserVisitPK userVisitPK, CreateCancellationKindDescriptionForm form);
    
    CommandResult<GetCancellationKindDescriptionsResult> getCancellationKindDescriptions(UserVisitPK userVisitPK, GetCancellationKindDescriptionsForm form);
    
    CommandResult<EditCancellationKindDescriptionResult> editCancellationKindDescription(UserVisitPK userVisitPK, EditCancellationKindDescriptionForm form);
    
    CommandResult<VoidResult> deleteCancellationKindDescription(UserVisitPK userVisitPK, DeleteCancellationKindDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Policies
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCancellationPolicy(UserVisitPK userVisitPK, CreateCancellationPolicyForm form);
    
    CommandResult<GetCancellationPoliciesResult> getCancellationPolicies(UserVisitPK userVisitPK, GetCancellationPoliciesForm form);
    
    CommandResult<GetCancellationPolicyResult> getCancellationPolicy(UserVisitPK userVisitPK, GetCancellationPolicyForm form);
    
    CommandResult<GetCancellationPolicyChoicesResult> getCancellationPolicyChoices(UserVisitPK userVisitPK, GetCancellationPolicyChoicesForm form);
    
    CommandResult<VoidResult> setDefaultCancellationPolicy(UserVisitPK userVisitPK, SetDefaultCancellationPolicyForm form);
    
    CommandResult<EditCancellationPolicyResult> editCancellationPolicy(UserVisitPK userVisitPK, EditCancellationPolicyForm form);
    
    CommandResult<VoidResult> deleteCancellationPolicy(UserVisitPK userVisitPK, DeleteCancellationPolicyForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Policy Translations
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCancellationPolicyTranslation(UserVisitPK userVisitPK, CreateCancellationPolicyTranslationForm form);

    CommandResult<GetCancellationPolicyTranslationResult> getCancellationPolicyTranslation(UserVisitPK userVisitPK, GetCancellationPolicyTranslationForm form);

    CommandResult<GetCancellationPolicyTranslationsResult> getCancellationPolicyTranslations(UserVisitPK userVisitPK, GetCancellationPolicyTranslationsForm form);
    
    CommandResult<EditCancellationPolicyTranslationResult> editCancellationPolicyTranslation(UserVisitPK userVisitPK, EditCancellationPolicyTranslationForm form);
    
    CommandResult<VoidResult> deleteCancellationPolicyTranslation(UserVisitPK userVisitPK, DeleteCancellationPolicyTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Policy Reasons
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCancellationPolicyReason(UserVisitPK userVisitPK, CreateCancellationPolicyReasonForm form);
    
    CommandResult<GetCancellationPolicyReasonsResult> getCancellationPolicyReasons(UserVisitPK userVisitPK, GetCancellationPolicyReasonsForm form);
    
    CommandResult<VoidResult> setDefaultCancellationPolicyReason(UserVisitPK userVisitPK, SetDefaultCancellationPolicyReasonForm form);
    
    CommandResult<EditCancellationPolicyReasonResult> editCancellationPolicyReason(UserVisitPK userVisitPK, EditCancellationPolicyReasonForm form);
    
    CommandResult<VoidResult> deleteCancellationPolicyReason(UserVisitPK userVisitPK, DeleteCancellationPolicyReasonForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Reasons
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCancellationReason(UserVisitPK userVisitPK, CreateCancellationReasonForm form);
    
    CommandResult<GetCancellationReasonsResult> getCancellationReasons(UserVisitPK userVisitPK, GetCancellationReasonsForm form);
    
    CommandResult<GetCancellationReasonResult> getCancellationReason(UserVisitPK userVisitPK, GetCancellationReasonForm form);
    
    CommandResult<GetCancellationReasonChoicesResult> getCancellationReasonChoices(UserVisitPK userVisitPK, GetCancellationReasonChoicesForm form);
    
    CommandResult<VoidResult> setDefaultCancellationReason(UserVisitPK userVisitPK, SetDefaultCancellationReasonForm form);
    
    CommandResult<EditCancellationReasonResult> editCancellationReason(UserVisitPK userVisitPK, EditCancellationReasonForm form);
    
    CommandResult<VoidResult> deleteCancellationReason(UserVisitPK userVisitPK, DeleteCancellationReasonForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Reason Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCancellationReasonDescription(UserVisitPK userVisitPK, CreateCancellationReasonDescriptionForm form);
    
    CommandResult<GetCancellationReasonDescriptionsResult> getCancellationReasonDescriptions(UserVisitPK userVisitPK, GetCancellationReasonDescriptionsForm form);
    
    CommandResult<EditCancellationReasonDescriptionResult> editCancellationReasonDescription(UserVisitPK userVisitPK, EditCancellationReasonDescriptionForm form);
    
    CommandResult<VoidResult> deleteCancellationReasonDescription(UserVisitPK userVisitPK, DeleteCancellationReasonDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Reason Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCancellationReasonType(UserVisitPK userVisitPK, CreateCancellationReasonTypeForm form);
    
    CommandResult<GetCancellationReasonTypesResult> getCancellationReasonTypes(UserVisitPK userVisitPK, GetCancellationReasonTypesForm form);
    
    CommandResult<VoidResult> setDefaultCancellationReasonType(UserVisitPK userVisitPK, SetDefaultCancellationReasonTypeForm form);
    
    CommandResult<EditCancellationReasonTypeResult> editCancellationReasonType(UserVisitPK userVisitPK, EditCancellationReasonTypeForm form);
    
    CommandResult<VoidResult> deleteCancellationReasonType(UserVisitPK userVisitPK, DeleteCancellationReasonTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCancellationType(UserVisitPK userVisitPK, CreateCancellationTypeForm form);
    
    CommandResult<GetCancellationTypesResult> getCancellationTypes(UserVisitPK userVisitPK, GetCancellationTypesForm form);
    
    CommandResult<GetCancellationTypeResult> getCancellationType(UserVisitPK userVisitPK, GetCancellationTypeForm form);
    
    CommandResult<GetCancellationTypeChoicesResult> getCancellationTypeChoices(UserVisitPK userVisitPK, GetCancellationTypeChoicesForm form);
    
    CommandResult<VoidResult> setDefaultCancellationType(UserVisitPK userVisitPK, SetDefaultCancellationTypeForm form);
    
    CommandResult<EditCancellationTypeResult> editCancellationType(UserVisitPK userVisitPK, EditCancellationTypeForm form);
    
    CommandResult<VoidResult> deleteCancellationType(UserVisitPK userVisitPK, DeleteCancellationTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createCancellationTypeDescription(UserVisitPK userVisitPK, CreateCancellationTypeDescriptionForm form);
    
    CommandResult<GetCancellationTypeDescriptionsResult> getCancellationTypeDescriptions(UserVisitPK userVisitPK, GetCancellationTypeDescriptionsForm form);
    
    CommandResult<EditCancellationTypeDescriptionResult> editCancellationTypeDescription(UserVisitPK userVisitPK, EditCancellationTypeDescriptionForm form);
    
    CommandResult<VoidResult> deleteCancellationTypeDescription(UserVisitPK userVisitPK, DeleteCancellationTypeDescriptionForm form);
    
}
