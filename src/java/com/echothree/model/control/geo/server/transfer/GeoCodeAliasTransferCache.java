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

import com.echothree.model.control.geo.common.transfer.GeoCodeAliasTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCodeAlias;
import com.echothree.model.data.user.server.entity.UserVisit;

public class GeoCodeAliasTransferCache
        extends BaseGeoTransferCache<GeoCodeAlias, GeoCodeAliasTransfer> {
    
    /** Creates a new instance of GeoCodeAliasTransferCache */
    public GeoCodeAliasTransferCache(GeoControl geoControl) {
        super(geoControl);
    }
    
    public GeoCodeAliasTransfer getGeoCodeAliasTransfer(GeoCodeAlias geoCodeAlias) {
        var geoCodeAliasTransfer = get(geoCodeAlias);
        
        if(geoCodeAliasTransfer == null) {
            var geoCode = geoControl.getGeoCodeTransfer(userVisit, geoCodeAlias.getGeoCode());
            var geoCodeScope = geoControl.getGeoCodeScopeTransfer(userVisit, geoCodeAlias.getGeoCodeScope());
            var geoCodeAliasType = geoControl.getGeoCodeAliasTypeTransfer(userVisit, geoCodeAlias.getGeoCodeAliasType());
            var alias = geoCodeAlias.getAlias();
            
            geoCodeAliasTransfer = new GeoCodeAliasTransfer(geoCode, geoCodeScope, geoCodeAliasType, alias);
            put(userVisit, geoCodeAlias, geoCodeAliasTransfer);
        }
        
        return geoCodeAliasTransfer;
    }
    
}
