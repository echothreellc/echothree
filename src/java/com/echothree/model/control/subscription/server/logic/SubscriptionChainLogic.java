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

package com.echothree.model.control.subscription.server.logic;

import com.echothree.model.control.chain.common.ChainConstants;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.chain.server.logic.BaseChainLogic;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.subscription.server.entity.Subscription;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SubscriptionChainLogic
        extends BaseChainLogic {

    protected SubscriptionChainLogic() {
        super();
    }

    public static SubscriptionChainLogic getInstance() {
        return CDI.current().select(SubscriptionChainLogic.class).get();
    }
    
    /** createChainInstance is different than the version in BaseChainLogic, it requires a Subscription parameter. Subscriptions
     * are allowed to override the Chain being used in each SubscriptionType, so this takes that into account when choosing a
     * Chain. If there is no SubscriptionTypeChain, then we fall back to the default for the ChainType, following the normal
     * rules that take OfferChainTypes into account as well. If the chainTypeName == RENEWAL, then we immediately fall back to the
     * default for the ChainType.
     * 
     * TODO: RENEWAL should really be based on the amount of time remaining in the Subscription, and not just fall back to the
     * default. getSubscriptionTypeChainsBySubscriptionTypeAndChainType(...) could order by the remaining time, and then search
     * for the one that's the best fit.
     */
    protected ChainInstance createChainInstance(final ExecutionErrorAccumulator eea, final String chainKindName, final String chainTypeName,
            final Subscription subscription, final BasePK createdBy) {
        var subscriptionControl = Session.getModelController(SubscriptionControl.class);
        var subscriptionType = subscription.getLastDetail().getSubscriptionType();
        var chainType = getChainTypeByName(eea, chainKindName, chainTypeName);
        var party = subscription.getLastDetail().getParty();
        var subscriptionTypeChains = chainTypeName.equals(ChainConstants.ChainType_RENEWAL) ? null
                : subscriptionControl.getSubscriptionTypeChainsBySubscriptionTypeAndChainType(subscriptionType, chainType);
        Chain chain;
        ChainInstance chainInstance = null;
        
        if(subscriptionTypeChains == null || subscriptionTypeChains.isEmpty()) {
            chain = getChain(eea, chainType, party);
        } else {
            chain = subscriptionTypeChains.iterator().next().getChain();
        }
        
        if(chain != null) {
            chainInstance = createChainInstance(eea, chainType, party, createdBy);
        }
        
        return chainInstance;
    }
    
    protected ChainInstance createSubscriptionChainInstance(final ExecutionErrorAccumulator eea, final String chainTypeName, final Subscription subscription,
            final BasePK createdBy) {
        var chainInstance = createChainInstance(eea, ChainConstants.ChainKind_SUBSCRIPTION, chainTypeName, subscription, createdBy);
        
        if(chainInstance != null) {
            var chainControl = Session.getModelController(ChainControl.class);
            var chainType = chainInstance.getLastDetail().getChain().getLastDetail().getChainType();
        
            chainControl.createChainInstanceEntityRole(chainInstance, chainControl.getChainEntityRoleTypeByName(chainType,
                    ChainConstants.ChainEntityRoleType_SUBSCRIPTION), subscription.getPrimaryKey(), createdBy);
        }
        
        return chainInstance;
    }
    
    public ChainInstance createSubscriptionInitialChainInstance(final ExecutionErrorAccumulator eea, final Subscription subscription,
            final BasePK createdBy) {
        return createSubscriptionChainInstance(eea, ChainConstants.ChainType_INITIAL, subscription, createdBy);
    }
    
    public ChainInstance createSubscriptionExpirationWarningChainInstance(final ExecutionErrorAccumulator eea, final Subscription subscription,
            final BasePK createdBy) {
        return createSubscriptionChainInstance(eea, ChainConstants.ChainType_EXPIRATION_WARNING, subscription, createdBy);
    }
    
    /** TODO: Consider the RemainingTime in the SubscriptionDetail when choosing the Chain to use.
     */
    public ChainInstance createSubscriptionRenewalChainInstance(final ExecutionErrorAccumulator eea, final Subscription subscription,
            final BasePK createdBy) {
        return createSubscriptionChainInstance(eea, ChainConstants.ChainType_RENEWAL, subscription, createdBy);
    }
    
    public ChainInstance createSubscriptionExpirationChainInstance(final ExecutionErrorAccumulator eea, final Subscription subscription,
            final BasePK createdBy) {
        return createSubscriptionChainInstance(eea, ChainConstants.ChainType_EXPIRATION, subscription, createdBy);
    }
    
}
