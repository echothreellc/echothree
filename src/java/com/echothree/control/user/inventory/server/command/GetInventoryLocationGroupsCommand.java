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

import com.echothree.control.user.inventory.common.form.GetInventoryLocationGroupsForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.inventory.server.factory.InventoryLocationGroupFactory;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
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
public class GetInventoryLocationGroupsCommand
        extends BasePaginatedMultipleEntitiesCommand<InventoryLocationGroup, GetInventoryLocationGroupsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.InventoryLocationGroup.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    /** Creates a new instance of GetInventoryLocationGroupsCommand */
    public GetInventoryLocationGroupsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Inject
    InventoryControl inventoryControl;

    @Inject
    WarehouseControl warehouseControl;

    Warehouse warehouse;
    Party warehouseParty;

    @Override
    protected void handleForm() {
        var warehouseName = form.getWarehouseName();

        warehouse = warehouseControl.getWarehouseByName(warehouseName);

        if(warehouse != null) {
            warehouseParty = warehouse.getParty();
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return warehouseParty == null ? null : inventoryControl.countInventoryLocationGroupsByWarehouseParty(warehouseParty);
    }

    @Override
    protected Collection<InventoryLocationGroup> getEntities() {
        return warehouseParty == null ? null : inventoryControl.getInventoryLocationGroupsByWarehouseParty(warehouseParty);
    }

    @Override
    protected BaseResult getResult(Collection<InventoryLocationGroup> entities) {
        var result = InventoryResultFactory.getGetInventoryLocationGroupsResult();

        if(entities != null) {
            result.setWarehouse(warehouseControl.getWarehouseTransfer(getUserVisit(), warehouse));

            if(session.hasLimit(InventoryLocationGroupFactory.class)) {
                result.setInventoryLocationGroupCount(getTotalEntities());
            }

            result.setInventoryLocationGroups(inventoryControl.getInventoryLocationGroupTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
