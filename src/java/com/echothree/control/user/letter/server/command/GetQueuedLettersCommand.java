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

package com.echothree.control.user.letter.server.command;

import com.echothree.control.user.letter.common.form.GetQueuedLettersForm;
import com.echothree.control.user.letter.common.result.GetQueuedLettersResult;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.letter.server.LetterControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.letter.server.entity.Letter;
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

public class GetQueuedLettersCommand
        extends BaseSimpleCommand<GetQueuedLettersForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.QueuedLetter.name(), SecurityRoles.List.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LetterName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetQueuedLettersCommand */
    public GetQueuedLettersCommand(UserVisitPK userVisitPK, GetQueuedLettersForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        ChainControl chainControl = (ChainControl)Session.getModelController(ChainControl.class);
        GetQueuedLettersResult result = LetterResultFactory.getGetQueuedLettersResult();
        String chainKindName = form.getChainKindName();
        String chainTypeName = form.getChainTypeName();
        String letterName = form.getLetterName();
        int parameterCount = (chainKindName != null? 1: 0) + (chainTypeName != null && letterName != null? 1: 0);

        if(parameterCount == 0 || parameterCount == 3) {
            ChainKind chainKind = chainKindName == null ? null : chainControl.getChainKindByName(chainKindName);

            if(chainKindName == null || chainKind != null) {
                ChainType chainType = chainTypeName == null ? null : chainControl.getChainTypeByName(chainKind, chainTypeName);

                if(chainTypeName == null || chainType != null) {
                    LetterControl letterControl = (LetterControl)Session.getModelController(LetterControl.class);
                    Letter letter = letterName == null ? null : letterControl.getLetterByName(chainType, letterName);

                    if(letterName == null || letter != null) {
                        if(letter == null) {
                            result.setQueuedLetters(letterControl.getQueuedLetterTransfers(getUserVisit()));
                        } else {
                            result.setLetter(letterControl.getLetterTransfer(getUserVisit(), letter));
                            result.setQueuedLetters(letterControl.getQueuedLetterTransfersByLetter(getUserVisit(), letter));
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLetterName.name(), chainKindName, chainTypeName, letterName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownChainTypeName.name(), chainKindName, chainTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
