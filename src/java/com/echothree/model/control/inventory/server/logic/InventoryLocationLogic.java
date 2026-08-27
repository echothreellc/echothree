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

package com.echothree.model.control.inventory.server.logic;

import com.echothree.model.control.inventory.common.exception.DuplicateInventoryLocationException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryLocationException;
import com.echothree.model.control.inventory.server.control.InventoryLocationControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryLocation;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.warehouse.server.entity.Location;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class InventoryLocationLogic
        extends BaseLogic {

    @Inject
    InventoryLocationControl inventoryLocationControl;

    @Inject
    PartyLogic partyLogic;

    @Inject
    UnitOfMeasureTypeLogic unitOfMeasureTypeLogic;

    protected InventoryLocationLogic() {
        super();
    }

    public Party getOwnerParty(final ExecutionErrorAccumulator eea, final String ownerPartyName) {
        return partyLogic.getPartyByName(eea, ownerPartyName, PartyTypes.COMPANY.name(), PartyTypes.VENDOR.name());
    }

    public UnitOfMeasureType getUnitOfMeasureType(final ExecutionErrorAccumulator eea, final Item item,
            final String unitOfMeasureTypeName) {
        return unitOfMeasureTypeLogic.getUnitOfMeasureTypeByName(eea,
                item.getLastDetail().getUnitOfMeasureKind(), unitOfMeasureTypeName);
    }

    public InventoryLocation createInventoryLocation(final ExecutionErrorAccumulator eea, final Location location,
            final Party ownerParty, final Item item, final UnitOfMeasureType unitOfMeasureType,
            final InventoryCondition inventoryCondition, final BasePK createdBy) {
        var inventoryLocation = inventoryLocationControl.getInventoryLocation(location, item);

        if(inventoryLocation == null) {
            inventoryLocation = inventoryLocationControl.createInventoryLocation(location, ownerParty, item,
                    unitOfMeasureType, inventoryCondition, createdBy);
        } else {
            handleExecutionError(DuplicateInventoryLocationException.class, eea,
                    ExecutionErrors.DuplicateInventoryLocation.name(), location.getLastDetail().getLocationName(),
                    item.getLastDetail().getItemName());
        }

        return inventoryLocation;
    }

    public InventoryLocation getInventoryLocation(final ExecutionErrorAccumulator eea, final Location location,
            final Item item, final EntityPermission entityPermission) {
        var inventoryLocation = entityPermission == EntityPermission.READ_WRITE
                ? inventoryLocationControl.getInventoryLocationForUpdate(location, item)
                : inventoryLocationControl.getInventoryLocation(location, item);

        if(inventoryLocation == null) {
            handleExecutionError(UnknownInventoryLocationException.class, eea,
                    ExecutionErrors.UnknownInventoryLocation.name(), location.getLastDetail().getLocationName(),
                    item.getLastDetail().getItemName());
        }

        return inventoryLocation;
    }

    public InventoryLocation getInventoryLocation(final ExecutionErrorAccumulator eea, final Location location,
            final Item item) {
        return getInventoryLocation(eea, location, item, EntityPermission.READ_ONLY);
    }

    public InventoryLocation getInventoryLocationForUpdate(final ExecutionErrorAccumulator eea, final Location location,
            final Item item) {
        return getInventoryLocation(eea, location, item, EntityPermission.READ_WRITE);
    }

    public void deleteInventoryLocation(final ExecutionErrorAccumulator eea,
            final InventoryLocation inventoryLocation, final BasePK deletedBy) {
        inventoryLocationControl.deleteInventoryLocation(inventoryLocation, deletedBy);
    }

}
