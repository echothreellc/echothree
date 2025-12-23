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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.item.common.transfer.ItemWeightTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemWeightType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ItemWeightTypeTransferCache
        extends BaseItemTransferCache<ItemWeightType, ItemWeightTypeTransfer> {

    ItemControl itemControl = Session.getModelController(ItemControl.class);

    /** Creates a new instance of ItemWeightTypeTransferCache */
    protected ItemWeightTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public ItemWeightTypeTransfer getTransfer(UserVisit userVisit, ItemWeightType itemWeightType) {
        var itemWeightTypeTransfer = get(itemWeightType);
        
        if(itemWeightTypeTransfer == null) {
            var itemWeightTypeDetail = itemWeightType.getLastDetail();
            var itemWeightTypeName = itemWeightTypeDetail.getItemWeightTypeName();
            var isDefault = itemWeightTypeDetail.getIsDefault();
            var sortOrder = itemWeightTypeDetail.getSortOrder();
            var description = itemControl.getBestItemWeightTypeDescription(itemWeightType, getLanguage(userVisit));
            
            itemWeightTypeTransfer = new ItemWeightTypeTransfer(itemWeightTypeName, isDefault, sortOrder,
                    description);
            put(userVisit, itemWeightType, itemWeightTypeTransfer);
        }
        
        return itemWeightTypeTransfer;
    }
    
}
