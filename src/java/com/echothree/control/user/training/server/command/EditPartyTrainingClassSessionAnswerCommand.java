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

package com.echothree.control.user.training.server.command;

import com.echothree.control.user.training.common.edit.PartyTrainingClassSessionAnswerEdit;
import com.echothree.control.user.training.common.edit.TrainingEditFactory;
import com.echothree.control.user.training.common.form.EditPartyTrainingClassSessionAnswerForm;
import com.echothree.control.user.training.common.result.EditPartyTrainingClassSessionAnswerResult;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.control.user.training.common.spec.PartyTrainingClassSessionAnswerSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.training.server.logic.PartyTrainingClassLogic;
import com.echothree.model.control.training.server.logic.PartyTrainingClassSessionLogic;
import com.echothree.model.control.training.server.logic.TrainingClassLogic;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionAnswer;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSessionQuestion;
import com.echothree.model.data.training.server.entity.TrainingClassAnswer;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.DateUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditPartyTrainingClassSessionAnswerCommand
        extends BaseAbstractEditCommand<PartyTrainingClassSessionAnswerSpec, PartyTrainingClassSessionAnswerEdit, EditPartyTrainingClassSessionAnswerResult, PartyTrainingClassSessionAnswer, PartyTrainingClassSessionQuestion> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyTrainingClassSessionAnswer.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyTrainingClassSessionSequence", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassQuestionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyTrainingClassSessionAnswerSequence", FieldType.UNSIGNED_INTEGER, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassAnswerName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("QuestionStartTime", FieldType.DATE_TIME, true, null, null),
                new FieldDefinition("QuestionEndTime", FieldType.DATE_TIME, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyTrainingClassSessionAnswerCommand */
    public EditPartyTrainingClassSessionAnswerCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPartyTrainingClassSessionAnswerResult getResult() {
        return TrainingResultFactory.getEditPartyTrainingClassSessionAnswerResult();
    }

    @Override
    public PartyTrainingClassSessionAnswerEdit getEdit() {
        return TrainingEditFactory.getPartyTrainingClassSessionAnswerEdit();
    }

    @Override
    public PartyTrainingClassSessionAnswer getEntity(EditPartyTrainingClassSessionAnswerResult result) {
        PartyTrainingClassSessionAnswer partyTrainingClassSessionAnswer = null;
        var partyTrainingClass = PartyTrainingClassLogic.getInstance().getPartyTrainingClassByName(this, spec.getPartyTrainingClassName());
        
        if(!hasExecutionErrors()) {
            var partyTrainingClassSessionSequence = Integer.valueOf(spec.getPartyTrainingClassSessionSequence());
            var partyTrainingClassSession = PartyTrainingClassSessionLogic.getInstance().getPartyTrainingClassSession(this,
                    partyTrainingClass, partyTrainingClassSessionSequence);
            
            if(!hasExecutionErrors()) {
                var trainingClassSection = TrainingClassLogic.getInstance().getTrainingClassSectionByName(this,
                        partyTrainingClassSession.getLastDetail().getPartyTrainingClass().getLastDetail().getTrainingClass(),
                        spec.getTrainingClassSectionName());
                
                if(!hasExecutionErrors()) {
                    var trainingClassQuestion = TrainingClassLogic.getInstance().getTrainingClassQuestionByName(this, trainingClassSection,
                            spec.getTrainingClassQuestionName());
                    
                    if(!hasExecutionErrors()) {
                        var partyTrainingClassSessionQuestion = PartyTrainingClassSessionLogic.getInstance().getPartyTrainingClassSessionQuestion(this,
                                partyTrainingClassSession, trainingClassQuestion);
                        
                        if(!hasExecutionErrors()) {
                            var partyTrainingClassSessionAnswerSequence = Integer.valueOf(spec.getPartyTrainingClassSessionAnswerSequence());

                            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                partyTrainingClassSessionAnswer = PartyTrainingClassSessionLogic.getInstance().getPartyTrainingClassSessionAnswer(this,
                                        partyTrainingClassSessionQuestion, partyTrainingClassSessionAnswerSequence);
                            } else { // EditMode.UPDATE
                                partyTrainingClassSessionAnswer = PartyTrainingClassSessionLogic.getInstance().getPartyTrainingClassSessionAnswerForUpdate(this,
                                        partyTrainingClassSessionQuestion, partyTrainingClassSessionAnswerSequence);
                            }
                        }
                    }
                }
            }
        }

        return partyTrainingClassSessionAnswer;
    }

    @Override
    public PartyTrainingClassSessionQuestion getLockEntity(PartyTrainingClassSessionAnswer partyTrainingClassSessionAnswer) {
        return partyTrainingClassSessionAnswer.getPartyTrainingClassSessionQuestion();
    }

    @Override
    public void fillInResult(EditPartyTrainingClassSessionAnswerResult result, PartyTrainingClassSessionAnswer partyTrainingClassSessionAnswer) {
        var trainingControl = Session.getModelController(TrainingControl.class);

        result.setPartyTrainingClassSessionAnswer(trainingControl.getPartyTrainingClassSessionAnswerTransfer(getUserVisit(), partyTrainingClassSessionAnswer));
    }
    
    TrainingClassAnswer trainingClassAnswer;
    
    @Override
    public void doLock(PartyTrainingClassSessionAnswerEdit edit, PartyTrainingClassSessionAnswer partyTrainingClassSessionAnswer) {
        var dateUtils = DateUtils.getInstance();
        var userVisit = getUserVisit();
        var preferredDateTimeFormat = getPreferredDateTimeFormat();
        
        trainingClassAnswer = partyTrainingClassSessionAnswer.getTrainingClassAnswer();
        
        edit.setTrainingClassAnswerName(trainingClassAnswer == null ? null : trainingClassAnswer.getLastDetail().getTrainingClassAnswerName());
        edit.setQuestionStartTime(dateUtils.formatTypicalDateTime(userVisit, preferredDateTimeFormat, partyTrainingClassSessionAnswer.getQuestionStartTime()));
        edit.setQuestionEndTime(dateUtils.formatTypicalDateTime(userVisit, preferredDateTimeFormat, partyTrainingClassSessionAnswer.getQuestionEndTime()));
    }

    Long questionStartTime;
    Long questionEndTime;
    
    @Override
    public void canUpdate(PartyTrainingClassSessionAnswer partyTrainingClassSessionAnswer) {
        var trainingClassAnswerName = edit.getTrainingClassAnswerName();

        trainingClassAnswer = trainingClassAnswerName == null ? null : TrainingClassLogic.getInstance().getTrainingClassAnswerByName(this,
                partyTrainingClassSessionAnswer.getTrainingClassAnswer().getLastDetail().getTrainingClassQuestion(), trainingClassAnswerName);
        
        if(!hasExecutionErrors()) {
            var strQuestionEndTime = edit.getQuestionEndTime();
            
            questionStartTime = Long.valueOf(edit.getQuestionStartTime());
            questionEndTime = strQuestionEndTime == null ? null : Long.valueOf(strQuestionEndTime);
        }
    }

    @Override
    public void doUpdate(PartyTrainingClassSessionAnswer partyTrainingClassSessionAnswer) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var partyPK = getPartyPK();
        var partyTrainingClassSessionAnswerValue = trainingControl.getPartyTrainingClassSessionAnswerValue(partyTrainingClassSessionAnswer);

        partyTrainingClassSessionAnswerValue.setTrainingClassAnswerPK(trainingClassAnswer == null ? null : trainingClassAnswer.getPrimaryKey());
        partyTrainingClassSessionAnswerValue.setQuestionStartTime(questionStartTime);
        partyTrainingClassSessionAnswerValue.setQuestionEndTime(questionEndTime);

        trainingControl.updatePartyTrainingClassSessionAnswerFromValue(partyTrainingClassSessionAnswerValue, partyPK);
    }

}
