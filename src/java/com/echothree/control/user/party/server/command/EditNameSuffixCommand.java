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

import com.echothree.control.user.party.common.edit.NameSuffixEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.result.EditNameSuffixResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.NameSuffixUniversalSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.NameSuffixLogic;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditNameSuffixCommand
        extends BaseEditCommand<NameSuffixUniversalSpec, NameSuffixEdit, EditNameSuffixResult, NameSuffix, NameSuffix> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("NameSuffixId", FieldType.ID, false, null, null),
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
    NameSuffixLogic nameSuffixLogic;

    /** Creates a new instance of EditNameSuffixCommand */
    public EditNameSuffixCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditNameSuffixResult getResult() {
        return PartyResultFactory.getEditNameSuffixResult();
    }

    @Override
    public NameSuffixEdit getEdit() {
        return PartyEditFactory.getNameSuffixEdit();
    }

    @Override
    public NameSuffix getEntity(EditNameSuffixResult result) {
        return nameSuffixLogic.getNameSuffixByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public NameSuffix getLockEntity(NameSuffix nameSuffix) {
        return nameSuffix;
    }

    @Override
    public void fillInResult(EditNameSuffixResult result, NameSuffix nameSuffix) {
        result.setNameSuffix(partyControl.getNameSuffixTransfer(getUserVisit(), nameSuffix));
    }

    @Override
    public void doLock(NameSuffixEdit edit, NameSuffix nameSuffix) {
        var nameSuffixDetail = nameSuffix.getLastDetail();

        edit.setDescription(nameSuffixDetail.getDescription());
        edit.setIsDefault(nameSuffixDetail.getIsDefault().toString());
        edit.setSortOrder(nameSuffixDetail.getSortOrder().toString());
    }

    @Override
    public void doUpdate(NameSuffix nameSuffix) {
        var nameSuffixDetailValue = partyControl.getNameSuffixDetailValueByPKForUpdate(nameSuffix.getPrimaryKey());

        nameSuffixDetailValue.setDescription(edit.getDescription());
        nameSuffixDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        nameSuffixDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        partyControl.updateNameSuffixFromValue(nameSuffixDetailValue, getPartyPK());
    }

}
