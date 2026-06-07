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

import com.echothree.control.user.letter.common.form.GetLettersForm;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.chain.server.logic.ChainTypeLogic;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.letter.server.factory.LetterFactory;
import com.echothree.util.common.command.BaseResult;
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
public class GetLettersCommand
        extends BasePaginatedMultipleEntitiesCommand<Letter, GetLettersForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Letter.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    ChainControl chainControl;

    @Inject
    LetterControl letterControl;

    @Inject
    ChainTypeLogic chainTypeLogic;

    /** Creates a new instance of GetLettersCommand */
    public GetLettersCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    ChainType chainType;

    @Override
    protected void handleForm() {
        var chainKindName = form.getChainKindName();
        var chainTypeName = form.getChainTypeName();

        chainType = chainTypeLogic.getChainTypeByName(this, chainKindName, chainTypeName);
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : letterControl.countLettersByChainType(chainType);
    }

    @Override
    protected Collection<Letter> getEntities() {
        return hasExecutionErrors() ? null : letterControl.getLettersByChainType(chainType);
    }

    @Override
    protected BaseResult getResult(Collection<Letter> entities) {
        var result = LetterResultFactory.getGetLettersResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setChainType(chainControl.getChainTypeTransfer(userVisit, chainType));

            if(session.hasLimit(LetterFactory.class)) {
                result.setLetterCount(getTotalEntities());
            }

            result.setLetters(letterControl.getLetterTransfers(userVisit, entities));
        }

        return result;
    }
    
}
