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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface SubscriptionService
        extends SubscriptionForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Subscription Kinds
    // -------------------------------------------------------------------------

    CommandResult createSubscriptionKind(UserVisitPK userVisitPK, CreateSubscriptionKindForm form);

    CommandResult getSubscriptionKinds(UserVisitPK userVisitPK, GetSubscriptionKindsForm form);

    CommandResult getSubscriptionKind(UserVisitPK userVisitPK, GetSubscriptionKindForm form);

    CommandResult getSubscriptionKindChoices(UserVisitPK userVisitPK, GetSubscriptionKindChoicesForm form);

    CommandResult setDefaultSubscriptionKind(UserVisitPK userVisitPK, SetDefaultSubscriptionKindForm form);

    CommandResult editSubscriptionKind(UserVisitPK userVisitPK, EditSubscriptionKindForm form);

    CommandResult deleteSubscriptionKind(UserVisitPK userVisitPK, DeleteSubscriptionKindForm form);

    // -------------------------------------------------------------------------
    //   Subscription Kind Descriptions
    // -------------------------------------------------------------------------

    CommandResult createSubscriptionKindDescription(UserVisitPK userVisitPK, CreateSubscriptionKindDescriptionForm form);

    CommandResult getSubscriptionKindDescriptions(UserVisitPK userVisitPK, GetSubscriptionKindDescriptionsForm form);

    CommandResult getSubscriptionKindDescription(UserVisitPK userVisitPK, GetSubscriptionKindDescriptionForm form);

    CommandResult editSubscriptionKindDescription(UserVisitPK userVisitPK, EditSubscriptionKindDescriptionForm form);

    CommandResult deleteSubscriptionKindDescription(UserVisitPK userVisitPK, DeleteSubscriptionKindDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Subscription Types
    // --------------------------------------------------------------------------------
    
    CommandResult createSubscriptionType(UserVisitPK userVisitPK, CreateSubscriptionTypeForm form);
    
    CommandResult getSubscriptionTypes(UserVisitPK userVisitPK, GetSubscriptionTypesForm form);
    
    CommandResult getSubscriptionType(UserVisitPK userVisitPK, GetSubscriptionTypeForm form);
    
    CommandResult getSubscriptionTypeChoices(UserVisitPK userVisitPK, GetSubscriptionTypeChoicesForm form);
    
    CommandResult setDefaultSubscriptionType(UserVisitPK userVisitPK, SetDefaultSubscriptionTypeForm form);
    
    CommandResult editSubscriptionType(UserVisitPK userVisitPK, EditSubscriptionTypeForm form);
    
    CommandResult deleteSubscriptionType(UserVisitPK userVisitPK, DeleteSubscriptionTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Subscription Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createSubscriptionTypeDescription(UserVisitPK userVisitPK, CreateSubscriptionTypeDescriptionForm form);
    
    CommandResult getSubscriptionTypeDescriptions(UserVisitPK userVisitPK, GetSubscriptionTypeDescriptionsForm form);
    
    CommandResult editSubscriptionTypeDescription(UserVisitPK userVisitPK, EditSubscriptionTypeDescriptionForm form);
    
    CommandResult deleteSubscriptionTypeDescription(UserVisitPK userVisitPK, DeleteSubscriptionTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Subscription Type Chains
    // --------------------------------------------------------------------------------
    
    CommandResult createSubscriptionTypeChain(UserVisitPK userVisitPK, CreateSubscriptionTypeChainForm form);
    
    CommandResult getSubscriptionTypeChains(UserVisitPK userVisitPK, GetSubscriptionTypeChainsForm form);
    
    CommandResult deleteSubscriptionTypeChain(UserVisitPK userVisitPK, DeleteSubscriptionTypeChainForm form);
    
    // --------------------------------------------------------------------------------
    //   Subscriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createSubscription(UserVisitPK userVisitPK, CreateSubscriptionForm form);
    
    CommandResult getSubscriptions(UserVisitPK userVisitPK, GetSubscriptionsForm form);
    
    CommandResult getSubscription(UserVisitPK userVisitPK, GetSubscriptionForm form);
    
    CommandResult deleteSubscription(UserVisitPK userVisitPK, DeleteSubscriptionForm form);
    
}
