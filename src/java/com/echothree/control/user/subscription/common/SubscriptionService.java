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

package com.echothree.control.user.subscription.common;

import com.echothree.control.user.subscription.common.form.*;
import com.echothree.control.user.subscription.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface SubscriptionService
        extends SubscriptionForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Subscription Kinds
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createSubscriptionKind(UserVisitPK userVisitPK, CreateSubscriptionKindForm form);

    CommandResult<GetSubscriptionKindsResult> getSubscriptionKinds(UserVisitPK userVisitPK, GetSubscriptionKindsForm form);

    CommandResult<GetSubscriptionKindResult> getSubscriptionKind(UserVisitPK userVisitPK, GetSubscriptionKindForm form);

    CommandResult<GetSubscriptionKindChoicesResult> getSubscriptionKindChoices(UserVisitPK userVisitPK, GetSubscriptionKindChoicesForm form);

    CommandResult<VoidResult> setDefaultSubscriptionKind(UserVisitPK userVisitPK, SetDefaultSubscriptionKindForm form);

    CommandResult<EditSubscriptionKindResult> editSubscriptionKind(UserVisitPK userVisitPK, EditSubscriptionKindForm form);

    CommandResult<VoidResult> deleteSubscriptionKind(UserVisitPK userVisitPK, DeleteSubscriptionKindForm form);

    // -------------------------------------------------------------------------
    //   Subscription Kind Descriptions
    // -------------------------------------------------------------------------

    CommandResult<VoidResult> createSubscriptionKindDescription(UserVisitPK userVisitPK, CreateSubscriptionKindDescriptionForm form);

    CommandResult<GetSubscriptionKindDescriptionsResult> getSubscriptionKindDescriptions(UserVisitPK userVisitPK, GetSubscriptionKindDescriptionsForm form);

    CommandResult<GetSubscriptionKindDescriptionResult> getSubscriptionKindDescription(UserVisitPK userVisitPK, GetSubscriptionKindDescriptionForm form);

    CommandResult<EditSubscriptionKindDescriptionResult> editSubscriptionKindDescription(UserVisitPK userVisitPK, EditSubscriptionKindDescriptionForm form);

    CommandResult<VoidResult> deleteSubscriptionKindDescription(UserVisitPK userVisitPK, DeleteSubscriptionKindDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Subscription Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createSubscriptionType(UserVisitPK userVisitPK, CreateSubscriptionTypeForm form);
    
    CommandResult<GetSubscriptionTypesResult> getSubscriptionTypes(UserVisitPK userVisitPK, GetSubscriptionTypesForm form);
    
    CommandResult<GetSubscriptionTypeResult> getSubscriptionType(UserVisitPK userVisitPK, GetSubscriptionTypeForm form);
    
    CommandResult<GetSubscriptionTypeChoicesResult> getSubscriptionTypeChoices(UserVisitPK userVisitPK, GetSubscriptionTypeChoicesForm form);
    
    CommandResult<VoidResult> setDefaultSubscriptionType(UserVisitPK userVisitPK, SetDefaultSubscriptionTypeForm form);
    
    CommandResult<EditSubscriptionTypeResult> editSubscriptionType(UserVisitPK userVisitPK, EditSubscriptionTypeForm form);
    
    CommandResult<VoidResult> deleteSubscriptionType(UserVisitPK userVisitPK, DeleteSubscriptionTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Subscription Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createSubscriptionTypeDescription(UserVisitPK userVisitPK, CreateSubscriptionTypeDescriptionForm form);
    
    CommandResult<GetSubscriptionTypeDescriptionsResult> getSubscriptionTypeDescriptions(UserVisitPK userVisitPK, GetSubscriptionTypeDescriptionsForm form);
    
    CommandResult<EditSubscriptionTypeDescriptionResult> editSubscriptionTypeDescription(UserVisitPK userVisitPK, EditSubscriptionTypeDescriptionForm form);
    
    CommandResult<VoidResult> deleteSubscriptionTypeDescription(UserVisitPK userVisitPK, DeleteSubscriptionTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Subscription Type Chains
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createSubscriptionTypeChain(UserVisitPK userVisitPK, CreateSubscriptionTypeChainForm form);
    
    CommandResult<GetSubscriptionTypeChainsResult> getSubscriptionTypeChains(UserVisitPK userVisitPK, GetSubscriptionTypeChainsForm form);
    
    CommandResult<VoidResult> deleteSubscriptionTypeChain(UserVisitPK userVisitPK, DeleteSubscriptionTypeChainForm form);
    
    // --------------------------------------------------------------------------------
    //   Subscriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createSubscription(UserVisitPK userVisitPK, CreateSubscriptionForm form);
    
    CommandResult<GetSubscriptionsResult> getSubscriptions(UserVisitPK userVisitPK, GetSubscriptionsForm form);
    
    CommandResult<GetSubscriptionResult> getSubscription(UserVisitPK userVisitPK, GetSubscriptionForm form);
    
    CommandResult<VoidResult> deleteSubscription(UserVisitPK userVisitPK, DeleteSubscriptionForm form);
    
}
