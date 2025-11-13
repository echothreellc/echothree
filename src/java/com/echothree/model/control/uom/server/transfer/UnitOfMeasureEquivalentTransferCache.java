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

import com.echothree.model.control.uom.common.transfer.UnitOfMeasureEquivalentTransfer;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureEquivalent;
import com.echothree.model.data.user.server.entity.UserVisit;

public class UnitOfMeasureEquivalentTransferCache
        extends BaseUomTransferCache<UnitOfMeasureEquivalent, UnitOfMeasureEquivalentTransfer> {
    
    /** Creates a new instance of UnitOfMeasureEquivalentTransferCache */
    public UnitOfMeasureEquivalentTransferCache(UomControl uomControl) {
        super(uomControl);
    }
    
    public UnitOfMeasureEquivalentTransfer getUnitOfMeasureEquivalentTransfer(UserVisit userVisit, UnitOfMeasureEquivalent unitOfMeasureEquivalent) {
        var unitOfMeasureEquivalentTransfer = get(unitOfMeasureEquivalent);
        
        if(unitOfMeasureEquivalentTransfer == null) {
            var unitOfMeasureTypeTransferCache = uomControl.getUomTransferCaches().getUnitOfMeasureTypeTransferCache();
            var fromUnitOfMeasureType = unitOfMeasureTypeTransferCache.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureEquivalent.getFromUnitOfMeasureType());
            var toUnitOfMeasureType = unitOfMeasureTypeTransferCache.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureEquivalent.getToUnitOfMeasureType());
            var toQuantity = unitOfMeasureEquivalent.getToQuantity();
            
            unitOfMeasureEquivalentTransfer = new UnitOfMeasureEquivalentTransfer(fromUnitOfMeasureType, toUnitOfMeasureType, toQuantity);
            put(userVisit, unitOfMeasureEquivalent, unitOfMeasureEquivalentTransfer);
        }
        
        return unitOfMeasureEquivalentTransfer;
    }
    
}
