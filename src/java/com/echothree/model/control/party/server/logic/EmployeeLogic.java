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

import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.employee.common.workflow.EmployeeStatusConstants;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.common.exception.UnknownEmployeeNameException;
import com.echothree.model.control.party.common.exception.UnknownEmployeeStatusChoiceException;
import com.echothree.model.control.party.common.exception.UnknownPartyNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.logic.UserKeyLogic;
import com.echothree.model.control.user.server.logic.UserSessionLogic;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowDestinationLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class EmployeeLogic
        extends BaseLogic {

    protected EmployeeLogic() {
        super();
    }

    public static EmployeeLogic getInstance() {
        return CDI.current().select(EmployeeLogic.class).get();
    }

    public PartyEmployee getPartyEmployeeByName(final ExecutionErrorAccumulator eea, final String employeeName, final String partyName) {
        var parameterCount = (employeeName == null ? 0 : 1) + (partyName == null ? 0 : 1);
        PartyEmployee partyEmployee = null;

        if(parameterCount == 1) {
            var employeeControl = Session.getModelController(EmployeeControl.class);

            if(employeeName != null) {
                partyEmployee = employeeControl.getPartyEmployeeByName(employeeName);

                if(partyEmployee == null) {
                    handleExecutionError(UnknownEmployeeNameException.class, eea, ExecutionErrors.UnknownEmployeeName.name(), employeeName);
                }
            } else {
                var partyControl = Session.getModelController(PartyControl.class);
                var party = partyControl.getPartyByName(partyName);

                if(party != null) {
                    PartyLogic.getInstance().checkPartyType(eea, party, PartyTypes.EMPLOYEE.name());

                    partyEmployee = employeeControl.getPartyEmployee(party);
                } else {
                    handleExecutionError(UnknownPartyNameException.class, eea, ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return partyEmployee;
    }

    public void setEmployeeStatus(final Session session, ExecutionErrorAccumulator eea, Party party, String employeeStatusChoice, PartyPK modifiedBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var workflow = WorkflowLogic.getInstance().getWorkflowByName(eea, EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(party.getPrimaryKey());
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        var workflowDestination = employeeStatusChoice == null ? null : workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), employeeStatusChoice);

        if(workflowDestination != null || employeeStatusChoice == null) {
            var workflowDestinationLogic = WorkflowDestinationLogic.getInstance();
            var currentWorkflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
            var map = workflowDestinationLogic.getWorkflowDestinationsAsMap(workflowDestination);
            Long triggerTime = null;

            if(currentWorkflowStepName.equals(EmployeeStatusConstants.WorkflowStep_ACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS, EmployeeStatusConstants.WorkflowStep_INACTIVE)) {
                    UserKeyLogic.getInstance().clearUserKeysByParty(party);
                    UserSessionLogic.getInstance().deleteUserSessionsByParty(party);
                }
            } else if(currentWorkflowStepName.equals(EmployeeStatusConstants.WorkflowStep_INACTIVE)) {
                if(workflowDestinationLogic.workflowDestinationMapContainsStep(map, EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS, EmployeeStatusConstants.WorkflowStep_ACTIVE)) {
                    // Nothing at this time.
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, triggerTime, modifiedBy);
            }
        } else {
            handleExecutionError(UnknownEmployeeStatusChoiceException.class, eea, ExecutionErrors.UnknownEmployeeStatusChoice.name(), employeeStatusChoice);
        }
    }

}
