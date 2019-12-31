// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.item.server.entity.ItemPriceType;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("item price type object")
@GraphQLName("ItemPriceType")
public class ItemPriceTypeObject
        extends BaseEntityInstanceObject {
    
    private final ItemPriceType itemPriceType; // Always Present
    
    public ItemPriceTypeObject(ItemPriceType itemPriceType) {
        super(itemPriceType.getPrimaryKey());
        
        this.itemPriceType = itemPriceType;
    }

    @GraphQLField
    @GraphQLDescription("item price type name")
    @GraphQLNonNull
    public String getItemPriceTypeName() {
        return itemPriceType.getItemPriceTypeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return itemPriceType.getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return itemPriceType.getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return itemControl.getBestItemPriceTypeDescription(itemPriceType, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }
    
}
