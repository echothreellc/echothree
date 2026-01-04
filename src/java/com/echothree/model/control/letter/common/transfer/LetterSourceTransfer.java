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

package com.echothree.model.control.letter.common.transfer;

import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.party.common.transfer.CompanyTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class LetterSourceTransfer
        extends BaseTransfer {
    
    private String letterSourceName;
    private CompanyTransfer company;
    private PartyContactMechanismTransfer emailAddressPartyContactMechanism;
    private PartyContactMechanismTransfer postalAddressPartyContactMechanism;
    private PartyContactMechanismTransfer letterSourcePartyContactMechanism;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of LetterSourceTransfer */
    public LetterSourceTransfer(String letterSourceName, CompanyTransfer company,
            PartyContactMechanismTransfer emailAddressPartyContactMechanism,
            PartyContactMechanismTransfer postalAddressPartyContactMechanism,
            PartyContactMechanismTransfer letterSourcePartyContactMechanism, Boolean isDefault, Integer sortOrder, String description) {
        this.letterSourceName = letterSourceName;
        this.company = company;
        this.emailAddressPartyContactMechanism = emailAddressPartyContactMechanism;
        this.postalAddressPartyContactMechanism = postalAddressPartyContactMechanism;
        this.letterSourcePartyContactMechanism = letterSourcePartyContactMechanism;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getLetterSourceName() {
        return letterSourceName;
    }
    
    public void setLetterSourceName(String letterSourceName) {
        this.letterSourceName = letterSourceName;
    }
    
    public CompanyTransfer getCompany() {
        return company;
    }
    
    public void setCompany(CompanyTransfer company) {
        this.company = company;
    }
    
    public PartyContactMechanismTransfer getEmailAddressPartyContactMechanism() {
        return emailAddressPartyContactMechanism;
    }
    
    public void setEmailAddressPartyContactMechanism(PartyContactMechanismTransfer emailAddressPartyContactMechanism) {
        this.emailAddressPartyContactMechanism = emailAddressPartyContactMechanism;
    }
    
    public PartyContactMechanismTransfer getPostalAddressPartyContactMechanism() {
        return postalAddressPartyContactMechanism;
    }
    
    public void setPostalAddressPartyContactMechanism(PartyContactMechanismTransfer postalAddressPartyContactMechanism) {
        this.postalAddressPartyContactMechanism = postalAddressPartyContactMechanism;
    }
    
    public PartyContactMechanismTransfer getLetterSourcePartyContactMechanism() {
        return letterSourcePartyContactMechanism;
    }
    
    public void setLetterSourcePartyContactMechanism(PartyContactMechanismTransfer letterSourcePartyContactMechanism) {
        this.letterSourcePartyContactMechanism = letterSourcePartyContactMechanism;
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
