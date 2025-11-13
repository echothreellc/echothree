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

package com.echothree.model.control.financial.server.transfer;

import com.echothree.model.control.financial.common.transfer.FinancialAccountTransactionTypeDescriptionTransfer;
import com.echothree.model.control.financial.server.control.FinancialControl;
import com.echothree.model.data.financial.server.entity.FinancialAccountTransactionTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class FinancialAccountTransactionTypeDescriptionTransferCache
        extends BaseFinancialDescriptionTransferCache<FinancialAccountTransactionTypeDescription, FinancialAccountTransactionTypeDescriptionTransfer> {
    
    /** Creates a new instance of FinancialAccountTransactionTypeDescriptionTransferCache */
    public FinancialAccountTransactionTypeDescriptionTransferCache(FinancialControl financialControl) {
        super(financialControl);
    }
    
    public FinancialAccountTransactionTypeDescriptionTransfer getFinancialAccountTransactionTypeDescriptionTransfer(UserVisit userVisit, FinancialAccountTransactionTypeDescription financialAccountTransactionTypeDescription) {
        var financialAccountTransactionTypeDescriptionTransfer = get(financialAccountTransactionTypeDescription);
        
        if(financialAccountTransactionTypeDescriptionTransfer == null) {
            var financialAccountTransactionTypeTransfer = financialControl.getFinancialAccountTransactionTypeTransfer(userVisit, financialAccountTransactionTypeDescription.getFinancialAccountTransactionType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, financialAccountTransactionTypeDescription.getLanguage());
            
            financialAccountTransactionTypeDescriptionTransfer = new FinancialAccountTransactionTypeDescriptionTransfer(languageTransfer, financialAccountTransactionTypeTransfer, financialAccountTransactionTypeDescription.getDescription());
            put(userVisit, financialAccountTransactionTypeDescription, financialAccountTransactionTypeDescriptionTransfer);
        }
        
        return financialAccountTransactionTypeDescriptionTransfer;
    }
    
}
