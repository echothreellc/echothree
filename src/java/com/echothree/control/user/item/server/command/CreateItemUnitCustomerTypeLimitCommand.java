// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.control.user.item.common.form.CreateItemUnitCustomerTypeLimitForm;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.customer.server.entity.CustomerType;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemUnitCustomerTypeLimit;
import com.echothree.model.data.item.server.entity.ItemUnitOfMeasureType;
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

public class CreateItemUnitCustomerTypeLimitCommand
        extends BaseSimpleCommand<CreateItemUnitCustomerTypeLimitForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.PERCENT, true, null, null),
                new FieldDefinition("CustomerTypeName", FieldType.PERCENT, true, null, null),
                new FieldDefinition("MinimumQuantity", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("MaximumQuantity", FieldType.UNSIGNED_LONG, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateItemUnitCustomerTypeLimitCommand */
    public CreateItemUnitCustomerTypeLimitCommand(UserVisitPK userVisitPK, CreateItemUnitCustomerTypeLimitForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var itemControl = Session.getModelController(ItemControl.class);
        String itemName = form.getItemName();
        Item item = itemControl.getItemByName(itemName);
        
        if(item != null) {
            var inventoryControl = Session.getModelController(InventoryControl.class);
            String inventoryConditionName = form.getInventoryConditionName();
            InventoryCondition inventoryCondition = inventoryControl.getInventoryConditionByName(inventoryConditionName);
            
            if(inventoryCondition != null) {
                var uomControl = Session.getModelController(UomControl.class);
                String unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(item.getLastDetail().getUnitOfMeasureKind(), unitOfMeasureTypeName);
                
                if(unitOfMeasureType != null) {
                    ItemUnitOfMeasureType itemUnitOfMeasureType = itemControl.getItemUnitOfMeasureType(item, unitOfMeasureType);
                    
                    if(itemUnitOfMeasureType != null) {
                        var customerControl = Session.getModelController(CustomerControl.class);
                        String customerTypeName = form.getCustomerTypeName();
                        CustomerType customerType = customerControl.getCustomerTypeByName(customerTypeName);
                        
                        if(customerType != null) {
                            ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit = itemControl.getItemUnitCustomerTypeLimit(item,
                                    inventoryCondition, unitOfMeasureType, customerType);
                            
                            if(itemUnitCustomerTypeLimit == null) {
                                String strMinimumQuantity = form.getMinimumQuantity();
                                Long minimumQuantity = strMinimumQuantity == null ? null : Long.valueOf(strMinimumQuantity);
                                String strMaximumQuantity = form.getMaximumQuantity();
                                Long maximumQuantity = strMaximumQuantity == null ? null : Long.valueOf(strMaximumQuantity);
                                
                                if(minimumQuantity != null && maximumQuantity != null) {
                                    if(maximumQuantity < minimumQuantity) {
                                        addExecutionError(ExecutionErrors.MaximumQuantityLessThanMinimumQuantity.name());
                                    }
                                }

                                if(!hasExecutionErrors()) {
                                    itemControl.createItemUnitCustomerTypeLimit(item, inventoryCondition, unitOfMeasureType, customerType, minimumQuantity,
                                            maximumQuantity, getPartyPK());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.DuplicateItemUnitCustomerTypeLimit.name(), itemName, inventoryConditionName, unitOfMeasureTypeName, customerTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
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
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }
        
        return null;
    }
    
}
