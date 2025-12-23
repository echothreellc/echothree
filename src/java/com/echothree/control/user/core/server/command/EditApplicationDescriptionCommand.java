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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.ApplicationDescriptionEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.form.EditApplicationDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditApplicationDescriptionResult;
import com.echothree.control.user.core.common.spec.ApplicationDescriptionSpec;
import com.echothree.model.control.core.server.control.ApplicationControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Application;
import com.echothree.model.data.core.server.entity.ApplicationDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
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
public class EditApplicationDescriptionCommand
        extends BaseAbstractEditCommand<ApplicationDescriptionSpec, ApplicationDescriptionEdit, EditApplicationDescriptionResult, ApplicationDescription, Application> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Application.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditApplicationDescriptionCommand */
    public EditApplicationDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditApplicationDescriptionResult getResult() {
        return CoreResultFactory.getEditApplicationDescriptionResult();
    }

    @Override
    public ApplicationDescriptionEdit getEdit() {
        return CoreEditFactory.getApplicationDescriptionEdit();
    }

    @Override
    public ApplicationDescription getEntity(EditApplicationDescriptionResult result) {
        var applicationControl = Session.getModelController(ApplicationControl.class);
        ApplicationDescription applicationDescription = null;
        var applicationName = spec.getApplicationName();
        var application = applicationControl.getApplicationByName(applicationName);

        if(application != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    applicationDescription = applicationControl.getApplicationDescription(application, language);
                } else { // EditMode.UPDATE
                    applicationDescription = applicationControl.getApplicationDescriptionForUpdate(application, language);
                }

                if(applicationDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownApplicationDescription.name(), applicationName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownApplicationName.name(), applicationName);
        }

        return applicationDescription;
    }

    @Override
    public Application getLockEntity(ApplicationDescription applicationDescription) {
        return applicationDescription.getApplication();
    }

    @Override
    public void fillInResult(EditApplicationDescriptionResult result, ApplicationDescription applicationDescription) {
        var applicationControl = Session.getModelController(ApplicationControl.class);

        result.setApplicationDescription(applicationControl.getApplicationDescriptionTransfer(getUserVisit(), applicationDescription));
    }

    @Override
    public void doLock(ApplicationDescriptionEdit edit, ApplicationDescription applicationDescription) {
        edit.setDescription(applicationDescription.getDescription());
    }

    @Override
    public void doUpdate(ApplicationDescription applicationDescription) {
        var applicationControl = Session.getModelController(ApplicationControl.class);
        var applicationDescriptionValue = applicationControl.getApplicationDescriptionValue(applicationDescription);
        applicationDescriptionValue.setDescription(edit.getDescription());

        applicationControl.updateApplicationDescriptionFromValue(applicationDescriptionValue, getPartyPK());
    }
    
}
