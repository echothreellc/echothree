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

package com.echothree.model.control.subscription.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.TimeObject;
import com.echothree.model.control.party.server.graphql.PartyObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.data.subscription.server.entity.Subscription;
import com.echothree.model.data.subscription.server.entity.SubscriptionDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("subscription object")
@GraphQLName("Subscription")
public class SubscriptionObject
        extends BaseEntityInstanceObject {
    
    private final Subscription subscription; // Always Present
    
    public SubscriptionObject(Subscription subscription) {
        super(subscription.getPrimaryKey());
        
        this.subscription = subscription;
    }

    private SubscriptionDetail subscriptionDetail; // Optional, use getSubscriptionDetail()
    
    private SubscriptionDetail getSubscriptionDetail() {
        if(subscriptionDetail == null) {
            subscriptionDetail = subscription.getLastDetail();
        }
        
        return subscriptionDetail;
    }

    @GraphQLField
    @GraphQLDescription("subscription type")
    public SubscriptionTypeObject getSubscriptionType(final DataFetchingEnvironment env) {
        return SubscriptionSecurityUtils.getHasSubscriptionTypeAccess(env) ? new SubscriptionTypeObject(getSubscriptionDetail().getSubscriptionType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("subscription name")
    @GraphQLNonNull
    public String getSubscriptionName() {
        return getSubscriptionDetail().getSubscriptionName();
    }

    @GraphQLField
    @GraphQLDescription("party")
    public PartyObject getParty(final DataFetchingEnvironment env) {
        var party = getSubscriptionDetail().getParty();

        return PartySecurityUtils.getHasPartyAccess(env, party) ? new PartyObject(party) : null;
    }

    @GraphQLField
    @GraphQLDescription("start time")
    @GraphQLNonNull
    public TimeObject getStartTime(final DataFetchingEnvironment env) {
        return new TimeObject(getSubscriptionDetail().getStartTime());
    }

    @GraphQLField
    @GraphQLDescription("end time")
    public TimeObject getEndTime(final DataFetchingEnvironment env) {
        var endTime = getSubscriptionDetail().getEndTime();

        return endTime == null ? null : new TimeObject(endTime);
    }

}
