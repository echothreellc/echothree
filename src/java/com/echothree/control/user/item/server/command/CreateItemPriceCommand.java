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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.CreateItemPriceForm;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.inventory.common.InventoryConstants;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateItemPriceCommand
        extends BaseSimpleCommand<CreateItemPriceForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemPrice.name(), SecurityRoles.Create.name())
                )))
        )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitPrice:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_UNIT, false, null, null),
                new FieldDefinition("MinimumUnitPrice:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_UNIT, false, null, null),
                new FieldDefinition("MaximumUnitPrice:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_UNIT, false, null, null),
                new FieldDefinition("UnitPriceIncrement:CurrencyIsoName,CurrencyIsoName", FieldType.UNSIGNED_PRICE_UNIT, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateItemPriceCommand */
    public CreateItemPriceCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemName = form.getItemName();
        var item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            var inventoryControl = Session.getModelController(InventoryControl.class);
            var inventoryConditionName = form.getInventoryConditionName();
            var inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
            
            if(inventoryCondition != null) {
                var inventoryConditionUseType = inventoryControl.getInventoryConditionUseTypeByName(InventoryConstants.InventoryConditionUseType_SALES_ORDER);
                var inventoryConditionUse = inventoryControl.getInventoryConditionUse(inventoryConditionUseType,
                        inventoryCondition);
                
                if(inventoryConditionUse != null) {
                    var uomControl = Session.getModelController(UomControl.class);
                    var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                    var itemDetail = item.getLastDetail();
                    var unitOfMeasureKind = itemDetail.getUnitOfMeasureKind();
                    var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                    
                    if(unitOfMeasureType != null) {
                        var itemUnitOfMeasureType = itemControl.getItemUnitOfMeasureType(item, unitOfMeasureType);
                        
                        if(itemUnitOfMeasureType != null) {
                            var accountingControl = Session.getModelController(AccountingControl.class);
                            var currencyIsoName = form.getCurrencyIsoName();
                            var currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
                            
                            if(currency != null) {
                                var itemPrice = itemControl.getItemPrice(item, inventoryCondition, unitOfMeasureType, currency);
                                
                                if(itemPrice == null) {
                                    var itemPriceTypeName = itemDetail.getItemPriceType().getItemPriceTypeName();
                                    BasePK createdBy = getPartyPK();
                                    
                                    if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
                                        var strUnitPrice = form.getUnitPrice();
                                        
                                        if(strUnitPrice != null) {
                                            var unitPrice = Long.valueOf(strUnitPrice);
                                            
                                            itemPrice = itemControl.createItemPrice(item, inventoryCondition, unitOfMeasureType,
                                                    currency, createdBy);
                                            itemControl.createItemFixedPrice(itemPrice, unitPrice, createdBy);
                                        } else {
                                            addExecutionError(ExecutionErrors.MissingUnitPrice.name());
                                        }
                                    } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
                                        var strMinimumUnitPrice = form.getMinimumUnitPrice();
                                        Long minimumUnitPrice = null;
                                        var strMaximumUnitPrice = form.getMaximumUnitPrice();
                                        Long maximumUnitPrice = null;
                                        var strUnitPriceIncrement = form.getUnitPriceIncrement();
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
