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

package com.echothree.model.control.communication.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class CommunicationSourceTransfer
        extends BaseTransfer {
    
    private String communicationSourceName;
    private CommunicationSourceTypeTransfer communicationSourceType;
    private Integer sortOrder;
    private String description;
    private CommunicationEmailSourceTransfer communicationEmailSource;
    
    /** Creates a new instance of CommunicationSourceTransfer */
    public CommunicationSourceTransfer(String communicationSourceName, CommunicationSourceTypeTransfer communicationSourceType,
            Integer sortOrder, String description,
            CommunicationEmailSourceTransfer communicationEmailSource) {
        this.communicationSourceName = communicationSourceName;
        this.communicationSourceType = communicationSourceType;
        this.sortOrder = sortOrder;
        this.description = description;
        this.communicationEmailSource = communicationEmailSource;
    }
    
    public String getCommunicationSourceName() {
        return communicationSourceName;
    }
    
    public void setCommunicationSourceName(String communicationSourceName) {
        this.communicationSourceName = communicationSourceName;
    }
    
    public CommunicationSourceTypeTransfer getCommunicationSourceType() {
        return communicationSourceType;
    }
    
    public void setCommunicationSourceType(CommunicationSourceTypeTransfer communicationSourceType) {
        this.communicationSourceType = communicationSourceType;
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
    
    public CommunicationEmailSourceTransfer getCommunicationEmailSource() {
        return communicationEmailSource;
    }
    
    public void setCommunicationEmailSource(CommunicationEmailSourceTransfer communicationEmailSource) {
        this.communicationEmailSource = communicationEmailSource;
    }
    
}
