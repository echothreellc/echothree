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
import com.echothree.control.user.party.common.spec.DepartmentUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.common.exception.UnknownDepartmentNameException;
import com.echothree.model.control.party.common.exception.UseOfDepartmentNameRequiresDivisionNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDepartment;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DepartmentLogic
        extends BaseLogic {

    @Inject
    PartyControl partyControl;

    @Inject
    DivisionLogic divisionLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    PartyLogic partyLogic;

    protected DepartmentLogic() {
        super();
    }

    public PartyDepartment getPartyDepartmentByName(final ExecutionErrorAccumulator eea, final String companyName,
            final String divisionName, final String departmentName, final String partyName,
            final UniversalEntitySpec universalEntitySpec, final boolean required, final EntityPermission entityPermission) {
        PartyDepartment partyDepartment = null;
        Party divisionParty = null;

        if(companyName != null || divisionName != null) {
            var partyDivision = divisionLogic.getPartyDivisionByName(eea, companyName, divisionName, null, null, true);

            if(!hasExecutionErrors(eea)) {
                divisionParty = partyDivision.getParty();
            }
        }

        if(!hasExecutionErrors(eea)) {
            partyDepartment = getPartyDepartmentByName(eea, divisionParty, departmentName, partyName, universalEntitySpec, required, entityPermission);
        }

        return partyDepartment;
    }

    public PartyDepartment getPartyDepartmentByName(final ExecutionErrorAccumulator eea, final String companyName,
            final String divisionName, final String departmentName, final String partyName,
            final UniversalEntitySpec universalEntitySpec, final boolean required) {
        return getPartyDepartmentByName(eea, companyName, divisionName, departmentName, partyName, universalEntitySpec, required, EntityPermission.READ_ONLY);
    }

    public PartyDepartment getPartyDepartmentByName(final ExecutionErrorAccumulator eea, final Party divisionParty,
            final String departmentName, final String partyName, final UniversalEntitySpec universalEntitySpec,
            final boolean required, final EntityPermission entityPermission) {
        var parameterCount = (departmentName == null ? 0 : 1) + (partyName == null ? 0 : 1)
                + entityInstanceLogic.countPossibleEntitySpecs(universalEntitySpec);
        PartyDepartment partyDepartment = null;

        if(divisionParty != null) {
            partyLogic.checkPartyType(eea, divisionParty, PartyTypes.DIVISION.name());
        }

        if(!hasExecutionErrors(eea)) {
            if(parameterCount == 1) {
                if(departmentName != null) {
                    if(divisionParty == null) {
                        handleExecutionError(UseOfDepartmentNameRequiresDivisionNameException.class, eea, ExecutionErrors.UseOfDepartmentNameRequiresDivisionName.name());
                    } else {
                        partyDepartment = partyControl.getPartyDepartmentByName(divisionParty, departmentName, entityPermission);

                        if(partyDepartment == null) {
                            var partyDivision = partyControl.getPartyDivision(divisionParty);
                            var partyCompany = partyControl.getPartyCompany(partyDivision.getCompanyParty());

                            handleExecutionError(UnknownDepartmentNameException.class, eea, ExecutionErrors.UnknownDepartmentName.name(),
                                    partyCompany.getPartyCompanyName(), partyDivision.getPartyDivisionName(), departmentName);
                        }
                    }
                } else if(divisionParty == null) {
                    var party = partyName != null ? partyLogic.getPartyByName(eea, partyName, PartyTypes.DEPARTMENT.name()) : null;

                    if(partyName == null) {
                        var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalEntitySpec,
                                ComponentVendors.ECHO_THREE.name(), EntityTypes.Party.name());

                        if(!hasExecutionErrors(eea)) {
                            party = partyControl.getPartyByEntityInstance(entityInstance);
                            partyLogic.checkPartyType(eea, party, PartyTypes.DEPARTMENT.name());
                        }
                    }

                    if(!hasExecutionErrors(eea)) {
                        partyDepartment = partyControl.getPartyDepartment(party, entityPermission);
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else if(required) {
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            }
        }

        return partyDepartment;
    }

    public PartyDepartment getPartyDepartmentByName(final ExecutionErrorAccumulator eea, final Party divisionParty,
            final String departmentName, final String partyName, final UniversalEntitySpec universalEntitySpec, final boolean required) {
        return getPartyDepartmentByName(eea, divisionParty, departmentName, partyName, universalEntitySpec, required, EntityPermission.READ_ONLY);
    }

    public PartyDepartment getPartyDepartmentByUniversalSpec(final ExecutionErrorAccumulator eea, final DepartmentUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        var companyName = universalSpec.getCompanyName();
        var divisionName = universalSpec.getDivisionName();
        var departmentName = universalSpec.getDepartmentName();
        var partyName = universalSpec.getPartyName();
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        PartyDepartment partyDepartment = null;

        if(companyName != null && divisionName != null && departmentName != null && partyName == null && possibleEntitySpecs == 0) {
            partyDepartment = getPartyDepartmentByName(eea, companyName, divisionName, departmentName, null, null, true, entityPermission);
        } else if(companyName == null && divisionName == null && departmentName == null
                && (partyName == null ? 0 : 1) + possibleEntitySpecs == 1) {
            partyDepartment = getPartyDepartmentByName(eea, (Party)null, null, partyName, universalSpec, true, entityPermission);
        } else if(companyName == null && divisionName == null && departmentName != null && partyName == null && possibleEntitySpecs == 0) {
            handleExecutionError(UseOfDepartmentNameRequiresDivisionNameException.class, eea, ExecutionErrors.UseOfDepartmentNameRequiresDivisionName.name());
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return partyDepartment;
    }

    public PartyDepartment getPartyDepartmentByUniversalSpec(final ExecutionErrorAccumulator eea, final DepartmentUniversalSpec universalSpec) {
        return getPartyDepartmentByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public PartyDepartment getPartyDepartmentByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final DepartmentUniversalSpec universalSpec) {
        return getPartyDepartmentByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
