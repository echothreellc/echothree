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

import com.echothree.control.user.uom.common.edit.UnitOfMeasureEquivalentEdit;
import com.echothree.control.user.uom.common.edit.UomEditFactory;
import com.echothree.control.user.uom.common.result.EditUnitOfMeasureEquivalentResult;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.control.user.uom.common.spec.UnitOfMeasureEquivalentSpec;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureEquivalent;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditUnitOfMeasureEquivalentCommand
        extends BaseAbstractEditCommand<UnitOfMeasureEquivalentSpec, UnitOfMeasureEquivalentEdit, EditUnitOfMeasureEquivalentResult, UnitOfMeasureEquivalent, UnitOfMeasureKind> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FromUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ToUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ToQuantity", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }

    @Inject
    UomControl uomControl;

    /** Creates a new instance of EditUnitOfMeasureEquivalentCommand */
    public EditUnitOfMeasureEquivalentCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditUnitOfMeasureEquivalentResult getResult() {
        return UomResultFactory.getEditUnitOfMeasureEquivalentResult();
    }

    @Override
    public UnitOfMeasureEquivalentEdit getEdit() {
        return UomEditFactory.getUnitOfMeasureEquivalentEdit();
    }

    UnitOfMeasureKind unitOfMeasureKind;

    @Override
    public UnitOfMeasureEquivalent getEntity(EditUnitOfMeasureEquivalentResult result) {
        var unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
        UnitOfMeasureEquivalent unitOfMeasureEquivalent = null;

        unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);

        if(unitOfMeasureKind != null) {
            var fromUnitOfMeasureTypeName = spec.getFromUnitOfMeasureTypeName();
            var fromUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, fromUnitOfMeasureTypeName);

            if(fromUnitOfMeasureType != null) {
                var toUnitOfMeasureTypeName = spec.getToUnitOfMeasureTypeName();
                var toUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, toUnitOfMeasureTypeName);

                if(toUnitOfMeasureType != null) {
                    unitOfMeasureEquivalent = uomControl.getUnitOfMeasureEquivalent(fromUnitOfMeasureType, toUnitOfMeasureType, editModeToEntityPermission(editMode));

                    if(unitOfMeasureEquivalent == null) {
                        addExecutionError(ExecutionErrors.UnknownUnitOfMeasureEquivalent.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownToUnitOfMeasureTypeName.name(), toUnitOfMeasureTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownFromUnitOfMeasureTypeName.name(), fromUnitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }

        return unitOfMeasureEquivalent;
    }

    @Override
    public UnitOfMeasureKind getLockEntity(UnitOfMeasureEquivalent unitOfMeasureEquivalent) {
        return unitOfMeasureKind;
    }

    @Override
    public void fillInResult(EditUnitOfMeasureEquivalentResult result, UnitOfMeasureEquivalent unitOfMeasureEquivalent) {
        result.setUnitOfMeasureEquivalent(uomControl.getUnitOfMeasureEquivalentTransfer(getUserVisit(), unitOfMeasureEquivalent));
    }

    @Override
    public void doLock(UnitOfMeasureEquivalentEdit edit, UnitOfMeasureEquivalent unitOfMeasureEquivalent) {
        edit.setToQuantity(unitOfMeasureEquivalent.getToQuantity().toString());
    }

    @Override
    public void doUpdate(UnitOfMeasureEquivalent unitOfMeasureEquivalent) {
        var unitOfMeasureEquivalentValue = uomControl.getUnitOfMeasureEquivalentValue(unitOfMeasureEquivalent);

        unitOfMeasureEquivalentValue.setToQuantity(Long.valueOf(edit.getToQuantity()));

        uomControl.updateUnitOfMeasureEquivalentFromValue(unitOfMeasureEquivalentValue, getPartyPK());
    }

}
