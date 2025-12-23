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

import com.echothree.control.user.training.common.form.CreateTrainingClassSectionForm;
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
import javax.enterprise.context.Dependent;

@Dependent
public class CreateTrainingClassSectionCommand
        extends BaseSimpleCommand<CreateTrainingClassSectionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClassSection.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PercentageToPass", FieldType.FRACTIONAL_PERCENT, false, null, null),
                new FieldDefinition("QuestionCount", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("OverviewMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Overview", FieldType.STRING, false, null, null),
                new FieldDefinition("IntroductionMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Introduction", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateTrainingClassSectionCommand */
    public CreateTrainingClassSectionCommand() {
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

            if(trainingClassSection == null) {
                var mimeTypeLogic = MimeTypeLogic.getInstance();
                var overview = form.getOverview();
                var overviewMimeType = mimeTypeLogic.checkMimeType(this, form.getOverviewMimeTypeName(), overview, MimeTypeUsageTypes.TEXT.name(),
                        ExecutionErrors.MissingRequiredOverviewMimeTypeName.name(), ExecutionErrors.MissingRequiredOverview.name(),
                        ExecutionErrors.UnknownOverviewMimeTypeName.name(), ExecutionErrors.UnknownOverviewMimeTypeUsage.name());

                if(!hasExecutionErrors()) {
                    var introduction = form.getIntroduction();
                    var introductionMimeType = mimeTypeLogic.checkMimeType(this, form.getIntroductionMimeTypeName(), introduction, MimeTypeUsageTypes.TEXT.name(),
                            ExecutionErrors.MissingRequiredIntroductionMimeTypeName.name(), ExecutionErrors.MissingRequiredIntroduction.name(),
                            ExecutionErrors.UnknownIntroductionMimeTypeName.name(), ExecutionErrors.UnknownIntroductionMimeTypeUsage.name());

                    if(!hasExecutionErrors()) {
                        var createdBy = getPartyPK();
                        var strPercentageToPass = form.getPercentageToPass();
                        var percentageToPass = strPercentageToPass == null ? null : Integer.valueOf(strPercentageToPass);
                        var strQuestionCount = form.getQuestionCount();
                        var questionCount = strQuestionCount == null ? null : Integer.valueOf(strQuestionCount);
                        var sortOrder = Integer.valueOf(form.getSortOrder());
                        var description = form.getDescription();

                        trainingClassSection = trainingControl.createTrainingClassSection(trainingClass, trainingClassSectionName, percentageToPass,
                                questionCount, sortOrder, createdBy);

                        if(description != null || overview != null || introduction != null) {
                            trainingControl.createTrainingClassSectionTranslation(trainingClassSection, getPreferredLanguage(), description, overviewMimeType,
                                    overview, introductionMimeType, introduction, createdBy);
                        }
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateTrainingClassSectionName.name(), trainingClassName, trainingClassSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
        }

        return null;
    }
    
}
