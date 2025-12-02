// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.party.common.spec.PartySpec;
import com.echothree.control.user.user.common.edit.RecoveryAnswerEdit;
import com.echothree.control.user.user.common.edit.UserEditFactory;
import com.echothree.control.user.user.common.form.EditRecoveryAnswerForm;
import com.echothree.control.user.user.common.result.EditRecoveryAnswerResult;
import com.echothree.control.user.user.common.result.UserResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.RecoveryAnswer;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditRecoveryAnswerCommand
        extends BaseAbstractEditCommand<PartySpec, RecoveryAnswerEdit, EditRecoveryAnswerResult, RecoveryAnswer, RecoveryAnswer> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("RecoveryQuestionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Answer", FieldType.STRING, true, 1L, 40L)
                ));
    }
    
    /** Creates a new instance of EditRecoveryAnswerCommand */
    public EditRecoveryAnswerCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditRecoveryAnswerResult getResult() {
        return UserResultFactory.getEditRecoveryAnswerResult();
    }

    @Override
    public RecoveryAnswerEdit getEdit() {
        return UserEditFactory.getRecoveryAnswerEdit();
    }

    @Override
    public RecoveryAnswer getEntity(EditRecoveryAnswerResult result) {
        RecoveryAnswer recoveryAnswer = null;
        var party = PartyLogic.getInstance().getPartyByName(this, spec.getPartyName());

        if(!hasExecutionErrors()) {
            PartyLogic.getInstance().checkPartyType(this, party, PartyTypes.CUSTOMER.name());

            if(!hasExecutionErrors()) {
                var userControl = getUserControl();

                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    recoveryAnswer = userControl.getRecoveryAnswer(party);
                } else { // EditMode.UPDATE
                    recoveryAnswer = userControl.getRecoveryAnswerForUpdate(party);
                }

                if(recoveryAnswer == null) {
                    addExecutionError(ExecutionErrors.UnknownRecoveryAnswer.name());
                }
            }
        }

        return recoveryAnswer;
    }

    @Override
    public RecoveryAnswer getLockEntity(RecoveryAnswer recoveryAnswer) {
        return recoveryAnswer;
    }

    @Override
    public void fillInResult(EditRecoveryAnswerResult result, RecoveryAnswer recoveryAnswer) {
        var userControl = getUserControl();

        result.setRecoveryAnswer(userControl.getRecoveryAnswerTransfer(getUserVisit(), recoveryAnswer));
    }

    @Override
    public void doLock(RecoveryAnswerEdit edit, RecoveryAnswer recoveryAnswer) {
        var recoveryAnswerDetail = recoveryAnswer.getLastDetail();
        
        edit.setRecoveryQuestionName(recoveryAnswerDetail.getRecoveryQuestion().getLastDetail().getRecoveryQuestionName());
        edit.setAnswer(recoveryAnswerDetail.getAnswer());
    }

    RecoveryQuestion recoveryQuestion;
    
    @Override
    public void canUpdate(RecoveryAnswer recoveryAnswer) {
        var userControl = getUserControl();
        var recoveryQuestionName = edit.getRecoveryQuestionName();
        
        recoveryQuestion = userControl.getRecoveryQuestionByName(recoveryQuestionName);
        
        if(recoveryQuestion == null) {
            addExecutionError(ExecutionErrors.UnknownRecoveryQuestionName.name(), recoveryQuestionName);
        }
    }

    @Override
    public void doUpdate(RecoveryAnswer recoveryAnswer) {
        var userControl = getUserControl();
        var recoveryAnswerDetailValue = userControl.getRecoveryAnswerDetailValueForUpdate(recoveryAnswer);
        
        recoveryAnswerDetailValue.setRecoveryQuestionPK(recoveryQuestion.getPrimaryKey());
        recoveryAnswerDetailValue.setAnswer(edit.getAnswer());
        
        userControl.updateRecoveryAnswerFromValue(recoveryAnswerDetailValue, getPartyPK());
    }
    
}
