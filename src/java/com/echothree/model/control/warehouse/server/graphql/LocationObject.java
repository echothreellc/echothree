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

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.inventory.server.control.InventoryLocationControl;
import com.echothree.model.control.inventory.server.graphql.InventoryLocationGroupObject;
import com.echothree.model.control.inventory.server.graphql.InventoryLocationObject;
import com.echothree.model.control.inventory.server.graphql.InventorySecurityUtils;
import com.echothree.model.control.item.common.workflow.ItemStatusConstants;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.warehouse.common.workflow.LocationStatusConstants;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.inventory.common.InventoryLocationConstants;
import com.echothree.model.data.warehouse.server.entity.Location;
import com.echothree.model.data.warehouse.server.entity.LocationDetail;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.stream.Collectors;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("location type object")
@GraphQLName("Location")
@SuppressWarnings("StringConcatToTextBlock")
public class LocationObject
        extends BaseEntityInstanceObject {
    
    private final Location location; // Always Present
    
    public LocationObject(Location location) {
        super(location.getPrimaryKey());
        
        this.location = location;
    }

    private LocationDetail locationDetail; // Optional, use getLocationDetail()
    
    private LocationDetail getLocationDetail() {
        if(locationDetail == null) {
            locationDetail = location.getLastDetail();
        }
        
        return locationDetail;
    }

    @GraphQLField
    @GraphQLDescription("warehouse")
    public WarehouseObject getWarehouse(final DataFetchingEnvironment env) {
        return WarehouseSecurityUtils.getHasWarehouseAccess(env) ?
                new WarehouseObject(getLocationDetail().getWarehouseParty()) : null;
    }

    @GraphQLField
    @GraphQLDescription("location name")
    @GraphQLNonNull
    public String getLocationName() {
        return getLocationDetail().getLocationName();
    }

    @GraphQLField
    @GraphQLDescription("location type")
    public LocationTypeObject getLocationType(final DataFetchingEnvironment env) {
        return WarehouseSecurityUtils.getHasLocationTypeAccess(env) ?
                new LocationTypeObject(getLocationDetail().getLocationType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("location use type")
    public LocationUseTypeObject getLocationUseType(final DataFetchingEnvironment env) {
        return WarehouseSecurityUtils.getHasLocationUseTypeAccess(env) ?
                new LocationUseTypeObject(getLocationDetail().getLocationUseType()) : null;
    }

    @GraphQLField
    @GraphQLDescription("velocity")
    @GraphQLNonNull
    public int getVelocity() {
        return getLocationDetail().getVelocity();
    }

    @GraphQLField
    @GraphQLDescription("inventory location group")
    public InventoryLocationGroupObject getInventoryLocationGroup(final DataFetchingEnvironment env) {
        return InventorySecurityUtils.getHasInventoryLocationGroupAccess(env) ?
                new InventoryLocationGroupObject(getLocationDetail().getInventoryLocationGroup()) : null;
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return warehouseControl.getBestLocationDescription(location, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("location status")
    public WorkflowEntityStatusObject getLocationStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, LocationStatusConstants.Workflow_LOCATION_STATUS);
    }

    @GraphQLField
    @GraphQLDescription("inventory locations")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<InventoryLocationObject> getInventoryLocations(final DataFetchingEnvironment env) {
        if(InventorySecurityUtils.getHasInventoryLocationsAccess(env)) {
            var inventoryLocationControl = Session.getModelController(InventoryLocationControl.class);
            var totalCount = inventoryLocationControl.countInventoryLocationsByLocation(location);

            try(var objectLimiter = new ObjectLimiter(env, InventoryLocationConstants.COMPONENT_VENDOR_NAME,
                    InventoryLocationConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = inventoryLocationControl.getInventoryLocationsByLocation(location);
                var inventoryLocations = entities.stream()
                        .map(InventoryLocationObject::new)
                        .collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, inventoryLocations);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
