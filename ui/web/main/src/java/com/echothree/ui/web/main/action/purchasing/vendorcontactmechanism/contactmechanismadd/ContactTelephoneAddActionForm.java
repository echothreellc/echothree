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

package com.echothree.ui.web.main.action.purchasing.vendorcontactmechanism.contactmechanismadd;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

@SproutForm(name="VendorContactTelephoneAdd")
public class ContactTelephoneAddActionForm
        extends BaseActionForm {
    
    private String partyName;
    private Boolean allowSolicitation;
    private String countryName;
    private String areaCode;
    private String telephoneNumber;
    private String telephoneExtension;
    private String description;
    
    public String getPartyName() {
        return partyName;
    }
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public Boolean getAllowSolicitation() {
        return allowSolicitation;
    }
    
    public void setAllowSolicitation(Boolean allowSolicitation) {
        this.allowSolicitation = allowSolicitation;
    }
    
    public String getCountryName() {
        return countryName;
    }
    
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    public String getAreaCode() {
        return areaCode;
    }
    
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
    public String getTelephoneNumber() {
        return telephoneNumber;
    }
    
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
    
    public String getTelephoneExtension() {
        return telephoneExtension;
    }
    
    public void setTelephoneExtension(String telephoneExtension) {
        this.telephoneExtension = telephoneExtension;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        setAllowSolicitation(false);
    }
    
}
