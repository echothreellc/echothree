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
import com.echothree.control.user.party.common.spec.DivisionUniversalSpec;
import com.echothree.control.user.party.common.spec.PartySpecFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.common.exception.UnknownDefaultDivisionException;
import com.echothree.model.control.party.common.exception.UnknownDivisionNameException;
import com.echothree.model.control.party.common.exception.UseOfDivisionNameRequiresCompanyNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DivisionLogic
        extends BaseLogic {

    @Inject
    PartyControl partyControl;

    @Inject
    CompanyLogic companyLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    PartyLogic partyLogic;

    protected DivisionLogic() {
        super();
    }

    public PartyDivision getPartyDivisionByName(final ExecutionErrorAccumulator eea, final String companyName,
            final String divisionName, final String partyName, final UniversalEntitySpec universalEntitySpec, final EntityPermission entityPermission) {
        PartyDivision partyDivision = null;
        Party companyParty = null;

        if(companyName != null) {
            var partyCompany = companyLogic.getPartyCompanyByName(eea, companyName, null, null);

            if(!hasExecutionErrors(eea)) {
                companyParty = partyCompany.getParty();
            }
        }

        if(!hasExecutionErrors(eea)) {
            partyDivision = getPartyDivisionByName(eea, companyParty, divisionName, partyName, universalEntitySpec, entityPermission);
        }

        return partyDivision;
    }

    public PartyDivision getPartyDivisionByName(final ExecutionErrorAccumulator eea, final String companyName,
            final String divisionName, final String partyName, final UniversalEntitySpec universalEntitySpec) {
        return getPartyDivisionByName(eea, companyName, divisionName, partyName, universalEntitySpec, EntityPermission.READ_ONLY);
    }

    public PartyDivision getPartyDivisionByName(final ExecutionErrorAccumulator eea, final Party companyParty,
            final String divisionName, final String partyName, final UniversalEntitySpec universalEntitySpec, final EntityPermission entityPermission) {
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalEntitySpec);
        var parameterCount = (divisionName == null ? 0 : 1) + (partyName == null ? 0 : 1) + possibleEntitySpecs;
        PartyDivision partyDivision = null;

        if(companyParty != null) {
            partyLogic.checkPartyType(eea, companyParty, PartyTypes.COMPANY.name());
        }

        if(!hasExecutionErrors(eea)) {
            if(parameterCount == 1) {
                if(divisionName != null) {
                    if(companyParty == null) {
                        handleExecutionError(UseOfDivisionNameRequiresCompanyNameException.class, eea, ExecutionErrors.UseOfDivisionNameRequiresCompanyName.name());
                    } else {
                        partyDivision = partyControl.getPartyDivisionByName(companyParty, divisionName, entityPermission);

                        if(partyDivision == null) {
                            var partyCompany = partyControl.getPartyCompany(companyParty);

                            handleExecutionError(UnknownDivisionNameException.class, eea, ExecutionErrors.UnknownDivisionName.name(),
                                    partyCompany.getPartyCompanyName(), divisionName);
                        }
                    }
                } else if(companyParty == null) {
                    var party = partyName != null ? partyLogic.getPartyByName(eea, partyName, PartyTypes.DIVISION.name()) : null;

                    if(partyName == null) {
                        var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalEntitySpec,
                                ComponentVendors.ECHO_THREE.name(), EntityTypes.Party.name());

                        if(!hasExecutionErrors(eea)) {
                            party = partyControl.getPartyByEntityInstance(entityInstance);
                            partyLogic.checkPartyType(eea, party, PartyTypes.DIVISION.name());
                        }
                    }

                    if(!hasExecutionErrors(eea)) {
                        partyDivision = partyControl.getPartyDivision(party, entityPermission);
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            }
        }

        return partyDivision;
    }

    public PartyDivision getPartyDivisionByName(final ExecutionErrorAccumulator eea, final Party companyParty,
            final String divisionName, final String partyName, final UniversalEntitySpec universalEntitySpec) {
        return getPartyDivisionByName(eea, companyParty, divisionName, partyName, universalEntitySpec, EntityPermission.READ_ONLY);
    }

    public PartyDivision getPartyDivisionByUniversalSpec(final ExecutionErrorAccumulator eea, final DivisionUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var companyName = universalSpec.getCompanyName();
        var divisionName = universalSpec.getDivisionName();
        var partyName = universalSpec.getPartyName();
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        PartyDivision partyDivision = null;

        if(partyName == null && possibleEntitySpecs == 0 && (allowDefault || (companyName != null && divisionName != null))) {
            var companySpec = PartySpecFactory.getCompanyUniversalSpec();

            companySpec.setCompanyName(companyName);

            var partyCompany = companyLogic.getPartyCompanyByUniversalSpec(eea, companySpec, allowDefault);

            if(!hasExecutionErrors(eea)) {
                var companyParty = partyCompany.getParty();

                if(divisionName == null) {
                    partyDivision = partyControl.getDefaultPartyDivision(companyParty, entityPermission);

                    if(partyDivision == null) {
                        handleExecutionError(UnknownDefaultDivisionException.class, eea, ExecutionErrors.UnknownDefaultDivision.name(),
                                partyCompany.getPartyCompanyName());
                    }
                } else {
                    partyDivision = getPartyDivisionByName(eea, companyParty, divisionName, null, null, entityPermission);
                }
            }
        } else if(companyName == null && divisionName == null && (partyName == null ? 0 : 1) + possibleEntitySpecs == 1) {
            partyDivision = getPartyDivisionByName(eea, (Party)null, null, partyName, universalSpec, entityPermission);
        } else if(companyName == null && divisionName != null && partyName == null && possibleEntitySpecs == 0) {
            handleExecutionError(UseOfDivisionNameRequiresCompanyNameException.class, eea, ExecutionErrors.UseOfDivisionNameRequiresCompanyName.name());
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return partyDivision;
    }

    public PartyDivision getPartyDivisionByUniversalSpec(final ExecutionErrorAccumulator eea, final DivisionUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        return getPartyDivisionByUniversalSpec(eea, universalSpec, false, entityPermission);
    }

    public PartyDivision getPartyDivisionByUniversalSpec(final ExecutionErrorAccumulator eea, final DivisionUniversalSpec universalSpec,
            final boolean allowDefault) {
        return getPartyDivisionByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public PartyDivision getPartyDivisionByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final DivisionUniversalSpec universalSpec,
            final boolean allowDefault) {
        return getPartyDivisionByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public PartyDivision getPartyDivisionByUniversalSpec(final ExecutionErrorAccumulator eea, final DivisionUniversalSpec universalSpec) {
        return getPartyDivisionByUniversalSpec(eea, universalSpec, false);
    }

    public PartyDivision getPartyDivisionByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final DivisionUniversalSpec universalSpec) {
        return getPartyDivisionByUniversalSpecForUpdate(eea, universalSpec, false);
    }

}
