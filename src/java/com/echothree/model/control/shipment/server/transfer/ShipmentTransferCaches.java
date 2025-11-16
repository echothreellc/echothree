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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class ShipmentTransferCaches
        extends BaseTransferCaches {
    
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
    
    /** Creates a new instance of ShipmentTransferCaches */
    protected ShipmentTransferCaches() {
        super();
    }
    
    public ShipmentTypeTransferCache getShipmentTypeTransferCache() {
        return shipmentTypeTransferCache;
    }
    
    public ShipmentTypeDescriptionTransferCache getShipmentTypeDescriptionTransferCache() {
        return shipmentTypeDescriptionTransferCache;
    }

    public ShipmentTypeShippingMethodTransferCache getShipmentTypeShippingMethodTransferCache() {
        return shipmentTypeShippingMethodTransferCache;
    }

    public ShipmentAliasTypeTransferCache getShipmentAliasTypeTransferCache() {
        return shipmentAliasTypeTransferCache;
    }
    
    public ShipmentAliasTypeDescriptionTransferCache getShipmentAliasTypeDescriptionTransferCache() {
        return shipmentAliasTypeDescriptionTransferCache;
    }
    
    public ShipmentAliasTransferCache getShipmentAliasTransferCache() {
        return shipmentAliasTransferCache;
    }
    
    public ShipmentTimeTypeTransferCache getShipmentTimeTypeTransferCache() {
        return shipmentTimeTypeTransferCache;
    }

    public ShipmentTimeTransferCache getShipmentTimeTransferCache() {
        return shipmentTimeTransferCache;
    }

    public ShipmentTimeTypeDescriptionTransferCache getShipmentTimeTypeDescriptionTransferCache() {
        return shipmentTimeTypeDescriptionTransferCache;
    }

    public FreeOnBoardTransferCache getFreeOnBoardTransferCache() {
        return freeOnBoardTransferCache;
    }

    public FreeOnBoardDescriptionTransferCache getFreeOnBoardDescriptionTransferCache() {
        return freeOnBoardDescriptionTransferCache;
    }

    public PartyFreeOnBoardTransferCache getPartyFreeOnBoardTransferCache() {
        return partyFreeOnBoardTransferCache;
    }

}
