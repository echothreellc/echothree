// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.GetPartyAliasForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.server.command.util.PartyAliasUtil;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyAliasTypeLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.PartyAlias;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class GetPartyAliasCommand
        extends BaseSingleEntityCommand<PartyAlias, GetPartyAliasForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PartyAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetPartyAliasCommand */
    public GetPartyAliasCommand(UserVisitPK userVisitPK, GetPartyAliasForm form) {
        super(userVisitPK, form, new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(PartyAliasUtil.getInstance().getSecurityRoleGroupNameByPartySpec(form), SecurityRoles.Review.name())
                ))
        )), FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected PartyAlias getEntity() {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyName = form.getPartyName();
        var party = PartyLogic.getInstance().getPartyByName(this, partyName);
        PartyAlias partyAlias = null;

        if(party != null) {
            var partyType = party.getLastDetail().getPartyType();
            var partyAliasTypeName = form.getPartyAliasTypeName();
            var partyAliasType = PartyAliasTypeLogic.getInstance().getPartyAliasTypeByName(this, partyType, partyAliasTypeName);

            if(partyAliasType != null) {
                partyAlias = partyControl.getPartyAlias(party, partyAliasType);

                if(partyAlias == null) {
                    addExecutionError(ExecutionErrors.UnknownPartyAlias.name(), partyName, partyAliasTypeName);
                }
            }
        }

        return partyAlias;
    }

    @Override
    protected BaseResult getTransfer(PartyAlias partyAlias) {
        var result = PartyResultFactory.getGetPartyAliasResult();

        if(partyAlias != null) {
            var partyControl = Session.getModelController(PartyControl.class);

            result.setPartyAlias(partyControl.getPartyAliasTransfer(getUserVisit(), partyAlias));
        }

        return result;
    }

}
