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

import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class GeoCodeDateTimeFormatTransfer
        extends BaseTransfer {
    
    private GeoCodeTransfer geoCode;
    private DateTimeFormatTransfer dateTimeFormat;
    private Boolean isDefault;
    private Integer sortOrder;

    /** Creates a new instance of GeoCodeDateTimeFormatTransfer */
    public GeoCodeDateTimeFormatTransfer(GeoCodeTransfer geoCode, DateTimeFormatTransfer dateTimeFormat, Boolean isDefault, Integer sortOrder) {
        this.geoCode = geoCode;
        this.dateTimeFormat = dateTimeFormat;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }
    
    public GeoCodeTransfer getGeoCode() {
        return geoCode;
    }
    
    public void setGeoCode(GeoCodeTransfer geoCode) {
        this.geoCode = geoCode;
    }
    
    public DateTimeFormatTransfer getDateTimeFormat() {
        return dateTimeFormat;
    }
    
    public void setDateTimeFormat(DateTimeFormatTransfer dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
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
    
}
