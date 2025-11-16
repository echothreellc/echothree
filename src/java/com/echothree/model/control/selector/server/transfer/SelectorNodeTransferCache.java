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

package com.echothree.model.control.selector.server.transfer;

import com.echothree.model.control.selector.common.transfer.SelectorNodeTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorNode;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SelectorNodeTransferCache
        extends BaseSelectorTransferCache<SelectorNode, SelectorNodeTransfer> {

    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);

    /** Creates a new instance of SelectorNodeTransferCache */
    public SelectorNodeTransferCache() {
        super();
    }

    public SelectorNodeTransfer getSelectorNodeTransfer(UserVisit userVisit, SelectorNode selectorNode) {
        var selectorNodeTransfer = get(selectorNode);
        
        if(selectorNodeTransfer == null) {
            var selectorNodeDetail = selectorNode.getLastDetail();
            var selectorTransferCache = selectorControl.getSelectorTransferCaches().getSelectorTransferCache();
            var selector = selectorTransferCache.getSelectorTransfer(userVisit, selectorNodeDetail.getSelector());
            var selectorNodeName = selectorNodeDetail.getSelectorNodeName();
            var isRootSelectorNode = selectorNodeDetail.getIsRootSelectorNode();
            var selectorNodeTypeTransferCache = selectorControl.getSelectorTransferCaches().getSelectorNodeTypeTransferCache();
            var selectorNodeType = selectorNodeTypeTransferCache.getSelectorNodeTypeTransfer(userVisit, selectorNodeDetail.getSelectorNodeType());
            var negate = selectorNodeDetail.getNegate();
            var description = selectorControl.getBestSelectorNodeDescription(selectorNode, getLanguage(userVisit));
            
            selectorNodeTransfer = new SelectorNodeTransfer(selector, selectorNodeName, isRootSelectorNode, selectorNodeType,
            negate, description);
            put(userVisit, selectorNode, selectorNodeTransfer);
        }
        
        return selectorNodeTransfer;
    }
    
}
