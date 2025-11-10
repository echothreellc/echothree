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

import com.echothree.model.control.geo.common.transfer.GeoCodeAliasTypeTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class GeoCodeAliasTypeTransferCache
        extends BaseGeoTransferCache<GeoCodeAliasType, GeoCodeAliasTypeTransfer> {
    
    /** Creates a new instance of GeoCodeAliasTypeTransferCache */
    public GeoCodeAliasTypeTransferCache(UserVisit userVisit, GeoControl geoControl) {
        super(userVisit, geoControl);

        setIncludeEntityInstance(true);
    }
    
    public GeoCodeAliasTypeTransfer getGeoCodeAliasTypeTransfer(GeoCodeAliasType geoCodeAliasType) {
        var geoCodeAliasTypeTransfer = get(geoCodeAliasType);
        
        if(geoCodeAliasTypeTransfer == null) {
            var geoCodeAliasTypeDetail = geoCodeAliasType.getLastDetail();
            var geoCodeType = geoControl.getGeoCodeTypeTransfer(userVisit, geoCodeAliasTypeDetail.getGeoCodeType());
            var geoCodeAliasTypeName = geoCodeAliasTypeDetail.getGeoCodeAliasTypeName();
            var validationPattern = geoCodeAliasTypeDetail.getValidationPattern();
            var isRequired = geoCodeAliasTypeDetail.getIsRequired();
            var isDefault = geoCodeAliasTypeDetail.getIsDefault();
            var sortOrder = geoCodeAliasTypeDetail.getSortOrder();
            var description = geoControl.getBestGeoCodeAliasTypeDescription(geoCodeAliasType, getLanguage(userVisit));
            
            geoCodeAliasTypeTransfer = new GeoCodeAliasTypeTransfer(geoCodeType, geoCodeAliasTypeName, validationPattern, isRequired, isDefault, sortOrder,
                    description);
            put(userVisit, geoCodeAliasType, geoCodeAliasTypeTransfer);
        }
        
        return geoCodeAliasTypeTransfer;
    }
    
}
