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

import com.echothree.model.control.shipment.common.transfer.ShipmentTypeDescriptionTransfer;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.ShipmentTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ShipmentTypeDescriptionTransferCache
        extends BaseShipmentDescriptionTransferCache<ShipmentTypeDescription, ShipmentTypeDescriptionTransfer> {

    ShipmentControl shipmentControl = Session.getModelController(ShipmentControl.class);

    /** Creates a new instance of ShipmentTypeDescriptionTransferCache */
    protected ShipmentTypeDescriptionTransferCache() {
        super();
    }

    @Override
    public ShipmentTypeDescriptionTransfer getTransfer(UserVisit userVisit, ShipmentTypeDescription shipmentTypeDescription) {
        var shipmentTypeDescriptionTransfer = get(shipmentTypeDescription);
        
        if(shipmentTypeDescriptionTransfer == null) {
            var shipmentTypeTransfer = shipmentControl.getShipmentTypeTransfer(userVisit, shipmentTypeDescription.getShipmentType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, shipmentTypeDescription.getLanguage());
            
            shipmentTypeDescriptionTransfer = new ShipmentTypeDescriptionTransfer(languageTransfer, shipmentTypeTransfer, shipmentTypeDescription.getDescription());
            put(userVisit, shipmentTypeDescription, shipmentTypeDescriptionTransfer);
        }
        
        return shipmentTypeDescriptionTransfer;
    }
    
}
