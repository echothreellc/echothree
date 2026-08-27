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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.form.GetInventoryLocationsForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.InventoryConditionControl;
import com.echothree.model.control.inventory.server.control.InventoryLocationControl;
import com.echothree.model.control.inventory.server.logic.InventoryConditionLogic;
import com.echothree.model.control.inventory.server.logic.InventoryLocationLogic;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.control.warehouse.server.logic.LocationLogic;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryLocation;
import com.echothree.model.data.inventory.server.factory.InventoryLocationFactory;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.warehouse.server.entity.Location;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetInventoryLocationsCommand
        extends BasePaginatedMultipleEntitiesCommand<InventoryLocation, GetInventoryLocationsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryLocation.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LocationName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("OwnerPartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    InventoryConditionControl inventoryConditionControl;

    @Inject
    InventoryLocationControl inventoryLocationControl;

    @Inject
    ItemControl itemControl;

    @Inject
    PartyControl partyControl;

    @Inject
    UomControl uomControl;

    @Inject
    WarehouseControl warehouseControl;

    @Inject
    InventoryConditionLogic inventoryConditionLogic;

    @Inject
    InventoryLocationLogic inventoryLocationLogic;

    @Inject
    ItemLogic itemLogic;

    @Inject
    LocationLogic locationLogic;

    @Inject
    UnitOfMeasureTypeLogic unitOfMeasureTypeLogic;

    /** Creates a new instance of GetInventoryLocationsCommand */
    public GetInventoryLocationsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private Location location;
    private Party ownerParty;
    private Item item;
    private UnitOfMeasureType unitOfMeasureType;
    private InventoryCondition inventoryCondition;

    @Override
    protected void handleForm() {
        var partyName = form.getPartyName();
        var warehouseName = form.getWarehouseName();
        var locationName = form.getLocationName();
        var ownerPartyName = form.getOwnerPartyName();
        var itemName = form.getItemName();
        var unitOfMeasureKindName = form.getUnitOfMeasureKindName();
        var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
        var inventoryConditionName = form.getInventoryConditionName();
        var warehouseParameterCount = (partyName == null ? 0 : 1) + (warehouseName == null ? 0 : 1);
        var hasLocationParameters = warehouseParameterCount != 0 || locationName != null;
        var hasCompleteLocationParameters = warehouseParameterCount == 1 && locationName != null;
        var hasUnitOfMeasureParameters = unitOfMeasureKindName != null || unitOfMeasureTypeName != null;
        var hasCompleteUnitOfMeasureParameters = unitOfMeasureKindName != null && unitOfMeasureTypeName != null;
        var selectorCount = (hasCompleteLocationParameters ? 1 : 0)
                + (ownerPartyName == null ? 0 : 1)
                + (itemName == null ? 0 : 1)
                + (hasCompleteUnitOfMeasureParameters ? 1 : 0)
                + (inventoryConditionName == null ? 0 : 1);

        if((hasLocationParameters && !hasCompleteLocationParameters)
                || (hasUnitOfMeasureParameters && !hasCompleteUnitOfMeasureParameters)
                || selectorCount != 1) {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        } else if(hasCompleteLocationParameters) {
            location = locationLogic.getLocation(this, partyName, warehouseName, locationName);
        } else if(ownerPartyName != null) {
            ownerParty = inventoryLocationLogic.getOwnerParty(this, ownerPartyName);
        } else if(itemName != null) {
            item = itemLogic.getItemByName(this, itemName);
        } else if(hasCompleteUnitOfMeasureParameters) {
            unitOfMeasureType = unitOfMeasureTypeLogic.getUnitOfMeasureTypeByName(this, unitOfMeasureKindName, unitOfMeasureTypeName);
        } else {
            inventoryCondition = inventoryConditionLogic.getInventoryConditionByName(this, inventoryConditionName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(location != null) {
                total = inventoryLocationControl.countInventoryLocationsByLocation(location);
            } else if(ownerParty != null) {
                total = inventoryLocationControl.countInventoryLocationsByOwnerParty(ownerParty);
            } else if(unitOfMeasureType != null) {
                total = inventoryLocationControl.countInventoryLocationsByUnitOfMeasureType(unitOfMeasureType);
            } else if(item != null) {
                total = inventoryLocationControl.countInventoryLocationsByItem(item);
            } else if(inventoryCondition != null) {
                total = inventoryLocationControl.countInventoryLocationsByInventoryCondition(inventoryCondition);
            }
        }

        return total;
    }

    @Override
    protected Collection<InventoryLocation> getEntities() {
        Collection<InventoryLocation> entities = null;

        if(!hasExecutionErrors()) {
            if(location != null) {
                entities = inventoryLocationControl.getInventoryLocationsByLocation(location);
            } else if(ownerParty != null) {
                entities = inventoryLocationControl.getInventoryLocationsByOwnerParty(ownerParty);
            } else if(unitOfMeasureType != null) {
                entities = inventoryLocationControl.getInventoryLocationsByUnitOfMeasureType(unitOfMeasureType);
            } else if(item != null) {
                entities = inventoryLocationControl.getInventoryLocationsByItem(item);
            } else if(inventoryCondition != null) {
                entities = inventoryLocationControl.getInventoryLocationsByInventoryCondition(inventoryCondition);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<InventoryLocation> entities) {
        var result = InventoryResultFactory.getGetInventoryLocationsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(location != null) {
                result.setLocation(warehouseControl.getLocationTransfer(userVisit, location));
            } else if(ownerParty != null) {
                result.setOwnerParty(partyControl.getPartyTransfer(userVisit, ownerParty));
            } else if(unitOfMeasureType != null) {
                result.setUnitOfMeasureType(uomControl.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureType));
            } else if(item != null) {
                result.setItem(itemControl.getItemTransfer(userVisit, item));
            } else if(inventoryCondition != null) {
                result.setInventoryCondition(inventoryConditionControl.getInventoryConditionTransfer(userVisit, inventoryCondition));
            }

            if(session.hasLimit(InventoryLocationFactory.class)) {
                result.setInventoryLocationCount(getTotalEntities());
            }

            result.setInventoryLocations(inventoryLocationControl.getInventoryLocationTransfers(userVisit, entities));
        }

        return result;
    }

}
