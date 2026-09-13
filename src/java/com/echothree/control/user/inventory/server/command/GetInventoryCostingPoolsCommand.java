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

import com.echothree.control.user.inventory.common.form.GetInventoryCostingPoolsForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.InventoryCostingPoolControl;
import com.echothree.model.control.inventory.server.control.InventoryConditionControl;
import com.echothree.model.control.inventory.server.logic.InventoryConditionLogic;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.server.logic.CompanyLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryCostingPool;
import com.echothree.model.data.inventory.server.factory.InventoryCostingPoolFactory;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
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
public class GetInventoryCostingPoolsCommand
        extends BasePaginatedMultipleEntitiesCommand<InventoryCostingPool, GetInventoryCostingPoolsForm> {

    private static final CommandSecurityDefinition COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
            new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
            new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                    new SecurityRoleDefinition(SecurityRoleGroups.InventoryCostingPool.name(), SecurityRoles.List.name())
            ))
    ));

    private static final List<FieldDefinition> FORM_FIELD_DEFINITIONS = List.of(
            new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, false, null, null)
    );

    @Inject
    InventoryConditionControl inventoryConditionControl;

    @Inject
    InventoryCostingPoolControl inventoryCostingPoolControl;

    @Inject
    ItemControl itemControl;

    @Inject
    PartyControl partyControl;

    @Inject
    CompanyLogic companyLogic;

    @Inject
    InventoryConditionLogic inventoryConditionLogic;

    @Inject
    ItemLogic itemLogic;

    private BaseEntity filter;

    public GetInventoryCostingPoolsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        var companyName = form.getCompanyName();
        var partyName = form.getPartyName();
        var itemName = form.getItemName();
        var inventoryConditionName = form.getInventoryConditionName();
        var parameterCount = (companyName == null ? 0 : 1) + (partyName == null ? 0 : 1)
                + (itemName == null ? 0 : 1) + (inventoryConditionName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(itemName != null) {
                filter = itemLogic.getItemByName(this, itemName);
            } else if(inventoryConditionName != null) {
                filter = inventoryConditionLogic.getInventoryConditionByName(this, inventoryConditionName);
            } else {
                var company = companyLogic.getPartyCompanyByName(this, companyName, partyName, null, true);

                if(!hasExecutionErrors()) {
                    filter = company.getParty();
                }
            }
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
            case Party companyParty -> inventoryCostingPoolControl.countInventoryCostingPoolsByCompanyParty(companyParty);
            case Item item -> inventoryCostingPoolControl.countInventoryCostingPoolsByItem(item);
            case InventoryCondition inventoryCondition ->
                    inventoryCostingPoolControl.countInventoryCostingPoolsByInventoryCondition(inventoryCondition);
            default -> throw new IllegalStateException("Unexpected inventory costing pool filter: " + filter);
        };
    }

    @Override
    protected Collection<InventoryCostingPool> getEntities() {
        if(hasExecutionErrors()) {
            return null;
        }

        return switch(filter) {
            case Party companyParty -> inventoryCostingPoolControl.getInventoryCostingPoolsByCompanyParty(companyParty);
            case Item item -> inventoryCostingPoolControl.getInventoryCostingPoolsByItem(item);
            case InventoryCondition inventoryCondition ->
                    inventoryCostingPoolControl.getInventoryCostingPoolsByInventoryCondition(inventoryCondition);
            default -> throw new IllegalStateException("Unexpected inventory costing pool filter: " + filter);
        };
    }

    @Override
    protected BaseResult getResult(Collection<InventoryCostingPool> entities) {
        var result = InventoryResultFactory.getGetInventoryCostingPoolsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            switch(filter) {
                case Party companyParty -> result.setCompany(partyControl.getCompanyTransfer(userVisit, companyParty));
                case Item item -> result.setItem(itemControl.getItemTransfer(userVisit, item));
                case InventoryCondition inventoryCondition ->
                        result.setInventoryCondition(inventoryConditionControl.getInventoryConditionTransfer(userVisit, inventoryCondition));
                default -> throw new IllegalStateException("Unexpected inventory costing pool filter: " + filter);
            }

            if(session.hasLimit(InventoryCostingPoolFactory.class)) {
                result.setInventoryCostingPoolCount(getTotalEntities());
            }

            result.setInventoryCostingPools(inventoryCostingPoolControl.getInventoryCostingPoolTransfers(userVisit, entities));
        }

        return result;
    }

}
