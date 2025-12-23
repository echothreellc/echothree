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

package com.echothree.model.control.associate.common.transfer;

import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class AssociatePartyContactMechanismTransfer
        extends BaseTransfer {
    
    private AssociateTransfer associate;
    private String associatePartyContactMechanismName;
    private PartyContactMechanismTransfer partyContactMechanism;
    private Boolean isDefault;
    private Integer sortOrder;
    
    /** Creates a new instance of AssociatePartyContactMechanismTransfer */
    public AssociatePartyContactMechanismTransfer(AssociateTransfer associate, String associatePartyContactMechanismName,
            PartyContactMechanismTransfer partyContactMechanism, Boolean isDefault, Integer sortOrder) {
        this.associate = associate;
        this.associatePartyContactMechanismName = associatePartyContactMechanismName;
        this.partyContactMechanism = partyContactMechanism;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }
    
    public AssociateTransfer getAssociate() {
        return associate;
    }
    
    public void setAssociate(AssociateTransfer associate) {
        this.associate = associate;
    }
    
    public String getAssociatePartyContactMechanismName() {
        return associatePartyContactMechanismName;
    }
    
    public void setAssociatePartyContactMechanismName(String associatePartyContactMechanismName) {
        this.associatePartyContactMechanismName = associatePartyContactMechanismName;
    }
    
    public PartyContactMechanismTransfer getPartyContactMechanism() {
        return partyContactMechanism;
    }
    
    public void setPartyContactMechanism(PartyContactMechanismTransfer partyContactMechanism) {
        this.partyContactMechanism = partyContactMechanism;
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
