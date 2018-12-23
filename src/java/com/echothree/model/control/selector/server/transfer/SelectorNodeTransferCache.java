// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.selector.server.transfer;

import com.echothree.model.control.selector.common.transfer.SelectorNodeTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorNodeTypeTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorNode;
import com.echothree.model.data.selector.server.entity.SelectorNodeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SelectorNodeTransferCache
        extends BaseSelectorTransferCache<SelectorNode, SelectorNodeTransfer> {
    
    /** Creates a new instance of SelectorNodeTransferCache */
    public SelectorNodeTransferCache(UserVisit userVisit, SelectorControl selectorControl) {
        super(userVisit, selectorControl);
    }
    
    public SelectorNodeTransfer getSelectorNodeTransfer(SelectorNode selectorNode) {
        SelectorNodeTransfer selectorNodeTransfer = get(selectorNode);
        
        if(selectorNodeTransfer == null) {
            SelectorNodeDetail selectorNodeDetail = selectorNode.getLastDetail();
            SelectorTransferCache selectorTransferCache = selectorControl.getSelectorTransferCaches(userVisit).getSelectorTransferCache();
            SelectorTransfer selector = selectorTransferCache.getSelectorTransfer(selectorNodeDetail.getSelector());
            String selectorNodeName = selectorNodeDetail.getSelectorNodeName();
            Boolean isRootSelectorNode = selectorNodeDetail.getIsRootSelectorNode();
            SelectorNodeTypeTransferCache selectorNodeTypeTransferCache = selectorControl.getSelectorTransferCaches(userVisit).getSelectorNodeTypeTransferCache();
            SelectorNodeTypeTransfer selectorNodeType = selectorNodeTypeTransferCache.getSelectorNodeTypeTransfer(selectorNodeDetail.getSelectorNodeType());
            Boolean negate = selectorNodeDetail.getNegate();
            String description = selectorControl.getBestSelectorNodeDescription(selectorNode, getLanguage());
            
            selectorNodeTransfer = new SelectorNodeTransfer(selector, selectorNodeName, isRootSelectorNode, selectorNodeType,
            negate, description);
            put(selectorNode, selectorNodeTransfer);
        }
        
        return selectorNodeTransfer;
    }
    
}
