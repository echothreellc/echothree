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

import com.echothree.control.user.inventory.common.form.GetPartyInventoryLevelForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
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

public class GetPartyInventoryLevelCommand
        extends BasePartyInventoryLevelCommand<GetPartyInventoryLevelForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetPartyInventoryLevelCommand */
    public GetPartyInventoryLevelCommand() {
        super(FORM_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var result = InventoryResultFactory.getGetPartyInventoryLevelResult();
        var party = getParty(form);
        
        if(party != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var partyControl = Session.getModelController(PartyControl.class);
            var itemName = form.getItemName();
            var item = itemControl.getItemByName(itemName);
            var userVisit = getUserVisit();
            
            if(form.getPartyName() != null) {
                result.setParty(partyControl.getPartyTransfer(userVisit, party));
            } else if(form.getCompanyName() != null) {
                result.setCompany(partyControl.getCompanyTransfer(userVisit, party));
            } else if(form.getWarehouseName() != null) {
                var warehouseControl = Session.getModelController(WarehouseControl.class);
                
                result.setWarehouse(warehouseControl.getWarehouseTransfer(userVisit, party));
            }
            
            if(item != null) {
                var inventoryConditionName = form.getInventoryConditionName();
                var inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
                
                if(inventoryCondition != null) {
                    var partyInventoryLevel = inventoryControl.getPartyInventoryLevel(party, item, inventoryCondition);
                    
                    if(partyInventoryLevel != null) {
                        result.setPartyInventoryLevel(inventoryControl.getPartyInventoryLevelTransfer(userVisit, partyInventoryLevel));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownPartyInventoryLevel.name(), party.getLastDetail().getPartyName(), itemName, inventoryConditionName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
            }
        }
        
        return result;
    }
    
}
