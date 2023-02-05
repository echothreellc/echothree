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
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriority;
import com.echothree.model.data.wishlist.server.entity.WishlistTypePriorityDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("wishlist type priority object")
@GraphQLName("WishlistTypePriority")
public class WishlistTypePriorityObject
        extends BaseEntityInstanceObject {
    
    private final WishlistTypePriority wishlistTypePriority; // Always Present

    public WishlistTypePriorityObject(final WishlistTypePriority wishlistTypePriority) {
        super(wishlistTypePriority.getPrimaryKey());

        this.wishlistTypePriority = wishlistTypePriority;
    }

    private WishlistTypePriorityDetail wishlistTypePriorityDetail; // Optional, use getWishlistTypePriorityDetail()
    
    private WishlistTypePriorityDetail getWishlistTypePriorityDetail() {
        if(wishlistTypePriorityDetail == null) {
            wishlistTypePriorityDetail = wishlistTypePriority.getLastDetail();
        }
        
        return wishlistTypePriorityDetail;
    }

    @GraphQLField
    @GraphQLDescription("wishlist type priority name")
    @GraphQLNonNull
    public String getWishlistTypePriorityName() {
        return getWishlistTypePriorityDetail().getWishlistTypePriorityName();
    }

    @GraphQLField
    @GraphQLDescription("wishlist type")
    @GraphQLNonNull
    public WishlistTypeObject getWishlistType(final DataFetchingEnvironment env) {
        return WishlistSecurityUtils.getInstance().getHasWishlistTypeAccess(env) ? new WishlistTypeObject(getWishlistTypePriorityDetail().getWishlistType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getWishlistTypePriorityDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getWishlistTypePriorityDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return wishlistControl.getBestWishlistTypePriorityDescription(wishlistTypePriority, userControl.getPreferredLanguageFromUserVisit(getUserVisit(env)));
    }

}
