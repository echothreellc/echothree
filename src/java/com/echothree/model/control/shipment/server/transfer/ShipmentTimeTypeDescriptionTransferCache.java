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

import com.echothree.model.control.shipment.common.transfer.ShipmentTimeTypeDescriptionTransfer;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.ShipmentTimeTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ShipmentTimeTypeDescriptionTransferCache
        extends BaseShipmentDescriptionTransferCache<ShipmentTimeTypeDescription, ShipmentTimeTypeDescriptionTransfer> {

    ShipmentControl shipmentControl = Session.getModelController(ShipmentControl.class);

    /** Creates a new instance of ShipmentTimeTypeDescriptionTransferCache */
    public ShipmentTimeTypeDescriptionTransferCache() {
        super();
    }

    @Override
    public ShipmentTimeTypeDescriptionTransfer getTransfer(ShipmentTimeTypeDescription shipmentTimeTypeDescription) {
        var shipmentTimeTypeDescriptionTransfer = get(shipmentTimeTypeDescription);
        
        if(shipmentTimeTypeDescriptionTransfer == null) {
            var shipmentTimeTypeTransfer = shipmentControl.getShipmentTimeTypeTransfer(userVisit, shipmentTimeTypeDescription.getShipmentTimeType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, shipmentTimeTypeDescription.getLanguage());
            
            shipmentTimeTypeDescriptionTransfer = new ShipmentTimeTypeDescriptionTransfer(languageTransfer, shipmentTimeTypeTransfer, shipmentTimeTypeDescription.getDescription());
            put(userVisit, shipmentTimeTypeDescription, shipmentTimeTypeDescriptionTransfer);
        }
        
        return shipmentTimeTypeDescriptionTransfer;
    }
    
}
