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

import com.echothree.model.control.geo.common.transfer.GeoCodeDescriptionTransfer;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCodeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GeoCodeDescriptionTransferCache
        extends BaseGeoDescriptionTransferCache<GeoCodeDescription, GeoCodeDescriptionTransfer> {

    GeoControl geoControl = Session.getModelController(GeoControl.class);

    /** Creates a new instance of GeoCodeDescriptionTransferCache */
    public GeoCodeDescriptionTransferCache() {
        super();
    }
    
    public GeoCodeDescriptionTransfer getGeoCodeDescriptionTransfer(UserVisit userVisit, GeoCodeDescription geoCodeDescription) {
        var geoCodeDescriptionTransfer = get(geoCodeDescription);
        
        if(geoCodeDescriptionTransfer == null) {
            var geoCodeTransfer = geoControl.getGeoCodeTransfer(userVisit, geoCodeDescription.getGeoCode());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, geoCodeDescription.getLanguage());
            
            geoCodeDescriptionTransfer = new GeoCodeDescriptionTransfer(languageTransfer, geoCodeTransfer, geoCodeDescription.getDescription());
            put(userVisit, geoCodeDescription, geoCodeDescriptionTransfer);
        }
        
        return geoCodeDescriptionTransfer;
    }
    
}
