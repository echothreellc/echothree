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

package com.echothree.control.user.letter.server.command;

import com.echothree.control.user.letter.common.form.GetQueuedLetterForm;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.model.control.chain.server.logic.ChainInstanceLogic;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.letter.server.entity.QueuedLetter;
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
public class GetQueuedLetterCommand
        extends BaseSingleEntityCommand<QueuedLetter, GetQueuedLetterForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.QueuedLetter.name(), SecurityRoles.Review.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ChainInstanceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("QueuedLetterSequence", FieldType.UNSIGNED_INTEGER, true, null, null)
        );
    }

    @Inject
    LetterControl letterControl;

    @Inject
    ChainInstanceLogic chainInstanceLogic;

    /** Creates a new instance of GetQueuedLetterCommand */
    public GetQueuedLetterCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected QueuedLetter getEntity() {
        QueuedLetter queuedLetter = null;
        var chainInstanceName = form.getChainInstanceName();
        var chainInstance = chainInstanceLogic.getChainInstanceByName(this, chainInstanceName);

        if(!hasExecutionErrors()) {
            var strQueuedLetterSequence = form.getQueuedLetterSequence();
            var queuedLetterSequence = Integer.valueOf(strQueuedLetterSequence);

            queuedLetter = letterControl.getQueuedLetter(chainInstance, queuedLetterSequence);

            if(queuedLetter == null) {
                addExecutionError(ExecutionErrors.UnknownQueuedLetter.name(), chainInstanceName, strQueuedLetterSequence);
            }
        }

        return queuedLetter;
    }

    @Override
    protected BaseResult getResult(QueuedLetter queuedLetter) {
        var result = LetterResultFactory.getGetQueuedLetterResult();

        if(queuedLetter != null) {
            result.setQueuedLetter(letterControl.getQueuedLetterTransfer(getUserVisit(), queuedLetter));
        }

        return result;
    }
    
}
