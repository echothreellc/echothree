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

import com.echothree.control.user.training.common.form.GetTrainingClassSectionsForm;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.training.server.logic.PartyTrainingClassLogic;
import com.echothree.model.control.training.server.logic.TrainingClassLogic;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClassSection;
import com.echothree.model.data.training.server.factory.TrainingClassSectionFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
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
public class GetTrainingClassSectionsCommand
        extends BasePaginatedMultipleEntitiesCommand<TrainingClassSection, GetTrainingClassSectionsForm> {
    
    private final static CommandSecurityDefinition EMPLOYEE_COMMAND_SECURITY_DEFINITION;
    private final static CommandSecurityDefinition TESTING_COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        EMPLOYEE_COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClassSection.name(), SecurityRoles.List.name())
                ))
        ));

        TESTING_COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyTrainingClassName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    @Inject
    TrainingControl trainingControl;

    @Inject
    PartyTrainingClassLogic partyTrainingClassLogic;

    @Inject
    TrainingClassLogic trainingClassLogic;

    /** Creates a new instance of GetTrainingClassSectionsCommand */
    public GetTrainingClassSectionsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    TrainingClass trainingClass;

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return form.getTrainingClassName() == null ? EMPLOYEE_COMMAND_SECURITY_DEFINITION : TESTING_COMMAND_SECURITY_DEFINITION;
    }

    @Override
    protected void handleForm() {
        var trainingClassName = form.getTrainingClassName();
        var partyTrainingClassName = form.getPartyTrainingClassName();
        var parameterCount = (trainingClassName == null ? 0 : 1) + (partyTrainingClassName == null ? 0 : 1);

        if(parameterCount == 0) {
            trainingClass = trainingClassLogic.getTrainingClassByName(this, null, true);
        } else if(parameterCount == 1) {
            if(trainingClassName != null) {
                trainingClass = trainingClassLogic.getTrainingClassByName(this, trainingClassName, false);
            } else {
                var partyTrainingClass = partyTrainingClassLogic.getPartyTrainingClassByName(this, partyTrainingClassName);

                if(!hasExecutionErrors()) {
                    partyTrainingClassLogic.checkPartyTrainingClassStatus(this, partyTrainingClass, getPartyPK());

                    if(!hasExecutionErrors()) {
                        trainingClass = partyTrainingClass.getLastDetail().getTrainingClass();
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : trainingControl.countTrainingClassSectionsByTrainingClass(trainingClass);
    }

    @Override
    protected Collection<TrainingClassSection> getEntities() {
        return hasExecutionErrors() ? null : trainingControl.getTrainingClassSections(trainingClass);
    }

    @Override
    protected BaseResult getResult(Collection<TrainingClassSection> entities) {
        var result = TrainingResultFactory.getGetTrainingClassSectionsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setTrainingClass(trainingControl.getTrainingClassTransfer(userVisit, trainingClass));

            if(session.hasLimit(TrainingClassSectionFactory.class)) {
                result.setTrainingClassSectionCount(trainingControl.countTrainingClassSectionsByTrainingClass(trainingClass));
            }

            result.setTrainingClassSections(trainingControl.getTrainingClassSectionTransfers(userVisit, entities));
        }

        return result;
    }
    
}
