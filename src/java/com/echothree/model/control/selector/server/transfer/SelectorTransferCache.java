// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorTypeTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SelectorTransferCache
        extends BaseSelectorTransferCache<Selector, SelectorTransfer> {
    
    /** Creates a new instance of SelectorTransferCache */
    public SelectorTransferCache(UserVisit userVisit, SelectorControl selectorControl) {
        super(userVisit, selectorControl);
        
        setIncludeEntityInstance(true);
    }
    
    public SelectorTransfer getSelectorTransfer(Selector selector) {
        SelectorTransfer selectorTransfer = get(selector);
        
        if(selectorTransfer == null) {
            SelectorDetail selectorDetail = selector.getLastDetail();
            SelectorTypeTransferCache selectorTypeTransferCache = selectorControl.getSelectorTransferCaches(userVisit).getSelectorTypeTransferCache();
            SelectorTypeTransfer selectorType = selectorTypeTransferCache.getSelectorTypeTransfer(selectorDetail.getSelectorType());
            String selectorName = selectorDetail.getSelectorName();
            Boolean isDefault = selectorDetail.getIsDefault();
            Integer sortOrder = selectorDetail.getSortOrder();
            String description = selectorControl.getBestSelectorDescription(selector, getLanguage());
            
            selectorTransfer = new SelectorTransfer(selectorType, selectorName, isDefault, sortOrder, description);
            put(selector, selectorTransfer);
        }
        return selectorTransfer;
    }
    
}
