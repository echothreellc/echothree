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

import com.echothree.control.user.party.common.spec.PartyUniversalSpec;
import com.echothree.control.user.user.common.edit.UserEditFactory;
import com.echothree.control.user.user.common.edit.UserLoginEdit;
import com.echothree.control.user.user.common.form.EditUserLoginForm;
import com.echothree.control.user.user.common.result.EditUserLoginResult;
import com.echothree.control.user.user.common.result.UserResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import static com.echothree.model.control.party.common.PartyTypes.CUSTOMER;
import static com.echothree.model.control.party.common.PartyTypes.EMPLOYEE;
import static com.echothree.model.control.party.common.PartyTypes.VENDOR;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import static com.echothree.model.control.security.common.SecurityRoleGroups.Customer;
import static com.echothree.model.control.security.common.SecurityRoleGroups.Employee;
import static com.echothree.model.control.security.common.SecurityRoleGroups.Vendor;
import com.echothree.model.control.security.common.SecurityRoles;
import static com.echothree.model.control.security.common.SecurityRoles.UserLogin;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.SecurityMessages;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditUserLoginCommand
        extends BaseAbstractEditCommand<PartyUniversalSpec, UserLoginEdit, EditUserLoginResult, UserLogin, Party> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Customer.name(), SecurityRoles.UserLogin.name()),
                        new SecurityRoleDefinition(SecurityRoleGroups.Employee.name(), SecurityRoles.UserLogin.name()),
                        new SecurityRoleDefinition(SecurityRoleGroups.Vendor.name(), SecurityRoles.UserLogin.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Username", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditUserLoginCommand */
    public EditUserLoginCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditUserLoginResult getResult() {
        return UserResultFactory.getEditUserLoginResult();
    }

    @Override
    public UserLoginEdit getEdit() {
        return UserEditFactory.getUserLoginEdit();
    }

    @Override
    public UserLogin getEntity(EditUserLoginResult result) {
        UserLogin userLogin = null;
        var partyName = spec.getPartyName();
        var parameterCount = (partyName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(spec);

        if(parameterCount == 1) {
            Party party = null;
            
            if(partyName == null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, spec, ComponentVendors.ECHO_THREE.name(),
                        EntityTypes.Party.name());

                if(!hasExecutionErrors()) {
                    party = partyControl.getPartyByEntityInstanceForUpdate(entityInstance);
                }
            } else {
                party = PartyLogic.getInstance().getPartyByName(this, spec.getPartyName());
            }
            
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

                if(securityRoleGroupName != null 
                        && SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, getParty(), securityRoleGroupName, UserLogin.name())) {
                    if(!hasExecutionErrors()) {
                        if(partyType.getAllowUserLogins()) {
                            var userControl = getUserControl();

                            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                userLogin = userControl.getUserLogin(party);
                            } else { // EditMode.UPDATE
                                userLogin = userControl.getUserLoginForUpdate(party);
                            }

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
    public Party getLockEntity(UserLogin userLogin) {
        return userLogin.getParty();
    }

    @Override
    public void fillInResult(EditUserLoginResult result, UserLogin userLogin) {
        var userControl = getUserControl();

        result.setUserLogin(userControl.getUserLoginTransfer(getUserVisit(), userLogin));
    }

    @Override
    public void doLock(UserLoginEdit edit, UserLogin userLogin) {
        edit.setUsername(userLogin.getUsername());
    }

    @Override
    public void canUpdate(UserLogin userLogin) {
        var userControl = getUserControl();
        var username = edit.getUsername();
        var duplicateUserLogin = userControl.getUserLoginByUsername(username);

        if(duplicateUserLogin != null && !userLogin.equals(duplicateUserLogin)) {
            addExecutionError(ExecutionErrors.DuplicateUsername.name(), username);
        }
    }

    @Override
    public void doUpdate(UserLogin userLogin) {
        var userControl = getUserControl();
        var userLoginValue = userControl.getUserLoginValue(userLogin);
        
        userLoginValue.setUsername(edit.getUsername());
        
        userControl.updateUserLoginFromValue(userLoginValue, getPartyPK());
    }
    
}
