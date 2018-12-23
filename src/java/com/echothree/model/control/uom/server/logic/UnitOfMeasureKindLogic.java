// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.uom.common.exception.UnknownUnitOfMeasureKindNameException;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;

public class UnitOfMeasureKindLogic
    extends BaseLogic {
    
    private UnitOfMeasureKindLogic() {
        super();
    }
    
    private static class UnitOfMeasureKindLogicHolder {
        static UnitOfMeasureKindLogic instance = new UnitOfMeasureKindLogic();
    }
    
    public static UnitOfMeasureKindLogic getInstance() {
        return UnitOfMeasureKindLogicHolder.instance;
    }

    public UnitOfMeasureKind getUnitOfMeasureKindByName(final ExecutionErrorAccumulator eea, final String unitOfMeasureKindName) {
        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
        UnitOfMeasureKind unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);

        if(unitOfMeasureKind == null) {
            handleExecutionError(UnknownUnitOfMeasureKindNameException.class, eea, ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }

        return unitOfMeasureKind;
    }

    public UnitOfMeasureKind getUnitOfMeasureKindByUlid(final ExecutionErrorAccumulator eea, final String ulid, final EntityPermission entityPermission) {
        UnitOfMeasureKind unitOfMeasureKind = null;
        
        EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, null, null, ulid,
                ComponentVendors.ECHOTHREE.name(), EntityTypes.UnitOfMeasureKind.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
            
            unitOfMeasureKind = uomControl.getUnitOfMeasureKindByEntityInstance(entityInstance, entityPermission);
        }

        return unitOfMeasureKind;
    }
    
    public UnitOfMeasureKind getUnitOfMeasureKindByUlid(final ExecutionErrorAccumulator eea, final String ulid) {
        return getUnitOfMeasureKindByUlid(eea, ulid, EntityPermission.READ_ONLY);
    }
    
    public UnitOfMeasureKind getUnitOfMeasureKindByUlidForUpdate(final ExecutionErrorAccumulator eea, final String ulid) {
        return getUnitOfMeasureKindByUlid(eea, ulid, EntityPermission.READ_WRITE);
    }
    
    public void checkDeleteUnitOfMeasureKind(final ExecutionErrorAccumulator ema, final UnitOfMeasureKind unitOfMeasureKind) {
        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
        
        if(uomControl.countUnitOfMeasureKindUsesByUnitOfMeasureKind(unitOfMeasureKind) != 0) {
            ema.addExecutionError(ExecutionErrors.CannotDeleteUnitOfMeasureKindInUse.name(), unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName());
        }
    }

    public void deleteUnitOfMeasureKind(final UnitOfMeasureKind unitOfMeasureKind, final BasePK deletedBy) {
        UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);

        uomControl.deleteUnitOfMeasureKind(unitOfMeasureKind, deletedBy);
    }

}
