// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.control.user.uom.common.edit.UnitOfMeasureTypeWeightEdit;
import com.echothree.control.user.uom.common.edit.UomEditFactory;
import com.echothree.control.user.uom.common.form.EditUnitOfMeasureTypeWeightForm;
import com.echothree.control.user.uom.common.result.EditUnitOfMeasureTypeWeightResult;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.control.user.uom.common.spec.UnitOfMeasureTypeSpec;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeWeight;
import com.echothree.model.data.uom.server.value.UnitOfMeasureTypeWeightValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditUnitOfMeasureTypeWeightCommand
        extends BaseEditCommand<UnitOfMeasureTypeSpec, UnitOfMeasureTypeWeightEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("WeightUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Weight", FieldType.UNSIGNED_LONG, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditUnitOfMeasureTypeWeightCommand */
    public EditUnitOfMeasureTypeWeightCommand(UserVisitPK userVisitPK, EditUnitOfMeasureTypeWeightForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var uomControl = Session.getModelController(UomControl.class);
        EditUnitOfMeasureTypeWeightResult result = UomResultFactory.getEditUnitOfMeasureTypeWeightResult();
        String unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
        UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
        
        if(unitOfMeasureKind != null) {
            String unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
            UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
            
            if(unitOfMeasureType != null) {
                UnitOfMeasureKind volumeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_WEIGHT);
                
                if(volumeUnitOfMeasureKind != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        UnitOfMeasureTypeWeight unitOfMeasureTypeWeight = uomControl.getUnitOfMeasureTypeWeight(unitOfMeasureType);
                        
                        if(unitOfMeasureTypeWeight != null) {
                            result.setUnitOfMeasureTypeWeight(uomControl.getUnitOfMeasureTypeWeightTransfer(getUserVisit(), unitOfMeasureTypeWeight));
                            
                            if(lockEntity(unitOfMeasureType)) {
                                UnitOfMeasureTypeWeightEdit edit = UomEditFactory.getUnitOfMeasureTypeWeightEdit();
                                Long weight = unitOfMeasureTypeWeight.getWeight();
                                Conversion weightConversion = weight == null? null: new Conversion(uomControl, volumeUnitOfMeasureKind, weight).convertToHighestUnitOfMeasureType();
                                
                                result.setEdit(edit);
                                edit.setWeight(weightConversion.getQuantity().toString());
                                edit.setWeightUnitOfMeasureTypeName(weightConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(unitOfMeasureType));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeWeight.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        UnitOfMeasureTypeWeightValue unitOfMeasureTypeWeightValue = uomControl.getUnitOfMeasureTypeWeightValueForUpdate(unitOfMeasureType);
                        
                        if(unitOfMeasureTypeWeightValue != null) {
                            String weightUnitOfMeasureTypeName = edit.getWeightUnitOfMeasureTypeName();
                            UnitOfMeasureType weightUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(volumeUnitOfMeasureKind,
                                    weightUnitOfMeasureTypeName);
                            
                            if(weightUnitOfMeasureType != null) {
                                Long weight = Long.valueOf(edit.getWeight());
                                
                                if(weight > 0) {
                                    if(lockEntityForUpdate(unitOfMeasureType)) {
                                        try {
                                            Conversion weightConversion = new Conversion(uomControl, weightUnitOfMeasureType, weight).convertToLowestUnitOfMeasureType();
                                            
                                            unitOfMeasureTypeWeightValue.setWeight(weightConversion.getQuantity());
                                            
                                            uomControl.updateUnitOfMeasureTypeWeightFromValue(unitOfMeasureTypeWeightValue, getPartyPK());
                                        } finally {
                                            unlockEntity(unitOfMeasureType);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.InvalidWeight.name(), weight);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownWeightUnitOfMeasureTypeName.name(), weightUnitOfMeasureTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeWeight.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWeightUnitOfMeasureKind.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }
        
        return result;
    }
    
}
