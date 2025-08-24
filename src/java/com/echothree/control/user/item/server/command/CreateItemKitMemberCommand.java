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

import com.echothree.control.user.item.common.form.CreateItemKitMemberForm;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.ItemTypes;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateItemKitMemberCommand
        extends BaseSimpleCommand<CreateItemKitMemberForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.PERCENT, true, null, null),
                new FieldDefinition("MemberItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MemberInventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MemberUnitOfMeasureTypeName", FieldType.PERCENT, true, null, null),
                new FieldDefinition("Quantity", FieldType.UNSIGNED_LONG, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateItemKitMemberCommand */
    public CreateItemKitMemberCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemName = form.getItemName();
        var item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            var itemDetail = item.getLastDetail();
            var itemTypeName = itemDetail.getItemType().getItemTypeName();
            
            if(itemTypeName.equals(ItemTypes.KIT.name())) {
                var inventoryControl = Session.getModelController(InventoryControl.class);
                var inventoryConditionName = form.getInventoryConditionName();
                var inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
                
                if(inventoryCondition != null) {
                    var uomControl = Session.getModelController(UomControl.class);
                    var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                    var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(itemDetail.getUnitOfMeasureKind(), unitOfMeasureTypeName);
                    
                    if(unitOfMeasureType != null) {
                        var itemUnitOfMeasureType = itemControl.getItemUnitOfMeasureType(item, unitOfMeasureType);
                        
                        if(itemUnitOfMeasureType != null) {
                            var memberItemName = form.getMemberItemName();
                            var memberItem = itemControl.getItemByName(memberItemName);
                            
                            if(memberItem != null) {
                                var memberInventoryConditionName = form.getMemberInventoryConditionName();
                                var memberInventoryCondition = inventoryControl.getInventoryConditionByName(memberInventoryConditionName);
                                
                                if(memberInventoryCondition != null) {
                                    var memberUnitOfMeasureTypeName = form.getMemberUnitOfMeasureTypeName();
                                    var memberUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(memberItem.getLastDetail().getUnitOfMeasureKind(), memberUnitOfMeasureTypeName);
                                    
                                    if(memberUnitOfMeasureType != null) {
                                        itemUnitOfMeasureType = itemControl.getItemUnitOfMeasureType(memberItem, memberUnitOfMeasureType);
                                        
                                        if(itemUnitOfMeasureType != null) {
                                            var itemPrices = itemControl.getItemPrices(memberItem, memberInventoryCondition, memberUnitOfMeasureType);
                                            
                                            if(itemPrices.size() > 0) {
                                                var itemKitMember = itemControl.getItemKitMember(item, inventoryCondition,
                                                        unitOfMeasureType, memberItem, memberInventoryCondition, memberUnitOfMeasureType);
                                                
                                                if(itemKitMember == null) {
                                                    var quantity = Long.valueOf(form.getQuantity());
                                                    
                                                    itemControl.createItemKitMember(item, inventoryCondition, unitOfMeasureType, memberItem,
                                                            memberInventoryCondition, memberUnitOfMeasureType, quantity, getPartyPK());
                                                } else {
                                                    addExecutionError(ExecutionErrors.DuplicateItemKitMember.name());
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.UnknownItemPrice.name(), memberItemName, memberInventoryConditionName, memberUnitOfMeasureTypeName);
                                            }
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownMemberItemUnitOfMeasureType.name(), memberItemName, memberUnitOfMeasureTypeName);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownMemberUnitOfMeasureTypeName.name(), memberUnitOfMeasureTypeName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownMemberInventoryConditionName.name(), memberInventoryConditionName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownMemberItemName.name(), memberItemName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownItemUnitOfMeasureType.name(), itemName, unitOfMeasureTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidItemType.name(), itemTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }
        
        return null;
    }
    
}
