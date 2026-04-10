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

import com.echothree.control.user.uom.common.edit.UnitOfMeasureKindEdit;
import com.echothree.control.user.uom.common.edit.UomEditFactory;
import com.echothree.control.user.uom.common.result.EditUnitOfMeasureKindResult;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.control.user.uom.common.spec.UnitOfMeasureKindSpec;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditUnitOfMeasureKindCommand
        extends BaseAbstractEditCommand<UnitOfMeasureKindSpec, UnitOfMeasureKindEdit, EditUnitOfMeasureKindResult, UnitOfMeasureKind, UnitOfMeasureKind> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FractionDigits", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    @Inject
    UomControl uomControl;
    
    /** Creates a new instance of EditUnitOfMeasureKindCommand */
    public EditUnitOfMeasureKindCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditUnitOfMeasureKindResult getResult() {
        return UomResultFactory.getEditUnitOfMeasureKindResult();
    }

    @Override
    public UnitOfMeasureKindEdit getEdit() {
        return UomEditFactory.getUnitOfMeasureKindEdit();
    }

    @Override
    public UnitOfMeasureKind getEntity(EditUnitOfMeasureKindResult result) {
        var unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
        var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName, editModeToEntityPermission(editMode));

        if(unitOfMeasureKind == null) {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }

        return unitOfMeasureKind;
    }

    @Override
    public UnitOfMeasureKind getLockEntity(UnitOfMeasureKind unitOfMeasureKind) {
        return unitOfMeasureKind;
    }

    @Override
    public void fillInResult(EditUnitOfMeasureKindResult result, UnitOfMeasureKind unitOfMeasureKind) {
        result.setUnitOfMeasureKind(uomControl.getUnitOfMeasureKindTransfer(getUserVisit(), unitOfMeasureKind));
    }

    @Override
    public void doLock(UnitOfMeasureKindEdit edit, UnitOfMeasureKind unitOfMeasureKind) {
        var unitOfMeasureKindDescription = uomControl.getUnitOfMeasureKindDescription(unitOfMeasureKind, getPreferredLanguage());
        var unitOfMeasureKindDetail = unitOfMeasureKind.getLastDetail();

        edit.setUnitOfMeasureKindName(unitOfMeasureKindDetail.getUnitOfMeasureKindName());
        edit.setFractionDigits(unitOfMeasureKindDetail.getFractionDigits().toString());
        edit.setIsDefault(unitOfMeasureKindDetail.getIsDefault().toString());
        edit.setSortOrder(unitOfMeasureKindDetail.getSortOrder().toString());

        if(unitOfMeasureKindDescription != null) {
            edit.setDescription(unitOfMeasureKindDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        var unitOfMeasureKindName = edit.getUnitOfMeasureKindName();
        var duplicateUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);

        if(duplicateUnitOfMeasureKind != null && !unitOfMeasureKind.equals(duplicateUnitOfMeasureKind)) {
            addExecutionError(ExecutionErrors.DuplicateUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }
    }

    @Override
    public void doUpdate(UnitOfMeasureKind unitOfMeasureKind) {
        var partyPK = getPartyPK();
        var unitOfMeasureKindDetailValue = uomControl.getUnitOfMeasureKindDetailValueForUpdate(unitOfMeasureKind);
        var unitOfMeasureKindDescription = uomControl.getUnitOfMeasureKindDescriptionForUpdate(unitOfMeasureKind, getPreferredLanguage());
        var description = edit.getDescription();

        unitOfMeasureKindDetailValue.setUnitOfMeasureKindName(edit.getUnitOfMeasureKindName());
        unitOfMeasureKindDetailValue.setFractionDigits(Integer.valueOf(edit.getFractionDigits()));
        unitOfMeasureKindDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        unitOfMeasureKindDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        uomControl.updateUnitOfMeasureKindFromValue(unitOfMeasureKindDetailValue, partyPK);

        if(unitOfMeasureKindDescription == null && description != null) {
            uomControl.createUnitOfMeasureKindDescription(unitOfMeasureKind, getPreferredLanguage(), description, partyPK);
        } else if(unitOfMeasureKindDescription != null && description == null) {
            uomControl.deleteUnitOfMeasureKindDescription(unitOfMeasureKindDescription, partyPK);
        } else if(unitOfMeasureKindDescription != null && description != null) {
            var unitOfMeasureKindDescriptionValue = uomControl.getUnitOfMeasureKindDescriptionValue(unitOfMeasureKindDescription);

            unitOfMeasureKindDescriptionValue.setDescription(description);
            uomControl.updateUnitOfMeasureKindDescriptionFromValue(unitOfMeasureKindDescriptionValue, partyPK);
        }
    }
    
}
