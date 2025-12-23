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

package com.echothree.model.control.accounting.server.graphql;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.item.server.graphql.ItemSecurityUtils;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategoryDetail;
import com.echothree.model.data.item.common.ItemConstants;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("item accounting category object")
@GraphQLName("ItemAccountingCategory")
public class ItemAccountingCategoryObject
        extends BaseEntityInstanceObject {
    
    private final ItemAccountingCategory itemAccountingCategory; // Always Present
    
    public ItemAccountingCategoryObject(ItemAccountingCategory itemAccountingCategory) {
        super(itemAccountingCategory.getPrimaryKey());
        
        this.itemAccountingCategory = itemAccountingCategory;
    }

    private ItemAccountingCategoryDetail itemAccountingCategoryDetail; // Optional, use getItemAccountingCategoryDetail()
    
    private ItemAccountingCategoryDetail getItemAccountingCategoryDetail() {
        if(itemAccountingCategoryDetail == null) {
            itemAccountingCategoryDetail = itemAccountingCategory.getLastDetail();
        }
        
        return itemAccountingCategoryDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("item accounting category name")
    @GraphQLNonNull
    public String getItemAccountingCategoryName() {
        return getItemAccountingCategoryDetail().getItemAccountingCategoryName();
    }

    @GraphQLField
    @GraphQLDescription("parent item accounting category")
    public ItemAccountingCategoryObject getParentItemAccountingCategory() {
        var parentItemAccountingCategory = getItemAccountingCategoryDetail().getParentItemAccountingCategory();
        
        return parentItemAccountingCategory == null ? null : new ItemAccountingCategoryObject(parentItemAccountingCategory);
    }

    @GraphQLField
    @GraphQLDescription("inventory GL account")
    public GlAccountObject getInventoryGlAccount(final DataFetchingEnvironment env) {
        var inventoryGlAccount = getItemAccountingCategoryDetail().getInventoryGlAccount();

        return inventoryGlAccount == null ? null : AccountingSecurityUtils.getHasGlAccountAccess(env) ?
                new GlAccountObject(inventoryGlAccount) : null;
    }

    @GraphQLField
    @GraphQLDescription("sales GL account")
    public GlAccountObject getSalesGlAccount(final DataFetchingEnvironment env) {
        var salesGlAccount = getItemAccountingCategoryDetail().getSalesGlAccount();

        return salesGlAccount == null ? null : AccountingSecurityUtils.getHasGlAccountAccess(env) ?
                new GlAccountObject(salesGlAccount) : null;
    }

    @GraphQLField
    @GraphQLDescription("returns GL account")
    public GlAccountObject getReturnsGlAccount(final DataFetchingEnvironment env) {
        var returnsGlAccount = getItemAccountingCategoryDetail().getReturnsGlAccount();

        return returnsGlAccount == null ? null : AccountingSecurityUtils.getHasGlAccountAccess(env) ?
                new GlAccountObject(returnsGlAccount) : null;
    }

    @GraphQLField
    @GraphQLDescription("COGS GL account")
    public GlAccountObject getCogsGlAccount(final DataFetchingEnvironment env) {
        var cogsGlAccount = getItemAccountingCategoryDetail().getCogsGlAccount();

        return cogsGlAccount == null ? null : AccountingSecurityUtils.getHasGlAccountAccess(env) ?
                new GlAccountObject(cogsGlAccount) : null;
    }

    @GraphQLField
    @GraphQLDescription("returns COGS GL account")
    public GlAccountObject getReturnsCogsGlAccount(final DataFetchingEnvironment env) {
        var returnsCogsGlAccount = getItemAccountingCategoryDetail().getReturnsCogsGlAccount();

        return returnsCogsGlAccount == null ? null : AccountingSecurityUtils.getHasGlAccountAccess(env) ?
                new GlAccountObject(returnsCogsGlAccount) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getItemAccountingCategoryDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getItemAccountingCategoryDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return accountingControl.getBestItemAccountingCategoryDescription(itemAccountingCategory, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("items")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ItemObject> getItems(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getHasItemsAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);
            var totalCount = itemControl.countItemsByItemAccountingCategory(itemAccountingCategory);

            try(var objectLimiter = new ObjectLimiter(env, ItemConstants.COMPONENT_VENDOR_NAME, ItemConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = itemControl.getItemsByItemAccountingCategory(itemAccountingCategory);
                var items = entities.stream().map(ItemObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, items);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
