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

import com.echothree.control.user.training.common.edit.TrainingClassTranslationEdit;
import com.echothree.control.user.training.common.edit.TrainingEditFactory;
import com.echothree.control.user.training.common.form.EditTrainingClassTranslationForm;
import com.echothree.control.user.training.common.result.EditTrainingClassTranslationResult;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.control.user.training.common.spec.TrainingClassTranslationSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClassTranslation;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditTrainingClassTranslationCommand
        extends BaseAbstractEditCommand<TrainingClassTranslationSpec, TrainingClassTranslationEdit, EditTrainingClassTranslationResult, TrainingClassTranslation, TrainingClass> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClass.name(), SecurityRoles.Translation.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L),
                new FieldDefinition("OverviewMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Overview", FieldType.STRING, false, null, null),
                new FieldDefinition("IntroductionMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Introduction", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of EditTrainingClassTranslationCommand */
    public EditTrainingClassTranslationCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTrainingClassTranslationResult getResult() {
        return TrainingResultFactory.getEditTrainingClassTranslationResult();
    }

    @Override
    public TrainingClassTranslationEdit getEdit() {
        return TrainingEditFactory.getTrainingClassTranslationEdit();
    }

    @Override
    public TrainingClassTranslation getEntity(EditTrainingClassTranslationResult result) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        TrainingClassTranslation trainingClassTranslation = null;
        var trainingClassName = spec.getTrainingClassName();
        var trainingClass = trainingControl.getTrainingClassByName(trainingClassName);

        if(trainingClass != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    trainingClassTranslation = trainingControl.getTrainingClassTranslation(trainingClass, language);
                } else { // EditMode.UPDATE
                    trainingClassTranslation = trainingControl.getTrainingClassTranslationForUpdate(trainingClass, language);
                }

                if(trainingClassTranslation == null) {
                    addExecutionError(ExecutionErrors.UnknownTrainingClassTranslation.name(), trainingClassName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
        }

        return trainingClassTranslation;
    }

    @Override
    public TrainingClass getLockEntity(TrainingClassTranslation trainingClassTranslation) {
        return trainingClassTranslation.getTrainingClass();
    }

    @Override
    public void fillInResult(EditTrainingClassTranslationResult result, TrainingClassTranslation trainingClassTranslation) {
        var trainingControl = Session.getModelController(TrainingControl.class);

        result.setTrainingClassTranslation(trainingControl.getTrainingClassTranslationTransfer(getUserVisit(), trainingClassTranslation));
    }

    MimeType overviewMimeType;
    MimeType introductionMimeType;
    
    @Override
    public void doLock(TrainingClassTranslationEdit edit, TrainingClassTranslation trainingClassTranslation) {
        overviewMimeType = trainingClassTranslation.getOverviewMimeType();
        introductionMimeType = trainingClassTranslation.getIntroductionMimeType();
        
        edit.setDescription(trainingClassTranslation.getDescription());
        edit.setOverviewMimeTypeName(overviewMimeType == null? null: overviewMimeType.getLastDetail().getMimeTypeName());
        edit.setOverview(trainingClassTranslation.getOverview());
        edit.setIntroductionMimeTypeName(introductionMimeType == null? null: introductionMimeType.getLastDetail().getMimeTypeName());
        edit.setIntroduction(trainingClassTranslation.getIntroduction());
    }

    @Override
    protected void canUpdate(TrainingClassTranslation trainingClassTranslation) {
        var mimeTypeLogic = MimeTypeLogic.getInstance();
        var overviewMimeTypeName = edit.getOverviewMimeTypeName();
        var overview = edit.getOverview();
        
        overviewMimeType = mimeTypeLogic.checkMimeType(this, overviewMimeTypeName, overview, MimeTypeUsageTypes.TEXT.name(),
                ExecutionErrors.MissingRequiredOverviewMimeTypeName.name(), ExecutionErrors.MissingRequiredOverview.name(),
                ExecutionErrors.UnknownOverviewMimeTypeName.name(), ExecutionErrors.UnknownOverviewMimeTypeUsage.name());
        
        if(!hasExecutionErrors()) {
            var introductionMimeTypeName = edit.getIntroductionMimeTypeName();
            var introduction = edit.getIntroduction();

            introductionMimeType = mimeTypeLogic.checkMimeType(this, introductionMimeTypeName, introduction, MimeTypeUsageTypes.TEXT.name(),
                    ExecutionErrors.MissingRequiredIntroductionMimeTypeName.name(), ExecutionErrors.MissingRequiredIntroduction.name(),
                    ExecutionErrors.UnknownIntroductionMimeTypeName.name(), ExecutionErrors.UnknownIntroductionMimeTypeUsage.name());
        }
    }
    
    @Override
    public void doUpdate(TrainingClassTranslation trainingClassTranslation) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var trainingClassTranslationValue = trainingControl.getTrainingClassTranslationValue(trainingClassTranslation);
        
        trainingClassTranslationValue.setDescription(edit.getDescription());
        trainingClassTranslationValue.setOverviewMimeTypePK(overviewMimeType == null? null: overviewMimeType.getPrimaryKey());
        trainingClassTranslationValue.setOverview(edit.getOverview());
        trainingClassTranslationValue.setIntroductionMimeTypePK(introductionMimeType == null? null: introductionMimeType.getPrimaryKey());
        trainingClassTranslationValue.setIntroduction(edit.getIntroduction());
        
        trainingControl.updateTrainingClassTranslationFromValue(trainingClassTranslationValue, getPartyPK());
    }

}
