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

package com.echothree.control.user.training.server.command;

import com.echothree.control.user.training.common.form.GetTrainingClassForm;
import com.echothree.control.user.training.common.result.GetTrainingClassResult;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.control.training.server.logic.PartyTrainingClassLogic;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.training.server.entity.PartyTrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetTrainingClassCommand
        extends BaseSimpleCommand<GetTrainingClassForm> {
    
    private final static CommandSecurityDefinition employeeCommandSecurityDefinition;
    private final static CommandSecurityDefinition testingCommandSecurityDefinition;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        employeeCommandSecurityDefinition = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TrainingClass.name(), SecurityRoles.Review.name())
                        )))
                )));

        testingCommandSecurityDefinition = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, null)
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyTrainingClassName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetTrainingClassCommand */
    public GetTrainingClassCommand(UserVisitPK userVisitPK, GetTrainingClassForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected CommandSecurityDefinition getCommandSecurityDefinition() {
        return form.getTrainingClassName() == null ? employeeCommandSecurityDefinition : testingCommandSecurityDefinition;
    }

    @Override
    protected BaseResult execute() {
        GetTrainingClassResult result = TrainingResultFactory.getGetTrainingClassResult();
        String trainingClassName = form.getTrainingClassName();
        String partyTrainingClassName = form.getPartyTrainingClassName();
        int parameterCount = (trainingClassName == null? 0: 1) + (partyTrainingClassName == null? 0: 1);

        if(parameterCount == 1) {
            TrainingControl trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
            TrainingClass trainingClass = null;
            PartyPK partyPK = getPartyPK();

            if(trainingClassName != null) {
                trainingClass = trainingControl.getTrainingClassByName(trainingClassName);

                if(trainingClass == null) {
                    addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
                }
            } else {
                PartyTrainingClass partyTrainingClass = trainingControl.getPartyTrainingClassByName(partyTrainingClassName);

                if(partyTrainingClass != null) {
                    PartyTrainingClassLogic.getInstance().checkPartyTrainingClassStatus(this, partyTrainingClass, partyPK);

                    if(!hasExecutionErrors()) {
                        trainingClass = partyTrainingClass.getLastDetail().getTrainingClass();
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyTrainingClassName.name(), partyTrainingClassName);
                }
            }

            if(!hasExecutionErrors()) {
                result.setTrainingClass(trainingControl.getTrainingClassTransfer(getUserVisit(), trainingClass));

                sendEventUsingNames(trainingClass.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return result;
    }
    
}
