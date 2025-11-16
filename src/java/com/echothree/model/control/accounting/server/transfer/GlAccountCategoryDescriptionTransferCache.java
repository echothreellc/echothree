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

import com.echothree.model.control.accounting.common.transfer.GlAccountCategoryDescriptionTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.GlAccountCategoryDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GlAccountCategoryDescriptionTransferCache
        extends BaseAccountingDescriptionTransferCache<GlAccountCategoryDescription, GlAccountCategoryDescriptionTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);

    /** Creates a new instance of GlAccountCategoryDescriptionTransferCache */
    public GlAccountCategoryDescriptionTransferCache() {
        super();
    }
    
    @Override
    public GlAccountCategoryDescriptionTransfer getTransfer(UserVisit userVisit, GlAccountCategoryDescription glAccountCategoryDescription) {
        var glAccountCategoryDescriptionTransfer = get(glAccountCategoryDescription);
        
        if(glAccountCategoryDescriptionTransfer == null) {
            var glAccountCategoryTransferCache = accountingControl.getAccountingTransferCaches().getGlAccountCategoryTransferCache();
            var glAccountCategoryTransfer = glAccountCategoryTransferCache.getTransfer(userVisit, glAccountCategoryDescription.getGlAccountCategory());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, glAccountCategoryDescription.getLanguage());
            
            glAccountCategoryDescriptionTransfer = new GlAccountCategoryDescriptionTransfer(languageTransfer, glAccountCategoryTransfer, glAccountCategoryDescription.getDescription());
            put(userVisit, glAccountCategoryDescription, glAccountCategoryDescriptionTransfer);
        }
        
        return glAccountCategoryDescriptionTransfer;
    }
    
}
