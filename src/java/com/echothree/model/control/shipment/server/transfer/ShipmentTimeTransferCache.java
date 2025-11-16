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

package com.echothree.model.control.shipment.server.transfer;

import com.echothree.model.control.shipment.common.transfer.ShipmentTimeTransfer;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.ShipmentTime;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ShipmentTimeTransferCache
        extends BaseShipmentTransferCache<ShipmentTime, ShipmentTimeTransfer> {

    ShipmentControl shipmentControl = Session.getModelController(ShipmentControl.class);

    /** Creates a new instance of ShipmentTimeTransferCache */
    protected ShipmentTimeTransferCache() {
        super();
    }

    @Override
    public ShipmentTimeTransfer getTransfer(UserVisit userVisit, ShipmentTime shipmentTime) {
        var shipmentTimeTransfer = get(shipmentTime);
        
        if(shipmentTimeTransfer == null) {
            var shipmentTimeType = shipmentControl.getShipmentTimeTypeTransfer(userVisit, shipmentTime.getShipmentTimeType());
            var unformattedTime = shipmentTime.getTime();
            var time = formatTypicalDateTime(userVisit, unformattedTime);
            
            shipmentTimeTransfer = new ShipmentTimeTransfer(shipmentTimeType, unformattedTime, time);
            put(userVisit, shipmentTime, shipmentTimeTransfer);
        }
        
        return shipmentTimeTransfer;
    }
    
}
