// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.uom.common.transfer.UnitOfMeasureKindUseTypeTransfer;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class UnitOfMeasureKindUseTypeTransferCache
        extends BaseUomTransferCache<UnitOfMeasureKindUseType, UnitOfMeasureKindUseTypeTransfer> {
    
    /** Creates a new instance of UnitOfMeasureKindUseTypeTransferCache */
    public UnitOfMeasureKindUseTypeTransferCache(UserVisit userVisit, UomControl uomControl) {
        super(userVisit, uomControl);
    }
    
    public UnitOfMeasureKindUseTypeTransfer getUnitOfMeasureKindUseTypeTransfer(UnitOfMeasureKindUseType unitOfMeasureKindUseType) {
        UnitOfMeasureKindUseTypeTransfer unitOfMeasureKindUseTypeTransfer = get(unitOfMeasureKindUseType);
        
        if(unitOfMeasureKindUseTypeTransfer == null) {
            String unitOfMeasureKindUseTypeName = unitOfMeasureKindUseType.getUnitOfMeasureKindUseTypeName();
            Boolean allowMultiple = unitOfMeasureKindUseType.getAllowMultiple();
            Boolean allowFractionDigits = unitOfMeasureKindUseType.getAllowFractionDigits();
            Boolean isDefault = unitOfMeasureKindUseType.getIsDefault();
            Integer sortOrder = unitOfMeasureKindUseType.getSortOrder();
            String description = uomControl.getBestUnitOfMeasureKindUseTypeDescription(unitOfMeasureKindUseType, getLanguage());
            
            unitOfMeasureKindUseTypeTransfer = new UnitOfMeasureKindUseTypeTransfer(unitOfMeasureKindUseTypeName, allowMultiple, allowFractionDigits, isDefault,
                    sortOrder, description);
            put(unitOfMeasureKindUseType, unitOfMeasureKindUseTypeTransfer);
        }
        
        return unitOfMeasureKindUseTypeTransfer;
    }
    
}
