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

package com.echothree.model.control.selector.server.transfer;

import com.echothree.model.control.selector.common.transfer.SelectorDescriptionTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SelectorDescriptionTransferCache
        extends BaseSelectorDescriptionTransferCache<SelectorDescription, SelectorDescriptionTransfer> {

    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);

    /** Creates a new instance of SelectorDescriptionTransferCache */
    protected SelectorDescriptionTransferCache() {
        super();
    }
    
    public SelectorDescriptionTransfer getSelectorDescriptionTransfer(UserVisit userVisit, SelectorDescription selectorDescription) {
        var selectorDescriptionTransfer = get(selectorDescription);
        
        if(selectorDescriptionTransfer == null) {
            var selectorTransfer = selectorControl.getSelectorTransfer(userVisit, selectorDescription.getSelector());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, selectorDescription.getLanguage());
            
            selectorDescriptionTransfer = new SelectorDescriptionTransfer(languageTransfer, selectorTransfer, selectorDescription.getDescription());
            put(userVisit, selectorDescription, selectorDescriptionTransfer);
        }
        
        return selectorDescriptionTransfer;
    }
    
}
