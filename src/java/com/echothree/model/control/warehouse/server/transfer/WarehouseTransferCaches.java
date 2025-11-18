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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class WarehouseTransferCaches
        extends BaseTransferCaches {

    @Inject
    LocationDescriptionTransferCache locationDescriptionTransferCache;

    @Inject
    LocationNameElementDescriptionTransferCache locationNameElementDescriptionTransferCache;

    @Inject
    LocationNameElementTransferCache locationNameElementTransferCache;

    @Inject
    LocationTransferCache locationTransferCache;

    @Inject
    LocationTypeDescriptionTransferCache locationTypeDescriptionTransferCache;

    @Inject
    LocationTypeTransferCache locationTypeTransferCache;

    @Inject
    LocationUseTypeTransferCache locationUseTypeTransferCache;

    @Inject
    WarehouseTransferCache warehouseTransferCache;

    @Inject
    LocationVolumeTransferCache locationVolumeTransferCache;

    @Inject
    LocationCapacityTransferCache locationCapacityTransferCache;

    @Inject
    WarehouseTypeTransferCache warehouseTypeTransferCache;

    @Inject
    WarehouseTypeDescriptionTransferCache warehouseTypeDescriptionTransferCache;
    
    /** Creates a new instance of WarehouseTransferCaches */
    protected WarehouseTransferCaches() {
        super();
    }
    
    public LocationDescriptionTransferCache getLocationDescriptionTransferCache() {
        return locationDescriptionTransferCache;
    }
    
    public LocationNameElementDescriptionTransferCache getLocationNameElementDescriptionTransferCache() {
        return locationNameElementDescriptionTransferCache;
    }
    
    public LocationNameElementTransferCache getLocationNameElementTransferCache() {
        return locationNameElementTransferCache;
    }
    
    public LocationTransferCache getLocationTransferCache() {
        return locationTransferCache;
    }
    
    public LocationTypeDescriptionTransferCache getLocationTypeDescriptionTransferCache() {
        return locationTypeDescriptionTransferCache;
    }
    
    public LocationTypeTransferCache getLocationTypeTransferCache() {
        return locationTypeTransferCache;
    }
    
    public LocationUseTypeTransferCache getLocationUseTypeTransferCache() {
        return locationUseTypeTransferCache;
    }
    
    public WarehouseTransferCache getWarehouseTransferCache() {
        return warehouseTransferCache;
    }
    
    public LocationVolumeTransferCache getLocationVolumeTransferCache() {
        return locationVolumeTransferCache;
    }
    
    public LocationCapacityTransferCache getLocationCapacityTransferCache() {
        return locationCapacityTransferCache;
    }

    public WarehouseTypeTransferCache getWarehouseTypeTransferCache() {
        return warehouseTypeTransferCache;
    }

    public WarehouseTypeDescriptionTransferCache getWarehouseTypeDescriptionTransferCache() {
        return warehouseTypeDescriptionTransferCache;
    }

}
