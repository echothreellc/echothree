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

import com.echothree.model.control.shipment.common.transfer.ShipmentAliasTransfer;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.ShipmentAlias;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ShipmentAliasTransferCache
        extends BaseShipmentTransferCache<ShipmentAlias, ShipmentAliasTransfer> {

    ShipmentControl shipmentControl = Session.getModelController(ShipmentControl.class);

    /** Creates a new instance of ShipmentAliasTransferCache */
    public ShipmentAliasTransferCache() {
        super();
    }

    @Override
    public ShipmentAliasTransfer getTransfer(UserVisit userVisit, ShipmentAlias shipmentAlias) {
        var shipmentAliasTransfer = get(shipmentAlias);
        
        if(shipmentAliasTransfer == null) {
//            ShipmentTransfer shipment = shipmentControl.getShipmentTransfer(userVisit, shipmentAlias.getShipment());
            var shipmentAliasType = shipmentControl.getShipmentAliasTypeTransfer(userVisit, shipmentAlias.getShipmentAliasType());
            var alias = shipmentAlias.getAlias();
            
            shipmentAliasTransfer = new ShipmentAliasTransfer(/*shipment,*/ shipmentAliasType, alias);
            put(userVisit, shipmentAlias, shipmentAliasTransfer);
        }
        
        return shipmentAliasTransfer;
    }
    
}
