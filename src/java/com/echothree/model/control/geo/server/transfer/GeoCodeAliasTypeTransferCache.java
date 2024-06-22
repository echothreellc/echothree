// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.model.control.geo.common.transfer.GeoCodeTypeTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasType;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class GeoCodeAliasTypeTransferCache
        extends BaseGeoTransferCache<GeoCodeAliasType, GeoCodeAliasTypeTransfer> {
    
    /** Creates a new instance of GeoCodeAliasTypeTransferCache */
    public GeoCodeAliasTypeTransferCache(UserVisit userVisit, GeoControl geoControl) {
        super(userVisit, geoControl);

        setIncludeEntityInstance(true);
    }
    
    public GeoCodeAliasTypeTransfer getGeoCodeAliasTypeTransfer(GeoCodeAliasType geoCodeAliasType) {
        GeoCodeAliasTypeTransfer geoCodeAliasTypeTransfer = get(geoCodeAliasType);
        
        if(geoCodeAliasTypeTransfer == null) {
            GeoCodeAliasTypeDetail geoCodeAliasTypeDetail = geoCodeAliasType.getLastDetail();
            GeoCodeTypeTransfer geoCodeType = geoControl.getGeoCodeTypeTransfer(userVisit, geoCodeAliasTypeDetail.getGeoCodeType());
            String geoCodeAliasTypeName = geoCodeAliasTypeDetail.getGeoCodeAliasTypeName();
            String validationPattern = geoCodeAliasTypeDetail.getValidationPattern();
            Boolean isRequired = geoCodeAliasTypeDetail.getIsRequired();
            Boolean isDefault = geoCodeAliasTypeDetail.getIsDefault();
            Integer sortOrder = geoCodeAliasTypeDetail.getSortOrder();
            String description = geoControl.getBestGeoCodeAliasTypeDescription(geoCodeAliasType, getLanguage());
            
            geoCodeAliasTypeTransfer = new GeoCodeAliasTypeTransfer(geoCodeType, geoCodeAliasTypeName, validationPattern, isRequired, isDefault, sortOrder,
                    description);
            put(geoCodeAliasType, geoCodeAliasTypeTransfer);
        }
        
        return geoCodeAliasTypeTransfer;
    }
    
}
