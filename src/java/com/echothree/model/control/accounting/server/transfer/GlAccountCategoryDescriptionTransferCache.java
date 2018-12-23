// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.accounting.common.transfer.GlAccountCategoryDescriptionTransfer;
import com.echothree.model.control.accounting.common.transfer.GlAccountCategoryTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.accounting.server.entity.GlAccountCategoryDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class GlAccountCategoryDescriptionTransferCache
        extends BaseAccountingDescriptionTransferCache<GlAccountCategoryDescription, GlAccountCategoryDescriptionTransfer> {
    
    /** Creates a new instance of GlAccountCategoryDescriptionTransferCache */
    public GlAccountCategoryDescriptionTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
    }
    
    @Override
    public GlAccountCategoryDescriptionTransfer getTransfer(GlAccountCategoryDescription glAccountCategoryDescription) {
        GlAccountCategoryDescriptionTransfer glAccountCategoryDescriptionTransfer = get(glAccountCategoryDescription);
        
        if(glAccountCategoryDescriptionTransfer == null) {
            GlAccountCategoryTransferCache glAccountCategoryTransferCache = accountingControl.getAccountingTransferCaches(userVisit).getGlAccountCategoryTransferCache();
            GlAccountCategoryTransfer glAccountCategoryTransfer = glAccountCategoryTransferCache.getTransfer(glAccountCategoryDescription.getGlAccountCategory());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, glAccountCategoryDescription.getLanguage());
            
            glAccountCategoryDescriptionTransfer = new GlAccountCategoryDescriptionTransfer(languageTransfer, glAccountCategoryTransfer, glAccountCategoryDescription.getDescription());
            put(glAccountCategoryDescription, glAccountCategoryDescriptionTransfer);
        }
        
        return glAccountCategoryDescriptionTransfer;
    }
    
}
