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

import com.echothree.control.user.inventory.common.spec.InventoryTransactionRoleTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateInventoryTransactionRoleTypeNameException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryTransactionRoleTypeException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryTransactionTypeException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryTransactionRoleTypeNameException;
import com.echothree.model.control.inventory.server.control.InventoryTransactionRoleControl;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRoleType;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.value.InventoryTransactionRoleTypeDetailValue;
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
public class InventoryTransactionRoleTypeLogic
        extends BaseLogic {

    @Inject
    InventoryTransactionRoleControl inventoryTransactionRoleControl;

    @Inject
    InventoryTransactionTypeControl inventoryTransactionTypeControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;

    protected InventoryTransactionRoleTypeLogic() {
        super();
    }

    public InventoryTransactionRoleType createInventoryTransactionRoleType(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionRoleTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryTransactionRoleType inventoryTransactionRoleType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryTransactionRoleType = createInventoryTransactionRoleType(eea, inventoryTransactionType, inventoryTransactionRoleTypeName,
                    isDefault, sortOrder, language, description, createdBy);
        }

        return inventoryTransactionRoleType;
    }

    public InventoryTransactionRoleType createInventoryTransactionRoleType(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransactionRoleTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var inventoryTransactionRoleType = inventoryTransactionRoleControl.getInventoryTransactionRoleTypeByName(inventoryTransactionType,
                inventoryTransactionRoleTypeName);

        if(inventoryTransactionRoleType == null) {
            inventoryTransactionRoleType = inventoryTransactionRoleControl.createInventoryTransactionRoleType(inventoryTransactionType,
                    inventoryTransactionRoleTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                inventoryTransactionRoleControl.createInventoryTransactionRoleTypeDescription(inventoryTransactionRoleType, language, description,
                        createdBy);
            }
        } else {
            handleExecutionError(DuplicateInventoryTransactionRoleTypeNameException.class, eea,
                    ExecutionErrors.DuplicateInventoryTransactionRoleTypeName.name(),
                    inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName(), inventoryTransactionRoleTypeName);
        }

        return inventoryTransactionRoleType;
    }

    public InventoryTransactionRoleType getInventoryTransactionRoleTypeByName(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransactionRoleTypeName,
            final EntityPermission entityPermission) {
        var inventoryTransactionRoleType = inventoryTransactionRoleControl.getInventoryTransactionRoleTypeByName(inventoryTransactionType,
                inventoryTransactionRoleTypeName, entityPermission);

        if(inventoryTransactionRoleType == null) {
            handleExecutionError(UnknownInventoryTransactionRoleTypeNameException.class, eea,
                    ExecutionErrors.UnknownInventoryTransactionRoleTypeName.name(),
                    inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName(), inventoryTransactionRoleTypeName);
        }

        return inventoryTransactionRoleType;
    }

    public InventoryTransactionRoleType getInventoryTransactionRoleTypeByName(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransactionRoleTypeName) {
        return getInventoryTransactionRoleTypeByName(eea, inventoryTransactionType, inventoryTransactionRoleTypeName, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionRoleType getInventoryTransactionRoleTypeByNameForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransactionRoleTypeName) {
        return getInventoryTransactionRoleTypeByName(eea, inventoryTransactionType, inventoryTransactionRoleTypeName, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionRoleType getInventoryTransactionRoleTypeByName(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionRoleTypeName,
            final EntityPermission entityPermission) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryTransactionRoleType inventoryTransactionRoleType = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryTransactionRoleType = getInventoryTransactionRoleTypeByName(eea, inventoryTransactionType, inventoryTransactionRoleTypeName,
                    entityPermission);
        }

        return inventoryTransactionRoleType;
    }

    public InventoryTransactionRoleType getInventoryTransactionRoleTypeByName(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionRoleTypeName) {
        return getInventoryTransactionRoleTypeByName(eea, inventoryTransactionTypeName, inventoryTransactionRoleTypeName, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionRoleType getInventoryTransactionRoleTypeByNameForUpdate(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionRoleTypeName) {
        return getInventoryTransactionRoleTypeByName(eea, inventoryTransactionTypeName, inventoryTransactionRoleTypeName,
                EntityPermission.READ_WRITE);
    }

    public InventoryTransactionRoleType getInventoryTransactionRoleTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryTransactionRoleTypeUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var inventoryTransactionTypeName = universalSpec.getInventoryTransactionTypeName();
        var inventoryTransactionRoleTypeName = universalSpec.getInventoryTransactionRoleTypeName();
        var nameParameterCount = ParameterUtils.getInstance().countNonNullParameters(inventoryTransactionTypeName, inventoryTransactionRoleTypeName);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        InventoryTransactionRoleType inventoryTransactionRoleType = null;

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
                if(inventoryTransactionRoleTypeName == null) {
                    if(allowDefault) {
                        inventoryTransactionRoleType = 
                                inventoryTransactionRoleControl.getDefaultInventoryTransactionRoleType(inventoryTransactionType, entityPermission);

                        if(inventoryTransactionRoleType == null) {
                            handleExecutionError(UnknownDefaultInventoryTransactionRoleTypeException.class, eea,
                                    ExecutionErrors.UnknownDefaultInventoryTransactionRoleType.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    inventoryTransactionRoleType = getInventoryTransactionRoleTypeByName(eea, inventoryTransactionType,
                            inventoryTransactionRoleTypeName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.InventoryTransactionRoleType.name());

            if(eea == null || !eea.hasExecutionErrors()) {
                inventoryTransactionRoleType = inventoryTransactionRoleControl.getInventoryTransactionRoleTypeByEntityInstance(entityInstance,
                        entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return inventoryTransactionRoleType;
    }

    public InventoryTransactionRoleType getInventoryTransactionRoleTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryTransactionRoleTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getInventoryTransactionRoleTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionRoleType getInventoryTransactionRoleTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryTransactionRoleTypeUniversalSpec universalSpec,
            boolean allowDefault) {
        return getInventoryTransactionRoleTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateInventoryTransactionRoleTypeFromValue(final ExecutionErrorAccumulator eea,
            InventoryTransactionRoleTypeDetailValue inventoryTransactionRoleTypeDetailValue,
            BasePK updatedBy) {
        inventoryTransactionRoleControl.updateInventoryTransactionRoleTypeFromValue(inventoryTransactionRoleTypeDetailValue, updatedBy);
    }

    public void deleteInventoryTransactionRoleType(final ExecutionErrorAccumulator eea,
            final InventoryTransactionRoleType inventoryTransactionRoleType,
            final BasePK deletedBy) {
        inventoryTransactionRoleControl.deleteInventoryTransactionRoleType(inventoryTransactionRoleType, deletedBy);
    }

}
