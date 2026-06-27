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
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.subscription.server.entity.SubscriptionKind;
import com.echothree.model.data.subscription.server.entity.SubscriptionKindDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("subscription kind object")
@GraphQLName("SubscriptionKind")
public class SubscriptionKindObject
        extends BaseEntityInstanceObject {
    
    private final SubscriptionKind subscriptionKind; // Always Present
    
    public SubscriptionKindObject(SubscriptionKind subscriptionKind) {
        super(subscriptionKind.getPrimaryKey());
        
        this.subscriptionKind = subscriptionKind;
    }

    private SubscriptionKindDetail subscriptionKindDetail; // Optional, use getSubscriptionKindDetail()
    
    private SubscriptionKindDetail getSubscriptionKindDetail() {
        if(subscriptionKindDetail == null) {
            subscriptionKindDetail = subscriptionKind.getLastDetail();
        }
        
        return subscriptionKindDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("subscription kind name")
    @GraphQLNonNull
    public String getSubscriptionKindName() {
        return getSubscriptionKindDetail().getSubscriptionKindName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getSubscriptionKindDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getSubscriptionKindDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var subscriptionControl = Session.getModelController(SubscriptionControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return subscriptionControl.getBestSubscriptionKindDescription(subscriptionKind, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

//    @GraphQLField
//    @GraphQLDescription("subscription types")
//    @GraphQLNonNull
//    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
//    public CountingPaginatedData<SubscriptionTypeObject> getSubscriptionTypes(final DataFetchingEnvironment env) {
//        if(SubscriptionSecurityUtils.getHasSubscriptionTypesAccess(env)) {
//            var subscriptionControl = Session.getModelController(SubscriptionControl.class);
//            var totalCount = subscriptionControl.countSubscriptionTypesBySubscriptionKind(subscriptionKind);
//
//            try(var objectLimiter = new ObjectLimiter(env, SubscriptionTypeConstants.COMPONENT_VENDOR_NAME, SubscriptionTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
//                var entities = subscriptionControl.getSubscriptionTypes(subscriptionKind);
//                var subscriptionTypes = entities.stream().map(SubscriptionTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));
//
//                return new CountedObjects<>(objectLimiter, subscriptionTypes);
//            }
//        } else {
//            return Connections.emptyConnection();
//        }
//    }

}
