// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.wishlist.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.wishlist.common.WishlistTypePriorityConstants;
import com.echothree.model.data.wishlist.server.entity.WishlistType;
import com.echothree.model.data.wishlist.server.entity.WishlistTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("wishlist type object")
@GraphQLName("WishlistType")
public class WishlistTypeObject
        extends BaseEntityInstanceObject {
    
    private final WishlistType wishlistType; // Always Present

    public WishlistTypeObject(final WishlistType wishlistType) {
        super(wishlistType.getPrimaryKey());

        this.wishlistType = wishlistType;
    }

    private WishlistTypeDetail wishlistTypeDetail; // Optional, use getWishlistTypeDetail()
    
    private WishlistTypeDetail getWishlistTypeDetail() {
        if(wishlistTypeDetail == null) {
            wishlistTypeDetail = wishlistType.getLastDetail();
        }
        
        return wishlistTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("wishlist type name")
    @GraphQLNonNull
    public String getWishlistTypeName() {
        return getWishlistTypeDetail().getWishlistTypeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getWishlistTypeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getWishlistTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return wishlistControl.getBestWishlistTypeDescription(wishlistType, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("wishlist type priorities")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<WishlistTypePriorityObject> getWishlistTypePriorities(final DataFetchingEnvironment env) {
        if(WishlistSecurityUtils.getInstance().getHasWishlistTypePrioritiesAccess(env)) {
            var uomControl = Session.getModelController(WishlistControl.class);
            var totalCount = uomControl.countWishlistTypePrioritiesByWishlistType(wishlistType);

            try(var objectLimiter = new ObjectLimiter(env, WishlistTypePriorityConstants.COMPONENT_VENDOR_NAME, WishlistTypePriorityConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = uomControl.getWishlistTypePriorities(wishlistType);
                var wishlistTypePriorities = entities.stream().map(WishlistTypePriorityObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, wishlistTypePriorities);
            }
        } else {
            return Connections.emptyConnection();
        }
    }
    
}
