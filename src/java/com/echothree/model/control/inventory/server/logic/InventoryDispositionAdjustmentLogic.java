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

import com.echothree.control.user.inventory.common.spec.InventoryDispositionAdjustmentUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.common.exception.DuplicateInventoryDispositionAdjustmentNameException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryDispositionAdjustmentException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryDispositionException;
import com.echothree.model.control.inventory.common.exception.UnknownDefaultInventoryTransactionTypeException;
import com.echothree.model.control.inventory.common.exception.UnknownInventoryDispositionAdjustmentNameException;
import com.echothree.model.control.inventory.server.control.InventoryDispositionAdjustmentControl;
import com.echothree.model.control.inventory.server.control.InventoryDispositionControl;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTypeControl;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionAdjustment;
import com.echothree.model.data.inventory.server.entity.InventoryDisposition;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentType;
import com.echothree.model.data.inventory.server.entity.InventoryBucketType;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionType;
import com.echothree.model.data.inventory.server.value.InventoryDispositionAdjustmentDetailValue;
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
public class InventoryDispositionAdjustmentLogic
        extends BaseLogic {

    @Inject
    InventoryDispositionAdjustmentControl inventoryDispositionAdjustmentControl;

    @Inject
    InventoryDispositionControl inventoryDispositionControl;

    @Inject
    InventoryTransactionTypeControl inventoryTransactionTypeControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    InventoryTransactionTypeLogic inventoryTransactionTypeLogic;

    @Inject
    InventoryDispositionLogic inventoryDispositionLogic;

    @Inject
    InventoryAdjustmentTypeLogic inventoryAdjustmentTypeLogic;

    @Inject
    InventoryBucketTypeLogic inventoryBucketTypeLogic;

    protected InventoryDispositionAdjustmentLogic() {
        super();
    }

    public InventoryDispositionAdjustment createInventoryDispositionAdjustment(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryDispositionAdjustmentName, final String inventoryDispositionName,
            final String inventoryAdjustmentTypeName, final String inventoryBucketTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryDispositionAdjustment inventoryDispositionAdjustment = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var inventoryDisposition = inventoryDispositionLogic.getInventoryDispositionByName(eea, inventoryTransactionType,
                    inventoryDispositionName);

            if(eea == null || !eea.hasExecutionErrors()) {
                var inventoryAdjustmentType = inventoryAdjustmentTypeLogic.getInventoryAdjustmentTypeByName(eea, inventoryAdjustmentTypeName);
                var inventoryBucketType = inventoryBucketTypeLogic.getInventoryBucketTypeByName(eea, inventoryBucketTypeName);

                if(eea == null || !eea.hasExecutionErrors()) {
                    inventoryDispositionAdjustment = createInventoryDispositionAdjustment(eea, inventoryDisposition,
                            inventoryDispositionAdjustmentName, inventoryAdjustmentType, inventoryBucketType, isDefault, sortOrder,
                            language, description, createdBy);
                }
            }
        }

        return inventoryDispositionAdjustment;
    }

    public InventoryDispositionAdjustment createInventoryDispositionAdjustment(final ExecutionErrorAccumulator eea,
            final InventoryDisposition inventoryDisposition,
            final String inventoryDispositionAdjustmentName, final InventoryAdjustmentType inventoryAdjustmentType,
            final InventoryBucketType inventoryBucketType,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var inventoryDispositionAdjustment = inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustmentByName(inventoryDisposition,
                inventoryDispositionAdjustmentName);

        if(inventoryDispositionAdjustment == null) {
            inventoryDispositionAdjustment = inventoryDispositionAdjustmentControl.createInventoryDispositionAdjustment(inventoryDisposition,
                    inventoryDispositionAdjustmentName, inventoryAdjustmentType, inventoryBucketType, isDefault, sortOrder, createdBy);

            if(description != null) {
                inventoryDispositionAdjustmentControl.createInventoryDispositionAdjustmentDescription(inventoryDispositionAdjustment,
                        language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateInventoryDispositionAdjustmentNameException.class, eea,
                    ExecutionErrors.DuplicateInventoryDispositionAdjustmentName.name(),
                    inventoryDisposition.getLastDetail().getInventoryDispositionName(), inventoryDispositionAdjustmentName);
        }

        return inventoryDispositionAdjustment;
    }

    public InventoryDispositionAdjustment getInventoryDispositionAdjustmentByName(final ExecutionErrorAccumulator eea,
            final InventoryDisposition inventoryDisposition, final String inventoryDispositionAdjustmentName,
            final EntityPermission entityPermission) {
        var inventoryDispositionAdjustment = inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustmentByName(inventoryDisposition,
                inventoryDispositionAdjustmentName, entityPermission);

        if(inventoryDispositionAdjustment == null) {
            handleExecutionError(UnknownInventoryDispositionAdjustmentNameException.class, eea,
                    ExecutionErrors.UnknownInventoryDispositionAdjustmentName.name(),
                    inventoryDisposition.getLastDetail().getInventoryDispositionName(), inventoryDispositionAdjustmentName);
        }

        return inventoryDispositionAdjustment;
    }

    public InventoryDispositionAdjustment getInventoryDispositionAdjustmentByName(final ExecutionErrorAccumulator eea,
            final InventoryDisposition inventoryDisposition, final String inventoryDispositionAdjustmentName) {
        return getInventoryDispositionAdjustmentByName(eea, inventoryDisposition, inventoryDispositionAdjustmentName, EntityPermission.READ_ONLY);
    }

    public InventoryDispositionAdjustment getInventoryDispositionAdjustmentByNameForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryDisposition inventoryDisposition, final String inventoryDispositionAdjustmentName) {
        return getInventoryDispositionAdjustmentByName(eea, inventoryDisposition, inventoryDispositionAdjustmentName, EntityPermission.READ_WRITE);
    }

    public InventoryDispositionAdjustment getInventoryDispositionAdjustmentByName(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryDispositionName,
            final String inventoryDispositionAdjustmentName,
            final EntityPermission entityPermission) {
        var inventoryTransactionType = inventoryTransactionTypeLogic.getInventoryTransactionTypeByName(eea, inventoryTransactionTypeName);
        InventoryDispositionAdjustment inventoryDispositionAdjustment = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            var inventoryDisposition = inventoryDispositionLogic.getInventoryDispositionByName(eea, inventoryTransactionType,
                    inventoryDispositionName);
            if(eea == null || !eea.hasExecutionErrors()) {
                inventoryDispositionAdjustment = getInventoryDispositionAdjustmentByName(eea, inventoryDisposition,
                        inventoryDispositionAdjustmentName, entityPermission);
            }
        }

        return inventoryDispositionAdjustment;
    }

    public InventoryDispositionAdjustment getInventoryDispositionAdjustmentByName(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryDispositionName,
            final String inventoryDispositionAdjustmentName) {
        return getInventoryDispositionAdjustmentByName(eea, inventoryTransactionTypeName, inventoryDispositionName,
                inventoryDispositionAdjustmentName, EntityPermission.READ_ONLY);
    }

    public InventoryDispositionAdjustment getInventoryDispositionAdjustmentByNameForUpdate(final ExecutionErrorAccumulator eea,
            final String inventoryTransactionTypeName, final String inventoryDispositionName,
            final String inventoryDispositionAdjustmentName) {
        return getInventoryDispositionAdjustmentByName(eea, inventoryTransactionTypeName, inventoryDispositionName,
                inventoryDispositionAdjustmentName,
                EntityPermission.READ_WRITE);
    }

    public InventoryDispositionAdjustment getInventoryDispositionAdjustmentByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryDispositionAdjustmentUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        var inventoryTransactionTypeName = universalSpec.getInventoryTransactionTypeName();
        var inventoryDispositionName = universalSpec.getInventoryDispositionName();
        var inventoryDispositionAdjustmentName = universalSpec.getInventoryDispositionAdjustmentName();
        var nameParameterCount = ParameterUtils.getInstance().countNonNullParameters(inventoryTransactionTypeName,
                inventoryDispositionName, inventoryDispositionAdjustmentName);
        var possibleEntitySpecs = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        InventoryDispositionAdjustment inventoryDispositionAdjustment = null;

        if(nameParameterCount < 4 && possibleEntitySpecs == 0) {
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
                InventoryDisposition inventoryDisposition;
                if(inventoryDispositionName == null && allowDefault) {
                    inventoryDisposition = inventoryDispositionControl.getDefaultInventoryDisposition(inventoryTransactionType);
                    if(inventoryDisposition == null) {
                        handleExecutionError(UnknownDefaultInventoryDispositionException.class, eea,
                                ExecutionErrors.UnknownDefaultInventoryDisposition.name());
                    }
                } else {
                    inventoryDisposition = inventoryDispositionLogic.getInventoryDispositionByName(eea, inventoryTransactionType,
                            inventoryDispositionName);
                }

                if(eea != null && eea.hasExecutionErrors()) {
                    return null;
                }

                if(inventoryDispositionAdjustmentName == null) {
                    if(allowDefault) {
                        inventoryDispositionAdjustment = 
                                inventoryDispositionAdjustmentControl.getDefaultInventoryDispositionAdjustment(inventoryDisposition, entityPermission);

                        if(inventoryDispositionAdjustment == null) {
                            handleExecutionError(UnknownDefaultInventoryDispositionAdjustmentException.class, eea,
                                    ExecutionErrors.UnknownDefaultInventoryDispositionAdjustment.name());
                        }
                    } else {
                        handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                    }
                } else {
                    inventoryDispositionAdjustment = getInventoryDispositionAdjustmentByName(eea, inventoryDisposition,
                            inventoryDispositionAdjustmentName, entityPermission);
                }
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.InventoryDispositionAdjustment.name());

            if(eea == null || !eea.hasExecutionErrors()) {
                inventoryDispositionAdjustment = inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustmentByEntityInstance(entityInstance,
                        entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return inventoryDispositionAdjustment;
    }

    public InventoryDispositionAdjustment getInventoryDispositionAdjustmentByUniversalSpec(final ExecutionErrorAccumulator eea,
            final InventoryDispositionAdjustmentUniversalSpec universalSpec, boolean allowDefault) {
        return getInventoryDispositionAdjustmentByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public InventoryDispositionAdjustment getInventoryDispositionAdjustmentByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final InventoryDispositionAdjustmentUniversalSpec universalSpec, boolean allowDefault) {
        return getInventoryDispositionAdjustmentByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateInventoryDispositionAdjustmentFromValue(final ExecutionErrorAccumulator eea,
            InventoryDispositionAdjustmentDetailValue inventoryDispositionAdjustmentDetailValue,
            BasePK updatedBy) {
        inventoryDispositionAdjustmentControl.updateInventoryDispositionAdjustmentFromValue(inventoryDispositionAdjustmentDetailValue, updatedBy);
    }

    public void deleteInventoryDispositionAdjustment(final ExecutionErrorAccumulator eea,
            final InventoryDispositionAdjustment inventoryDispositionAdjustment, final BasePK deletedBy) {
        inventoryDispositionAdjustmentControl.deleteInventoryDispositionAdjustment(inventoryDispositionAdjustment, deletedBy);
    }

}
