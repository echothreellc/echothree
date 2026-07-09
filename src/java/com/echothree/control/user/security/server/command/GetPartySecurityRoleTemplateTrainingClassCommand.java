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

import com.echothree.control.user.security.common.form.GetPartySecurityRoleTemplateTrainingClassForm;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.security.server.logic.PartySecurityRoleTemplateLogic;
import com.echothree.model.control.training.server.logic.TrainingClassLogic;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateTrainingClass;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartySecurityRoleTemplateTrainingClassCommand
        extends BaseSingleEntityCommand<PartySecurityRoleTemplateTrainingClass, GetPartySecurityRoleTemplateTrainingClassForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartySecurityRoleTemplateTrainingClass.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartySecurityRoleTemplateName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    SecurityControl securityControl;

    @Inject
    PartySecurityRoleTemplateLogic partySecurityRoleTemplateLogic;

    @Inject
    TrainingClassLogic trainingClassLogic;

    /** Creates a new instance of GetPartySecurityRoleTemplateTrainingClassCommand */
    public GetPartySecurityRoleTemplateTrainingClassCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected PartySecurityRoleTemplateTrainingClass getEntity() {
        PartySecurityRoleTemplateTrainingClass partySecurityRoleTemplateTrainingClass = null;
        var partySecurityRoleTemplateName = form.getPartySecurityRoleTemplateName();
        var partySecurityRoleTemplate = partySecurityRoleTemplateLogic.getPartySecurityRoleTemplateByName(this, partySecurityRoleTemplateName);

        if(!hasExecutionErrors()) {
            var trainingClassName = form.getTrainingClassName();
            var trainingClass = trainingClassLogic.getTrainingClassByName(this, trainingClassName, false);

            if(!hasExecutionErrors()) {
                partySecurityRoleTemplateTrainingClass = securityControl.getPartySecurityRoleTemplateTrainingClass(partySecurityRoleTemplate, trainingClass);

                if(partySecurityRoleTemplateTrainingClass == null) {
                    addExecutionError(ExecutionErrors.UnknownPartySecurityRoleTemplateTrainingClass.name(), partySecurityRoleTemplateName, trainingClassName);
                }
            }
        }

        return partySecurityRoleTemplateTrainingClass;
    }

    @Override
    protected BaseResult getResult(PartySecurityRoleTemplateTrainingClass partySecurityRoleTemplateTrainingClass) {
        var result = SecurityResultFactory.getGetPartySecurityRoleTemplateTrainingClassResult();

        if(partySecurityRoleTemplateTrainingClass != null) {
            result.setPartySecurityRoleTemplateTrainingClass(securityControl.getPartySecurityRoleTemplateTrainingClassTransfer(getUserVisit(),
                    partySecurityRoleTemplateTrainingClass));
        }

        return result;
    }
    
}
