// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.model.control.shipment.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ShipmentAliasTransfer
        extends BaseTransfer {
    
//    private ShipmentTransfer shipment;
    private ShipmentAliasTypeTransfer shipmentAliasType;
    private String alias;
    
    /** Creates a new instance of ShipmentAliasTransfer */
    public ShipmentAliasTransfer(/*ShipmentTransfer shipment,*/ ShipmentAliasTypeTransfer shipmentAliasType, String alias) {
//        this.shipment = shipment;
        this.shipmentAliasType = shipmentAliasType;
        this.alias = alias;
    }

//    public ShipmentTransfer getShipment() {
//        return shipment;
//    }
//
//    public void setShipment(ShipmentTransfer shipment) {
//        this.shipment = shipment;
//    }

    public ShipmentAliasTypeTransfer getShipmentAliasType() {
        return shipmentAliasType;
    }

    public void setShipmentAliasType(ShipmentAliasTypeTransfer shipmentAliasType) {
        this.shipmentAliasType = shipmentAliasType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
