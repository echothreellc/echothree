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

import com.echothree.control.user.authentication.server.command.BaseLoginCommand;
import com.echothree.control.user.user.common.form.CreateUserLoginForm;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.party.server.logic.PasswordStringPolicyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import static com.echothree.model.control.security.common.SecurityRoles.UserLogin;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.user.common.UserConstants;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateUserLoginCommand
        extends BaseLoginCommand<CreateUserLoginForm> {
    
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
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("Username", FieldType.STRING, true, 1L, 80L),
                new FieldDefinition("Password1", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("Password2", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("RecoveryQuestionName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Answer", FieldType.STRING, false, 1L, 40L)
                ));
    }
    
    /** Creates a new instance of CreateUserLoginCommand */
    public CreateUserLoginCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var partyName = form.getPartyName();
        var parameterCount = (partyName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            Party party = null;
            
            if(partyName == null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form, ComponentVendors.ECHO_THREE.name(),
                        EntityTypes.Party.name());

                if(!hasExecutionErrors()) {
                    party = partyControl.getPartyByEntityInstanceForUpdate(entityInstance);
                }
            } else {
                party = PartyLogic.getInstance().getPartyByName(this, form.getPartyName());
            }
            
            if(!hasExecutionErrors()) {
                var partyLogic = PartyLogic.getInstance();

                if(!hasExecutionErrors()) {
                    var partyType = party.getLastDetail().getPartyType();
                    var securityRoleGroupName = getSecurityRoleGroupName(partyType);

                    if(securityRoleGroupName != null 
                            && SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, getParty(), securityRoleGroupName, UserLogin.name())) {
                        if(!hasExecutionErrors()) {
                            if(partyType.getAllowUserLogins()) {
                                partyLogic.checkPartyType(this, party, PartyTypes.CUSTOMER.name(), PartyTypes.EMPLOYEE.name(),
                                        PartyTypes.VENDOR.name());

                                if(!hasExecutionErrors()) {
                                    var userControl = getUserControl();
                                    var username = form.getUsername();
                                    var userLogin = userControl.getUserLoginByUsername(username);

                                    if(userLogin == null) {
                                        var recoveryQuestionName = form.getRecoveryQuestionName();
                                        var answer = form.getAnswer();
                                        var recoveryParameterCount = (recoveryQuestionName == null ? 0 : 1) + (answer == null ? 0 : 1);

                                        if(partyLogic.isPartyType(party, PartyTypes.CUSTOMER.name())) {
                                            if(recoveryParameterCount != 2) {
                                                // RecoveryQuestionName and Answer are required for CUSTOMERs
                                                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                                            }
                                        } else if(recoveryParameterCount != 0) {
                                            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                                        }

                                        if(!hasExecutionErrors()) {
                                            var password1 = form.getPassword1();
                                            var password2 = form.getPassword2();

                                            if(password1.equals(password2)) {
                                                var partyTypePasswordStringPolicy = PasswordStringPolicyLogic.getInstance().checkStringPassword(session,
                                                        getUserVisit(), this, partyType, null, null, password1);

                                                if(!hasExecutionErrors()) {
                                                    var recoveryQuestion = recoveryQuestionName == null ? null : userControl.getRecoveryQuestionByName(recoveryQuestionName);

                                                    if(recoveryQuestionName == null || recoveryQuestion != null) {
                                                        var userLoginPasswordType = userControl.getUserLoginPasswordTypeByName(UserConstants.UserLoginPasswordType_STRING);
                                                        var createdBy = getPartyPK();

                                                        userControl.createUserLogin(party, username, createdBy);

                                                        var userLoginPassword = userControl.createUserLoginPassword(party, userLoginPasswordType, createdBy);
                                                        userControl.createUserLoginPasswordString(userLoginPassword, password1, session.getStartTime(), false, createdBy);

                                                        if(partyTypePasswordStringPolicy != null && partyTypePasswordStringPolicy.getLastDetail().getForceChangeAfterCreate()) {
                                                            var userLoginStatus = userControl.getUserLoginStatusForUpdate(party);

                                                            userLoginStatus.setForceChange(true);
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
