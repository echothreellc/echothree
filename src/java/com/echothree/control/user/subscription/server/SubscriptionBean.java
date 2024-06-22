// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
        return new CreateSubscriptionKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSubscriptionKinds(UserVisitPK userVisitPK, GetSubscriptionKindsForm form) {
        return new GetSubscriptionKindsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSubscriptionKind(UserVisitPK userVisitPK, GetSubscriptionKindForm form) {
        return new GetSubscriptionKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSubscriptionKindChoices(UserVisitPK userVisitPK, GetSubscriptionKindChoicesForm form) {
        return new GetSubscriptionKindChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultSubscriptionKind(UserVisitPK userVisitPK, SetDefaultSubscriptionKindForm form) {
        return new SetDefaultSubscriptionKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSubscriptionKind(UserVisitPK userVisitPK, EditSubscriptionKindForm form) {
        return new EditSubscriptionKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSubscriptionKind(UserVisitPK userVisitPK, DeleteSubscriptionKindForm form) {
        return new DeleteSubscriptionKindCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Subscription Kind Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSubscriptionKindDescription(UserVisitPK userVisitPK, CreateSubscriptionKindDescriptionForm form) {
        return new CreateSubscriptionKindDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSubscriptionKindDescriptions(UserVisitPK userVisitPK, GetSubscriptionKindDescriptionsForm form) {
        return new GetSubscriptionKindDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getSubscriptionKindDescription(UserVisitPK userVisitPK, GetSubscriptionKindDescriptionForm form) {
        return new GetSubscriptionKindDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editSubscriptionKindDescription(UserVisitPK userVisitPK, EditSubscriptionKindDescriptionForm form) {
        return new EditSubscriptionKindDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteSubscriptionKindDescription(UserVisitPK userVisitPK, DeleteSubscriptionKindDescriptionForm form) {
        return new DeleteSubscriptionKindDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Subscription Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSubscriptionType(UserVisitPK userVisitPK, CreateSubscriptionTypeForm form) {
        return new CreateSubscriptionTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSubscriptionTypes(UserVisitPK userVisitPK, GetSubscriptionTypesForm form) {
        return new GetSubscriptionTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSubscriptionType(UserVisitPK userVisitPK, GetSubscriptionTypeForm form) {
        return new GetSubscriptionTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSubscriptionTypeChoices(UserVisitPK userVisitPK, GetSubscriptionTypeChoicesForm form) {
        return new GetSubscriptionTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultSubscriptionType(UserVisitPK userVisitPK, SetDefaultSubscriptionTypeForm form) {
        return new SetDefaultSubscriptionTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSubscriptionType(UserVisitPK userVisitPK, EditSubscriptionTypeForm form) {
        return new EditSubscriptionTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSubscriptionType(UserVisitPK userVisitPK, DeleteSubscriptionTypeForm form) {
        return new DeleteSubscriptionTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Subscription Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSubscriptionTypeDescription(UserVisitPK userVisitPK, CreateSubscriptionTypeDescriptionForm form) {
        return new CreateSubscriptionTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSubscriptionTypeDescriptions(UserVisitPK userVisitPK, GetSubscriptionTypeDescriptionsForm form) {
        return new GetSubscriptionTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSubscriptionTypeDescription(UserVisitPK userVisitPK, EditSubscriptionTypeDescriptionForm form) {
        return new EditSubscriptionTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSubscriptionTypeDescription(UserVisitPK userVisitPK, DeleteSubscriptionTypeDescriptionForm form) {
        return new DeleteSubscriptionTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Subscription Type Chains
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSubscriptionTypeChain(UserVisitPK userVisitPK, CreateSubscriptionTypeChainForm form) {
        return new CreateSubscriptionTypeChainCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSubscriptionTypeChains(UserVisitPK userVisitPK, GetSubscriptionTypeChainsForm form) {
        return new GetSubscriptionTypeChainsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSubscriptionTypeChain(UserVisitPK userVisitPK, DeleteSubscriptionTypeChainForm form) {
        return new DeleteSubscriptionTypeChainCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Subscriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSubscription(UserVisitPK userVisitPK, CreateSubscriptionForm form) {
        return new CreateSubscriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSubscriptions(UserVisitPK userVisitPK, GetSubscriptionsForm form) {
        return new GetSubscriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSubscription(UserVisitPK userVisitPK, GetSubscriptionForm form) {
        return new GetSubscriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSubscription(UserVisitPK userVisitPK, DeleteSubscriptionForm form) {
        return new DeleteSubscriptionCommand(userVisitPK, form).run();
    }
    
}
