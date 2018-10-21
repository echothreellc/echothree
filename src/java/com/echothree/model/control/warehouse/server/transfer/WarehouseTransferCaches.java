// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.warehouse.server.WarehouseControl;
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
    
    /** Creates a new instance of WarehouseTransferCaches */
    public WarehouseTransferCaches(UserVisit userVisit, WarehouseControl warehouseControl) {
        super(userVisit);
        
        this.warehouseControl = warehouseControl;
    }
    
    public LocationDescriptionTransferCache getLocationDescriptionTransferCache() {
        if(locationDescriptionTransferCache == null)
            locationDescriptionTransferCache = new LocationDescriptionTransferCache(userVisit, warehouseControl);
        
        return locationDescriptionTransferCache;
    }
    
    public LocationNameElementDescriptionTransferCache getLocationNameElementDescriptionTransferCache() {
        if(locationNameElementDescriptionTransferCache == null)
            locationNameElementDescriptionTransferCache = new LocationNameElementDescriptionTransferCache(userVisit, warehouseControl);
        
        return locationNameElementDescriptionTransferCache;
    }
    
    public LocationNameElementTransferCache getLocationNameElementTransferCache() {
        if(locationNameElementTransferCache == null)
            locationNameElementTransferCache = new LocationNameElementTransferCache(userVisit, warehouseControl);
        
        return locationNameElementTransferCache;
    }
    
    public LocationTransferCache getLocationTransferCache() {
        if(locationTransferCache == null)
            locationTransferCache = new LocationTransferCache(userVisit, warehouseControl);
        
        return locationTransferCache;
    }
    
    public LocationTypeDescriptionTransferCache getLocationTypeDescriptionTransferCache() {
        if(locationTypeDescriptionTransferCache == null)
            locationTypeDescriptionTransferCache = new LocationTypeDescriptionTransferCache(userVisit, warehouseControl);
        
        return locationTypeDescriptionTransferCache;
    }
    
    public LocationTypeTransferCache getLocationTypeTransferCache() {
        if(locationTypeTransferCache == null)
            locationTypeTransferCache = new LocationTypeTransferCache(userVisit, warehouseControl);
        
        return locationTypeTransferCache;
    }
    
    public LocationUseTypeTransferCache getLocationUseTypeTransferCache() {
        if(locationUseTypeTransferCache == null)
            locationUseTypeTransferCache = new LocationUseTypeTransferCache(userVisit, warehouseControl);
        
        return locationUseTypeTransferCache;
    }
    
    public WarehouseTransferCache getWarehouseTransferCache() {
        if(warehouseTransferCache == null)
            warehouseTransferCache = new WarehouseTransferCache(userVisit, warehouseControl);
        
        return warehouseTransferCache;
    }
    
    public LocationVolumeTransferCache getLocationVolumeTransferCache() {
        if(locationVolumeTransferCache == null)
            locationVolumeTransferCache = new LocationVolumeTransferCache(userVisit, warehouseControl);
        
        return locationVolumeTransferCache;
    }
    
    public LocationCapacityTransferCache getLocationCapacityTransferCache() {
        if(locationCapacityTransferCache == null)
            locationCapacityTransferCache = new LocationCapacityTransferCache(userVisit, warehouseControl);
        
        return locationCapacityTransferCache;
    }
    
}
