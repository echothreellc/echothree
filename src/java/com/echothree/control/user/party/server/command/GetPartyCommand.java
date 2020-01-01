// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.party.common.form.GetPartyForm;
import com.echothree.control.user.party.common.result.GetPartyResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.user.server.logic.UserLoginLogic;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetPartyCommand
        extends BaseSimpleCommand<GetPartyForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Username", FieldType.STRING, false, 1L, 80L)
                ));
    }

    /** Creates a new instance of GetPartyCommand */
    public GetPartyCommand(UserVisitPK userVisitPK, GetPartyForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected BaseResult execute() {
        GetPartyResult result = PartyResultFactory.getGetPartyResult();
        String partyName = form.getPartyName();
        String username = form.getUsername();
        int parameterCount = (partyName == null ? 0 : 1) + (username == null ? 0 : 1);

        if(parameterCount == 1) {
            Party party = null;

            if(partyName != null) {
                party = PartyLogic.getInstance().getPartyByName(this, partyName);
            }

            if(username != null) {
                UserLogin userLogin = UserLoginLogic.getInstance().getUserLoginByUsername(this, username);

                if(!hasExecutionErrors()) {
                    party = userLogin.getParty();
                }
            }

            if(!hasExecutionErrors()) {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                
                result.setParty(partyControl.getPartyTransfer(getUserVisit(), party));

                sendEventUsingNames(party.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }

}
