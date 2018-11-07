// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
        return new GetPartyCancellationPolicyCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyCancellationPolicies(UserVisitPK userVisitPK, GetPartyCancellationPoliciesForm form) {
        return new GetPartyCancellationPoliciesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyCancellationPolicyStatusChoices(UserVisitPK userVisitPK, GetPartyCancellationPolicyStatusChoicesForm form) {
        return new GetPartyCancellationPolicyStatusChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setPartyCancellationPolicyStatus(UserVisitPK userVisitPK, SetPartyCancellationPolicyStatusForm form) {
        return new SetPartyCancellationPolicyStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartyCancellationPolicy(UserVisitPK userVisitPK, DeletePartyCancellationPolicyForm form) {
        return new DeletePartyCancellationPolicyCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Cancellation Kinds
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationKind(UserVisitPK userVisitPK, CreateCancellationKindForm form) {
        return new CreateCancellationKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationKinds(UserVisitPK userVisitPK, GetCancellationKindsForm form) {
        return new GetCancellationKindsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationKind(UserVisitPK userVisitPK, GetCancellationKindForm form) {
        return new GetCancellationKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationKindChoices(UserVisitPK userVisitPK, GetCancellationKindChoicesForm form) {
        return new GetCancellationKindChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCancellationKind(UserVisitPK userVisitPK, SetDefaultCancellationKindForm form) {
        return new SetDefaultCancellationKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCancellationKind(UserVisitPK userVisitPK, EditCancellationKindForm form) {
        return new EditCancellationKindCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCancellationKind(UserVisitPK userVisitPK, DeleteCancellationKindForm form) {
        return new DeleteCancellationKindCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Kind Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationKindDescription(UserVisitPK userVisitPK, CreateCancellationKindDescriptionForm form) {
        return new CreateCancellationKindDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationKindDescriptions(UserVisitPK userVisitPK, GetCancellationKindDescriptionsForm form) {
        return new GetCancellationKindDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCancellationKindDescription(UserVisitPK userVisitPK, EditCancellationKindDescriptionForm form) {
        return new EditCancellationKindDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCancellationKindDescription(UserVisitPK userVisitPK, DeleteCancellationKindDescriptionForm form) {
        return new DeleteCancellationKindDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationPolicy(UserVisitPK userVisitPK, CreateCancellationPolicyForm form) {
        return new CreateCancellationPolicyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationPolicies(UserVisitPK userVisitPK, GetCancellationPoliciesForm form) {
        return new GetCancellationPoliciesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationPolicy(UserVisitPK userVisitPK, GetCancellationPolicyForm form) {
        return new GetCancellationPolicyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationPolicyChoices(UserVisitPK userVisitPK, GetCancellationPolicyChoicesForm form) {
        return new GetCancellationPolicyChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCancellationPolicy(UserVisitPK userVisitPK, SetDefaultCancellationPolicyForm form) {
        return new SetDefaultCancellationPolicyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCancellationPolicy(UserVisitPK userVisitPK, EditCancellationPolicyForm form) {
        return new EditCancellationPolicyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCancellationPolicy(UserVisitPK userVisitPK, DeleteCancellationPolicyForm form) {
        return new DeleteCancellationPolicyCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Policy Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationPolicyTranslation(UserVisitPK userVisitPK, CreateCancellationPolicyTranslationForm form) {
        return new CreateCancellationPolicyTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationPolicyTranslation(UserVisitPK userVisitPK, GetCancellationPolicyTranslationForm form) {
        return new GetCancellationPolicyTranslationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCancellationPolicyTranslations(UserVisitPK userVisitPK, GetCancellationPolicyTranslationsForm form) {
        return new GetCancellationPolicyTranslationsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editCancellationPolicyTranslation(UserVisitPK userVisitPK, EditCancellationPolicyTranslationForm form) {
        return new EditCancellationPolicyTranslationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCancellationPolicyTranslation(UserVisitPK userVisitPK, DeleteCancellationPolicyTranslationForm form) {
        return new DeleteCancellationPolicyTranslationCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Policy Reasons
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationPolicyReason(UserVisitPK userVisitPK, CreateCancellationPolicyReasonForm form) {
        return new CreateCancellationPolicyReasonCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationPolicyReasons(UserVisitPK userVisitPK, GetCancellationPolicyReasonsForm form) {
        return new GetCancellationPolicyReasonsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCancellationPolicyReason(UserVisitPK userVisitPK, SetDefaultCancellationPolicyReasonForm form) {
        return new SetDefaultCancellationPolicyReasonCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCancellationPolicyReason(UserVisitPK userVisitPK, EditCancellationPolicyReasonForm form) {
        return new EditCancellationPolicyReasonCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCancellationPolicyReason(UserVisitPK userVisitPK, DeleteCancellationPolicyReasonForm form) {
        return new DeleteCancellationPolicyReasonCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Reasons
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationReason(UserVisitPK userVisitPK, CreateCancellationReasonForm form) {
        return new CreateCancellationReasonCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationReasons(UserVisitPK userVisitPK, GetCancellationReasonsForm form) {
        return new GetCancellationReasonsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationReason(UserVisitPK userVisitPK, GetCancellationReasonForm form) {
        return new GetCancellationReasonCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationReasonChoices(UserVisitPK userVisitPK, GetCancellationReasonChoicesForm form) {
        return new GetCancellationReasonChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCancellationReason(UserVisitPK userVisitPK, SetDefaultCancellationReasonForm form) {
        return new SetDefaultCancellationReasonCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCancellationReason(UserVisitPK userVisitPK, EditCancellationReasonForm form) {
        return new EditCancellationReasonCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCancellationReason(UserVisitPK userVisitPK, DeleteCancellationReasonForm form) {
        return new DeleteCancellationReasonCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Reason Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationReasonDescription(UserVisitPK userVisitPK, CreateCancellationReasonDescriptionForm form) {
        return new CreateCancellationReasonDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationReasonDescriptions(UserVisitPK userVisitPK, GetCancellationReasonDescriptionsForm form) {
        return new GetCancellationReasonDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCancellationReasonDescription(UserVisitPK userVisitPK, EditCancellationReasonDescriptionForm form) {
        return new EditCancellationReasonDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCancellationReasonDescription(UserVisitPK userVisitPK, DeleteCancellationReasonDescriptionForm form) {
        return new DeleteCancellationReasonDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Reason Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationReasonType(UserVisitPK userVisitPK, CreateCancellationReasonTypeForm form) {
        return new CreateCancellationReasonTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationReasonTypes(UserVisitPK userVisitPK, GetCancellationReasonTypesForm form) {
        return new GetCancellationReasonTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCancellationReasonType(UserVisitPK userVisitPK, SetDefaultCancellationReasonTypeForm form) {
        return new SetDefaultCancellationReasonTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCancellationReasonType(UserVisitPK userVisitPK, EditCancellationReasonTypeForm form) {
        return new EditCancellationReasonTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCancellationReasonType(UserVisitPK userVisitPK, DeleteCancellationReasonTypeForm form) {
        return new DeleteCancellationReasonTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationType(UserVisitPK userVisitPK, CreateCancellationTypeForm form) {
        return new CreateCancellationTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationTypes(UserVisitPK userVisitPK, GetCancellationTypesForm form) {
        return new GetCancellationTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationType(UserVisitPK userVisitPK, GetCancellationTypeForm form) {
        return new GetCancellationTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationTypeChoices(UserVisitPK userVisitPK, GetCancellationTypeChoicesForm form) {
        return new GetCancellationTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCancellationType(UserVisitPK userVisitPK, SetDefaultCancellationTypeForm form) {
        return new SetDefaultCancellationTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCancellationType(UserVisitPK userVisitPK, EditCancellationTypeForm form) {
        return new EditCancellationTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCancellationType(UserVisitPK userVisitPK, DeleteCancellationTypeForm form) {
        return new DeleteCancellationTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationTypeDescription(UserVisitPK userVisitPK, CreateCancellationTypeDescriptionForm form) {
        return new CreateCancellationTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCancellationTypeDescriptions(UserVisitPK userVisitPK, GetCancellationTypeDescriptionsForm form) {
        return new GetCancellationTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCancellationTypeDescription(UserVisitPK userVisitPK, EditCancellationTypeDescriptionForm form) {
        return new EditCancellationTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCancellationTypeDescription(UserVisitPK userVisitPK, DeleteCancellationTypeDescriptionForm form) {
        return new DeleteCancellationTypeDescriptionCommand(userVisitPK, form).run();
    }
    
}
