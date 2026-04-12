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

import com.echothree.control.user.inventory.common.form.GetPartyInventoryLevelsForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.server.command.common.PartyInventoryLevelUtil;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.inventory.server.logic.InventoryConditionLogic;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.PartyInventoryLevel;
import com.echothree.model.data.inventory.server.factory.PartyInventoryLevelFactory;
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
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyInventoryLevelsCommand
        extends BasePaginatedMultipleEntitiesCommand<PartyInventoryLevel, GetPartyInventoryLevelsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyInventoryLevel.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    InventoryControl inventoryControl;

    @Inject
    ItemControl itemControl;

    @Inject
    PartyControl partyControl;

    @Inject
    WarehouseControl warehouseControl;

    @Inject
    PartyInventoryLevelUtil partyInventoryLevelUtil;

    /** Creates a new instance of GetPartyInventoryLevelsCommand */
    public GetPartyInventoryLevelsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private Item item;
    private InventoryCondition inventoryCondition;
    private Party party;

    @Override
    protected void handleForm() {
        var partyName = form.getPartyName();
        var companyName = form.getCompanyName();
        var warehouseName = form.getWarehouseName();
        var itemName = form.getItemName();
        var inventoryConditionName = form.getInventoryConditionName();
        var parameterCount = (partyName == null ? 0 : 1) + (companyName == null ? 0 : 1) + (warehouseName == null ? 0 : 1) +
                (itemName == null ? 0 : 1) + (inventoryConditionName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(itemName != null) {
                item = ItemLogic.getInstance().getItemByName(this, itemName);
            } else if(inventoryConditionName != null) {
                inventoryCondition = InventoryConditionLogic.getInstance().getInventoryConditionByName(this, inventoryConditionName);
            } else {
                party = partyInventoryLevelUtil.getParty(this, partyName, companyName, warehouseName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(item != null) {
                total = inventoryControl.countPartyInventoryLevelsByItem(item);
            } else if(inventoryCondition != null) {
                total = inventoryControl.countPartyInventoryLevelsByInventoryCondition(inventoryCondition);
            } else if(party != null) {
                total = inventoryControl.countPartyInventoryLevelsByParty(party);
            }
        }

        return total;
    }

    @Override
    protected Collection<PartyInventoryLevel> getEntities() {
        Collection<PartyInventoryLevel> entities = null;

        if(!hasExecutionErrors()) {
            if(item != null) {
                entities = inventoryControl.getPartyInventoryLevelsByItem(item);
            } else if(inventoryCondition != null) {
                entities = inventoryControl.getPartyInventoryLevelsByInventoryCondition(inventoryCondition);
            } else if(party != null) {
                entities = inventoryControl.getPartyInventoryLevelsByParty(party);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<PartyInventoryLevel> entities) {
        var result = InventoryResultFactory.getGetPartyInventoryLevelsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(item != null) {
                result.setItem(itemControl.getItemTransfer(userVisit, item));
            } else if(inventoryCondition != null) {
                result.setInventoryCondition(inventoryControl.getInventoryConditionTransfer(userVisit, inventoryCondition));
            } else if(party != null) {
                var partyType = PartyTypes.valueOf(partyInventoryLevelUtil.getPartyTypeName(party));

                switch(partyType) {
                    case COMPANY -> result.setCompany(partyControl.getCompanyTransfer(userVisit, party));
                    case WAREHOUSE -> result.setWarehouse(warehouseControl.getWarehouseTransfer(userVisit, party));
                    default -> {}
                }
            }

            if(session.hasLimit(PartyInventoryLevelFactory.class)) {
                result.setPartyInventoryLevelCount(getTotalEntities());
            }

            result.setPartyInventoryLevels(inventoryControl.getPartyInventoryLevelTransfers(userVisit, entities));
        }

        return result;
    }

}
