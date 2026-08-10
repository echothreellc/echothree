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

import com.echothree.control.user.inventory.common.spec.InventoryCostingMethodUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateInventoryCostingMethodNameException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryCostingMethodException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryCostingMethodNameException;
import com.echothree.model.control.inventory.server.control.InventoryCostingMethodControl;
import com.echothree.model.data.inventory.server.entity.InventoryCostingMethod;
import com.echothree.model.data.inventory.server.value.InventoryCostingMethodDetailValue;
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
public class InventoryCostingMethodLogic
    extends BaseLogic {

    @Inject
    InventoryCostingMethodControl inventoryCostingMethodControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected InventoryCostingMethodLogic() {
        super();
    }

    public static InventoryCostingMethodLogic getInstance() {
        return CDI.current().select(InventoryCostingMethodLogic.class).get();
    }

    public InventoryCostingMethod createInventoryCostingMethod(final ExecutionErrorAccumulator eea, final String inventoryCostingMethodName,
            final Boolean isDefault, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var inventoryCostingMethod = inventoryCostingMethodControl.getInventoryCostingMethodByName(inventoryCostingMethodName);

        if(inventoryCostingMethod == null) {
            inventoryCostingMethod = inventoryCostingMethodControl.createInventoryCostingMethod(inventoryCostingMethodName,
                    isDefault, sortOrder, createdBy);

            if(description != null) {
                inventoryCostingMethodControl.createInventoryCostingMethodDescription(inventoryCostingMethod, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateInventoryCostingMethodNameException.class, eea, ExecutionErrors.DuplicateInventoryCostingMethodName.name(), inventoryCostingMethodName);
        }

        return inventoryCostingMethod;
    }

    public InventoryCostingMethod getInventoryCostingMethodByName(final ExecutionErrorAccumulator eea, final String inventoryCostingMethodName,
            final EntityPermission entityPermission) {
        var inventoryCostingMethod = inventoryCostingMethodControl.getInventoryCostingMethodByName(inventoryCostingMethodName, entityPermission);

        if(inventoryCostingMethod == null) {
            handleExecutionError(UnknownInventoryCostingMethodNameException.class, eea, ExecutionErrors.UnknownInventoryCostingMethodName.name(), inventoryCostingMethodName);
        }

        return inventoryCostingMethod;
    }

    public InventoryCostingMethod getInventoryCostingMethodByName(final ExecutionErrorAccumulator eea, final String inventoryCostingMethodName) {
        return getInventoryCostingMethodByName(eea, inventoryCostingMethodName, EntityPermission.READ_ONLY);
    }

    public InventoryCostingMethod getInventoryCostingMethodByNameForUpdate(final ExecutionErrorAccumulator eea, final String inventoryCostingMethodName) {
        return getInventoryCostingMethodByName(eea, inventoryCostingMethodName, EntityPermission.READ_WRITE);
    }

    public InventoryCostingMethod getInventoryCostingMethodByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryCostingMethodUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        InventoryCostingMethod inventoryCostingMethod = null;
        var inventoryCostingMethodName = universalSpec.getInventoryCostingMethodName();
        var parameterCount = (inventoryCostingMethodName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    inventoryCostingMethod = inventoryCostingMethodControl.getDefaultInventoryCostingMethod(entityPermission);

                    if(inventoryCostingMethod == null) {
                        handleExecutionError(UnknownDefaultInventoryCostingMethodException.class, eea, ExecutionErrors.UnknownDefaultInventoryCostingMethod.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(inventoryCostingMethodName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.InventoryCostingMethod.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        inventoryCostingMethod = inventoryCostingMethodControl.getInventoryCostingMethodByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    inventoryCostingMethod = getInventoryCostingMethodByName(eea, inventoryCostingMethodName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return inventoryCostingMethod;
    }

    public InventoryCostingMethod getInventoryCostingMethodByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryCostingMethodUniversalSpec universalSpec, boolean allowDefault) {
        return getInventoryCostingMethodByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public InventoryCostingMethod getInventoryCostingMethodByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryCostingMethodUniversalSpec universalSpec, boolean allowDefault) {
        return getInventoryCostingMethodByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateInventoryCostingMethodFromValue(final InventoryCostingMethodDetailValue inventoryCostingMethodDetailValue,
            final BasePK updatedBy) {
        inventoryCostingMethodControl.updateInventoryCostingMethodFromValue(inventoryCostingMethodDetailValue, updatedBy);
    }
    
    public void deleteInventoryCostingMethod(final ExecutionErrorAccumulator eea, final InventoryCostingMethod inventoryCostingMethod,
            final BasePK deletedBy) {
        inventoryCostingMethodControl.deleteInventoryCostingMethod(inventoryCostingMethod, deletedBy);
    }

}
