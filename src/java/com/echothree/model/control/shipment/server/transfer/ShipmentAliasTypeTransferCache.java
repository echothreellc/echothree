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

import com.echothree.model.control.shipment.common.transfer.ShipmentAliasTypeTransfer;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.ShipmentAliasType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ShipmentAliasTypeTransferCache
        extends BaseShipmentTransferCache<ShipmentAliasType, ShipmentAliasTypeTransfer> {

    ShipmentControl shipmentControl = Session.getModelController(ShipmentControl.class);

    /** Creates a new instance of ShipmentAliasTypeTransferCache */
    public ShipmentAliasTypeTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }

    @Override
    public ShipmentAliasTypeTransfer getTransfer(ShipmentAliasType shipmentAliasType) {
        var shipmentAliasTypeTransfer = get(shipmentAliasType);
        
        if(shipmentAliasTypeTransfer == null) {
            var shipmentAliasTypeDetail = shipmentAliasType.getLastDetail();
            var shipmentType = shipmentControl.getShipmentTypeTransfer(userVisit, shipmentAliasTypeDetail.getShipmentType());
            var shipmentAliasTypeName = shipmentAliasTypeDetail.getShipmentAliasTypeName();
            var validationPattern = shipmentAliasTypeDetail.getValidationPattern();
            var isDefault = shipmentAliasTypeDetail.getIsDefault();
            var sortOrder = shipmentAliasTypeDetail.getSortOrder();
            var description = shipmentControl.getBestShipmentAliasTypeDescription(shipmentAliasType, getLanguage(userVisit));
            
            shipmentAliasTypeTransfer = new ShipmentAliasTypeTransfer(shipmentType, shipmentAliasTypeName, validationPattern, isDefault, sortOrder, description);
            put(userVisit, shipmentAliasType, shipmentAliasTypeTransfer);
        }
        
        return shipmentAliasTypeTransfer;
    }
    
}
