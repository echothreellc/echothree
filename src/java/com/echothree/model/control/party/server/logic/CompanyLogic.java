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

package com.echothree.model.control.party.server.logic;

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.common.exception.UnknownCompanyNameException;
import com.echothree.model.control.party.common.exception.UnknownPartyNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class CompanyLogic
        extends BaseLogic {

    protected CompanyLogic() {
        super();
    }

    public static CompanyLogic getInstance() {
        return CDI.current().select(CompanyLogic.class).get();
    }

    public PartyCompany getPartyCompanyByName(final ExecutionErrorAccumulator eea, final String companyName,
            final String partyName, final UniversalEntitySpec universalEntitySpec, final boolean required) {
        var parameterCount = (companyName == null ? 0 : 1) + (partyName == null ? 0 : 1) +
                EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalEntitySpec);
        PartyCompany partyCompany = null;

        if(parameterCount == 1) {
            var partyControl = Session.getModelController(PartyControl.class);

            if(companyName != null) {
                partyCompany = partyControl.getPartyCompanyByName(companyName);

                if(partyCompany == null) {
                    handleExecutionError(UnknownCompanyNameException.class, eea, ExecutionErrors.UnknownCompanyName.name(), companyName);
                }
            } else if(partyName != null) {
                var party = partyControl.getPartyByName(partyName);

                if(party != null) {
                    PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.COMPANY.name());

                    partyCompany = partyControl.getPartyCompany(party);
                } else {
                    handleExecutionError(UnknownPartyNameException.class, eea, ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else if(universalEntitySpec != null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalEntitySpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.Party.name());

                if(!eea.hasExecutionErrors()) {
                    var party = partyControl.getPartyByEntityInstance(entityInstance);

                    PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.COMPANY.name());

                    partyCompany = partyControl.getPartyCompany(party);
                }
            }
        } else {
            if(required) {
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            }
        }

        return partyCompany;
    }

}
