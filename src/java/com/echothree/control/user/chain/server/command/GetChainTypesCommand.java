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

package com.echothree.control.user.chain.server.command;

import com.echothree.control.user.chain.common.form.GetChainTypesForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.chain.server.logic.ChainKindLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.chain.server.factory.ChainTypeFactory;
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
public class GetChainTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<ChainType, GetChainTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ChainType.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    @Inject
    ChainControl chainControl;

    @Inject
    ChainKindLogic chainKindLogic;

    /** Creates a new instance of GetChainTypesCommand */
    public GetChainTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    ChainKind chainKind;

    @Override
    protected void handleForm() {
        chainKind = chainKindLogic.getChainKindByName(this, form.getChainKindName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : chainControl.countChainTypesByChainKind(chainKind);
    }

    @Override
    protected Collection<ChainType> getEntities() {
        return hasExecutionErrors() ? null : chainControl.getChainTypesByChainKind(chainKind);
    }

    @Override
    protected BaseResult getResult(Collection<ChainType> entities) {
        var result = ChainResultFactory.getGetChainTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setChainKind(chainControl.getChainKindTransfer(userVisit, chainKind));

            if(session.hasLimit(ChainTypeFactory.class)) {
                result.setChainTypeCount(getTotalEntities());
            }

            result.setChainTypes(chainControl.getChainTypeTransfers(userVisit, entities));
        }

        return result;
    }
    
}
