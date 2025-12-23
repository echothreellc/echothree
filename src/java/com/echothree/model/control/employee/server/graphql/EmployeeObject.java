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

package com.echothree.model.control.employee.server.graphql;

import com.echothree.model.control.employee.common.workflow.EmployeeAvailabilityConstants;
import com.echothree.model.control.employee.common.workflow.EmployeeStatusConstants;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.server.graphql.BasePartyObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("employee object")
@GraphQLName("Employee")
public class EmployeeObject
        extends BasePartyObject {

    public EmployeeObject(Party party) {
        super(party);
    }

    public EmployeeObject(PartyEmployee partyEmployee) {
        super(partyEmployee.getParty());

        this.partyEmployee = partyEmployee;
    }

    private PartyEmployee partyEmployee;  // Optional, use getEmployee()

    protected PartyEmployee getPartyEmployee() {
        if(partyEmployee == null) {
            var employeeControl = Session.getModelController(EmployeeControl.class);

            partyEmployee = employeeControl.getPartyEmployee(party);
        }

        return partyEmployee;
    }

    @GraphQLField
    @GraphQLDescription("employee name")
    @GraphQLNonNull
    public String getEmployeeName() {
        return getPartyEmployee().getPartyEmployeeName();
    }

//    @GraphQLField
//    @GraphQLDescription("employee type")
//    @GraphQLNonNull
//    public EmployeeTypeObject getEmployeeType(final DataFetchingEnvironment env) {
//        return EmployeeSecurityUtils.getHasEmployeeTypeAccess(env) ?
//                new EmployeeTypeObject(getEmployee().getEmployeeType()) : null;
//    }


    @GraphQLField
    @GraphQLDescription("employee status")
    public WorkflowEntityStatusObject getEmployeeStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS);
    }


    @GraphQLField
    @GraphQLDescription("employee availability")
    public WorkflowEntityStatusObject getEmployeeAvailability(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, EmployeeAvailabilityConstants.Workflow_EMPLOYEE_AVAILABILITY);
    }

}
