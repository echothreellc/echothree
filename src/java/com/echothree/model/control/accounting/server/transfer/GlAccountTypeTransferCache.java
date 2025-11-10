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

import com.echothree.model.control.accounting.common.transfer.GlAccountTypeTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.GlAccountType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class GlAccountTypeTransferCache
        extends BaseAccountingTransferCache<GlAccountType, GlAccountTypeTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);

    /** Creates a new instance of GlAccountTypeTransferCache */
    public GlAccountTypeTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    @Override
    public GlAccountTypeTransfer getTransfer(GlAccountType glAccountType) {
        var glAccountTypeTransfer = get(glAccountType);
        
        if(glAccountTypeTransfer == null) {
            var glAccountTypeName = glAccountType.getGlAccountTypeName();
            var isDefault = glAccountType.getIsDefault();
            var sortOrder = glAccountType.getSortOrder();
            var description = accountingControl.getBestGlAccountTypeDescription(glAccountType, getLanguage(userVisit));
            
            glAccountTypeTransfer = new GlAccountTypeTransfer(glAccountTypeName, isDefault, sortOrder, description);
            put(userVisit, glAccountType, glAccountTypeTransfer);
        }
        return glAccountTypeTransfer;
    }
    
}
