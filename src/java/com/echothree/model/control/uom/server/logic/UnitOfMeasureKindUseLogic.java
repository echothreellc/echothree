// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.model.control.uom.server.logic;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.common.exception.UnknownUnitOfMeasureKindUseException;
import com.echothree.model.control.uom.common.exception.UnknownUnitOfMeasureKindUseTypeNameException;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class UnitOfMeasureKindUseLogic
    extends BaseLogic {
    
    private UnitOfMeasureKindUseLogic() {
        super();
    }
    
    private static class UnitOfMeasureKindUseLogicHolder {
        static UnitOfMeasureKindUseLogic instance = new UnitOfMeasureKindUseLogic();
    }
    
    public static UnitOfMeasureKindUseLogic getInstance() {
        return UnitOfMeasureKindUseLogicHolder.instance;
    }

    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByName(final ExecutionErrorAccumulator eea, final String unitOfMeasureKindUseTypeName) {
        var uomControl = Session.getModelController(UomControl.class);
        var unitOfMeasureKindUseType = uomControl.getUnitOfMeasureKindUseTypeByName(unitOfMeasureKindUseTypeName);

        if(unitOfMeasureKindUseType == null) {
            handleExecutionError(UnknownUnitOfMeasureKindUseTypeNameException.class, eea, ExecutionErrors.UnknownUnitOfMeasureKindUseTypeName.name(), unitOfMeasureKindUseTypeName);
        }

        return unitOfMeasureKindUseType;
    }

    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByUlid(final ExecutionErrorAccumulator eea, final String ulid, final EntityPermission entityPermission) {
        UnitOfMeasureKindUseType unitOfMeasureKindUseType = null;
        
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, null, null, ulid,
                ComponentVendors.ECHO_THREE.name(), EntityTypes.UnitOfMeasureKindUseType.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            var uomControl = Session.getModelController(UomControl.class);
            
            unitOfMeasureKindUseType = uomControl.getUnitOfMeasureKindUseTypeByEntityInstance(entityInstance, entityPermission);
        }

        return unitOfMeasureKindUseType;
    }
    
    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByUlid(final ExecutionErrorAccumulator eea, final String ulid) {
        return getUnitOfMeasureKindUseTypeByUlid(eea, ulid, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByUlidForUpdate(final ExecutionErrorAccumulator eea, final String ulid) {
        return getUnitOfMeasureKindUseTypeByUlid(eea, ulid, EntityPermission.READ_WRITE);
    }
    
    public UnitOfMeasureKindUse getUnitOfMeasureKindUse(final ExecutionErrorAccumulator eea, final UnitOfMeasureKindUseType unitOfMeasureKindUseType,
            final UnitOfMeasureKind unitOfMeasureKind) {
        var uomControl = Session.getModelController(UomControl.class);
        var unitOfMeasureKindUse = uomControl.getUnitOfMeasureKindUse(unitOfMeasureKindUseType, unitOfMeasureKind);

        if(unitOfMeasureKindUse == null) {
            handleExecutionError(UnknownUnitOfMeasureKindUseException.class, eea, ExecutionErrors.UnknownUnitOfMeasureKindUse.name(),
                    unitOfMeasureKindUseType.getUnitOfMeasureKindUseTypeName(), unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName());
        }

        return unitOfMeasureKindUse;
    }

    public UnitOfMeasureKindUse getUnitOfMeasureKindUse(final ExecutionErrorAccumulator eea, final String unitOfMeasureKindUseTypeName,
            final String unitOfMeasureKindName) {
        var unitOfMeasureKindUseType = getUnitOfMeasureKindUseTypeByName(eea, unitOfMeasureKindUseTypeName);
        var unitOfMeasureKind = UnitOfMeasureKindLogic.getInstance().getUnitOfMeasureKindByName(eea, unitOfMeasureKindName);
        UnitOfMeasureKindUse unitOfMeasureKindUse = null;
        
        if(!hasExecutionErrors(eea)) {
            unitOfMeasureKindUse = getUnitOfMeasureKindUse(eea, unitOfMeasureKindUseType, unitOfMeasureKind);
        }
        
        return unitOfMeasureKindUse;
    }

    public UnitOfMeasureKindUse getUnitOfMeasureKindUseByUlid(final ExecutionErrorAccumulator eea, final String ulid, final EntityPermission entityPermission) {
        UnitOfMeasureKindUse unitOfMeasureKindUse = null;
        
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, null, null, ulid,
                ComponentVendors.ECHO_THREE.name(), EntityTypes.UnitOfMeasureKindUse.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            var uomControl = Session.getModelController(UomControl.class);
            
            unitOfMeasureKindUse = uomControl.getUnitOfMeasureKindUseByEntityInstance(entityInstance, entityPermission);
        }

        return unitOfMeasureKindUse;
    }
    
    public UnitOfMeasureKindUse getUnitOfMeasureKindUseByUlid(final ExecutionErrorAccumulator eea, final String ulid) {
        return getUnitOfMeasureKindUseByUlid(eea, ulid, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureKindUse getUnitOfMeasureKindUseByUlidForUpdate(final ExecutionErrorAccumulator eea, final String ulid) {
        return getUnitOfMeasureKindUseByUlid(eea, ulid, EntityPermission.READ_WRITE);
    }
    
    private String getUnitOfMeasureKindName(final UnitOfMeasureKindUse unitOfMeasureKindUse) {
        return unitOfMeasureKindUse.getUnitOfMeasureKind().getLastDetail().getUnitOfMeasureKindName();
    }

    public void checkDeleteUnitOfMeasureKindUse(final ExecutionErrorAccumulator ema, final UnitOfMeasureKindUse unitOfMeasureKindUse) {
        var unitOfMeasureKindUseType = unitOfMeasureKindUse.getUnitOfMeasureKindUseType();

        if(unitOfMeasureKindUseType.getAllowMultiple()) {
            var itemControl = Session.getModelController(ItemControl.class);

            if(itemControl.countItemsByUnitOfMeasureKind(unitOfMeasureKindUse.getUnitOfMeasureKind()) != 0) {
                ema.addExecutionError(ExecutionErrors.CannotDeleteUnitOfMeasureKindUseInUse.name(), unitOfMeasureKindUseType.getUnitOfMeasureKindUseTypeName(),
                        getUnitOfMeasureKindName(unitOfMeasureKindUse));
            }
        } else {
            ema.addExecutionError(ExecutionErrors.CannotDeleteUnitOfMeasureKindUse.name(), unitOfMeasureKindUseType.getUnitOfMeasureKindUseTypeName(),
                    getUnitOfMeasureKindName(unitOfMeasureKindUse));
        }
    }

    public void deleteUnitOfMeasureKindUse(final UnitOfMeasureKindUse unitOfMeasureKindUse, final BasePK deletedBy) {
        var uomControl = Session.getModelController(UomControl.class);

        uomControl.deleteUnitOfMeasureKindUse(unitOfMeasureKindUse, deletedBy);
    }

}
