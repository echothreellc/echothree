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

import com.echothree.control.user.uom.common.edit.UnitOfMeasureTypeDescriptionEdit;
import com.echothree.control.user.uom.common.edit.UomEditFactory;
import com.echothree.control.user.uom.common.result.EditUnitOfMeasureTypeDescriptionResult;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.control.user.uom.common.spec.UnitOfMeasureTypeDescriptionSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditUnitOfMeasureTypeDescriptionCommand
        extends BaseAbstractEditCommand<UnitOfMeasureTypeDescriptionSpec, UnitOfMeasureTypeDescriptionEdit, EditUnitOfMeasureTypeDescriptionResult, UnitOfMeasureTypeDescription, UnitOfMeasureType> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SingularDescription", FieldType.STRING, true, 1L, 132L),
                new FieldDefinition("PluralDescription", FieldType.STRING, true, 1L, 132L),
                new FieldDefinition("Symbol", FieldType.STRING, true, 1L, 20L)
        );
    }

    @Inject
    PartyControl partyControl;

    @Inject
    UomControl uomControl;

    /** Creates a new instance of EditUnitOfMeasureTypeDescriptionCommand */
    public EditUnitOfMeasureTypeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditUnitOfMeasureTypeDescriptionResult getResult() {
        return UomResultFactory.getEditUnitOfMeasureTypeDescriptionResult();
    }

    @Override
    public UnitOfMeasureTypeDescriptionEdit getEdit() {
        return UomEditFactory.getUnitOfMeasureTypeDescriptionEdit();
    }

    @Override
    public UnitOfMeasureTypeDescription getEntity(EditUnitOfMeasureTypeDescriptionResult result) {
        UnitOfMeasureTypeDescription unitOfMeasureTypeDescription = null;
        var unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
        var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);

        if(unitOfMeasureKind != null) {
            var unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);

            if(unitOfMeasureType != null) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    unitOfMeasureTypeDescription = uomControl.getUnitOfMeasureTypeDescription(unitOfMeasureType, language, editModeToEntityPermission(editMode));

                    if(unitOfMeasureTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeDescription.name(), unitOfMeasureKindName, unitOfMeasureTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureKindName, unitOfMeasureTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }

        return unitOfMeasureTypeDescription;
    }

    @Override
    public UnitOfMeasureType getLockEntity(UnitOfMeasureTypeDescription unitOfMeasureTypeDescription) {
        return unitOfMeasureTypeDescription.getUnitOfMeasureType();
    }

    @Override
    public void fillInResult(EditUnitOfMeasureTypeDescriptionResult result, UnitOfMeasureTypeDescription unitOfMeasureTypeDescription) {
        result.setUnitOfMeasureTypeDescription(uomControl.getUnitOfMeasureTypeDescriptionTransfer(getUserVisit(), unitOfMeasureTypeDescription));
    }

    @Override
    public void doLock(UnitOfMeasureTypeDescriptionEdit edit, UnitOfMeasureTypeDescription unitOfMeasureTypeDescription) {
        edit.setSingularDescription(unitOfMeasureTypeDescription.getSingularDescription());
        edit.setPluralDescription(unitOfMeasureTypeDescription.getPluralDescription());
        edit.setSymbol(unitOfMeasureTypeDescription.getSymbol());
    }

    @Override
    public void doUpdate(UnitOfMeasureTypeDescription unitOfMeasureTypeDescription) {
        var unitOfMeasureTypeDescriptionValue = uomControl.getUnitOfMeasureTypeDescriptionValue(unitOfMeasureTypeDescription);

        unitOfMeasureTypeDescriptionValue.setSingularDescription(edit.getSingularDescription());
        unitOfMeasureTypeDescriptionValue.setPluralDescription(edit.getPluralDescription());
        unitOfMeasureTypeDescriptionValue.setSymbol(edit.getSymbol());

        uomControl.updateUnitOfMeasureTypeDescriptionFromValue(unitOfMeasureTypeDescriptionValue, getPartyPK());
    }
    
}
