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

import com.echothree.control.user.selector.common.form.GetSelectorsForm;
import com.echothree.control.user.selector.common.result.SelectorResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.selector.server.logic.SelectorTypeLogic;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.selector.server.factory.SelectorFactory;
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

public class GetSelectorsCommand
        extends BaseMultipleEntitiesCommand<Selector, GetSelectorsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Selector.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SelectorKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectorTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    /** Creates a new instance of GetSelectorsCommand */
    public GetSelectorsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    SelectorType selectorType;

    @Override
    protected Collection<Selector> getEntities() {
        var selectorControl = Session.getModelController(SelectorControl.class);

        selectorType = SelectorTypeLogic.getInstance().getSelectorTypeByName(this, form.getSelectorKindName(), form.getSelectorTypeName());

        return hasExecutionErrors() ? null : selectorControl.getSelectorsBySelectorType(selectorType);
    }

    @Override
    protected BaseResult getResult(Collection<Selector> entities) {
        var result = SelectorResultFactory.getGetSelectorsResult();

        if(entities != null) {
            var selectorControl = Session.getModelController(SelectorControl.class);

            if(session.hasLimit(SelectorFactory.class)) {
                result.setSelectorCount(selectorControl.countSelectorsBySelectorType(selectorType));
            }

            result.setSelectorType(selectorControl.getSelectorTypeTransfer(getUserVisit(), selectorType));
            result.setSelectors(selectorControl.getSelectorTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
