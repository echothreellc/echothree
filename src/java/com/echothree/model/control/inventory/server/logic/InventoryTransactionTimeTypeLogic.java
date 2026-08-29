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

import com.echothree.control.user.inventory.common.spec.InventoryTransactionTimeTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateInventoryTransactionTimeTypeNameException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryTransactionTimeTypeException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryTransactionTypeException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryTransactionTimeTypeNameException;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTimeControl;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTimeType;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.value.InventoryTransactionTimeTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.validation.ParameterUtils;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InventoryTransactionTimeTypeLogic
        extends BaseLogic {

    @Inject
    InventoryTransactionTimeControl inventoryTransactionTimeControl;

    @Inject
    InventoryTransactionTypeControl inventoryTransactionTypeControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;

    protected InventoryTransactionTimeTypeLogic() {
        super();
    }

    public InventoryTransactionTimeType createInventoryTransactionTimeType(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionTimeTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryTransactionTimeType inventoryTransactionTimeType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryTransactionTimeType = createInventoryTransactionTimeType(eea, inventoryTransactionType, inventoryTransactionTimeTypeName,
                    isDefault, sortOrder, language, description, createdBy);
        }

        return inventoryTransactionTimeType;
    }

    public InventoryTransactionTimeType createInventoryTransactionTimeType(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransactionTimeTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var inventoryTransactionTimeType = inventoryTransactionTimeControl.getInventoryTransactionTimeTypeByName(inventoryTransactionType,
                inventoryTransactionTimeTypeName);

        if(inventoryTransactionTimeType == null) {
            inventoryTransactionTimeType = inventoryTransactionTimeControl.createInventoryTransactionTimeType(inventoryTransactionType,
                    inventoryTransactionTimeTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                inventoryTransactionTimeControl.createInventoryTransactionTimeTypeDescription(inventoryTransactionTimeType, language, description,
                        createdBy);
            }
        } else {
            handleExecutionError(DuplicateInventoryTransactionTimeTypeNameException.class, eea,
                    ExecutionErrors.DuplicateInventoryTransactionTimeTypeName.name(),
                    inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName(), inventoryTransactionTimeTypeName);
        }

        return inventoryTransactionTimeType;
    }

    public InventoryTransactionTimeType getInventoryTransactionTimeTypeByName(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransactionTimeTypeName,
            final EntityPermission entityPermission) {
        var inventoryTransactionTimeType = inventoryTransactionTimeControl.getInventoryTransactionTimeTypeByName(inventoryTransactionType,
                inventoryTransactionTimeTypeName, entityPermission);

        if(inventoryTransactionTimeType == null) {
            handleExecutionError(UnknownInventoryTransactionTimeTypeNameException.class, eea,
                    ExecutionErrors.UnknownInventoryTransactionTimeTypeName.name(),
                    inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName(), inventoryTransactionTimeTypeName);
        }

        return inventoryTransactionTimeType;
    }

    public InventoryTransactionTimeType getInventoryTransactionTimeTypeByName(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransactionTimeTypeName) {
        return getInventoryTransactionTimeTypeByName(eea, inventoryTransactionType, inventoryTransactionTimeTypeName, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionTimeType getInventoryTransactionTimeTypeByNameForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransactionTimeTypeName) {
        return getInventoryTransactionTimeTypeByName(eea, inventoryTransactionType, inventoryTransactionTimeTypeName, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionTimeType getInventoryTransactionTimeTypeByName(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionTimeTypeName,
            final EntityPermission entityPermission) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryTransactionTimeType inventoryTransactionTimeType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryTransactionTimeType = getInventoryTransactionTimeTypeByName(eea, inventoryTransactionType, inventoryTransactionTimeTypeName,
                    entityPermission);
        }

        return inventoryTransactionTimeType;
    }

    public InventoryTransactionTimeType getInventoryTransactionTimeTypeByName(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionTimeTypeName) {
        return getInventoryTransactionTimeTypeByName(eea, inventoryTransactionTypeName, inventoryTransactionTimeTypeName, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionTimeType getInventoryTransactionTimeTypeByNameForUpdate(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionTimeTypeName) {
        return getInventoryTransactionTimeTypeByName(eea, inventoryTransactionTypeName, inventoryTransactionTimeTypeName,
                EntityPermission.READ_WRITE);
    }

    public InventoryTransactionTimeType getInventoryTransactionTimeTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryTransactionTimeTypeUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var inventoryTransactionTypeName = universalSpec.getInventoryTransactionTypeName();
        var inventoryTransactionTimeTypeName = universalSpec.getInventoryTransactionTimeTypeName();
        var nameParameterCount = ParameterUtils.getInstance().countNonNullParameters(inventoryTransactionTypeName, inventoryTransactionTimeTypeName);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        InventoryTransactionTimeType inventoryTransactionTimeType = null;

        if(nameParameterCount < 3 && possibleEntitySpecs == 0) {
            InventoryTransactionType inventoryTransactionType = null;

            if(inventoryTransactionTypeName == null) {
                if(allowDefault) {
                    inventoryTransactionType = inventoryTransactionTypeControl.getDefaultInventoryTransactionType();

                    if(inventoryTransactionType == null) {
                        handleExecutionError(UnknownDefaultInventoryTransactionTypeException.class, eea,
                                ExecutionErrors.UnknownDefaultInventoryTransactionType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            } else {
                inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                if(inventoryTransactionTimeTypeName == null) {
                    if(allowDefault) {
                        inventoryTransactionTimeType = 
                                inventoryTransactionTimeControl.getDefaultInventoryTransactionTimeType(inventoryTransactionType, entityPermission);

                        if(inventoryTransactionTimeType == null) {
                            handleExecutionError(UnknownDefaultInventoryTransactionTimeTypeException.class, eea,
                                    ExecutionErrors.UnknownDefaultInventoryTransactionTimeType.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    inventoryTransactionTimeType = getInventoryTransactionTimeTypeByName(eea, inventoryTransactionType,
                            inventoryTransactionTimeTypeName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.InventoryTransactionTimeType.name());

            if(eea == null || !eea.hasExecutionErrors()) {
                inventoryTransactionTimeType = inventoryTransactionTimeControl.getInventoryTransactionTimeTypeByEntityInstance(entityInstance,
                        entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return inventoryTransactionTimeType;
    }

    public InventoryTransactionTimeType getInventoryTransactionTimeTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryTransactionTimeTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getInventoryTransactionTimeTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionTimeType getInventoryTransactionTimeTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryTransactionTimeTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getInventoryTransactionTimeTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateInventoryTransactionTimeTypeFromValue(final ExecutionErrorAccumulator eea,
            InventoryTransactionTimeTypeDetailValue inventoryTransactionTimeTypeDetailValue,
            BasePK updatedBy) {
        inventoryTransactionTimeControl.updateInventoryTransactionTimeTypeFromValue(inventoryTransactionTimeTypeDetailValue, updatedBy);
    }

    public void deleteInventoryTransactionTimeType(final ExecutionErrorAccumulator eea,
            final InventoryTransactionTimeType inventoryTransactionTimeType,
            final BasePK deletedBy) {
        inventoryTransactionTimeControl.deleteInventoryTransactionTimeType(inventoryTransactionTimeType, deletedBy);
    }

}
