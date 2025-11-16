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
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.context.RequestScoped;

@RequestScoped
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
    protected WarehouseTransferCaches() {
        super();
    }
    
    public LocationDescriptionTransferCache getLocationDescriptionTransferCache() {
        if(locationDescriptionTransferCache == null)
            locationDescriptionTransferCache = CDI.current().select(LocationDescriptionTransferCache.class).get();
        
        return locationDescriptionTransferCache;
    }
    
    public LocationNameElementDescriptionTransferCache getLocationNameElementDescriptionTransferCache() {
        if(locationNameElementDescriptionTransferCache == null)
            locationNameElementDescriptionTransferCache = CDI.current().select(LocationNameElementDescriptionTransferCache.class).get();
        
        return locationNameElementDescriptionTransferCache;
    }
    
    public LocationNameElementTransferCache getLocationNameElementTransferCache() {
        if(locationNameElementTransferCache == null)
            locationNameElementTransferCache = CDI.current().select(LocationNameElementTransferCache.class).get();
        
        return locationNameElementTransferCache;
    }
    
    public LocationTransferCache getLocationTransferCache() {
        if(locationTransferCache == null)
            locationTransferCache = CDI.current().select(LocationTransferCache.class).get();
        
        return locationTransferCache;
    }
    
    public LocationTypeDescriptionTransferCache getLocationTypeDescriptionTransferCache() {
        if(locationTypeDescriptionTransferCache == null)
            locationTypeDescriptionTransferCache = CDI.current().select(LocationTypeDescriptionTransferCache.class).get();
        
        return locationTypeDescriptionTransferCache;
    }
    
    public LocationTypeTransferCache getLocationTypeTransferCache() {
        if(locationTypeTransferCache == null)
            locationTypeTransferCache = CDI.current().select(LocationTypeTransferCache.class).get();
        
        return locationTypeTransferCache;
    }
    
    public LocationUseTypeTransferCache getLocationUseTypeTransferCache() {
        if(locationUseTypeTransferCache == null)
            locationUseTypeTransferCache = CDI.current().select(LocationUseTypeTransferCache.class).get();
        
        return locationUseTypeTransferCache;
    }
    
    public WarehouseTransferCache getWarehouseTransferCache() {
        if(warehouseTransferCache == null)
            warehouseTransferCache = CDI.current().select(WarehouseTransferCache.class).get();
        
        return warehouseTransferCache;
    }
    
    public LocationVolumeTransferCache getLocationVolumeTransferCache() {
        if(locationVolumeTransferCache == null)
            locationVolumeTransferCache = CDI.current().select(LocationVolumeTransferCache.class).get();
        
        return locationVolumeTransferCache;
    }
    
    public LocationCapacityTransferCache getLocationCapacityTransferCache() {
        if(locationCapacityTransferCache == null)
            locationCapacityTransferCache = CDI.current().select(LocationCapacityTransferCache.class).get();
        
        return locationCapacityTransferCache;
    }

    public WarehouseTypeTransferCache getWarehouseTypeTransferCache() {
        if(warehouseTypeTransferCache == null)
            warehouseTypeTransferCache = CDI.current().select(WarehouseTypeTransferCache.class).get();

        return warehouseTypeTransferCache;
    }

    public WarehouseTypeDescriptionTransferCache getWarehouseTypeDescriptionTransferCache() {
        if(warehouseTypeDescriptionTransferCache == null)
            warehouseTypeDescriptionTransferCache = CDI.current().select(WarehouseTypeDescriptionTransferCache.class).get();

        return warehouseTypeDescriptionTransferCache;
    }

}
