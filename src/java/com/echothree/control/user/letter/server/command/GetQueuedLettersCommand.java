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

package com.echothree.control.user.letter.server.command;

import com.echothree.control.user.letter.common.form.GetQueuedLettersForm;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetQueuedLettersCommand
        extends BaseSimpleCommand<GetQueuedLettersForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
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
    public GetQueuedLettersCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var chainControl = Session.getModelController(ChainControl.class);
        var result = LetterResultFactory.getGetQueuedLettersResult();
        var chainKindName = form.getChainKindName();
        var chainTypeName = form.getChainTypeName();
        var letterName = form.getLetterName();
        var parameterCount = (chainKindName != null? 1: 0) + (chainTypeName != null && letterName != null? 1: 0);

        if(parameterCount == 0 || parameterCount == 3) {
            var chainKind = chainKindName == null ? null : chainControl.getChainKindByName(chainKindName);

            if(chainKindName == null || chainKind != null) {
                var chainType = chainTypeName == null ? null : chainControl.getChainTypeByName(chainKind, chainTypeName);

                if(chainTypeName == null || chainType != null) {
                    var letterControl = Session.getModelController(LetterControl.class);
                    var letter = letterName == null ? null : letterControl.getLetterByName(chainType, letterName);

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
