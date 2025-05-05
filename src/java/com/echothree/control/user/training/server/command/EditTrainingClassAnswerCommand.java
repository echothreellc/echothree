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

package com.echothree.control.user.training.server.command;

import com.echothree.control.user.training.common.edit.TrainingClassAnswerEdit;
import com.echothree.control.user.training.common.edit.TrainingEditFactory;
import com.echothree.control.user.training.common.form.EditTrainingClassAnswerForm;
import com.echothree.control.user.training.common.result.EditTrainingClassAnswerResult;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.control.user.training.common.spec.TrainingClassAnswerSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.training.server.entity.TrainingClassAnswer;
import com.echothree.model.data.training.server.entity.TrainingClassQuestion;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditTrainingClassAnswerCommand
        extends BaseAbstractEditCommand<TrainingClassAnswerSpec, TrainingClassAnswerEdit, EditTrainingClassAnswerResult, TrainingClassAnswer, TrainingClassAnswer> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClassAnswer.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassQuestionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassAnswerName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassAnswerName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsCorrect", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("AnswerMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Answer", FieldType.STRING, false, null, null),
                new FieldDefinition("SelectedMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Selected", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditTrainingClassAnswerCommand */
    public EditTrainingClassAnswerCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditTrainingClassAnswerResult getResult() {
        return TrainingResultFactory.getEditTrainingClassAnswerResult();
    }

    @Override
    public TrainingClassAnswerEdit getEdit() {
        return TrainingEditFactory.getTrainingClassAnswerEdit();
    }

    TrainingClassQuestion trainingClassQuestion;
    
    @Override
    public TrainingClassAnswer getEntity(EditTrainingClassAnswerResult result) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        TrainingClassAnswer trainingClassAnswer = null;
        var trainingClassName = spec.getTrainingClassName();
        var trainingClass = trainingControl.getTrainingClassByName(trainingClassName);

        if(trainingClass != null) {
            var trainingClassSectionName = spec.getTrainingClassSectionName();
            var trainingClassSection = trainingControl.getTrainingClassSectionByName(trainingClass, trainingClassSectionName);

            if(trainingClassSection != null) {
                var trainingClassQuestionName = spec.getTrainingClassQuestionName();
                
                trainingClassQuestion = trainingControl.getTrainingClassQuestionByName(trainingClassSection, trainingClassQuestionName);

                if(trainingClassQuestion != null) {
                    var trainingClassAnswerName = spec.getTrainingClassAnswerName();

                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        trainingClassAnswer = trainingControl.getTrainingClassAnswerByName(trainingClassQuestion, trainingClassAnswerName);
                    } else { // EditMode.UPDATE
                        trainingClassAnswer = trainingControl.getTrainingClassAnswerByNameForUpdate(trainingClassQuestion, trainingClassAnswerName);
                    }

                    if(trainingClassAnswer != null) {
                        result.setTrainingClassAnswer(trainingControl.getTrainingClassAnswerTransfer(getUserVisit(), trainingClassAnswer));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTrainingClassAnswerName.name(), trainingClassName, trainingClassSectionName,
                                trainingClassQuestionName, trainingClassAnswerName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownTrainingClassQuestionName.name(), trainingClassName, trainingClassSectionName,
                            trainingClassQuestionName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTrainingClassSectionName.name(), trainingClassName, trainingClassSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
        }

        return trainingClassAnswer;
    }

    @Override
    public TrainingClassAnswer getLockEntity(TrainingClassAnswer trainingClassAnswer) {
        return trainingClassAnswer;
    }

    @Override
    public void fillInResult(EditTrainingClassAnswerResult result, TrainingClassAnswer trainingClassAnswer) {
        var trainingControl = Session.getModelController(TrainingControl.class);

        result.setTrainingClassAnswer(trainingControl.getTrainingClassAnswerTransfer(getUserVisit(), trainingClassAnswer));
    }

    MimeType answerMimeType;
    MimeType selectedMimeType;
    
    @Override
    public void doLock(TrainingClassAnswerEdit edit, TrainingClassAnswer trainingClassAnswer) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var trainingClassAnswerTranslation = trainingControl.getTrainingClassAnswerTranslation(trainingClassAnswer, getPreferredLanguage());
        var trainingClassAnswerDetail = trainingClassAnswer.getLastDetail();

        edit.setTrainingClassAnswerName(trainingClassAnswerDetail.getTrainingClassAnswerName());
        edit.setIsCorrect(trainingClassAnswerDetail.getIsCorrect().toString());
        edit.setSortOrder(trainingClassAnswerDetail.getSortOrder().toString());

        if(trainingClassAnswerTranslation != null) {
            answerMimeType = trainingClassAnswerTranslation.getAnswerMimeType();
            selectedMimeType = trainingClassAnswerTranslation.getSelectedMimeType();

            edit.setAnswerMimeTypeName(answerMimeType == null? null: answerMimeType.getLastDetail().getMimeTypeName());
            edit.setAnswer(trainingClassAnswerTranslation.getAnswer());
            edit.setSelectedMimeTypeName(selectedMimeType == null? null: selectedMimeType.getLastDetail().getMimeTypeName());
            edit.setSelected(trainingClassAnswerTranslation.getSelected());
        }
    }

    @Override
    public void canUpdate(TrainingClassAnswer trainingClassAnswer) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var trainingClassAnswerName = edit.getTrainingClassAnswerName();
        var duplicateTrainingClassAnswer = trainingControl.getTrainingClassAnswerByName(trainingClassQuestion, trainingClassAnswerName);

        if(duplicateTrainingClassAnswer != null && !trainingClassAnswer.equals(duplicateTrainingClassAnswer)) {
            addExecutionError(ExecutionErrors.DuplicateTrainingClassAnswerName.name(), trainingClassAnswerName);
        } else {
            var mimeTypeLogic = MimeTypeLogic.getInstance();
            var answerMimeTypeName = edit.getAnswerMimeTypeName();
            var answer = edit.getAnswer();

            answerMimeType = mimeTypeLogic.checkMimeType(this, answerMimeTypeName, answer, MimeTypeUsageTypes.TEXT.name(),
                    ExecutionErrors.MissingRequiredAnswerMimeTypeName.name(), ExecutionErrors.MissingRequiredAnswer.name(),
                    ExecutionErrors.UnknownAnswerMimeTypeName.name(), ExecutionErrors.UnknownAnswerMimeTypeUsage.name());

            if(!hasExecutionErrors()) {
                var selected = edit.getSelected();

                selectedMimeType = mimeTypeLogic.checkMimeType(this, edit.getSelectedMimeTypeName(), selected, MimeTypeUsageTypes.TEXT.name(),
                        ExecutionErrors.MissingRequiredSelectedMimeTypeName.name(), ExecutionErrors.MissingRequiredSelected.name(),
                        ExecutionErrors.UnknownSelectedMimeTypeName.name(), ExecutionErrors.UnknownSelectedMimeTypeUsage.name());

                if(!hasExecutionErrors()) {
                    if(answer == null && selected != null) {
                        addExecutionError(ExecutionErrors.MissingRequiredAnswerMimeTypeName.name());
                        addExecutionError(ExecutionErrors.MissingRequiredAnswer.name());
                    }
                }
            }
        }
    }
    
    @Override
    public void doUpdate(TrainingClassAnswer trainingClassAnswer) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var partyPK = getPartyPK();
        var trainingClassAnswerDetailValue = trainingControl.getTrainingClassAnswerDetailValueForUpdate(trainingClassAnswer);
        var trainingClassAnswerTranslation = trainingControl.getTrainingClassAnswerTranslationForUpdate(trainingClassAnswer, getPreferredLanguage());
        var answer = edit.getAnswer();
        var selected = edit.getSelected();

        trainingClassAnswerDetailValue.setTrainingClassAnswerName(edit.getTrainingClassAnswerName());
        trainingClassAnswerDetailValue.setIsCorrect(Boolean.valueOf(edit.getIsCorrect()));
        trainingClassAnswerDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        trainingControl.updateTrainingClassAnswerFromValue(trainingClassAnswerDetailValue, partyPK);

        if(trainingClassAnswerTranslation == null && (answer != null || selected != null)) {
            trainingControl.createTrainingClassAnswerTranslation(trainingClassAnswer, getPreferredLanguage(), answerMimeType, answer, selectedMimeType,
                    selected, partyPK);
        } else if(trainingClassAnswerTranslation != null && (answer == null && selected == null)) {
            trainingControl.deleteTrainingClassAnswerTranslation(trainingClassAnswerTranslation, partyPK);
        } else if(trainingClassAnswerTranslation != null && (answer != null || selected != null)) {
            var trainingClassAnswerTranslationValue = trainingControl.getTrainingClassAnswerTranslationValue(trainingClassAnswerTranslation);

            trainingClassAnswerTranslationValue.setAnswerMimeTypePK(answerMimeType == null? null: answerMimeType.getPrimaryKey());
            trainingClassAnswerTranslationValue.setAnswer(answer);
            trainingClassAnswerTranslationValue.setSelectedMimeTypePK(selectedMimeType == null? null: selectedMimeType.getPrimaryKey());
            trainingClassAnswerTranslationValue.setSelected(selected);
            trainingControl.updateTrainingClassAnswerTranslationFromValue(trainingClassAnswerTranslationValue, partyPK);
        }
    }

}
