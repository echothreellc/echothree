// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.shipment.common.transfer.ShipmentTimeTypeTransfer;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.ShipmentTimeType;
import com.echothree.model.data.shipment.server.entity.ShipmentTimeTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ShipmentTimeTypeTransferCache
        extends BaseShipmentTransferCache<ShipmentTimeType, ShipmentTimeTypeTransfer> {
    
    /** Creates a new instance of ShipmentTimeTypeTransferCache */
    public ShipmentTimeTypeTransferCache(UserVisit userVisit, ShipmentControl shipmentControl) {
        super(userVisit, shipmentControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ShipmentTimeTypeTransfer getShipmentTimeTypeTransfer(ShipmentTimeType shipmentTimeType) {
        ShipmentTimeTypeTransfer shipmentTimeTypeTransfer = get(shipmentTimeType);
        
        if(shipmentTimeTypeTransfer == null) {
            ShipmentTimeTypeDetail shipmentTimeTypeDetail = shipmentTimeType.getLastDetail();
            String shipmentTimeTypeName = shipmentTimeTypeDetail.getShipmentTimeTypeName();
            Boolean isDefault = shipmentTimeTypeDetail.getIsDefault();
            Integer sortOrder = shipmentTimeTypeDetail.getSortOrder();
            String description = shipmentControl.getBestShipmentTimeTypeDescription(shipmentTimeType, getLanguage());
            
            shipmentTimeTypeTransfer = new ShipmentTimeTypeTransfer(shipmentTimeTypeName, isDefault, sortOrder, description);
            put(shipmentTimeType, shipmentTimeTypeTransfer);
        }
        
        return shipmentTimeTypeTransfer;
    }
    
}
