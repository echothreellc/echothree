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

import com.echothree.model.control.geo.common.transfer.GeoCodeAliasTypeDescriptionTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class GeoCodeAliasTypeDescriptionTransferCache
        extends BaseGeoDescriptionTransferCache<GeoCodeAliasTypeDescription, GeoCodeAliasTypeDescriptionTransfer> {
    
    /** Creates a new instance of GeoCodeAliasTypeDescriptionTransferCache */
    public GeoCodeAliasTypeDescriptionTransferCache(UserVisit userVisit, GeoControl geoControl) {
        super(userVisit, geoControl);
    }
    
    public GeoCodeAliasTypeDescriptionTransfer getGeoCodeAliasTypeDescriptionTransfer(GeoCodeAliasTypeDescription geoCodeAliasTypeDescription) {
        var geoCodeAliasTypeDescriptionTransfer = get(geoCodeAliasTypeDescription);
        
        if(geoCodeAliasTypeDescriptionTransfer == null) {
            var geoCodeAliasTypeTransfer = geoControl.getGeoCodeAliasTypeTransfer(userVisit, geoCodeAliasTypeDescription.getGeoCodeAliasType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, geoCodeAliasTypeDescription.getLanguage());
            
            geoCodeAliasTypeDescriptionTransfer = new GeoCodeAliasTypeDescriptionTransfer(languageTransfer, geoCodeAliasTypeTransfer, geoCodeAliasTypeDescription.getDescription());
            put(userVisit, geoCodeAliasTypeDescription, geoCodeAliasTypeDescriptionTransfer);
        }
        
        return geoCodeAliasTypeDescriptionTransfer;
    }
    
}
