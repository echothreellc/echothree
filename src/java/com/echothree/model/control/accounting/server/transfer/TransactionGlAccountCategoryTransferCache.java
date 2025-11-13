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

import com.echothree.model.control.accounting.common.transfer.TransactionGlAccountCategoryTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TransactionGlAccountCategoryTransferCache
        extends BaseAccountingTransferCache<TransactionGlAccountCategory, TransactionGlAccountCategoryTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);

    /** Creates a new instance of TransactionGlAccountCategoryTransferCache */
    public TransactionGlAccountCategoryTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public TransactionGlAccountCategoryTransfer getTransfer(UserVisit userVisit, TransactionGlAccountCategory transactionGlAccountCategory) {
        var transactionGlAccountCategoryTransfer = get(transactionGlAccountCategory);
        
        if(transactionGlAccountCategoryTransfer == null) {
            var transactionGlAccountCategoryDetail = transactionGlAccountCategory.getLastDetail();
            var transactionTypeTransfer = accountingControl.getTransactionTypeTransfer(userVisit, transactionGlAccountCategoryDetail.getTransactionType());
            var transactionGlAccountCategoryName = transactionGlAccountCategoryDetail.getTransactionGlAccountCategoryName();
            var glAccountCategory = transactionGlAccountCategoryDetail.getGlAccountCategory();
            var glAccountCategoryTransfer = glAccountCategory == null? null: accountingControl.getGlAccountCategoryTransfer(userVisit, glAccountCategory);
            var sortOrder = transactionGlAccountCategoryDetail.getSortOrder();
            var transactionGlAccount = accountingControl.getTransactionGlAccount(transactionGlAccountCategory);
            var glAccountTransfer = transactionGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, transactionGlAccount.getGlAccount());
            var description = accountingControl.getBestTransactionGlAccountCategoryDescription(transactionGlAccountCategory, getLanguage(userVisit));
            
            transactionGlAccountCategoryTransfer = new TransactionGlAccountCategoryTransfer(transactionTypeTransfer, transactionGlAccountCategoryName,
                    glAccountCategoryTransfer, sortOrder, glAccountTransfer, description);
            put(userVisit, transactionGlAccountCategory, transactionGlAccountCategoryTransfer);
        }
        
        return transactionGlAccountCategoryTransfer;
    }
    
}
