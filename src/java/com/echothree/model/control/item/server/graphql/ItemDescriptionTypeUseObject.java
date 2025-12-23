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

import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUse;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("item description type use object")
@GraphQLName("ItemDescriptionTypeUse")
public class ItemDescriptionTypeUseObject
        implements BaseGraphQl {
    
    private final ItemDescriptionTypeUse itemDescriptionTypeUse; // Always Present
    
    public ItemDescriptionTypeUseObject(ItemDescriptionTypeUse itemDescriptionTypeUse) {
        this.itemDescriptionTypeUse = itemDescriptionTypeUse;
    }

    @GraphQLField
    @GraphQLDescription("item description type")
    public ItemDescriptionTypeObject getItemDescriptionType(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemDescriptionTypeAccess(env) ? new ItemDescriptionTypeObject(itemDescriptionTypeUse.getItemDescriptionType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("item description type use type")
    public ItemDescriptionTypeUseTypeObject getItemDescriptionTypeUseType(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemDescriptionTypeUseTypeAccess(env) ? new ItemDescriptionTypeUseTypeObject(itemDescriptionTypeUse.getItemDescriptionTypeUseType()) : null;
    }

}
