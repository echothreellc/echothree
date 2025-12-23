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

package com.echothree.model.control.core.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class EntityTimeTransfer
        extends BaseTransfer {
    
    private Long unformattedCreatedTime;
    private String createdTime;
    private Long unformattedModifiedTime;
    private String modifiedTime;
    private Long unformattedDeletedTime;
    private String deletedTime;
    
    /** Creates a new instance of EntityTimeTransfer */
    public EntityTimeTransfer(Long unformattedCreatedTime, String createdTime, Long unformattedModifiedTime, String modifiedTime,
            Long unformattedDeletedTime, String deletedTime) {
        this.unformattedCreatedTime = unformattedCreatedTime;
        this.createdTime = createdTime;
        this.unformattedModifiedTime = unformattedModifiedTime;
        this.modifiedTime = modifiedTime;
        this.unformattedDeletedTime = unformattedDeletedTime;
        this.deletedTime = deletedTime;
    }
    
    public Long getUnformattedCreatedTime() {
        return unformattedCreatedTime;
    }
    
    public void setUnformattedCreatedTime(Long unformattedCreatedTime) {
        this.unformattedCreatedTime = unformattedCreatedTime;
    }
    
    public String getCreatedTime() {
        return createdTime;
    }
    
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
    
    public Long getUnformattedModifiedTime() {
        return unformattedModifiedTime;
    }
    
    public void setUnformattedModifiedTime(Long unformattedModifiedTime) {
        this.unformattedModifiedTime = unformattedModifiedTime;
    }
    
    public String getModifiedTime() {
        return modifiedTime;
    }
    
    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
    
    public Long getUnformattedDeletedTime() {
        return unformattedDeletedTime;
    }
    
    public void setUnformattedDeletedTime(Long unformattedDeletedTime) {
        this.unformattedDeletedTime = unformattedDeletedTime;
    }
    
    public String getDeletedTime() {
        return deletedTime;
    }
    
    public void setDeletedTime(String deletedTime) {
        this.deletedTime = deletedTime;
    }
    
}
