// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.training.common.form.CreateTrainingClassTranslationForm;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClassTranslation;
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

public class CreateTrainingClassTranslationCommand
        extends BaseSimpleCommand<CreateTrainingClassTranslationForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClass.name(), SecurityRoles.Translation.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L),
                new FieldDefinition("OverviewMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Overview", FieldType.STRING, false, null, null),
                new FieldDefinition("IntroductionMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Introduction", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateTrainingClassTranslationCommand */
    public CreateTrainingClassTranslationCommand(UserVisitPK userVisitPK, CreateTrainingClassTranslationForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        String trainingClassName = form.getTrainingClassName();
        TrainingClass trainingClass = trainingControl.getTrainingClassByName(trainingClassName);

        if(trainingClass != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                TrainingClassTranslation trainingClassTranslation = trainingControl.getTrainingClassTranslation(trainingClass, language);

                if(trainingClassTranslation == null) {
                    MimeTypeLogic mimeTypeLogic = MimeTypeLogic.getInstance();
                    String overviewMimeTypeName = form.getOverviewMimeTypeName();
                    String overview = form.getOverview();

                    MimeType overviewMimeType = mimeTypeLogic.checkMimeType(this, overviewMimeTypeName, overview, MimeTypeUsageTypes.TEXT.name(),
                            ExecutionErrors.MissingRequiredOverviewMimeTypeName.name(), ExecutionErrors.MissingRequiredOverview.name(),
                            ExecutionErrors.UnknownOverviewMimeTypeName.name(), ExecutionErrors.UnknownOverviewMimeTypeUsage.name());

                    if(!hasExecutionErrors()) {
                        String introductionMimeTypeName = form.getIntroductionMimeTypeName();
                        String introduction = form.getIntroduction();

                        MimeType introductionMimeType = mimeTypeLogic.checkMimeType(this, introductionMimeTypeName, introduction, MimeTypeUsageTypes.TEXT.name(),
                                ExecutionErrors.MissingRequiredIntroductionMimeTypeName.name(), ExecutionErrors.MissingRequiredIntroduction.name(),
                                ExecutionErrors.UnknownIntroductionMimeTypeName.name(), ExecutionErrors.UnknownIntroductionMimeTypeUsage.name());

                        if(!hasExecutionErrors()) {
                            var description = form.getDescription();

                            trainingControl.createTrainingClassTranslation(trainingClass, language, description, overviewMimeType, overview,
                                    introductionMimeType, introduction, getPartyPK());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateTrainingClassTranslation.name(), trainingClassName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
        }

        return null;
    }
    
}
