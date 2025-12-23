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

package com.echothree.control.user.subscription.server;

import com.echothree.control.user.subscription.common.SubscriptionRemote;
import com.echothree.control.user.subscription.common.form.*;
import com.echothree.control.user.subscription.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateSubscriptionKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSubscriptionKinds(UserVisitPK userVisitPK, GetSubscriptionKindsForm form) {
        return CDI.current().select(GetSubscriptionKindsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSubscriptionKind(UserVisitPK userVisitPK, GetSubscriptionKindForm form) {
        return CDI.current().select(GetSubscriptionKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSubscriptionKindChoices(UserVisitPK userVisitPK, GetSubscriptionKindChoicesForm form) {
        return CDI.current().select(GetSubscriptionKindChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultSubscriptionKind(UserVisitPK userVisitPK, SetDefaultSubscriptionKindForm form) {
        return CDI.current().select(SetDefaultSubscriptionKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSubscriptionKind(UserVisitPK userVisitPK, EditSubscriptionKindForm form) {
        return CDI.current().select(EditSubscriptionKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSubscriptionKind(UserVisitPK userVisitPK, DeleteSubscriptionKindForm form) {
        return CDI.current().select(DeleteSubscriptionKindCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Subscription Kind Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createSubscriptionKindDescription(UserVisitPK userVisitPK, CreateSubscriptionKindDescriptionForm form) {
        return CDI.current().select(CreateSubscriptionKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSubscriptionKindDescriptions(UserVisitPK userVisitPK, GetSubscriptionKindDescriptionsForm form) {
        return CDI.current().select(GetSubscriptionKindDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getSubscriptionKindDescription(UserVisitPK userVisitPK, GetSubscriptionKindDescriptionForm form) {
        return CDI.current().select(GetSubscriptionKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editSubscriptionKindDescription(UserVisitPK userVisitPK, EditSubscriptionKindDescriptionForm form) {
        return CDI.current().select(EditSubscriptionKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteSubscriptionKindDescription(UserVisitPK userVisitPK, DeleteSubscriptionKindDescriptionForm form) {
        return CDI.current().select(DeleteSubscriptionKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Subscription Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSubscriptionType(UserVisitPK userVisitPK, CreateSubscriptionTypeForm form) {
        return CDI.current().select(CreateSubscriptionTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSubscriptionTypes(UserVisitPK userVisitPK, GetSubscriptionTypesForm form) {
        return CDI.current().select(GetSubscriptionTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSubscriptionType(UserVisitPK userVisitPK, GetSubscriptionTypeForm form) {
        return CDI.current().select(GetSubscriptionTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSubscriptionTypeChoices(UserVisitPK userVisitPK, GetSubscriptionTypeChoicesForm form) {
        return CDI.current().select(GetSubscriptionTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSubscriptionType(UserVisitPK userVisitPK, SetDefaultSubscriptionTypeForm form) {
        return CDI.current().select(SetDefaultSubscriptionTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSubscriptionType(UserVisitPK userVisitPK, EditSubscriptionTypeForm form) {
        return CDI.current().select(EditSubscriptionTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSubscriptionType(UserVisitPK userVisitPK, DeleteSubscriptionTypeForm form) {
        return CDI.current().select(DeleteSubscriptionTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Subscription Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSubscriptionTypeDescription(UserVisitPK userVisitPK, CreateSubscriptionTypeDescriptionForm form) {
        return CDI.current().select(CreateSubscriptionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSubscriptionTypeDescriptions(UserVisitPK userVisitPK, GetSubscriptionTypeDescriptionsForm form) {
        return CDI.current().select(GetSubscriptionTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSubscriptionTypeDescription(UserVisitPK userVisitPK, EditSubscriptionTypeDescriptionForm form) {
        return CDI.current().select(EditSubscriptionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSubscriptionTypeDescription(UserVisitPK userVisitPK, DeleteSubscriptionTypeDescriptionForm form) {
        return CDI.current().select(DeleteSubscriptionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Subscription Type Chains
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSubscriptionTypeChain(UserVisitPK userVisitPK, CreateSubscriptionTypeChainForm form) {
        return CDI.current().select(CreateSubscriptionTypeChainCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSubscriptionTypeChains(UserVisitPK userVisitPK, GetSubscriptionTypeChainsForm form) {
        return CDI.current().select(GetSubscriptionTypeChainsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSubscriptionTypeChain(UserVisitPK userVisitPK, DeleteSubscriptionTypeChainForm form) {
        return CDI.current().select(DeleteSubscriptionTypeChainCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Subscriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSubscription(UserVisitPK userVisitPK, CreateSubscriptionForm form) {
        return CDI.current().select(CreateSubscriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSubscriptions(UserVisitPK userVisitPK, GetSubscriptionsForm form) {
        return CDI.current().select(GetSubscriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSubscription(UserVisitPK userVisitPK, GetSubscriptionForm form) {
        return CDI.current().select(GetSubscriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSubscription(UserVisitPK userVisitPK, DeleteSubscriptionForm form) {
        return CDI.current().select(DeleteSubscriptionCommand.class).get().run(userVisitPK, form);
    }
    
}
