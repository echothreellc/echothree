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

import com.echothree.model.control.accounting.common.transfer.GlAccountClassTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.GlAccountClass;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class GlAccountClassTransferCache
        extends BaseAccountingTransferCache<GlAccountClass, GlAccountClassTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);

    /** Creates a new instance of GlAccountClassTransferCache */
    public GlAccountClassTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public GlAccountClassTransfer getTransfer(UserVisit userVisit, GlAccountClass glAccountClass) {
        var glAccountClassTransfer = get(glAccountClass);
        
        if(glAccountClassTransfer == null) {
            var glAccountClassDetail = glAccountClass.getLastDetail();
            var glAccountClassName = glAccountClassDetail.getGlAccountClassName();
            var parentGlAccountClass = glAccountClassDetail.getParentGlAccountClass();
            var parentGlAccountClassTransfer = parentGlAccountClass == null? null: getTransfer(parentGlAccountClass);
            var isDefault = glAccountClassDetail.getIsDefault();
            var sortOrder = glAccountClassDetail.getSortOrder();
            var description = accountingControl.getBestGlAccountClassDescription(glAccountClass, getLanguage(userVisit));
            
            glAccountClassTransfer = new GlAccountClassTransfer(glAccountClassName, parentGlAccountClassTransfer, isDefault, sortOrder, description);
            put(userVisit, glAccountClass, glAccountClassTransfer);
        }
        
        return glAccountClassTransfer;
    }
    
}
