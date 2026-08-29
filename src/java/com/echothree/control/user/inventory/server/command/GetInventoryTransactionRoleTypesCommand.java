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

import com.echothree.control.user.inventory.common.form.GetInventoryTransactionRoleTypesForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.InventoryTransactionRoleControl;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRoleType;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionRoleTypeFactory;
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
public class GetInventoryTransactionRoleTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<InventoryTransactionRoleType, GetInventoryTransactionRoleTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryTransactionRoleType.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    InventoryTransactionRoleControl inventoryTransactionRoleControl;

    @Inject
    InventoryTransactionTypeControl inventoryTransactionTypeControl;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;

    /** Creates a new instance of GetInventoryTransactionRoleTypesCommand */
    public GetInventoryTransactionRoleTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    InventoryTransactionType inventoryTransactionType;

    @Override
    protected void handleForm() {
        var inventoryTransactionTypeName = form.getInventoryTransactionTypeName();

        inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(this, inventoryTransactionTypeName);
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : inventoryTransactionRoleControl.countInventoryTransactionRoleTypesByInventoryTransactionType(inventoryTransactionType);
    }

    @Override
    protected Collection<InventoryTransactionRoleType> getEntities() {
        return hasExecutionErrors() ? null : inventoryTransactionRoleControl.getInventoryTransactionRoleTypes(inventoryTransactionType);
    }

    @Override
    protected BaseResult getResult(Collection<InventoryTransactionRoleType> entities) {
        var result = InventoryResultFactory.getGetInventoryTransactionRoleTypesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setInventoryTransactionType(
                    inventoryTransactionTypeControl.getInventoryTransactionTypeTransfer(userVisit, inventoryTransactionType));

            if(session.hasLimit(InventoryTransactionRoleTypeFactory.class)) {
                result.setInventoryTransactionRoleTypeCount(getTotalEntities());
            }

            result.setInventoryTransactionRoleTypes(inventoryTransactionRoleControl.getInventoryTransactionRoleTypeTransfers(userVisit, entities));
        }

        return result;
    }
    
}
