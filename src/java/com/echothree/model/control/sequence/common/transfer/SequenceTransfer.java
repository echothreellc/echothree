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

package com.echothree.model.control.sequence.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class SequenceTransfer
        extends BaseTransfer {
    
    private SequenceTypeTransfer sequenceType;
    private String sequenceName;
    private String mask;
    private Integer chunkSize;
    private Boolean isDefault;
    private Integer sortOrder;
    private String value;
    private String description;
    
    /** Creates a new instance of SequenceTransfer */
    public SequenceTransfer(SequenceTypeTransfer sequenceType, String sequenceName, String mask, Integer chunkSize, Boolean isDefault, Integer sortOrder,
    String value, String description) {
        this.sequenceType = sequenceType;
        this.sequenceName = sequenceName;
        this.mask = mask;
        this.chunkSize = chunkSize;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.value = value;
        this.description = description;
    }
    
    public SequenceTypeTransfer getSequenceType() {
        return sequenceType;
    }
    
    public void setSequenceType(SequenceTypeTransfer sequenceType) {
        this.sequenceType = sequenceType;
    }
    
    public String getSequenceName() {
        return sequenceName;
    }
    
    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }
    
    public String getMask() {
        return mask;
    }
    
    public void setMask(String mask) {
        this.mask = mask;
    }
    
    public Integer getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(Integer chunkSize) {
        this.chunkSize = chunkSize;
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
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
