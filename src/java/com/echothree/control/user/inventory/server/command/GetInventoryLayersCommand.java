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

import com.echothree.control.user.inventory.common.form.GetInventoryLayersForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.InventoryCostingPoolControl;
import com.echothree.model.control.inventory.server.control.InventoryLayerControl;
import com.echothree.model.control.inventory.server.logic.InventoryCostingPoolLogic;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionLineLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryCostingPool;
import com.echothree.model.data.inventory.server.entity.InventoryLayer;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionLine;
import com.echothree.model.data.inventory.server.factory.InventoryLayerFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.BaseEntity;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetInventoryLayersCommand
        extends BasePaginatedMultipleEntitiesCommand<InventoryLayer, GetInventoryLayersForm> {

    private static final CommandSecurityDefinition COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
            new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
            new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                    new SecurityRoleDefinition(SecurityRoleGroups.InventoryLayer.name(), SecurityRoles.List.name())
            ))
    ));

    private static final List<FieldDefinition> FORM_FIELD_DEFINITIONS = List.of(
            new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("InventoryTransactionName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("InventoryTransactionLineSequence", FieldType.UNSIGNED_INTEGER, false, null, null)
    );

    @Inject
    InventoryCostingPoolControl inventoryCostingPoolControl;

    @Inject
    InventoryLayerControl inventoryLayerControl;

    @Inject
    InventoryCostingPoolLogic inventoryCostingPoolLogic;

    @Inject
    InventoryTransactionLineLogic inventoryTransactionLineLogic;

    private BaseEntity filter;

    public GetInventoryLayersCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        var companyName = form.getCompanyName();
        var partyName = form.getPartyName();
        var itemName = form.getItemName();
        var inventoryConditionName = form.getInventoryConditionName();
        var inventoryTransactionTypeName = form.getInventoryTransactionTypeName();
        var inventoryTransactionName = form.getInventoryTransactionName();
        var inventoryTransactionLineSequence = form.getInventoryTransactionLineSequence();
        var hasPoolParameters = companyName != null || partyName != null || itemName != null || inventoryConditionName != null;
        var hasLineParameters = inventoryTransactionTypeName != null || inventoryTransactionName != null || inventoryTransactionLineSequence != null;
        var hasCompletePool = (companyName != null ^ partyName != null) && itemName != null && inventoryConditionName != null;
        var hasCompleteLine = inventoryTransactionTypeName != null && inventoryTransactionName != null && inventoryTransactionLineSequence != null;

        if(hasCompletePool && !hasLineParameters) {
            filter = inventoryCostingPoolLogic.getInventoryCostingPoolByName(this, companyName, partyName, itemName, inventoryConditionName);
        } else if(hasCompleteLine && !hasPoolParameters) {
            filter = inventoryTransactionLineLogic.getInventoryTransactionLineBySequence(this, inventoryTransactionTypeName,
                    inventoryTransactionName, Integer.valueOf(inventoryTransactionLineSequence));
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        if(hasExecutionErrors()) {
            return null;
        }

        return switch(filter) {
            case InventoryCostingPool inventoryCostingPool -> inventoryLayerControl.countInventoryLayersByInventoryCostingPool(inventoryCostingPool);
            case InventoryTransactionLine inventoryTransactionLine ->
                    inventoryLayerControl.countInventoryLayersByInventoryTransactionLine(inventoryTransactionLine);
            default -> throw new IllegalStateException("Unexpected inventory layer filter: " + filter);
        };
    }

    @Override
    protected Collection<InventoryLayer> getEntities() {
        if(hasExecutionErrors()) {
            return null;
        }

        return switch(filter) {
            case InventoryCostingPool inventoryCostingPool -> inventoryLayerControl.getInventoryLayersByInventoryCostingPool(inventoryCostingPool);
            case InventoryTransactionLine inventoryTransactionLine -> inventoryLayerControl.getInventoryLayersByInventoryTransactionLine(inventoryTransactionLine);
            default -> throw new IllegalStateException("Unexpected inventory layer filter: " + filter);
        };
    }

    @Override
    protected BaseResult getResult(Collection<InventoryLayer> entities) {
        var result = InventoryResultFactory.getGetInventoryLayersResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            switch(filter) {
                case InventoryCostingPool inventoryCostingPool ->
                        result.setInventoryCostingPool(inventoryCostingPoolControl.getInventoryCostingPoolTransfer(userVisit, inventoryCostingPool));
                case InventoryTransactionLine inventoryTransactionLine -> {
                    // TODO: Set the InventoryTransactionLineTransfer when transaction-line transfers are implemented.
                }
                default -> throw new IllegalStateException("Unexpected inventory layer filter: " + filter);
            }

            if(session.hasLimit(InventoryLayerFactory.class)) {
                result.setInventoryLayerCount(getTotalEntities());
            }

            result.setInventoryLayers(inventoryLayerControl.getInventoryLayerTransfers(userVisit, entities));
        }

        return result;
    }

}
