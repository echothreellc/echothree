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

package com.echothree.model.control.inventory.server.logic;

import com.echothree.control.user.inventory.common.spec.InventoryConditionUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateInventoryConditionNameException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryConditionException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryConditionNameException;
import com.echothree.model.control.inventory.server.control.InventoryConditionControl;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class InventoryConditionLogic
    extends BaseLogic {

    @Inject
    InventoryConditionControl inventoryConditionControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected InventoryConditionLogic() {
        super();
    }

    public static InventoryConditionLogic getInstance() {
        return CDI.current().select(InventoryConditionLogic.class).get();
    }

    public InventoryCondition createInventoryCondition(final ExecutionErrorAccumulator eea, final String inventoryConditionName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var inventoryCondition = inventoryConditionControl.getInventoryConditionByName(inventoryConditionName);

        if(inventoryCondition == null) {
            inventoryCondition = inventoryConditionControl.createInventoryCondition(inventoryConditionName, isDefault, sortOrder, createdBy);

            if(description != null) {
                inventoryConditionControl.createInventoryConditionDescription(inventoryCondition, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateInventoryConditionNameException.class, eea, ExecutionErrors.DuplicateInventoryConditionName.name(), inventoryConditionName);
        }

        return inventoryCondition;
    }

    public InventoryCondition getInventoryConditionByName(final ExecutionErrorAccumulator eea, final String inventoryConditionName,
            final EntityPermission entityPermission) {
        var inventoryCondition = inventoryConditionControl.getInventoryConditionByName(inventoryConditionName, entityPermission);

        if(inventoryCondition == null) {
            handleExecutionError(UnknownInventoryConditionNameException.class, eea, ExecutionErrors.UnknownInventoryConditionName.name(), inventoryConditionName);
        }

        return inventoryCondition;
    }

    public InventoryCondition getInventoryConditionByName(final ExecutionErrorAccumulator eea, final String inventoryConditionName) {
        return getInventoryConditionByName(eea, inventoryConditionName, EntityPermission.READ_ONLY);
    }

    public InventoryCondition getInventoryConditionByNameForUpdate(final ExecutionErrorAccumulator eea, final String inventoryConditionName) {
        return getInventoryConditionByName(eea, inventoryConditionName, EntityPermission.READ_WRITE);
    }

    public InventoryCondition getInventoryConditionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryConditionUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        InventoryCondition inventoryCondition = null;
        var inventoryConditionName = universalSpec.getInventoryConditionName();
        var parameterCount = (inventoryConditionName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    inventoryCondition = inventoryConditionControl.getDefaultInventoryCondition(entityPermission);

                    if(inventoryCondition == null) {
                        handleExecutionError(UnknownDefaultInventoryConditionException.class, eea, ExecutionErrors.UnknownDefaultInventoryCondition.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(inventoryConditionName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.InventoryCondition.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        inventoryCondition = inventoryConditionControl.getInventoryConditionByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    inventoryCondition = getInventoryConditionByName(eea, inventoryConditionName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return inventoryCondition;
    }

    public InventoryCondition getInventoryConditionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryConditionUniversalSpec universalSpec, boolean allowDefault) {
        return getInventoryConditionByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public InventoryCondition getInventoryConditionByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryConditionUniversalSpec universalSpec, boolean allowDefault) {
        return getInventoryConditionByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteInventoryCondition(final ExecutionErrorAccumulator eea, final InventoryCondition inventoryCondition,
            final BasePK deletedBy) {
        inventoryConditionControl.deleteInventoryCondition(inventoryCondition, deletedBy);
    }

}
