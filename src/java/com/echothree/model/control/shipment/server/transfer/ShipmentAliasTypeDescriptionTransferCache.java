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

import com.echothree.model.control.shipment.common.transfer.ShipmentAliasTypeDescriptionTransfer;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.ShipmentAliasTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ShipmentAliasTypeDescriptionTransferCache
        extends BaseShipmentDescriptionTransferCache<ShipmentAliasTypeDescription, ShipmentAliasTypeDescriptionTransfer> {

    ShipmentControl shipmentControl = Session.getModelController(ShipmentControl.class);

    /** Creates a new instance of ShipmentAliasTypeDescriptionTransferCache */
    public ShipmentAliasTypeDescriptionTransferCache() {
        super();
    }

    @Override
    public ShipmentAliasTypeDescriptionTransfer getTransfer(UserVisit userVisit, ShipmentAliasTypeDescription shipmentAliasTypeDescription) {
        var shipmentAliasTypeDescriptionTransfer = get(shipmentAliasTypeDescription);
        
        if(shipmentAliasTypeDescriptionTransfer == null) {
            var shipmentAliasTypeTransfer = shipmentControl.getShipmentAliasTypeTransfer(userVisit, shipmentAliasTypeDescription.getShipmentAliasType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, shipmentAliasTypeDescription.getLanguage());
            
            shipmentAliasTypeDescriptionTransfer = new ShipmentAliasTypeDescriptionTransfer(languageTransfer, shipmentAliasTypeTransfer, shipmentAliasTypeDescription.getDescription());
            put(userVisit, shipmentAliasTypeDescription, shipmentAliasTypeDescriptionTransfer);
        }
        
        return shipmentAliasTypeDescriptionTransfer;
    }
    
}
