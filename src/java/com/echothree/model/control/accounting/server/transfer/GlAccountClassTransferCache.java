// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.data.accounting.server.entity.GlAccountClassDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class GlAccountClassTransferCache
        extends BaseAccountingTransferCache<GlAccountClass, GlAccountClassTransfer> {
    
    /** Creates a new instance of GlAccountClassTransferCache */
    public GlAccountClassTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public GlAccountClassTransfer getTransfer(GlAccountClass glAccountClass) {
        GlAccountClassTransfer glAccountClassTransfer = get(glAccountClass);
        
        if(glAccountClassTransfer == null) {
            GlAccountClassDetail glAccountClassDetail = glAccountClass.getLastDetail();
            String glAccountClassName = glAccountClassDetail.getGlAccountClassName();
            GlAccountClass parentGlAccountClass = glAccountClassDetail.getParentGlAccountClass();
            GlAccountClassTransfer parentGlAccountClassTransfer = parentGlAccountClass == null? null: getTransfer(parentGlAccountClass);
            Boolean isDefault = glAccountClassDetail.getIsDefault();
            Integer sortOrder = glAccountClassDetail.getSortOrder();
            String description = accountingControl.getBestGlAccountClassDescription(glAccountClass, getLanguage());
            
            glAccountClassTransfer = new GlAccountClassTransfer(glAccountClassName, parentGlAccountClassTransfer, isDefault, sortOrder, description);
            put(glAccountClass, glAccountClassTransfer);
        }
        
        return glAccountClassTransfer;
    }
    
}
