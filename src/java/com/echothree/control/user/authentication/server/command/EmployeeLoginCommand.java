// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.authentication.remote.form.EmployeeLoginForm;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.party.server.logic.LockoutPolicyLogic;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.control.user.server.logic.UserLoginLogic;
import com.echothree.model.control.employee.common.workflow.EmployeeStatusConstants;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyRelationship;
import com.echothree.model.data.party.server.entity.PartyRelationshipType;
import com.echothree.model.data.party.server.entity.RoleType;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.model.data.user.server.entity.UserLoginStatus;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EmployeeLoginCommand
        extends BaseLoginCommand<EmployeeLoginForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Username", FieldType.STRING, true, 1L, 80L),
                new FieldDefinition("Password", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("RemoteInet4Address", FieldType.INET_4_ADDRESS, true, null, null),
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EmployeeLoginCommand */
    public EmployeeLoginCommand(UserVisitPK userVisitPK, EmployeeLoginForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        UserLogin userLogin = UserLoginLogic.getInstance().getUserLoginByUsername(this, form.getUsername());
        
        if(!hasExecutionErrors()) {
            Party party = userLogin.getParty();
            PartyDetail partyDetail = party.getLastDetail();
            PartyLogic.getInstance().checkPartyType(this, party, PartyConstants.PartyType_EMPLOYEE);

            if(!hasExecutionErrors()) {
                UserControl userControl = getUserControl();
                UserLoginStatus userLoginStatus = userControl.getUserLoginStatusForUpdate(party);

                if(!WorkflowLogic.getInstance().isEntityInWorkflowSteps(this, EmployeeStatusConstants.Workflow_EMPLOYEE_STATUS, party,
                        EmployeeStatusConstants.WorkflowStep_ACTIVE).isEmpty()) {
                    LockoutPolicyLogic.getInstance().checkUserLogin(session, this, party, userLoginStatus);

                    if(!hasExecutionErrors()) {
                        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);

                        if(checkPasswords(userLoginStatus, form.getPassword(), party, true)) {
                            String partyCompanyName = form.getCompanyName();
                            PartyCompany partyCompany = partyControl.getPartyCompanyByName(partyCompanyName);

                            if(partyCompany != null) {
                                Party partyCompanyParty = partyCompany.getParty();
                                PartyRelationshipType partyRelationshipType = partyControl.getPartyRelationshipTypeByName(PartyConstants.PartyRelationshipType_EMPLOYMENT);
                                RoleType fromRoleType = partyControl.getRoleTypeByName(PartyConstants.RoleType_EMPLOYER);
                                RoleType toRoleType = partyControl.getRoleTypeByName(PartyConstants.RoleType_EMPLOYEE);

                                PartyRelationship partyRelationship = partyControl.getPartyRelationship(partyRelationshipType, partyCompanyParty,
                                        fromRoleType, party, toRoleType);

                                if(partyRelationship != null) {
                                    Integer remoteInet4Address = Integer.valueOf(form.getRemoteInet4Address());

                                    successfulLogin(userLoginStatus, party, partyRelationship, remoteInet4Address);
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownPartyRelationship.name(), PartyConstants.PartyRelationshipType_EMPLOYMENT,
                                            partyCompanyParty.getLastDetail().getPartyName(), PartyConstants.RoleType_EMPLOYER, partyDetail.getPartyName(),
                                            PartyConstants.RoleType_EMPLOYEE);
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
