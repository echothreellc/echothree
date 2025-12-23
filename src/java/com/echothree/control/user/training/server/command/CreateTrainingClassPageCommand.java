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

import com.echothree.control.user.training.common.form.CreateTrainingClassPageForm;
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
public class CreateTrainingClassPageCommand
        extends BaseSimpleCommand<CreateTrainingClassPageForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClassPage.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassSectionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassPageName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PercentageToPass", FieldType.FRACTIONAL_PERCENT, false, null, null),
                new FieldDefinition("QuestionCount", FieldType.UNSIGNED_INTEGER, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("PageMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Page", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateTrainingClassPageCommand */
    public CreateTrainingClassPageCommand() {
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
                var trainingClassPageName = form.getTrainingClassPageName();
                var trainingClassPage = trainingControl.getTrainingClassPageByName(trainingClassSection, trainingClassPageName);

                if(trainingClassPage == null) {
                    var mimeTypeLogic = MimeTypeLogic.getInstance();
                    var page = form.getPage();
                    var pageMimeType = mimeTypeLogic.checkMimeType(this, form.getPageMimeTypeName(), page, MimeTypeUsageTypes.TEXT.name(),
                            ExecutionErrors.MissingRequiredPageMimeTypeName.name(), ExecutionErrors.MissingRequiredPage.name(),
                            ExecutionErrors.UnknownPageMimeTypeName.name(), ExecutionErrors.UnknownPageMimeTypeUsage.name());

                    if(!hasExecutionErrors()) {
                        var createdBy = getPartyPK();
                        var sortOrder = Integer.valueOf(form.getSortOrder());
                        var description = form.getDescription();

                        trainingClassPage = trainingControl.createTrainingClassPage(trainingClassSection, trainingClassPageName, sortOrder, createdBy);

                        if(description != null || page != null) {
                            trainingControl.createTrainingClassPageTranslation(trainingClassPage, getPreferredLanguage(), description, pageMimeType,
                                    page, createdBy);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateTrainingClassPageName.name(), trainingClassName, trainingClassPageName);
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
