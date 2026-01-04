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

import com.echothree.model.control.uom.common.exception.UnknownUnitOfMeasureKindNameException;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class UnitOfMeasureKindLogic
    extends BaseLogic {

    protected UnitOfMeasureKindLogic() {
        super();
    }

    public static UnitOfMeasureKindLogic getInstance() {
        return CDI.current().select(UnitOfMeasureKindLogic.class).get();
    }

    public UnitOfMeasureKind getUnitOfMeasureKindByName(final ExecutionErrorAccumulator eea, final String unitOfMeasureKindName) {
        var uomControl = Session.getModelController(UomControl.class);
        var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByName(unitOfMeasureKindName);

        if(unitOfMeasureKind == null) {
            handleExecutionError(UnknownUnitOfMeasureKindNameException.class, eea, ExecutionErrors.UnknownUnitOfMeasureKindName.name(), unitOfMeasureKindName);
        }

        return unitOfMeasureKind;
    }

    public void checkDeleteUnitOfMeasureKind(final ExecutionErrorAccumulator ema, final UnitOfMeasureKind unitOfMeasureKind) {
        var uomControl = Session.getModelController(UomControl.class);
        
        if(uomControl.countUnitOfMeasureKindUsesByUnitOfMeasureKind(unitOfMeasureKind) != 0) {
            ema.addExecutionError(ExecutionErrors.CannotDeleteUnitOfMeasureKindInUse.name(), unitOfMeasureKind.getLastDetail().getUnitOfMeasureKindName());
        }
    }

    public void deleteUnitOfMeasureKind(final UnitOfMeasureKind unitOfMeasureKind, final BasePK deletedBy) {
        var uomControl = Session.getModelController(UomControl.class);

        uomControl.deleteUnitOfMeasureKind(unitOfMeasureKind, deletedBy);
    }

}
