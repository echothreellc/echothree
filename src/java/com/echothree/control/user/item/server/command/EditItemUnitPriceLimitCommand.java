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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.edit.ItemUnitPriceLimitEdit;
import com.echothree.control.user.item.common.form.EditItemUnitPriceLimitForm;
import com.echothree.control.user.item.common.result.EditItemUnitPriceLimitResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemUnitPriceLimitSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.item.server.entity.ItemUnitPriceLimit;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditItemUnitPriceLimitCommand
        extends BaseAbstractEditCommand<ItemUnitPriceLimitSpec, ItemUnitPriceLimitEdit, EditItemUnitPriceLimitResult, ItemUnitPriceLimit, ItemUnitPriceLimit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MinimumUnitPrice", FieldType.UNSIGNED_PRICE_UNIT, false, null, null),
                new FieldDefinition("MaximumUnitPrice", FieldType.UNSIGNED_PRICE_UNIT, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditItemUnitPriceLimitCommand */
    public EditItemUnitPriceLimitCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var currencyIsoName = spec.getCurrencyIsoName();
        
        validator.setCurrency(accountingControl.getCurrencyByIsoName(currencyIsoName));
    }
    
    @Override
    public EditItemUnitPriceLimitResult getResult() {
        return ItemResultFactory.getEditItemUnitPriceLimitResult();
    }

    @Override
    public ItemUnitPriceLimitEdit getEdit() {
        return ItemEditFactory.getItemUnitPriceLimitEdit();
    }

    Currency currency;

    @Override
    public ItemUnitPriceLimit getEntity(EditItemUnitPriceLimitResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemUnitPriceLimit itemUnitPriceLimit = null;
        var itemName = spec.getItemName();
        var item = itemControl.getItemByName(itemName);

        if(item != null) {
            var inventoryControl = Session.getModelController(InventoryControl.class);
            var inventoryConditionName = spec.getInventoryConditionName();
            var inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);

            if(inventoryCondition != null) {
                var uomControl = Session.getModelController(UomControl.class);
                var itemDetail = item.getLastDetail();
                var unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
                var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(itemDetail.getUnitOfMeasureKind(), unitOfMeasureTypeName);

                if(unitOfMeasureType != null) {
                    var accountingControl = Session.getModelController(AccountingControl.class);
                    var currencyIsoName = spec.getCurrencyIsoName();

                    currency = accountingControl.getCurrencyByIsoName(currencyIsoName);

                    if(currency != null) {
                        itemUnitPriceLimit = itemControl.getItemUnitPriceLimit(item, inventoryCondition, unitOfMeasureType, currency);

                        if(itemUnitPriceLimit != null) {
                            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                itemUnitPriceLimit = itemControl.getItemUnitPriceLimit(item, inventoryCondition, unitOfMeasureType, currency);
                            } else { // EditMode.UPDATE
                                itemUnitPriceLimit = itemControl.getItemUnitPriceLimitForUpdate(item, inventoryCondition, unitOfMeasureType, currency);
                            }

                            if(itemUnitPriceLimit == null) {
                                addExecutionError(ExecutionErrors.UnknownItemUnitPriceLimit.name(), itemName, inventoryConditionName, unitOfMeasureTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownItemUnitPriceLimit.name(), itemName, inventoryConditionName, unitOfMeasureTypeName, currencyIsoName);
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

        return itemUnitPriceLimit;
    }

    @Override
    public ItemUnitPriceLimit getLockEntity(ItemUnitPriceLimit itemUnitPriceLimit) {
        return itemUnitPriceLimit;
    }

    @Override
    public void fillInResult(EditItemUnitPriceLimitResult result, ItemUnitPriceLimit itemUnitPriceLimit) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setItemUnitPriceLimit(itemControl.getItemUnitPriceLimitTransfer(getUserVisit(), itemUnitPriceLimit));
    }

    @Override
    public void doLock(ItemUnitPriceLimitEdit edit, ItemUnitPriceLimit itemUnitPriceLimit) {
        edit.setMinimumUnitPrice(AmountUtils.getInstance().formatPriceUnit(currency, itemUnitPriceLimit.getMinimumUnitPrice()));
        edit.setMaximumUnitPrice(AmountUtils.getInstance().formatPriceUnit(currency, itemUnitPriceLimit.getMaximumUnitPrice()));
    }

    Long minimumUnitPrice;
    Long maximumUnitPrice;

    @Override
    public void canUpdate(ItemUnitPriceLimit itemUnitPriceLimit) {
        var strMinimumUnitPrice = edit.getMinimumUnitPrice();
        var strMaximumUnitPrice = edit.getMaximumUnitPrice();

        if(strMinimumUnitPrice != null) {
            minimumUnitPrice = Long.valueOf(strMinimumUnitPrice);
        } else {
            addExecutionError(ExecutionErrors.MissingMinimumUnitPrice.name());
        }

        if(strMaximumUnitPrice != null) {
            maximumUnitPrice = Long.valueOf(strMaximumUnitPrice);
        } else {
            addExecutionError(ExecutionErrors.MissingMaximumUnitPrice.name());
        }
        
        if(minimumUnitPrice != null && maximumUnitPrice != null) {
            if(maximumUnitPrice < minimumUnitPrice) {
                addExecutionError(ExecutionErrors.MaximumUnitPriceLessThanMinimumUnitPrice.name());
            }
        }
    }

    @Override
    public void doUpdate(ItemUnitPriceLimit itemUnitPriceLimit) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemUnitPriceLimitValue = itemControl.getItemUnitPriceLimitValue(itemUnitPriceLimit);

        itemUnitPriceLimitValue.setMinimumUnitPrice(minimumUnitPrice);
        itemUnitPriceLimitValue.setMaximumUnitPrice(maximumUnitPrice);

        itemControl.updateItemUnitPriceLimitFromValue(itemUnitPriceLimitValue, getPartyPK());
    }
    
}
