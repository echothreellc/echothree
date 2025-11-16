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

import com.echothree.model.control.financial.common.transfer.FinancialAccountAliasTypeTransfer;
import com.echothree.model.control.financial.server.control.FinancialControl;
import com.echothree.model.data.financial.server.entity.FinancialAccountAliasType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FinancialAccountAliasTypeTransferCache
        extends BaseFinancialTransferCache<FinancialAccountAliasType, FinancialAccountAliasTypeTransfer> {

    FinancialControl financialControl = Session.getModelController(FinancialControl.class);

    /** Creates a new instance of FinancialAccountAliasTypeTransferCache */
    protected FinancialAccountAliasTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public FinancialAccountAliasTypeTransfer getFinancialAccountAliasTypeTransfer(UserVisit userVisit, FinancialAccountAliasType financialAccountAliasType) {
        var financialAccountAliasTypeTransfer = get(financialAccountAliasType);
        
        if(financialAccountAliasTypeTransfer == null) {
            var financialAccountAliasTypeDetail = financialAccountAliasType.getLastDetail();
            var financialAccountType = financialControl.getFinancialAccountTypeTransfer(userVisit, financialAccountAliasTypeDetail.getFinancialAccountType());
            var financialAccountAliasTypeName = financialAccountAliasTypeDetail.getFinancialAccountAliasTypeName();
            var validationPattern = financialAccountAliasTypeDetail.getValidationPattern();
            var isDefault = financialAccountAliasTypeDetail.getIsDefault();
            var sortOrder = financialAccountAliasTypeDetail.getSortOrder();
            var description = financialControl.getBestFinancialAccountAliasTypeDescription(financialAccountAliasType, getLanguage(userVisit));
            
            financialAccountAliasTypeTransfer = new FinancialAccountAliasTypeTransfer(financialAccountType, financialAccountAliasTypeName, validationPattern, isDefault, sortOrder, description);
            put(userVisit, financialAccountAliasType, financialAccountAliasTypeTransfer);
        }
        
        return financialAccountAliasTypeTransfer;
    }
    
}
