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

import com.echothree.control.user.training.common.form.CreateTrainingClassAnswerForm;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateTrainingClassAnswerCommand
        extends BaseSimpleCommand<CreateTrainingClassAnswerForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClassAnswer.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassQuestionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassAnswerName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsCorrect", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("AnswerMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Answer", FieldType.STRING, false, null, null),
                new FieldDefinition("SelectedMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Selected", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateTrainingClassAnswerCommand */
    public CreateTrainingClassAnswerCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var trainingClassName = form.getTrainingClassName();
        var trainingClass = trainingControl.getTrainingClassByName(trainingClassName);

        if(trainingClass != null) {
            var trainingClassSectionName = form.getTrainingClassSectionName();
            var trainingClassSection = trainingControl.getTrainingClassSectionByName(trainingClass, trainingClassSectionName);

            if(trainingClassSection != null) {
                var trainingClassQuestionName = form.getTrainingClassQuestionName();
                var trainingClassQuestion = trainingControl.getTrainingClassQuestionByName(trainingClassSection, trainingClassQuestionName);

                if(trainingClassQuestion != null) {
                    var trainingClassAnswerName = form.getTrainingClassAnswerName();
                    var trainingClassAnswer = trainingControl.getTrainingClassAnswerByName(trainingClassQuestion, trainingClassAnswerName);

                    if(trainingClassAnswer == null) {
                        var mimeTypeLogic = MimeTypeLogic.getInstance();
                        var answer = form.getAnswer();
                        var answerMimeType = mimeTypeLogic.checkMimeType(this, form.getAnswerMimeTypeName(), answer, MimeTypeUsageTypes.TEXT.name(),
                                ExecutionErrors.MissingRequiredAnswerMimeTypeName.name(), ExecutionErrors.MissingRequiredAnswer.name(),
                                ExecutionErrors.UnknownAnswerMimeTypeName.name(), ExecutionErrors.UnknownAnswerMimeTypeUsage.name());

                        if(!hasExecutionErrors()) {
                            var selected = form.getSelected();
                            var selectedMimeType = mimeTypeLogic.checkMimeType(this, form.getSelectedMimeTypeName(), selected, MimeTypeUsageTypes.TEXT.name(),
                                    ExecutionErrors.MissingRequiredSelectedMimeTypeName.name(), ExecutionErrors.MissingRequiredSelected.name(),
                                    ExecutionErrors.UnknownSelectedMimeTypeName.name(), ExecutionErrors.UnknownSelectedMimeTypeUsage.name());

                            if(!hasExecutionErrors()) {
                                if(answer == null && selected != null) {
                                    addExecutionError(ExecutionErrors.MissingRequiredAnswerMimeTypeName.name());
                                    addExecutionError(ExecutionErrors.MissingRequiredAnswer.name());
                                } else {
                                    var createdBy = getPartyPK();
                                    var isCorrect = Boolean.valueOf(form.getIsCorrect());
                                    var sortOrder = Integer.valueOf(form.getSortOrder());

                                    trainingClassAnswer = trainingControl.createTrainingClassAnswer(trainingClassQuestion, trainingClassAnswerName, isCorrect,
                                            sortOrder, createdBy);

                                    if(answer != null || selected != null) {
                                        trainingControl.createTrainingClassAnswerTranslation(trainingClassAnswer, getPreferredLanguage(), answerMimeType,
                                                answer, selectedMimeType, selected, createdBy);
                                    }
                                }
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateTrainingClassAnswerName.name(), trainingClassName, trainingClassSectionName,
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

        return null;
    }
    
}
