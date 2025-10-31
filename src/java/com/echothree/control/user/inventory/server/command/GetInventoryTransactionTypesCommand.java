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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.form.GetInventoryTransactionTypesForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetInventoryTransactionTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<InventoryTransactionType, GetInventoryTransactionTypesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryTransactionType.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    /** Creates a new instance of GetInventoryTransactionTypesCommand */
    public GetInventoryTransactionTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);

        return inventoryTransactionTypeControl.countInventoryTransactionTypes();
    }

    @Override
    protected Collection<InventoryTransactionType> getEntities() {
        var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);

        return inventoryTransactionTypeControl.getInventoryTransactionTypes();
    }

    @Override
    protected BaseResult getResult(Collection<InventoryTransactionType> entities) {
        var result = InventoryResultFactory.getGetInventoryTransactionTypesResult();

        if(entities != null) {
            var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);

            if(session.hasLimit(InventoryTransactionTypeFactory.class)) {
                result.setInventoryTransactionTypeCount(getTotalEntities());
            }

            result.setInventoryTransactionTypes(inventoryTransactionTypeControl.getInventoryTransactionTypeTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
