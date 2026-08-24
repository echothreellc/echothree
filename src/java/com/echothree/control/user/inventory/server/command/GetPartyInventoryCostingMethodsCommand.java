// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.form.GetPartyInventoryCostingMethodsForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.server.control.InventoryCostingMethodControl;
import com.echothree.model.control.inventory.server.logic.InventoryCostingMethodLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryCostingMethod;
import com.echothree.model.data.inventory.server.entity.PartyInventoryCostingMethod;
import com.echothree.model.data.inventory.server.factory.PartyInventoryCostingMethodFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyInventoryCostingMethodsCommand
        extends BasePaginatedMultipleEntitiesCommand<PartyInventoryCostingMethod, GetPartyInventoryCostingMethodsForm> {

    private static final CommandSecurityDefinition COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
            new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
            new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                    new SecurityRoleDefinition(SecurityRoleGroups.InventoryCostingMethod.name(), SecurityRoles.List.name())
            ))
    ));

    private static final List<FieldDefinition> FORM_FIELD_DEFINITIONS = List.of(
            new FieldDefinition("InventoryCostingMethodName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
            new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
    );

    @Inject
    InventoryCostingMethodControl inventoryCostingMethodControl;

    @Inject
    InventoryCostingMethodLogic inventoryCostingMethodLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    private InventoryCostingMethod inventoryCostingMethod;

    public GetPartyInventoryCostingMethodsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        var inventoryCostingMethodName = form.getInventoryCostingMethodName();
        var parameterCount = (inventoryCostingMethodName == null ? 0 : 1)
                + entityInstanceLogic.countPossibleEntitySpecs(form);

        if(parameterCount > 0) {
            inventoryCostingMethod = inventoryCostingMethodLogic.getInventoryCostingMethodByUniversalSpec(this, form, false);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : inventoryCostingMethod == null
                ? inventoryCostingMethodControl.countPartyInventoryCostingMethods()
                : inventoryCostingMethodControl.countPartyInventoryCostingMethodsByInventoryCostingMethod(inventoryCostingMethod);
    }

    @Override
    protected Collection<PartyInventoryCostingMethod> getEntities() {
        return hasExecutionErrors() ? null : inventoryCostingMethod == null
                ? inventoryCostingMethodControl.getPartyInventoryCostingMethods()
                : inventoryCostingMethodControl.getPartyInventoryCostingMethodsByInventoryCostingMethod(inventoryCostingMethod);
    }

    @Override
    protected BaseResult getResult(Collection<PartyInventoryCostingMethod> entities) {
        var result = InventoryResultFactory.getGetPartyInventoryCostingMethodsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(inventoryCostingMethod != null) {
                result.setInventoryCostingMethod(inventoryCostingMethodControl.getInventoryCostingMethodTransfer(userVisit,
                        inventoryCostingMethod));
            }

            if(session.hasLimit(PartyInventoryCostingMethodFactory.class)) {
                result.setPartyInventoryCostingMethodCount(getTotalEntities());
            }

            result.setPartyInventoryCostingMethods(inventoryCostingMethodControl.getPartyInventoryCostingMethodTransfers(
                    userVisit, entities));
        }

        return result;
    }
}
