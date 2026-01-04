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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.AddEmployeeToDepartmentForm;
import com.echothree.model.control.party.server.logic.CompanyLogic;
import com.echothree.model.control.party.server.logic.DepartmentLogic;
import com.echothree.model.control.party.server.logic.DivisionLogic;
import com.echothree.model.control.party.server.logic.EmployeeLogic;
import com.echothree.model.control.party.server.logic.PartyRelationshipLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class AddEmployeeToDepartmentCommand
        extends BaseSimpleCommand<AddEmployeeToDepartmentForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DepartmentName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EmployeeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of AddEmployeeToDepartmentCommand */
    public AddEmployeeToDepartmentCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var employeeName = form.getEmployeeName();
        var partyName = form.getPartyName();
        var partyEmployee = EmployeeLogic.getInstance().getPartyEmployeeByName(this, employeeName, partyName);

        if(!hasExecutionErrors()) {
            var companyName = form.getCompanyName();
            var partyCompany = CompanyLogic.getInstance().getPartyCompanyByName(this, companyName, null, null, false);

            if(!hasExecutionErrors()) {
                var divisionName = form.getDivisionName();
                var partyDivision = DivisionLogic.getInstance().getPartyDivisionByName(this, partyCompany == null ? null : partyCompany.getParty(), divisionName, null, null, false);

                if(!hasExecutionErrors()) {
                    var departmentName = form.getDepartmentName();
                    var partyDepartment = DepartmentLogic.getInstance().getPartyDepartmentByName(this, partyDivision == null ? null : partyDivision.getParty(), departmentName, null, null, true);

                    if(!hasExecutionErrors()) {
                        var departmentParty = partyDepartment.getParty();
                        var employeeParty = partyEmployee.getParty();

                        PartyRelationshipLogic.getInstance().addEmployeeToDepartment(this, departmentParty, employeeParty, getPartyPK());
                    }
                }
            }
        }

        return null;
    }
    
}
