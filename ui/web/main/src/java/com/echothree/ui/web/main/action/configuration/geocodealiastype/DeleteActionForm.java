// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.geocodealiastype;

import com.echothree.ui.web.main.framework.MainBaseDeleteActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="GeoCodeAliasTypeDelete")
public class DeleteActionForm
        extends MainBaseDeleteActionForm {
    
    private String geoCodeTypeName;
    private String geoCodeAliasTypeName;

    /**
     * Returns the geoCodeTypeName.
     * @return the geoCodeTypeName
     */
    public String getGeoCodeTypeName() {
        return geoCodeTypeName;
    }

    /**
     * Sets the geoCodeTypeName.
     * @param geoCodeTypeName the geoCodeTypeName to set
     */
    public void setGeoCodeTypeName(String geoCodeTypeName) {
        this.geoCodeTypeName = geoCodeTypeName;
    }

    /**
     * Returns the geoCodeAliasTypeName.
     * @return the geoCodeAliasTypeName
     */
    public String getGeoCodeAliasTypeName() {
        return geoCodeAliasTypeName;
    }

    /**
     * Sets the geoCodeAliasTypeName.
     * @param geoCodeAliasTypeName the geoCodeAliasTypeName to set
     */
    public void setGeoCodeAliasTypeName(String geoCodeAliasTypeName) {
        this.geoCodeAliasTypeName = geoCodeAliasTypeName;
    }

}
