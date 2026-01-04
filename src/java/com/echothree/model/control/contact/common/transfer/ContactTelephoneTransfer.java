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

package com.echothree.model.control.contact.common.transfer;

import com.echothree.model.control.geo.common.transfer.CountryTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ContactTelephoneTransfer
        extends BaseTransfer {

    public WorkflowEntityStatusTransfer getTelephoneStatus() {
        return telephoneStatus;
    }

    public void setTelephoneStatus(WorkflowEntityStatusTransfer telephoneStatus) {
        this.telephoneStatus = telephoneStatus;
    }
    
    private CountryTransfer countryGeoCode;
    private String areaCode;
    private String telephoneNumber;
    private String telephoneExtension;
    private WorkflowEntityStatusTransfer telephoneStatus;
    
    /** Creates a new instance of ContactTelephoneTransfer */
    public ContactTelephoneTransfer(CountryTransfer countryGeoCode, String areaCode, String telephoneNumber,
            String telephoneExtension, WorkflowEntityStatusTransfer telephoneStatus) {
        this.countryGeoCode = countryGeoCode;
        this.areaCode = areaCode;
        this.telephoneNumber = telephoneNumber;
        this.telephoneExtension = telephoneExtension;
        this.telephoneStatus = telephoneStatus;
    }
    
    public CountryTransfer getCountryGeoCode() {
        return countryGeoCode;
    }
    
    public void setCountryGeoCode(CountryTransfer countryGeoCode) {
        this.countryGeoCode = countryGeoCode;
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
    
}
