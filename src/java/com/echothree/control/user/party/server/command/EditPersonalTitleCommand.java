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

import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.edit.PersonalTitleEdit;
import com.echothree.control.user.party.common.result.EditPersonalTitleResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.PersonalTitleUniversalSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PersonalTitleLogic;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditPersonalTitleCommand
        extends BaseAbstractEditCommand<PersonalTitleUniversalSpec, PersonalTitleEdit, EditPersonalTitleResult, PersonalTitle, PersonalTitle> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PersonalTitleId", FieldType.ID, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }

    @Inject
    PartyControl partyControl;

    @Inject
    PersonalTitleLogic personalTitleLogic;

    /** Creates a new instance of EditPersonalTitleCommand */
    public EditPersonalTitleCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPersonalTitleResult getResult() {
        return PartyResultFactory.getEditPersonalTitleResult();
    }

    @Override
    public PersonalTitleEdit getEdit() {
        return PartyEditFactory.getPersonalTitleEdit();
    }

    @Override
    public PersonalTitle getEntity(EditPersonalTitleResult result) {
        return personalTitleLogic.getPersonalTitleByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public PersonalTitle getLockEntity(PersonalTitle personalTitle) {
        return personalTitle;
    }

    @Override
    public void fillInResult(EditPersonalTitleResult result, PersonalTitle personalTitle) {
        result.setPersonalTitle(partyControl.getPersonalTitleTransfer(getUserVisit(), personalTitle));
    }

    @Override
    public void doLock(PersonalTitleEdit edit, PersonalTitle personalTitle) {
        var personalTitleDetail = personalTitle.getLastDetail();

        edit.setDescription(personalTitleDetail.getDescription());
        edit.setIsDefault(personalTitleDetail.getIsDefault().toString());
        edit.setSortOrder(personalTitleDetail.getSortOrder().toString());
    }

    @Override
    public void doUpdate(PersonalTitle personalTitle) {
        var personalTitleDetailValue = partyControl.getPersonalTitleDetailValueByPKForUpdate(personalTitle.getPrimaryKey());

        personalTitleDetailValue.setDescription(edit.getDescription());
        personalTitleDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        personalTitleDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        partyControl.updatePersonalTitleFromValue(personalTitleDetailValue, getPartyPK());
    }

}
