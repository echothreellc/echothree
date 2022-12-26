// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.control.user.user.common.form.CreateUserLoginForm;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import static com.echothree.model.control.party.common.PartyTypes.CUSTOMER;
import static com.echothree.model.control.party.common.PartyTypes.EMPLOYEE;
import static com.echothree.model.control.party.common.PartyTypes.VENDOR;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.party.server.logic.PasswordStringPolicyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import static com.echothree.model.control.security.common.SecurityRoleGroups.Customer;
import static com.echothree.model.control.security.common.SecurityRoleGroups.Employee;
import static com.echothree.model.control.security.common.SecurityRoleGroups.Vendor;
import com.echothree.model.control.security.common.SecurityRoles;
import static com.echothree.model.control.security.common.SecurityRoles.UserLogin;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.user.common.UserConstants;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.entity.PartyTypePasswordStringPolicy;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.model.data.user.server.entity.UserLoginPassword;
import com.echothree.model.data.user.server.entity.UserLoginPasswordType;
import com.echothree.model.data.user.server.entity.UserLoginStatus;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateUserLoginCommand
        extends BaseSimpleCommand<CreateUserLoginForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Customer.name(), SecurityRoles.UserLogin.name()),
                        new SecurityRoleDefinition(SecurityRoleGroups.Employee.name(), SecurityRoles.UserLogin.name()),
                        new SecurityRoleDefinition(SecurityRoleGroups.Vendor.name(), SecurityRoles.UserLogin.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Key", FieldType.KEY, false, null, null),
                new FieldDefinition("Guid", FieldType.GUID, false, null, null),
                new FieldDefinition("Ulid", FieldType.ULID, false, null, null),
                new FieldDefinition("Username", FieldType.STRING, true, 1L, 80L),
                new FieldDefinition("Password1", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("Password2", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("RecoveryQuestionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Answer", FieldType.STRING, false, 1L, 40L)
                ));
    }
    
    /** Creates a new instance of CreateUserLoginCommand */
    public CreateUserLoginCommand(UserVisitPK userVisitPK, CreateUserLoginForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        String partyName = form.getPartyName();
        var parameterCount = (partyName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            Party party = null;
            
            if(partyName == null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form, ComponentVendors.ECHOTHREE.name(),
                        EntityTypes.Party.name());

                if(!hasExecutionErrors()) {
                    party = partyControl.getPartyByEntityInstanceForUpdate(entityInstance);
                }
            } else {
                party = PartyLogic.getInstance().getPartyByName(this, form.getPartyName());
            }
            
            if(!hasExecutionErrors()) {
                PartyLogic partyLogic = PartyLogic.getInstance();

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
                                partyLogic.checkPartyType(this, party, PartyTypes.CUSTOMER.name(), PartyTypes.EMPLOYEE.name(),
                                        PartyTypes.VENDOR.name());

                                if(!hasExecutionErrors()) {
                                    UserControl userControl = getUserControl();
                                    String username = form.getUsername();
                                    UserLogin userLogin = userControl.getUserLoginByUsername(username);

                                    if(userLogin == null) {
                                        String recoveryQuestionName = form.getRecoveryQuestionName();
                                        String answer = form.getAnswer();
                                        int recoveryParameterCount = (recoveryQuestionName == null ? 0 : 1) + (answer == null ? 0 : 1);

                                        if(partyLogic.isPartyType(party, PartyTypes.CUSTOMER.name())) {
                                            if(recoveryParameterCount != 2) {
                                                // RecoveryQuestionName and Answer are required for CUSTOMERs
                                                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                                            }
                                        } else if(recoveryParameterCount != 0) {
                                            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                                        }

                                        if(!hasExecutionErrors()) {
                                            String password1 = form.getPassword1();
                                            String password2 = form.getPassword2();

                                            if(password1.equals(password2)) {
                                                PartyTypePasswordStringPolicy partyTypePasswordStringPolicy = PasswordStringPolicyLogic.getInstance().checkStringPassword(session,
                                                        getUserVisit(), this, partyType, null, null, password1);

                                                if(!hasExecutionErrors()) {
                                                    RecoveryQuestion recoveryQuestion = recoveryQuestionName == null ? null : userControl.getRecoveryQuestionByName(recoveryQuestionName);

                                                    if(recoveryQuestionName == null || recoveryQuestion != null) {
                                                        UserLoginPasswordType userLoginPasswordType = userControl.getUserLoginPasswordTypeByName(UserConstants.UserLoginPasswordType_STRING);
                                                        PartyPK createdBy = getPartyPK();

                                                        userControl.createUserLogin(party, username, createdBy);

                                                        UserLoginPassword userLoginPassword = userControl.createUserLoginPassword(party, userLoginPasswordType, createdBy);
                                                        userControl.createUserLoginPasswordString(userLoginPassword, password1, session.START_TIME_LONG, Boolean.FALSE, createdBy);

                                                        if(partyTypePasswordStringPolicy != null && partyTypePasswordStringPolicy.getLastDetail().getForceChangeAfterCreate()) {
                                                            UserLoginStatus userLoginStatus = userControl.getUserLoginStatusForUpdate(party);

                                                            userLoginStatus.setForceChange(Boolean.TRUE);
                                                        }

                                                        if(recoveryQuestion != null && answer != null) {
                                                            userControl.createRecoveryAnswer(party, recoveryQuestion, answer, createdBy);
                                                        }
                                                    } else {
                                                        addExecutionError(ExecutionErrors.UnknownRecoveryQuestionName.name(), recoveryQuestionName);
                                                    }
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.MismatchedPasswords.name());
                                            }
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.DuplicateUsername.name(), username);
                                    }
                                }
                            } else {
                                addExecutionError(ExecutionErrors.InvalidPartyType.name(), partyType.getPartyTypeName());
                            }
                        }
                    }
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return null;
    }
    
}
