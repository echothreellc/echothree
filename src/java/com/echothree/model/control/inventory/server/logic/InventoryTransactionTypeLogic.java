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

package com.echothree.model.control.inventory.server.logic;

import com.echothree.control.user.inventory.common.spec.InventoryTransactionTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateInventoryTransactionTypeNameException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryTransactionTypeException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryTransactionTypeNameException;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.value.InventoryTransactionTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class InventoryTransactionTypeLogic
    extends BaseLogic {

    protected InventoryTransactionTypeLogic() {
        super();
    }

    public static InventoryTransactionTypeLogic getInstance() {
        return CDI.current().select(InventoryTransactionTypeLogic.class).get();
    }

    public InventoryTransactionType createInventoryTransactionType(final ExecutionErrorAccumulator eea, final String inventoryTransactionTypeName,
            final SequenceType inventoryTransactionSequenceType, final Workflow inventoryTransactionWorkflow,
            final WorkflowEntrance inventoryTransactionWorkflowEntrance, final Boolean isDefault, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);
        var inventoryTransactionType = inventoryTransactionTypeControl.getInventoryTransactionTypeByName(inventoryTransactionTypeName);

        if(inventoryTransactionType == null) {
            inventoryTransactionType = inventoryTransactionTypeControl.createInventoryTransactionType(inventoryTransactionTypeName, inventoryTransactionSequenceType, inventoryTransactionWorkflow,
                    inventoryTransactionWorkflowEntrance, isDefault, sortOrder, createdBy);

            if(description != null) {
                inventoryTransactionTypeControl.createInventoryTransactionTypeDescription(inventoryTransactionType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateInventoryTransactionTypeNameException.class, eea, ExecutionErrors.DuplicateInventoryTransactionTypeName.name(), inventoryTransactionTypeName);
        }

        return inventoryTransactionType;
    }

    public InventoryTransactionType getInventoryTransactionTypeByName(final ExecutionErrorAccumulator eea, final String inventoryTransactionTypeName,
            final EntityPermission entityPermission) {
        var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);
        var inventoryTransactionType = inventoryTransactionTypeControl.getInventoryTransactionTypeByName(inventoryTransactionTypeName, entityPermission);

        if(inventoryTransactionType == null) {
            handleExecutionError(UnknownInventoryTransactionTypeNameException.class, eea, ExecutionErrors.UnknownInventoryTransactionTypeName.name(), inventoryTransactionTypeName);
        }

        return inventoryTransactionType;
    }

    public InventoryTransactionType getInventoryTransactionTypeByName(final ExecutionErrorAccumulator eea, final String inventoryTransactionTypeName) {
        return getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionType getInventoryTransactionTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String inventoryTransactionTypeName) {
        return getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionType getInventoryTransactionTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryTransactionTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        InventoryTransactionType inventoryTransactionType = null;
        var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);
        var inventoryTransactionTypeName = universalSpec.getInventoryTransactionTypeName();
        var parameterCount = (inventoryTransactionTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    inventoryTransactionType = inventoryTransactionTypeControl.getDefaultInventoryTransactionType(entityPermission);

                    if(inventoryTransactionType == null) {
                        handleExecutionError(UnknownDefaultInventoryTransactionTypeException.class, eea, ExecutionErrors.UnknownDefaultInventoryTransactionType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(inventoryTransactionTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.InventoryTransactionType.name());

                    if(!eea.hasExecutionErrors()) {
                        inventoryTransactionType = inventoryTransactionTypeControl.getInventoryTransactionTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    inventoryTransactionType = getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return inventoryTransactionType;
    }

    public InventoryTransactionType getInventoryTransactionTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryTransactionTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getInventoryTransactionTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionType getInventoryTransactionTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryTransactionTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getInventoryTransactionTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateInventoryTransactionTypeFromValue(final InventoryTransactionTypeDetailValue inventoryTransactionTypeDetailValue,
            final BasePK updatedBy) {
        final var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);

        inventoryTransactionTypeControl.updateInventoryTransactionTypeFromValue(inventoryTransactionTypeDetailValue, updatedBy);
    }
    
    public void deleteInventoryTransactionType(final ExecutionErrorAccumulator eea, final InventoryTransactionType inventoryTransactionType,
            final BasePK deletedBy) {
        var inventoryTransactionTypeControl = Session.getModelController(InventoryTransactionTypeControl.class);

        inventoryTransactionTypeControl.deleteInventoryTransactionType(inventoryTransactionType, deletedBy);
    }

}
