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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.common.transfer.TransactionEntityRoleTypeDescriptionTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TransactionEntityRoleTypeDescriptionTransferCache
        extends BaseAccountingDescriptionTransferCache<TransactionEntityRoleTypeDescription, TransactionEntityRoleTypeDescriptionTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);

    /** Creates a new instance of TransactionEntityRoleTypeDescriptionTransferCache */
    protected TransactionEntityRoleTypeDescriptionTransferCache() {
        super();
    }
    
    @Override
    public TransactionEntityRoleTypeDescriptionTransfer getTransfer(UserVisit userVisit, TransactionEntityRoleTypeDescription transactionEntityRoleTypeDescription) {
        var transactionEntityRoleTypeDescriptionTransfer = get(transactionEntityRoleTypeDescription);
        
        if(transactionEntityRoleTypeDescriptionTransfer == null) {
            var transactionEntityRoleTypeTransfer = accountingControl.getTransactionEntityRoleTypeTransfer(userVisit, transactionEntityRoleTypeDescription.getTransactionEntityRoleType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, transactionEntityRoleTypeDescription.getLanguage());
            
            transactionEntityRoleTypeDescriptionTransfer = new TransactionEntityRoleTypeDescriptionTransfer(languageTransfer, transactionEntityRoleTypeTransfer, transactionEntityRoleTypeDescription.getDescription());
            put(userVisit, transactionEntityRoleTypeDescription, transactionEntityRoleTypeDescriptionTransfer);
        }
        
        return transactionEntityRoleTypeDescriptionTransfer;
    }
    
}
