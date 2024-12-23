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

import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class SelectorTransferCaches
        extends BaseTransferCaches {
    
    protected SelectorControl selectorControl;
    
    protected SelectorKindTransferCache filterKindTransferCache;
    protected SelectorKindDescriptionTransferCache filterKindDescriptionTransferCache;
    protected SelectorTypeTransferCache filterTypeTransferCache;
    protected SelectorTypeDescriptionTransferCache filterTypeDescriptionTransferCache;
    protected SelectorTransferCache selectorTransferCache;
    protected SelectorDescriptionTransferCache selectorDescriptionTransferCache;
    protected SelectorNodeDescriptionTransferCache selectorNodeDescriptionTransferCache;
    protected SelectorNodeTransferCache selectorNodeTransferCache;
    protected SelectorNodeTypeTransferCache selectorNodeTypeTransferCache;
    protected SelectorPartyTransferCache selectorPartyTransferCache;
    
    /** Creates a new instance of SelectorTransferCaches */
    public SelectorTransferCaches(UserVisit userVisit, SelectorControl selectorControl) {
        super(userVisit);
        
        this.selectorControl = selectorControl;
    }
    
    public SelectorKindTransferCache getSelectorKindTransferCache() {
        if(filterKindTransferCache == null)
            filterKindTransferCache = new SelectorKindTransferCache(userVisit, selectorControl);

        return filterKindTransferCache;
    }

    public SelectorKindDescriptionTransferCache getSelectorKindDescriptionTransferCache() {
        if(filterKindDescriptionTransferCache == null)
            filterKindDescriptionTransferCache = new SelectorKindDescriptionTransferCache(userVisit, selectorControl);

        return filterKindDescriptionTransferCache;
    }

    public SelectorTypeTransferCache getSelectorTypeTransferCache() {
        if(filterTypeTransferCache == null)
            filterTypeTransferCache = new SelectorTypeTransferCache(userVisit, selectorControl);

        return filterTypeTransferCache;
    }

    public SelectorTypeDescriptionTransferCache getSelectorTypeDescriptionTransferCache() {
        if(filterTypeDescriptionTransferCache == null)
            filterTypeDescriptionTransferCache = new SelectorTypeDescriptionTransferCache(userVisit, selectorControl);

        return filterTypeDescriptionTransferCache;
    }

    public SelectorTransferCache getSelectorTransferCache() {
        if(selectorTransferCache == null)
            selectorTransferCache = new SelectorTransferCache(userVisit, selectorControl);
        
        return selectorTransferCache;
    }
    
    public SelectorDescriptionTransferCache getSelectorDescriptionTransferCache() {
        if(selectorDescriptionTransferCache == null)
            selectorDescriptionTransferCache = new SelectorDescriptionTransferCache(userVisit, selectorControl);
        
        return selectorDescriptionTransferCache;
    }
    
    public SelectorNodeDescriptionTransferCache getSelectorNodeDescriptionTransferCache() {
        if(selectorNodeDescriptionTransferCache == null)
            selectorNodeDescriptionTransferCache = new SelectorNodeDescriptionTransferCache(userVisit, selectorControl);
        
        return selectorNodeDescriptionTransferCache;
    }
    
    public SelectorNodeTransferCache getSelectorNodeTransferCache() {
        if(selectorNodeTransferCache == null)
            selectorNodeTransferCache = new SelectorNodeTransferCache(userVisit, selectorControl);
        
        return selectorNodeTransferCache;
    }
    
    public SelectorNodeTypeTransferCache getSelectorNodeTypeTransferCache() {
        if(selectorNodeTypeTransferCache == null)
            selectorNodeTypeTransferCache = new SelectorNodeTypeTransferCache(userVisit, selectorControl);
        
        return selectorNodeTypeTransferCache;
    }
    
    public SelectorPartyTransferCache getSelectorPartyTransferCache() {
        if(selectorPartyTransferCache == null)
            selectorPartyTransferCache = new SelectorPartyTransferCache(userVisit, selectorControl);
        
        return selectorPartyTransferCache;
    }
    
}
