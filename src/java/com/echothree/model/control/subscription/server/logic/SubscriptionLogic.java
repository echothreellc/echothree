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

package com.echothree.model.control.subscription.server.logic;

import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.subscription.server.entity.Subscription;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class SubscriptionLogic {

    protected SubscriptionLogic() {
        super();
    }

    public static SubscriptionLogic getInstance() {
        return CDI.current().select(SubscriptionLogic.class).get();
    }
    
    public Subscription createSubscription(final ExecutionErrorAccumulator eea, final Session session, final SubscriptionType subscriptionType, final Party party,
            final Long endTime, final BasePK createdBy) {
        var subscriptionControl = Session.getModelController(SubscriptionControl.class);
        var subscription = subscriptionControl.createSubscription(subscriptionType, party, session.getStartTime(), endTime, createdBy);

        SubscriptionChainLogic.getInstance().createSubscriptionInitialChainInstance(eea, subscription, createdBy);
        
        return subscription;
    }
    
}
