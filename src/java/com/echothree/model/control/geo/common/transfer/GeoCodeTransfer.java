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


public class GeoCodeTransfer
        extends BaseGeoCodeTransfer {
    
    private String geoCodeName;
    private GeoCodeTypeTransfer geoCodeType;
    private GeoCodeScopeTransfer geoCodeScope;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of GeoCodeTransfer */
    public GeoCodeTransfer(String geoCodeName, GeoCodeTypeTransfer geoCodeType, GeoCodeScopeTransfer geoCodeScope,
            Boolean isDefault, Integer sortOrder, String description) {
        this.geoCodeName = geoCodeName;
        this.geoCodeType = geoCodeType;
        this.geoCodeScope = geoCodeScope;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getGeoCodeName() {
        return geoCodeName;
    }
    
    public void setGeoCodeName(String geoCodeName) {
        this.geoCodeName = geoCodeName;
    }
    
    public GeoCodeTypeTransfer getGeoCodeType() {
        return geoCodeType;
    }
    
    public void setGeoCodeType(GeoCodeTypeTransfer geoCodeType) {
        this.geoCodeType = geoCodeType;
    }
    
    public GeoCodeScopeTransfer getGeoCodeScope() {
        return geoCodeScope;
    }
    
    public void setGeoCodeScope(GeoCodeScopeTransfer geoCodeScope) {
        this.geoCodeScope = geoCodeScope;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
