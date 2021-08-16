// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
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

public class DepartmentLogic
        extends BaseLogic {
    
    private DepartmentLogic() {
        super();
    }
    
    private static class DepartmentLogicHolder {
        static DepartmentLogic instance = new DepartmentLogic();
    }
    
    public static DepartmentLogic getInstance() {
        return DepartmentLogicHolder.instance;
    }

    public PartyDepartment getPartyDepartmentByName(final ExecutionErrorAccumulator eea, final Party divisionParty, final String departmentName,
            final String partyName, boolean required) {
        int parameterCount = (departmentName == null? 0: 1) + (partyName == null? 0: 1);
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
                    Party party = partyControl.getPartyByName(partyName);

                    if(party != null) {
                        PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.DEPARTMENT.name());

                        partyDepartment = partyControl.getPartyDepartment(party);
                    } else {
                        handleExecutionError(UnknownPartyNameException.class, eea, ExecutionErrors.UnknownPartyName.name(), partyName);
                    }
                }
            } else {
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            }
        }

        return partyDepartment;
    }

}
