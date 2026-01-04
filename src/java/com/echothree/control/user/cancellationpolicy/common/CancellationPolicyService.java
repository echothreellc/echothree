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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface CancellationPolicyService
        extends CancellationPolicyForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Party Cancellation Policies
    // -------------------------------------------------------------------------
    
    CommandResult getPartyCancellationPolicy(UserVisitPK userVisitPK, GetPartyCancellationPolicyForm form);

    CommandResult getPartyCancellationPolicies(UserVisitPK userVisitPK, GetPartyCancellationPoliciesForm form);

    CommandResult getPartyCancellationPolicyStatusChoices(UserVisitPK userVisitPK, GetPartyCancellationPolicyStatusChoicesForm form);

    CommandResult setPartyCancellationPolicyStatus(UserVisitPK userVisitPK, SetPartyCancellationPolicyStatusForm form);

    CommandResult deletePartyCancellationPolicy(UserVisitPK userVisitPK, DeletePartyCancellationPolicyForm form);

    // -------------------------------------------------------------------------
    //   Cancellation Kinds
    // -------------------------------------------------------------------------
    
    CommandResult createCancellationKind(UserVisitPK userVisitPK, CreateCancellationKindForm form);
    
    CommandResult getCancellationKinds(UserVisitPK userVisitPK, GetCancellationKindsForm form);
    
    CommandResult getCancellationKind(UserVisitPK userVisitPK, GetCancellationKindForm form);
    
    CommandResult getCancellationKindChoices(UserVisitPK userVisitPK, GetCancellationKindChoicesForm form);
    
    CommandResult setDefaultCancellationKind(UserVisitPK userVisitPK, SetDefaultCancellationKindForm form);
    
    CommandResult editCancellationKind(UserVisitPK userVisitPK, EditCancellationKindForm form);
    
    CommandResult deleteCancellationKind(UserVisitPK userVisitPK, DeleteCancellationKindForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Kind Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createCancellationKindDescription(UserVisitPK userVisitPK, CreateCancellationKindDescriptionForm form);
    
    CommandResult getCancellationKindDescriptions(UserVisitPK userVisitPK, GetCancellationKindDescriptionsForm form);
    
    CommandResult editCancellationKindDescription(UserVisitPK userVisitPK, EditCancellationKindDescriptionForm form);
    
    CommandResult deleteCancellationKindDescription(UserVisitPK userVisitPK, DeleteCancellationKindDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Policies
    // -------------------------------------------------------------------------
    
    CommandResult createCancellationPolicy(UserVisitPK userVisitPK, CreateCancellationPolicyForm form);
    
    CommandResult getCancellationPolicies(UserVisitPK userVisitPK, GetCancellationPoliciesForm form);
    
    CommandResult getCancellationPolicy(UserVisitPK userVisitPK, GetCancellationPolicyForm form);
    
    CommandResult getCancellationPolicyChoices(UserVisitPK userVisitPK, GetCancellationPolicyChoicesForm form);
    
    CommandResult setDefaultCancellationPolicy(UserVisitPK userVisitPK, SetDefaultCancellationPolicyForm form);
    
    CommandResult editCancellationPolicy(UserVisitPK userVisitPK, EditCancellationPolicyForm form);
    
    CommandResult deleteCancellationPolicy(UserVisitPK userVisitPK, DeleteCancellationPolicyForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Policy Translations
    // -------------------------------------------------------------------------
    
    CommandResult createCancellationPolicyTranslation(UserVisitPK userVisitPK, CreateCancellationPolicyTranslationForm form);

    CommandResult getCancellationPolicyTranslation(UserVisitPK userVisitPK, GetCancellationPolicyTranslationForm form);

    CommandResult getCancellationPolicyTranslations(UserVisitPK userVisitPK, GetCancellationPolicyTranslationsForm form);
    
    CommandResult editCancellationPolicyTranslation(UserVisitPK userVisitPK, EditCancellationPolicyTranslationForm form);
    
    CommandResult deleteCancellationPolicyTranslation(UserVisitPK userVisitPK, DeleteCancellationPolicyTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Policy Reasons
    // -------------------------------------------------------------------------
    
    CommandResult createCancellationPolicyReason(UserVisitPK userVisitPK, CreateCancellationPolicyReasonForm form);
    
    CommandResult getCancellationPolicyReasons(UserVisitPK userVisitPK, GetCancellationPolicyReasonsForm form);
    
    CommandResult setDefaultCancellationPolicyReason(UserVisitPK userVisitPK, SetDefaultCancellationPolicyReasonForm form);
    
    CommandResult editCancellationPolicyReason(UserVisitPK userVisitPK, EditCancellationPolicyReasonForm form);
    
    CommandResult deleteCancellationPolicyReason(UserVisitPK userVisitPK, DeleteCancellationPolicyReasonForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Reasons
    // -------------------------------------------------------------------------
    
    CommandResult createCancellationReason(UserVisitPK userVisitPK, CreateCancellationReasonForm form);
    
    CommandResult getCancellationReasons(UserVisitPK userVisitPK, GetCancellationReasonsForm form);
    
    CommandResult getCancellationReason(UserVisitPK userVisitPK, GetCancellationReasonForm form);
    
    CommandResult getCancellationReasonChoices(UserVisitPK userVisitPK, GetCancellationReasonChoicesForm form);
    
    CommandResult setDefaultCancellationReason(UserVisitPK userVisitPK, SetDefaultCancellationReasonForm form);
    
    CommandResult editCancellationReason(UserVisitPK userVisitPK, EditCancellationReasonForm form);
    
    CommandResult deleteCancellationReason(UserVisitPK userVisitPK, DeleteCancellationReasonForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Reason Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createCancellationReasonDescription(UserVisitPK userVisitPK, CreateCancellationReasonDescriptionForm form);
    
    CommandResult getCancellationReasonDescriptions(UserVisitPK userVisitPK, GetCancellationReasonDescriptionsForm form);
    
    CommandResult editCancellationReasonDescription(UserVisitPK userVisitPK, EditCancellationReasonDescriptionForm form);
    
    CommandResult deleteCancellationReasonDescription(UserVisitPK userVisitPK, DeleteCancellationReasonDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Reason Types
    // -------------------------------------------------------------------------
    
    CommandResult createCancellationReasonType(UserVisitPK userVisitPK, CreateCancellationReasonTypeForm form);
    
    CommandResult getCancellationReasonTypes(UserVisitPK userVisitPK, GetCancellationReasonTypesForm form);
    
    CommandResult setDefaultCancellationReasonType(UserVisitPK userVisitPK, SetDefaultCancellationReasonTypeForm form);
    
    CommandResult editCancellationReasonType(UserVisitPK userVisitPK, EditCancellationReasonTypeForm form);
    
    CommandResult deleteCancellationReasonType(UserVisitPK userVisitPK, DeleteCancellationReasonTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Types
    // -------------------------------------------------------------------------
    
    CommandResult createCancellationType(UserVisitPK userVisitPK, CreateCancellationTypeForm form);
    
    CommandResult getCancellationTypes(UserVisitPK userVisitPK, GetCancellationTypesForm form);
    
    CommandResult getCancellationType(UserVisitPK userVisitPK, GetCancellationTypeForm form);
    
    CommandResult getCancellationTypeChoices(UserVisitPK userVisitPK, GetCancellationTypeChoicesForm form);
    
    CommandResult setDefaultCancellationType(UserVisitPK userVisitPK, SetDefaultCancellationTypeForm form);
    
    CommandResult editCancellationType(UserVisitPK userVisitPK, EditCancellationTypeForm form);
    
    CommandResult deleteCancellationType(UserVisitPK userVisitPK, DeleteCancellationTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Cancellation Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createCancellationTypeDescription(UserVisitPK userVisitPK, CreateCancellationTypeDescriptionForm form);
    
    CommandResult getCancellationTypeDescriptions(UserVisitPK userVisitPK, GetCancellationTypeDescriptionsForm form);
    
    CommandResult editCancellationTypeDescription(UserVisitPK userVisitPK, EditCancellationTypeDescriptionForm form);
    
    CommandResult deleteCancellationTypeDescription(UserVisitPK userVisitPK, DeleteCancellationTypeDescriptionForm form);
    
}
