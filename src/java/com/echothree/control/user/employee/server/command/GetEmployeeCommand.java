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

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.control.user.employee.common.form.GetEmployeeForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.employee.server.logic.PartyEmployeeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetEmployeeCommand
        extends BaseSingleEntityCommand<PartyEmployee, GetEmployeeForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.CUSTOMER.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Employee.name(), SecurityRoles.Review.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EmployeeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    /** Creates a new instance of GetEmployeeCommand */
    public GetEmployeeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    String employeeName;
    String partyName;
    UniversalEntitySpec universalEntitySpec;
    int parameterCount;

    @Override
    public SecurityResult security() {
        var securityResult = super.security();

        employeeName = form.getEmployeeName();
        partyName = form.getPartyName();
        universalEntitySpec = form;
        parameterCount = (employeeName == null ? 0 : 1) + (partyName == null ? 0 : 1) +
                EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(!canSpecifyParty() && parameterCount != 0) {
            securityResult = getInsufficientSecurityResult();
        }

        return securityResult;
    }

    @Override
    protected PartyEmployee getEntity() {
        PartyEmployee partyEmployee = null;

        if(parameterCount == 0) {
            var party = getParty();

            PartyLogic.getInstance().checkPartyType(this, party, PartyTypes.EMPLOYEE.name());

            if(!hasExecutionErrors()) {
                var employeeControl = Session.getModelController(EmployeeControl.class);

                partyEmployee = employeeControl.getPartyEmployee(party);
            }
        } else {
            partyEmployee = PartyEmployeeLogic.getInstance().getPartyEmployeeByName(this, employeeName, partyName, universalEntitySpec);
        }

        if(partyEmployee != null) {
            sendEvent(partyEmployee.getPartyPK(), EventTypes.READ, null, null, getPartyPK());
        }

        return partyEmployee;
    }

    @Override
    protected BaseResult getResult(PartyEmployee partyEmployee) {
        var result = EmployeeResultFactory.getGetEmployeeResult();

        if(partyEmployee != null) {
            var employeeControl = Session.getModelController(EmployeeControl.class);

            result.setEmployee(employeeControl.getEmployeeTransfer(getUserVisit(), partyEmployee));
        }

        return result;
    }

}
