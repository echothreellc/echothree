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

package com.echothree.control.user.user.server.command;

import com.echothree.control.user.user.common.form.GetUserLoginForm;
import com.echothree.control.user.user.common.result.UserResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import static com.echothree.model.control.party.common.PartyTypes.CUSTOMER;
import static com.echothree.model.control.party.common.PartyTypes.EMPLOYEE;
import static com.echothree.model.control.party.common.PartyTypes.VENDOR;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import static com.echothree.model.control.security.common.SecurityRoleGroups.Customer;
import static com.echothree.model.control.security.common.SecurityRoleGroups.Employee;
import static com.echothree.model.control.security.common.SecurityRoleGroups.Vendor;
import static com.echothree.model.control.security.common.SecurityRoles.UserLogin;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.user.server.logic.UserLoginLogic;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.SecurityMessages;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetUserLoginCommand
        extends BaseSingleEntityCommand<UserLogin, GetUserLoginForm> {
    
    // No COMMAND_SECURITY_DEFINITION, security is enforced below by PartyType.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Username", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetUserLoginCommand */
    public GetUserLoginCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected UserLogin getEntity() {
        UserLogin userLogin = null;
        var username = form.getUsername();
        var partyName = form.getPartyName();
        var parameterCount = (username == null ? 0 : 1) + (partyName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            Party party = null;

            // 1) Attempt to map whatever we're given as input to a Party
            if(username != null) {
                var foundByUsernameUserLogin = UserLoginLogic.getInstance().getUserLoginByUsername(this, username);
                
                if(foundByUsernameUserLogin != null) {
                    party = foundByUsernameUserLogin.getParty();
                }
            } else if(partyName != null) {
                party = PartyLogic.getInstance().getPartyByName(this, partyName);
            } else {
                var partyControl = Session.getModelController(PartyControl.class);
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form, ComponentVendors.ECHO_THREE.name(),
                        EntityTypes.Party.name());

                if(!hasExecutionErrors()) {
                    party = partyControl.getPartyByEntityInstance(entityInstance);
                }
            }

            // 2) Based on the PartyType, check the SecurityRoles and return the UserLogin if permitted and it exists.
            if(!hasExecutionErrors()) {
                var partyType = party.getLastDetail().getPartyType();
                String securityRoleGroupName = null;
                var partyTypeName = partyType.getPartyTypeName();

                if(partyTypeName.equals(CUSTOMER.name())) {
                    securityRoleGroupName = Customer.name();
                } else if(partyTypeName.equals(EMPLOYEE.name())) {
                    securityRoleGroupName = Employee.name();
                } else if(partyTypeName.equals(VENDOR.name())) {
                    securityRoleGroupName = Vendor.name();
                }

                // 2A) SecurityRole check.
                if(securityRoleGroupName != null 
                        && SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, getParty(), securityRoleGroupName, UserLogin.name())) {
                    if(!hasExecutionErrors()) {
                        // 2B) Does the PartyType allow UserLogins for it?
                        if(partyType.getAllowUserLogins()) {
                            var userControl = getUserControl();

                            // 2C) Map Party to a UserLogin if it exists.
                            userLogin = userControl.getUserLogin(party);

                            if(userLogin == null) {
                                addExecutionError(ExecutionErrors.UnknownUserLogin.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.InvalidPartyType.name(), partyType.getPartyTypeName());
                        }
                    }
                } else {
                    addSecurityMessage(SecurityMessages.InsufficientSecurity.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return userLogin;
    }
    
    @Override
    protected BaseResult getResult(UserLogin userLogin) {
        var userControl = getUserControl();
        var result = UserResultFactory.getGetUserLoginResult();

        if(userLogin != null) {
            result.setUserLogin(userControl.getUserLoginTransfer(getUserVisit(), userLogin));
        }

        return result;
    }
    
}
