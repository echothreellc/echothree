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

import com.echothree.model.control.item.common.transfer.ItemVolumeTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemVolumeType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ItemVolumeTypeTransferCache
        extends BaseItemTransferCache<ItemVolumeType, ItemVolumeTypeTransfer> {
    
    /** Creates a new instance of ItemVolumeTypeTransferCache */
    public ItemVolumeTypeTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public ItemVolumeTypeTransfer getTransfer(ItemVolumeType itemVolumeType) {
        var itemVolumeTypeTransfer = get(itemVolumeType);
        
        if(itemVolumeTypeTransfer == null) {
            var itemVolumeTypeDetail = itemVolumeType.getLastDetail();
            var itemVolumeTypeName = itemVolumeTypeDetail.getItemVolumeTypeName();
            var isDefault = itemVolumeTypeDetail.getIsDefault();
            var sortOrder = itemVolumeTypeDetail.getSortOrder();
            var description = itemControl.getBestItemVolumeTypeDescription(itemVolumeType, getLanguage(userVisit));
            
            itemVolumeTypeTransfer = new ItemVolumeTypeTransfer(itemVolumeTypeName, isDefault, sortOrder,
                    description);
            put(userVisit, itemVolumeType, itemVolumeTypeTransfer);
        }
        
        return itemVolumeTypeTransfer;
    }
    
}
