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
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.sequence.server.graphql.SequenceObject;
import com.echothree.model.control.sequence.server.graphql.SequenceSecurityUtils;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.model.data.subscription.server.entity.SubscriptionTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("subscription type object")
@GraphQLName("SubscriptionType")
public class SubscriptionTypeObject
        extends BaseEntityInstanceObject {
    
    private final SubscriptionType subscriptionType; // Always Present
    
    public SubscriptionTypeObject(SubscriptionType subscriptionType) {
        super(subscriptionType.getPrimaryKey());
        
        this.subscriptionType = subscriptionType;
    }

    private SubscriptionTypeDetail subscriptionTypeDetail; // Optional, use getSubscriptionTypeDetail()
    
    private SubscriptionTypeDetail getSubscriptionTypeDetail() {
        if(subscriptionTypeDetail == null) {
            subscriptionTypeDetail = subscriptionType.getLastDetail();
        }
        
        return subscriptionTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("subscription kind")
    public SubscriptionKindObject getSubscriptionKind(final DataFetchingEnvironment env) {
        return SubscriptionSecurityUtils.getHasSubscriptionKindAccess(env) ? new SubscriptionKindObject(getSubscriptionTypeDetail().getSubscriptionKind()) : null;
    }

    @GraphQLField
    @GraphQLDescription("subscription type name")
    @GraphQLNonNull
    public String getSubscriptionTypeName() {
        return getSubscriptionTypeDetail().getSubscriptionTypeName();
    }

    @GraphQLField
    @GraphQLDescription("subscription sequence")
    public SequenceObject getSalesOrderSequence(final DataFetchingEnvironment env) {
        var subscriptionSequence = getSubscriptionTypeDetail().getSubscriptionSequence();

        return subscriptionSequence == null ? null : (SequenceSecurityUtils.getHasSequenceAccess(env) ? new SequenceObject(subscriptionSequence) : null);
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getSubscriptionTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getSubscriptionTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var subscriptionControl = Session.getModelController(SubscriptionControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return subscriptionControl.getBestSubscriptionTypeDescription(subscriptionType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

//    @GraphQLField
//    @GraphQLDescription("subscriptions")
//    @GraphQLNonNull
//    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
//    public CountingPaginatedData<SubscriptionObject> getSubscriptions(final DataFetchingEnvironment env) {
//        if(SubscriptionSecurityUtils.getHasSubscriptionsAccess(env)) {
//            var subscriptionControl = Session.getModelController(SubscriptionControl.class);
//            var totalCount = subscriptionControl.countSubscriptionsBySubscriptionType(subscriptionType);
//
//            try(var objectLimiter = new ObjectLimiter(env, SubscriptionConstants.COMPONENT_VENDOR_NAME, SubscriptionConstants.ENTITY_TYPE_NAME, totalCount)) {
//                var entities = subscriptionControl.getSubscriptions(subscriptionType);
//                var subscriptions = entities.stream().map(SubscriptionObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));
//
//                return new CountedObjects<>(objectLimiter, subscriptions);
//            }
//        } else {
//            return Connections.emptyConnection();
//        }
//    }

}
