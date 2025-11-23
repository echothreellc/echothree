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

import com.echothree.control.user.core.common.edit.ApplicationEdit;
import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.form.EditApplicationForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditApplicationResult;
import com.echothree.control.user.core.common.spec.ApplicationSpec;
import com.echothree.model.control.core.server.control.ApplicationControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Application;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditApplicationCommand
        extends BaseAbstractEditCommand<ApplicationSpec, ApplicationEdit, EditApplicationResult, Application, Application> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Application.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditApplicationCommand */
    public EditApplicationCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditApplicationResult getResult() {
        return CoreResultFactory.getEditApplicationResult();
    }

    @Override
    public ApplicationEdit getEdit() {
        return CoreEditFactory.getApplicationEdit();
    }

    @Override
    public Application getEntity(EditApplicationResult result) {
        var applicationControl = Session.getModelController(ApplicationControl.class);
        Application application;
        var applicationName = spec.getApplicationName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            application = applicationControl.getApplicationByName(applicationName);
        } else { // EditMode.UPDATE
            application = applicationControl.getApplicationByNameForUpdate(applicationName);
        }

        if(application == null) {
            addExecutionError(ExecutionErrors.UnknownApplicationName.name(), applicationName);
        }

        return application;
    }

    @Override
    public Application getLockEntity(Application application) {
        return application;
    }

    @Override
    public void fillInResult(EditApplicationResult result, Application application) {
        var applicationControl = Session.getModelController(ApplicationControl.class);

        result.setApplication(applicationControl.getApplicationTransfer(getUserVisit(), application));
    }

    @Override
    public void doLock(ApplicationEdit edit, Application application) {
        var applicationControl = Session.getModelController(ApplicationControl.class);
        var applicationDescription = applicationControl.getApplicationDescription(application, getPreferredLanguage());
        var applicationDetail = application.getLastDetail();

        edit.setApplicationName(applicationDetail.getApplicationName());
        edit.setIsDefault(applicationDetail.getIsDefault().toString());
        edit.setSortOrder(applicationDetail.getSortOrder().toString());

        if(applicationDescription != null) {
            edit.setDescription(applicationDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(Application application) {
        var applicationControl = Session.getModelController(ApplicationControl.class);
        var applicationName = edit.getApplicationName();
        var duplicateApplication = applicationControl.getApplicationByName(applicationName);

        if(duplicateApplication != null && !application.equals(duplicateApplication)) {
            addExecutionError(ExecutionErrors.DuplicateApplicationName.name(), applicationName);
        }
    }

    @Override
    public void doUpdate(Application application) {
        var applicationControl = Session.getModelController(ApplicationControl.class);
        var partyPK = getPartyPK();
        var applicationDetailValue = applicationControl.getApplicationDetailValueForUpdate(application);
        var applicationDescription = applicationControl.getApplicationDescriptionForUpdate(application, getPreferredLanguage());
        var description = edit.getDescription();

        applicationDetailValue.setApplicationName(edit.getApplicationName());
        applicationDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        applicationDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        applicationControl.updateApplicationFromValue(applicationDetailValue, partyPK);

        if(applicationDescription == null && description != null) {
            applicationControl.createApplicationDescription(application, getPreferredLanguage(), description, partyPK);
        } else {
            if(applicationDescription != null && description == null) {
                applicationControl.deleteApplicationDescription(applicationDescription, partyPK);
            } else {
                if(applicationDescription != null && description != null) {
                    var applicationDescriptionValue = applicationControl.getApplicationDescriptionValue(applicationDescription);

                    applicationDescriptionValue.setDescription(description);
                    applicationControl.updateApplicationDescriptionFromValue(applicationDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
