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

import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class GlAccountTransferCache
        extends BaseAccountingTransferCache<GlAccount, GlAccountTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);

    /** Creates a new instance of GlAccountTransferCache */
    public GlAccountTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public GlAccountTransfer getTransfer(GlAccount glAccount) {
        var glAccountTransfer = get(glAccount);
        
        if(glAccountTransfer == null) {
            var glAccountDetail = glAccount.getLastDetail();
            var glAccountName = glAccountDetail.getGlAccountName();
            var parentGlAccount = glAccountDetail.getParentGlAccount();
            var parentGlAccountTransfer = parentGlAccount == null? null: getTransfer(parentGlAccount);
            var glAccountTypeTransfer = accountingControl.getGlAccountTypeTransfer(userVisit, glAccountDetail.getGlAccountType());
            var glAccountClassTransfer = accountingControl.getGlAccountClassTransfer(userVisit, glAccountDetail.getGlAccountClass());
            var glAccountCategory = glAccountDetail.getGlAccountCategory();
            var glAccountCategoryTransfer = glAccountCategory == null? null: accountingControl.getGlAccountCategoryTransfer(userVisit, glAccountCategory);
            var glResourceTypeTransfer = accountingControl.getGlResourceTypeTransfer(userVisit, glAccountDetail.getGlResourceType());
            var currencyTransfer = accountingControl.getCurrencyTransfer(userVisit, glAccountDetail.getCurrency());
            var isDefault = glAccountDetail.getIsDefault();
            var description = accountingControl.getBestGlAccountDescription(glAccount, getLanguage(userVisit));
            
            glAccountTransfer = new GlAccountTransfer(glAccountName, parentGlAccountTransfer, glAccountTypeTransfer,
                    glAccountClassTransfer, glAccountCategoryTransfer, glResourceTypeTransfer, currencyTransfer, isDefault,
                    description);
            put(userVisit, glAccount, glAccountTransfer);
        }
        
        return glAccountTransfer;
    }
    
}
