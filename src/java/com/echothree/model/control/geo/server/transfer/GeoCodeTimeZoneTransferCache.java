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

package com.echothree.model.control.geo.server.transfer;

import com.echothree.model.control.geo.common.transfer.GeoCodeTimeZoneTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.geo.server.entity.GeoCodeTimeZone;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class GeoCodeTimeZoneTransferCache
        extends BaseGeoTransferCache<GeoCodeTimeZone, GeoCodeTimeZoneTransfer> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of GeoCodeTimeZoneTransferCache */
    public GeoCodeTimeZoneTransferCache(UserVisit userVisit, GeoControl geoControl) {
        super(userVisit, geoControl);
        
        partyControl = Session.getModelController(PartyControl.class);
    }
    
    public GeoCodeTimeZoneTransfer getGeoCodeTimeZoneTransfer(GeoCodeTimeZone geoCodeTimeZone) {
        var geoCodeTimeZoneTransfer = get(geoCodeTimeZone);
        
        if(geoCodeTimeZoneTransfer == null) {
            var geoCode = geoControl.getGeoCodeTransfer(userVisit, geoCodeTimeZone.getGeoCode());
            var timeZone = partyControl.getTimeZoneTransfer(userVisit, geoCodeTimeZone.getTimeZone());
            var isDefault = geoCodeTimeZone.getIsDefault();
            var sortOrder = geoCodeTimeZone.getSortOrder();
            
            geoCodeTimeZoneTransfer = new GeoCodeTimeZoneTransfer(geoCode, timeZone, isDefault, sortOrder);
            put(userVisit, geoCodeTimeZone, geoCodeTimeZoneTransfer);
        }
        
        return geoCodeTimeZoneTransfer;
    }
    
}
