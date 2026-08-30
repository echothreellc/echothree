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

import com.echothree.control.user.inventory.common.spec.InventoryTransactionReasonUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateInventoryTransactionReasonNameException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryTransactionReasonException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryTransactionTypeException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryTransactionReasonNameException;
import com.echothree.model.control.inventory.server.control.InventoryTransactionReasonControl;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionReason;
import com.echothree.model.data.inventory.server.entity.InventoryDisposition;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.value.InventoryTransactionReasonDetailValue;
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
public class InventoryTransactionReasonLogic
        extends BaseLogic {

    @Inject
    InventoryTransactionReasonControl inventoryTransactionReasonControl;

    @Inject
    InventoryTransactionTypeControl inventoryTransactionTypeControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;

    @Inject
    InventoryDispositionLogic inventoryDispositionLogic;

    protected InventoryTransactionReasonLogic() {
        super();
    }

    public InventoryTransactionReason createInventoryTransactionReason(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionReasonName, final String inventoryDispositionName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryTransactionReason inventoryTransactionReason = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var inventoryDisposition = inventoryDispositionLogic.getInventoryDispositionByName(eea, inventoryTransactionType,
                    inventoryDispositionName);

            if(eea == null || !eea.hasExecutionErrors()) {
                inventoryTransactionReason = createInventoryTransactionReason(eea, inventoryTransactionType,
                        inventoryTransactionReasonName, inventoryDisposition, isDefault, sortOrder, language, description, createdBy);
            }
        }

        return inventoryTransactionReason;
    }

    public InventoryTransactionReason createInventoryTransactionReason(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransactionReasonName,
            final InventoryDisposition inventoryDisposition,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var inventoryTransactionReason = inventoryTransactionReasonControl.getInventoryTransactionReasonByName(inventoryTransactionType,
                inventoryTransactionReasonName);

        if(inventoryTransactionReason == null) {
            inventoryTransactionReason = inventoryTransactionReasonControl.createInventoryTransactionReason(inventoryTransactionType,
                    inventoryTransactionReasonName, inventoryDisposition, isDefault, sortOrder, createdBy);

            if(description != null) {
                inventoryTransactionReasonControl.createInventoryTransactionReasonDescription(inventoryTransactionReason, language, description,
                        createdBy);
            }
        } else {
            handleExecutionError(DuplicateInventoryTransactionReasonNameException.class, eea,
                    ExecutionErrors.DuplicateInventoryTransactionReasonName.name(),
                    inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName(), inventoryTransactionReasonName);
        }

        return inventoryTransactionReason;
    }

    public InventoryTransactionReason getInventoryTransactionReasonByName(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransactionReasonName,
            final EntityPermission entityPermission) {
        var inventoryTransactionReason = inventoryTransactionReasonControl.getInventoryTransactionReasonByName(inventoryTransactionType,
                inventoryTransactionReasonName, entityPermission);

        if(inventoryTransactionReason == null) {
            handleExecutionError(UnknownInventoryTransactionReasonNameException.class, eea,
                    ExecutionErrors.UnknownInventoryTransactionReasonName.name(),
                    inventoryTransactionType.getLastDetail().getInventoryTransactionTypeName(), inventoryTransactionReasonName);
        }

        return inventoryTransactionReason;
    }

    public InventoryTransactionReason getInventoryTransactionReasonByName(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransactionReasonName) {
        return getInventoryTransactionReasonByName(eea, inventoryTransactionType, inventoryTransactionReasonName, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionReason getInventoryTransactionReasonByNameForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryTransactionType inventoryTransactionType, final String inventoryTransactionReasonName) {
        return getInventoryTransactionReasonByName(eea, inventoryTransactionType, inventoryTransactionReasonName, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionReason getInventoryTransactionReasonByName(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionReasonName,
            final EntityPermission entityPermission) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryTransactionReason inventoryTransactionReason = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            inventoryTransactionReason = getInventoryTransactionReasonByName(eea, inventoryTransactionType, inventoryTransactionReasonName,
                    entityPermission);
        }

        return inventoryTransactionReason;
    }

    public InventoryTransactionReason getInventoryTransactionReasonByName(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionReasonName) {
        return getInventoryTransactionReasonByName(eea, inventoryTransactionTypeName, inventoryTransactionReasonName, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionReason getInventoryTransactionReasonByNameForUpdate(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryTransactionReasonName) {
        return getInventoryTransactionReasonByName(eea, inventoryTransactionTypeName, inventoryTransactionReasonName,
                EntityPermission.READ_WRITE);
    }

    public InventoryTransactionReason getInventoryTransactionReasonByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryTransactionReasonUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var inventoryTransactionTypeName = universalSpec.getInventoryTransactionTypeName();
        var inventoryTransactionReasonName = universalSpec.getInventoryTransactionReasonName();
        var nameParameterCount = ParameterUtils.getInstance().countNonNullParameters(inventoryTransactionTypeName, inventoryTransactionReasonName);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        InventoryTransactionReason inventoryTransactionReason = null;

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
                if(inventoryTransactionReasonName == null) {
                    if(allowDefault) {
                        inventoryTransactionReason = 
                                inventoryTransactionReasonControl.getDefaultInventoryTransactionReason(inventoryTransactionType, entityPermission);

                        if(inventoryTransactionReason == null) {
                            handleExecutionError(UnknownDefaultInventoryTransactionReasonException.class, eea,
                                    ExecutionErrors.UnknownDefaultInventoryTransactionReason.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    inventoryTransactionReason = getInventoryTransactionReasonByName(eea, inventoryTransactionType,
                            inventoryTransactionReasonName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.InventoryTransactionReason.name());

            if(eea == null || !eea.hasExecutionErrors()) {
                inventoryTransactionReason = inventoryTransactionReasonControl.getInventoryTransactionReasonByEntityInstance(entityInstance,
                        entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return inventoryTransactionReason;
    }

    public InventoryTransactionReason getInventoryTransactionReasonByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryTransactionReasonUniversalSpec universalSpec,
            boolean allowDefault) {
        return getInventoryTransactionReasonByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionReason getInventoryTransactionReasonByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryTransactionReasonUniversalSpec universalSpec,
            boolean allowDefault) {
        return getInventoryTransactionReasonByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateInventoryTransactionReasonFromValue(final ExecutionErrorAccumulator eea,
            InventoryTransactionReasonDetailValue inventoryTransactionReasonDetailValue,
            BasePK updatedBy) {
        inventoryTransactionReasonControl.updateInventoryTransactionReasonFromValue(inventoryTransactionReasonDetailValue, updatedBy);
    }

    public void deleteInventoryTransactionReason(final ExecutionErrorAccumulator eea,
            final InventoryTransactionReason inventoryTransactionReason,
            final BasePK deletedBy) {
        inventoryTransactionReasonControl.deleteInventoryTransactionReason(inventoryTransactionReason, deletedBy);
    }

}
