// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.control.selector.remote.transfer.SelectorTypeDescriptionTransfer;
import com.echothree.model.control.selector.remote.transfer.SelectorTypeTransfer;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class SelectorTypeDescriptionTransferCache
        extends BaseSelectorDescriptionTransferCache<SelectorTypeDescription, SelectorTypeDescriptionTransfer> {
    
    /** Creates a new instance of SelectorTypeDescriptionTransferCache */
    public SelectorTypeDescriptionTransferCache(UserVisit userVisit, SelectorControl selectorControl) {
        super(userVisit, selectorControl);
    }
    
    public SelectorTypeDescriptionTransfer getSelectorTypeDescriptionTransfer(SelectorTypeDescription selectorTypeDescription) {
        SelectorTypeDescriptionTransfer selectorTypeDescriptionTransfer = get(selectorTypeDescription);
        
        if(selectorTypeDescriptionTransfer == null) {
            SelectorTypeTransfer selectorTypeTransfer = selectorControl.getSelectorTypeTransfer(userVisit, selectorTypeDescription.getSelectorType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, selectorTypeDescription.getLanguage());
            
            selectorTypeDescriptionTransfer = new SelectorTypeDescriptionTransfer(languageTransfer, selectorTypeTransfer, selectorTypeDescription.getDescription());
            put(selectorTypeDescription, selectorTypeDescriptionTransfer);
        }
        
        return selectorTypeDescriptionTransfer;
    }
    
}
