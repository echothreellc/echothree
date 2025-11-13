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

import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class ShipmentTransferCaches
        extends BaseTransferCaches {
    
    protected ShipmentTypeTransferCache shipmentTypeTransferCache;
    protected ShipmentTypeDescriptionTransferCache shipmentTypeDescriptionTransferCache;
    protected ShipmentTypeShippingMethodTransferCache shipmentTypeShippingMethodTransferCache;
    protected ShipmentAliasTypeTransferCache shipmentAliasTypeTransferCache;
    protected ShipmentAliasTypeDescriptionTransferCache shipmentAliasTypeDescriptionTransferCache;
    protected ShipmentAliasTransferCache shipmentAliasTransferCache;
    protected ShipmentTimeTypeTransferCache shipmentTimeTypeTransferCache;
    protected ShipmentTimeTypeDescriptionTransferCache shipmentTimeTypeDescriptionTransferCache;
    protected ShipmentTimeTransferCache shipmentTimeTransferCache;
    protected FreeOnBoardTransferCache freeOnBoardTransferCache;
    protected FreeOnBoardDescriptionTransferCache freeOnBoardDescriptionTransferCache;
    protected PartyFreeOnBoardTransferCache partyFreeOnBoardTransferCache;

    /** Creates a new instance of ShipmentTransferCaches */
    public ShipmentTransferCaches() {
        super();
    }
    
    public ShipmentTypeTransferCache getShipmentTypeTransferCache() {
        if(shipmentTypeTransferCache == null)
            shipmentTypeTransferCache = new ShipmentTypeTransferCache();
        
        return shipmentTypeTransferCache;
    }
    
    public ShipmentTypeDescriptionTransferCache getShipmentTypeDescriptionTransferCache() {
        if(shipmentTypeDescriptionTransferCache == null)
            shipmentTypeDescriptionTransferCache = new ShipmentTypeDescriptionTransferCache();

        return shipmentTypeDescriptionTransferCache;
    }

    public ShipmentTypeShippingMethodTransferCache getShipmentTypeShippingMethodTransferCache() {
        if(shipmentTypeShippingMethodTransferCache == null)
            shipmentTypeShippingMethodTransferCache = new ShipmentTypeShippingMethodTransferCache();

        return shipmentTypeShippingMethodTransferCache;
    }

    public ShipmentAliasTypeTransferCache getShipmentAliasTypeTransferCache() {
        if(shipmentAliasTypeTransferCache == null)
            shipmentAliasTypeTransferCache = new ShipmentAliasTypeTransferCache();
        
        return shipmentAliasTypeTransferCache;
    }
    
    public ShipmentAliasTypeDescriptionTransferCache getShipmentAliasTypeDescriptionTransferCache() {
        if(shipmentAliasTypeDescriptionTransferCache == null)
            shipmentAliasTypeDescriptionTransferCache = new ShipmentAliasTypeDescriptionTransferCache();
        
        return shipmentAliasTypeDescriptionTransferCache;
    }
    
    public ShipmentAliasTransferCache getShipmentAliasTransferCache() {
        if(shipmentAliasTransferCache == null)
            shipmentAliasTransferCache = new ShipmentAliasTransferCache();
        
        return shipmentAliasTransferCache;
    }
    
    public ShipmentTimeTypeTransferCache getShipmentTimeTypeTransferCache() {
        if(shipmentTimeTypeTransferCache == null)
            shipmentTimeTypeTransferCache = new ShipmentTimeTypeTransferCache();

        return shipmentTimeTypeTransferCache;
    }

    public ShipmentTimeTransferCache getShipmentTimeTransferCache() {
        if(shipmentTimeTransferCache == null)
            shipmentTimeTransferCache = new ShipmentTimeTransferCache();

        return shipmentTimeTransferCache;
    }

    public ShipmentTimeTypeDescriptionTransferCache getShipmentTimeTypeDescriptionTransferCache() {
        if(shipmentTimeTypeDescriptionTransferCache == null)
            shipmentTimeTypeDescriptionTransferCache = new ShipmentTimeTypeDescriptionTransferCache();

        return shipmentTimeTypeDescriptionTransferCache;
    }

    public FreeOnBoardTransferCache getFreeOnBoardTransferCache() {
        if(freeOnBoardTransferCache == null)
            freeOnBoardTransferCache = new FreeOnBoardTransferCache();

        return freeOnBoardTransferCache;
    }

    public FreeOnBoardDescriptionTransferCache getFreeOnBoardDescriptionTransferCache() {
        if(freeOnBoardDescriptionTransferCache == null)
            freeOnBoardDescriptionTransferCache = new FreeOnBoardDescriptionTransferCache();

        return freeOnBoardDescriptionTransferCache;
    }

    public PartyFreeOnBoardTransferCache getPartyFreeOnBoardTransferCache() {
        if(partyFreeOnBoardTransferCache == null)
            partyFreeOnBoardTransferCache = new PartyFreeOnBoardTransferCache();

        return partyFreeOnBoardTransferCache;
    }

}
