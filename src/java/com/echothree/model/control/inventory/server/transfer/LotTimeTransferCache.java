// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.inventory.common.transfer.LotTimeTransfer;
import com.echothree.model.control.inventory.common.transfer.LotTimeTypeTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.inventory.server.control.LotTimeControl;
import com.echothree.model.data.inventory.server.entity.LotTime;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class LotTimeTransferCache
        extends BaseInventoryTransferCache<LotTime, LotTimeTransfer> {

    LotTimeControl lotTimeControl = Session.getModelController(LotTimeControl.class);

    /** Creates a new instance of LotTimeTransferCache */
    public LotTimeTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
    }
    
    @Override
    public LotTimeTransfer getTransfer(LotTime lotTime) {
        LotTimeTransfer lotTimeTransfer = get(lotTime);
        
        if(lotTimeTransfer == null) {
            LotTimeTypeTransfer lotTimeType = lotTimeControl.getLotTimeTypeTransfer(userVisit, lotTime.getLotTimeType());
            Long unformattedTime = lotTime.getTime();
            String time = formatTypicalDateTime(unformattedTime);
            
            lotTimeTransfer = new LotTimeTransfer(lotTimeType, unformattedTime, time);
            put(lotTime, lotTimeTransfer);
        }
        
        return lotTimeTransfer;
    }
    
}
