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

import com.echothree.control.user.training.common.form.CreatePartyTrainingClassForm;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.control.training.server.logic.PartyTrainingClassLogic;
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
import javax.enterprise.context.Dependent;

@Dependent
public class CreatePartyTrainingClassCommand
        extends BaseSimpleCommand<CreatePartyTrainingClassForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyTrainingClass.name(), SecurityRoles.Create.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CompletedTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("ValidUntilTime", FieldType.DATE_TIME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreatePartyTrainingClassCommand */
    public CreatePartyTrainingClassCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyName = form.getPartyName();
        var party = partyControl.getPartyByName(partyName);

        if(party != null) {
            var trainingControl = Session.getModelController(TrainingControl.class);
            var trainingClassName = form.getTrainingClassName();
            var trainingClass = trainingControl.getTrainingClassByName(trainingClassName);

            if(trainingClass != null) {
                var strCompletedTime = form.getCompletedTime();
                var completedTime = strCompletedTime == null ? null : Long.valueOf(strCompletedTime);

                if(completedTime == null || completedTime < session.getStartTime()) {
                    var strValidUntilTime = form.getValidUntilTime();
                    var validUntilTime = strValidUntilTime == null ? null : Long.valueOf(strValidUntilTime);

                    if(validUntilTime == null || validUntilTime > session.getStartTime()) {
                        var partyTrainingClassLogic = PartyTrainingClassLogic.getInstance();
                        var preparedPartyTrainingClass = partyTrainingClassLogic.preparePartyTrainingClass(this, party, trainingClass,
                                completedTime, validUntilTime);

                        if(!hasExecutionErrors()) {
                            partyTrainingClassLogic.createPartyTrainingClass(session, preparedPartyTrainingClass, getPartyPK());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.InvalidValidUntilTime.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidCompletedTime.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTrainingClassName.name(), trainingClassName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return null;
    }

}
