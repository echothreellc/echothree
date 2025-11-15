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

import com.echothree.util.server.transfer.BaseTransferCaches;

public class WarehouseTransferCaches
        extends BaseTransferCaches {
    
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
    public WarehouseTransferCaches() {
        super();
    }
    
    public LocationDescriptionTransferCache getLocationDescriptionTransferCache() {
        if(locationDescriptionTransferCache == null)
            locationDescriptionTransferCache = new LocationDescriptionTransferCache();
        
        return locationDescriptionTransferCache;
    }
    
    public LocationNameElementDescriptionTransferCache getLocationNameElementDescriptionTransferCache() {
        if(locationNameElementDescriptionTransferCache == null)
            locationNameElementDescriptionTransferCache = new LocationNameElementDescriptionTransferCache();
        
        return locationNameElementDescriptionTransferCache;
    }
    
    public LocationNameElementTransferCache getLocationNameElementTransferCache() {
        if(locationNameElementTransferCache == null)
            locationNameElementTransferCache = new LocationNameElementTransferCache();
        
        return locationNameElementTransferCache;
    }
    
    public LocationTransferCache getLocationTransferCache() {
        if(locationTransferCache == null)
            locationTransferCache = new LocationTransferCache();
        
        return locationTransferCache;
    }
    
    public LocationTypeDescriptionTransferCache getLocationTypeDescriptionTransferCache() {
        if(locationTypeDescriptionTransferCache == null)
            locationTypeDescriptionTransferCache = new LocationTypeDescriptionTransferCache();
        
        return locationTypeDescriptionTransferCache;
    }
    
    public LocationTypeTransferCache getLocationTypeTransferCache() {
        if(locationTypeTransferCache == null)
            locationTypeTransferCache = new LocationTypeTransferCache();
        
        return locationTypeTransferCache;
    }
    
    public LocationUseTypeTransferCache getLocationUseTypeTransferCache() {
        if(locationUseTypeTransferCache == null)
            locationUseTypeTransferCache = new LocationUseTypeTransferCache();
        
        return locationUseTypeTransferCache;
    }
    
    public WarehouseTransferCache getWarehouseTransferCache() {
        if(warehouseTransferCache == null)
            warehouseTransferCache = new WarehouseTransferCache();
        
        return warehouseTransferCache;
    }
    
    public LocationVolumeTransferCache getLocationVolumeTransferCache() {
        if(locationVolumeTransferCache == null)
            locationVolumeTransferCache = new LocationVolumeTransferCache();
        
        return locationVolumeTransferCache;
    }
    
    public LocationCapacityTransferCache getLocationCapacityTransferCache() {
        if(locationCapacityTransferCache == null)
            locationCapacityTransferCache = new LocationCapacityTransferCache();
        
        return locationCapacityTransferCache;
    }

    public WarehouseTypeTransferCache getWarehouseTypeTransferCache() {
        if(warehouseTypeTransferCache == null)
            warehouseTypeTransferCache = new WarehouseTypeTransferCache();

        return warehouseTypeTransferCache;
    }

    public WarehouseTypeDescriptionTransferCache getWarehouseTypeDescriptionTransferCache() {
        if(warehouseTypeDescriptionTransferCache == null)
            warehouseTypeDescriptionTransferCache = new WarehouseTypeDescriptionTransferCache();

        return warehouseTypeDescriptionTransferCache;
    }

}
