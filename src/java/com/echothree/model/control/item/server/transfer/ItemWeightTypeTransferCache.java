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

import com.echothree.model.control.item.common.transfer.ItemWeightTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemWeightType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ItemWeightTypeTransferCache
        extends BaseItemTransferCache<ItemWeightType, ItemWeightTypeTransfer> {
    
    /** Creates a new instance of ItemWeightTypeTransferCache */
    public ItemWeightTypeTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public ItemWeightTypeTransfer getTransfer(ItemWeightType itemWeightType) {
        var itemWeightTypeTransfer = get(itemWeightType);
        
        if(itemWeightTypeTransfer == null) {
            var itemWeightTypeDetail = itemWeightType.getLastDetail();
            var itemWeightTypeName = itemWeightTypeDetail.getItemWeightTypeName();
            var isDefault = itemWeightTypeDetail.getIsDefault();
            var sortOrder = itemWeightTypeDetail.getSortOrder();
            var description = itemControl.getBestItemWeightTypeDescription(itemWeightType, getLanguage());
            
            itemWeightTypeTransfer = new ItemWeightTypeTransfer(itemWeightTypeName, isDefault, sortOrder,
                    description);
            put(itemWeightType, itemWeightTypeTransfer);
        }
        
        return itemWeightTypeTransfer;
    }
    
}
