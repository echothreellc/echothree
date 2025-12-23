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

package com.echothree.model.control.wishlist.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.wishlist.server.control.WishlistControl;
import com.echothree.model.data.wishlist.server.entity.WishlistPriority;
import com.echothree.model.data.wishlist.server.entity.WishlistPriorityDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("wishlist type priority object")
@GraphQLName("WishlistPriority")
public class WishlistPriorityObject
        extends BaseEntityInstanceObject {
    
    private final WishlistPriority wishlistPriority; // Always Present

    public WishlistPriorityObject(final WishlistPriority wishlistPriority) {
        super(wishlistPriority.getPrimaryKey());

        this.wishlistPriority = wishlistPriority;
    }

    private WishlistPriorityDetail wishlistPriorityDetail; // Optional, use getWishlistPriorityDetail()
    
    private WishlistPriorityDetail getWishlistPriorityDetail() {
        if(wishlistPriorityDetail == null) {
            wishlistPriorityDetail = wishlistPriority.getLastDetail();
        }
        
        return wishlistPriorityDetail;
    }

    @GraphQLField
    @GraphQLDescription("wishlist type priority name")
    @GraphQLNonNull
    public String getWishlistPriorityName() {
        return getWishlistPriorityDetail().getWishlistPriorityName();
    }

    @GraphQLField
    @GraphQLDescription("wishlist type")
    @GraphQLNonNull
    public WishlistTypeObject getWishlistType(final DataFetchingEnvironment env) {
        return WishlistSecurityUtils.getHasWishlistTypeAccess(env) ? new WishlistTypeObject(getWishlistPriorityDetail().getWishlistType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getWishlistPriorityDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getWishlistPriorityDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var wishlistControl = Session.getModelController(WishlistControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return wishlistControl.getBestWishlistPriorityDescription(wishlistPriority, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
