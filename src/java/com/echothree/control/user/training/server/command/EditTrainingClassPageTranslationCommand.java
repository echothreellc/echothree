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

import com.echothree.control.user.training.common.edit.TrainingClassPageTranslationEdit;
import com.echothree.control.user.training.common.edit.TrainingEditFactory;
import com.echothree.control.user.training.common.form.EditTrainingClassPageTranslationForm;
import com.echothree.control.user.training.common.result.EditTrainingClassPageTranslationResult;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.control.user.training.common.spec.TrainingClassPageTranslationSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.training.server.entity.TrainingClassPage;
import com.echothree.model.data.training.server.entity.TrainingClassPageTranslation;
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
public class EditTrainingClassPageTranslationCommand
        extends BaseAbstractEditCommand<TrainingClassPageTranslationSpec, TrainingClassPageTranslationEdit, EditTrainingClassPageTranslationResult, TrainingClassPageTranslation, TrainingClassPage> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClassPage.name(), SecurityRoles.Translation.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassPageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L),
                new FieldDefinition("PageMimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("Page", FieldType.STRING, true, null, null)
                ));
    }

    /** Creates a new instance of EditTrainingClassPageTranslationCommand */
    public EditTrainingClassPageTranslationCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTrainingClassPageTranslationResult getResult() {
        return TrainingResultFactory.getEditTrainingClassPageTranslationResult();
    }

    @Override
    public TrainingClassPageTranslationEdit getEdit() {
        return TrainingEditFactory.getTrainingClassPageTranslationEdit();
    }

    TrainingClassSection trainingClassSection;
    
    @Override
    public TrainingClassPageTranslation getEntity(EditTrainingClassPageTranslationResult result) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        TrainingClassPageTranslation trainingClassPageTranslation = null;
        var trainingClassName = spec.getTrainingClassName();
        var trainingClass = trainingControl.getTrainingClassByName(trainingClassName);

        if(trainingClass != null) {
            var trainingClassSectionName = spec.getTrainingClassSectionName();
            
            trainingClassSection = trainingControl.getTrainingClassSectionByName(trainingClass, trainingClassSectionName);

            if(trainingClassSection != null) {
                var trainingClassPageName = spec.getTrainingClassPageName();
                var trainingClassPage = trainingControl.getTrainingClassPageByName(trainingClassSection, trainingClassPageName);

                if(trainingClassPage != null) {
                    var partyControl = Session.getModelController(PartyControl.class);
                    var languageIsoName = spec.getLanguageIsoName();
                    var language = partyControl.getLanguageByIsoName(languageIsoName);

                    if(language != null) {
                        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                            trainingClassPageTranslation = trainingControl.getTrainingClassPageTranslation(trainingClassPage, language);
                        } else { // EditMode.UPDATE
                            trainingClassPageTranslation = trainingControl.getTrainingClassPageTranslationForUpdate(trainingClassPage, language);
                        }

                        if(trainingClassPageTranslation == null) {
                            addExecutionError(ExecutionErrors.UnknownTrainingClassPageTranslation.name(), trainingClassName, trainingClassSectionName,
                                    trainingClassPageName, languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownTrainingClassPageName.name(), trainingClassName, trainingClassSectionName, trainingClassPageName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTrainingClassSectionName.name(), trainingClassName, trainingClassSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
        }

        return trainingClassPageTranslation;
    }

    @Override
    public TrainingClassPage getLockEntity(TrainingClassPageTranslation trainingClassPageTranslation) {
        return trainingClassPageTranslation.getTrainingClassPage();
    }

    @Override
    public void fillInResult(EditTrainingClassPageTranslationResult result, TrainingClassPageTranslation trainingClassPageTranslation) {
        var trainingControl = Session.getModelController(TrainingControl.class);

        result.setTrainingClassPageTranslation(trainingControl.getTrainingClassPageTranslationTransfer(getUserVisit(), trainingClassPageTranslation));
    }

    MimeType pageMimeType;
    MimeType introductionMimeType;
    
    @Override
    public void doLock(TrainingClassPageTranslationEdit edit, TrainingClassPageTranslation trainingClassPageTranslation) {
        pageMimeType = trainingClassPageTranslation.getPageMimeType();
        
        edit.setDescription(trainingClassPageTranslation.getDescription());
        edit.setPageMimeTypeName(pageMimeType == null? null: pageMimeType.getLastDetail().getMimeTypeName());
        edit.setPage(trainingClassPageTranslation.getPage());
    }

    @Override
    protected void canUpdate(TrainingClassPageTranslation trainingClassPageTranslation) {
        var mimeTypeLogic = MimeTypeLogic.getInstance();
        var pageMimeTypeName = edit.getPageMimeTypeName();
        var page = edit.getPage();
        
        pageMimeType = mimeTypeLogic.checkMimeType(this, pageMimeTypeName, page, MimeTypeUsageTypes.TEXT.name(),
                ExecutionErrors.MissingRequiredPageMimeTypeName.name(), ExecutionErrors.MissingRequiredPage.name(),
                ExecutionErrors.UnknownPageMimeTypeName.name(), ExecutionErrors.UnknownPageMimeTypeUsage.name());
    }
    
    @Override
    public void doUpdate(TrainingClassPageTranslation trainingClassPageTranslation) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var trainingClassPageTranslationValue = trainingControl.getTrainingClassPageTranslationValue(trainingClassPageTranslation);
        
        trainingClassPageTranslationValue.setDescription(edit.getDescription());
        trainingClassPageTranslationValue.setPageMimeTypePK(pageMimeType == null? null: pageMimeType.getPrimaryKey());
        trainingClassPageTranslationValue.setPage(edit.getPage());
        
        trainingControl.updateTrainingClassPageTranslationFromValue(trainingClassPageTranslationValue, getPartyPK());
    }

}
