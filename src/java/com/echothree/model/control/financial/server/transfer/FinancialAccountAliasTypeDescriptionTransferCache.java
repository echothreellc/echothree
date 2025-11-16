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

import com.echothree.model.control.financial.common.transfer.FinancialAccountAliasTypeDescriptionTransfer;
import com.echothree.model.control.financial.server.control.FinancialControl;
import com.echothree.model.data.financial.server.entity.FinancialAccountAliasTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FinancialAccountAliasTypeDescriptionTransferCache
        extends BaseFinancialDescriptionTransferCache<FinancialAccountAliasTypeDescription, FinancialAccountAliasTypeDescriptionTransfer> {

    FinancialControl financialControl = Session.getModelController(FinancialControl.class);

    /** Creates a new instance of FinancialAccountAliasTypeDescriptionTransferCache */
    protected FinancialAccountAliasTypeDescriptionTransferCache() {
        super();
    }
    
    public FinancialAccountAliasTypeDescriptionTransfer getFinancialAccountAliasTypeDescriptionTransfer(UserVisit userVisit, FinancialAccountAliasTypeDescription financialAccountAliasTypeDescription) {
        var financialAccountAliasTypeDescriptionTransfer = get(financialAccountAliasTypeDescription);
        
        if(financialAccountAliasTypeDescriptionTransfer == null) {
            var financialAccountAliasTypeTransfer = financialControl.getFinancialAccountAliasTypeTransfer(userVisit, financialAccountAliasTypeDescription.getFinancialAccountAliasType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, financialAccountAliasTypeDescription.getLanguage());
            
            financialAccountAliasTypeDescriptionTransfer = new FinancialAccountAliasTypeDescriptionTransfer(languageTransfer, financialAccountAliasTypeTransfer, financialAccountAliasTypeDescription.getDescription());
            put(userVisit, financialAccountAliasTypeDescription, financialAccountAliasTypeDescriptionTransfer);
        }
        
        return financialAccountAliasTypeDescriptionTransfer;
    }
    
}
