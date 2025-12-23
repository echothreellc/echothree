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

package com.echothree.model.control.uom.server.transfer;

import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeVolumeTransfer;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureTypeVolume;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class UnitOfMeasureTypeVolumeTransferCache
        extends BaseUomTransferCache<UnitOfMeasureTypeVolume, UnitOfMeasureTypeVolumeTransfer> {

    UomControl uomControl = Session.getModelController(UomControl.class);

    /** Creates a new instance of UnitOfMeasureTypeVolumeTransferCache */
    protected UnitOfMeasureTypeVolumeTransferCache() {
        super();
    }
    
    public UnitOfMeasureTypeVolumeTransfer getUnitOfMeasureTypeVolumeTransfer(UserVisit userVisit, UnitOfMeasureTypeVolume unitOfMeasureTypeVolume) {
        var unitOfMeasureTypeVolumeTransfer = get(unitOfMeasureTypeVolume);
        
        if(unitOfMeasureTypeVolumeTransfer == null) {
            var unitOfMeasureTypeTransfer = uomControl.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureTypeVolume.getUnitOfMeasureType());
            var volumeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_VOLUME);
            var height = formatUnitOfMeasure(userVisit, volumeUnitOfMeasureKind, unitOfMeasureTypeVolume.getHeight());
            var width = formatUnitOfMeasure(userVisit, volumeUnitOfMeasureKind, unitOfMeasureTypeVolume.getWidth());
            var depth = formatUnitOfMeasure(userVisit, volumeUnitOfMeasureKind, unitOfMeasureTypeVolume.getDepth());
            
            unitOfMeasureTypeVolumeTransfer = new UnitOfMeasureTypeVolumeTransfer(unitOfMeasureTypeTransfer, height, width, depth);
            put(userVisit, unitOfMeasureTypeVolume, unitOfMeasureTypeVolumeTransfer);
        }
        
        return unitOfMeasureTypeVolumeTransfer;
    }
    
}
