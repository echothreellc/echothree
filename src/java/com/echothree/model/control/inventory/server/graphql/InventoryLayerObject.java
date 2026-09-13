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

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.UnitCostObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.inventory.server.control.BucketControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.inventory.common.InventoryLayerBucketConstants;
import com.echothree.model.data.inventory.server.entity.InventoryLayer;
import com.echothree.model.data.inventory.server.entity.InventoryLayerDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("inventory layer object")
@GraphQLName("InventoryLayer")
public class InventoryLayerObject
        extends BaseEntityInstanceObject {

    private final InventoryLayer inventoryLayer; // Always Present

    public InventoryLayerObject(final InventoryLayer inventoryLayer) {
        super(inventoryLayer.getPrimaryKey());

        this.inventoryLayer = inventoryLayer;
    }

    private InventoryLayerDetail inventoryLayerDetail; // Optional, use getInventoryLayerDetail()

    private InventoryLayerDetail getInventoryLayerDetail() {
        if(inventoryLayerDetail == null) {
            inventoryLayerDetail = inventoryLayer.getLastDetail();
        }

        return inventoryLayerDetail;
    }

    @GraphQLField
    @GraphQLDescription("inventory costing pool")
    public InventoryCostingPoolObject getInventoryCostingPool(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getHasInventoryCostingPoolAccess(env)
                ? new InventoryCostingPoolObject(getInventoryLayerDetail().getInventoryCostingPool()) : null;
    }

    @GraphQLField
    @GraphQLDescription("inventory layer sequence")
    @GraphQLNonNull
    public Integer getInventoryLayerSequence() {
        return getInventoryLayerDetail().getInventoryLayerSequence();
    }

    // TODO: Add the InventoryTransactionLineObject relationship when transaction-line GraphQL objects are implemented.

    @GraphQLField
    @GraphQLDescription("receipt quantity")
    @GraphQLNonNull
    public Long getReceiptQuantity() {
        return getInventoryLayerDetail().getReceiptQuantity();
    }

    @GraphQLField
    @GraphQLDescription("unit cost")
    public UnitCostObject getUnitCost() {
        var unitCost = getInventoryLayerDetail().getUnitCost();
        var partyControl = Session.getModelController(PartyControl.class);

        return unitCost == null ? null : new UnitCostObject(partyControl.getPreferredCurrency(
                getInventoryLayerDetail().getInventoryCostingPool().getLastDetail().getCompanyParty()), unitCost);
    }

    @GraphQLField
    @GraphQLDescription("description")
    public String getDescription() {
        return getInventoryLayerDetail().getDescription();
    }

    @GraphQLField
    @GraphQLDescription("inventory layer buckets")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<InventoryLayerBucketObject> getInventoryLayerBuckets(final DataFetchingEnvironment env) {
        if(InventorySecurityUtils.getHasInventoryLayerBucketsAccess(env)) {
            var bucketControl = Session.getModelController(BucketControl.class);
            var totalCount = bucketControl.countInventoryLayerBucketsByInventoryLayer(inventoryLayer);

            try(var objectLimiter = new ObjectLimiter(env, InventoryLayerBucketConstants.COMPONENT_VENDOR_NAME,
                    InventoryLayerBucketConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = bucketControl.getInventoryLayerBucketsByInventoryLayer(inventoryLayer);
                var inventoryLayerBuckets = entities.stream()
                        .map(InventoryLayerBucketObject::new)
                        .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, inventoryLayerBuckets);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
