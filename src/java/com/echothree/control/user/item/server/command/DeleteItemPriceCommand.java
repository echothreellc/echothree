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

import com.echothree.control.user.item.common.form.DeleteItemPriceForm;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
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

public class DeleteItemPriceCommand
        extends BaseSimpleCommand<DeleteItemPriceForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemPrice.name(), SecurityRoles.Delete.name())
                )))
        )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteItemPriceCommand */
    public DeleteItemPriceCommand() {
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
                var uomControl = Session.getModelController(UomControl.class);
                var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                var itemDetail = item.getLastDetail();
                var unitOfMeasureKind = itemDetail.getUnitOfMeasureKind();
                var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                
                if(unitOfMeasureType != null) {
                    var accountingControl = Session.getModelController(AccountingControl.class);
                    var currencyIsoName = form.getCurrencyIsoName();
                    var currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
                    
                    if(currency != null) {
                        var itemPrice = itemControl.getItemPriceForUpdate(item, inventoryCondition, unitOfMeasureType, currency);
                        
                        if(itemPrice != null) {
                            BasePK deletedBy = getPartyPK();
                            
                            itemControl.deleteItemPrice(itemPrice, deletedBy);
                            
                            if(itemControl.getItemPrices(item, inventoryCondition, unitOfMeasureType).isEmpty()) {
                                itemControl.deleteItemKitMembers(itemControl.getItemKitMembersFromMemberItemForUpdate(item,
                                        inventoryCondition, unitOfMeasureType), deletedBy);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownItemPrice.name(), itemName, inventoryConditionName,
                                    unitOfMeasureTypeName, currencyIsoName);
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
