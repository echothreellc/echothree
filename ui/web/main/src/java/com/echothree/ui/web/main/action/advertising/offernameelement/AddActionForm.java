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

package com.echothree.ui.web.main.action.advertising.offernameelement;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="OfferNameElementAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private String offerNameElementName;
    private String offset;
    private String length;
    private String validationPattern;
    private String description;
    
    public void setOfferNameElementName(String offerNameElementName) {
        this.offerNameElementName = offerNameElementName;
    }
    
    public String getOfferNameElementName() {
        return offerNameElementName;
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
