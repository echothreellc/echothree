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

import com.echothree.model.control.accounting.common.transfer.TransactionGlAccountCategoryDescriptionTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategoryDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TransactionGlAccountCategoryDescriptionTransferCache
        extends BaseAccountingDescriptionTransferCache<TransactionGlAccountCategoryDescription, TransactionGlAccountCategoryDescriptionTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);

    /** Creates a new instance of TransactionGlAccountCategoryDescriptionTransferCache */
    public TransactionGlAccountCategoryDescriptionTransferCache() {
        super();
    }
    
    @Override
    public TransactionGlAccountCategoryDescriptionTransfer getTransfer(UserVisit userVisit, TransactionGlAccountCategoryDescription transactionGlAccountCategoryDescription) {
        var transactionGlAccountCategoryDescriptionTransfer = get(transactionGlAccountCategoryDescription);
        
        if(transactionGlAccountCategoryDescriptionTransfer == null) {
            var transactionGlAccountCategoryTransferCache = accountingControl.getAccountingTransferCaches().getTransactionGlAccountCategoryTransferCache();
            var transactionGlAccountCategoryTransfer = transactionGlAccountCategoryTransferCache.getTransfer(transactionGlAccountCategoryDescription.getTransactionGlAccountCategory());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, transactionGlAccountCategoryDescription.getLanguage());
            
            transactionGlAccountCategoryDescriptionTransfer = new TransactionGlAccountCategoryDescriptionTransfer(languageTransfer, transactionGlAccountCategoryTransfer, transactionGlAccountCategoryDescription.getDescription());
            put(userVisit, transactionGlAccountCategoryDescription, transactionGlAccountCategoryDescriptionTransfer);
        }
        
        return transactionGlAccountCategoryDescriptionTransfer;
    }
    
}
