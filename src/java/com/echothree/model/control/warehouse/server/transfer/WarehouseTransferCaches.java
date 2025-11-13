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

import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class WarehouseTransferCaches
        extends BaseTransferCaches {
    
    protected WarehouseControl warehouseControl;
    
    LocationDescriptionTransferCache locationDescriptionTransferCache;
    LocationNameElementDescriptionTransferCache locationNameElementDescriptionTransferCache;
    LocationNameElementTransferCache locationNameElementTransferCache;
    LocationTransferCache locationTransferCache;
    LocationTypeDescriptionTransferCache locationTypeDescriptionTransferCache;
    LocationTypeTransferCache locationTypeTransferCache;
    LocationUseTypeTransferCache locationUseTypeTransferCache;
    WarehouseTransferCache warehouseTransferCache;
    LocationVolumeTransferCache locationVolumeTransferCache;
    LocationCapacityTransferCache locationCapacityTransferCache;
    WarehouseTypeTransferCache warehouseTypeTransferCache;
    WarehouseTypeDescriptionTransferCache warehouseTypeDescriptionTransferCache;
    
    /** Creates a new instance of WarehouseTransferCaches */
    public WarehouseTransferCaches(WarehouseControl warehouseControl) {
        super();
        
        this.warehouseControl = warehouseControl;
    }
    
    public LocationDescriptionTransferCache getLocationDescriptionTransferCache() {
        if(locationDescriptionTransferCache == null)
            locationDescriptionTransferCache = new LocationDescriptionTransferCache(warehouseControl);
        
        return locationDescriptionTransferCache;
    }
    
    public LocationNameElementDescriptionTransferCache getLocationNameElementDescriptionTransferCache() {
        if(locationNameElementDescriptionTransferCache == null)
            locationNameElementDescriptionTransferCache = new LocationNameElementDescriptionTransferCache(warehouseControl);
        
        return locationNameElementDescriptionTransferCache;
    }
    
    public LocationNameElementTransferCache getLocationNameElementTransferCache() {
        if(locationNameElementTransferCache == null)
            locationNameElementTransferCache = new LocationNameElementTransferCache(warehouseControl);
        
        return locationNameElementTransferCache;
    }
    
    public LocationTransferCache getLocationTransferCache() {
        if(locationTransferCache == null)
            locationTransferCache = new LocationTransferCache(warehouseControl);
        
        return locationTransferCache;
    }
    
    public LocationTypeDescriptionTransferCache getLocationTypeDescriptionTransferCache() {
        if(locationTypeDescriptionTransferCache == null)
            locationTypeDescriptionTransferCache = new LocationTypeDescriptionTransferCache(warehouseControl);
        
        return locationTypeDescriptionTransferCache;
    }
    
    public LocationTypeTransferCache getLocationTypeTransferCache() {
        if(locationTypeTransferCache == null)
            locationTypeTransferCache = new LocationTypeTransferCache(warehouseControl);
        
        return locationTypeTransferCache;
    }
    
    public LocationUseTypeTransferCache getLocationUseTypeTransferCache() {
        if(locationUseTypeTransferCache == null)
            locationUseTypeTransferCache = new LocationUseTypeTransferCache(warehouseControl);
        
        return locationUseTypeTransferCache;
    }
    
    public WarehouseTransferCache getWarehouseTransferCache() {
        if(warehouseTransferCache == null)
            warehouseTransferCache = new WarehouseTransferCache(warehouseControl);
        
        return warehouseTransferCache;
    }
    
    public LocationVolumeTransferCache getLocationVolumeTransferCache() {
        if(locationVolumeTransferCache == null)
            locationVolumeTransferCache = new LocationVolumeTransferCache(warehouseControl);
        
        return locationVolumeTransferCache;
    }
    
    public LocationCapacityTransferCache getLocationCapacityTransferCache() {
        if(locationCapacityTransferCache == null)
            locationCapacityTransferCache = new LocationCapacityTransferCache(warehouseControl);
        
        return locationCapacityTransferCache;
    }

    public WarehouseTypeTransferCache getWarehouseTypeTransferCache() {
        if(warehouseTypeTransferCache == null)
            warehouseTypeTransferCache = new WarehouseTypeTransferCache(warehouseControl);

        return warehouseTypeTransferCache;
    }

    public WarehouseTypeDescriptionTransferCache getWarehouseTypeDescriptionTransferCache() {
        if(warehouseTypeDescriptionTransferCache == null)
            warehouseTypeDescriptionTransferCache = new WarehouseTypeDescriptionTransferCache(warehouseControl);

        return warehouseTypeDescriptionTransferCache;
    }

}
