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

import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.edit.ItemPriceEdit;
import com.echothree.control.user.item.common.form.EditItemPriceForm;
import com.echothree.control.user.item.common.result.EditItemPriceResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemPriceSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.item.server.entity.ItemPrice;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditItemPriceCommand
        extends BaseAbstractEditCommand<ItemPriceSpec, ItemPriceEdit, EditItemPriceResult, ItemPrice, ItemPrice> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemPrice.name(), SecurityRoles.Edit.name())
                )))
        )));

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
    public EditItemPriceCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected void setupValidatorForEdit(Validator validator, BaseForm specForm) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var currencyIsoName = spec.getCurrencyIsoName();
        
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
        var itemControl = Session.getModelController(ItemControl.class);
        ItemPrice itemPrice = null;
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
        var itemControl = Session.getModelController(ItemControl.class);

        result.setItemPrice(itemControl.getItemPriceTransfer(getUserVisit(), itemPrice));
    }

    @Override
    public void doLock(ItemPriceEdit edit, ItemPrice itemPrice) {
        var itemControl = Session.getModelController(ItemControl.class);

        if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
            var itemFixedPrice = itemControl.getItemFixedPrice(itemPrice);

            edit.setUnitPrice(AmountUtils.getInstance().formatPriceUnit(currency, itemFixedPrice.getUnitPrice()));
        } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
            var itemVariablePrice = itemControl.getItemVariablePrice(itemPrice);

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
            var strUnitPrice = edit.getUnitPrice();

            if(strUnitPrice != null) {
                unitPrice = Long.valueOf(strUnitPrice);
            } else {
                addExecutionError(ExecutionErrors.MissingUnitPrice.name());
            }
        } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
            var strMinimumUnitPrice = edit.getMinimumUnitPrice();
            var strMaximumUnitPrice = edit.getMaximumUnitPrice();
            var strUnitPriceIncrement = edit.getUnitPriceIncrement();

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
        var itemControl = Session.getModelController(ItemControl.class);
        if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
            var itemFixedPriceValue = itemControl.getItemFixedPriceValueForUpdate(itemPrice);

            itemFixedPriceValue.setUnitPrice(unitPrice);

            itemControl.updateItemFixedPriceFromValue(itemFixedPriceValue, getPartyPK());
        } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
            var itemVariablePriceValue = itemControl.getItemVariablePriceValueForUpdate(itemPrice);

            itemVariablePriceValue.setMinimumUnitPrice(minimumUnitPrice);
            itemVariablePriceValue.setMaximumUnitPrice(maximumUnitPrice);
            itemVariablePriceValue.setUnitPriceIncrement(unitPriceIncrement);

            itemControl.updateItemVariablePriceFromValue(itemVariablePriceValue, getPartyPK());
        }
    }
    
}
