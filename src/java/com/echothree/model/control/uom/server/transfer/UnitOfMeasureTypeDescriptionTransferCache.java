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

import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeDescriptionTransfer;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class UnitOfMeasureTypeDescriptionTransferCache
        extends BaseUomDescriptionTransferCache<UnitOfMeasureTypeDescription, UnitOfMeasureTypeDescriptionTransfer> {
    
    /** Creates a new instance of UnitOfMeasureTypeDescriptionTransferCache */
    public UnitOfMeasureTypeDescriptionTransferCache(UomControl uomControl) {
        super(uomControl);
    }
    
    public UnitOfMeasureTypeDescriptionTransfer getUnitOfMeasureTypeDescriptionTransfer(UnitOfMeasureTypeDescription unitOfMeasureTypeDescription) {
        var unitOfMeasureTypeDescriptionTransfer = get(unitOfMeasureTypeDescription);
        
        if(unitOfMeasureTypeDescriptionTransfer == null) {
            var unitOfMeasureTypeTransferCache = uomControl.getUomTransferCaches(userVisit).getUnitOfMeasureTypeTransferCache();
            var unitOfMeasureTypeTransfer = unitOfMeasureTypeTransferCache.getUnitOfMeasureTypeTransfer(unitOfMeasureTypeDescription.getUnitOfMeasureType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, unitOfMeasureTypeDescription.getLanguage());
            
            unitOfMeasureTypeDescriptionTransfer = new UnitOfMeasureTypeDescriptionTransfer(languageTransfer,
                    unitOfMeasureTypeTransfer, unitOfMeasureTypeDescription.getSingularDescription(),
                    unitOfMeasureTypeDescription.getPluralDescription(), unitOfMeasureTypeDescription.getSymbol());
            put(userVisit, unitOfMeasureTypeDescription, unitOfMeasureTypeDescriptionTransfer);
        }
        
        return unitOfMeasureTypeDescriptionTransfer;
    }
    
}
