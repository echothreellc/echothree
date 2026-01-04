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

public class SequenceTypeTransfer
        extends BaseTransfer {
    
    private String sequenceTypeName;
    private String prefix;
    private String suffix;
    private SequenceEncoderTypeTransfer sequenceEncoderType;
    private SequenceChecksumTypeTransfer sequenceChecksumType;
    private Integer chunkSize;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of SequenceTypeTransfer */
    public SequenceTypeTransfer(String sequenceTypeName, String prefix, String suffix, SequenceEncoderTypeTransfer sequenceEncoderType,
    SequenceChecksumTypeTransfer sequenceChecksumType, Integer chunkSize, Boolean isDefault, Integer sortOrder,
            String description) {
        this.sequenceTypeName = sequenceTypeName;
        this.prefix = prefix;
        this.suffix = suffix;
        this.sequenceEncoderType = sequenceEncoderType;
        this.sequenceChecksumType = sequenceChecksumType;
        this.chunkSize = chunkSize;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getSequenceTypeName() {
        return sequenceTypeName;
    }
    
    public void setSequenceTypeName(String sequenceTypeName) {
        this.sequenceTypeName = sequenceTypeName;
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    public String getSuffix() {
        return suffix;
    }
    
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    
    public SequenceEncoderTypeTransfer getSequenceEncoderType() {
        return sequenceEncoderType;
    }
    
    public void setSequenceEncoderType(SequenceEncoderTypeTransfer sequenceEncoderType) {
        this.sequenceEncoderType = sequenceEncoderType;
    }
    
    public SequenceChecksumTypeTransfer getSequenceChecksumType() {
        return sequenceChecksumType;
    }
    
    public void setSequenceChecksumType(SequenceChecksumTypeTransfer sequenceChecksumType) {
        this.sequenceChecksumType = sequenceChecksumType;
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

}
