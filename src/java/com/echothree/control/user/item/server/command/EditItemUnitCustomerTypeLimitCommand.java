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
import com.echothree.control.user.item.common.edit.ItemUnitCustomerTypeLimitEdit;
import com.echothree.control.user.item.common.form.EditItemUnitCustomerTypeLimitForm;
import com.echothree.control.user.item.common.result.EditItemUnitCustomerTypeLimitResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemUnitCustomerTypeLimitSpec;
import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.item.server.entity.ItemUnitCustomerTypeLimit;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditItemUnitCustomerTypeLimitCommand
        extends BaseAbstractEditCommand<ItemUnitCustomerTypeLimitSpec, ItemUnitCustomerTypeLimitEdit, EditItemUnitCustomerTypeLimitResult, ItemUnitCustomerTypeLimit, ItemUnitCustomerTypeLimit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CustomerTypeName", FieldType.PERCENT, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MinimumQuantity", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("MaximumQuantity", FieldType.UNSIGNED_LONG, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditItemUnitCustomerTypeLimitCommand */
    public EditItemUnitCustomerTypeLimitCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditItemUnitCustomerTypeLimitResult getResult() {
        return ItemResultFactory.getEditItemUnitCustomerTypeLimitResult();
    }

    @Override
    public ItemUnitCustomerTypeLimitEdit getEdit() {
        return ItemEditFactory.getItemUnitCustomerTypeLimitEdit();
    }

    @Override
    public ItemUnitCustomerTypeLimit getEntity(EditItemUnitCustomerTypeLimitResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit = null;
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
                    var customerControl = Session.getModelController(CustomerControl.class);
                    var customerTypeName = spec.getCustomerTypeName();
                    var customerType = customerControl.getCustomerTypeByName(customerTypeName);

                    if(customerType != null) {
                        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                            itemUnitCustomerTypeLimit = itemControl.getItemUnitCustomerTypeLimit(item, inventoryCondition, unitOfMeasureType, customerType);
                        } else { // EditMode.UPDATE
                            itemUnitCustomerTypeLimit = itemControl.getItemUnitCustomerTypeLimitForUpdate(item, inventoryCondition, unitOfMeasureType, customerType);
                        }

                        if(itemUnitCustomerTypeLimit == null) {
                            addExecutionError(ExecutionErrors.UnknownItemUnitCustomerTypeLimit.name(), itemName, inventoryConditionName, unitOfMeasureTypeName,
                                    customerTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCustomerTypeName.name(), customerTypeName);
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

        return itemUnitCustomerTypeLimit;
    }

    @Override
    public ItemUnitCustomerTypeLimit getLockEntity(ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit) {
        return itemUnitCustomerTypeLimit;
    }

    @Override
    public void fillInResult(EditItemUnitCustomerTypeLimitResult result, ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setItemUnitCustomerTypeLimit(itemControl.getItemUnitCustomerTypeLimitTransfer(getUserVisit(), itemUnitCustomerTypeLimit));
    }

    Long minimumQuantity;
    Long maximumQuantity;

    @Override
    public void doLock(ItemUnitCustomerTypeLimitEdit edit, ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit) {
        minimumQuantity = itemUnitCustomerTypeLimit.getMinimumQuantity();
        maximumQuantity = itemUnitCustomerTypeLimit.getMaximumQuantity();

        edit.setMinimumQuantity(minimumQuantity == null ? null : minimumQuantity.toString());
        edit.setMaximumQuantity(maximumQuantity == null ? null : maximumQuantity.toString());
    }

    @Override
    public void canUpdate(ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit) {
        var strMinimumQuantity = edit.getMinimumQuantity();
        var strMaximumQuantity = edit.getMaximumQuantity();

        minimumQuantity = strMinimumQuantity == null ? null : Long.valueOf(strMinimumQuantity);
        maximumQuantity = strMaximumQuantity == null ? null : Long.valueOf(strMaximumQuantity);

        if(minimumQuantity != null && maximumQuantity != null) {
            if(maximumQuantity < minimumQuantity) {
                addExecutionError(ExecutionErrors.MaximumQuantityLessThanMinimumQuantity.name());
            }
        }
    }

    @Override
    public void doUpdate(ItemUnitCustomerTypeLimit itemUnitCustomerTypeLimit) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemUnitCustomerTypeLimitValue = itemControl.getItemUnitCustomerTypeLimitValue(itemUnitCustomerTypeLimit);

        itemUnitCustomerTypeLimitValue.setMinimumQuantity(minimumQuantity);
        itemUnitCustomerTypeLimitValue.setMaximumQuantity(maximumQuantity);

        itemControl.updateItemUnitCustomerTypeLimitFromValue(itemUnitCustomerTypeLimitValue, getPartyPK());
    }
    
}
