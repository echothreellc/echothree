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

import com.echothree.control.user.chain.common.form.GetChainEntityRoleTypesForm;
import com.echothree.control.user.chain.common.result.ChainResultFactory;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.chain.server.logic.ChainTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleType;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.chain.server.factory.ChainEntityRoleTypeFactory;
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
public class GetChainEntityRoleTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<ChainEntityRoleType, GetChainEntityRoleTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ChainEntityRoleType.name(), SecurityRoles.List.name())
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
    ChainTypeLogic chainTypeLogic;

    /** Creates a new instance of GetChainEntityRoleTypesCommand */
    public GetChainEntityRoleTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private ChainType chainType;

    @Override
    protected void handleForm() {
        chainType = chainTypeLogic.getChainTypeByName(this, form.getChainKindName(), form.getChainTypeName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : chainControl.countChainEntityRoleTypesByChainType(chainType);
    }

    @Override
    protected Collection<ChainEntityRoleType> getEntities() {
        return hasExecutionErrors() ? null : chainControl.getChainEntityRoleTypes(chainType);
    }

    @Override
    protected BaseResult getResult(Collection<ChainEntityRoleType> entities) {
        var result = ChainResultFactory.getGetChainEntityRoleTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setChainType(chainControl.getChainTypeTransfer(userVisit, chainType));

            if(session.hasLimit(ChainEntityRoleTypeFactory.class)) {
                result.setChainEntityRoleTypeCount(getTotalEntities());
            }

            result.setChainEntityRoleTypes(chainControl.getChainEntityRoleTypeTransfersByChainType(userVisit, chainType));
        }

        return result;
    }
    
}
