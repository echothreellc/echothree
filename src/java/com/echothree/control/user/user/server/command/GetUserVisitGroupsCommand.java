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

import com.echothree.control.user.user.common.form.GetUserVisitGroupsForm;
import com.echothree.control.user.user.common.result.UserResultFactory;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.server.entity.UserVisitGroup;
import com.echothree.model.data.user.server.factory.UserVisitGroupFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetUserVisitGroupsCommand
        extends BaseMultipleEntitiesCommand<UserVisitGroup, GetUserVisitGroupsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.UserVisitGroup.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of();
    }

    /** Creates a new instance of GetUserVisitGroupsCommand */
    public GetUserVisitGroupsCommand(UserVisitPK userVisitPK, GetUserVisitGroupsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected Collection<UserVisitGroup> getEntities() {
        var userControl = Session.getModelController(UserControl.class);

        return userControl.getUserVisitGroups();
    }

    @Override
    protected BaseResult getResult(Collection<UserVisitGroup> entities) {
        var result = UserResultFactory.getGetUserVisitGroupsResult();

        if(entities != null) {
            var userControl = Session.getModelController(UserControl.class);

            if(session.hasLimit(UserVisitGroupFactory.class)) {
                result.setUserVisitGroupCount(userControl.countUserVisitGroups());
            }

            result.setUserVisitGroups(userControl.getUserVisitGroupTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
