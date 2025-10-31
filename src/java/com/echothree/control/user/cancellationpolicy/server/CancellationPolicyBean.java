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
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(GetPartyCancellationPolicyCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyCancellationPolicies(UserVisitPK userVisitPK, GetPartyCancellationPoliciesForm form) {
        return CDI.current().select(GetPartyCancellationPoliciesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyCancellationPolicyStatusChoices(UserVisitPK userVisitPK, GetPartyCancellationPolicyStatusChoicesForm form) {
        return CDI.current().select(GetPartyCancellationPolicyStatusChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setPartyCancellationPolicyStatus(UserVisitPK userVisitPK, SetPartyCancellationPolicyStatusForm form) {
        return CDI.current().select(SetPartyCancellationPolicyStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyCancellationPolicy(UserVisitPK userVisitPK, DeletePartyCancellationPolicyForm form) {
        return CDI.current().select(DeletePartyCancellationPolicyCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Cancellation Kinds
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationKind(UserVisitPK userVisitPK, CreateCancellationKindForm form) {
        return CDI.current().select(CreateCancellationKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationKinds(UserVisitPK userVisitPK, GetCancellationKindsForm form) {
        return CDI.current().select(GetCancellationKindsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationKind(UserVisitPK userVisitPK, GetCancellationKindForm form) {
        return CDI.current().select(GetCancellationKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationKindChoices(UserVisitPK userVisitPK, GetCancellationKindChoicesForm form) {
        return CDI.current().select(GetCancellationKindChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCancellationKind(UserVisitPK userVisitPK, SetDefaultCancellationKindForm form) {
        return CDI.current().select(SetDefaultCancellationKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationKind(UserVisitPK userVisitPK, EditCancellationKindForm form) {
        return CDI.current().select(EditCancellationKindCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationKind(UserVisitPK userVisitPK, DeleteCancellationKindForm form) {
        return CDI.current().select(DeleteCancellationKindCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Kind Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationKindDescription(UserVisitPK userVisitPK, CreateCancellationKindDescriptionForm form) {
        return CDI.current().select(CreateCancellationKindDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationKindDescriptions(UserVisitPK userVisitPK, GetCancellationKindDescriptionsForm form) {
        return CDI.current().select(GetCancellationKindDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationKindDescription(UserVisitPK userVisitPK, EditCancellationKindDescriptionForm form) {
        return CDI.current().select(EditCancellationKindDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationKindDescription(UserVisitPK userVisitPK, DeleteCancellationKindDescriptionForm form) {
        return CDI.current().select(DeleteCancellationKindDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Policies
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationPolicy(UserVisitPK userVisitPK, CreateCancellationPolicyForm form) {
        return CDI.current().select(CreateCancellationPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationPolicies(UserVisitPK userVisitPK, GetCancellationPoliciesForm form) {
        return CDI.current().select(GetCancellationPoliciesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationPolicy(UserVisitPK userVisitPK, GetCancellationPolicyForm form) {
        return CDI.current().select(GetCancellationPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationPolicyChoices(UserVisitPK userVisitPK, GetCancellationPolicyChoicesForm form) {
        return CDI.current().select(GetCancellationPolicyChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCancellationPolicy(UserVisitPK userVisitPK, SetDefaultCancellationPolicyForm form) {
        return CDI.current().select(SetDefaultCancellationPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationPolicy(UserVisitPK userVisitPK, EditCancellationPolicyForm form) {
        return CDI.current().select(EditCancellationPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationPolicy(UserVisitPK userVisitPK, DeleteCancellationPolicyForm form) {
        return CDI.current().select(DeleteCancellationPolicyCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Policy Translations
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationPolicyTranslation(UserVisitPK userVisitPK, CreateCancellationPolicyTranslationForm form) {
        return CDI.current().select(CreateCancellationPolicyTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationPolicyTranslation(UserVisitPK userVisitPK, GetCancellationPolicyTranslationForm form) {
        return CDI.current().select(GetCancellationPolicyTranslationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getCancellationPolicyTranslations(UserVisitPK userVisitPK, GetCancellationPolicyTranslationsForm form) {
        return CDI.current().select(GetCancellationPolicyTranslationsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editCancellationPolicyTranslation(UserVisitPK userVisitPK, EditCancellationPolicyTranslationForm form) {
        return CDI.current().select(EditCancellationPolicyTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationPolicyTranslation(UserVisitPK userVisitPK, DeleteCancellationPolicyTranslationForm form) {
        return CDI.current().select(DeleteCancellationPolicyTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Policy Reasons
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationPolicyReason(UserVisitPK userVisitPK, CreateCancellationPolicyReasonForm form) {
        return CDI.current().select(CreateCancellationPolicyReasonCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationPolicyReasons(UserVisitPK userVisitPK, GetCancellationPolicyReasonsForm form) {
        return CDI.current().select(GetCancellationPolicyReasonsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCancellationPolicyReason(UserVisitPK userVisitPK, SetDefaultCancellationPolicyReasonForm form) {
        return CDI.current().select(SetDefaultCancellationPolicyReasonCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationPolicyReason(UserVisitPK userVisitPK, EditCancellationPolicyReasonForm form) {
        return CDI.current().select(EditCancellationPolicyReasonCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationPolicyReason(UserVisitPK userVisitPK, DeleteCancellationPolicyReasonForm form) {
        return CDI.current().select(DeleteCancellationPolicyReasonCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Reasons
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationReason(UserVisitPK userVisitPK, CreateCancellationReasonForm form) {
        return CDI.current().select(CreateCancellationReasonCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationReasons(UserVisitPK userVisitPK, GetCancellationReasonsForm form) {
        return CDI.current().select(GetCancellationReasonsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationReason(UserVisitPK userVisitPK, GetCancellationReasonForm form) {
        return CDI.current().select(GetCancellationReasonCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationReasonChoices(UserVisitPK userVisitPK, GetCancellationReasonChoicesForm form) {
        return CDI.current().select(GetCancellationReasonChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCancellationReason(UserVisitPK userVisitPK, SetDefaultCancellationReasonForm form) {
        return CDI.current().select(SetDefaultCancellationReasonCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationReason(UserVisitPK userVisitPK, EditCancellationReasonForm form) {
        return CDI.current().select(EditCancellationReasonCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationReason(UserVisitPK userVisitPK, DeleteCancellationReasonForm form) {
        return CDI.current().select(DeleteCancellationReasonCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Reason Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationReasonDescription(UserVisitPK userVisitPK, CreateCancellationReasonDescriptionForm form) {
        return CDI.current().select(CreateCancellationReasonDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationReasonDescriptions(UserVisitPK userVisitPK, GetCancellationReasonDescriptionsForm form) {
        return CDI.current().select(GetCancellationReasonDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationReasonDescription(UserVisitPK userVisitPK, EditCancellationReasonDescriptionForm form) {
        return CDI.current().select(EditCancellationReasonDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationReasonDescription(UserVisitPK userVisitPK, DeleteCancellationReasonDescriptionForm form) {
        return CDI.current().select(DeleteCancellationReasonDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Reason Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationReasonType(UserVisitPK userVisitPK, CreateCancellationReasonTypeForm form) {
        return CDI.current().select(CreateCancellationReasonTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationReasonTypes(UserVisitPK userVisitPK, GetCancellationReasonTypesForm form) {
        return CDI.current().select(GetCancellationReasonTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCancellationReasonType(UserVisitPK userVisitPK, SetDefaultCancellationReasonTypeForm form) {
        return CDI.current().select(SetDefaultCancellationReasonTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationReasonType(UserVisitPK userVisitPK, EditCancellationReasonTypeForm form) {
        return CDI.current().select(EditCancellationReasonTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationReasonType(UserVisitPK userVisitPK, DeleteCancellationReasonTypeForm form) {
        return CDI.current().select(DeleteCancellationReasonTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationType(UserVisitPK userVisitPK, CreateCancellationTypeForm form) {
        return CDI.current().select(CreateCancellationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationTypes(UserVisitPK userVisitPK, GetCancellationTypesForm form) {
        return CDI.current().select(GetCancellationTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationType(UserVisitPK userVisitPK, GetCancellationTypeForm form) {
        return CDI.current().select(GetCancellationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationTypeChoices(UserVisitPK userVisitPK, GetCancellationTypeChoicesForm form) {
        return CDI.current().select(GetCancellationTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultCancellationType(UserVisitPK userVisitPK, SetDefaultCancellationTypeForm form) {
        return CDI.current().select(SetDefaultCancellationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationType(UserVisitPK userVisitPK, EditCancellationTypeForm form) {
        return CDI.current().select(EditCancellationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationType(UserVisitPK userVisitPK, DeleteCancellationTypeForm form) {
        return CDI.current().select(DeleteCancellationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Cancellation Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCancellationTypeDescription(UserVisitPK userVisitPK, CreateCancellationTypeDescriptionForm form) {
        return CDI.current().select(CreateCancellationTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getCancellationTypeDescriptions(UserVisitPK userVisitPK, GetCancellationTypeDescriptionsForm form) {
        return CDI.current().select(GetCancellationTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editCancellationTypeDescription(UserVisitPK userVisitPK, EditCancellationTypeDescriptionForm form) {
        return CDI.current().select(EditCancellationTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteCancellationTypeDescription(UserVisitPK userVisitPK, DeleteCancellationTypeDescriptionForm form) {
        return CDI.current().select(DeleteCancellationTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
}
