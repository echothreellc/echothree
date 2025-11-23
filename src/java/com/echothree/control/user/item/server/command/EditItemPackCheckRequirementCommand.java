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
import com.echothree.control.user.item.common.edit.ItemPackCheckRequirementEdit;
import com.echothree.control.user.item.common.form.EditItemPackCheckRequirementForm;
import com.echothree.control.user.item.common.result.EditItemPackCheckRequirementResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemPackCheckRequirementSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemPackCheckRequirement;
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
public class EditItemPackCheckRequirementCommand
        extends BaseAbstractEditCommand<ItemPackCheckRequirementSpec, ItemPackCheckRequirementEdit, EditItemPackCheckRequirementResult, ItemPackCheckRequirement, Item> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MinimumQuantity", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("MaximumQuantity", FieldType.UNSIGNED_LONG, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditItemPackCheckRequirementCommand */
    public EditItemPackCheckRequirementCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditItemPackCheckRequirementResult getResult() {
        return ItemResultFactory.getEditItemPackCheckRequirementResult();
    }

    @Override
    public ItemPackCheckRequirementEdit getEdit() {
        return ItemEditFactory.getItemPackCheckRequirementEdit();
    }

    @Override
    public ItemPackCheckRequirement getEntity(EditItemPackCheckRequirementResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemPackCheckRequirement itemPackCheckRequirement = null;
        var itemName = spec.getItemName();
        var item = itemControl.getItemByName(itemName);

        if(item != null) {
            var uomControl = Session.getModelController(UomControl.class);
            var unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(item.getLastDetail().getUnitOfMeasureKind(), unitOfMeasureTypeName);

            if(unitOfMeasureType != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    itemPackCheckRequirement = itemControl.getItemPackCheckRequirement(item, unitOfMeasureType);
                } else { // EditMode.UPDATE
                    itemPackCheckRequirement = itemControl.getItemPackCheckRequirementForUpdate(item, unitOfMeasureType);
                }

                if(itemPackCheckRequirement == null) {
                    addExecutionError(ExecutionErrors.UnknownItemPackCheckRequirement.name(), itemName, unitOfMeasureTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }

        return itemPackCheckRequirement;
    }

    @Override
    public Item getLockEntity(ItemPackCheckRequirement itemPackCheckRequirement) {
        return itemPackCheckRequirement.getItem();
    }

    @Override
    public void fillInResult(EditItemPackCheckRequirementResult result, ItemPackCheckRequirement itemPackCheckRequirement) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setItemPackCheckRequirement(itemControl.getItemPackCheckRequirementTransfer(getUserVisit(), itemPackCheckRequirement));
    }

    Long minimumQuantity;
    Long maximumQuantity;

    @Override
    public void doLock(ItemPackCheckRequirementEdit edit, ItemPackCheckRequirement itemPackCheckRequirement) {
        minimumQuantity = itemPackCheckRequirement.getMinimumQuantity();
        maximumQuantity = itemPackCheckRequirement.getMaximumQuantity();

        edit.setMinimumQuantity(minimumQuantity == null ? null : minimumQuantity.toString());
        edit.setMaximumQuantity(maximumQuantity == null ? null : maximumQuantity.toString());
    }

    @Override
    public void canUpdate(ItemPackCheckRequirement itemPackCheckRequirement) {
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
    public void doUpdate(ItemPackCheckRequirement itemPackCheckRequirement) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemPackCheckRequirementValue = itemControl.getItemPackCheckRequirementValue(itemPackCheckRequirement);

        itemPackCheckRequirementValue.setMinimumQuantity(minimumQuantity);
        itemPackCheckRequirementValue.setMaximumQuantity(maximumQuantity);

        itemControl.updateItemPackCheckRequirementFromValue(itemPackCheckRequirementValue, getPartyPK());
    }
    
}
