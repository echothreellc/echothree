// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.control.user.uom.server.command;

import com.echothree.control.user.uom.common.edit.UnitOfMeasureTypeVolumeEdit;
import com.echothree.control.user.uom.common.edit.UomEditFactory;
import com.echothree.control.user.uom.common.result.EditUnitOfMeasureTypeVolumeResult;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.control.user.uom.common.spec.UnitOfMeasureTypeSpec;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeVolume;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditUnitOfMeasureTypeVolumeCommand
        extends BaseAbstractEditCommand<UnitOfMeasureTypeSpec, UnitOfMeasureTypeVolumeEdit, EditUnitOfMeasureTypeVolumeResult, UnitOfMeasureTypeVolume, UnitOfMeasureType> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
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
    
    /** Creates a new instance of EditUnitOfMeasureTypeVolumeCommand */
    public EditUnitOfMeasureTypeVolumeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Inject
    UomControl uomControl;

    @Override
    protected EditUnitOfMeasureTypeVolumeResult getResult() {
        return UomResultFactory.getEditUnitOfMeasureTypeVolumeResult();
    }

    @Override
    protected UnitOfMeasureTypeVolumeEdit getEdit() {
        return UomEditFactory.getUnitOfMeasureTypeVolumeEdit();
    }

    @Override
    protected UnitOfMeasureTypeVolume getEntity(EditUnitOfMeasureTypeVolumeResult result) {
        UnitOfMeasureTypeVolume unitOfMeasureTypeVolume = null;
        var unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
        var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);

        if(unitOfMeasureKind != null) {
            var unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);

            if(unitOfMeasureType != null) {
                unitOfMeasureTypeVolume = uomControl.getUnitOfMeasureTypeVolume(unitOfMeasureType);

                if(unitOfMeasureTypeVolume == null) {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeVolume.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }

        return unitOfMeasureTypeVolume;
    }

    @Override
    protected UnitOfMeasureType getLockEntity(UnitOfMeasureTypeVolume unitOfMeasureTypeVolume) {
        return unitOfMeasureTypeVolume.getUnitOfMeasureType();
    }

    @Override
    protected void fillInResult(EditUnitOfMeasureTypeVolumeResult result, UnitOfMeasureTypeVolume unitOfMeasureTypeVolume) {
        result.setUnitOfMeasureTypeVolume(uomControl.getUnitOfMeasureTypeVolumeTransfer(getUserVisit(), unitOfMeasureTypeVolume));
    }

    UnitOfMeasureKind volumeUnitOfMeasureKind;

    private UnitOfMeasureKind getVolumeUnitOfMeasureKind() {
        if(volumeUnitOfMeasureKind == null) {
            volumeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_VOLUME);
        }

        return volumeUnitOfMeasureKind;
    }

    @Override
    protected void doLock(UnitOfMeasureTypeVolumeEdit edit, UnitOfMeasureTypeVolume unitOfMeasureTypeVolume) {
        var volumeUnitOfMeasureKind = getVolumeUnitOfMeasureKind();
        var height = unitOfMeasureTypeVolume.getHeight();
        var heightConversion = height == null? null: new Conversion(uomControl, volumeUnitOfMeasureKind, height).convertToHighestUnitOfMeasureType();
        var width = unitOfMeasureTypeVolume.getWidth();
        var widthConversion = width == null? null: new Conversion(uomControl, volumeUnitOfMeasureKind, width).convertToHighestUnitOfMeasureType();
        var depth = unitOfMeasureTypeVolume.getDepth();
        var depthConversion = depth == null? null: new Conversion(uomControl, volumeUnitOfMeasureKind, depth).convertToHighestUnitOfMeasureType();

        edit.setHeight(heightConversion.getQuantity().toString());
        edit.setHeightUnitOfMeasureTypeName(heightConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        edit.setWidth(widthConversion.getQuantity().toString());
        edit.setWidthUnitOfMeasureTypeName(widthConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        edit.setDepth(depthConversion.getQuantity().toString());
        edit.setDepthUnitOfMeasureTypeName(depthConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
    }

    Long height;
    Long width;
    Long depth;

    @Override
    protected void canUpdate(UnitOfMeasureTypeVolume unitOfMeasureTypeVolume) {
        var volumeUnitOfMeasureKind = getVolumeUnitOfMeasureKind();

        if(volumeUnitOfMeasureKind != null) {
            var heightUnitOfMeasureTypeName = edit.getHeightUnitOfMeasureTypeName();
            var heightUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind, heightUnitOfMeasureTypeName);

            if(heightUnitOfMeasureType != null) {
                var heightValue = Long.valueOf(edit.getHeight());

                if(heightValue > 0) {
                    var widthUnitOfMeasureTypeName = edit.getWidthUnitOfMeasureTypeName();
                    var widthUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind, widthUnitOfMeasureTypeName);

                    if(widthUnitOfMeasureType != null) {
                        var widthValue = Long.valueOf(edit.getWidth());

                        if(widthValue > 0) {
                            var depthUnitOfMeasureTypeName = edit.getDepthUnitOfMeasureTypeName();
                            var depthUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind, depthUnitOfMeasureTypeName);

                            if(depthUnitOfMeasureType != null) {
                                var depthValue = Long.valueOf(edit.getDepth());

                                if(depthValue > 0) {
                                    height = new Conversion(uomControl, heightUnitOfMeasureType, heightValue).convertToLowestUnitOfMeasureType().getQuantity();
                                    width = new Conversion(uomControl, widthUnitOfMeasureType, widthValue).convertToLowestUnitOfMeasureType().getQuantity();
                                    depth = new Conversion(uomControl, depthUnitOfMeasureType, depthValue).convertToLowestUnitOfMeasureType().getQuantity();
                                } else {
                                    addExecutionError(ExecutionErrors.InvalidDepth.name(), depthValue);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownDepthUnitOfMeasureTypeName.name(), depthUnitOfMeasureTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.InvalidWidth.name(), widthValue);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownWidthUnitOfMeasureTypeName.name(), widthUnitOfMeasureTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidHeight.name(), heightValue);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownHeightUnitOfMeasureTypeName.name(), heightUnitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownVolumeUnitOfMeasureKind.name());
        }
    }

    @Override
    protected void doUpdate(UnitOfMeasureTypeVolume unitOfMeasureTypeVolume) {
        var unitOfMeasureTypeVolumeValue = uomControl.getUnitOfMeasureTypeVolumeValue(unitOfMeasureTypeVolume);

        unitOfMeasureTypeVolumeValue.setHeight(height);
        unitOfMeasureTypeVolumeValue.setWidth(width);
        unitOfMeasureTypeVolumeValue.setDepth(depth);

        uomControl.updateUnitOfMeasureTypeVolumeFromValue(unitOfMeasureTypeVolumeValue, getPartyPK());
    }
    
}
