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

import com.echothree.model.control.geo.common.GeoConstants;
import com.echothree.model.control.geo.common.GeoOptions;
import com.echothree.model.control.geo.common.transfer.CountyTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeScopeTransfer;
import com.echothree.model.control.geo.common.transfer.GeoCodeTypeTransfer;
import com.echothree.model.control.geo.common.transfer.StateTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeDetail;
import com.echothree.model.data.geo.server.entity.GeoCodeRelationship;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.model.data.user.server.entity.UserVisit;
import java.util.List;
import java.util.Set;

public class CountyTransferCache
        extends BaseGeoCodeTransferCache<GeoCode, CountyTransfer> {
    
    boolean includeAliases;
    
    /** Creates a new instance of CountyTransferCache */
    public CountyTransferCache(UserVisit userVisit, GeoControl geoControl) {
        super(userVisit, geoControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeAliases = options.contains(GeoOptions.CountyIncludeAliases);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public CountyTransfer getCountyTransfer(GeoCode geoCode) {
        CountyTransfer countyTransfer = get(geoCode);
        
        if(countyTransfer == null) {
            GeoCodeDetail geoCodeDetail = geoCode.getLastDetail();
            String geoCodeName = geoCodeDetail.getGeoCodeName();
            GeoCodeTypeTransfer geoCodeType = geoControl.getGeoCodeTypeTransfer(userVisit, geoCodeDetail.getGeoCodeType());
            GeoCodeScopeTransfer geoCodeScope = geoControl.getGeoCodeScopeTransfer(userVisit, geoCodeDetail.getGeoCodeScope());
            Boolean isDefault = geoCodeDetail.getIsDefault();
            Integer sortOrder = geoCodeDetail.getSortOrder();
            String description = geoControl.getBestGeoCodeDescription(geoCode, getLanguage());
            
            GeoCodeType stateGeoCodeType = geoControl.getGeoCodeTypeByName(GeoConstants.GeoCodeType_STATE);
            List<GeoCodeRelationship> geoCodeRelationships = geoControl.getGeoCodeRelationshipsByFromGeoCodeAndGeoCodeType(geoCode, stateGeoCodeType);
            if(geoCodeRelationships.size() != 1) {
                getLog().error("non-1 geoCodeRelationships.size()");
            }
            StateTransfer state = geoControl.getStateTransfer(userVisit, geoCodeRelationships.iterator().next().getToGeoCode());
            
            countyTransfer = new CountyTransfer(state, geoCodeName, geoCodeType, geoCodeScope, isDefault, sortOrder, description);
            put(geoCode, countyTransfer);
            
            if(includeAliases) {
                setupGeoCodeAliasTransfers(geoCode, countyTransfer);
            }
        }
        
        return countyTransfer;
    }
    
}
