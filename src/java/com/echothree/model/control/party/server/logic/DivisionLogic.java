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

package com.echothree.model.control.party.server.logic;

import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.common.exception.CannotSpecifyDivisionNameAndPartyNameException;
import com.echothree.model.control.party.common.exception.MustSpecifyDivisionNameOrPartyNameException;
import com.echothree.model.control.party.common.exception.UnknownDivisionNameException;
import com.echothree.model.control.party.common.exception.UnknownPartyNameException;
import com.echothree.model.control.party.common.exception.UseOfDivisionNameRequiresCompanyNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class DivisionLogic
        extends BaseLogic {
    
    private DivisionLogic() {
        super();
    }
    
    private static class DivisionLogicHolder {
        static DivisionLogic instance = new DivisionLogic();
    }
    
    public static DivisionLogic getInstance() {
        return DivisionLogicHolder.instance;
    }

    public PartyDivision getPartyDivisionByName(final ExecutionErrorAccumulator eea, final Party companyParty, final String divisionName,
            final String partyName, boolean required) {
        int parameterCount = (divisionName == null? 0: 1) + (partyName == null? 0: 1);
        PartyDivision partyDivision = null;

        if(companyParty != null) {
            PartyLogic.getInstance().checkPartyType(eea, companyParty, PartyTypes.COMPANY.name());
        }

        if(!hasExecutionErrors(eea)) {
            if(parameterCount == 1) {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);

                if(divisionName != null) {
                    if(companyParty == null) {
                        handleExecutionError(UseOfDivisionNameRequiresCompanyNameException.class, eea, ExecutionErrors.UseOfDivisionNameRequiresCompanyName.name());
                    } else {
                        partyDivision = partyControl.getPartyDivisionByName(companyParty, divisionName);

                        if(partyDivision == null) {
                            handleExecutionError(UnknownDivisionNameException.class, eea, ExecutionErrors.UnknownDivisionName.name(), divisionName);
                        }
                    }
                } else {
                    Party party = partyControl.getPartyByName(partyName);

                    if(party != null) {
                        PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.DIVISION.name());

                        partyDivision = partyControl.getPartyDivision(party);
                    } else {
                        handleExecutionError(UnknownPartyNameException.class, eea, ExecutionErrors.UnknownPartyName.name(), partyName);
                    }
                }
            } else {
                if(parameterCount == 2) {
                    handleExecutionError(CannotSpecifyDivisionNameAndPartyNameException.class, eea, ExecutionErrors.CannotSpecifyDivisionNameAndPartyName.name());
                } else if(required) {
                    handleExecutionError(MustSpecifyDivisionNameOrPartyNameException.class, eea, ExecutionErrors.MustSpecifyDivisionNameOrPartyName.name());
                }
            }
        }

        return partyDivision;
    }

}
