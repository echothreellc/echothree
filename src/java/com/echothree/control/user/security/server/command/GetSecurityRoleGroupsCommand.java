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

import com.echothree.control.user.security.common.form.GetSecurityRoleGroupsForm;
import com.echothree.control.user.security.common.result.SecurityResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.data.security.server.entity.SecurityRoleGroup;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetSecurityRoleGroupsCommand
        extends BasePaginatedMultipleEntitiesCommand<SecurityRoleGroup, GetSecurityRoleGroupsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.SecurityRoleGroup.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ParentSecurityRoleGroupName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetSecurityRoleGroupsCommand */
    public GetSecurityRoleGroupsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    SecurityRoleGroup parentSecurityRoleGroup;

    @Override
    protected void handleForm() {
        var securityControl = Session.getModelController(SecurityControl.class);
        var parentSecurityRoleGroupName = form.getParentSecurityRoleGroupName();

        parentSecurityRoleGroup = parentSecurityRoleGroupName == null ? null : securityControl.getSecurityRoleGroupByName(parentSecurityRoleGroupName);

        if(parentSecurityRoleGroupName != null && parentSecurityRoleGroup == null) {
            addExecutionError(ExecutionErrors.UnknownParentSecurityRoleGroupName.name(), parentSecurityRoleGroupName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        var securityControl = Session.getModelController(SecurityControl.class);

        return hasExecutionErrors() ? null :
                parentSecurityRoleGroup == null ?
                        securityControl.countSecurityRoleGroups() :
                        securityControl.countSecurityRoleGroupsByParentSecurityRoleGroup(parentSecurityRoleGroup);
    }

    @Override
    protected Collection<SecurityRoleGroup> getEntities() {
        var securityControl = Session.getModelController(SecurityControl.class);
        Collection<SecurityRoleGroup> entities = null;

        if(!hasExecutionErrors()) {
            entities = parentSecurityRoleGroup == null ?
                    securityControl.getSecurityRoleGroups():
                    securityControl.getSecurityRoleGroupsByParentSecurityRoleGroup(parentSecurityRoleGroup);
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<SecurityRoleGroup> entities) {
        var result = SecurityResultFactory.getGetSecurityRoleGroupsResult();
        var securityControl = Session.getModelController(SecurityControl.class);
        var userVisit = getUserVisit();

        result.setParentSecurityRoleGroup(parentSecurityRoleGroup == null ? null : securityControl.getSecurityRoleGroupTransfer(userVisit, parentSecurityRoleGroup));

        if(entities != null) {
            result.setSecurityRoleGroups(securityControl.getSecurityRoleGroupTransfers(userVisit, entities));
        }

        return result;
    }

}
