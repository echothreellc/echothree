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

package com.echothree.model.control.offer.server.transfer;

import com.echothree.model.control.offer.common.transfer.UseTransfer;
import com.echothree.model.control.offer.server.control.UseControl;
import com.echothree.model.control.offer.server.control.UseTypeControl;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class UseTransferCache
        extends BaseOfferTransferCache<Use, UseTransfer> {

    UseControl useControl = Session.getModelController(UseControl.class);
    UseTypeControl useTypeControl = Session.getModelController(UseTypeControl.class);

    /** Creates a new instance of UseTransferCache */
    public UseTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public UseTransfer getUseTransfer(UserVisit userVisit, Use use) {
        var useTransfer = get(use);
        
        if(useTransfer == null) {
            var useDetail = use.getLastDetail();
            var useName = useDetail.getUseName();
            var useType = useTypeControl.getUseTypeTransfer(userVisit, useDetail.getUseType());
            var isDefault = useDetail.getIsDefault();
            var sortOrder = useDetail.getSortOrder();
            var description = useControl.getBestUseDescription(use, getLanguage(userVisit));
            
            useTransfer = new UseTransfer(useName, useType, isDefault, sortOrder, description);
            put(userVisit, use, useTransfer);
        }
        
        return useTransfer;
    }
    
}
