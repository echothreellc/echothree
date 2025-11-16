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
import javax.enterprise.inject.spi.CDI;

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
            shipmentTypeTransferCache = CDI.current().select(ShipmentTypeTransferCache.class).get();
        
        return shipmentTypeTransferCache;
    }
    
    public ShipmentTypeDescriptionTransferCache getShipmentTypeDescriptionTransferCache() {
        if(shipmentTypeDescriptionTransferCache == null)
            shipmentTypeDescriptionTransferCache = CDI.current().select(ShipmentTypeDescriptionTransferCache.class).get();

        return shipmentTypeDescriptionTransferCache;
    }

    public ShipmentTypeShippingMethodTransferCache getShipmentTypeShippingMethodTransferCache() {
        if(shipmentTypeShippingMethodTransferCache == null)
            shipmentTypeShippingMethodTransferCache = CDI.current().select(ShipmentTypeShippingMethodTransferCache.class).get();

        return shipmentTypeShippingMethodTransferCache;
    }

    public ShipmentAliasTypeTransferCache getShipmentAliasTypeTransferCache() {
        if(shipmentAliasTypeTransferCache == null)
            shipmentAliasTypeTransferCache = CDI.current().select(ShipmentAliasTypeTransferCache.class).get();
        
        return shipmentAliasTypeTransferCache;
    }
    
    public ShipmentAliasTypeDescriptionTransferCache getShipmentAliasTypeDescriptionTransferCache() {
        if(shipmentAliasTypeDescriptionTransferCache == null)
            shipmentAliasTypeDescriptionTransferCache = CDI.current().select(ShipmentAliasTypeDescriptionTransferCache.class).get();
        
        return shipmentAliasTypeDescriptionTransferCache;
    }
    
    public ShipmentAliasTransferCache getShipmentAliasTransferCache() {
        if(shipmentAliasTransferCache == null)
            shipmentAliasTransferCache = CDI.current().select(ShipmentAliasTransferCache.class).get();
        
        return shipmentAliasTransferCache;
    }
    
    public ShipmentTimeTypeTransferCache getShipmentTimeTypeTransferCache() {
        if(shipmentTimeTypeTransferCache == null)
            shipmentTimeTypeTransferCache = CDI.current().select(ShipmentTimeTypeTransferCache.class).get();

        return shipmentTimeTypeTransferCache;
    }

    public ShipmentTimeTransferCache getShipmentTimeTransferCache() {
        if(shipmentTimeTransferCache == null)
            shipmentTimeTransferCache = CDI.current().select(ShipmentTimeTransferCache.class).get();

        return shipmentTimeTransferCache;
    }

    public ShipmentTimeTypeDescriptionTransferCache getShipmentTimeTypeDescriptionTransferCache() {
        if(shipmentTimeTypeDescriptionTransferCache == null)
            shipmentTimeTypeDescriptionTransferCache = CDI.current().select(ShipmentTimeTypeDescriptionTransferCache.class).get();

        return shipmentTimeTypeDescriptionTransferCache;
    }

    public FreeOnBoardTransferCache getFreeOnBoardTransferCache() {
        if(freeOnBoardTransferCache == null)
            freeOnBoardTransferCache = CDI.current().select(FreeOnBoardTransferCache.class).get();

        return freeOnBoardTransferCache;
    }

    public FreeOnBoardDescriptionTransferCache getFreeOnBoardDescriptionTransferCache() {
        if(freeOnBoardDescriptionTransferCache == null)
            freeOnBoardDescriptionTransferCache = CDI.current().select(FreeOnBoardDescriptionTransferCache.class).get();

        return freeOnBoardDescriptionTransferCache;
    }

    public PartyFreeOnBoardTransferCache getPartyFreeOnBoardTransferCache() {
        if(partyFreeOnBoardTransferCache == null)
            partyFreeOnBoardTransferCache = CDI.current().select(PartyFreeOnBoardTransferCache.class).get();

        return partyFreeOnBoardTransferCache;
    }

}
