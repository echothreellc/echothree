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

package com.echothree.model.control.warehouse.server.graphql;

import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.inventory.server.graphql.InventoryLocationGroupObject;
import com.echothree.model.control.inventory.server.graphql.InventorySecurityUtils;
import com.echothree.model.control.party.server.graphql.BasePartyObject;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.vendor.server.graphql.VendorItemObject;
import com.echothree.model.control.vendor.server.graphql.VendorSecurityUtils;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.inventory.common.InventoryLocationGroupConstants;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.vendor.common.VendorItemConstants;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("warehouse object")
@GraphQLName("Warehouse")
public class WarehouseObject
        extends BasePartyObject {

    public WarehouseObject(Party party) {
        super(party);
    }

    public WarehouseObject(Warehouse warehouse) {
        super(warehouse.getParty());

        this.warehouse = warehouse;
    }

    private Warehouse warehouse;  // Optional, use getWarehouse()

    protected Warehouse getWarehouse() {
        if(warehouse == null) {
            var warehouseControl = Session.getModelController(WarehouseControl.class);

            warehouse = warehouseControl.getWarehouse(party);
        }

        return warehouse;
    }

    @GraphQLField
    @GraphQLDescription("warehouse name")
    @GraphQLNonNull
    public String getWarehouseName() {
        return getWarehouse().getWarehouseName();
    }

    @GraphQLField
    @GraphQLDescription("warehouse type")
    public WarehouseTypeObject getWarehouseType(final DataFetchingEnvironment env) {
        return WarehouseSecurityUtils.getHasWarehouseTypeAccess(env) ?
                new WarehouseTypeObject(getWarehouse().getWarehouseType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getWarehouse().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getWarehouse().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("inventory location groups")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<InventoryLocationGroupObject> getInventoryLocationGroups(final DataFetchingEnvironment env) {
        if(InventorySecurityUtils.getHasInventoryLocationGroupsAccess(env)) {
            var inventoryControl = Session.getModelController(InventoryControl.class);
            var warehouseParty = getWarehouse().getParty();
            var totalCount = inventoryControl.countInventoryLocationGroupsByWarehouseParty(warehouseParty);

            try(var objectLimiter = new ObjectLimiter(env, InventoryLocationGroupConstants.COMPONENT_VENDOR_NAME, InventoryLocationGroupConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = inventoryControl.getInventoryLocationGroupsByWarehouseParty(warehouseParty);
                var items = entities.stream().map(InventoryLocationGroupObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, items);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
