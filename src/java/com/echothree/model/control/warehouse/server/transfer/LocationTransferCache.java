// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.model.control.warehouse.server.transfer;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.inventory.common.transfer.InventoryLocationGroupTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.warehouse.common.WarehouseOptions;
import com.echothree.model.control.warehouse.common.transfer.LocationTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationTypeTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationUseTypeTransfer;
import com.echothree.model.control.warehouse.common.transfer.WarehouseTransfer;
import com.echothree.model.control.warehouse.common.workflow.LocationStatusConstants;
import com.echothree.model.control.warehouse.server.control.LocationUseTypeControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.Location;
import com.echothree.model.data.warehouse.server.entity.LocationDetail;
import com.echothree.model.data.warehouse.server.entity.LocationVolume;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class LocationTransferCache
        extends BaseWarehouseTransferCache<Location, LocationTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    InventoryControl inventoryControl = Session.getModelController(InventoryControl.class);
    LocationUseTypeControl locationUseTypeControl = Session.getModelController(LocationUseTypeControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);

    boolean includeCapacities;
    boolean includeVolume;
    
    /** Creates a new instance of LocationTransferCache */
    public LocationTransferCache(UserVisit userVisit, WarehouseControl warehouseControl) {
        super(userVisit, warehouseControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeCapacities = options.contains(WarehouseOptions.LocationIncludeCapacities);
            includeVolume = options.contains(WarehouseOptions.LocationIncludeVolume);
            setIncludeEntityAttributeGroups(options.contains(WarehouseOptions.LocationIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(WarehouseOptions.LocationIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }
    
    public LocationTransfer getLocationTransfer(Location location) {
        LocationTransfer locationTransfer = get(location);
        
        if(locationTransfer == null) {
            LocationDetail locationDetail = location.getLastDetail();
            Warehouse warehouse = warehouseControl.getWarehouse(locationDetail.getWarehouseParty());
            WarehouseTransfer warehouseTransfer = warehouseControl.getWarehouseTransfer(userVisit, warehouse);
            String locationName = locationDetail.getLocationName();
            LocationTypeTransfer locationTypeTransfer = warehouseControl.getLocationTypeTransfer(userVisit, locationDetail.getLocationType());
            LocationUseTypeTransfer locationUseTypeTransfer = locationUseTypeControl.getLocationUseTypeTransfer(userVisit, locationDetail.getLocationUseType());
            Integer velocity = locationDetail.getVelocity();
            InventoryLocationGroupTransfer inventoryLocationGroup = inventoryControl.getInventoryLocationGroupTransfer(userVisit, locationDetail.getInventoryLocationGroup());
            String description = warehouseControl.getBestLocationDescription(location, getLanguage());
            
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(location.getPrimaryKey());
            WorkflowEntityStatusTransfer locationStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    LocationStatusConstants.Workflow_LOCATION_STATUS, entityInstance);
            
            locationTransfer = new LocationTransfer(warehouseTransfer, locationName, locationTypeTransfer, locationUseTypeTransfer,
                    velocity, inventoryLocationGroup, description, locationStatusTransfer);
            put(location, locationTransfer);
            
            if(includeCapacities) {
                locationTransfer.setLocationCapacities(new ListWrapper<>(warehouseControl.getLocationCapacityTransfersByLocation(userVisit, location)));
            }
            
            if(includeVolume) {
                LocationVolume locationVolume = warehouseControl.getLocationVolume(location);
                
                if(locationVolume != null) {
                    locationTransfer.setLocationVolume(warehouseControl.getLocationVolumeTransfer(userVisit, locationVolume));
                }
            }
        }
        
        return locationTransfer;
    }
    
}
