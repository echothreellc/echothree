// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.DeleteItemUnitPriceLimitForm;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemUnitPriceLimit;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteItemUnitPriceLimitCommand
        extends BaseSimpleCommand<DeleteItemUnitPriceLimitForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("UnitOfMeasureTypeName", FieldType.PERCENT, true, null, null),
            new FieldDefinition("CurrencyIsoName", FieldType.PERCENT, true, null, null)
        ));
    }
    
    /** Creates a new instance of DeleteItemUnitPriceLimitCommand */
    public DeleteItemUnitPriceLimitCommand(UserVisitPK userVisitPK, DeleteItemUnitPriceLimitForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        String itemName = form.getItemName();
        Item item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
            String inventoryConditionName = form.getInventoryConditionName();
            InventoryCondition inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
            
            if(inventoryCondition != null) {
                var uomControl = (UomControl)Session.getModelController(UomControl.class);
                String unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(item.getLastDetail().getUnitOfMeasureKind(), unitOfMeasureTypeName);
                
                if(unitOfMeasureType != null) {
                    var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                    String currencyIsoName = form.getCurrencyIsoName();
                    Currency currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
                    
                    if(currency != null) {
                        ItemUnitPriceLimit itemUnitPriceLimit = itemControl.getItemUnitPriceLimitForUpdate(item, inventoryCondition, unitOfMeasureType, currency);
                        
                        if(itemUnitPriceLimit != null) {
                            itemControl.deleteItemUnitPriceLimit(itemUnitPriceLimit, getPartyPK());
                        } else {
                            addExecutionError(ExecutionErrors.UnknownItemUnitPriceLimit.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }
        
        return null;
    }
    
}
