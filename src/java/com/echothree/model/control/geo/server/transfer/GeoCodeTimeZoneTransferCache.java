// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.geo.common.transfer.GeoCodeTransfer;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
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
        
        partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    }
    
    public GeoCodeTimeZoneTransfer getGeoCodeTimeZoneTransfer(GeoCodeTimeZone geoCodeTimeZone) {
        GeoCodeTimeZoneTransfer geoCodeTimeZoneTransfer = get(geoCodeTimeZone);
        
        if(geoCodeTimeZoneTransfer == null) {
            GeoCodeTransfer geoCode = geoControl.getGeoCodeTransfer(userVisit, geoCodeTimeZone.getGeoCode());
            TimeZoneTransfer timeZone = partyControl.getTimeZoneTransfer(userVisit, geoCodeTimeZone.getTimeZone());
            Boolean isDefault = geoCodeTimeZone.getIsDefault();
            Integer sortOrder = geoCodeTimeZone.getSortOrder();
            
            geoCodeTimeZoneTransfer = new GeoCodeTimeZoneTransfer(geoCode, timeZone, isDefault, sortOrder);
            put(geoCodeTimeZone, geoCodeTimeZoneTransfer);
        }
        
        return geoCodeTimeZoneTransfer;
    }
    
}
