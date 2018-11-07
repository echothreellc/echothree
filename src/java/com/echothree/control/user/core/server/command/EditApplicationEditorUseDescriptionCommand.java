// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.core.server.logic.ApplicationLogic;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Application;
import com.echothree.model.data.core.server.entity.ApplicationEditorUse;
import com.echothree.model.data.core.server.entity.ApplicationEditorUseDescription;
import com.echothree.model.data.core.server.value.ApplicationEditorUseDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditApplicationEditorUseDescriptionCommand
        extends BaseAbstractEditCommand<ApplicationEditorUseDescriptionSpec, ApplicationEditorUseDescriptionEdit, EditApplicationEditorUseDescriptionResult, ApplicationEditorUseDescription, ApplicationEditorUse> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ApplicationEditorUse.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ApplicationEditorUseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditApplicationEditorUseDescriptionCommand */
    public EditApplicationEditorUseDescriptionCommand(UserVisitPK userVisitPK, EditApplicationEditorUseDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        CoreControl coreControl = getCoreControl();
        String applicationName = spec.getApplicationName();
        Application application = ApplicationLogic.getInstance().getApplicationByName(this, applicationName);

        if(!hasExecutionErrors()) {
            String applicationEditorUseName = spec.getApplicationEditorUseName();
            ApplicationEditorUse applicationEditorUse = coreControl.getApplicationEditorUseByName(application, applicationEditorUseName);

            if(applicationEditorUse != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        applicationEditorUseDescription = coreControl.getApplicationEditorUseDescription(applicationEditorUse, language);
                    } else { // EditMode.UPDATE
                        applicationEditorUseDescription = coreControl.getApplicationEditorUseDescriptionForUpdate(applicationEditorUse, language);
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
        CoreControl coreControl = getCoreControl();

        result.setApplicationEditorUseDescription(coreControl.getApplicationEditorUseDescriptionTransfer(getUserVisit(), applicationEditorUseDescription));
    }

    @Override
    public void doLock(ApplicationEditorUseDescriptionEdit edit, ApplicationEditorUseDescription applicationEditorUseDescription) {
        edit.setDescription(applicationEditorUseDescription.getDescription());
    }

    @Override
    public void doUpdate(ApplicationEditorUseDescription applicationEditorUseDescription) {
        CoreControl coreControl = getCoreControl();
        ApplicationEditorUseDescriptionValue applicationEditorUseDescriptionValue = coreControl.getApplicationEditorUseDescriptionValue(applicationEditorUseDescription);
        applicationEditorUseDescriptionValue.setDescription(edit.getDescription());

        coreControl.updateApplicationEditorUseDescriptionFromValue(applicationEditorUseDescriptionValue, getPartyPK());
    }
    
}
