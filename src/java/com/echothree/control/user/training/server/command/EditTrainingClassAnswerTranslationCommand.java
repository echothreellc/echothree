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

import com.echothree.control.user.training.common.edit.TrainingClassAnswerTranslationEdit;
import com.echothree.control.user.training.common.edit.TrainingEditFactory;
import com.echothree.control.user.training.common.form.EditTrainingClassAnswerTranslationForm;
import com.echothree.control.user.training.common.result.EditTrainingClassAnswerTranslationResult;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.control.user.training.common.spec.TrainingClassAnswerTranslationSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.training.server.entity.TrainingClassAnswer;
import com.echothree.model.data.training.server.entity.TrainingClassAnswerTranslation;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditTrainingClassAnswerTranslationCommand
        extends BaseAbstractEditCommand<TrainingClassAnswerTranslationSpec, TrainingClassAnswerTranslationEdit, EditTrainingClassAnswerTranslationResult, TrainingClassAnswerTranslation, TrainingClassAnswer> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClassAnswer.name(), SecurityRoles.Translation.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassQuestionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassAnswerName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AnswerMimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("Answer", FieldType.STRING, true, null, null),
                new FieldDefinition("SelectedMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Selected", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of EditTrainingClassAnswerTranslationCommand */
    public EditTrainingClassAnswerTranslationCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTrainingClassAnswerTranslationResult getResult() {
        return TrainingResultFactory.getEditTrainingClassAnswerTranslationResult();
    }

    @Override
    public TrainingClassAnswerTranslationEdit getEdit() {
        return TrainingEditFactory.getTrainingClassAnswerTranslationEdit();
    }

   TrainingClassQuestion trainingClassQuestion;
    
    @Override
    public TrainingClassAnswerTranslation getEntity(EditTrainingClassAnswerTranslationResult result) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        TrainingClassAnswerTranslation trainingClassAnswerTranslation = null;
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
                    var trainingClassAnswer = trainingControl.getTrainingClassAnswerByName(trainingClassQuestion, trainingClassAnswerName);

                    if(trainingClassAnswer != null) {
                        var partyControl = Session.getModelController(PartyControl.class);
                        var languageIsoName = spec.getLanguageIsoName();
                        var language = partyControl.getLanguageByIsoName(languageIsoName);

                        if(language != null) {
                            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                trainingClassAnswerTranslation = trainingControl.getTrainingClassAnswerTranslation(trainingClassAnswer, language);
                            } else { // EditMode.UPDATE
                                trainingClassAnswerTranslation = trainingControl.getTrainingClassAnswerTranslationForUpdate(trainingClassAnswer, language);
                            }

                            if(trainingClassAnswerTranslation == null) {
                                addExecutionError(ExecutionErrors.UnknownTrainingClassAnswerTranslation.name(), trainingClassName, trainingClassSectionName,
                                        trainingClassQuestionName, trainingClassAnswerName, languageIsoName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                        }
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

        return trainingClassAnswerTranslation;
    }

    @Override
    public TrainingClassAnswer getLockEntity(TrainingClassAnswerTranslation trainingClassAnswerTranslation) {
        return trainingClassAnswerTranslation.getTrainingClassAnswer();
    }

    @Override
    public void fillInResult(EditTrainingClassAnswerTranslationResult result, TrainingClassAnswerTranslation trainingClassAnswerTranslation) {
        var trainingControl = Session.getModelController(TrainingControl.class);

        result.setTrainingClassAnswerTranslation(trainingControl.getTrainingClassAnswerTranslationTransfer(getUserVisit(), trainingClassAnswerTranslation));
    }

    MimeType answerMimeType;
    MimeType selectedMimeType;
    
    @Override
    public void doLock(TrainingClassAnswerTranslationEdit edit, TrainingClassAnswerTranslation trainingClassAnswerTranslation) {
        answerMimeType = trainingClassAnswerTranslation.getAnswerMimeType();
        selectedMimeType = trainingClassAnswerTranslation.getSelectedMimeType();
        
        edit.setAnswerMimeTypeName(answerMimeType == null? null: answerMimeType.getLastDetail().getMimeTypeName());
        edit.setAnswer(trainingClassAnswerTranslation.getAnswer());
        edit.setSelectedMimeTypeName(selectedMimeType == null? null: selectedMimeType.getLastDetail().getMimeTypeName());
        edit.setSelected(trainingClassAnswerTranslation.getSelected());
    }

    @Override
    protected void canUpdate(TrainingClassAnswerTranslation trainingClassAnswerTranslation) {
        var mimeTypeLogic = MimeTypeLogic.getInstance();
        var answerMimeTypeName = edit.getAnswerMimeTypeName();
        var answer = edit.getAnswer();
        
        answerMimeType = mimeTypeLogic.checkMimeType(this, answerMimeTypeName, answer, MimeTypeUsageTypes.TEXT.name(),
                ExecutionErrors.MissingRequiredAnswerMimeTypeName.name(), ExecutionErrors.MissingRequiredAnswer.name(),
                ExecutionErrors.UnknownAnswerMimeTypeName.name(), ExecutionErrors.UnknownAnswerMimeTypeUsage.name());
        
        if(!hasExecutionErrors()) {
            var selectedMimeTypeName = edit.getSelectedMimeTypeName();
            var selected = edit.getSelected();

            selectedMimeType = mimeTypeLogic.checkMimeType(this, selectedMimeTypeName, selected, MimeTypeUsageTypes.TEXT.name(),
                    ExecutionErrors.MissingRequiredSelectedMimeTypeName.name(), ExecutionErrors.MissingRequiredSelected.name(),
                    ExecutionErrors.UnknownSelectedMimeTypeName.name(), ExecutionErrors.UnknownSelectedMimeTypeUsage.name());
        }
    }
    
    @Override
    public void doUpdate(TrainingClassAnswerTranslation trainingClassAnswerTranslation) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var trainingClassAnswerTranslationValue = trainingControl.getTrainingClassAnswerTranslationValue(trainingClassAnswerTranslation);
        
        trainingClassAnswerTranslationValue.setAnswerMimeTypePK(answerMimeType == null? null: answerMimeType.getPrimaryKey());
        trainingClassAnswerTranslationValue.setAnswer(edit.getAnswer());
        trainingClassAnswerTranslationValue.setSelectedMimeTypePK(selectedMimeType == null? null: selectedMimeType.getPrimaryKey());
        trainingClassAnswerTranslationValue.setSelected(edit.getSelected());
        
        trainingControl.updateTrainingClassAnswerTranslationFromValue(trainingClassAnswerTranslationValue, getPartyPK());
    }

}
