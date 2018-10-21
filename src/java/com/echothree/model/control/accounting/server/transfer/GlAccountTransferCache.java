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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.remote.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.remote.transfer.GlAccountCategoryTransfer;
import com.echothree.model.control.accounting.remote.transfer.GlAccountClassTransfer;
import com.echothree.model.control.accounting.remote.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.remote.transfer.GlAccountTypeTransfer;
import com.echothree.model.control.accounting.remote.transfer.GlResourceTypeTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.accounting.server.entity.GlAccountDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class GlAccountTransferCache
        extends BaseAccountingTransferCache<GlAccount, GlAccountTransfer> {
    
    /** Creates a new instance of GlAccountTransferCache */
    public GlAccountTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public GlAccountTransfer getTransfer(GlAccount glAccount) {
        GlAccountTransfer glAccountTransfer = get(glAccount);
        
        if(glAccountTransfer == null) {
            GlAccountDetail glAccountDetail = glAccount.getLastDetail();
            String glAccountName = glAccountDetail.getGlAccountName();
            GlAccount parentGlAccount = glAccountDetail.getParentGlAccount();
            GlAccountTransfer parentGlAccountTransfer = parentGlAccount == null? null: getTransfer(parentGlAccount);
            GlAccountTypeTransfer glAccountTypeTransfer = accountingControl.getGlAccountTypeTransfer(userVisit, glAccountDetail.getGlAccountType());
            GlAccountClassTransfer glAccountClassTransfer = accountingControl.getGlAccountClassTransfer(userVisit, glAccountDetail.getGlAccountClass());
            GlAccountCategory glAccountCategory = glAccountDetail.getGlAccountCategory();
            GlAccountCategoryTransfer glAccountCategoryTransfer = glAccountCategory == null? null: accountingControl.getGlAccountCategoryTransfer(userVisit, glAccountCategory);
            GlResourceTypeTransfer glResourceTypeTransfer = accountingControl.getGlResourceTypeTransfer(userVisit, glAccountDetail.getGlResourceType());
            CurrencyTransfer currencyTransfer = accountingControl.getCurrencyTransfer(userVisit, glAccountDetail.getCurrency());
            Boolean isDefault = glAccountDetail.getIsDefault();
            String description = accountingControl.getBestGlAccountDescription(glAccount, getLanguage());
            
            glAccountTransfer = new GlAccountTransfer(glAccountName, parentGlAccountTransfer, glAccountTypeTransfer,
                    glAccountClassTransfer, glAccountCategoryTransfer, glResourceTypeTransfer, currencyTransfer, isDefault,
                    description);
            put(glAccount, glAccountTransfer);
        }
        
        return glAccountTransfer;
    }
    
}
