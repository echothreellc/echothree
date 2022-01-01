// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.offer.common.transfer.UseTypeTransfer;
import com.echothree.model.control.offer.server.control.UseTypeControl;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.entity.UseTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class UseTypeTransferCache
        extends BaseOfferTransferCache<UseType, UseTypeTransfer> {

    UseTypeControl useTypeControl = Session.getModelController(UseTypeControl.class);

    /** Creates a new instance of UseTypeTransferCache */
    public UseTypeTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }
    
    public UseTypeTransfer getUseTypeTransfer(UseType useType) {
        UseTypeTransfer useTypeTransfer = get(useType);
        
        if(useTypeTransfer == null) {
            UseTypeDetail useTypeDetail = useType.getLastDetail();
            String useTypeName = useTypeDetail.getUseTypeName();
            Boolean isDefault = useTypeDetail.getIsDefault();
            Integer sortOrder = useTypeDetail.getSortOrder();
            String description = useTypeControl.getBestUseTypeDescription(useType, getLanguage());
            
            useTypeTransfer = new UseTypeTransfer(useTypeName, isDefault, sortOrder, description);
            put(useType, useTypeTransfer);
        }
        
        return useTypeTransfer;
    }
    
}
