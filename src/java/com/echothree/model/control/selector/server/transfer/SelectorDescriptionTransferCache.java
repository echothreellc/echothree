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

import com.echothree.model.control.selector.common.transfer.SelectorDescriptionTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SelectorDescriptionTransferCache
        extends BaseSelectorDescriptionTransferCache<SelectorDescription, SelectorDescriptionTransfer> {
    
    /** Creates a new instance of SelectorDescriptionTransferCache */
    public SelectorDescriptionTransferCache(SelectorControl selectorControl) {
        super(selectorControl);
    }
    
    public SelectorDescriptionTransfer getSelectorDescriptionTransfer(SelectorDescription selectorDescription) {
        var selectorDescriptionTransfer = get(selectorDescription);
        
        if(selectorDescriptionTransfer == null) {
            var selectorTransferCache = selectorControl.getSelectorTransferCaches(userVisit).getSelectorTransferCache();
            var selectorTransfer = selectorTransferCache.getSelectorTransfer(selectorDescription.getSelector());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, selectorDescription.getLanguage());
            
            selectorDescriptionTransfer = new SelectorDescriptionTransfer(languageTransfer, selectorTransfer, selectorDescription.getDescription());
            put(userVisit, selectorDescription, selectorDescriptionTransfer);
        }
        
        return selectorDescriptionTransfer;
    }
    
}
