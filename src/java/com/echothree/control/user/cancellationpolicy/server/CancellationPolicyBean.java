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

package com.echothree.control.user.cancellationpolicy.server;

import com.echothree.control.user.cancellationpolicy.common.CancellationPolicyRemote;
import com.echothree.control.user.cancellationpolicy.common.form.*;
import com.echothree.control.user.cancellationpolicy.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class CancellationPolicyBean
        extends CancellationPolicyFormsImpl
        implements CancellationPolicyRemote, CancellationPolicyLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "CancellationPolicyBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Party Cancellation Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getPartyCancellationPolicy(UserVisitPK userVisitPK, GetPartyCancellationPolicyForm form) {
        return new GetPartyCancellationPolicyCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyCancellationPolicies(UserVisitPK userVisitPK, GetPartyCancellationPoliciesForm form) {
        return new GetPartyCancellationPoliciesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyCancellationPolicyStatusChoices(UserVisitPK userVisitPK, GetPartyCancellationPolicyStatusChoicesForm form) {
        return new GetPartyCancellationPolicyStatusChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setPartyCancellationPolicyStatus(UserVisitPK userVisitPK, SetPartyCancellationPolicyStatusForm form) {
        return new SetPartyCancellationPolicyStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyCancellationPolicy(UserVisitPK userVisitPK, DeletePartyCancellationPolicyForm form) {
        return new DeletePartyCancellationPolicyCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Cancellation Kinds
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationKind(UserVisitPK userVisitPK, CreateCancellationKindForm form) {
        return new CreateCancellationKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationKinds(UserVisitPK userVisitPK, GetCancellationKindsForm form) {
        return new GetCancellationKindsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationKind(UserVisitPK userVisitPK, GetCancellationKindForm form) {
        return new GetCancellationKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationKindChoices(UserVisitPK userVisitPK, GetCancellationKindChoicesForm form) {
        return new GetCancellationKindChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCancellationKind(UserVisitPK userVisitPK, SetDefaultCancellationKindForm form) {
        return new SetDefaultCancellationKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationKind(UserVisitPK userVisitPK, EditCancellationKindForm form) {
        return new EditCancellationKindCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationKind(UserVisitPK userVisitPK, DeleteCancellationKindForm form) {
        return new DeleteCancellationKindCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Kind Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationKindDescription(UserVisitPK userVisitPK, CreateCancellationKindDescriptionForm form) {
        return new CreateCancellationKindDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationKindDescriptions(UserVisitPK userVisitPK, GetCancellationKindDescriptionsForm form) {
        return new GetCancellationKindDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationKindDescription(UserVisitPK userVisitPK, EditCancellationKindDescriptionForm form) {
        return new EditCancellationKindDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationKindDescription(UserVisitPK userVisitPK, DeleteCancellationKindDescriptionForm form) {
        return new DeleteCancellationKindDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationPolicy(UserVisitPK userVisitPK, CreateCancellationPolicyForm form) {
        return new CreateCancellationPolicyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationPolicies(UserVisitPK userVisitPK, GetCancellationPoliciesForm form) {
        return new GetCancellationPoliciesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationPolicy(UserVisitPK userVisitPK, GetCancellationPolicyForm form) {
        return new GetCancellationPolicyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationPolicyChoices(UserVisitPK userVisitPK, GetCancellationPolicyChoicesForm form) {
        return new GetCancellationPolicyChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCancellationPolicy(UserVisitPK userVisitPK, SetDefaultCancellationPolicyForm form) {
        return new SetDefaultCancellationPolicyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationPolicy(UserVisitPK userVisitPK, EditCancellationPolicyForm form) {
        return new EditCancellationPolicyCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationPolicy(UserVisitPK userVisitPK, DeleteCancellationPolicyForm form) {
        return new DeleteCancellationPolicyCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Policy Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationPolicyTranslation(UserVisitPK userVisitPK, CreateCancellationPolicyTranslationForm form) {
        return new CreateCancellationPolicyTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationPolicyTranslation(UserVisitPK userVisitPK, GetCancellationPolicyTranslationForm form) {
        return new GetCancellationPolicyTranslationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCancellationPolicyTranslations(UserVisitPK userVisitPK, GetCancellationPolicyTranslationsForm form) {
        return new GetCancellationPolicyTranslationsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editCancellationPolicyTranslation(UserVisitPK userVisitPK, EditCancellationPolicyTranslationForm form) {
        return new EditCancellationPolicyTranslationCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationPolicyTranslation(UserVisitPK userVisitPK, DeleteCancellationPolicyTranslationForm form) {
        return new DeleteCancellationPolicyTranslationCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Policy Reasons
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationPolicyReason(UserVisitPK userVisitPK, CreateCancellationPolicyReasonForm form) {
        return new CreateCancellationPolicyReasonCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationPolicyReasons(UserVisitPK userVisitPK, GetCancellationPolicyReasonsForm form) {
        return new GetCancellationPolicyReasonsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCancellationPolicyReason(UserVisitPK userVisitPK, SetDefaultCancellationPolicyReasonForm form) {
        return new SetDefaultCancellationPolicyReasonCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationPolicyReason(UserVisitPK userVisitPK, EditCancellationPolicyReasonForm form) {
        return new EditCancellationPolicyReasonCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationPolicyReason(UserVisitPK userVisitPK, DeleteCancellationPolicyReasonForm form) {
        return new DeleteCancellationPolicyReasonCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Reasons
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationReason(UserVisitPK userVisitPK, CreateCancellationReasonForm form) {
        return new CreateCancellationReasonCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationReasons(UserVisitPK userVisitPK, GetCancellationReasonsForm form) {
        return new GetCancellationReasonsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationReason(UserVisitPK userVisitPK, GetCancellationReasonForm form) {
        return new GetCancellationReasonCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationReasonChoices(UserVisitPK userVisitPK, GetCancellationReasonChoicesForm form) {
        return new GetCancellationReasonChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCancellationReason(UserVisitPK userVisitPK, SetDefaultCancellationReasonForm form) {
        return new SetDefaultCancellationReasonCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationReason(UserVisitPK userVisitPK, EditCancellationReasonForm form) {
        return new EditCancellationReasonCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationReason(UserVisitPK userVisitPK, DeleteCancellationReasonForm form) {
        return new DeleteCancellationReasonCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Reason Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationReasonDescription(UserVisitPK userVisitPK, CreateCancellationReasonDescriptionForm form) {
        return new CreateCancellationReasonDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationReasonDescriptions(UserVisitPK userVisitPK, GetCancellationReasonDescriptionsForm form) {
        return new GetCancellationReasonDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationReasonDescription(UserVisitPK userVisitPK, EditCancellationReasonDescriptionForm form) {
        return new EditCancellationReasonDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationReasonDescription(UserVisitPK userVisitPK, DeleteCancellationReasonDescriptionForm form) {
        return new DeleteCancellationReasonDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Reason Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationReasonType(UserVisitPK userVisitPK, CreateCancellationReasonTypeForm form) {
        return new CreateCancellationReasonTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationReasonTypes(UserVisitPK userVisitPK, GetCancellationReasonTypesForm form) {
        return new GetCancellationReasonTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCancellationReasonType(UserVisitPK userVisitPK, SetDefaultCancellationReasonTypeForm form) {
        return new SetDefaultCancellationReasonTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationReasonType(UserVisitPK userVisitPK, EditCancellationReasonTypeForm form) {
        return new EditCancellationReasonTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationReasonType(UserVisitPK userVisitPK, DeleteCancellationReasonTypeForm form) {
        return new DeleteCancellationReasonTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationType(UserVisitPK userVisitPK, CreateCancellationTypeForm form) {
        return new CreateCancellationTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationTypes(UserVisitPK userVisitPK, GetCancellationTypesForm form) {
        return new GetCancellationTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationType(UserVisitPK userVisitPK, GetCancellationTypeForm form) {
        return new GetCancellationTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationTypeChoices(UserVisitPK userVisitPK, GetCancellationTypeChoicesForm form) {
        return new GetCancellationTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCancellationType(UserVisitPK userVisitPK, SetDefaultCancellationTypeForm form) {
        return new SetDefaultCancellationTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationType(UserVisitPK userVisitPK, EditCancellationTypeForm form) {
        return new EditCancellationTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationType(UserVisitPK userVisitPK, DeleteCancellationTypeForm form) {
        return new DeleteCancellationTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationTypeDescription(UserVisitPK userVisitPK, CreateCancellationTypeDescriptionForm form) {
        return new CreateCancellationTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationTypeDescriptions(UserVisitPK userVisitPK, GetCancellationTypeDescriptionsForm form) {
        return new GetCancellationTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationTypeDescription(UserVisitPK userVisitPK, EditCancellationTypeDescriptionForm form) {
        return new EditCancellationTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationTypeDescription(UserVisitPK userVisitPK, DeleteCancellationTypeDescriptionForm form) {
        return new DeleteCancellationTypeDescriptionCommand().run(userVisitPK, form);
    }
    
}
