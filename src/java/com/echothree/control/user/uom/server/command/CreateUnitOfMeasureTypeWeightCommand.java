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

import com.echothree.control.user.uom.common.form.CreateUnitOfMeasureTypeWeightForm;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeWeight;
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

public class CreateUnitOfMeasureTypeWeightCommand
        extends BaseSimpleCommand<CreateUnitOfMeasureTypeWeightForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WeightUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Weight", FieldType.UNSIGNED_LONG, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateUnitOfMeasureTypeWeightCommand */
    public CreateUnitOfMeasureTypeWeightCommand(UserVisitPK userVisitPK, CreateUnitOfMeasureTypeWeightForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var uomControl = Session.getModelController(UomControl.class);
        String unitOfMeasureKindName = form.getUnitOfMeasureKindName();
        UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);
        
        if(unitOfMeasureKind != null) {
            UnitOfMeasureKindUseType unitOfMeasureKindUseType = uomControl.getUnitOfMeasureKindUseTypeByName(UomConstants.UnitOfMeasureKindUseType_QUANTITY);
            UnitOfMeasureKindUse unitOfMeasureKindUse = uomControl.getUnitOfMeasureKindUse(unitOfMeasureKindUseType, unitOfMeasureKind);
            
            if(unitOfMeasureKindUse != null) {
                String unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                UnitOfMeasureType unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                
                if(unitOfMeasureType != null) {
                    UnitOfMeasureTypeWeight unitOfMeasureTypeWeight = uomControl.getUnitOfMeasureTypeWeight(unitOfMeasureType);
                    
                    if(unitOfMeasureTypeWeight == null) {
                        UnitOfMeasureKind weightUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_WEIGHT);
                        
                        if(weightUnitOfMeasureKind != null) {
                            String weightUnitOfMeasureTypeName = form.getWeightUnitOfMeasureTypeName();
                            UnitOfMeasureType weightUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(weightUnitOfMeasureKind,
                                    weightUnitOfMeasureTypeName);
                            
                            if(weightUnitOfMeasureType != null) {
                                Long weight = Long.valueOf(form.getWeight());
                                
                                if(weight > 0) {
                                    Conversion weightConversion = new Conversion(uomControl, weightUnitOfMeasureType, weight).convertToLowestUnitOfMeasureType();
                                    
                                    uomControl.createUnitOfMeasureTypeWeight(unitOfMeasureType, weightConversion.getQuantity(),
                                            getPartyPK());
                                } else {
                                    addExecutionError(ExecutionErrors.InvalidWeight.name(), weight);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownWeightUnitOfMeasureTypeName.name(), weightUnitOfMeasureTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWeightUnitOfMeasureKind.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateUnitOfMeasureTypeWeight.name());
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
