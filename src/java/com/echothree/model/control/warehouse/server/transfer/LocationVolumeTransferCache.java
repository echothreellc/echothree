// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.warehouse.common.transfer.LocationTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationVolumeTransfer;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.LocationVolume;
import com.echothree.util.server.persistence.Session;

public class LocationVolumeTransferCache
        extends BaseWarehouseTransferCache<LocationVolume, LocationVolumeTransfer> {
    
    UomControl uomControl;
    
    /** Creates a new instance of LocationVolumeTransferCache */
    public LocationVolumeTransferCache(UserVisit userVisit, WarehouseControl warehouseControl) {
        super(userVisit, warehouseControl);
        
        uomControl = Session.getModelController(UomControl.class);
    }
    
    public LocationVolumeTransfer getLocationVolumeTransfer(LocationVolume locationVolume) {
        LocationVolumeTransfer locationVolumeTransfer = get(locationVolume);
        
        if(locationVolumeTransfer == null) {
            LocationTransfer locationTransfer = warehouseControl.getLocationTransfer(userVisit, locationVolume.getLocation());
            UnitOfMeasureKind volumeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_VOLUME);
            String height = formatUnitOfMeasure(volumeUnitOfMeasureKind, locationVolume.getHeight());
            String width = formatUnitOfMeasure(volumeUnitOfMeasureKind, locationVolume.getWidth());
            String depth = formatUnitOfMeasure(volumeUnitOfMeasureKind, locationVolume.getDepth());
            Long cubicVolume = locationVolume.getHeight() * locationVolume.getWidth()
                    * locationVolume.getDepth();
            
            locationVolumeTransfer = new LocationVolumeTransfer(locationTransfer, height, width, depth, cubicVolume);
            put(locationVolume, locationVolumeTransfer);
        }
        
        return locationVolumeTransfer;
    }
    
}
