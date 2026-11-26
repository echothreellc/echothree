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
import com.echothree.control.user.party.common.spec.CompanyUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.common.exception.UnknownCompanyNameException;
import com.echothree.model.control.party.common.exception.UnknownDefaultCompanyException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CompanyLogic
        extends BaseLogic {

    @Inject
    PartyControl partyControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    PartyLogic partyLogic;

    protected CompanyLogic() {
        super();
    }

    public PartyCompany getPartyCompanyByName(final ExecutionErrorAccumulator eea, final String companyName,
            final String partyName, final UniversalEntitySpec universalEntitySpec, final EntityPermission entityPermission) {
        var parameterCount = (companyName == null ? 0 : 1) + (partyName == null ? 0 : 1)
                + entityInstanceLogic.countPossibleEntitySpecs(universalEntitySpec);
        PartyCompany partyCompany = null;

        if(parameterCount == 1) {
            if(companyName != null) {
                partyCompany = partyControl.getPartyCompanyByName(companyName, entityPermission);

                if(partyCompany == null) {
                    handleExecutionError(UnknownCompanyNameException.class, eea, ExecutionErrors.UnknownCompanyName.name(), companyName);
                }
            } else {
                var party = partyName != null ? partyLogic.getPartyByName(eea, partyName, PartyTypes.COMPANY.name()) : null;

                if(partyName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalEntitySpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Party.name());

                    if(!hasExecutionErrors(eea)) {
                        party = partyControl.getPartyByEntityInstance(entityInstance);
                        partyLogic.checkPartyType(eea, party, PartyTypes.COMPANY.name());
                    }
                }

                if(!hasExecutionErrors(eea)) {
                    partyCompany = partyControl.getPartyCompany(party, entityPermission);
                }
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return partyCompany;
    }

    public PartyCompany getPartyCompanyByName(final ExecutionErrorAccumulator eea, final String companyName,
            final String partyName, final UniversalEntitySpec universalEntitySpec) {
        return getPartyCompanyByName(eea, companyName, partyName, universalEntitySpec, EntityPermission.READ_ONLY);
    }

    public PartyCompany getPartyCompanyByUniversalSpec(final ExecutionErrorAccumulator eea, final CompanyUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        PartyCompany partyCompany;
        var parameterCount = (universalSpec.getCompanyName() == null ? 0 : 1) + (universalSpec.getPartyName() == null ? 0 : 1)
                + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        if(parameterCount == 0 && allowDefault) {
            partyCompany = partyControl.getDefaultPartyCompany(entityPermission);

            if(partyCompany == null) {
                handleExecutionError(UnknownDefaultCompanyException.class, eea, ExecutionErrors.UnknownDefaultCompany.name());
            }
        } else {
            partyCompany = getPartyCompanyByName(eea, universalSpec.getCompanyName(), universalSpec.getPartyName(), universalSpec, entityPermission);
        }

        return partyCompany;
    }

    public PartyCompany getPartyCompanyByUniversalSpec(final ExecutionErrorAccumulator eea, final CompanyUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        return getPartyCompanyByUniversalSpec(eea, universalSpec, false, entityPermission);
    }

    public PartyCompany getPartyCompanyByUniversalSpec(final ExecutionErrorAccumulator eea, final CompanyUniversalSpec universalSpec,
            final boolean allowDefault) {
        return getPartyCompanyByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public PartyCompany getPartyCompanyByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final CompanyUniversalSpec universalSpec,
            final boolean allowDefault) {
        return getPartyCompanyByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public PartyCompany getPartyCompanyByUniversalSpec(final ExecutionErrorAccumulator eea, final CompanyUniversalSpec universalSpec) {
        return getPartyCompanyByUniversalSpec(eea, universalSpec, false);
    }

    public PartyCompany getPartyCompanyByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final CompanyUniversalSpec universalSpec) {
        return getPartyCompanyByUniversalSpecForUpdate(eea, universalSpec, false);
    }

}
