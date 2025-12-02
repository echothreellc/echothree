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
import com.echothree.control.user.user.common.form.DeleteUserLoginForm;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import static com.echothree.model.control.security.common.SecurityRoles.UserLogin;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.user.server.logic.UserKeyLogic;
import com.echothree.model.control.user.server.logic.UserSessionLogic;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.SecurityMessages;
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
public class DeleteUserLoginCommand
        extends BaseLoginCommand<DeleteUserLoginForm> {
    
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
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteUserLoginCommand */
    public DeleteUserLoginCommand() {
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
                var partyType = party.getLastDetail().getPartyType();
                var securityRoleGroupName = getSecurityRoleGroupName(partyType);

                if(securityRoleGroupName != null 
                        && SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(this, getParty(), securityRoleGroupName, UserLogin.name())) {
                    if(!hasExecutionErrors()) {
                        if(partyType.getAllowUserLogins()) {
                            var userControl = getUserControl();
                            var userLogin = userControl.getUserLoginForUpdate(party);

                            if(userLogin != null) {
                                UserKeyLogic.getInstance().clearUserKeysByParty(party);
                                UserSessionLogic.getInstance().deleteUserSessionsByParty(party);

                                userControl.deleteUserLogin(userLogin, getPartyPK());
                            } else {
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

        return null;
    }
    
}
