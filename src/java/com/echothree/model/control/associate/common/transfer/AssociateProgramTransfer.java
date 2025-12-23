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

import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class AssociateProgramTransfer
        extends BaseTransfer {
    
    private String associateProgramName;
    private SequenceTransfer associateSequence;
    private SequenceTransfer associatePartyContactMechanismSequence;
    private SequenceTransfer associateReferralSequence;
    private String itemIndirectSalePercent;
    private String itemDirectSalePercent;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of AssociateProgramTransfer */
    public AssociateProgramTransfer(String associateProgramName, SequenceTransfer associateSequence,
            SequenceTransfer associatePartyContactMechanismSequence, SequenceTransfer associateReferralSequence,
            String itemIndirectSalePercent, String itemDirectSalePercent, Boolean isDefault, Integer sortOrder, String description) {
        this.associateProgramName = associateProgramName;
        this.associateSequence = associateSequence;
        this.associatePartyContactMechanismSequence = associatePartyContactMechanismSequence;
        this.associateReferralSequence = associateReferralSequence;
        this.itemIndirectSalePercent = itemIndirectSalePercent;
        this.itemDirectSalePercent = itemDirectSalePercent;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getAssociateProgramName() {
        return associateProgramName;
    }
    
    public void setAssociateProgramName(String associateProgramName) {
        this.associateProgramName = associateProgramName;
    }
    
    public SequenceTransfer getAssociateSequence() {
        return associateSequence;
    }
    
    public void setAssociateSequence(SequenceTransfer associateSequence) {
        this.associateSequence = associateSequence;
    }
    
    public SequenceTransfer getAssociatePartyContactMechanismSequence() {
        return associatePartyContactMechanismSequence;
    }
    
    public void setAssociatePartyContactMechanismSequence(SequenceTransfer associatePartyContactMechanismSequence) {
        this.associatePartyContactMechanismSequence = associatePartyContactMechanismSequence;
    }
    
    public SequenceTransfer getAssociateReferralSequence() {
        return associateReferralSequence;
    }
    
    public void setAssociateReferralSequence(SequenceTransfer associateReferralSequence) {
        this.associateReferralSequence = associateReferralSequence;
    }
    
    public String getItemIndirectSalePercent() {
        return itemIndirectSalePercent;
    }
    
    public void setItemIndirectSalePercent(String itemIndirectSalePercent) {
        this.itemIndirectSalePercent = itemIndirectSalePercent;
    }
    
    public String getItemDirectSalePercent() {
        return itemDirectSalePercent;
    }
    
    public void setItemDirectSalePercent(String itemDirectSalePercent) {
        this.itemDirectSalePercent = itemDirectSalePercent;
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
