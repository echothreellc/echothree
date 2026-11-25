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

import com.echothree.control.user.user.common.edit.RecoveryQuestionDescriptionEdit;
import com.echothree.control.user.user.common.edit.UserEditFactory;
import com.echothree.control.user.user.common.result.EditRecoveryQuestionDescriptionResult;
import com.echothree.control.user.user.common.result.UserResultFactory;
import com.echothree.control.user.user.common.spec.RecoveryQuestionDescriptionSpec;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.user.server.logic.RecoveryQuestionLogic;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.model.data.user.server.entity.RecoveryQuestionDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditRecoveryQuestionDescriptionCommand
        extends BaseAbstractEditCommand<RecoveryQuestionDescriptionSpec, RecoveryQuestionDescriptionEdit, EditRecoveryQuestionDescriptionResult, RecoveryQuestionDescription, RecoveryQuestion> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("RecoveryQuestionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    LanguageLogic languageLogic;

    @Inject
    RecoveryQuestionLogic recoveryQuestionLogic;

    /** Creates a new instance of EditRecoveryQuestionDescriptionCommand */
    public EditRecoveryQuestionDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditRecoveryQuestionDescriptionResult getResult() {
        return UserResultFactory.getEditRecoveryQuestionDescriptionResult();
    }

    @Override
    public RecoveryQuestionDescriptionEdit getEdit() {
        return UserEditFactory.getRecoveryQuestionDescriptionEdit();
    }

    @Override
    public RecoveryQuestionDescription getEntity(EditRecoveryQuestionDescriptionResult result) {
        RecoveryQuestionDescription recoveryQuestionDescription = null;
        var recoveryQuestionName = spec.getRecoveryQuestionName();
        var languageIsoName = spec.getLanguageIsoName();
        var recoveryQuestion = recoveryQuestionLogic.getRecoveryQuestionByName(this, recoveryQuestionName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            recoveryQuestionDescription = userControl.getRecoveryQuestionDescription(recoveryQuestion, language,
                    editModeToEntityPermission(editMode));

            if(recoveryQuestionDescription == null) {
                addExecutionError(ExecutionErrors.UnknownRecoveryQuestionDescription.name(), recoveryQuestionName, languageIsoName);
            }
        }

        return recoveryQuestionDescription;
    }

    @Override
    public RecoveryQuestion getLockEntity(RecoveryQuestionDescription recoveryQuestionDescription) {
        return recoveryQuestionDescription.getRecoveryQuestion();
    }

    @Override
    public void fillInResult(EditRecoveryQuestionDescriptionResult result, RecoveryQuestionDescription recoveryQuestionDescription) {
        result.setRecoveryQuestionDescription(userControl.getRecoveryQuestionDescriptionTransfer(getUserVisit(), recoveryQuestionDescription));
    }

    @Override
    public void doLock(RecoveryQuestionDescriptionEdit edit, RecoveryQuestionDescription recoveryQuestionDescription) {
        edit.setDescription(recoveryQuestionDescription.getDescription());
    }

    @Override
    public void doUpdate(RecoveryQuestionDescription recoveryQuestionDescription) {
        var recoveryQuestionDescriptionValue = userControl.getRecoveryQuestionDescriptionValue(recoveryQuestionDescription);

        recoveryQuestionDescriptionValue.setDescription(edit.getDescription());

        userControl.updateRecoveryQuestionDescriptionFromValue(recoveryQuestionDescriptionValue, getPartyPK());
    }

}
