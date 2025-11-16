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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;

public class SelectorTransferCaches
        extends BaseTransferCaches {
    
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
    public SelectorTransferCaches() {
        super();
    }
    
    public SelectorKindTransferCache getSelectorKindTransferCache() {
        if(filterKindTransferCache == null)
            filterKindTransferCache = CDI.current().select(SelectorKindTransferCache.class).get();

        return filterKindTransferCache;
    }

    public SelectorKindDescriptionTransferCache getSelectorKindDescriptionTransferCache() {
        if(filterKindDescriptionTransferCache == null)
            filterKindDescriptionTransferCache = CDI.current().select(SelectorKindDescriptionTransferCache.class).get();

        return filterKindDescriptionTransferCache;
    }

    public SelectorTypeTransferCache getSelectorTypeTransferCache() {
        if(filterTypeTransferCache == null)
            filterTypeTransferCache = CDI.current().select(SelectorTypeTransferCache.class).get();

        return filterTypeTransferCache;
    }

    public SelectorTypeDescriptionTransferCache getSelectorTypeDescriptionTransferCache() {
        if(filterTypeDescriptionTransferCache == null)
            filterTypeDescriptionTransferCache = CDI.current().select(SelectorTypeDescriptionTransferCache.class).get();

        return filterTypeDescriptionTransferCache;
    }

    public SelectorTransferCache getSelectorTransferCache() {
        if(selectorTransferCache == null)
            selectorTransferCache = CDI.current().select(SelectorTransferCache.class).get();
        
        return selectorTransferCache;
    }
    
    public SelectorDescriptionTransferCache getSelectorDescriptionTransferCache() {
        if(selectorDescriptionTransferCache == null)
            selectorDescriptionTransferCache = CDI.current().select(SelectorDescriptionTransferCache.class).get();
        
        return selectorDescriptionTransferCache;
    }
    
    public SelectorNodeDescriptionTransferCache getSelectorNodeDescriptionTransferCache() {
        if(selectorNodeDescriptionTransferCache == null)
            selectorNodeDescriptionTransferCache = CDI.current().select(SelectorNodeDescriptionTransferCache.class).get();
        
        return selectorNodeDescriptionTransferCache;
    }
    
    public SelectorNodeTransferCache getSelectorNodeTransferCache() {
        if(selectorNodeTransferCache == null)
            selectorNodeTransferCache = CDI.current().select(SelectorNodeTransferCache.class).get();
        
        return selectorNodeTransferCache;
    }
    
    public SelectorNodeTypeTransferCache getSelectorNodeTypeTransferCache() {
        if(selectorNodeTypeTransferCache == null)
            selectorNodeTypeTransferCache = CDI.current().select(SelectorNodeTypeTransferCache.class).get();
        
        return selectorNodeTypeTransferCache;
    }
    
    public SelectorPartyTransferCache getSelectorPartyTransferCache() {
        if(selectorPartyTransferCache == null)
            selectorPartyTransferCache = CDI.current().select(SelectorPartyTransferCache.class).get();
        
        return selectorPartyTransferCache;
    }
    
}
