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

package com.echothree.control.user.authentication.server.command;

import com.echothree.control.user.authentication.common.form.EmployeeLoginForm;
import com.echothree.model.control.employee.common.workflow.EmployeeStatusConstants;
import com.echothree.model.control.party.common.PartyRelationshipTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.common.RoleTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.LockoutPolicyLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.user.server.logic.UserLoginLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowStepLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EmployeeLoginCommand
        extends BaseLoginCommand<EmployeeLoginForm> {

    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Username", FieldType.STRING, true, 1L, 80L),
                new FieldDefinition("Password", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("RemoteInet4Address", FieldType.INET_4_ADDRESS, false, null, null),
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EmployeeLoginCommand */
    public EmployeeLoginCommand() {
        super(null, FORM_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var userLogin = UserLoginLogic.getInstance().getUserLoginByUsername(this, form.getUsername());
        
        if(!hasExecutionErrors()) {
            var party = userLogin.getParty();
            var partyDetail = party.getLastDetail();

            PartyLogic.getInstance().checkPartyType(this, party, PartyTypes.EMPLOYEE.name());

            if(!hasExecutionErrors()) {
                var userControl = getUserControl();
                var userLoginStatus = userControl.getUserLoginStatusForUpdate(party);

                if(!WorkflowStepLogic.getInstance().isEntityInWorkflowSteps(this, EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS, party,
                        EmployeeStatusConstants.WorkflowStep_ACTIVE).isEmpty()) {
                    LockoutPolicyLogic.getInstance().checkUserLogin(session, this, party, userLoginStatus);

                    if(!hasExecutionErrors()) {
                        var partyControl = Session.getModelController(PartyControl.class);

                        if(checkPasswords(userLoginStatus, form.getPassword(), party, true)) {
                            var partyCompanyName = form.getCompanyName();
                            var partyCompany = partyControl.getPartyCompanyByName(partyCompanyName);

                            if(partyCompany != null) {
                                var partyCompanyParty = partyCompany.getParty();
                                var partyRelationshipType = partyControl.getPartyRelationshipTypeByName(PartyRelationshipTypes.EMPLOYMENT.name());
                                var fromRoleType = partyControl.getRoleTypeByName(RoleTypes.EMPLOYER.name());
                                var toRoleType = partyControl.getRoleTypeByName(RoleTypes.EMPLOYEE.name());
                                var partyRelationship = partyControl.getPartyRelationship(partyRelationshipType, partyCompanyParty,
                                        fromRoleType, party, toRoleType);

                                if(partyRelationship != null) {
                                    var strRemoteInet4Address = form.getRemoteInet4Address();
                                    var remoteInet4Address = strRemoteInet4Address == null ? null : Integer.valueOf(form.getRemoteInet4Address());

                                    successfulLogin(userLoginStatus, party, partyRelationship, remoteInet4Address);
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownPartyRelationship.name(), PartyRelationshipTypes.EMPLOYMENT.name(),
                                            partyCompanyParty.getLastDetail().getPartyName(), RoleTypes.EMPLOYER.name(), partyDetail.getPartyName(),
                                            RoleTypes.EMPLOYEE.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownPartyCompanyName.name(), partyCompanyName);
                            }
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.EmployeeNotActive.name(), partyDetail.getPartyName());
                }
                
                if(hasExecutionErrors()) {
                    unsuccessfulLogin(userLoginStatus);
                }
            }
        }
        
        return null;
    }
    
}
