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

package com.echothree.model.control.party.server.logic;

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
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
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class DivisionLogic
        extends BaseLogic {

    protected DivisionLogic() {
        super();
    }

    public static DivisionLogic getInstance() {
        return CDI.current().select(DivisionLogic.class).get();
    }

    public PartyDivision getPartyDivisionByName(final ExecutionErrorAccumulator eea, final String companyName,
            final String divisionName, final String partyName, final UniversalEntitySpec universalEntitySpec,
            final boolean required) {
        PartyDivision partyDivision = null;
        Party companyParty = null;

        if(companyName != null) {
            var partyCompany = CompanyLogic.getInstance().getPartyCompanyByName(eea, companyName, null,
                    null, true);

            companyParty = hasExecutionErrors(eea) ? null : partyCompany.getParty();
        }

        if(!hasExecutionErrors(eea)) {
            partyDivision = getPartyDivisionByName(eea, companyParty, divisionName, partyName, universalEntitySpec, required);
        }

        return partyDivision;
    }

    public PartyDivision getPartyDivisionByName(final ExecutionErrorAccumulator eea, final Party companyParty,
            final String divisionName, final String partyName, final UniversalEntitySpec universalEntitySpec,
            final boolean required) {
        var parameterCount = (divisionName == null ? 0 : 1) + (partyName == null ? 0 : 1) +
                EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalEntitySpec);
        PartyDivision partyDivision = null;

        if(companyParty != null) {
            PartyLogic.getInstance().checkPartyType(eea, companyParty, PartyTypes.COMPANY.name());
        }

        if(!hasExecutionErrors(eea)) {
            if(parameterCount == 1) {
                var partyControl = Session.getModelController(PartyControl.class);

                if(divisionName != null) {
                    // Use of divisionName requires a companyParty.
                    if(companyParty == null) {
                        handleExecutionError(UseOfDivisionNameRequiresCompanyNameException.class, eea, ExecutionErrors.UseOfDivisionNameRequiresCompanyName.name());
                    } else {
                        partyDivision = partyControl.getPartyDivisionByName(companyParty, divisionName);

                        if(partyDivision == null) {
                            handleExecutionError(UnknownDivisionNameException.class, eea, ExecutionErrors.UnknownDivisionName.name(), divisionName);
                        }
                    }
                } else {
                    // Use of partyName or universalEntitySpec cannot include a companyParty.
                    if(companyParty == null) {
                        if(partyName != null) {
                            var party = partyControl.getPartyByName(partyName);

                            if(party != null) {
                                PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.DIVISION.name());

                                partyDivision = partyControl.getPartyDivision(party);
                            } else {
                                handleExecutionError(UnknownPartyNameException.class, eea, ExecutionErrors.UnknownPartyName.name(), partyName);
                            }
                        } else if(universalEntitySpec != null) {
                            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalEntitySpec,
                                    ComponentVendors.ECHO_THREE.name(), EntityTypes.Party.name());

                            if(!eea.hasExecutionErrors()) {
                                var party = partyControl.getPartyByEntityInstance(entityInstance);

                                PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.DIVISION.name());

                                partyDivision = partyControl.getPartyDivision(party);
                            }
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                }
            } else {
                if(required) {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
        }

        return partyDivision;
    }

}
