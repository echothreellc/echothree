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

package com.echothree.model.control.chain.common.transfer;

import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ChainTransfer
        extends BaseTransfer {
    
    private ChainTypeTransfer chainType;
    private String chainName;
    private SequenceTransfer chainInstanceSequence;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of ChainTransfer */
    public ChainTransfer(ChainTypeTransfer chainType, String chainName, SequenceTransfer chainInstanceSequence, Boolean isDefault,
            Integer sortOrder, String description) {
        this.chainType = chainType;
        this.chainName = chainName;
        this.chainInstanceSequence = chainInstanceSequence;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public ChainTypeTransfer getChainType() {
        return chainType;
    }
    
    public void setChainType(ChainTypeTransfer chainType) {
        this.chainType = chainType;
    }
    
    public String getChainName() {
        return chainName;
    }
    
    public void setChainName(String chainName) {
        this.chainName = chainName;
    }
    
    public SequenceTransfer getChainInstanceSequence() {
        return chainInstanceSequence;
    }
    
    public void setChainInstanceSequence(SequenceTransfer chainInstanceSequence) {
        this.chainInstanceSequence = chainInstanceSequence;
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
