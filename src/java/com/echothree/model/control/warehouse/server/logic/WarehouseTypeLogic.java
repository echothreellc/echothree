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

package com.echothree.model.control.warehouse.server.logic;

import com.echothree.control.user.warehouse.common.spec.WarehouseTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.warehouse.common.exception.DuplicateWarehouseTypeNameException;
import com.echothree.model.control.warehouse.common.exception.UnknownDefaultWarehouseTypeException;
import com.echothree.model.control.warehouse.common.exception.UnknownWarehouseTypeNameException;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.warehouse.server.entity.WarehouseType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class WarehouseTypeLogic
        extends BaseLogic {

    protected WarehouseTypeLogic() {
        super();
    }

    public static WarehouseTypeLogic getInstance() {
        return CDI.current().select(WarehouseTypeLogic.class).get();
    }

    public WarehouseType createWarehouseType(final ExecutionErrorAccumulator eea, final String warehouseTypeName,
            final Integer priority, final Boolean isDefault, final Integer sortOrder, final Language language,
            final String description, final BasePK createdBy) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var warehouseType = warehouseControl.getWarehouseTypeByName(warehouseTypeName);

        if(warehouseType == null) {
            warehouseType = warehouseControl.createWarehouseType(warehouseTypeName, priority, isDefault, sortOrder, createdBy);

            if(description != null) {
                warehouseControl.createWarehouseTypeDescription(warehouseType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateWarehouseTypeNameException.class, eea, ExecutionErrors.DuplicateWarehouseTypeName.name(), warehouseTypeName);
        }

        return warehouseType;
    }

    public WarehouseType getWarehouseTypeByName(final ExecutionErrorAccumulator eea, final String warehouseTypeName,
            final EntityPermission entityPermission) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var warehouseType = warehouseControl.getWarehouseTypeByName(warehouseTypeName, entityPermission);

        if(warehouseType == null) {
            handleExecutionError(UnknownWarehouseTypeNameException.class, eea, ExecutionErrors.UnknownWarehouseTypeName.name(), warehouseTypeName);
        }

        return warehouseType;
    }

    public WarehouseType getWarehouseTypeByName(final ExecutionErrorAccumulator eea, final String warehouseTypeName) {
        return getWarehouseTypeByName(eea, warehouseTypeName, EntityPermission.READ_ONLY);
    }

    public WarehouseType getWarehouseTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String warehouseTypeName) {
        return getWarehouseTypeByName(eea, warehouseTypeName, EntityPermission.READ_WRITE);
    }

    public WarehouseType getWarehouseTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final WarehouseTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        WarehouseType warehouseType = null;
        var warehouseControl = Session.getModelController(WarehouseControl.class);
        var warehouseTypeName = universalSpec.getWarehouseTypeName();
        var parameterCount = (warehouseTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0:
                if(allowDefault) {
                    warehouseType = warehouseControl.getDefaultWarehouseType(entityPermission);

                    if(warehouseType == null) {
                        handleExecutionError(UnknownDefaultWarehouseTypeException.class, eea, ExecutionErrors.UnknownDefaultWarehouseType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
                break;
            case 1:
                if(warehouseTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.WarehouseType.name());

                    if(!eea.hasExecutionErrors()) {
                        warehouseType = warehouseControl.getWarehouseTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    warehouseType = getWarehouseTypeByName(eea, warehouseTypeName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return warehouseType;
    }

    public WarehouseType getWarehouseTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final WarehouseTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getWarehouseTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public WarehouseType getWarehouseTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final WarehouseTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getWarehouseTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteWarehouseType(final ExecutionErrorAccumulator eea, final WarehouseType warehouseType,
            final BasePK deletedBy) {
        var warehouseControl = Session.getModelController(WarehouseControl.class);

        warehouseControl.deleteWarehouseType(warehouseType, deletedBy);
    }

}
