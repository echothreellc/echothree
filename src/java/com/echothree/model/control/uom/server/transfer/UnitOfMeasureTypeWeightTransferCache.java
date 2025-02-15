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

import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeWeightTransfer;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeWeight;
import com.echothree.model.data.user.server.entity.UserVisit;

public class UnitOfMeasureTypeWeightTransferCache
        extends BaseUomTransferCache<UnitOfMeasureTypeWeight, UnitOfMeasureTypeWeightTransfer> {
    
    /** Creates a new instance of UnitOfMeasureTypeWeightTransferCache */
    public UnitOfMeasureTypeWeightTransferCache(UserVisit userVisit, UomControl uomControl) {
        super(userVisit, uomControl);
    }
    
    public UnitOfMeasureTypeWeightTransfer getUnitOfMeasureTypeWeightTransfer(UnitOfMeasureTypeWeight unitOfMeasureTypeWeight) {
        var unitOfMeasureTypeWeightTransfer = get(unitOfMeasureTypeWeight);
        
        if(unitOfMeasureTypeWeightTransfer == null) {
            var unitOfMeasureTypeTransfer = uomControl.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureTypeWeight.getUnitOfMeasureType());
            var volumeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_WEIGHT);
            var weight = formatUnitOfMeasure(volumeUnitOfMeasureKind, unitOfMeasureTypeWeight.getWeight());
            
            unitOfMeasureTypeWeightTransfer = new UnitOfMeasureTypeWeightTransfer(unitOfMeasureTypeTransfer, weight);
            put(unitOfMeasureTypeWeight, unitOfMeasureTypeWeightTransfer);
        }
        
        return unitOfMeasureTypeWeightTransfer;
    }
    
}
