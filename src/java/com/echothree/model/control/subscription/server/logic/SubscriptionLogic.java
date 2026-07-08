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

import com.echothree.control.user.subscription.common.spec.SubscriptionUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.subscription.common.exception.UnknownSubscriptionNameException;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.subscription.server.entity.Subscription;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class SubscriptionLogic
        extends BaseLogic {

    @Inject
    SubscriptionControl subscriptionControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    SubscriptionChainLogic subscriptionChainLogic;

    protected SubscriptionLogic() {
        super();
    }

    public static SubscriptionLogic getInstance() {
        return CDI.current().select(SubscriptionLogic.class).get();
    }

    public Subscription createSubscription(final ExecutionErrorAccumulator eea, final Session session, final SubscriptionType subscriptionType, final Party party,
            final Long endTime, final BasePK createdBy) {
        var subscription = subscriptionControl.createSubscription(subscriptionType, party, session.getStartTime(), endTime, createdBy);

        subscriptionChainLogic.createSubscriptionInitialChainInstance(eea, subscription, createdBy);

        return subscription;
    }

    public Subscription getSubscriptionByName(final ExecutionErrorAccumulator eea, final String subscriptionName,
            final EntityPermission entityPermission) {
        var subscription = subscriptionControl.getSubscriptionByName(subscriptionName, entityPermission);

        if(subscription == null) {
            handleExecutionError(UnknownSubscriptionNameException.class, eea, ExecutionErrors.UnknownSubscriptionName.name(), subscriptionName);
        }

        return subscription;
    }

    public Subscription getSubscriptionByName(final ExecutionErrorAccumulator eea, final String subscriptionName) {
        return getSubscriptionByName(eea, subscriptionName, EntityPermission.READ_ONLY);
    }

    public Subscription getSubscriptionByNameForUpdate(final ExecutionErrorAccumulator eea, final String subscriptionName) {
        return getSubscriptionByName(eea, subscriptionName, EntityPermission.READ_WRITE);
    }

    public Subscription getSubscriptionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SubscriptionUniversalSpec universalSpec, final EntityPermission entityPermission) {
        Subscription subscription = null;
        var subscriptionName = universalSpec.getSubscriptionName();
        var parameterCount = (subscriptionName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(subscriptionName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Subscription.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        subscription = subscriptionControl.getSubscriptionByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    subscription = getSubscriptionByName(eea, subscriptionName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return subscription;
    }

    public Subscription getSubscriptionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final SubscriptionUniversalSpec universalSpec) {
        return getSubscriptionByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public Subscription getSubscriptionByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final SubscriptionUniversalSpec universalSpec) {
        return getSubscriptionByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
