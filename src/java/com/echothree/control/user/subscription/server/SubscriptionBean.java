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

package com.echothree.control.user.subscription.server;

import com.echothree.control.user.subscription.common.SubscriptionRemote;
import com.echothree.control.user.subscription.common.form.*;
import com.echothree.control.user.subscription.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class SubscriptionBean
        extends SubscriptionFormsImpl
        implements SubscriptionRemote, SubscriptionLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "SubscriptionBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Subscription Kinds
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSubscriptionKind(UserVisitPK userVisitPK, CreateSubscriptionKindForm form) {
        return new CreateSubscriptionKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSubscriptionKinds(UserVisitPK userVisitPK, GetSubscriptionKindsForm form) {
        return new GetSubscriptionKindsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSubscriptionKind(UserVisitPK userVisitPK, GetSubscriptionKindForm form) {
        return new GetSubscriptionKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSubscriptionKindChoices(UserVisitPK userVisitPK, GetSubscriptionKindChoicesForm form) {
        return new GetSubscriptionKindChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultSubscriptionKind(UserVisitPK userVisitPK, SetDefaultSubscriptionKindForm form) {
        return new SetDefaultSubscriptionKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSubscriptionKind(UserVisitPK userVisitPK, EditSubscriptionKindForm form) {
        return new EditSubscriptionKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSubscriptionKind(UserVisitPK userVisitPK, DeleteSubscriptionKindForm form) {
        return new DeleteSubscriptionKindCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Subscription Kind Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSubscriptionKindDescription(UserVisitPK userVisitPK, CreateSubscriptionKindDescriptionForm form) {
        return new CreateSubscriptionKindDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSubscriptionKindDescriptions(UserVisitPK userVisitPK, GetSubscriptionKindDescriptionsForm form) {
        return new GetSubscriptionKindDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSubscriptionKindDescription(UserVisitPK userVisitPK, GetSubscriptionKindDescriptionForm form) {
        return new GetSubscriptionKindDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSubscriptionKindDescription(UserVisitPK userVisitPK, EditSubscriptionKindDescriptionForm form) {
        return new EditSubscriptionKindDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSubscriptionKindDescription(UserVisitPK userVisitPK, DeleteSubscriptionKindDescriptionForm form) {
        return new DeleteSubscriptionKindDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Subscription Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSubscriptionType(UserVisitPK userVisitPK, CreateSubscriptionTypeForm form) {
        return new CreateSubscriptionTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSubscriptionTypes(UserVisitPK userVisitPK, GetSubscriptionTypesForm form) {
        return new GetSubscriptionTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSubscriptionType(UserVisitPK userVisitPK, GetSubscriptionTypeForm form) {
        return new GetSubscriptionTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSubscriptionTypeChoices(UserVisitPK userVisitPK, GetSubscriptionTypeChoicesForm form) {
        return new GetSubscriptionTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSubscriptionType(UserVisitPK userVisitPK, SetDefaultSubscriptionTypeForm form) {
        return new SetDefaultSubscriptionTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSubscriptionType(UserVisitPK userVisitPK, EditSubscriptionTypeForm form) {
        return new EditSubscriptionTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSubscriptionType(UserVisitPK userVisitPK, DeleteSubscriptionTypeForm form) {
        return new DeleteSubscriptionTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Subscription Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSubscriptionTypeDescription(UserVisitPK userVisitPK, CreateSubscriptionTypeDescriptionForm form) {
        return new CreateSubscriptionTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSubscriptionTypeDescriptions(UserVisitPK userVisitPK, GetSubscriptionTypeDescriptionsForm form) {
        return new GetSubscriptionTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSubscriptionTypeDescription(UserVisitPK userVisitPK, EditSubscriptionTypeDescriptionForm form) {
        return new EditSubscriptionTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSubscriptionTypeDescription(UserVisitPK userVisitPK, DeleteSubscriptionTypeDescriptionForm form) {
        return new DeleteSubscriptionTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Subscription Type Chains
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSubscriptionTypeChain(UserVisitPK userVisitPK, CreateSubscriptionTypeChainForm form) {
        return new CreateSubscriptionTypeChainCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSubscriptionTypeChains(UserVisitPK userVisitPK, GetSubscriptionTypeChainsForm form) {
        return new GetSubscriptionTypeChainsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSubscriptionTypeChain(UserVisitPK userVisitPK, DeleteSubscriptionTypeChainForm form) {
        return new DeleteSubscriptionTypeChainCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Subscriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSubscription(UserVisitPK userVisitPK, CreateSubscriptionForm form) {
        return new CreateSubscriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSubscriptions(UserVisitPK userVisitPK, GetSubscriptionsForm form) {
        return new GetSubscriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSubscription(UserVisitPK userVisitPK, GetSubscriptionForm form) {
        return new GetSubscriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSubscription(UserVisitPK userVisitPK, DeleteSubscriptionForm form) {
        return new DeleteSubscriptionCommand().run(userVisitPK, form);
    }
    
}
