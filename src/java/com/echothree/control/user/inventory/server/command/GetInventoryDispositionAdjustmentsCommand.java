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

import com.echothree.control.user.inventory.common.form.GetInventoryDispositionAdjustmentsForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.InventoryDispositionAdjustmentControl;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.control.inventory.server.logic.InventoryTransactionTypeLogic;
import com.echothree.model.control.inventory.server.logic.InventoryDispositionLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionAdjustment;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.entity.InventoryDisposition;
import com.echothree.model.data.inventory.server.factory.InventoryDispositionAdjustmentFactory;
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
public class GetInventoryDispositionAdjustmentsCommand
        extends BasePaginatedMultipleEntitiesCommand<InventoryDispositionAdjustment, GetInventoryDispositionAdjustmentsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryDispositionAdjustment.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("InventoryTransactionTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    InventoryDispositionAdjustmentControl inventoryDispositionAdjustmentControl;

    @Inject
    InventoryTransactionTypeControl inventoryTransactionTypeControl;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;

    @Inject
    InventoryDispositionLogic inventoryDispositionLogic;

    /** Creates a new instance of GetInventoryDispositionAdjustmentsCommand */
    public GetInventoryDispositionAdjustmentsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    InventoryTransactionType inventoryTransactionType;
    InventoryDisposition inventoryDisposition;

    @Override
    protected void handleForm() {
        var inventoryTransactionTypeName = form.getInventoryTransactionTypeName();

        inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(this, inventoryTransactionTypeName);
        if(!hasExecutionErrors()) {
            inventoryDisposition = inventoryDispositionLogic.getInventoryDispositionByName(this, inventoryTransactionType,
                    form.getInventoryDispositionName());
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null
                : inventoryDispositionAdjustmentControl.countInventoryDispositionAdjustmentsByInventoryDisposition(inventoryDisposition);
    }

    @Override
    protected Collection<InventoryDispositionAdjustment> getEntities() {
        return hasExecutionErrors() ? null : inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustments(inventoryDisposition);
    }

    @Override
    protected BaseResult getResult(Collection<InventoryDispositionAdjustment> entities) {
        var result = InventoryResultFactory.getGetInventoryDispositionAdjustmentsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setInventoryTransactionType(
                    inventoryTransactionTypeControl.getInventoryTransactionTypeTransfer(userVisit, inventoryTransactionType));

            if(session.hasLimit(InventoryDispositionAdjustmentFactory.class)) {
                result.setInventoryDispositionAdjustmentCount(getTotalEntities());
            }

            result.setInventoryDispositionAdjustments(inventoryDispositionAdjustmentControl
                    .getInventoryDispositionAdjustmentTransfers(userVisit, entities));
        }

        return result;
    }
    
}
