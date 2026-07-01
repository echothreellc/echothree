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

package com.echothree.control.user.security.server.command;

import com.echothree.control.user.security.common.form.GetPartySecurityRoleTemplateTrainingClassesForm;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.security.server.logic.PartySecurityRoleTemplateLogic;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplate;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateTrainingClass;
import com.echothree.model.data.security.server.factory.PartySecurityRoleTemplateTrainingClassFactory;
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
public class GetPartySecurityRoleTemplateTrainingClassesCommand
        extends BasePaginatedMultipleEntitiesCommand<PartySecurityRoleTemplateTrainingClass, GetPartySecurityRoleTemplateTrainingClassesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartySecurityRoleTemplateTrainingClass.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartySecurityRoleTemplateName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    SecurityControl securityControl;

    @Inject
    PartySecurityRoleTemplateLogic partySecurityRoleTemplateLogic;

    /** Creates a new instance of GetPartySecurityRoleTemplateTrainingClassesCommand */
    public GetPartySecurityRoleTemplateTrainingClassesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    PartySecurityRoleTemplate partySecurityRoleTemplate;

    @Override
    protected void handleForm() {
        partySecurityRoleTemplate = partySecurityRoleTemplateLogic.getPartySecurityRoleTemplateByName(this, form.getPartySecurityRoleTemplateName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : securityControl.countPartySecurityRoleTemplateTrainingClassesByPartySecurityRoleTemplate(partySecurityRoleTemplate);
    }

    @Override
    protected Collection<PartySecurityRoleTemplateTrainingClass> getEntities() {
        return hasExecutionErrors() ? null : securityControl.getPartySecurityRoleTemplateTrainingClasses(partySecurityRoleTemplate);
    }

    @Override
    protected BaseResult getResult(Collection<PartySecurityRoleTemplateTrainingClass> entities) {
        var result = SecurityResultFactory.getGetPartySecurityRoleTemplateTrainingClassesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setPartySecurityRoleTemplate(securityControl.getPartySecurityRoleTemplateTransfer(userVisit, partySecurityRoleTemplate));

            if(session.hasLimit(PartySecurityRoleTemplateTrainingClassFactory.class)) {
                result.setPartySecurityRoleTemplateTrainingClassCount(getTotalEntities());
            }

            result.setPartySecurityRoleTemplateTrainingClasses(securityControl.getPartySecurityRoleTemplateTrainingClassTransfers(userVisit, entities));
        }

        return result;
    }
    
}
