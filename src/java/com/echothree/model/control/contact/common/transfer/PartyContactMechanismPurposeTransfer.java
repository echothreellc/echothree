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

package com.echothree.model.control.contact.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class PartyContactMechanismPurposeTransfer
        extends BaseTransfer {
    
    private PartyContactMechanismTransfer partyContactMechanism;
    private ContactMechanismPurposeTransfer contactMechanismPurpose;
    private Boolean isDefault;
    private Integer sortOrder;
    
    /** Creates a new instance of PartyContactMechanismPurposeTransfer */
    public PartyContactMechanismPurposeTransfer(Boolean isDefault, Integer sortOrder) {
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }
    
    /** Creates a new instance of PartyContactMechanismPurposeTransfer */
    public PartyContactMechanismPurposeTransfer(PartyContactMechanismTransfer partyContactMechanism,
            ContactMechanismPurposeTransfer contactMechanismPurpose, Boolean isDefault, Integer sortOrder) {
        this.partyContactMechanism = partyContactMechanism;
        this.contactMechanismPurpose = contactMechanismPurpose;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }
    
    public PartyContactMechanismTransfer getPartyContactMechanism() {
        return partyContactMechanism;
    }
    
    public void setPartyContactMechanism(PartyContactMechanismTransfer partyContactMechanism) {
        this.partyContactMechanism = partyContactMechanism;
    }
    
    public ContactMechanismPurposeTransfer getContactMechanismPurpose() {
        return contactMechanismPurpose;
    }
    
    public void setContactMechanismPurpose(ContactMechanismPurposeTransfer contactMechanismPurpose) {
        this.contactMechanismPurpose = contactMechanismPurpose;
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
