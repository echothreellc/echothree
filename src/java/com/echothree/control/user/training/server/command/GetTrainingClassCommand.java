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

import com.echothree.control.user.training.common.form.GetTrainingClassForm;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.training.server.logic.PartyTrainingClassLogic;
import com.echothree.model.control.training.server.logic.TrainingClassLogic;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.util.common.command.BaseResult;
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
public class GetTrainingClassCommand
        extends BaseSingleEntityCommand<TrainingClass, GetTrainingClassForm> {
    
    private final static CommandSecurityDefinition employeeCommandSecurityDefinition;
    private final static CommandSecurityDefinition testingCommandSecurityDefinition;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        employeeCommandSecurityDefinition = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClass.name(), SecurityRoles.Review.name())
                ))
        ));

        testingCommandSecurityDefinition = new CommandSecurityDefinition(List.of(
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

    /** Creates a new instance of GetTrainingClassCommand */
    public GetTrainingClassCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return form.getTrainingClassName() == null ? employeeCommandSecurityDefinition : testingCommandSecurityDefinition;
    }

    @Override
    protected TrainingClass getEntity() {
        var trainingClassName = form.getTrainingClassName();
        var partyTrainingClassName = form.getPartyTrainingClassName();
        var parameterCount = (trainingClassName == null ? 0 : 1) + (partyTrainingClassName == null ? 0 : 1);
        TrainingClass trainingClass = null;

        if(parameterCount == 0 || trainingClassName != null) {
            trainingClass = trainingClassLogic.getTrainingClassByName(this, trainingClassName, true);
        } else {
            var partyTrainingClass = partyTrainingClassLogic.getPartyTrainingClassByName(this, partyTrainingClassName);

            if(!hasExecutionErrors()) {
                partyTrainingClassLogic.checkPartyTrainingClassStatus(this, partyTrainingClass, getPartyPK());

                if(!hasExecutionErrors()) {
                    trainingClass = partyTrainingClass.getLastDetail().getTrainingClass();
                }
            }
        }

        if(trainingClass != null) {
            sendEvent(trainingClass.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return trainingClass;
    }

    @Override
    protected BaseResult getResult(TrainingClass trainingClass) {
        var result = TrainingResultFactory.getGetTrainingClassResult();

        if(trainingClass != null) {
            result.setTrainingClass(trainingControl.getTrainingClassTransfer(getUserVisit(), trainingClass));
        }

        return result;
    }
    
}
