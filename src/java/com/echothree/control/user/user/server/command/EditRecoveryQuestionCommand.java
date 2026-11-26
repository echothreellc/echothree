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

package com.echothree.control.user.user.server.command;

import com.echothree.control.user.user.common.edit.RecoveryQuestionEdit;
import com.echothree.control.user.user.common.edit.UserEditFactory;
import com.echothree.control.user.user.common.result.EditRecoveryQuestionResult;
import com.echothree.control.user.user.common.result.UserResultFactory;
import com.echothree.control.user.user.common.spec.RecoveryQuestionUniversalSpec;
import com.echothree.model.control.user.server.logic.RecoveryQuestionLogic;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditRecoveryQuestionCommand
        extends BaseEditCommand<RecoveryQuestionUniversalSpec, RecoveryQuestionEdit, EditRecoveryQuestionResult, RecoveryQuestion, RecoveryQuestion> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("RecoveryQuestionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("RecoveryQuestionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    RecoveryQuestionLogic recoveryQuestionLogic;

    /** Creates a new instance of EditRecoveryQuestionCommand */
    public EditRecoveryQuestionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditRecoveryQuestionResult getResult() {
        return UserResultFactory.getEditRecoveryQuestionResult();
    }

    @Override
    public RecoveryQuestionEdit getEdit() {
        return UserEditFactory.getRecoveryQuestionEdit();
    }

    @Override
    public RecoveryQuestion getEntity(EditRecoveryQuestionResult result) {
        return recoveryQuestionLogic.getRecoveryQuestionByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public RecoveryQuestion getLockEntity(RecoveryQuestion recoveryQuestion) {
        return recoveryQuestion;
    }

    @Override
    public void fillInResult(EditRecoveryQuestionResult result, RecoveryQuestion recoveryQuestion) {
        result.setRecoveryQuestion(userControl.getRecoveryQuestionTransfer(getUserVisit(), recoveryQuestion));
    }

    @Override
    public void doLock(RecoveryQuestionEdit edit, RecoveryQuestion recoveryQuestion) {
        var recoveryQuestionDescription = userControl.getRecoveryQuestionDescription(recoveryQuestion, getPreferredLanguage());
        var recoveryQuestionDetail = recoveryQuestion.getLastDetail();

        edit.setRecoveryQuestionName(recoveryQuestionDetail.getRecoveryQuestionName());
        edit.setIsDefault(recoveryQuestionDetail.getIsDefault().toString());
        edit.setSortOrder(recoveryQuestionDetail.getSortOrder().toString());

        if(recoveryQuestionDescription != null) {
            edit.setDescription(recoveryQuestionDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(RecoveryQuestion recoveryQuestion) {
        var recoveryQuestionName = edit.getRecoveryQuestionName();
        var duplicateRecoveryQuestion = userControl.getRecoveryQuestionByName(recoveryQuestionName);

        if(duplicateRecoveryQuestion != null && !recoveryQuestion.equals(duplicateRecoveryQuestion)) {
            addExecutionError(ExecutionErrors.DuplicateRecoveryQuestionName.name(), recoveryQuestionName);
        }
    }

    @Override
    public void doUpdate(RecoveryQuestion recoveryQuestion) {
        var partyPK = getPartyPK();
        var recoveryQuestionDetailValue = userControl.getRecoveryQuestionDetailValueForUpdate(recoveryQuestion);
        var recoveryQuestionDescription = userControl.getRecoveryQuestionDescriptionForUpdate(recoveryQuestion, getPreferredLanguage());
        var description = edit.getDescription();

        recoveryQuestionDetailValue.setRecoveryQuestionName(edit.getRecoveryQuestionName());
        recoveryQuestionDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        recoveryQuestionDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        userControl.updateRecoveryQuestionFromValue(recoveryQuestionDetailValue, partyPK);

        if(recoveryQuestionDescription == null && description != null) {
            userControl.createRecoveryQuestionDescription(recoveryQuestion, getPreferredLanguage(), description, partyPK);
        } else if(recoveryQuestionDescription != null && description == null) {
            userControl.deleteRecoveryQuestionDescription(recoveryQuestionDescription, partyPK);
        } else if(recoveryQuestionDescription != null && description != null) {
            var recoveryQuestionDescriptionValue = userControl.getRecoveryQuestionDescriptionValue(recoveryQuestionDescription);

            recoveryQuestionDescriptionValue.setDescription(description);

            userControl.updateRecoveryQuestionDescriptionFromValue(recoveryQuestionDescriptionValue, partyPK);
        }
    }

}
