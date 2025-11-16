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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class SelectorTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    SelectorKindTransferCache filterKindTransferCache;
    
    @Inject
    SelectorKindDescriptionTransferCache filterKindDescriptionTransferCache;
    
    @Inject
    SelectorTypeTransferCache filterTypeTransferCache;
    
    @Inject
    SelectorTypeDescriptionTransferCache filterTypeDescriptionTransferCache;
    
    @Inject
    SelectorTransferCache selectorTransferCache;
    
    @Inject
    SelectorDescriptionTransferCache selectorDescriptionTransferCache;
    
    @Inject
    SelectorNodeDescriptionTransferCache selectorNodeDescriptionTransferCache;
    
    @Inject
    SelectorNodeTransferCache selectorNodeTransferCache;
    
    @Inject
    SelectorNodeTypeTransferCache selectorNodeTypeTransferCache;
    
    @Inject
    SelectorPartyTransferCache selectorPartyTransferCache;

    /** Creates a new instance of SelectorTransferCaches */
    protected SelectorTransferCaches() {
        super();
    }
    
    public SelectorKindTransferCache getSelectorKindTransferCache() {
        return filterKindTransferCache;
    }

    public SelectorKindDescriptionTransferCache getSelectorKindDescriptionTransferCache() {
        return filterKindDescriptionTransferCache;
    }

    public SelectorTypeTransferCache getSelectorTypeTransferCache() {
        return filterTypeTransferCache;
    }

    public SelectorTypeDescriptionTransferCache getSelectorTypeDescriptionTransferCache() {
        return filterTypeDescriptionTransferCache;
    }

    public SelectorTransferCache getSelectorTransferCache() {
        return selectorTransferCache;
    }
    
    public SelectorDescriptionTransferCache getSelectorDescriptionTransferCache() {
        return selectorDescriptionTransferCache;
    }
    
    public SelectorNodeDescriptionTransferCache getSelectorNodeDescriptionTransferCache() {
        return selectorNodeDescriptionTransferCache;
    }
    
    public SelectorNodeTransferCache getSelectorNodeTransferCache() {
        return selectorNodeTransferCache;
    }
    
    public SelectorNodeTypeTransferCache getSelectorNodeTypeTransferCache() {
        return selectorNodeTypeTransferCache;
    }
    
    public SelectorPartyTransferCache getSelectorPartyTransferCache() {
        return selectorPartyTransferCache;
    }
    
}
