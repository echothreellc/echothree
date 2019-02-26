// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.item.common.edit.ItemPriceEdit;
import com.echothree.control.user.item.common.form.EditItemPriceForm;
import com.echothree.control.user.item.common.result.EditItemPriceResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemPriceSpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDetail;
import com.echothree.model.data.item.server.entity.ItemFixedPrice;
import com.echothree.model.data.item.server.entity.ItemPrice;
import com.echothree.model.data.item.server.entity.ItemVariablePrice;
import com.echothree.model.data.item.server.value.ItemFixedPriceValue;
import com.echothree.model.data.item.server.value.ItemVariablePriceValue;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditItemPriceCommand
        extends BaseAbstractEditCommand<ItemPriceSpec, ItemPriceEdit, EditItemPriceResult, ItemPrice, ItemPrice> {
    
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
                new FieldDefinition("UnitPrice", FieldType.UNSIGNED_PRICE_UNIT, false, null, null),
                new FieldDefinition("MinimumUnitPrice", FieldType.UNSIGNED_PRICE_UNIT, false, null, null),
                new FieldDefinition("MaximumUnitPrice", FieldType.UNSIGNED_PRICE_UNIT, false, null, null),
                new FieldDefinition("UnitPriceIncrement", FieldType.UNSIGNED_PRICE_UNIT, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditItemPriceCommand */
    public EditItemPriceCommand(UserVisitPK userVisitPK, EditItemPriceForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
        String currencyIsoName = spec.getCurrencyIsoName();
        
        validator.setCurrency(accountingControl.getCurrencyByIsoName(currencyIsoName));
    }
    
    @Override
    public EditItemPriceResult getResult() {
        return ItemResultFactory.getEditItemPriceResult();
    }

    @Override
    public ItemPriceEdit getEdit() {
        return ItemEditFactory.getItemPriceEdit();
    }

    Currency currency;
    String itemPriceTypeName;

    @Override
    public ItemPrice getEntity(EditItemPriceResult result) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        ItemPrice itemPrice = null;
        String itemName = spec.getItemName();
        Item item = itemControl.getItemByName(itemName);

        if(item != null) {
            InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
            String inventoryConditionName = spec.getInventoryConditionName();
            InventoryCondition inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);

            if(inventoryCondition != null) {
                UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
                ItemDetail itemDetail = item.getLastDetail();
                String unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
                UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(itemDetail.getUnitOfMeasureKind(), unitOfMeasureTypeName);

                if(unitOfMeasureType != null) {
                    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                    String currencyIsoName = spec.getCurrencyIsoName();

                    currency = accountingControl.getCurrencyByIsoName(currencyIsoName);

                    if(currency != null) {
                        itemPrice = itemControl.getItemPrice(item, inventoryCondition, unitOfMeasureType, currency);

                        if(itemPrice != null) {
                            itemPriceTypeName = itemDetail.getItemPriceType().getItemPriceTypeName();
                        } else {
                            addExecutionError(ExecutionErrors.UnknownItemPrice.name(), itemName, inventoryConditionName, unitOfMeasureTypeName, currencyIsoName);
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

        return itemPrice;
    }

    @Override
    public ItemPrice getLockEntity(ItemPrice itemPrice) {
        return itemPrice;
    }

    @Override
    public void fillInResult(EditItemPriceResult result, ItemPrice itemPrice) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);

        result.setItemPrice(itemControl.getItemPriceTransfer(getUserVisit(), itemPrice));
    }

    @Override
    public void doLock(ItemPriceEdit edit, ItemPrice itemPrice) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);

        if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
            ItemFixedPrice itemFixedPrice = itemControl.getItemFixedPrice(itemPrice);

            edit.setUnitPrice(AmountUtils.getInstance().formatPriceUnit(currency, itemFixedPrice.getUnitPrice()));
        } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
            ItemVariablePrice itemVariablePrice = itemControl.getItemVariablePrice(itemPrice);

            edit.setMinimumUnitPrice(AmountUtils.getInstance().formatPriceUnit(currency, itemVariablePrice.getMinimumUnitPrice()));
            edit.setMaximumUnitPrice(AmountUtils.getInstance().formatPriceUnit(currency, itemVariablePrice.getMaximumUnitPrice()));
            edit.setUnitPriceIncrement(AmountUtils.getInstance().formatPriceUnit(currency, itemVariablePrice.getUnitPriceIncrement()));
        } else {
            addExecutionError(ExecutionErrors.UnknownItemPriceType.name(), itemPriceTypeName);
        }
    }

    Long unitPrice;
    Long minimumUnitPrice;
    Long maximumUnitPrice;
    Long unitPriceIncrement;

    @Override
    public void canUpdate(ItemPrice itemPrice) {
        if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
            String strUnitPrice = edit.getUnitPrice();

            if(strUnitPrice != null) {
                unitPrice = Long.valueOf(strUnitPrice);
            } else {
                addExecutionError(ExecutionErrors.MissingUnitPrice.name());
            }
        } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
            String strMinimumUnitPrice = edit.getMinimumUnitPrice();
            String strMaximumUnitPrice = edit.getMaximumUnitPrice();
            String strUnitPriceIncrement = edit.getUnitPriceIncrement();

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

            if(strUnitPriceIncrement != null) {
                unitPriceIncrement = Long.valueOf(strUnitPriceIncrement);
            } else {
                addExecutionError(ExecutionErrors.MissingUnitPriceIncrement.name());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemPriceType.name(), itemPriceTypeName);
        }
    }

    @Override
    public void doUpdate(ItemPrice itemPrice) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
            ItemFixedPriceValue itemFixedPriceValue = itemControl.getItemFixedPriceValueForUpdate(itemPrice);

            itemFixedPriceValue.setUnitPrice(unitPrice);

            itemControl.updateItemFixedPriceFromValue(itemFixedPriceValue, getPartyPK());
        } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
            ItemVariablePriceValue itemVariablePriceValue = itemControl.getItemVariablePriceValueForUpdate(itemPrice);

            itemVariablePriceValue.setMinimumUnitPrice(minimumUnitPrice);
            itemVariablePriceValue.setMaximumUnitPrice(maximumUnitPrice);
            itemVariablePriceValue.setUnitPriceIncrement(unitPriceIncrement);

            itemControl.updateItemVariablePriceFromValue(itemVariablePriceValue, getPartyPK());
        }
    }
    
}
