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

package com.echothree.model.control.uom.server.transfer;

import com.echothree.model.control.uom.common.transfer.UnitOfMeasureKindTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureKindUseTransfer;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureKindUseTypeTransfer;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.user.server.entity.UserVisit;

public class UnitOfMeasureKindUseTransferCache
        extends BaseUomTransferCache<UnitOfMeasureKindUse, UnitOfMeasureKindUseTransfer> {
    
    /** Creates a new instance of UnitOfMeasureKindUseTransferCache */
    public UnitOfMeasureKindUseTransferCache(UserVisit userVisit, UomControl uomControl) {
        super(userVisit, uomControl);
    }
    
    public UnitOfMeasureKindUseTransfer getUnitOfMeasureKindUseTransfer(UnitOfMeasureKindUse unitOfMeasureKindUse) {
        UnitOfMeasureKindUseTransfer unitOfMeasureKindUseTransfer = get(unitOfMeasureKindUse);
        
        if(unitOfMeasureKindUseTransfer == null) {
            UnitOfMeasureKindUseTypeTransfer unitOfMeasureKindUseType = uomControl.getUnitOfMeasureKindUseTypeTransfer(userVisit, unitOfMeasureKindUse.getUnitOfMeasureKindUseType());
            UnitOfMeasureKindTransfer unitOfMeasureKind = uomControl.getUnitOfMeasureKindTransfer(userVisit, unitOfMeasureKindUse.getUnitOfMeasureKind());
            Boolean isDefault = unitOfMeasureKindUse.getIsDefault();
            Integer sortOrder = unitOfMeasureKindUse.getSortOrder();
            
            unitOfMeasureKindUseTransfer = new UnitOfMeasureKindUseTransfer(unitOfMeasureKindUseType, unitOfMeasureKind, isDefault,
                    sortOrder);
            put(unitOfMeasureKindUse, unitOfMeasureKindUseTransfer);
        }
        
        return unitOfMeasureKindUseTransfer;
    }
    
}
