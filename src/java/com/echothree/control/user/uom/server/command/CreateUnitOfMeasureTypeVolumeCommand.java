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

package com.echothree.control.user.uom.server.command;

import com.echothree.control.user.uom.common.form.CreateUnitOfMeasureTypeVolumeForm;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeVolume;
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

public class CreateUnitOfMeasureTypeVolumeCommand
        extends BaseSimpleCommand<CreateUnitOfMeasureTypeVolumeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("HeightUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Height", FieldType.UNSIGNED_LONG, true, null, null),
                new FieldDefinition("WidthUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Width", FieldType.UNSIGNED_LONG, true, null, null),
                new FieldDefinition("DepthUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Depth", FieldType.UNSIGNED_LONG, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateUnitOfMeasureTypeVolumeCommand */
    public CreateUnitOfMeasureTypeVolumeCommand(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeVolumeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
        String unitOfMeasureKindName = form.getUnitOfMeasureKindName();
        UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
        
        if(unitOfMeasureKind != null) {
            UnitOfMeasureKindUseType unitOfMeasureKindUseType = uomControl.getUnitOfMeasureKindUseTypeByName(UomConstants.UnitOfMeasureKindUseType_QUANTITY);
            UnitOfMeasureKindUse unitOfMeasureKindUse = uomControl.getUnitOfMeasureKindUse(unitOfMeasureKindUseType, unitOfMeasureKind);
            
            if(unitOfMeasureKindUse != null) {
                String unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                
                if(unitOfMeasureType != null) {
                    UnitOfMeasureTypeVolume unitOfMeasureTypeVolume = uomControl.getUnitOfMeasureTypeVolume(unitOfMeasureType);
                    
                    if(unitOfMeasureTypeVolume == null) {
                        UnitOfMeasureKind volumeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_VOLUME);
                        
                        if(volumeUnitOfMeasureKind != null) {
                            String heightUnitOfMeasureTypeName = form.getHeightUnitOfMeasureTypeName();
                            UnitOfMeasureType heightUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind,
                                    heightUnitOfMeasureTypeName);
                            
                            if(heightUnitOfMeasureType != null) {
                                Long height = Long.valueOf(form.getHeight());
                                
                                if(height > 0) {
                                    String widthUnitOfMeasureTypeName = form.getWidthUnitOfMeasureTypeName();
                                    UnitOfMeasureType widthUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind,
                                            widthUnitOfMeasureTypeName);
                                    
                                    if(widthUnitOfMeasureType != null) {
                                        Long width = Long.valueOf(form.getWidth());
                                        
                                        if(width > 0) {
                                            String depthUnitOfMeasureTypeName = form.getDepthUnitOfMeasureTypeName();
                                            UnitOfMeasureType depthUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind,
                                                    depthUnitOfMeasureTypeName);
                                            
                                            if(depthUnitOfMeasureType != null) {
                                                Long depth = Long.valueOf(form.getDepth());
                                                
                                                if(depth > 0) {
                                                    Conversion heightConversion = new Conversion(uomControl, heightUnitOfMeasureType, height).convertToLowestUnitOfMeasureType();
                                                    Conversion widthConversion = new Conversion(uomControl, widthUnitOfMeasureType, width).convertToLowestUnitOfMeasureType();
                                                    Conversion depthConversion = new Conversion(uomControl, depthUnitOfMeasureType, depth).convertToLowestUnitOfMeasureType();
                                                    
                                                    uomControl.createUnitOfMeasureTypeVolume(unitOfMeasureType, heightConversion.getQuantity(),
                                                            widthConversion.getQuantity(), depthConversion.getQuantity(), getPartyPK());
                                                } else {
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
                        } else {
                            addExecutionError(ExecutionErrors.UnknownVolumeUnitOfMeasureKind.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateUnitOfMeasureTypeVolume.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.InvalidUnitOfMeasureKind.name());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }
        
        return null;
    }
    
}
