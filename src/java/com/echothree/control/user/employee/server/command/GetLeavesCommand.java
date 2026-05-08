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

import com.echothree.control.user.employee.common.form.GetLeavesForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.employee.server.entity.Leave;
import com.echothree.model.data.employee.server.factory.LeaveFactory;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetLeavesCommand
        extends BasePaginatedMultipleEntitiesCommand<Leave, GetLeavesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Leave.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    EmployeeControl employeeControl;

    @Inject
    PartyControl partyControl;

    @Inject
    PartyLogic partyLogic;

    /** Creates a new instance of GetLeavesCommand */
    public GetLeavesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    protected Party party;

    @Override
    protected void handleForm() {
        party = partyLogic.getPartyByName(this, form.getPartyName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : employeeControl.countLeavesByParty(party);
    }

    @Override
    protected Collection<Leave> getEntities() {
        return hasExecutionErrors() ? null : employeeControl.getLeavesByParty(party);
    }

    @Override
    protected BaseResult getResult(Collection<Leave> entities) {
        var result = EmployeeResultFactory.getGetLeavesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setParty(partyControl.getPartyTransfer(userVisit, party));

            if(session.hasLimit(LeaveFactory.class)) {
                result.setLeaveCount(getTotalEntities());
            }

            result.setLeaves(employeeControl.getLeaveTransfers(userVisit, entities));
        }

        return result;
    }

}
