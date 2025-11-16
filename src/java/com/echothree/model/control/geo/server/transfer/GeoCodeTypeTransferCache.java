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

import com.echothree.model.control.geo.common.transfer.GeoCodeTypeTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GeoCodeTypeTransferCache
        extends BaseGeoTransferCache<GeoCodeType, GeoCodeTypeTransfer> {

    GeoControl geoControl = Session.getModelController(GeoControl.class);

    /** Creates a new instance of GeoCodeTypeTransferCache */
    protected GeoCodeTypeTransferCache() {
        super();

        setIncludeEntityInstance(true);
    }
    
    public GeoCodeTypeTransfer getGeoCodeTypeTransfer(UserVisit userVisit, GeoCodeType geoCodeType) {
        var geoCodeTypeTransfer = get(geoCodeType);
        
        if(geoCodeTypeTransfer == null) {
            var geoCodeTypeDetail = geoCodeType.getLastDetail();
            var geoCodeTypeName = geoCodeTypeDetail.getGeoCodeTypeName();
            var parentGeoCodeType = geoCodeTypeDetail.getParentGeoCodeType();
            var parentGeoCodeTypeTransfer = parentGeoCodeType == null? null: geoControl.getGeoCodeTypeTransfer(userVisit,
                    parentGeoCodeType);
            var isDefault = geoCodeTypeDetail.getIsDefault();
            var sortOrder = geoCodeTypeDetail.getSortOrder();
            var description = geoControl.getBestGeoCodeTypeDescription(geoCodeType, getLanguage(userVisit));
            
            geoCodeTypeTransfer = new GeoCodeTypeTransfer(geoCodeTypeName, parentGeoCodeTypeTransfer, isDefault, sortOrder,
                    description);
            put(userVisit, geoCodeType, geoCodeTypeTransfer);
        }
        
        return geoCodeTypeTransfer;
    }
    
}
