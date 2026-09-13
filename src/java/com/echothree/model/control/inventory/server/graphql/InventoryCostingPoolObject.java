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

import java.util.stream.Collectors;
import java.util.ArrayList;
import graphql.annotations.connection.GraphQLConnection;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import com.echothree.util.server.persistence.Session;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.data.inventory.common.InventoryLayerConstants;
import com.echothree.model.control.inventory.server.control.InventoryLayerControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.item.server.graphql.ItemSecurityUtils;
import com.echothree.model.control.party.server.graphql.CompanyObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.data.inventory.server.entity.InventoryCostingPool;
import com.echothree.model.data.inventory.server.entity.InventoryCostingPoolDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("inventory costing pool object")
@GraphQLName("InventoryCostingPool")
public class InventoryCostingPoolObject
        extends BaseEntityInstanceObject {

    private final InventoryCostingPool inventoryCostingPool; // Always Present

    public InventoryCostingPoolObject(final InventoryCostingPool inventoryCostingPool) {
        super(inventoryCostingPool.getPrimaryKey());

        this.inventoryCostingPool = inventoryCostingPool;
    }

    private InventoryCostingPoolDetail inventoryCostingPoolDetail; // Optional, use getInventoryCostingPoolDetail()

    private InventoryCostingPoolDetail getInventoryCostingPoolDetail() {
        if(inventoryCostingPoolDetail == null) {
            inventoryCostingPoolDetail = inventoryCostingPool.getLastDetail();
        }

        return inventoryCostingPoolDetail;
    }

    @GraphQLField
    @GraphQLDescription("company")
    public CompanyObject getCompany(final DataFetchingEnvironment env) {
        var party = getInventoryCostingPoolDetail().getCompanyParty();

        return PartySecurityUtils.getHasPartyAccess(env, party) ? new CompanyObject(party) : null;
    }

    @GraphQLField
    @GraphQLDescription("item")
    public ItemObject getItem(final DataFetchingEnvironment env) {
        return ItemSecurityUtils.getHasItemAccess(env) ? new ItemObject(getInventoryCostingPoolDetail().getItem()) : null;
    }

    @GraphQLField
    @GraphQLDescription("inventory condition")
    public InventoryConditionObject getInventoryCondition(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getHasInventoryConditionAccess(env)
                ? new InventoryConditionObject(getInventoryCostingPoolDetail().getInventoryCondition()) : null;
    }

    @GraphQLField
    @GraphQLDescription("inventory layers")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<InventoryLayerObject> getInventoryLayers(final DataFetchingEnvironment env) {
        if(InventorySecurityUtils.getHasInventoryLayersAccess(env)) {
            var inventoryLayerControl = Session.getModelController(InventoryLayerControl.class);
            var totalCount = inventoryLayerControl.countInventoryLayersByInventoryCostingPool(inventoryCostingPool);

            try(var objectLimiter = new ObjectLimiter(env, InventoryLayerConstants.COMPONENT_VENDOR_NAME, InventoryLayerConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = inventoryLayerControl.getInventoryLayersByInventoryCostingPool(inventoryCostingPool);
                var inventoryLayers = entities.stream()
                        .map(InventoryLayerObject::new)
                        .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, inventoryLayers);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
