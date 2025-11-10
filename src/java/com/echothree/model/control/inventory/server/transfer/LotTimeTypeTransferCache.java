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

package com.echothree.model.control.inventory.server.transfer;

import com.echothree.model.control.inventory.common.transfer.LotTimeTypeTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.inventory.server.control.LotTimeControl;
import com.echothree.model.data.inventory.server.entity.LotTimeType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class LotTimeTypeTransferCache
        extends BaseInventoryTransferCache<LotTimeType, LotTimeTypeTransfer> {

    LotTimeControl lotTimeControl = Session.getModelController(LotTimeControl.class);

    /** Creates a new instance of LotTimeTypeTransferCache */
    public LotTimeTypeTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public LotTimeTypeTransfer getTransfer(LotTimeType lotTimeType) {
        var lotTimeTypeTransfer = get(lotTimeType);
        
        if(lotTimeTypeTransfer == null) {
            var lotTimeTypeDetail = lotTimeType.getLastDetail();
            var lotTimeTypeName = lotTimeTypeDetail.getLotTimeTypeName();
            var isDefault = lotTimeTypeDetail.getIsDefault();
            var sortOrder = lotTimeTypeDetail.getSortOrder();
            var description = lotTimeControl.getBestLotTimeTypeDescription(lotTimeType, getLanguage(userVisit));
            
            lotTimeTypeTransfer = new LotTimeTypeTransfer(lotTimeTypeName, isDefault, sortOrder, description);
            put(userVisit, lotTimeType, lotTimeTypeTransfer);
        }
        
        return lotTimeTypeTransfer;
    }
    
}
