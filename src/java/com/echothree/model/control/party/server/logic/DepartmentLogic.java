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
import com.echothree.model.control.party.common.exception.UnknownDepartmentNameException;
import com.echothree.model.control.party.common.exception.UnknownPartyNameException;
import com.echothree.model.control.party.common.exception.UseOfDepartmentNameRequiresDivisionNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDepartment;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class DepartmentLogic
        extends BaseLogic {

    protected DepartmentLogic() {
        super();
    }

    public static DepartmentLogic getInstance() {
        return CDI.current().select(DepartmentLogic.class).get();
    }

    public PartyDepartment getPartyDepartmentByName(final ExecutionErrorAccumulator eea, final String companyName,
            final String divisionName, final String departmentName, final String partyName,
            final UniversalEntitySpec universalEntitySpec, final boolean required) {
        PartyDepartment partyDepartment = null;
        Party divisionParty = null;

        if(companyName != null || divisionName != null) {
            var partyDivision = DivisionLogic.getInstance().getPartyDivisionByName(eea, companyName,
                    divisionName, null, null, true);

            divisionParty = hasExecutionErrors(eea) ? null : partyDivision.getParty();
        }

        if(!hasExecutionErrors(eea)) {
            partyDepartment = getPartyDepartmentByName(eea, divisionParty, departmentName, partyName, universalEntitySpec,
                    required);
        }

        return partyDepartment;
    }

    public PartyDepartment getPartyDepartmentByName(final ExecutionErrorAccumulator eea, final Party divisionParty,
            final String departmentName, final String partyName, final UniversalEntitySpec universalEntitySpec,
            final boolean required) {
        var parameterCount = (departmentName == null ? 0 : 1) + (partyName == null ? 0 : 1) +
                EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalEntitySpec);
        PartyDepartment partyDepartment = null;

        if(divisionParty != null) {
            PartyLogic.getInstance().checkPartyType(eea, divisionParty, PartyTypes.DIVISION.name());
        }

        if(!hasExecutionErrors(eea)) {
            if(parameterCount == 1) {
                var partyControl = Session.getModelController(PartyControl.class);

                if(departmentName != null) {
                    if(divisionParty == null) {
                        handleExecutionError(UseOfDepartmentNameRequiresDivisionNameException.class, eea, ExecutionErrors.UseOfDepartmentNameRequiresDivisionName.name());
                    } else {
                        partyDepartment = partyControl.getPartyDepartmentByName(divisionParty, departmentName);

                        if(partyDepartment == null) {
                            handleExecutionError(UnknownDepartmentNameException.class, eea, ExecutionErrors.UnknownDepartmentName.name(), departmentName);
                        }
                    }
                } else {
                    // Use of partyName or universalEntitySpec cannot include a divisionParty.
                    if(divisionParty == null) {
                        if(partyName != null) {
                            var party = partyControl.getPartyByName(partyName);

                            if(party != null) {
                                PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.DEPARTMENT.name());

                                partyDepartment = partyControl.getPartyDepartment(party);
                            } else {
                                handleExecutionError(UnknownPartyNameException.class, eea, ExecutionErrors.UnknownPartyName.name(), partyName);
                            }
                        } else if(universalEntitySpec != null) {
                            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalEntitySpec,
                                    ComponentVendors.ECHO_THREE.name(), EntityTypes.Party.name());

                            if(!eea.hasExecutionErrors()) {
                                var party = partyControl.getPartyByEntityInstance(entityInstance);

                                PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.DEPARTMENT.name());

                                partyDepartment = partyControl.getPartyDepartment(party);
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

        return partyDepartment;
    }

}
