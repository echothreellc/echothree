// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.item.remote.edit.ItemEditFactory;
import com.echothree.control.user.item.remote.edit.ItemWeightEdit;
import com.echothree.control.user.item.remote.form.EditItemWeightForm;
import com.echothree.control.user.item.remote.result.EditItemWeightResult;
import com.echothree.control.user.item.remote.result.ItemResultFactory;
import com.echothree.control.user.item.remote.spec.ItemWeightSpec;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemWeight;
import com.echothree.model.data.item.server.value.ItemWeightValue;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditItemWeightCommand
        extends BaseAbstractEditCommand<ItemWeightSpec, ItemWeightEdit, EditItemWeightResult, ItemWeight, Item> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WeightUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Weight", FieldType.UNSIGNED_LONG, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditItemWeightCommand */
    public EditItemWeightCommand(UserVisitPK userVisitPK, EditItemWeightForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditItemWeightResult getResult() {
        return ItemResultFactory.getEditItemWeightResult();
    }

    @Override
    public ItemWeightEdit getEdit() {
        return ItemEditFactory.getItemWeightEdit();
    }

    UnitOfMeasureKind volumeUnitOfMeasureKind;

    @Override
    public ItemWeight getEntity(EditItemWeightResult result) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
        ItemWeight itemWeight = null;
        String itemName = spec.getItemName();
        Item item = itemControl.getItemByName(itemName);

        if(item != null) {
            String unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
            UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(item.getLastDetail().getUnitOfMeasureKind(), unitOfMeasureTypeName);

            if(unitOfMeasureType != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    itemWeight = itemControl.getItemWeight(item, unitOfMeasureType);
                } else { // EditMode.UPDATE
                    itemWeight = itemControl.getItemWeightForUpdate(item, unitOfMeasureType);
                }

                if(itemWeight == null) {
                    addExecutionError(ExecutionErrors.UnknownItemWeight.name(), itemName, unitOfMeasureTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }

        if(!hasExecutionErrors() && (editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.UPDATE))) {
            volumeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_WEIGHT);

            if(volumeUnitOfMeasureKind == null) {
                addExecutionError(ExecutionErrors.UnknownWeightUnitOfMeasureKind.name());
            }
        }

        return itemWeight;
    }

    @Override
    public Item getLockEntity(ItemWeight itemWeight) {
        return itemWeight.getItem();
    }

    @Override
    public void fillInResult(EditItemWeightResult result, ItemWeight itemWeight) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);

        result.setItemWeight(itemControl.getItemWeightTransfer(getUserVisit(), itemWeight));
    }

    @Override
    public void doLock(ItemWeightEdit edit, ItemWeight itemWeight) {
        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);

        weight = itemWeight.getWeight();
        Conversion weightConversion = weight == null ? null : new Conversion(uomControl, volumeUnitOfMeasureKind, weight).convertToHighestUnitOfMeasureType();

        edit.setWeight(weightConversion.getQuantity().toString());
        edit.setWeightUnitOfMeasureTypeName(weightConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
    }

    UnitOfMeasureType weightUnitOfMeasureType;
    Long weight;

    @Override
    public void canUpdate(ItemWeight itemWeight) {
        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
        String weightUnitOfMeasureTypeName = edit.getWeightUnitOfMeasureTypeName();

        weightUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind, weightUnitOfMeasureTypeName);

        if(weightUnitOfMeasureType != null) {
            weight = Long.valueOf(edit.getWeight());

            if(weight < 1) {
                addExecutionError(ExecutionErrors.InvalidWeight.name(), weight);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWeightUnitOfMeasureTypeName.name(), weightUnitOfMeasureTypeName);
        }
    }

    @Override
    public void doUpdate(ItemWeight itemWeight) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
        ItemWeightValue itemWeightValue = itemControl.getItemWeightValue(itemWeight);

        Conversion weightConversion = new Conversion(uomControl, weightUnitOfMeasureType, weight).convertToLowestUnitOfMeasureType();

        itemWeightValue.setWeight(weightConversion.getQuantity());

        itemControl.updateItemWeightFromValue(itemWeightValue, getPartyPK());
    }
    
}
