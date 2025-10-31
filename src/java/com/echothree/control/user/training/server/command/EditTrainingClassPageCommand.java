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

import com.echothree.control.user.training.common.edit.TrainingClassPageEdit;
import com.echothree.control.user.training.common.edit.TrainingEditFactory;
import com.echothree.control.user.training.common.form.EditTrainingClassPageForm;
import com.echothree.control.user.training.common.result.EditTrainingClassPageResult;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.control.user.training.common.spec.TrainingClassPageSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.training.server.entity.TrainingClassPage;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditTrainingClassPageCommand
        extends BaseAbstractEditCommand<TrainingClassPageSpec, TrainingClassPageEdit, EditTrainingClassPageResult, TrainingClassPage, TrainingClassPage> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClassPage.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassPageName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassPageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PercentageToPass", FieldType.FRACTIONAL_PERCENT, false, null, null),
                new FieldDefinition("QuestionCount", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("PageMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Page", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of EditTrainingClassPageCommand */
    public EditTrainingClassPageCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditTrainingClassPageResult getResult() {
        return TrainingResultFactory.getEditTrainingClassPageResult();
    }

    @Override
    public TrainingClassPageEdit getEdit() {
        return TrainingEditFactory.getTrainingClassPageEdit();
    }

    TrainingClassSection trainingClassSection;
    
    @Override
    public TrainingClassPage getEntity(EditTrainingClassPageResult result) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        TrainingClassPage trainingClassPage = null;
        var trainingClassName = spec.getTrainingClassName();
        var trainingClass = trainingControl.getTrainingClassByName(trainingClassName);

        if(trainingClass != null) {
            var trainingClassSectionName = spec.getTrainingClassSectionName();
            
            trainingClassSection = trainingControl.getTrainingClassSectionByName(trainingClass, trainingClassSectionName);

            if(trainingClassSection != null) {
                var trainingClassPageName = spec.getTrainingClassPageName();

                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    trainingClassPage = trainingControl.getTrainingClassPageByName(trainingClassSection, trainingClassPageName);
                } else { // EditMode.UPDATE
                    trainingClassPage = trainingControl.getTrainingClassPageByNameForUpdate(trainingClassSection, trainingClassPageName);
                }

                if(trainingClassPage != null) {
                    result.setTrainingClassPage(trainingControl.getTrainingClassPageTransfer(getUserVisit(), trainingClassPage));
                } else {
                    addExecutionError(ExecutionErrors.UnknownTrainingClassPageName.name(), trainingClassName, trainingClassSectionName, trainingClassPageName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTrainingClassSectionName.name(), trainingClassName, trainingClassSectionName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
        }

        return trainingClassPage;
    }

    @Override
    public TrainingClassPage getLockEntity(TrainingClassPage trainingClassPage) {
        return trainingClassPage;
    }

    @Override
    public void fillInResult(EditTrainingClassPageResult result, TrainingClassPage trainingClassPage) {
        var trainingControl = Session.getModelController(TrainingControl.class);

        result.setTrainingClassPage(trainingControl.getTrainingClassPageTransfer(getUserVisit(), trainingClassPage));
    }

    MimeType pageMimeType;
    MimeType introductionMimeType;
    
    @Override
    public void doLock(TrainingClassPageEdit edit, TrainingClassPage trainingClassPage) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var trainingClassPageTranslation = trainingControl.getTrainingClassPageTranslation(trainingClassPage, getPreferredLanguage());
        var trainingClassPageDetail = trainingClassPage.getLastDetail();

        edit.setTrainingClassPageName(trainingClassPageDetail.getTrainingClassPageName());
        edit.setSortOrder(trainingClassPageDetail.getSortOrder().toString());

        if(trainingClassPageTranslation != null) {
            pageMimeType = trainingClassPageTranslation.getPageMimeType();

            edit.setDescription(trainingClassPageTranslation.getDescription());
            edit.setPageMimeTypeName(pageMimeType == null? null: pageMimeType.getLastDetail().getMimeTypeName());
            edit.setPage(trainingClassPageTranslation.getPage());
        }
    }

    @Override
    public void canUpdate(TrainingClassPage trainingClassPage) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var trainingClassPageName = edit.getTrainingClassPageName();
        var duplicateTrainingClassPage = trainingControl.getTrainingClassPageByName(trainingClassSection, trainingClassPageName);

        if(duplicateTrainingClassPage != null && !trainingClassPage.equals(duplicateTrainingClassPage)) {
            addExecutionError(ExecutionErrors.DuplicateTrainingClassPageName.name(), trainingClassPageName);
        } else {
            var mimeTypeLogic = MimeTypeLogic.getInstance();
            var pageMimeTypeName = edit.getPageMimeTypeName();
            var page = edit.getPage();

            pageMimeType = mimeTypeLogic.checkMimeType(this, pageMimeTypeName, page, MimeTypeUsageTypes.TEXT.name(),
                    ExecutionErrors.MissingRequiredPageMimeTypeName.name(), ExecutionErrors.MissingRequiredPage.name(),
                    ExecutionErrors.UnknownPageMimeTypeName.name(), ExecutionErrors.UnknownPageMimeTypeUsage.name());
        }
    }
    
    @Override
    public void doUpdate(TrainingClassPage trainingClassPage) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var partyPK = getPartyPK();
        var trainingClassPageDetailValue = trainingControl.getTrainingClassPageDetailValueForUpdate(trainingClassPage);
        var trainingClassPageTranslation = trainingControl.getTrainingClassPageTranslationForUpdate(trainingClassPage, getPreferredLanguage());
        var description = edit.getDescription();
        var page = edit.getPage();

        trainingClassPageDetailValue.setTrainingClassPageName(edit.getTrainingClassPageName());
        trainingClassPageDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        trainingControl.updateTrainingClassPageFromValue(trainingClassPageDetailValue, partyPK);

        if(trainingClassPageTranslation == null && (description != null || page != null)) {
            trainingControl.createTrainingClassPageTranslation(trainingClassPage, getPreferredLanguage(), description, pageMimeType, page,
                    partyPK);
        } else if(trainingClassPageTranslation != null && (description == null && page == null)) {
            trainingControl.deleteTrainingClassPageTranslation(trainingClassPageTranslation, partyPK);
        } else if(trainingClassPageTranslation != null && (description != null || page != null)) {
            var trainingClassPageTranslationValue = trainingControl.getTrainingClassPageTranslationValue(trainingClassPageTranslation);

            trainingClassPageTranslationValue.setDescription(description);
            trainingClassPageTranslationValue.setPageMimeTypePK(pageMimeType == null? null: pageMimeType.getPrimaryKey());
            trainingClassPageTranslationValue.setPage(page);
            trainingControl.updateTrainingClassPageTranslationFromValue(trainingClassPageTranslationValue, partyPK);
        }
    }

}
