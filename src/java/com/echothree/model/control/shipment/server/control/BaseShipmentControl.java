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

package com.echothree.model.control.shipment.server.control;

import com.echothree.model.control.shipment.server.transfer.FreeOnBoardDescriptionTransferCache;
import com.echothree.model.control.shipment.server.transfer.FreeOnBoardTransferCache;
import com.echothree.model.control.shipment.server.transfer.PartyFreeOnBoardTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentAliasTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentAliasTypeDescriptionTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentAliasTypeTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentTimeTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentTimeTypeDescriptionTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentTimeTypeTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentTypeDescriptionTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentTypeShippingMethodTransferCache;
import com.echothree.model.control.shipment.server.transfer.ShipmentTypeTransferCache;
import com.echothree.util.server.control.BaseModelControl;
import javax.inject.Inject;

public abstract class BaseShipmentControl
        extends BaseModelControl {

    /** Creates a new instance of ShipmentControl */
    protected BaseShipmentControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Shipment Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    ShipmentTypeTransferCache shipmentTypeTransferCache;

    @Inject
    ShipmentTypeDescriptionTransferCache shipmentTypeDescriptionTransferCache;

    @Inject
    ShipmentTypeShippingMethodTransferCache shipmentTypeShippingMethodTransferCache;

    @Inject
    ShipmentAliasTypeTransferCache shipmentAliasTypeTransferCache;

    @Inject
    ShipmentAliasTypeDescriptionTransferCache shipmentAliasTypeDescriptionTransferCache;

    @Inject
    ShipmentAliasTransferCache shipmentAliasTransferCache;

    @Inject
    ShipmentTimeTypeTransferCache shipmentTimeTypeTransferCache;

    @Inject
    ShipmentTimeTypeDescriptionTransferCache shipmentTimeTypeDescriptionTransferCache;

    @Inject
    ShipmentTimeTransferCache shipmentTimeTransferCache;

    @Inject
    FreeOnBoardTransferCache freeOnBoardTransferCache;

    @Inject
    FreeOnBoardDescriptionTransferCache freeOnBoardDescriptionTransferCache;

    @Inject
    PartyFreeOnBoardTransferCache partyFreeOnBoardTransferCache;

}
