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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.common.transfer.GlAccountDescriptionTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.GlAccountDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class GlAccountDescriptionTransferCache
        extends BaseAccountingDescriptionTransferCache<GlAccountDescription, GlAccountDescriptionTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);

    /** Creates a new instance of GlAccountDescriptionTransferCache */
    public GlAccountDescriptionTransferCache() {
        super();
    }
    
    @Override
    public GlAccountDescriptionTransfer getTransfer(UserVisit userVisit, GlAccountDescription glAccountDescription) {
        var glAccountDescriptionTransfer = get(glAccountDescription);
        
        if(glAccountDescriptionTransfer == null) {
            var glAccountTransferCache = accountingControl.getAccountingTransferCaches().getGlAccountTransferCache();
            var glAccountTransfer = glAccountTransferCache.getTransfer(glAccountDescription.getGlAccount());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, glAccountDescription.getLanguage());
            
            glAccountDescriptionTransfer = new GlAccountDescriptionTransfer(languageTransfer, glAccountTransfer, glAccountDescription.getDescription());
            put(userVisit, glAccountDescription, glAccountDescriptionTransfer);
        }
        
        return glAccountDescriptionTransfer;
    }
    
}
