// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.control.party.server.graphql.CompanyObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDetail;
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
    
    @GraphQLField
    @GraphQLDescription("item name")
    @GraphQLNonNull
    public String getItemName() {
        return getItemDetail().getItemName();
    }

    @GraphQLField
    @GraphQLDescription("item category")
    public ItemCategoryObject getItemCategory(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getInstance().getHasItemCategoryAccess(env) ? new ItemCategoryObject(getItemDetail().getItemCategory()) : null;
    }

    @GraphQLField
    @GraphQLDescription("company")
    @GraphQLNonNull
    public CompanyObject getCompany(final DataFetchingEnvironment env) {
        var companyParty = getItemDetail().getCompanyParty();

        return PartySecurityUtils.getInstance().getHasPartyAccess(env, companyParty) ? new CompanyObject(companyParty) : null;
    }

    @GraphQLField
    @GraphQLDescription("item price type")
    @GraphQLNonNull
    public ItemPriceTypeObject getItemPriceType(final DataFetchingEnvironment env) {
        return new ItemPriceTypeObject(getItemDetail().getItemPriceType());
    }
    
}
