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

import com.echothree.control.user.uom.common.edit.UnitOfMeasureTypeWeightEdit;
import com.echothree.control.user.uom.common.edit.UomEditFactory;
import com.echothree.control.user.uom.common.result.EditUnitOfMeasureTypeWeightResult;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.control.user.uom.common.spec.UnitOfMeasureTypeSpec;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeWeight;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditUnitOfMeasureTypeWeightCommand
        extends BaseAbstractEditCommand<UnitOfMeasureTypeSpec, UnitOfMeasureTypeWeightEdit, EditUnitOfMeasureTypeWeightResult, UnitOfMeasureTypeWeight, UnitOfMeasureType> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WeightUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Weight", FieldType.UNSIGNED_LONG, true, null, null)
        );
    }
    
    /** Creates a new instance of EditUnitOfMeasureTypeWeightCommand */
    public EditUnitOfMeasureTypeWeightCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Inject
    UomControl uomControl;

    @Override
    protected EditUnitOfMeasureTypeWeightResult getResult() {
        return UomResultFactory.getEditUnitOfMeasureTypeWeightResult();
    }

    @Override
    protected UnitOfMeasureTypeWeightEdit getEdit() {
        return UomEditFactory.getUnitOfMeasureTypeWeightEdit();
    }

    @Override
    protected UnitOfMeasureTypeWeight getEntity(EditUnitOfMeasureTypeWeightResult result) {
        UnitOfMeasureTypeWeight unitOfMeasureTypeWeight = null;
        var unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
        var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);

        if(unitOfMeasureKind != null) {
            var unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);

            if(unitOfMeasureType != null) {
                unitOfMeasureTypeWeight = uomControl.getUnitOfMeasureTypeWeight(unitOfMeasureType);

                if(unitOfMeasureTypeWeight == null) {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeWeight.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }

        return unitOfMeasureTypeWeight;
    }

    @Override
    protected UnitOfMeasureType getLockEntity(UnitOfMeasureTypeWeight unitOfMeasureTypeWeight) {
        return unitOfMeasureTypeWeight.getUnitOfMeasureType();
    }

    @Override
    protected void fillInResult(EditUnitOfMeasureTypeWeightResult result, UnitOfMeasureTypeWeight unitOfMeasureTypeWeight) {
        result.setUnitOfMeasureTypeWeight(uomControl.getUnitOfMeasureTypeWeightTransfer(getUserVisit(), unitOfMeasureTypeWeight));
    }

    UnitOfMeasureKind weightUnitOfMeasureKind;

    private UnitOfMeasureKind getWeightUnitOfMeasureKind() {
        if(weightUnitOfMeasureKind == null) {
            weightUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_WEIGHT);
        }

        return weightUnitOfMeasureKind;
    }

    @Override
    protected void doLock(UnitOfMeasureTypeWeightEdit edit, UnitOfMeasureTypeWeight unitOfMeasureTypeWeight) {
        var weightUnitOfMeasureKind = getWeightUnitOfMeasureKind();
        var weight = unitOfMeasureTypeWeight.getWeight();
        var weightConversion = weight == null? null: new Conversion(uomControl, weightUnitOfMeasureKind, weight).convertToHighestUnitOfMeasureType();

        if(weightConversion != null) {
            edit.setWeight(weightConversion.getQuantity().toString());
            edit.setWeightUnitOfMeasureTypeName(weightConversion.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureTypeName());
        }
    }

    Long weight;

    @Override
    protected void canUpdate(UnitOfMeasureTypeWeight unitOfMeasureTypeWeight) {
        var weightUnitOfMeasureKind = getWeightUnitOfMeasureKind();

        if(weightUnitOfMeasureKind != null) {
            var weightUnitOfMeasureTypeName = edit.getWeightUnitOfMeasureTypeName();
            var weightUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(weightUnitOfMeasureKind, weightUnitOfMeasureTypeName);

            if(weightUnitOfMeasureType != null) {
                var weightValue = Long.valueOf(edit.getWeight());

                if(weightValue > 0) {
                    weight = new Conversion(uomControl, weightUnitOfMeasureType, weightValue).convertToLowestUnitOfMeasureType().getQuantity();
                } else {
                    addExecutionError(ExecutionErrors.InvalidWeight.name(), weightValue);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWeightUnitOfMeasureTypeName.name(), weightUnitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWeightUnitOfMeasureKind.name());
        }
    }

    @Override
    protected void doUpdate(UnitOfMeasureTypeWeight unitOfMeasureTypeWeight) {
        var unitOfMeasureTypeWeightValue = uomControl.getUnitOfMeasureTypeWeightValue(unitOfMeasureTypeWeight);

        unitOfMeasureTypeWeightValue.setWeight(weight);

        uomControl.updateUnitOfMeasureTypeWeightFromValue(unitOfMeasureTypeWeightValue, getPartyPK());
    }
    
}
