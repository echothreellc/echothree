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

import com.echothree.control.user.item.common.form.CreateItemPriceForm;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.inventory.common.InventoryConstants;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.item.common.ItemConstants;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUse;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUseType;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDetail;
import com.echothree.model.data.item.server.entity.ItemPrice;
import com.echothree.model.data.item.server.entity.ItemUnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateItemPriceCommand
        extends BaseSimpleCommand<CreateItemPriceForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitPrice:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_UNIT, Boolean.FALSE, null, null),
                new FieldDefinition("MinimumUnitPrice:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_UNIT, Boolean.FALSE, null, null),
                new FieldDefinition("MaximumUnitPrice:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_UNIT, Boolean.FALSE, null, null),
                new FieldDefinition("UnitPriceIncrement:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_UNIT, Boolean.FALSE, null, null)
                ));
    }
    
    /** Creates a new instance of CreateItemPriceCommand */
    public CreateItemPriceCommand(UserVisitPK userVisitPK, CreateItemPriceForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        String itemName = form.getItemName();
        Item item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            InventoryControl inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
            String inventoryConditionName = form.getInventoryConditionName();
            InventoryCondition inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
            
            if(inventoryCondition != null) {
                InventoryConditionUseType inventoryConditionUseType = inventoryControl.getInventoryConditionUseTypeByName(InventoryConstants.InventoryConditionUseType_SALES_ORDER);
                InventoryConditionUse inventoryConditionUse = inventoryControl.getInventoryConditionUse(inventoryConditionUseType,
                        inventoryCondition);
                
                if(inventoryConditionUse != null) {
                    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
                    String unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                    ItemDetail itemDetail = item.getLastDetail();
                    UnitOfMeasureKind unitOfMeasureKind = itemDetail.getUnitOfMeasureKind();
                    UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                    
                    if(unitOfMeasureType != null) {
                        ItemUnitOfMeasureType itemUnitOfMeasureType = itemControl.getItemUnitOfMeasureType(item, unitOfMeasureType);
                        
                        if(itemUnitOfMeasureType != null) {
                            AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                            String currencyIsoName = form.getCurrencyIsoName();
                            Currency currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
                            
                            if(currency != null) {
                                ItemPrice itemPrice = itemControl.getItemPrice(item, inventoryCondition, unitOfMeasureType, currency);
                                
                                if(itemPrice == null) {
                                    String itemPriceTypeName = itemDetail.getItemPriceType().getItemPriceTypeName();
                                    BasePK createdBy = getPartyPK();
                                    
                                    if(itemPriceTypeName.equals(ItemConstants.ItemPriceType_FIXED)) {
                                        String strUnitPrice = form.getUnitPrice();
                                        
                                        if(strUnitPrice != null) {
                                            Long unitPrice = Long.valueOf(strUnitPrice);
                                            
                                            itemPrice = itemControl.createItemPrice(item, inventoryCondition, unitOfMeasureType,
                                                    currency, createdBy);
                                            itemControl.createItemFixedPrice(itemPrice, unitPrice, createdBy);
                                        } else {
                                            addExecutionError(ExecutionErrors.MissingUnitPrice.name());
                                        }
                                    } else if(itemPriceTypeName.equals(ItemConstants.ItemPriceType_VARIABLE)) {
                                        String strMinimumUnitPrice = form.getMinimumUnitPrice();
                                        Long minimumUnitPrice = null;
                                        String strMaximumUnitPrice = form.getMaximumUnitPrice();
                                        Long maximumUnitPrice = null;
                                        String strUnitPriceIncrement = form.getUnitPriceIncrement();
                                        Long unitPriceIncrement = null;
                                        
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
                                        
                                        if(minimumUnitPrice != null && maximumUnitPrice != null && unitPriceIncrement != null) {
                                            itemPrice = itemControl.createItemPrice(item, inventoryCondition, unitOfMeasureType,
                                                    currency, createdBy);
                                            itemControl.createItemVariablePrice(itemPrice, minimumUnitPrice, maximumUnitPrice,
                                                    unitPriceIncrement, createdBy);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownItemPriceType.name(), itemPriceTypeName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.DuplicateItemPrice.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownItemUnitOfMeasureType.name(), itemName, unitOfMeasureTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidInventoryCondition.name(), inventoryConditionName);
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
