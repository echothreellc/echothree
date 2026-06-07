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

import com.echothree.control.user.letter.common.form.GetLetterContactMechanismPurposesForm;
import com.echothree.control.user.letter.common.result.LetterResultFactory;
import com.echothree.model.control.chain.server.logic.ChainTypeLogic;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.letter.server.entity.LetterContactMechanismPurpose;
import com.echothree.model.data.letter.server.factory.LetterContactMechanismPurposeFactory;
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
public class GetLetterContactMechanismPurposesCommand
        extends BasePaginatedMultipleEntitiesCommand<LetterContactMechanismPurpose, GetLetterContactMechanismPurposesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.LetterContactMechanismPurpose.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LetterName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    LetterControl letterControl;

    @Inject
    ChainTypeLogic chainTypeLogic;

    /** Creates a new instance of GetLetterContactMechanismPurposesCommand */
    public GetLetterContactMechanismPurposesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private Letter letter;

    @Override
    protected void handleForm() {
        var chainKindName = form.getChainKindName();
        var chainTypeName = form.getChainTypeName();
        var chainType = chainTypeLogic.getChainTypeByName(this, chainKindName, chainTypeName);

        if(!hasExecutionErrors()) {
            var letterName = form.getLetterName();

            letter = letterControl.getLetterByName(chainType, letterName);

            if(letter == null) {
                addExecutionError(com.echothree.util.common.message.ExecutionErrors.UnknownLetterName.name(), letterName);
            }
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : letterControl.countLetterContactMechanismPurposesByLetter(letter);
    }

    @Override
    protected Collection<LetterContactMechanismPurpose> getEntities() {
        return hasExecutionErrors() ? null : letterControl.getLetterContactMechanismPurposesByLetter(letter);
    }

    @Override
    protected BaseResult getResult(Collection<LetterContactMechanismPurpose> entities) {
        var result = LetterResultFactory.getGetLetterContactMechanismPurposesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setLetter(letterControl.getLetterTransfer(userVisit, letter));

            if(session.hasLimit(LetterContactMechanismPurposeFactory.class)) {
                result.setLetterContactMechanismPurposeCount(getTotalEntities());
            }

            result.setLetterContactMechanismPurposes(letterControl.getLetterContactMechanismPurposeTransfers(userVisit, entities));
        }

        return result;
    }
    
}
