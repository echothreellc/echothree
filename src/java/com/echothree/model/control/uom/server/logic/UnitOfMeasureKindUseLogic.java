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

package com.echothree.model.control.uom.server.logic;

import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.common.exception.UnknownUnitOfMeasureKindUseException;
import com.echothree.model.control.uom.common.exception.UnknownUnitOfMeasureKindUseTypeNameException;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class UnitOfMeasureKindUseLogic
    extends BaseLogic {

    protected UnitOfMeasureKindUseLogic() {
        super();
    }

    public static UnitOfMeasureKindUseLogic getInstance() {
        return CDI.current().select(UnitOfMeasureKindUseLogic.class).get();
    }

    public UnitOfMeasureKindUseType getUnitOfMeasureKindUseTypeByName(final ExecutionErrorAccumulator eea, final String unitOfMeasureKindUseTypeName) {
        var uomControl = Session.getModelController(UomControl.class);
        var unitOfMeasureKindUseType = uomControl.getUnitOfMeasureKindUseTypeByName(unitOfMeasureKindUseTypeName);

        if(unitOfMeasureKindUseType == null) {
            handleExecutionError(UnknownUnitOfMeasureKindUseTypeNameException.class, eea, ExecutionErrors.UnknownUnitOfMeasureKindUseTypeName.name(), unitOfMeasureKindUseTypeName);
        }

        return unitOfMeasureKindUseType;
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
