// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.item.server.command.GetItemCategoryCommand;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDetail;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("item object")
@GraphQLName("Item")
public class ItemObject
        extends BaseEntityInstanceObject {
    
    private final Item item; // Always Present
    
    public ItemObject(Item item) {
        super(item.getPrimaryKey());

        this.item = item;
    }

    private ItemDetail itemDetail; // Optional, use getItemDetail()
    
    private ItemDetail getItemDetail() {
        if(itemDetail == null) {
            itemDetail = item.getLastDetail();
        }
        
        return itemDetail;
    }
    
    private Boolean hasItemCategoryAccess;
    
    private boolean getHasItemCategoryAccess(final DataFetchingEnvironment env) {
        if(hasItemCategoryAccess == null) {
            GraphQlContext context = env.getContext();
            BaseSingleEntityCommand baseSingleEntityCommand = new GetItemCategoryCommand(context.getUserVisitPK(), null);
            
            baseSingleEntityCommand.security();
            
            hasItemCategoryAccess = !baseSingleEntityCommand.hasSecurityMessages();
        }
        
        return hasItemCategoryAccess;
    }
        
    @GraphQLField
    @GraphQLDescription("item name")
    @GraphQLNonNull
    public String getItemName() {
        return getItemDetail().getItemName();
    }

    @GraphQLField
    @GraphQLDescription("item category")
    public ItemCategoryObject getItemCategory(final DataFetchingEnvironment env) {
        return getHasItemCategoryAccess(env) ? new ItemCategoryObject(getItemDetail().getItemCategory()) : null;
    }

}
