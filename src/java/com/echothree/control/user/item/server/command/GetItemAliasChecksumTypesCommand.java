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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetItemAliasChecksumTypesForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.ItemAliasChecksumType;
import com.echothree.model.data.item.server.factory.ItemAliasChecksumTypeFactory;
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

public class GetItemAliasChecksumTypesCommand
        extends BaseMultipleEntitiesCommand<ItemAliasChecksumType, GetItemAliasChecksumTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemAliasChecksumType.name(), SecurityRoles.List.name())
                )))
        )));

        FORM_FIELD_DEFINITIONS = List.of(
        );
    }

    /** Creates a new instance of GetItemAliasChecksumTypesCommand */
    public GetItemAliasChecksumTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected Collection<ItemAliasChecksumType> getEntities() {
        var itemControl = Session.getModelController(ItemControl.class);

        return itemControl.getItemAliasChecksumTypes();
    }

    @Override
    protected BaseResult getResult(Collection<ItemAliasChecksumType> entities) {
        var result = ItemResultFactory.getGetItemAliasChecksumTypesResult();
        var itemControl = Session.getModelController(ItemControl.class);

        if(session.hasLimit(ItemAliasChecksumTypeFactory.class)) {
            result.setItemAliasChecksumTypeCount(itemControl.countItemAliasChecksumTypes());
        }

        result.setItemAliasChecksumTypes(itemControl.getItemAliasChecksumTypeTransfers(getUserVisit(), entities));

        return result;
    }

}
