// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.warehouse.common.transfer.LocationUseTypeTransfer;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.LocationUseType;

public class LocationUseTypeTransferCache
        extends BaseWarehouseTransferCache<LocationUseType, LocationUseTypeTransfer> {
    
    /** Creates a new instance of LocationUseTypeTransferCache */
    public LocationUseTypeTransferCache(UserVisit userVisit, WarehouseControl warehouseControl) {
        super(userVisit, warehouseControl);
    }
    
    public LocationUseTypeTransfer getLocationUseTypeTransfer(LocationUseType locationUseType) {
        LocationUseTypeTransfer locationUseTypeTransfer = get(locationUseType);
        
        if(locationUseTypeTransfer == null) {
            String locationUseTypeName = locationUseType.getLocationUseTypeName();
            Boolean allowMultiple = locationUseType.getAllowMultiple();
            Boolean isDefault = locationUseType.getIsDefault();
            Integer sortOrder = locationUseType.getSortOrder();
            String description = warehouseControl.getBestLocationUseTypeDescription(locationUseType, getLanguage());
            
            locationUseTypeTransfer = new LocationUseTypeTransfer(locationUseTypeName, allowMultiple, isDefault, sortOrder,
            description);
            put(locationUseType, locationUseTypeTransfer);
        }
        
        return locationUseTypeTransfer;
    }
    
}
