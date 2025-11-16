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

package com.echothree.model.control.term.server.transfer;

import com.echothree.model.control.term.common.transfer.TermDescriptionTransfer;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.term.server.entity.TermDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TermDescriptionTransferCache
        extends BaseTermDescriptionTransferCache<TermDescription, TermDescriptionTransfer> {

    TermControl termControl = Session.getModelController(TermControl.class);

    /** Creates a new instance of TermDescriptionTransferCache */
    protected TermDescriptionTransferCache() {
        super();
    }
    
    public TermDescriptionTransfer getTermDescriptionTransfer(UserVisit userVisit, TermDescription termDescription) {
        var termDescriptionTransfer = get(termDescription);
        
        if(termDescriptionTransfer == null) {
            var termTransferCache = termControl.getTermTransferCaches().getTermTransferCache();
            var termTransfer = termTransferCache.getTermTransfer(userVisit, termDescription.getTerm());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, termDescription.getLanguage());
            
            termDescriptionTransfer = new TermDescriptionTransfer(languageTransfer, termTransfer, termDescription.getDescription());
            put(userVisit, termDescription, termDescriptionTransfer);
        }
        
        return termDescriptionTransfer;
    }
    
}
