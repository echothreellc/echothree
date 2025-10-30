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

package com.echothree.control.user.workrequirement.server.command;

import com.echothree.control.user.workrequirement.common.form.GetWorkAssignmentsForm;
import com.echothree.control.user.workrequirement.common.result.WorkRequirementResultFactory;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workrequirement.server.factory.WorkAssignmentFactory;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetWorkAssignmentsCommand
        extends BaseSimpleCommand<GetWorkAssignmentsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EmployeeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetWorkAssignmentsCommand */
    public GetWorkAssignmentsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = WorkRequirementResultFactory.getGetWorkAssignmentsResult();
        var employeeName = form.getEmployeeName();
        var partyName = form.getPartyName();
        var parameterCount = (employeeName == null ? 0 : 1) + (partyName == null ? 0 : 1);

        if(parameterCount < 2) {
            Party party = null;

            if(employeeName != null) {
                var employeeControl = Session.getModelController(EmployeeControl.class);
                var partyEmployee = employeeControl.getPartyEmployeeByName(employeeName);

                if(partyEmployee != null) {
                    party = partyEmployee.getParty();
                } else {
                    addExecutionError(ExecutionErrors.UnknownEmployeeName.name(), employeeName);
                }
            } else if(partyName != null) {
                party = partyControl.getPartyByName(partyName);

                if(party == null) {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else {
                party = getParty();
            }

            if(!hasExecutionErrors()) {
                var workRequirementControl = Session.getModelController(WorkRequirementControl.class);
                var userVisit = getUserVisit();

                if(session.hasLimit(WorkAssignmentFactory.class)) {
                    result.setWorkAssignmentCount(workRequirementControl.countWorkAssignmentsByParty(party));
                }

                result.setParty(partyControl.getPartyTransfer(userVisit, party));
                result.setWorkAssignments(workRequirementControl.getWorkAssignmentTransfersByParty(userVisit, party));
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
