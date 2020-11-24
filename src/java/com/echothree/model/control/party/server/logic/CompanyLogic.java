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
import com.echothree.model.control.party.common.exception.CannotSpecifyCompanyNameAndPartyNameException;
import com.echothree.model.control.party.common.exception.MustSpecifyCompanyNameOrPartyNameException;
import com.echothree.model.control.party.common.exception.UnknownCompanyNameException;
import com.echothree.model.control.party.common.exception.UnknownPartyNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class CompanyLogic
        extends BaseLogic {
    
    private CompanyLogic() {
        super();
    }
    
    private static class CompanyLogicHolder {
        static CompanyLogic instance = new CompanyLogic();
    }
    
    public static CompanyLogic getInstance() {
        return CompanyLogicHolder.instance;
    }

    public PartyCompany getPartyCompanyByName(final ExecutionErrorAccumulator eea, final String companyName, final String partyName, boolean required) {
        int parameterCount = (companyName == null? 0: 1) + (partyName == null? 0: 1);
        PartyCompany partyCompany = null;

        if(parameterCount == 1) {
            var partyControl = Session.getModelController(PartyControl.class);

            if(companyName != null) {
                partyCompany = partyControl.getPartyCompanyByName(companyName);

                if(partyCompany == null) {
                    handleExecutionError(UnknownCompanyNameException.class, eea, ExecutionErrors.UnknownCompanyName.name(), companyName);
                }
            } else {
                Party party = partyControl.getPartyByName(partyName);

                if(party != null) {
                    PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.COMPANY.name());

                    partyCompany = partyControl.getPartyCompany(party);
                } else {
                    handleExecutionError(UnknownPartyNameException.class, eea, ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            }
        } else {
            if(parameterCount == 2) {
                handleExecutionError(CannotSpecifyCompanyNameAndPartyNameException.class, eea, ExecutionErrors.CannotSpecifyCompanyNameAndPartyName.name());
            } else if(required) {
                handleExecutionError(MustSpecifyCompanyNameOrPartyNameException.class, eea, ExecutionErrors.MustSpecifyCompanyNameOrPartyName.name());
            }
        }

        return partyCompany;
    }

}
