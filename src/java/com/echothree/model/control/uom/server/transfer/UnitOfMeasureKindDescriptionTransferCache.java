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

import com.echothree.model.control.uom.common.transfer.UnitOfMeasureKindDescriptionTransfer;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class UnitOfMeasureKindDescriptionTransferCache
        extends BaseUomDescriptionTransferCache<UnitOfMeasureKindDescription, UnitOfMeasureKindDescriptionTransfer> {
    
    /** Creates a new instance of UnitOfMeasureKindDescriptionTransferCache */
    public UnitOfMeasureKindDescriptionTransferCache(UserVisit userVisit, UomControl uomControl) {
        super(userVisit, uomControl);
    }
    
    public UnitOfMeasureKindDescriptionTransfer getUnitOfMeasureKindDescriptionTransfer(UnitOfMeasureKindDescription unitOfMeasureKindDescription) {
        var unitOfMeasureKindDescriptionTransfer = get(unitOfMeasureKindDescription);
        
        if(unitOfMeasureKindDescriptionTransfer == null) {
            var unitOfMeasureKindTransferCache = uomControl.getUomTransferCaches(userVisit).getUnitOfMeasureKindTransferCache();
            var unitOfMeasureKindTransfer = unitOfMeasureKindTransferCache.getUnitOfMeasureKindTransfer(unitOfMeasureKindDescription.getUnitOfMeasureKind());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, unitOfMeasureKindDescription.getLanguage());
            
            unitOfMeasureKindDescriptionTransfer = new UnitOfMeasureKindDescriptionTransfer(languageTransfer, unitOfMeasureKindTransfer, unitOfMeasureKindDescription.getDescription());
            put(userVisit, unitOfMeasureKindDescription, unitOfMeasureKindDescriptionTransfer);
        }
        
        return unitOfMeasureKindDescriptionTransfer;
    }
    
}
