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

import com.echothree.control.user.uom.common.edit.UnitOfMeasureKindDescriptionEdit;
import com.echothree.control.user.uom.common.edit.UomEditFactory;
import com.echothree.control.user.uom.common.result.EditUnitOfMeasureKindDescriptionResult;
import com.echothree.control.user.uom.common.result.UomResultFactory;
import com.echothree.control.user.uom.common.spec.UnitOfMeasureKindDescriptionSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditUnitOfMeasureKindDescriptionCommand
        extends BaseAbstractEditCommand<UnitOfMeasureKindDescriptionSpec, UnitOfMeasureKindDescriptionEdit, EditUnitOfMeasureKindDescriptionResult, UnitOfMeasureKindDescription, UnitOfMeasureKind> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }
    
    @Inject
    UomControl uomControl;

    @Inject
    PartyControl partyControl;

    /** Creates a new instance of EditUnitOfMeasureKindDescriptionCommand */
    public EditUnitOfMeasureKindDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditUnitOfMeasureKindDescriptionResult getResult() {
        return UomResultFactory.getEditUnitOfMeasureKindDescriptionResult();
    }

    @Override
    public UnitOfMeasureKindDescriptionEdit getEdit() {
        return UomEditFactory.getUnitOfMeasureKindDescriptionEdit();
    }

    @Override
    public UnitOfMeasureKindDescription getEntity(EditUnitOfMeasureKindDescriptionResult result) {
        UnitOfMeasureKindDescription unitOfMeasureKindDescription = null;
        var unitOfMeasureKindName = spec.getUnitOfMeasureKindName();
        var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);

        if(unitOfMeasureKind != null) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                unitOfMeasureKindDescription = uomControl.getUnitOfMeasureKindDescription(unitOfMeasureKind, language, editModeToEntityPermission(editMode));

                if(unitOfMeasureKindDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindDescription.name(), unitOfMeasureKindName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }

        return unitOfMeasureKindDescription;
    }

    @Override
    public UnitOfMeasureKind getLockEntity(UnitOfMeasureKindDescription unitOfMeasureKindDescription) {
        return unitOfMeasureKindDescription.getUnitOfMeasureKind();
    }

    @Override
    public void fillInResult(EditUnitOfMeasureKindDescriptionResult result, UnitOfMeasureKindDescription unitOfMeasureKindDescription) {
        result.setUnitOfMeasureKindDescription(uomControl.getUnitOfMeasureKindDescriptionTransfer(getUserVisit(), unitOfMeasureKindDescription));
    }

    @Override
    public void doLock(UnitOfMeasureKindDescriptionEdit edit, UnitOfMeasureKindDescription unitOfMeasureKindDescription) {
        edit.setDescription(unitOfMeasureKindDescription.getDescription());
    }

    @Override
    public void doUpdate(UnitOfMeasureKindDescription unitOfMeasureKindDescription) {
        var unitOfMeasureKindDescriptionValue = uomControl.getUnitOfMeasureKindDescriptionValue(unitOfMeasureKindDescription);

        unitOfMeasureKindDescriptionValue.setDescription(edit.getDescription());

        uomControl.updateUnitOfMeasureKindDescriptionFromValue(unitOfMeasureKindDescriptionValue, getPartyPK());
    }
    
}
