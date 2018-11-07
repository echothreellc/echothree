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

import com.echothree.model.control.accounting.common.transfer.GlAccountTypeTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.data.accounting.server.entity.GlAccountType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class GlAccountTypeTransferCache
        extends BaseAccountingTransferCache<GlAccountType, GlAccountTypeTransfer> {
    
    /** Creates a new instance of GlAccountTypeTransferCache */
    public GlAccountTypeTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
    }
    
    @Override
    public GlAccountTypeTransfer getTransfer(GlAccountType glAccountType) {
        GlAccountTypeTransfer glAccountTypeTransfer = get(glAccountType);
        
        if(glAccountTypeTransfer == null) {
            String glAccountTypeName = glAccountType.getGlAccountTypeName();
            Boolean isDefault = glAccountType.getIsDefault();
            Integer sortOrder = glAccountType.getSortOrder();
            String description = accountingControl.getBestGlAccountTypeDescription(glAccountType, getLanguage());
            
            glAccountTypeTransfer = new GlAccountTypeTransfer(glAccountTypeName, isDefault, sortOrder, description);
            put(glAccountType, glAccountTypeTransfer);
        }
        return glAccountTypeTransfer;
    }
    
}
