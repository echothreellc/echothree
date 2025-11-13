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

import com.echothree.model.control.financial.common.transfer.FinancialAccountTypeDescriptionTransfer;
import com.echothree.model.control.financial.server.control.FinancialControl;
import com.echothree.model.data.financial.server.entity.FinancialAccountTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class FinancialAccountTypeDescriptionTransferCache
        extends BaseFinancialDescriptionTransferCache<FinancialAccountTypeDescription, FinancialAccountTypeDescriptionTransfer> {
    
    /** Creates a new instance of FinancialAccountTypeDescriptionTransferCache */
    public FinancialAccountTypeDescriptionTransferCache(FinancialControl financialControl) {
        super(financialControl);
    }
    
    public FinancialAccountTypeDescriptionTransfer getFinancialAccountTypeDescriptionTransfer(FinancialAccountTypeDescription financialAccountTypeDescription) {
        var financialAccountTypeDescriptionTransfer = get(financialAccountTypeDescription);
        
        if(financialAccountTypeDescriptionTransfer == null) {
            var financialAccountTypeTransfer = financialControl.getFinancialAccountTypeTransfer(userVisit, financialAccountTypeDescription.getFinancialAccountType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, financialAccountTypeDescription.getLanguage());
            
            financialAccountTypeDescriptionTransfer = new FinancialAccountTypeDescriptionTransfer(languageTransfer, financialAccountTypeTransfer, financialAccountTypeDescription.getDescription());
            put(userVisit, financialAccountTypeDescription, financialAccountTypeDescriptionTransfer);
        }
        
        return financialAccountTypeDescriptionTransfer;
    }
    
}
