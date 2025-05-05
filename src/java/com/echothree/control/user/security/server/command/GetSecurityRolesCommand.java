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

package com.echothree.control.user.security.server.command;

import com.echothree.control.user.security.common.form.GetSecurityRolesForm;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.security.server.logic.SecurityRoleGroupLogic;
import com.echothree.model.data.security.server.entity.SecurityRole;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;

public class GetSecurityRolesCommand
        extends BasePaginatedMultipleEntitiesCommand<SecurityRole, GetSecurityRolesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.SecurityRole.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SecurityRoleGroupName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetSecurityRolesCommand */
    public GetSecurityRolesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    SecurityRoleGroup securityRoleGroup;

    @Override
    protected void handleForm() {
        var securityRoleGroupName = form.getSecurityRoleGroupName();

        securityRoleGroup = SecurityRoleGroupLogic.getInstance().getSecurityRoleGroupByName(this, securityRoleGroupName);
    }

    @Override
    protected Long getTotalEntities() {
        var securityControl = Session.getModelController(SecurityControl.class);

        return hasExecutionErrors() ? null :
                securityControl.countSecurityRolesBySecurityRoleGroup(securityRoleGroup);
    }

    @Override
    protected Collection<SecurityRole> getEntities() {
        Collection<SecurityRole> entities = null;

        if(!hasExecutionErrors()) {
            var securityControl = Session.getModelController(SecurityControl.class);

            entities = securityControl.getSecurityRoles(securityRoleGroup);
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<SecurityRole> entities) {
        var result = SecurityResultFactory.getGetSecurityRolesResult();
        var securityControl = Session.getModelController(SecurityControl.class);
        var userVisit = getUserVisit();

        result.setSecurityRoleGroup(securityControl.getSecurityRoleGroupTransfer(userVisit, securityRoleGroup));

        if(entities != null) {
            result.setSecurityRoles(securityControl.getSecurityRoleTransfers(userVisit, entities));
        }

        return result;
    }

}
