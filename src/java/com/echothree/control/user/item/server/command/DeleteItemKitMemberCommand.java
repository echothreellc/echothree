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

import com.echothree.control.user.item.common.form.DeleteItemKitMemberForm;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.common.ItemConstants;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemDetail;
import com.echothree.model.data.item.server.entity.ItemKitMember;
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

public class DeleteItemKitMemberCommand
        extends BaseSimpleCommand<DeleteItemKitMemberForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("UnitOfMeasureTypeName", FieldType.PERCENT, true, null, null),
            new FieldDefinition("MemberItemName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("MemberInventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("MemberUnitOfMeasureTypeName", FieldType.PERCENT, true, null, null)
        ));
    }
    
    /** Creates a new instance of DeleteItemKitMemberCommand */
    public DeleteItemKitMemberCommand(UserVisitPK userVisitPK, DeleteItemKitMemberForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        String itemName = form.getItemName();
        Item item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            ItemDetail itemDetail = item.getLastDetail();
            String itemTypeName = itemDetail.getItemType().getItemTypeName();
            
            if(itemTypeName.equals(ItemConstants.ItemType_KIT)) {
                var inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
                String inventoryConditionName = form.getInventoryConditionName();
                InventoryCondition inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
                
                if(inventoryCondition != null) {
                    var uomControl = (UomControl)Session.getModelController(UomControl.class);
                    String unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                    UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(itemDetail.getUnitOfMeasureKind(), unitOfMeasureTypeName);
                    
                    if(unitOfMeasureType != null) {
                        String memberItemName = form.getMemberItemName();
                        Item memberItem = itemControl.getItemByName(memberItemName);
                        
                        if(memberItem != null) {
                            String memberInventoryConditionName = form.getMemberInventoryConditionName();
                            InventoryCondition memberInventoryCondition = inventoryControl.getInventoryConditionByName(memberInventoryConditionName);
                            
                            if(memberInventoryCondition != null) {
                                String memberUnitOfMeasureTypeName = form.getMemberUnitOfMeasureTypeName();
                                UnitOfMeasureType memberUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(memberItem.getLastDetail().getUnitOfMeasureKind(), memberUnitOfMeasureTypeName);
                                
                                if(memberUnitOfMeasureType != null) {
                                    ItemKitMember itemKitMember = itemControl.getItemKitMemberForUpdate(item, inventoryCondition,
                                            unitOfMeasureType, memberItem, memberInventoryCondition, memberUnitOfMeasureType);
                                    
                                    if(itemKitMember != null) {
                                        itemControl.deleteItemKitMember(itemKitMember, getPartyPK());
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownItemKitMember.name());
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
