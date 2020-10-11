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

import com.echothree.model.control.geo.common.GeoOptions;
import com.echothree.model.control.geo.common.transfer.GeoCodeScopeTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeTypeTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import java.util.Set;

public class GeoCodeTransferCache
        extends BaseGeoCodeTransferCache<GeoCode, GeoCodeTransfer> {
    
    boolean includeAliases;
    
    /** Creates a new instance of GeoCodeTransferCache */
    public GeoCodeTransferCache(UserVisit userVisit, GeoControl geoControl) {
        super(userVisit, geoControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeAliases = options.contains(GeoOptions.GeoCodeIncludeAliases);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public GeoCodeTransfer getGeoCodeTransfer(GeoCode geoCode) {
        GeoCodeTransfer geoCodeTransfer = get(geoCode);
        
        if(geoCodeTransfer == null) {
            GeoCodeDetail geoCodeDetail = geoCode.getLastDetail();
            String geoCodeName = geoCodeDetail.getGeoCodeName();
            GeoCodeTypeTransfer geoCodeType = geoControl.getGeoCodeTypeTransfer(userVisit, geoCodeDetail.getGeoCodeType());
            GeoCodeScopeTransfer geoCodeScope = geoControl.getGeoCodeScopeTransfer(userVisit, geoCodeDetail.getGeoCodeScope());
            Boolean isDefault = geoCodeDetail.getIsDefault();
            Integer sortOrder = geoCodeDetail.getSortOrder();
            String description = geoControl.getBestGeoCodeDescription(geoCode, getLanguage());
            
            geoCodeTransfer = new GeoCodeTransfer(geoCodeName, geoCodeType, geoCodeScope, isDefault, sortOrder, description);
            put(geoCode, geoCodeTransfer);
            
            if(includeAliases) {
                setupGeoCodeAliasTransfers(geoCode, geoCodeTransfer);
            }
        }
        
        return geoCodeTransfer;
    }
    
}
