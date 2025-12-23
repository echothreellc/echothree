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

package com.echothree.model.control.geo.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class GeoCodeAliasTransfer
        extends BaseTransfer {
    
    private GeoCodeTransfer geoCode;
    private GeoCodeScopeTransfer geoCodeScope;
    private GeoCodeAliasTypeTransfer geoCodeAliasType;
    private String alias;
    
    /** Creates a new instance of GeoCodeAliasTransfer */
    public GeoCodeAliasTransfer(GeoCodeTransfer geoCode, GeoCodeScopeTransfer geoCodeScope,
            GeoCodeAliasTypeTransfer geoCodeAliasType, String alias) {
        this.geoCode = geoCode;
        this.geoCodeScope = geoCodeScope;
        this.geoCodeAliasType = geoCodeAliasType;
        this.alias = alias;
    }
    
    public GeoCodeTransfer getGeoCode() {
        return geoCode;
    }
    
    public void setGeoCode(GeoCodeTransfer geoCode) {
        this.geoCode = geoCode;
    }
    
    public GeoCodeScopeTransfer getGeoCodeScope() {
        return geoCodeScope;
    }
    
    public void setGeoCodeScope(GeoCodeScopeTransfer geoCodeScope) {
        this.geoCodeScope = geoCodeScope;
    }
    
    public GeoCodeAliasTypeTransfer getGeoCodeAliasType() {
        return geoCodeAliasType;
    }
    
    public void setGeoCodeAliasType(GeoCodeAliasTypeTransfer geoCodeAliasType) {
        this.geoCodeAliasType = geoCodeAliasType;
    }
    
    public String getAlias() {
        return alias;
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }
    
}
