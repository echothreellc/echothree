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

package com.echothree.control.user.employee.server.command;

import com.echothree.control.user.employee.common.form.GetPartyResponsibilitiesForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.data.employee.server.entity.PartyResponsibility;
import com.echothree.model.data.employee.server.entity.ResponsibilityType;
import com.echothree.model.data.employee.server.factory.PartyResponsibilityFactory;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyResponsibilitiesCommand
        extends BasePaginatedMultipleEntitiesCommand<PartyResponsibility, GetPartyResponsibilitiesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ResponsibilityTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    @Inject
    EmployeeControl employeeControl;

    @Inject
    PartyControl partyControl;

    @Inject
    PartyLogic partyLogic;

    /** Creates a new instance of GetPartyResponsibilitiesCommand */
    public GetPartyResponsibilitiesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    private Party party;
    private ResponsibilityType responsibilityType;

    @Override
    protected void handleForm() {
        var partyName = form.getPartyName();
        var responsibilityTypeName = form.getResponsibilityTypeName();
        var parameterCount = (partyName == null ? 0 : 1) + (responsibilityTypeName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(partyName != null) {
                party = partyLogic.getPartyByName(this, partyName);
            } else {
                responsibilityType = employeeControl.getResponsibilityTypeByName(responsibilityTypeName);

                if(responsibilityType == null) {
                    addExecutionError(ExecutionErrors.UnknownResponsibilityTypeName.name(), responsibilityTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(party != null) {
                total = employeeControl.countPartyResponsibilitiesByParty(party);
            } else {
                total = employeeControl.countPartyResponsibilitiesByResponsibilityType(responsibilityType);
            }
        }

        return total;
    }

    @Override
    protected Collection<PartyResponsibility> getEntities() {
        Collection<PartyResponsibility> entities = null;

        if(!hasExecutionErrors()) {
            if(party != null) {
                entities = employeeControl.getPartyResponsibilitiesByParty(party);
            } else {
                entities = employeeControl.getPartyResponsibilitiesByResponsibilityType(responsibilityType);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<PartyResponsibility> entities) {
        var result = EmployeeResultFactory.getGetPartyResponsibilitiesResult();

        if(entities != null) {
            if(party != null) {
                result.setParty(partyControl.getPartyTransfer(getUserVisit(), party));
            } else {
                result.setResponsibilityType(employeeControl.getResponsibilityTypeTransfer(getUserVisit(), responsibilityType));
            }

            if(session.hasLimit(PartyResponsibilityFactory.class)) {
                result.setPartyResponsibilityCount(getTotalEntities());
            }

            result.setPartyResponsibilities(employeeControl.getPartyResponsibilityTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
