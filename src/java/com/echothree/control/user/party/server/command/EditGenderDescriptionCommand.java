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

import com.echothree.control.user.party.common.edit.GenderDescriptionEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.result.EditGenderDescriptionResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.GenderDescriptionSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.GenderLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.party.server.entity.Gender;
import com.echothree.model.data.party.server.entity.GenderDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditGenderDescriptionCommand
        extends BaseAbstractEditCommand<GenderDescriptionSpec, GenderDescriptionEdit, EditGenderDescriptionResult, GenderDescription, Gender> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GenderName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    PartyControl partyControl;

    @Inject
    GenderLogic genderLogic;

    @Inject
    LanguageLogic languageLogic;

    /** Creates a new instance of EditGenderDescriptionCommand */
    public EditGenderDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGenderDescriptionResult getResult() {
        return PartyResultFactory.getEditGenderDescriptionResult();
    }

    @Override
    public GenderDescriptionEdit getEdit() {
        return PartyEditFactory.getGenderDescriptionEdit();
    }

    @Override
    public GenderDescription getEntity(EditGenderDescriptionResult result) {
        GenderDescription genderDescription = null;
        var genderName = spec.getGenderName();
        var languageIsoName = spec.getLanguageIsoName();
        var gender = genderLogic.getGenderByName(this, genderName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            genderDescription = partyControl.getGenderDescription(gender, language,
                    editModeToEntityPermission(editMode));

            if(genderDescription == null) {
                addExecutionError(ExecutionErrors.UnknownGenderDescription.name(), genderName, languageIsoName);
            }
        }

        return genderDescription;
    }

    @Override
    public Gender getLockEntity(GenderDescription genderDescription) {
        return genderDescription.getGender();
    }

    @Override
    public void fillInResult(EditGenderDescriptionResult result, GenderDescription genderDescription) {
        result.setGenderDescription(partyControl.getGenderDescriptionTransfer(getUserVisit(), genderDescription));
    }

    @Override
    public void doLock(GenderDescriptionEdit edit, GenderDescription genderDescription) {
        edit.setDescription(genderDescription.getDescription());
    }

    @Override
    public void doUpdate(GenderDescription genderDescription) {
        var genderDescriptionValue = partyControl.getGenderDescriptionValue(genderDescription);

        genderDescriptionValue.setDescription(edit.getDescription());

        partyControl.updateGenderDescriptionFromValue(genderDescriptionValue, getPartyPK());
    }

}
