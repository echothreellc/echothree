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

package com.echothree.model.control.vendor.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.item.server.graphql.ItemSecurityUtils;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.item.common.ItemConstants;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategoryDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("item purchasing category object")
@GraphQLName("ItemPurchasingCategory")
public class ItemPurchasingCategoryObject
        extends BaseEntityInstanceObject {
    
    private final ItemPurchasingCategory itemPurchasingCategory; // Always Present
    
    public ItemPurchasingCategoryObject(ItemPurchasingCategory itemPurchasingCategory) {
        super(itemPurchasingCategory.getPrimaryKey());
        
        this.itemPurchasingCategory = itemPurchasingCategory;
    }

    private ItemPurchasingCategoryDetail itemPurchasingCategoryDetail; // Optional, use getItemPurchasingCategoryDetail()
    
    private ItemPurchasingCategoryDetail getItemPurchasingCategoryDetail() {
        if(itemPurchasingCategoryDetail == null) {
            itemPurchasingCategoryDetail = itemPurchasingCategory.getLastDetail();
        }
        
        return itemPurchasingCategoryDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("item purchasing category name")
    @GraphQLNonNull
    public String getItemPurchasingCategoryName() {
        return getItemPurchasingCategoryDetail().getItemPurchasingCategoryName();
    }

    @GraphQLField
    @GraphQLDescription("parent item purchasing category")
    public ItemPurchasingCategoryObject getParentItemPurchasingCategory() {
        var parentItemPurchasingCategory = getItemPurchasingCategoryDetail().getParentItemPurchasingCategory();
        
        return parentItemPurchasingCategory == null ? null : new ItemPurchasingCategoryObject(parentItemPurchasingCategory);
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getItemPurchasingCategoryDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getItemPurchasingCategoryDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var vendorControl = Session.getModelController(VendorControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return vendorControl.getBestItemPurchasingCategoryDescription(itemPurchasingCategory, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("items")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ItemObject> getItems(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getHasItemsAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemsByItemPurchasingCategory(itemPurchasingCategory);

            try(var objectLimiter = new ObjectLimiter(env, ItemConstants.COMPONENT_VENDOR_NAME, ItemConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getItemsByItemPurchasingCategory(itemPurchasingCategory);
                var items = entities.stream().map(ItemObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, items);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
