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

import com.echothree.model.control.accounting.common.transfer.GlResourceTypeTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.GlResourceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class GlResourceTypeTransferCache
        extends BaseAccountingTransferCache<GlResourceType, GlResourceTypeTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);

    /** Creates a new instance of GlResourceTypeTransferCache */
    public GlResourceTypeTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public GlResourceTypeTransfer getTransfer(GlResourceType glResourceType) {
        var glResourceTypeTransfer = get(glResourceType);
        
        if(glResourceTypeTransfer == null) {
            var glResourceTypeDetail = glResourceType.getLastDetail();
            var glResourceTypeName = glResourceTypeDetail.getGlResourceTypeName();
            var isDefault = glResourceTypeDetail.getIsDefault();
            var sortOrder = glResourceTypeDetail.getSortOrder();
            var description = accountingControl.getBestGlResourceTypeDescription(glResourceType, getLanguage(userVisit));
            
            glResourceTypeTransfer = new GlResourceTypeTransfer(glResourceTypeName, isDefault, sortOrder, description);
            put(userVisit, glResourceType, glResourceTypeTransfer);
        }
        
        return glResourceTypeTransfer;
    }
    
}
