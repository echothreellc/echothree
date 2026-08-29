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

import com.echothree.control.user.inventory.common.spec.InventoryDispositionUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateInventoryDispositionNameException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryDispositionException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryTransactionTypeException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryDispositionNameException;
import com.echothree.model.control.inventory.server.control.InventoryDispositionControl;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.data.inventory.server.entity.InventoryDisposition;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.value.InventoryDispositionDetailValue;
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
public class InventoryDispositionLogic
        extends BaseLogic {

    @Inject
    InventoryDispositionControl inventoryDispositionControl;

    @Inject
    InventoryTransactionTypeControl inventoryTransactionTypeControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;

    protected InventoryDispositionLogic() {
        super();
    }

    public InventoryDisposition createInventoryDisposition(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryDispositionName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryDisposition inventoryDisposition = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryDisposition = createInventoryDisposition(eea, inventoryTransactionType, inventoryDispositionName,
                    isDefault, sortOrder, language, description, createdBy);
        }

        return inventoryDisposition;
    }

    public InventoryDisposition createInventoryDisposition(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryDispositionName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var inventoryDisposition = inventoryDispositionControl.getInventoryDispositionByName(inventoryTransactionType,
                inventoryDispositionName);

        if(inventoryDisposition == null) {
            inventoryDisposition = inventoryDispositionControl.createInventoryDisposition(inventoryTransactionType,
                    inventoryDispositionName, isDefault, sortOrder, createdBy);

            if(description != null) {
                inventoryDispositionControl.createInventoryDispositionDescription(inventoryDisposition, language, description,
                        createdBy);
            }
        } else {
            handleExecutionError(DuplicateInventoryDispositionNameException.class, eea,
                    ExecutionErrors.DuplicateInventoryDispositionName.name(),
                    inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName(), inventoryDispositionName);
        }

        return inventoryDisposition;
    }

    public InventoryDisposition getInventoryDispositionByName(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryDispositionName,
            final EntityPermission entityPermission) {
        var inventoryDisposition = inventoryDispositionControl.getInventoryDispositionByName(inventoryTransactionType,
                inventoryDispositionName, entityPermission);

        if(inventoryDisposition == null) {
            handleExecutionError(UnknownInventoryDispositionNameException.class, eea,
                    ExecutionErrors.UnknownInventoryDispositionName.name(),
                    inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName(), inventoryDispositionName);
        }

        return inventoryDisposition;
    }

    public InventoryDisposition getInventoryDispositionByName(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryDispositionName) {
        return getInventoryDispositionByName(eea, inventoryTransactionType, inventoryDispositionName, EntityPermission.READ_ONLY);
    }

    public InventoryDisposition getInventoryDispositionByNameForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryDispositionName) {
        return getInventoryDispositionByName(eea, inventoryTransactionType, inventoryDispositionName, EntityPermission.READ_WRITE);
    }

    public InventoryDisposition getInventoryDispositionByName(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryDispositionName,
            final EntityPermission entityPermission) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryDisposition inventoryDisposition = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryDisposition = getInventoryDispositionByName(eea, inventoryTransactionType, inventoryDispositionName,
                    entityPermission);
        }

        return inventoryDisposition;
    }

    public InventoryDisposition getInventoryDispositionByName(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryDispositionName) {
        return getInventoryDispositionByName(eea, inventoryTransactionTypeName, inventoryDispositionName, EntityPermission.READ_ONLY);
    }

    public InventoryDisposition getInventoryDispositionByNameForUpdate(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryDispositionName) {
        return getInventoryDispositionByName(eea, inventoryTransactionTypeName, inventoryDispositionName,
                EntityPermission.READ_WRITE);
    }

    public InventoryDisposition getInventoryDispositionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryDispositionUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var inventoryTransactionTypeName = universalSpec.getInventoryTransactionTypeName();
        var inventoryDispositionName = universalSpec.getInventoryDispositionName();
        var nameParameterCount = ParameterUtils.getInstance().countNonNullParameters(inventoryTransactionTypeName, inventoryDispositionName);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        InventoryDisposition inventoryDisposition = null;

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
                if(inventoryDispositionName == null) {
                    if(allowDefault) {
                        inventoryDisposition = 
                                inventoryDispositionControl.getDefaultInventoryDisposition(inventoryTransactionType, entityPermission);

                        if(inventoryDisposition == null) {
                            handleExecutionError(UnknownDefaultInventoryDispositionException.class, eea,
                                    ExecutionErrors.UnknownDefaultInventoryDisposition.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    inventoryDisposition = getInventoryDispositionByName(eea, inventoryTransactionType,
                            inventoryDispositionName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.InventoryDisposition.name());

            if(eea == null || !eea.hasExecutionErrors()) {
                inventoryDisposition = inventoryDispositionControl.getInventoryDispositionByEntityInstance(entityInstance,
                        entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return inventoryDisposition;
    }

    public InventoryDisposition getInventoryDispositionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryDispositionUniversalSpec universalSpec,
            boolean allowDefault) {
        return getInventoryDispositionByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public InventoryDisposition getInventoryDispositionByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryDispositionUniversalSpec universalSpec,
            boolean allowDefault) {
        return getInventoryDispositionByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateInventoryDispositionFromValue(final ExecutionErrorAccumulator eea,
            InventoryDispositionDetailValue inventoryDispositionDetailValue,
            BasePK updatedBy) {
        inventoryDispositionControl.updateInventoryDispositionFromValue(inventoryDispositionDetailValue, updatedBy);
    }

    public void deleteInventoryDisposition(final ExecutionErrorAccumulator eea,
            final InventoryDisposition inventoryDisposition,
            final BasePK deletedBy) {
        inventoryDispositionControl.deleteInventoryDisposition(inventoryDisposition, deletedBy);
    }

}
