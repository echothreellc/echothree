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
import com.echothree.control.user.item.common.edit.ItemVolumeEdit;
import com.echothree.control.user.item.common.result.EditItemVolumeResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.ItemVolumeSpec;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemVolumeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemVolume;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class EditItemVolumeCommand
        extends BaseAbstractEditCommand<ItemVolumeSpec, ItemVolumeEdit, EditItemVolumeResult, ItemVolume, Item> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemVolume.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ItemVolumeTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("HeightUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Height", FieldType.UNSIGNED_LONG, true, null, null),
                new FieldDefinition("WidthUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Width", FieldType.UNSIGNED_LONG, true, null, null),
                new FieldDefinition("DepthUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Depth", FieldType.UNSIGNED_LONG, true, null, null)
        );
    }
    
    /** Creates a new instance of EditItemVolumeCommand */
    public EditItemVolumeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditItemVolumeResult getResult() {
        return ItemResultFactory.getEditItemVolumeResult();
    }

    @Override
    public ItemVolumeEdit getEdit() {
        return ItemEditFactory.getItemVolumeEdit();
    }

    UnitOfMeasureKind volumeUnitOfMeasureKind;

    @Override
    public ItemVolume getEntity(EditItemVolumeResult result) {
        var itemControl = Session.getModelController(ItemControl.class);
        var uomControl = Session.getModelController(UomControl.class);
        ItemVolume itemVolume = null;
        var itemName = spec.getItemName();
        var item = itemControl.getItemByName(itemName);

        if(item != null) {
            var unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(item.getLastDetail().getUnitOfMeasureKind(), unitOfMeasureTypeName);

            if(unitOfMeasureType != null) {
                var itemVolumeType = ItemVolumeTypeLogic.getInstance().getItemVolumeTypeByName(this, spec.getItemVolumeTypeName());

                if(!hasExecutionErrors()) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        itemVolume = itemControl.getItemVolume(item, unitOfMeasureType, itemVolumeType);
                    } else { // EditMode.UPDATE
                        itemVolume = itemControl.getItemVolumeForUpdate(item, unitOfMeasureType, itemVolumeType);
                    }
    
                    if(itemVolume == null) {
                        addExecutionError(ExecutionErrors.UnknownItemVolume.name(), itemName, unitOfMeasureTypeName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
        }

        if(!hasExecutionErrors() && (editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.UPDATE))) {
            volumeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_VOLUME);

            if(volumeUnitOfMeasureKind == null) {
                addExecutionError(ExecutionErrors.UnknownVolumeUnitOfMeasureKind.name());
            }
        }

        return itemVolume;
    }

    @Override
    public Item getLockEntity(ItemVolume itemVolume) {
        return itemVolume.getItem();
    }

    @Override
    public void fillInResult(EditItemVolumeResult result, ItemVolume itemVolume) {
        var itemControl = Session.getModelController(ItemControl.class);

        result.setItemVolume(itemControl.getItemVolumeTransfer(getUserVisit(), itemVolume));
    }

    @Override
    public void doLock(ItemVolumeEdit edit, ItemVolume itemVolume) {
        var uomControl = Session.getModelController(UomControl.class);

        height = itemVolume.getHeight();
        var heightConversion = height == null ? null : new Conversion(uomControl, volumeUnitOfMeasureKind, height).convertToHighestUnitOfMeasureType();

        width = itemVolume.getWidth();
        var widthConversion = width == null ? null : new Conversion(uomControl, volumeUnitOfMeasureKind, width).convertToHighestUnitOfMeasureType();

        depth = itemVolume.getDepth();
        var depthConversion = depth == null ? null : new Conversion(uomControl, volumeUnitOfMeasureKind, depth).convertToHighestUnitOfMeasureType();

        edit.setHeight(heightConversion.getQuantity().toString());
        edit.setHeightUnitOfMeasureTypeName(heightConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        edit.setWidth(widthConversion.getQuantity().toString());
        edit.setWidthUnitOfMeasureTypeName(widthConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        edit.setDepth(depthConversion.getQuantity().toString());
        edit.setDepthUnitOfMeasureTypeName(depthConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
    }

    UnitOfMeasureType heightUnitOfMeasureType;
    Long height;
    UnitOfMeasureType widthUnitOfMeasureType;
    Long width;
    UnitOfMeasureType depthUnitOfMeasureType;
    Long depth;

    @Override
    public void canUpdate(ItemVolume itemVolume) {
        var uomControl = Session.getModelController(UomControl.class);
        var heightUnitOfMeasureTypeName = edit.getHeightUnitOfMeasureTypeName();

        heightUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind, heightUnitOfMeasureTypeName);

        if(heightUnitOfMeasureType != null) {
            height = Long.valueOf(edit.getHeight());

            if(height > 0) {
                var widthUnitOfMeasureTypeName = edit.getWidthUnitOfMeasureTypeName();

                widthUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind, widthUnitOfMeasureTypeName);

                if(widthUnitOfMeasureType != null) {
                    width = Long.valueOf(edit.getWidth());

                    if(width > 0) {
                        var depthUnitOfMeasureTypeName = edit.getDepthUnitOfMeasureTypeName();

                        depthUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind, depthUnitOfMeasureTypeName);

                        if(depthUnitOfMeasureType != null) {
                            depth = Long.valueOf(edit.getDepth());

                            if(depth < 1) {
                                addExecutionError(ExecutionErrors.InvalidDepth.name(), depth);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownDepthUnitOfMeasureTypeName.name(), depthUnitOfMeasureTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidWidth.name(), width);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWidthUnitOfMeasureTypeName.name(), widthUnitOfMeasureTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidHeight.name(), height);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownHeightUnitOfMeasureTypeName.name(), heightUnitOfMeasureTypeName);
        }
    }

    @Override
    public void doUpdate(ItemVolume itemVolume) {
        var itemControl = Session.getModelController(ItemControl.class);
        var uomControl = Session.getModelController(UomControl.class);
        var itemVolumeValue = itemControl.getItemVolumeValue(itemVolume);

        var heightConversion = new Conversion(uomControl, heightUnitOfMeasureType, height).convertToLowestUnitOfMeasureType();
        var widthConversion = new Conversion(uomControl, widthUnitOfMeasureType, width).convertToLowestUnitOfMeasureType();
        var depthConversion = new Conversion(uomControl, depthUnitOfMeasureType, depth).convertToLowestUnitOfMeasureType();

        itemVolumeValue.setHeight(heightConversion.getQuantity());
        itemVolumeValue.setWidth(widthConversion.getQuantity());
        itemVolumeValue.setDepth(depthConversion.getQuantity());

        itemControl.updateItemVolumeFromValue(itemVolumeValue, getPartyPK());
    }
    
}
