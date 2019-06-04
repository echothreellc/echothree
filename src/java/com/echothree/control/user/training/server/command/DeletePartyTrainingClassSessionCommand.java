// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.training.common.form.DeletePartyTrainingClassSessionForm;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.control.training.server.logic.PartyTrainingClassSessionLogic;
import com.echothree.model.data.training.server.entity.PartyTrainingClass;
import com.echothree.model.data.training.server.entity.PartyTrainingClassSession;
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

public class DeletePartyTrainingClassSessionCommand
        extends BaseSimpleCommand<DeletePartyTrainingClassSessionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyTrainingClassSession.name(), SecurityRoles.Delete.name())
                        )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTrainingClassName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyTrainingClassSessionSequence", FieldType.UNSIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeletePartyTrainingClassSessionCommand */
    public DeletePartyTrainingClassSessionCommand(UserVisitPK userVisitPK, DeletePartyTrainingClassSessionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
        String partyTrainingClassName = form.getPartyTrainingClassName();
        PartyTrainingClass partyTrainingClass = trainingControl.getPartyTrainingClassByNameForUpdate(partyTrainingClassName);

        if(partyTrainingClass != null) {
            Integer partyTrainingClassSessionSequence = Integer.valueOf(form.getPartyTrainingClassSessionSequence());
            PartyTrainingClassSession partyTrainingClassSession = trainingControl.getPartyTrainingClassSessionBySequenceForUpdate(partyTrainingClass, partyTrainingClassSessionSequence);

            if(partyTrainingClassSession != null) {
                PartyTrainingClassSessionLogic.getInstance().deletePartyTrainingClassSession(partyTrainingClassSession, getPartyPK());
            } else {
                addExecutionError(ExecutionErrors.UnknownPartyTrainingClassSessionSequence.name(), partyTrainingClassName, form.getPartyTrainingClassSessionSequence());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyTrainingClassName.name(), partyTrainingClassName);
        }

        return null;
    }
    
}
