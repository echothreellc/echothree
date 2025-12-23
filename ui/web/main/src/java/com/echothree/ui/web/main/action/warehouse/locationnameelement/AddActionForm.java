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

package com.echothree.ui.web.main.action.warehouse.locationnameelement;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="LocationNameElementAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private String warehouseName;
    private String locationTypeName;
    private String locationNameElementName;
    private String offset;
    private String length;
    private String validationPattern;
    private String description;
    
    public String getWarehouseName() {
        return warehouseName;
    }
    
    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
    
    public String getLocationTypeName() {
        return locationTypeName;
    }
    
    public void setLocationTypeName(String locationTypeName) {
        this.locationTypeName = locationTypeName;
    }
    
    public void setLocationNameElementName(String locationNameElementName) {
        this.locationNameElementName = locationNameElementName;
    }
    
    public String getLocationNameElementName() {
        return locationNameElementName;
    }
    
    public String getOffset() {
        return offset;
    }
    
    public void setOffset(String offset) {
        this.offset = offset;
    }
    
    public String getLength() {
        return length;
    }
    
    public void setLength(String length) {
        this.length = length;
    }
    
    public String getValidationPattern() {
        return validationPattern;
    }
    
    public void setValidationPattern(String validationPattern) {
        this.validationPattern = validationPattern;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
}
