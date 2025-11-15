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

import com.echothree.model.control.geo.common.transfer.GeoCodeDateTimeFormatTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.geo.server.entity.GeoCodeDateTimeFormat;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class GeoCodeDateTimeFormatTransferCache
        extends BaseGeoTransferCache<GeoCodeDateTimeFormat, GeoCodeDateTimeFormatTransfer> {

    GeoControl geoControl = Session.getModelController(GeoControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of GeoCodeDateTimeFormatTransferCache */
    public GeoCodeDateTimeFormatTransferCache() {
        super();
    }
    
    public GeoCodeDateTimeFormatTransfer getGeoCodeDateTimeFormatTransfer(UserVisit userVisit, GeoCodeDateTimeFormat geoCodeDateTimeFormat) {
        var geoCodeDateTimeFormatTransfer = get(geoCodeDateTimeFormat);
        
        if(geoCodeDateTimeFormatTransfer == null) {
            var geoCode = geoControl.getGeoCodeTransfer(userVisit, geoCodeDateTimeFormat.getGeoCode());
            var dateTimeFormat = partyControl.getDateTimeFormatTransfer(userVisit, geoCodeDateTimeFormat.getDateTimeFormat());
            var isDefault = geoCodeDateTimeFormat.getIsDefault();
            var sortOrder = geoCodeDateTimeFormat.getSortOrder();
            
            geoCodeDateTimeFormatTransfer = new GeoCodeDateTimeFormatTransfer(geoCode, dateTimeFormat, isDefault, sortOrder);
            put(userVisit, geoCodeDateTimeFormat, geoCodeDateTimeFormatTransfer);
        }
        
        return geoCodeDateTimeFormatTransfer;
    }
    
}
