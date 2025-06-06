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

package com.echothree.control.user.selector.server.command;

import com.echothree.control.user.selector.common.form.GetSelectorTypesForm;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.selector.server.logic.SelectorKindLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.selector.server.factory.SelectorTypeFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;

public class GetSelectorTypesCommand
        extends BaseMultipleEntitiesCommand<SelectorType, GetSelectorTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.SelectorType.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    /** Creates a new instance of GetSelectorTypesCommand */
    public GetSelectorTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    SelectorKind selectorKind;

    @Override
    protected Collection<SelectorType> getEntities() {
        var selectorControl = Session.getModelController(SelectorControl.class);

        selectorKind = SelectorKindLogic.getInstance().getSelectorKindByName(this, form.getSelectorKindName());

        return hasExecutionErrors() ? null : selectorControl.getSelectorTypes(selectorKind);
    }

    @Override
    protected BaseResult getResult(Collection<SelectorType> entities) {
        var result = SelectorResultFactory.getGetSelectorTypesResult();

        if(entities != null) {
            var selectorControl = Session.getModelController(SelectorControl.class);

            if(session.hasLimit(SelectorTypeFactory.class)) {
                result.setSelectorTypeCount(selectorControl.countSelectorTypesBySelectorKind(selectorKind));
            }

            result.setSelectorKind(selectorControl.getSelectorKindTransfer(getUserVisit(), selectorKind));
            result.setSelectorTypes(selectorControl.getSelectorTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
