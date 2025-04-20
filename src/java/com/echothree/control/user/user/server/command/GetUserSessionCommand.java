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

package com.echothree.control.user.user.server.command;

import com.echothree.control.user.user.common.form.GetUserSessionForm;
import com.echothree.control.user.user.common.result.UserResultFactory;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.employee.common.workflow.EmployeeAvailabilityConstants;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class GetUserSessionCommand
        extends BaseSimpleCommand<GetUserSessionForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    /** Creates a new instance of GetUserSessionCommand */
    public GetUserSessionCommand(UserVisitPK userVisitPK, GetUserSessionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var userControl = getUserControl();
        var result = UserResultFactory.getGetUserSessionResult();
        var userSession = getUserSession();
        
        if(userSession != null) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(getPartyPK());
            var party = userSession.getParty();
            var partyTypeName = party == null ? null : party.getLastDetail().getPartyType().getPartyTypeName();
            var userVisit = getUserVisit();
            
            result.setUserSession(userControl.getUserSessionTransfer(userVisit, userSession));

            if(partyTypeName != null && partyTypeName.equals(PartyTypes.EMPLOYEE.name())) {
                result.setEmployeeAvailability(workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                        EmployeeAvailabilityConstants.Workflow_EMPLOYEE_AVAILABILITY, entityInstance));
            }
        }
        
        return result;
    }
    
}
