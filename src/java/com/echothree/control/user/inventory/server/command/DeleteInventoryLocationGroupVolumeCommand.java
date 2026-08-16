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

import com.echothree.control.user.inventory.common.form.DeleteInventoryLocationGroupVolumeForm;
import com.echothree.model.control.inventory.server.control.InventoryLocationGroupControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.List;
import javax.inject.Inject;
import javax.enterprise.context.Dependent;

@Dependent
public class DeleteInventoryLocationGroupVolumeCommand
        extends BaseSimpleCommand<DeleteInventoryLocationGroupVolumeForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    InventoryLocationGroupControl inventoryLocationGroupControl;

    @Inject
    WarehouseControl warehouseControl;

    /** Creates a new instance of DeleteInventoryLocationGroupVolumeCommand */
    public DeleteInventoryLocationGroupVolumeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        var warehouseName = form.getWarehouseName();
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);

        if(warehouse != null) {
            var inventoryLocationGroupName = form.getInventoryLocationGroupName();
            var inventoryLocationGroup = inventoryLocationGroupControl.getInventoryLocationGroupByName(warehouse.getParty(),
                    inventoryLocationGroupName);

            if(inventoryLocationGroup != null) {
                var inventoryLocationGroupVolume = inventoryLocationGroupControl.getInventoryLocationGroupVolumeForUpdate(inventoryLocationGroup);

                if(inventoryLocationGroupVolume != null) {
                    inventoryLocationGroupControl.deleteInventoryLocationGroupVolume(inventoryLocationGroupVolume, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupVolume.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupName.name(), inventoryLocationGroupName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }

        return null;
    }

}
