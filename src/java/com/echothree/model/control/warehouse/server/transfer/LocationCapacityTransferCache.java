// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.warehouse.common.transfer.LocationCapacityTransfer;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.LocationCapacity;
import com.echothree.util.server.persistence.Session;

public class LocationCapacityTransferCache
        extends BaseWarehouseTransferCache<LocationCapacity, LocationCapacityTransfer> {
    
    UomControl uomControl;
    
    /** Creates a new instance of LocationCapacityTransferCache */
    public LocationCapacityTransferCache(WarehouseControl warehouseControl) {
        super(warehouseControl);
        
        uomControl = Session.getModelController(UomControl.class);
    }
    
    public LocationCapacityTransfer getLocationCapacityTransfer(UserVisit userVisit, LocationCapacity locationCapacity) {
        var locationCapacityTransfer = get(locationCapacity);
        
        if(locationCapacityTransfer == null) {
            var locationTransferCache = warehouseControl.getWarehouseTransferCaches().getLocationTransferCache();
            var locationTransfer = locationTransferCache.getLocationTransfer(userVisit, locationCapacity.getLocation());
            var unitOfMeasureTypeTransferCache = uomControl.getUomTransferCaches().getUnitOfMeasureTypeTransferCache();
            var unitOfMeasureType = locationCapacity.getUnitOfMeasureType();
            var unitOfMeasureTypeTransfer = unitOfMeasureTypeTransferCache.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureType);
            var capacity = locationCapacity.getCapacity();
            
            locationCapacityTransfer = new LocationCapacityTransfer(locationTransfer, unitOfMeasureTypeTransfer, capacity);
            put(userVisit, locationCapacity, locationCapacityTransfer);
        }
        
        return locationCapacityTransfer;
    }
    
}
