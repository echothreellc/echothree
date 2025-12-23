// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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
import com.echothree.model.control.warehouse.server.control.LocationUseTypeControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.LocationUseType;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class LocationUseTypeTransferCache
        extends BaseWarehouseTransferCache<LocationUseType, LocationUseTypeTransfer> {

    LocationUseTypeControl locationUseTypeControl = Session.getModelController(LocationUseTypeControl.class);

    /** Creates a new instance of LocationUseTypeTransferCache */
    protected LocationUseTypeTransferCache() {
        super();
    }
    
    public LocationUseTypeTransfer getLocationUseTypeTransfer(UserVisit userVisit, LocationUseType locationUseType) {
        var locationUseTypeTransfer = get(locationUseType);
        
        if(locationUseTypeTransfer == null) {
            var locationUseTypeName = locationUseType.getLocationUseTypeName();
            var allowMultiple = locationUseType.getAllowMultiple();
            var isDefault = locationUseType.getIsDefault();
            var sortOrder = locationUseType.getSortOrder();
            var description = locationUseTypeControl.getBestLocationUseTypeDescription(locationUseType, getLanguage(userVisit));
            
            locationUseTypeTransfer = new LocationUseTypeTransfer(locationUseTypeName, allowMultiple, isDefault, sortOrder,
            description);
            put(userVisit, locationUseType, locationUseTypeTransfer);
        }
        
        return locationUseTypeTransfer;
    }
    
}
