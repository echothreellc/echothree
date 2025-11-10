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

import com.echothree.model.control.selector.common.transfer.SelectorKindDescriptionTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorKindDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SelectorKindDescriptionTransferCache
        extends BaseSelectorDescriptionTransferCache<SelectorKindDescription, SelectorKindDescriptionTransfer> {
    
    /** Creates a new instance of SelectorKindDescriptionTransferCache */
    public SelectorKindDescriptionTransferCache(UserVisit userVisit, SelectorControl selectorControl) {
        super(userVisit, selectorControl);
    }
    
    public SelectorKindDescriptionTransfer getSelectorKindDescriptionTransfer(SelectorKindDescription selectorKindDescription) {
        var selectorKindDescriptionTransfer = get(selectorKindDescription);
        
        if(selectorKindDescriptionTransfer == null) {
            var selectorKindTransfer = selectorControl.getSelectorKindTransfer(userVisit, selectorKindDescription.getSelectorKind());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, selectorKindDescription.getLanguage());
            
            selectorKindDescriptionTransfer = new SelectorKindDescriptionTransfer(languageTransfer, selectorKindTransfer, selectorKindDescription.getDescription());
            put(userVisit, selectorKindDescription, selectorKindDescriptionTransfer);
        }
        
        return selectorKindDescriptionTransfer;
    }
    
}
