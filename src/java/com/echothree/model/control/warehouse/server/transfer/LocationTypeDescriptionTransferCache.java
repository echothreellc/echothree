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

import com.echothree.model.control.warehouse.common.transfer.LocationTypeDescriptionTransfer;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.LocationTypeDescription;

public class LocationTypeDescriptionTransferCache
        extends BaseWarehouseDescriptionTransferCache<LocationTypeDescription, LocationTypeDescriptionTransfer> {
    
    /** Creates a new instance of LocationTypeDescriptionTransferCache */
    public LocationTypeDescriptionTransferCache(WarehouseControl warehouseControl) {
        super(warehouseControl);
    }
    
    public LocationTypeDescriptionTransfer getLocationTypeDescriptionTransfer(UserVisit userVisit, LocationTypeDescription locationTypeDescription) {
        var locationTypeDescriptionTransfer = get(locationTypeDescription);
        
        if(locationTypeDescriptionTransfer == null) {
            var locationTypeTransferCache = warehouseControl.getWarehouseTransferCaches(userVisit).getLocationTypeTransferCache();
            var locationTypeTransfer = locationTypeTransferCache.getLocationTypeTransfer(locationTypeDescription.getLocationType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, locationTypeDescription.getLanguage());
            
            locationTypeDescriptionTransfer = new LocationTypeDescriptionTransfer(languageTransfer, locationTypeTransfer, locationTypeDescription.getDescription());
            put(userVisit, locationTypeDescription, locationTypeDescriptionTransfer);
        }
        
        return locationTypeDescriptionTransfer;
    }
    
}
