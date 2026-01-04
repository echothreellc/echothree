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
import com.echothree.model.data.item.server.entity.RelatedItem;
import com.echothree.model.data.item.server.entity.RelatedItemDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("related item object")
@GraphQLName("RelatedItem")
public class RelatedItemObject
        extends BaseEntityInstanceObject {

    private final RelatedItem relatedItem; // Always Present

    public RelatedItemObject(RelatedItem relatedItem) {
        super(relatedItem.getPrimaryKey());
        
        this.relatedItem = relatedItem;
    }

    private RelatedItemDetail relatedItemDetail; // Optional, use getRelatedItemTypeDetail()
    
    private RelatedItemDetail getRelatedItemDetail() {
        if(relatedItemDetail == null) {
            relatedItemDetail = relatedItem.getLastDetail();
        }
        
        return relatedItemDetail;
    }

    @GraphQLField
    @GraphQLDescription("related item type")
    @GraphQLNonNull
    public RelatedItemTypeObject getRelatedItemType(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemAliasTypeAccess(env) ? new RelatedItemTypeObject(getRelatedItemDetail().getRelatedItemType(), null) : null;
    }

    @GraphQLField
    @GraphQLDescription("from item")
    public ItemObject getFromItem(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemAccess(env) ? new ItemObject(getRelatedItemDetail().getFromItem()) : null;
    }

    @GraphQLField
    @GraphQLDescription("toitem")
    public ItemObject getToItem(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemAccess(env) ? new ItemObject(getRelatedItemDetail().getToItem()) : null;
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getRelatedItemDetail().getSortOrder();
    }
    
}
