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

import com.echothree.model.control.warehouse.common.transfer.LocationNameElementTransfer;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.LocationNameElement;
import com.echothree.util.server.persistence.Session;

public class LocationNameElementTransferCache
        extends BaseWarehouseTransferCache<LocationNameElement, LocationNameElementTransfer> {

    WarehouseControl warehouseControl = Session.getModelController(WarehouseControl.class);

    /** Creates a new instance of LocationNameElementTransferCache */
    public LocationNameElementTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public LocationNameElementTransfer getLocationNameElementTransfer(UserVisit userVisit, LocationNameElement locationNameElement) {
        var locationNameElementTransfer = get(locationNameElement);
        
        if(locationNameElementTransfer == null) {
            var locationNameElementDetail = locationNameElement.getLastDetail();
            var locationNameElementName = locationNameElementDetail.getLocationNameElementName();
            var locationTypeTransferCache = warehouseControl.getWarehouseTransferCaches().getLocationTypeTransferCache();
            var locationType = locationTypeTransferCache.getLocationTypeTransfer(userVisit, locationNameElementDetail.getLocationType());
            var offset = locationNameElementDetail.getOffset();
            var length = locationNameElementDetail.getLength();
            var validationPattern = locationNameElementDetail.getValidationPattern();
            var description = warehouseControl.getBestLocationNameElementDescription(locationNameElement, getLanguage(userVisit));
            
            locationNameElementTransfer = new LocationNameElementTransfer(locationNameElementName, locationType, offset, length,
                    validationPattern, description);
            put(userVisit, locationNameElement, locationNameElementTransfer);
        }
        
        return locationNameElementTransfer;
    }
    
}
