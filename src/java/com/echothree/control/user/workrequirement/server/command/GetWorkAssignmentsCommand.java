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

package com.echothree.control.user.workrequirement.server.command;

import com.echothree.control.user.workrequirement.common.form.GetWorkAssignmentsForm;
import com.echothree.control.user.workrequirement.common.result.WorkRequirementResultFactory;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.EmployeeLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.workrequirement.server.entity.WorkAssignment;
import com.echothree.model.data.workrequirement.server.factory.WorkAssignmentFactory;
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
public class GetWorkAssignmentsCommand
        extends BasePaginatedMultipleEntitiesCommand<WorkAssignment, GetWorkAssignmentsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EmployeeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    PartyControl partyControl;

    @Inject
    WorkRequirementControl workRequirementControl;

    @Inject
    EmployeeLogic employeeLogic;

    @Inject
    PartyLogic partyLogic;

    /** Creates a new instance of GetWorkAssignmentsCommand */
    public GetWorkAssignmentsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    private Party party;

    @Override
    protected void handleForm() {
        var employeeName = form.getEmployeeName();
        var partyName = form.getPartyName();
        var parameterCount = (employeeName == null ? 0 : 1) + (partyName == null ? 0 : 1);

        if(parameterCount < 2) {
            if(employeeName != null) {
                var partyEmployee = employeeLogic.getPartyEmployeeByName(this, employeeName, null);

                if(!hasExecutionErrors()) {
                    party = partyEmployee.getParty();
                }
            } else if(partyName != null) {
                party = partyLogic.getPartyByName(this, partyName);
            } else {
                party = getParty();
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : workRequirementControl.countWorkAssignmentsByParty(party);
    }

    @Override
    protected Collection<WorkAssignment> getEntities() {
        return hasExecutionErrors() ? null : workRequirementControl.getWorkAssignmentsByParty(party);
    }

    @Override
    protected BaseResult getResult(Collection<WorkAssignment> entities) {
        var result = WorkRequirementResultFactory.getGetWorkAssignmentsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setParty(partyControl.getPartyTransfer(userVisit, party));

            if(session.hasLimit(WorkAssignmentFactory.class)) {
                result.setWorkAssignmentCount(getTotalEntities());
            }

            result.setWorkAssignments(workRequirementControl.getWorkAssignmentTransfers(userVisit, entities));
        }

        return result;
    }
    
}
