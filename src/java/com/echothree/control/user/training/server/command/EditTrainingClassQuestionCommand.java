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

import com.echothree.control.user.training.common.edit.TrainingClassQuestionEdit;
import com.echothree.control.user.training.common.edit.TrainingEditFactory;
import com.echothree.control.user.training.common.form.EditTrainingClassQuestionForm;
import com.echothree.control.user.training.common.result.EditTrainingClassQuestionResult;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.control.user.training.common.spec.TrainingClassQuestionSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.training.server.entity.TrainingClassQuestion;
import com.echothree.model.data.training.server.entity.TrainingClassSection;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditTrainingClassQuestionCommand
        extends BaseAbstractEditCommand<TrainingClassQuestionSpec, TrainingClassQuestionEdit, EditTrainingClassQuestionResult, TrainingClassQuestion, TrainingClassQuestion> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClassQuestion.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassQuestionName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassQuestionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("AskingRequired", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("PassingRequired", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("QuestionMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Question", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditTrainingClassQuestionCommand */
    public EditTrainingClassQuestionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditTrainingClassQuestionResult getResult() {
        return TrainingResultFactory.getEditTrainingClassQuestionResult();
    }

    @Override
    public TrainingClassQuestionEdit getEdit() {
        return TrainingEditFactory.getTrainingClassQuestionEdit();
    }

    TrainingClassSection trainingClassSection;
    
    @Override
    public TrainingClassQuestion getEntity(EditTrainingClassQuestionResult result) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        TrainingClassQuestion trainingClassQuestion = null;
        var trainingClassName = spec.getTrainingClassName();
        var trainingClass = trainingControl.getTrainingClassByName(trainingClassName);

        if(trainingClass != null) {
            var trainingClassSectionName = spec.getTrainingClassSectionName();
            
            trainingClassSection = trainingControl.getTrainingClassSectionByName(trainingClass, trainingClassSectionName);

            if(trainingClassSection != null) {
                var trainingClassQuestionName = spec.getTrainingClassQuestionName();

                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    trainingClassQuestion = trainingControl.getTrainingClassQuestionByName(trainingClassSection, trainingClassQuestionName);
                } else { // EditMode.UPDATE
                    trainingClassQuestion = trainingControl.getTrainingClassQuestionByNameForUpdate(trainingClassSection, trainingClassQuestionName);
                }

                if(trainingClassQuestion != null) {
                    result.setTrainingClassQuestion(trainingControl.getTrainingClassQuestionTransfer(getUserVisit(), trainingClassQuestion));
                } else {
                    addExecutionError(ExecutionErrors.UnknownTrainingClassQuestionName.name(), trainingClassName, trainingClassQuestionName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTrainingClassSectionName.name(), trainingClassName, trainingClassSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
        }

        return trainingClassQuestion;
    }

    @Override
    public TrainingClassQuestion getLockEntity(TrainingClassQuestion trainingClassQuestion) {
        return trainingClassQuestion;
    }

    @Override
    public void fillInResult(EditTrainingClassQuestionResult result, TrainingClassQuestion trainingClassQuestion) {
        var trainingControl = Session.getModelController(TrainingControl.class);

        result.setTrainingClassQuestion(trainingControl.getTrainingClassQuestionTransfer(getUserVisit(), trainingClassQuestion));
    }

    MimeType questionMimeType;
    
    @Override
    public void doLock(TrainingClassQuestionEdit edit, TrainingClassQuestion trainingClassQuestion) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var trainingClassQuestionTranslation = trainingControl.getTrainingClassQuestionTranslation(trainingClassQuestion, getPreferredLanguage());
        var trainingClassQuestionDetail = trainingClassQuestion.getLastDetail();

        edit.setTrainingClassQuestionName(trainingClassQuestionDetail.getTrainingClassQuestionName());
        edit.setAskingRequired(trainingClassQuestionDetail.getAskingRequired().toString());
        edit.setPassingRequired(trainingClassQuestionDetail.getPassingRequired().toString());
        edit.setSortOrder(trainingClassQuestionDetail.getSortOrder().toString());

        if(trainingClassQuestionTranslation != null) {
            edit.setQuestionMimeTypeName(trainingClassQuestionTranslation.getQuestionMimeType().getLastDetail().getMimeTypeName());
            edit.setQuestion(trainingClassQuestionTranslation.getQuestion());
        }
    }

    @Override
    public void canUpdate(TrainingClassQuestion trainingClassQuestion) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var trainingClassQuestionName = edit.getTrainingClassQuestionName();
        var duplicateTrainingClassQuestion = trainingControl.getTrainingClassQuestionByName(trainingClassSection, trainingClassQuestionName);

        if(duplicateTrainingClassQuestion != null && !trainingClassQuestion.equals(duplicateTrainingClassQuestion)) {
            addExecutionError(ExecutionErrors.DuplicateTrainingClassQuestionName.name(), trainingClassQuestionName);
        } else {
            var mimeTypeLogic = MimeTypeLogic.getInstance();
            var questionMimeTypeName = edit.getQuestionMimeTypeName();
            var question = edit.getQuestion();

            questionMimeType = mimeTypeLogic.checkMimeType(this, questionMimeTypeName, question, MimeTypeUsageTypes.TEXT.name(),
                    ExecutionErrors.MissingRequiredQuestionMimeTypeName.name(), ExecutionErrors.MissingRequiredQuestion.name(),
                    ExecutionErrors.UnknownQuestionMimeTypeName.name(), ExecutionErrors.UnknownQuestionMimeTypeUsage.name());
        }
    }
    
    @Override
    public void doUpdate(TrainingClassQuestion trainingClassQuestion) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var partyPK = getPartyPK();
        var trainingClassQuestionDetailValue = trainingControl.getTrainingClassQuestionDetailValueForUpdate(trainingClassQuestion);
        var trainingClassQuestionTranslation = trainingControl.getTrainingClassQuestionTranslationForUpdate(trainingClassQuestion, getPreferredLanguage());
        var question = edit.getQuestion();

        trainingClassQuestionDetailValue.setTrainingClassQuestionName(edit.getTrainingClassQuestionName());
        trainingClassQuestionDetailValue.setAskingRequired(Boolean.valueOf(edit.getAskingRequired()));
        trainingClassQuestionDetailValue.setPassingRequired(Boolean.valueOf(edit.getPassingRequired()));
        trainingClassQuestionDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        trainingControl.updateTrainingClassQuestionFromValue(trainingClassQuestionDetailValue, partyPK);

        if(trainingClassQuestionTranslation == null && (question != null)) {
            trainingControl.createTrainingClassQuestionTranslation(trainingClassQuestion, getPreferredLanguage(), questionMimeType, question, partyPK);
        } else if(trainingClassQuestionTranslation != null && (question == null)) {
            trainingControl.deleteTrainingClassQuestionTranslation(trainingClassQuestionTranslation, partyPK);
        } else if(trainingClassQuestionTranslation != null && (question != null)) {
            var trainingClassQuestionTranslationValue = trainingControl.getTrainingClassQuestionTranslationValue(trainingClassQuestionTranslation);

            trainingClassQuestionTranslationValue.setQuestionMimeTypePK(questionMimeType == null? null: questionMimeType.getPrimaryKey());
            trainingClassQuestionTranslationValue.setQuestion(question);
            trainingControl.updateTrainingClassQuestionTranslationFromValue(trainingClassQuestionTranslationValue, partyPK);
        }
    }

}
