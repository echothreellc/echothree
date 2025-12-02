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

package com.echothree.control.user.training.server.command;

import com.echothree.control.user.training.common.form.SetPartyTrainingClassStatusForm;
import com.echothree.control.user.training.common.result.SetPartyTrainingClassStatusResult;
import com.echothree.control.user.training.common.result.TrainingResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.control.TrainingControl;
import com.echothree.model.data.training.server.entity.PartyTrainingClass;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSetStatusCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class SetPartyTrainingClassStatusCommand
        extends BaseSetStatusCommand<SetPartyTrainingClassStatusForm, SetPartyTrainingClassStatusResult, PartyTrainingClass, PartyTrainingClass> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PartyTrainingClassStatus.name(), SecurityRoles.Choices.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyTrainingClassStatusChoice", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetPartyTrainingClassStatusCommand */
    public SetPartyTrainingClassStatusCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS);
    }
    
    @Override
    public SetPartyTrainingClassStatusResult getResult() {
        return TrainingResultFactory.getSetPartyTrainingClassStatusResult();
    }

    @Override
    public PartyTrainingClass getEntity() {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var partyTrainingClassName = form.getPartyTrainingClassName();
        var partyTrainingClass = trainingControl.getPartyTrainingClassByNameForUpdate(partyTrainingClassName);

        if(partyTrainingClass == null) {
            addExecutionError(ExecutionErrors.UnknownPartyTrainingClass.name(), partyTrainingClassName);
        }

        return partyTrainingClass;
    }

    @Override
    public PartyTrainingClass getLockEntity(PartyTrainingClass partyTrainingClass) {
        return partyTrainingClass;
    }

    @Override
    public void doUpdate(PartyTrainingClass partyTrainingClass) {
        var trainingControl = Session.getModelController(TrainingControl.class);
        var partyTrainingClassStatusChoice = form.getPartyTrainingClassStatusChoice();

        trainingControl.setPartyTrainingClassStatus(this, partyTrainingClass, partyTrainingClassStatusChoice, getPartyPK());
    }
    
}
