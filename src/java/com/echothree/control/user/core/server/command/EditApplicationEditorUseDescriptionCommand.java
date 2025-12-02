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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.ApplicationEditorUseDescriptionEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.form.EditApplicationEditorUseDescriptionForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditApplicationEditorUseDescriptionResult;
import com.echothree.control.user.core.common.spec.ApplicationEditorUseDescriptionSpec;
import com.echothree.model.control.core.server.control.ApplicationControl;
import com.echothree.model.control.core.server.logic.ApplicationLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.ApplicationEditorUse;
import com.echothree.model.data.core.server.entity.ApplicationEditorUseDescription;
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
public class EditApplicationEditorUseDescriptionCommand
        extends BaseAbstractEditCommand<ApplicationEditorUseDescriptionSpec, ApplicationEditorUseDescriptionEdit, EditApplicationEditorUseDescriptionResult, ApplicationEditorUseDescription, ApplicationEditorUse> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ApplicationEditorUse.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ApplicationEditorUseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditApplicationEditorUseDescriptionCommand */
    public EditApplicationEditorUseDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditApplicationEditorUseDescriptionResult getResult() {
        return CoreResultFactory.getEditApplicationEditorUseDescriptionResult();
    }

    @Override
    public ApplicationEditorUseDescriptionEdit getEdit() {
        return CoreEditFactory.getApplicationEditorUseDescriptionEdit();
    }

    @Override
    public ApplicationEditorUseDescription getEntity(EditApplicationEditorUseDescriptionResult result) {
        ApplicationEditorUseDescription applicationEditorUseDescription = null;
        var applicationControl = Session.getModelController(ApplicationControl.class);
        var applicationName = spec.getApplicationName();
        var application = ApplicationLogic.getInstance().getApplicationByName(this, applicationName);

        if(!hasExecutionErrors()) {
            var applicationEditorUseName = spec.getApplicationEditorUseName();
            var applicationEditorUse = applicationControl.getApplicationEditorUseByName(application, applicationEditorUseName);

            if(applicationEditorUse != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        applicationEditorUseDescription = applicationControl.getApplicationEditorUseDescription(applicationEditorUse, language);
                    } else { // EditMode.UPDATE
                        applicationEditorUseDescription = applicationControl.getApplicationEditorUseDescriptionForUpdate(applicationEditorUse, language);
                    }

                    if(applicationEditorUseDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownApplicationEditorUseDescription.name(), applicationName, applicationEditorUseName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownApplicationEditorUseName.name(), applicationName, applicationEditorUseName);
            }
        }
        
        return applicationEditorUseDescription;
    }

    @Override
    public ApplicationEditorUse getLockEntity(ApplicationEditorUseDescription applicationEditorUseDescription) {
        return applicationEditorUseDescription.getApplicationEditorUse();
    }

    @Override
    public void fillInResult(EditApplicationEditorUseDescriptionResult result, ApplicationEditorUseDescription applicationEditorUseDescription) {
        var applicationControl = Session.getModelController(ApplicationControl.class);

        result.setApplicationEditorUseDescription(applicationControl.getApplicationEditorUseDescriptionTransfer(getUserVisit(), applicationEditorUseDescription));
    }

    @Override
    public void doLock(ApplicationEditorUseDescriptionEdit edit, ApplicationEditorUseDescription applicationEditorUseDescription) {
        edit.setDescription(applicationEditorUseDescription.getDescription());
    }

    @Override
    public void doUpdate(ApplicationEditorUseDescription applicationEditorUseDescription) {
        var applicationControl = Session.getModelController(ApplicationControl.class);
        var applicationEditorUseDescriptionValue = applicationControl.getApplicationEditorUseDescriptionValue(applicationEditorUseDescription);
        applicationEditorUseDescriptionValue.setDescription(edit.getDescription());

        applicationControl.updateApplicationEditorUseDescriptionFromValue(applicationEditorUseDescriptionValue, getPartyPK());
    }
    
}
