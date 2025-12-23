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

import com.echothree.control.user.inventory.common.form.CreatePartyInventoryLevelForm;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreatePartyInventoryLevelCommand
        extends BasePartyInventoryLevelCommand<CreatePartyInventoryLevelForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MinimumInventoryUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MinimumInventory", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("MaximumInventoryUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MaximumInventory", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("ReorderQuantityUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ReorderQuantity", FieldType.UNSIGNED_LONG, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePartyInventoryLevelCommand */
    public CreatePartyInventoryLevelCommand() {
        super(FORM_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        var party = getParty(form);
        
        if(party != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var itemName = form.getItemName();
            var item = itemControl.getItemByName(itemName);
            
            if(item != null) {
                var partyTypeName = getPartyTypeName(party);
                
                if(partyTypeName.equals(PartyTypes.COMPANY.name())) {
                    if(!party.equals(item.getLastDetail().getCompanyParty())) {
                        addExecutionError(ExecutionErrors.InvalidCompany.name());
                    }
                }
                
                if(!hasExecutionErrors()) {
                    var inventoryConditionName = form.getInventoryConditionName();
                    var inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
                    
                    if(inventoryCondition != null) {
                        var unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();
                        var unitOfMeasureKind = item.getLastDetail().getUnitOfMeasureKind();
                        var minimumInventory = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, unitOfMeasureKind,
                                form.getMinimumInventory(), form.getMinimumInventoryUnitOfMeasureTypeName(),
                                null, ExecutionErrors.MissingRequiredMinimumInventory.name(), null, ExecutionErrors.MissingRequiredMinimumInventoryUnitOfMeasureTypeName.name(),
                                null, ExecutionErrors.UnknownMinimumInventoryUnitOfMeasureTypeName.name());

                        if(!hasExecutionErrors()) {
                            var maximumInventory = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, unitOfMeasureKind,
                                    form.getMaximumInventory(), form.getMaximumInventoryUnitOfMeasureTypeName(),
                                    null, ExecutionErrors.MissingRequiredMaximumInventory.name(), null, ExecutionErrors.MissingRequiredMaximumInventoryUnitOfMeasureTypeName.name(),
                                    null, ExecutionErrors.UnknownMaximumInventoryUnitOfMeasureTypeName.name());

                            if(!hasExecutionErrors()) {
                                var reorderQuantity = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, unitOfMeasureKind,
                                        form.getReorderQuantity(), form.getReorderQuantityUnitOfMeasureTypeName(),
                                        null, ExecutionErrors.MissingRequiredReorderQuantity.name(), null, ExecutionErrors.MissingRequiredReorderQuantityUnitOfMeasureTypeName.name(),
                                        null, ExecutionErrors.UnknownReorderQuantityUnitOfMeasureTypeName.name());

                                if(!hasExecutionErrors()) {
                                    var partyInventoryLevel = inventoryControl.getPartyInventoryLevel(party, item, inventoryCondition);
                                    
                                    if(partyInventoryLevel == null) {
                                        inventoryControl.createPartyInventoryLevel(party, item, inventoryCondition, minimumInventory, maximumInventory,
                                                reorderQuantity, getPartyPK());
                                    } else {
                                        addExecutionError(ExecutionErrors.DuplicatePartyInventoryLevel.name(), party.getLastDetail().getPartyName(), itemName,
                                                inventoryConditionName);
                                    }
                                }
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
            }
        }
        
        return null;
    }
    
}
