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

package com.echothree.model.control.item.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.sequence.server.graphql.SequenceObject;
import com.echothree.model.control.sequence.server.graphql.SequenceSecurityUtils;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.item.common.ItemConstants;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.item.server.entity.ItemCategoryDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("item category object")
@GraphQLName("ItemCategory")
public class ItemCategoryObject
        extends BaseEntityInstanceObject {
    
    private final ItemCategory itemCategory; // Always Present
    
    public ItemCategoryObject(ItemCategory itemCategory) {
        super(itemCategory.getPrimaryKey());
        
        this.itemCategory = itemCategory;
    }

    private ItemCategoryDetail itemCategoryDetail; // Optional, use getItemCategoryDetail()
    
    private ItemCategoryDetail getItemCategoryDetail() {
        if(itemCategoryDetail == null) {
            itemCategoryDetail = itemCategory.getLastDetail();
        }
        
        return itemCategoryDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("item category name")
    @GraphQLNonNull
    public String getItemCategoryName() {
        return getItemCategoryDetail().getItemCategoryName();
    }

    @GraphQLField
    @GraphQLDescription("parent item category")
    public ItemCategoryObject getParentItemCategory() {
        var parentItemCategory = getItemCategoryDetail().getParentItemCategory();
        
        return parentItemCategory == null ? null : new ItemCategoryObject(parentItemCategory);
    }
    
    @GraphQLField
    @GraphQLDescription("item sequence")
    public SequenceObject getItemSequence(final DataFetchingEnvironment env) {
        if(SequenceSecurityUtils.getHasSequenceAccess(env)) {
            var itemSequence = getItemCategoryDetail().getItemSequence();

            return itemSequence == null ? null : new SequenceObject(itemSequence);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getItemCategoryDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getItemCategoryDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var itemControl = Session.getModelController(ItemControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return itemControl.getBestItemCategoryDescription(itemCategory, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("items")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ItemObject> getItems(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getHasItemsAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemsByItemCategory(itemCategory);

            try(var objectLimiter = new ObjectLimiter(env, ItemConstants.COMPONENT_VENDOR_NAME, ItemConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getItemsByItemCategory(itemCategory);
                var items = entities.stream().map(ItemObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, items);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
