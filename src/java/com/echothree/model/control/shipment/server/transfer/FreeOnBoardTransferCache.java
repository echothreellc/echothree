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

package com.echothree.model.control.shipment.server.transfer;

import com.echothree.model.control.shipment.common.transfer.FreeOnBoardTransfer;
import com.echothree.model.control.shipment.server.control.FreeOnBoardControl;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.shipment.server.entity.FreeOnBoardDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class FreeOnBoardTransferCache
        extends BaseShipmentTransferCache<FreeOnBoard, FreeOnBoardTransfer> {

    FreeOnBoardControl freeOnBoardControl = Session.getModelController(FreeOnBoardControl.class);

    /** Creates a new instance of FreeOnBoardTransferCache */
    public FreeOnBoardTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public FreeOnBoardTransfer getTransfer(FreeOnBoard freeOnBoard) {
        FreeOnBoardTransfer freeOnBoardTransfer = get(freeOnBoard);
        
        if(freeOnBoardTransfer == null) {
            FreeOnBoardDetail freeOnBoardDetail = freeOnBoard.getLastDetail();
            String freeOnBoardName = freeOnBoardDetail.getFreeOnBoardName();
            Boolean isDefault = freeOnBoardDetail.getIsDefault();
            Integer sortOrder = freeOnBoardDetail.getSortOrder();
            String description = freeOnBoardControl.getBestFreeOnBoardDescription(freeOnBoard, getLanguage());
            
            freeOnBoardTransfer = new FreeOnBoardTransfer(freeOnBoardName, isDefault, sortOrder, description);
            put(freeOnBoard, freeOnBoardTransfer);
        }
        
        return freeOnBoardTransfer;
    }
    
}
