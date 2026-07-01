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

import com.echothree.control.user.letter.common.form.GetQueuedLettersForm;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.model.control.chain.server.logic.ChainKindLogic;
import com.echothree.model.control.chain.server.logic.ChainTypeLogic;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.letter.server.entity.QueuedLetter;
import com.echothree.model.data.letter.server.factory.LetterFactory;
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
public class GetQueuedLettersCommand
        extends BasePaginatedMultipleEntitiesCommand<QueuedLetter, GetQueuedLettersForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.QueuedLetter.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LetterName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    LetterControl letterControl;

    @Inject
    ChainKindLogic chainKindLogic;

    @Inject
    ChainTypeLogic chainTypeLogic;

    /** Creates a new instance of GetQueuedLettersCommand */
    public GetQueuedLettersCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private Letter letter;

    @Override
    protected void handleForm() {
        var chainKindName = form.getChainKindName();
        var chainTypeName = form.getChainTypeName();
        var letterName = form.getLetterName();
        var parameterCount = (chainKindName != null ? 1 : 0) + (chainTypeName != null ? 1 : 0) + (letterName != null ? 1 : 0);

        if(parameterCount == 0 || parameterCount == 3) {
            ChainKind chainKind = null;

            if(chainKindName != null) {
                chainKind = chainKindLogic.getChainKindByName(this, chainKindName);
            }

            if(!hasExecutionErrors()) {
                ChainType chainType = null;

                if(chainTypeName != null) {
                    chainType = chainTypeLogic.getChainTypeByName(this, chainKind, chainTypeName);
                }

                if(!hasExecutionErrors()) {
                    if(letterName != null) {
                        letter = letterControl.getLetterByName(chainType, letterName);

                        if(letter == null) {
                            addExecutionError(ExecutionErrors.UnknownLetterName.name(), chainKindName, chainTypeName, letterName);
                        }
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : letter == null ? letterControl.countQueuedLetters() : letterControl.countQueuedLettersByLetter(letter);
    }

    @Override
    protected Collection<QueuedLetter> getEntities() {
        return hasExecutionErrors() ? null : letter == null ? letterControl.getQueuedLetters() : letterControl.getQueuedLettersByLetter(letter);
    }

    @Override
    protected BaseResult getResult(Collection<QueuedLetter> entities) {
        var result = LetterResultFactory.getGetQueuedLettersResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(letter != null) {
                result.setLetter(letterControl.getLetterTransfer(userVisit, letter));
            }

            if(session.hasLimit(LetterFactory.class)) {
                result.setQueuedLetterCount(getTotalEntities());
            }

            result.setQueuedLetters(letterControl.getQueuedLetterTransfers(userVisit, entities));
        }

        return result;
    }
    
}
