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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.model.control.item.common.transfer.ItemVolumeTransfer;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.data.item.server.entity.ItemVolume;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ItemVolumeTransferCache
        extends BaseItemTransferCache<ItemVolume, ItemVolumeTransfer> {
    
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    
    /** Creates a new instance of ItemVolumeTransferCache */
    public ItemVolumeTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
    }
    
    @Override
    public ItemVolumeTransfer getTransfer(ItemVolume itemVolume) {
        ItemVolumeTransfer itemVolumeTransfer = get(itemVolume);
        
        if(itemVolumeTransfer == null) {
            ItemTransfer itemTransfer = itemControl.getItemTransfer(userVisit, itemVolume.getItem());
            UnitOfMeasureTypeTransfer unitOfMeasureTypeTransfer = uomControl.getUnitOfMeasureTypeTransfer(userVisit,
                    itemVolume.getUnitOfMeasureType());
            UnitOfMeasureKind volumeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_VOLUME);
            String height = formatUnitOfMeasure(volumeUnitOfMeasureKind, itemVolume.getHeight());
            String width = formatUnitOfMeasure(volumeUnitOfMeasureKind, itemVolume.getWidth());
            String depth = formatUnitOfMeasure(volumeUnitOfMeasureKind, itemVolume.getDepth());
            
            itemVolumeTransfer = new ItemVolumeTransfer(itemTransfer, unitOfMeasureTypeTransfer, height, width, depth);
            put(itemVolume, itemVolumeTransfer);
        }
        
        return itemVolumeTransfer;
    }
    
}
