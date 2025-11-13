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

import com.echothree.model.control.shipment.common.transfer.ShipmentTypeShippingMethodTransfer;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.shipment.server.entity.ShipmentTypeShippingMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ShipmentTypeShippingMethodTransferCache
        extends BaseShipmentTransferCache<ShipmentTypeShippingMethod, ShipmentTypeShippingMethodTransfer> {

    ShipmentControl shipmentControl = Session.getModelController(ShipmentControl.class);
    ShippingControl shippingControl = Session.getModelController(ShippingControl.class);

    /** Creates a new instance of ShipmentTypeShippingMethodTransferCache */
    public ShipmentTypeShippingMethodTransferCache() {
        super();
    }

    @Override
    public ShipmentTypeShippingMethodTransfer getTransfer(ShipmentTypeShippingMethod shipmentTypeShippingMethod) {
        var shipmentTypeShippingMethodTransfer = get(shipmentTypeShippingMethod);
        
        if(shipmentTypeShippingMethodTransfer == null) {
            var shipmentType = shipmentControl.getShipmentTypeTransfer(userVisit, shipmentTypeShippingMethod.getShipmentType());
            var shippingMethod = shippingControl.getShippingMethodTransfer(userVisit, shipmentTypeShippingMethod.getShippingMethod());
            var isDefault = shipmentTypeShippingMethod.getIsDefault();
            var sortOrder = shipmentTypeShippingMethod.getSortOrder();
            
            shipmentTypeShippingMethodTransfer = new ShipmentTypeShippingMethodTransfer(shipmentType, shippingMethod, isDefault,
                    sortOrder);
            put(userVisit, shipmentTypeShippingMethod, shipmentTypeShippingMethodTransfer);
        }
        
        return shipmentTypeShippingMethodTransfer;
    }
    
}
