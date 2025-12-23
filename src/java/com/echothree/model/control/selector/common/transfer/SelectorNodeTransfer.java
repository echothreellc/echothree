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

package com.echothree.model.control.selector.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class SelectorNodeTransfer
        extends BaseTransfer {
    
    private SelectorTransfer selector;
    private String selectorNodeName;
    private Boolean isRootSelectorNode;
    private SelectorNodeTypeTransfer selectorNodeType;
    private Boolean negate;
    private String description;
    
    /** Creates a new instance of SelectorNodeTransfer */
    public SelectorNodeTransfer(SelectorTransfer selector, String selectorNodeName, Boolean isRootSelectorNode,
    SelectorNodeTypeTransfer selectorNodeType, Boolean negate, String description) {
        this.selector = selector;
        this.selectorNodeName = selectorNodeName;
        this.isRootSelectorNode = isRootSelectorNode;
        this.selectorNodeType = selectorNodeType;
        this.negate = negate;
        this.description = description;
    }
    
    public SelectorTransfer getSelector() {
        return selector;
    }
    
    public void setSelector(SelectorTransfer selector) {
        this.selector = selector;
    }
    
    public String getSelectorNodeName() {
        return selectorNodeName;
    }
    
    public void setSelectorNodeName(String selectorNodeName) {
        this.selectorNodeName = selectorNodeName;
    }
    
    public Boolean getIsRootSelectorNode() {
        return isRootSelectorNode;
    }
    
    public void setIsRootSelectorNode(Boolean isRootSelectorNode) {
        this.isRootSelectorNode = isRootSelectorNode;
    }
    
    public SelectorNodeTypeTransfer getSelectorNodeType() {
        return selectorNodeType;
    }
    
    public void setSelectorNodeType(SelectorNodeTypeTransfer selectorNodeType) {
        this.selectorNodeType = selectorNodeType;
    }
    
    public Boolean getNegate() {
        return negate;
    }
    
    public void setNegate(Boolean negate) {
        this.negate = negate;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
