// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.warehouse.common.transfer.LocationTypeTransfer;
import com.echothree.model.control.warehouse.common.transfer.WarehouseTransfer;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.LocationType;
import com.echothree.model.data.warehouse.server.entity.LocationTypeDetail;
import com.echothree.model.data.warehouse.server.entity.Warehouse;

public class LocationTypeTransferCache
        extends BaseWarehouseTransferCache<LocationType, LocationTypeTransfer> {
    
    /** Creates a new instance of LocationTypeTransferCache */
    public LocationTypeTransferCache(UserVisit userVisit, WarehouseControl warehouseControl) {
        super(userVisit, warehouseControl);
        
        setIncludeEntityInstance(true);
    }
    
    public LocationTypeTransfer getLocationTypeTransfer(LocationType locationType) {
        LocationTypeTransfer locationTypeTransfer = get(locationType);
        
        if(locationTypeTransfer == null) {
            LocationTypeDetail locationTypeDetail = locationType.getLastDetail();
            WarehouseTransferCache warehouseTransferCache = warehouseControl.getWarehouseTransferCaches(userVisit).getWarehouseTransferCache();
            Warehouse warehouse = warehouseControl.getWarehouse(locationTypeDetail.getWarehouseParty());
            WarehouseTransfer warehouseTransfer = warehouseTransferCache.getWarehouseTransfer(warehouse);
            String locationTypeName = locationTypeDetail.getLocationTypeName();
            Boolean isDefault = locationTypeDetail.getIsDefault();
            Integer sortOrder = locationTypeDetail.getSortOrder();
            String description = warehouseControl.getBestLocationTypeDescription(locationType, getLanguage());
            
            locationTypeTransfer = new LocationTypeTransfer(warehouseTransfer, locationTypeName, isDefault, sortOrder, description);
            put(locationType, locationTypeTransfer);
        }
        
        return locationTypeTransfer;
    }
    
}
