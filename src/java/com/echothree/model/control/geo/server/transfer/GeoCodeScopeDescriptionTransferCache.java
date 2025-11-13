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

import com.echothree.model.control.geo.common.transfer.GeoCodeScopeDescriptionTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCodeScopeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class GeoCodeScopeDescriptionTransferCache
        extends BaseGeoDescriptionTransferCache<GeoCodeScopeDescription, GeoCodeScopeDescriptionTransfer> {
    
    /** Creates a new instance of GeoCodeScopeDescriptionTransferCache */
    public GeoCodeScopeDescriptionTransferCache(GeoControl geoControl) {
        super(geoControl);
    }
    
    public GeoCodeScopeDescriptionTransfer getGeoCodeScopeDescriptionTransfer(GeoCodeScopeDescription geoCodeScopeDescription) {
        var geoCodeScopeDescriptionTransfer = get(geoCodeScopeDescription);
        
        if(geoCodeScopeDescriptionTransfer == null) {
            var geoCodeScopeTransfer = geoControl.getGeoCodeScopeTransfer(userVisit, geoCodeScopeDescription.getGeoCodeScope());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, geoCodeScopeDescription.getLanguage());
            
            geoCodeScopeDescriptionTransfer = new GeoCodeScopeDescriptionTransfer(languageTransfer, geoCodeScopeTransfer, geoCodeScopeDescription.getDescription());
            put(userVisit, geoCodeScopeDescription, geoCodeScopeDescriptionTransfer);
        }
        
        return geoCodeScopeDescriptionTransfer;
    }
    
}
