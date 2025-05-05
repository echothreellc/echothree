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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.form.GetPartyInventoryLevelsForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.common.transfer.PartyInventoryLevelTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetPartyInventoryLevelsCommand
        extends BasePartyInventoryLevelCommand<GetPartyInventoryLevelsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetPartyInventoryLevelsCommand */
    public GetPartyInventoryLevelsCommand() {
        super(FORM_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var result = InventoryResultFactory.getGetPartyInventoryLevelsResult();
        var partyName = form.getPartyName();
        var companyName = form.getCompanyName();
        var warehouseName = form.getWarehouseName();
        var itemName = form.getItemName();
        var inventoryConditionName = form.getInventoryConditionName();
        var parameterCount = (partyName == null ? 0 : 1) + (companyName == null ? 0 : 1) + (warehouseName == null ? 0 : 1) +
                (itemName == null ? 0 : 1) + (inventoryConditionName == null ? 0 : 1);
        
        if(parameterCount == 1) {
            var inventoryControl = Session.getModelController(InventoryControl.class);
            var userVisit = getUserVisit();
            List<PartyInventoryLevelTransfer> partyInventoryLevels = null;
            
            if(itemName != null) {
                var itemControl = Session.getModelController(ItemControl.class);
                var item = itemControl.getItemByName(itemName);
                
                if(item != null) {
                    partyInventoryLevels = inventoryControl.getPartyInventoryLevelTransfersByItem(userVisit, item);
                    result.setItem(itemControl.getItemTransfer(userVisit, item));
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
                }
            } if(inventoryConditionName != null) {
                var inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
                
                if(inventoryCondition != null) {
                    partyInventoryLevels = inventoryControl.getPartyInventoryLevelTransfersByInventoryCondition(userVisit, inventoryCondition);
                    result.setInventoryCondition(inventoryControl.getInventoryConditionTransfer(userVisit, inventoryCondition));
                } else {
                    addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
                }
            }else {
                var party = getParty(partyName, companyName, warehouseName);
                
                if(party != null) {
                    var partyControl = Session.getModelController(PartyControl.class);
                    
                    partyInventoryLevels = inventoryControl.getPartyInventoryLevelTransfersByParty(userVisit, party);
                    
                    if(partyName != null) {
                        result.setParty(partyControl.getPartyTransfer(userVisit, party));
                    } else if(companyName != null) {
                        result.setCompany(partyControl.getCompanyTransfer(userVisit, party));
                    } else if(warehouseName != null) {
                        var warehouseControl = Session.getModelController(WarehouseControl.class);
                        
                        result.setWarehouse(warehouseControl.getWarehouseTransfer(userVisit, party));
                    }
                }
            }
            
            result.setPartyInventoryLevels(partyInventoryLevels);
        }  else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
