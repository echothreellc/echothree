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

package com.echothree.model.control.uom.server.transfer;

import com.echothree.model.control.uom.common.transfer.UnitOfMeasureKindUseTransfer;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class UnitOfMeasureKindUseTransferCache
        extends BaseUomTransferCache<UnitOfMeasureKindUse, UnitOfMeasureKindUseTransfer> {

    UomControl uomControl = Session.getModelController(UomControl.class);

    /** Creates a new instance of UnitOfMeasureKindUseTransferCache */
    public UnitOfMeasureKindUseTransferCache() {
        super();
    }
    
    public UnitOfMeasureKindUseTransfer getUnitOfMeasureKindUseTransfer(UserVisit userVisit, UnitOfMeasureKindUse unitOfMeasureKindUse) {
        var unitOfMeasureKindUseTransfer = get(unitOfMeasureKindUse);
        
        if(unitOfMeasureKindUseTransfer == null) {
            var unitOfMeasureKindUseType = uomControl.getUnitOfMeasureKindUseTypeTransfer(userVisit, unitOfMeasureKindUse.getUnitOfMeasureKindUseType());
            var unitOfMeasureKind = uomControl.getUnitOfMeasureKindTransfer(userVisit, unitOfMeasureKindUse.getUnitOfMeasureKind());
            var isDefault = unitOfMeasureKindUse.getIsDefault();
            var sortOrder = unitOfMeasureKindUse.getSortOrder();
            
            unitOfMeasureKindUseTransfer = new UnitOfMeasureKindUseTransfer(unitOfMeasureKindUseType, unitOfMeasureKind, isDefault,
                    sortOrder);
            put(userVisit, unitOfMeasureKindUse, unitOfMeasureKindUseTransfer);
        }
        
        return unitOfMeasureKindUseTransfer;
    }
    
}
