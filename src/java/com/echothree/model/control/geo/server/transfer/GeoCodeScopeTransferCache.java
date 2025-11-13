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

import com.echothree.model.control.geo.common.transfer.GeoCodeScopeTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.user.server.entity.UserVisit;

public class GeoCodeScopeTransferCache
        extends BaseGeoTransferCache<GeoCodeScope, GeoCodeScopeTransfer> {
    
    /** Creates a new instance of GeoCodeScopeTransferCache */
    public GeoCodeScopeTransferCache(GeoControl geoControl) {
        super(geoControl);

        setIncludeEntityInstance(true);
    }
    
    public GeoCodeScopeTransfer getGeoCodeScopeTransfer(GeoCodeScope geoCodeScope) {
        var geoCodeScopeTransfer = get(geoCodeScope);
        
        if(geoCodeScopeTransfer == null) {
            var geoCodeScopeDetail = geoCodeScope.getLastDetail();
            var geoCodeScopeName = geoCodeScopeDetail.getGeoCodeScopeName();
            var isDefault = geoCodeScopeDetail.getIsDefault();
            var sortOrder = geoCodeScopeDetail.getSortOrder();
            var description = geoControl.getBestGeoCodeScopeDescription(geoCodeScope, getLanguage(userVisit));
            
            geoCodeScopeTransfer = new GeoCodeScopeTransfer(geoCodeScopeName, isDefault, sortOrder, description);
            put(userVisit, geoCodeScope, geoCodeScopeTransfer);
        }
        
        return geoCodeScopeTransfer;
    }
    
}
