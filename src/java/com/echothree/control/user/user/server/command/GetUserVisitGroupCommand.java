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

import com.echothree.control.user.user.common.form.GetUserVisitGroupForm;
import com.echothree.control.user.user.common.result.UserResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.control.user.server.logic.UserVisitGroupLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.server.entity.UserVisitGroup;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class GetUserVisitGroupCommand
        extends BaseSingleEntityCommand<UserVisitGroup, GetUserVisitGroupForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.UserVisitGroup.name(), SecurityRoles.Review.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("UserVisitGroupName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );
    }

    /** Creates a new instance of GetUserVisitGroupCommand */
    public GetUserVisitGroupCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected UserVisitGroup getEntity() {
        var userVisitGroup = UserVisitGroupLogic.getInstance().getUserVisitGroupByUniversalSpec(this, form);

        if(userVisitGroup != null) {
            sendEvent(userVisitGroup.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return userVisitGroup;
    }

    @Override
    protected BaseResult getResult(UserVisitGroup userVisitGroup) {
        var userControl = Session.getModelController(UserControl.class);
        var result = UserResultFactory.getGetUserVisitGroupResult();

        if(userVisitGroup != null) {
            result.setUserVisitGroup(userControl.getUserVisitGroupTransfer(getUserVisit(), userVisitGroup));
        }

        return result;
    }

}
