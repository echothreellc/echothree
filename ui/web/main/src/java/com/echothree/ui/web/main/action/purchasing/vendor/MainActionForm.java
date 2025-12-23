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

package com.echothree.ui.web.main.action.purchasing.vendor;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

@SproutForm(name="VendorMain")
public class MainActionForm
        extends BaseActionForm {
    
    private String vendorName;
    private String firstName;
    private Boolean firstNameSoundex;
    private String middleName;
    private Boolean middleNameSoundex;
    private String lastName;
    private Boolean lastNameSoundex;
    private String name;
    private String createdSince;
    private String modifiedSince;
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public Boolean getFirstNameSoundex() {
        return firstNameSoundex;
    }
    
    public void setFirstNameSoundex(Boolean firstNameSoundex) {
        this.firstNameSoundex = firstNameSoundex;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    public Boolean getMiddleNameSoundex() {
        return middleNameSoundex;
    }
    
    public void setMiddleNameSoundex(Boolean middleNameSoundex) {
        this.middleNameSoundex = middleNameSoundex;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public Boolean getLastNameSoundex() {
        return lastNameSoundex;
    }
    
    public void setLastNameSoundex(Boolean lastNameSoundex) {
        this.lastNameSoundex = lastNameSoundex;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getVendorName() {
        return vendorName;
    }
    
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    
    public String getCreatedSince() {
        return createdSince;
    }

    public void setCreatedSince(String createdSince) {
        this.createdSince = createdSince;
    }

    public String getModifiedSince() {
        return modifiedSince;
    }

    public void setModifiedSince(String modifiedSince) {
        this.modifiedSince = modifiedSince;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setFirstNameSoundex(false);
        setMiddleNameSoundex(false);
        setLastNameSoundex(false);
    }
    
}
