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

package com.echothree.model.control.inventory.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.item.server.graphql.ItemSecurityUtils;
import com.echothree.model.control.party.server.graphql.PartyObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureTypeObject;
import com.echothree.model.control.uom.server.graphql.UomSecurityUtils;
import com.echothree.model.control.warehouse.server.graphql.LocationObject;
import com.echothree.model.control.warehouse.server.graphql.WarehouseSecurityUtils;
import com.echothree.model.data.inventory.server.entity.InventoryLocation;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("inventory location object")
@GraphQLName("InventoryLocation")
public class InventoryLocationObject
        extends BaseObject {

    private final InventoryLocation inventoryLocation;

    public InventoryLocationObject(final InventoryLocation inventoryLocation) {
        this.inventoryLocation = inventoryLocation;
    }

    @GraphQLField
    @GraphQLDescription("location")
    public LocationObject getLocation(final DataFetchingEnvironment env) {
        return WarehouseSecurityUtils.getHasLocationAccess(env)
                ? new LocationObject(inventoryLocation.getLocation()) : null;
    }

    @GraphQLField
    @GraphQLDescription("owner party")
    public PartyObject getOwnerParty(final DataFetchingEnvironment env) {
        var ownerParty = inventoryLocation.getOwnerParty();

        return PartySecurityUtils.getHasPartyAccess(env, ownerParty) ? new PartyObject(ownerParty) : null;
    }

    @GraphQLField
    @GraphQLDescription("item")
    public ItemObject getItem(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemAccess(env) ? new ItemObject(inventoryLocation.getItem()) : null;
    }

    @GraphQLField
    @GraphQLDescription("unit of measure type")
    public UnitOfMeasureTypeObject getUnitOfMeasureType(final DataFetchingEnvironment env) {
        return UomSecurityUtils.getHasUnitOfMeasureTypeAccess(env)
                ? new UnitOfMeasureTypeObject(inventoryLocation.getUnitOfMeasureType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("inventory condition")
    public InventoryConditionObject getInventoryCondition(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getHasInventoryConditionAccess(env)
                ? new InventoryConditionObject(inventoryLocation.getInventoryCondition()) : null;
    }

}
