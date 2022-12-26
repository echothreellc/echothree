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
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetSecurityRoleGroupsCommand
        extends BaseMultipleEntitiesCommand<SecurityRoleGroup, GetSecurityRoleGroupsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SecurityRoleGroup.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ParentSecurityRoleGroupName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetSecurityRoleGroupsCommand */
    public GetSecurityRoleGroupsCommand(UserVisitPK userVisitPK, GetSecurityRoleGroupsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    SecurityRoleGroup parentSecurityRoleGroup;

    @Override
    protected Collection<SecurityRoleGroup> getEntities() {
        var securityControl = Session.getModelController(SecurityControl.class);
        var parentSecurityRoleGroupName = form.getParentSecurityRoleGroupName();
        Collection<SecurityRoleGroup> entities = null;

        parentSecurityRoleGroup = parentSecurityRoleGroupName == null ? null : securityControl.getSecurityRoleGroupByName(parentSecurityRoleGroupName);

        if(parentSecurityRoleGroupName == null || parentSecurityRoleGroup != null) {
            entities = parentSecurityRoleGroup == null? securityControl.getSecurityRoleGroups():
                    securityControl.getSecurityRoleGroupsByParentSecurityRoleGroup(parentSecurityRoleGroup);
        } else {
            addExecutionError(ExecutionErrors.UnknownParentSecurityRoleGroupName.name(), parentSecurityRoleGroupName);
        }

        return entities;
    }

    @Override
    protected BaseResult getTransfers(Collection<SecurityRoleGroup> entities) {
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
