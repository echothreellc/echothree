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

import com.echothree.control.user.inventory.common.form.CreateInventoryLocationGroupForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.inventory.common.workflow.InventoryLocationGroupStatusConstants;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateInventoryLocationGroupCommand
        extends BaseSimpleCommand<CreateInventoryLocationGroupForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
        new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, true, null, null),
        new FieldDefinition("InventoryLocationGroupName", FieldType.ENTITY_NAME, true, null, null),
        new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
        new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
        new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of CreateInventoryLocationGroupCommand */
    public CreateInventoryLocationGroupCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = InventoryResultFactory.getCreateInventoryLocationGroupResult();
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var warehouseName = form.getWarehouseName();
        var warehouse = warehouseControl.getWarehouseByName(warehouseName);
        
        if(warehouse != null) {
            var inventoryControl = Session.getModelController(InventoryControl.class);
            var warehouseParty = warehouse.getParty();
            var inventoryLocationGroupName = form.getInventoryLocationGroupName();
            var inventoryLocationGroup = inventoryControl.getInventoryLocationGroupByName(warehouseParty,
                    inventoryLocationGroupName);
            
            if(inventoryLocationGroup == null) {
                var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
                var workflowControl = Session.getModelController(WorkflowControl.class);
                var createdBy = getPartyPK();
                var isDefault = Boolean.valueOf(form.getIsDefault());
                var sortOrder = Integer.valueOf(form.getSortOrder());
                var description = form.getDescription();
                
                inventoryLocationGroup = inventoryControl.createInventoryLocationGroup(warehouseParty, inventoryLocationGroupName,
                        isDefault, sortOrder, getPartyPK());

                var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(inventoryLocationGroup.getPrimaryKey());
                workflowControl.addEntityToWorkflowUsingNames(null, InventoryLocationGroupStatusConstants.Workflow_INVENTORY_LOCATION_GROUP_STATUS,
                        InventoryLocationGroupStatusConstants.WorkflowEntrance_NEW_INVENTORY_LOCATION_GROUP, entityInstance, null, null, createdBy);
                
                if(description != null) {
                    inventoryControl.createInventoryLocationGroupDescription(inventoryLocationGroup, getPreferredLanguage(),
                            description, createdBy);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateInventoryLocationGroupName.name(), inventoryLocationGroupName);
            }

            if(inventoryLocationGroup != null) {
                result.setEntityRef(inventoryLocationGroup.getPrimaryKey().getEntityRef());
                result.setWarehouseName(warehouse.getWarehouseName());
                result.setInventoryLocationGroupName(inventoryLocationGroup.getLastDetail().getInventoryLocationGroupName());
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWarehouseName.name(), warehouseName);
        }
        
        return result;
    }
    
}
