// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.control.user.user.common.result.GetUserLoginResult;
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
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.user.server.logic.UserLoginLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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

public class GetUserLoginCommand
        extends BaseSingleEntityCommand<UserLogin, GetUserLoginForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Username", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetUserLoginCommand */
    public GetUserLoginCommand(UserVisitPK userVisitPK, GetUserLoginForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    public UserLogin foundByUsernameUserLogin = null;

    @Override
    protected UserLogin getEntity() {
        UserLogin userLogin = null;
        String username = form.getUsername();
        String partyName = form.getPartyName();
        int parameterCount = (username == null ? 0 : 1) + (partyName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            Party party = null;
            
            if(username != null) {
                foundByUsernameUserLogin = UserLoginLogic.getInstance().getUserLoginByUsername(this, username);
                
                if(foundByUsernameUserLogin != null) {
                    party = foundByUsernameUserLogin.getParty();
                }
            } else if(partyName != null) {
                party = PartyLogic.getInstance().getPartyByName(this, partyName);
            } else {
                var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form, ComponentVendors.ECHOTHREE.name(),
                        EntityTypes.Party.name());

                if(!hasExecutionErrors()) {
                    party = partyControl.getPartyByEntityInstance(entityInstance);
                }
            }
            
            if(!hasExecutionErrors()) {
                PartyType partyType = party.getLastDetail().getPartyType();
                String securityRoleGroupName = null;
                var partyTypeName = partyType.getPartyTypeName();

                if(partyTypeName.equals(CUSTOMER.name())) {
                    securityRoleGroupName = Customer.name();
                } else if(partyTypeName.equals(EMPLOYEE.name())) {
                    securityRoleGroupName = Employee.name();
                } else if(partyTypeName.equals(VENDOR.name())) {
                    securityRoleGroupName = Vendor.name();
                }

                if(securityRoleGroupName != null 
                        && SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, getParty(), securityRoleGroupName, UserLogin.name())) {
                    if(!hasExecutionErrors()) {
                        if(partyType.getAllowUserLogins()) {
                            UserControl userControl = getUserControl();
                            
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
    protected BaseResult getTransfer(UserLogin userLogin) {
        UserControl userControl = getUserControl();
        GetUserLoginResult result = UserResultFactory.getGetUserLoginResult();

        if(userLogin != null) {
            result.setUserLogin(userControl.getUserLoginTransfer(getUserVisit(), userLogin));
        }

        return result;
    }
    
}
