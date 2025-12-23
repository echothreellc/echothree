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

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureTypeObject;
import com.echothree.model.control.uom.server.graphql.UomSecurityUtils;
import com.echothree.model.data.item.server.entity.ItemAlias;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("item alias object")
@GraphQLName("ItemAlias")
public class ItemAliasObject
        extends BaseObject {
    
    private final ItemAlias itemAlias; // Always Present
    
    public ItemAliasObject(ItemAlias itemAlias) {
        this.itemAlias = itemAlias;
    }

    @GraphQLField
    @GraphQLDescription("item")
    public ItemObject getItem(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemAccess(env) ? new ItemObject(itemAlias.getItem()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unit of measure type")
    public UnitOfMeasureTypeObject getUnitOfMeasureType(final DataFetchingEnvironment env) {
        return UomSecurityUtils.getHasUnitOfMeasureTypeAccess(env) ? new UnitOfMeasureTypeObject(itemAlias.getUnitOfMeasureType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("item alias type")
    public ItemAliasTypeObject getItemAliasType(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemAliasTypeAccess(env) ? new ItemAliasTypeObject(itemAlias.getItemAliasType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("alias")
    @GraphQLNonNull
    public String setAlias() {
        return itemAlias.getAlias();
    }

}
