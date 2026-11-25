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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.GenderEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.result.EditGenderResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.GenderUniversalSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.GenderLogic;
import com.echothree.model.data.party.server.entity.Gender;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditGenderCommand
        extends BaseAbstractEditCommand<GenderUniversalSpec, GenderEdit, EditGenderResult, Gender, Gender> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GenderName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GenderName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    PartyControl partyControl;

    @Inject
    GenderLogic genderLogic;

    /** Creates a new instance of EditGenderCommand */
    public EditGenderCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGenderResult getResult() {
        return PartyResultFactory.getEditGenderResult();
    }

    @Override
    public GenderEdit getEdit() {
        return PartyEditFactory.getGenderEdit();
    }

    @Override
    public Gender getEntity(EditGenderResult result) {
        return genderLogic.getGenderByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public Gender getLockEntity(Gender gender) {
        return gender;
    }

    @Override
    public void fillInResult(EditGenderResult result, Gender gender) {
        result.setGender(partyControl.getGenderTransfer(getUserVisit(), gender));
    }

    @Override
    public void doLock(GenderEdit edit, Gender gender) {
        var genderDescription = partyControl.getGenderDescription(gender, getPreferredLanguage());
        var genderDetail = gender.getLastDetail();

        edit.setGenderName(genderDetail.getGenderName());
        edit.setIsDefault(genderDetail.getIsDefault().toString());
        edit.setSortOrder(genderDetail.getSortOrder().toString());

        if(genderDescription != null) {
            edit.setDescription(genderDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Gender gender) {
        var genderName = edit.getGenderName();
        var duplicateGender = partyControl.getGenderByName(genderName);

        if(duplicateGender != null && !gender.equals(duplicateGender)) {
            addExecutionError(ExecutionErrors.DuplicateGenderName.name(), genderName);
        }
    }

    @Override
    public void doUpdate(Gender gender) {
        var partyPK = getPartyPK();
        var genderDetailValue = partyControl.getGenderDetailValueForUpdate(gender);
        var genderDescription = partyControl.getGenderDescriptionForUpdate(gender, getPreferredLanguage());
        var description = edit.getDescription();

        genderDetailValue.setGenderName(edit.getGenderName());
        genderDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        genderDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        partyControl.updateGenderFromValue(genderDetailValue, partyPK);

        if(genderDescription == null && description != null) {
            partyControl.createGenderDescription(gender, getPreferredLanguage(), description, partyPK);
        } else if(genderDescription != null && description == null) {
            partyControl.deleteGenderDescription(genderDescription, partyPK);
        } else if(genderDescription != null && description != null) {
            var genderDescriptionValue = partyControl.getGenderDescriptionValue(genderDescription);

            genderDescriptionValue.setDescription(description);

            partyControl.updateGenderDescriptionFromValue(genderDescriptionValue, partyPK);
        }
    }

}
