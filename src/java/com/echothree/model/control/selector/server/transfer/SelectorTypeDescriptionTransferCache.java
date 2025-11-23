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

import com.echothree.model.control.selector.common.transfer.SelectorTypeDescriptionTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.selector.server.entity.SelectorTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SelectorTypeDescriptionTransferCache
        extends BaseSelectorDescriptionTransferCache<SelectorTypeDescription, SelectorTypeDescriptionTransfer> {

    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);

    /** Creates a new instance of SelectorTypeDescriptionTransferCache */
    protected SelectorTypeDescriptionTransferCache() {
        super();
    }
    
    public SelectorTypeDescriptionTransfer getSelectorTypeDescriptionTransfer(UserVisit userVisit, SelectorTypeDescription selectorTypeDescription) {
        var selectorTypeDescriptionTransfer = get(selectorTypeDescription);
        
        if(selectorTypeDescriptionTransfer == null) {
            var selectorTypeTransfer = selectorControl.getSelectorTypeTransfer(userVisit, selectorTypeDescription.getSelectorType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, selectorTypeDescription.getLanguage());
            
            selectorTypeDescriptionTransfer = new SelectorTypeDescriptionTransfer(languageTransfer, selectorTypeTransfer, selectorTypeDescription.getDescription());
            put(userVisit, selectorTypeDescription, selectorTypeDescriptionTransfer);
        }
        
        return selectorTypeDescriptionTransfer;
    }
    
}
