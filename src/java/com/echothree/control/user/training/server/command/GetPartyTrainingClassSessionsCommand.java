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

import com.echothree.control.user.training.common.form.GetPartyTrainingClassSessionsForm;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.training.server.logic.PartyTrainingClassLogic;
import com.echothree.model.data.training.server.entity.PartyTrainingClass;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSession;
import com.echothree.model.data.training.server.factory.PartyTrainingClassSessionFactory;
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
public class GetPartyTrainingClassSessionsCommand
        extends BasePaginatedMultipleEntitiesCommand<PartyTrainingClassSession, GetPartyTrainingClassSessionsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyTrainingClassSession.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyTrainingClassName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    TrainingControl trainingControl;

    @Inject
    PartyTrainingClassLogic partyTrainingClassLogic;
    
    /** Creates a new instance of GetPartyTrainingClassSessionsCommand */
    public GetPartyTrainingClassSessionsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    protected PartyTrainingClass partyTrainingClass;

    @Override
    protected void handleForm() {
        var partyTrainingClassName = form.getPartyTrainingClassName();

        partyTrainingClass = partyTrainingClassLogic.getPartyTrainingClassByName(this, partyTrainingClassName);
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : trainingControl.countPartyTrainingClassSessionsByPartyTrainingClass(partyTrainingClass);
    }

    @Override
    protected Collection<PartyTrainingClassSession> getEntities() {
        return hasExecutionErrors() ? null : trainingControl.getPartyTrainingClassSessionsByPartyTrainingClass(partyTrainingClass);
    }

    @Override
    protected BaseResult getResult(Collection<PartyTrainingClassSession> entities) {
        var result = TrainingResultFactory.getGetPartyTrainingClassSessionsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setPartyTrainingClass(trainingControl.getPartyTrainingClassTransfer(userVisit, partyTrainingClass));

            if(session.hasLimit(PartyTrainingClassSessionFactory.class)) {
                result.setPartyTrainingClassSessionCount(getTotalEntities());
            }

            result.setPartyTrainingClassSessions(trainingControl.getPartyTrainingClassSessionTransfers(userVisit, entities));
        }

        return result;
    }
    
}
