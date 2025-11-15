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

import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.warehouse.common.transfer.LocationVolumeTransfer;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.LocationVolume;
import com.echothree.util.server.persistence.Session;

public class LocationVolumeTransferCache
        extends BaseWarehouseTransferCache<LocationVolume, LocationVolumeTransfer> {
    
    UomControl uomControl = Session.getModelController(UomControl.class);
    WarehouseControl warehouseControl = Session.getModelController(WarehouseControl.class);

    /** Creates a new instance of LocationVolumeTransferCache */
    public LocationVolumeTransferCache() {
        super();
    }
    
    public LocationVolumeTransfer getLocationVolumeTransfer(UserVisit userVisit, LocationVolume locationVolume) {
        var locationVolumeTransfer = get(locationVolume);
        
        if(locationVolumeTransfer == null) {
            var locationTransfer = warehouseControl.getLocationTransfer(userVisit, locationVolume.getLocation());
            var volumeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_VOLUME);
            var height = formatUnitOfMeasure(userVisit, volumeUnitOfMeasureKind, locationVolume.getHeight());
            var width = formatUnitOfMeasure(userVisit, volumeUnitOfMeasureKind, locationVolume.getWidth());
            var depth = formatUnitOfMeasure(userVisit, volumeUnitOfMeasureKind, locationVolume.getDepth());
            Long cubicVolume = locationVolume.getHeight() * locationVolume.getWidth()
                    * locationVolume.getDepth();
            
            locationVolumeTransfer = new LocationVolumeTransfer(locationTransfer, height, width, depth, cubicVolume);
            put(userVisit, locationVolume, locationVolumeTransfer);
        }
        
        return locationVolumeTransfer;
    }
    
}
