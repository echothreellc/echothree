// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.geo.remote.transfer;

import com.echothree.util.remote.transfer.BaseTransfer;

public class GeoCodeAliasTypeTransfer
        extends BaseTransfer {
    
    private GeoCodeTypeTransfer geoCodeType;
    private String geoCodeAliasTypeName;
    private String validationPattern;
    private Boolean isRequired;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of GeoCodeAliasTypeTransfer */
    public GeoCodeAliasTypeTransfer(GeoCodeTypeTransfer geoCodeType, String geoCodeAliasTypeName, String validationPattern, Boolean isRequired, Boolean isDefault,
            Integer sortOrder, String description) {
        this.geoCodeType = geoCodeType;
        this.geoCodeAliasTypeName = geoCodeAliasTypeName;
        this.validationPattern = validationPattern;
        this.isRequired = isRequired;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * @return the geoCodeType
     */
    public GeoCodeTypeTransfer getGeoCodeType() {
        return geoCodeType;
    }

    /**
     * @param geoCodeType the geoCodeType to set
     */
    public void setGeoCodeType(GeoCodeTypeTransfer geoCodeType) {
        this.geoCodeType = geoCodeType;
    }

    /**
     * @return the geoCodeAliasTypeName
     */
    public String getGeoCodeAliasTypeName() {
        return geoCodeAliasTypeName;
    }

    /**
     * @param geoCodeAliasTypeName the geoCodeAliasTypeName to set
     */
    public void setGeoCodeAliasTypeName(String geoCodeAliasTypeName) {
        this.geoCodeAliasTypeName = geoCodeAliasTypeName;
    }

    /**
     * @return the validationPattern
     */
    public String getValidationPattern() {
        return validationPattern;
    }

    /**
     * @param validationPattern the validationPattern to set
     */
    public void setValidationPattern(String validationPattern) {
        this.validationPattern = validationPattern;
    }

    /**
     * @return the isRequired
     */
    public Boolean getIsRequired() {
        return isRequired;
    }

    /**
     * @param isRequired the isRequired to set
     */
    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
}
