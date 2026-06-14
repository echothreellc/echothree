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

import com.echothree.control.user.core.common.form.GetApplicationEditorUsesForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.ApplicationControl;
import com.echothree.model.control.core.server.logic.ApplicationLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.Application;
import com.echothree.model.data.core.server.entity.ApplicationEditorUse;
import com.echothree.model.data.core.server.factory.ApplicationEditorUseFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetApplicationEditorUsesCommand
        extends BasePaginatedMultipleEntitiesCommand<ApplicationEditorUse, GetApplicationEditorUsesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ApplicationEditorUse.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ApplicationName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    ApplicationControl applicationControl;

    @Inject
    ApplicationLogic applicationLogic;

    /** Creates a new instance of GetApplicationEditorUsesCommand */
    public GetApplicationEditorUsesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    Application application;

    @Override
    protected void handleForm() {
        var applicationName = form.getApplicationName();

        application = applicationLogic.getApplicationByName(this, applicationName);
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : applicationControl.countApplicationEditorUsesByApplication(application);
    }

    @Override
    protected Collection<ApplicationEditorUse> getEntities() {
        return hasExecutionErrors() ? null : applicationControl.getApplicationEditorUsesByApplication(application);
    }

    @Override
    protected BaseResult getResult(Collection<ApplicationEditorUse> entities) {
        var result = CoreResultFactory.getGetApplicationEditorUsesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setApplication(applicationControl.getApplicationTransfer(userVisit, application));

            if(session.hasLimit(ApplicationEditorUseFactory.class)) {
                result.setApplicationEditorUseCount(getTotalEntities());
            }

            result.setApplicationEditorUses(applicationControl.getApplicationEditorUseTransfers((List<ApplicationEditorUse>)entities, userVisit));
        }

        return result;
    }

}
