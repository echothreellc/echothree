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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.item.common.transfer.ItemVolumeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.item.server.entity.ItemVolume;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ItemVolumeTransferCache
        extends BaseItemTransferCache<ItemVolume, ItemVolumeTransfer> {
    
    UomControl uomControl = Session.getModelController(UomControl.class);
    
    /** Creates a new instance of ItemVolumeTransferCache */
    public ItemVolumeTransferCache(ItemControl itemControl) {
        super(itemControl);
    }
    
    @Override
    public ItemVolumeTransfer getTransfer(UserVisit userVisit, ItemVolume itemVolume) {
        var itemVolumeTransfer = get(itemVolume);
        
        if(itemVolumeTransfer == null) {
            var itemTransfer = itemControl.getItemTransfer(userVisit, itemVolume.getItem());
            var unitOfMeasureTypeTransfer = uomControl.getUnitOfMeasureTypeTransfer(userVisit,
                    itemVolume.getUnitOfMeasureType());
            var itemVolumeType = itemControl.getItemVolumeTypeTransfer(userVisit, itemVolume.getItemVolumeType());
            var volumeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_VOLUME);
            var height = formatUnitOfMeasure(userVisit, volumeUnitOfMeasureKind, itemVolume.getHeight());
            var width = formatUnitOfMeasure(userVisit, volumeUnitOfMeasureKind, itemVolume.getWidth());
            var depth = formatUnitOfMeasure(userVisit, volumeUnitOfMeasureKind, itemVolume.getDepth());

            itemVolumeTransfer = new ItemVolumeTransfer(itemTransfer, unitOfMeasureTypeTransfer, itemVolumeType, height, width, depth);
            put(userVisit, itemVolume, itemVolumeTransfer);
        }
        
        return itemVolumeTransfer;
    }
    
}
