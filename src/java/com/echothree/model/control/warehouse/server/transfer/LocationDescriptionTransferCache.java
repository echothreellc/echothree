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

import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.control.warehouse.remote.transfer.LocationDescriptionTransfer;
import com.echothree.model.control.warehouse.remote.transfer.LocationTransfer;
import com.echothree.model.control.warehouse.server.WarehouseControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.LocationDescription;

public class LocationDescriptionTransferCache
        extends BaseWarehouseDescriptionTransferCache<LocationDescription, LocationDescriptionTransfer> {
    
    /** Creates a new instance of LocationDescriptionTransferCache */
    public LocationDescriptionTransferCache(UserVisit userVisit, WarehouseControl warehouseControl) {
        super(userVisit, warehouseControl);
    }
    
    public LocationDescriptionTransfer getLocationDescriptionTransfer(LocationDescription locationDescription) {
        LocationDescriptionTransfer locationDescriptionTransfer = get(locationDescription);
        
        if(locationDescriptionTransfer == null) {
            LocationTransferCache locationTransferCache = warehouseControl.getWarehouseTransferCaches(userVisit).getLocationTransferCache();
            LocationTransfer locationTransfer = locationTransferCache.getLocationTransfer(locationDescription.getLocation());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, locationDescription.getLanguage());
            
            locationDescriptionTransfer = new LocationDescriptionTransfer(languageTransfer, locationTransfer, locationDescription.getDescription());
            put(locationDescription, locationDescriptionTransfer);
        }
        
        return locationDescriptionTransfer;
    }
    
}
