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

import com.echothree.model.control.item.common.transfer.ItemDescriptionTypeUseTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ItemDescriptionTypeUseTypeTransferCache
        extends BaseItemTransferCache<ItemDescriptionTypeUseType, ItemDescriptionTypeUseTypeTransfer> {

    ItemControl itemControl = Session.getModelController(ItemControl.class);

    /** Creates a new instance of ItemDescriptionTypeUseTypeTransferCache */
    protected ItemDescriptionTypeUseTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public ItemDescriptionTypeUseTypeTransfer getTransfer(UserVisit userVisit, ItemDescriptionTypeUseType itemDescriptionTypeUseType) {
        var itemDescriptionTypeUseTypeTransfer = get(itemDescriptionTypeUseType);
        
        if(itemDescriptionTypeUseTypeTransfer == null) {
            var itemDescriptionTypeUseTypeDetail = itemDescriptionTypeUseType.getLastDetail();
            var itemDescriptionTypeUseTypeName = itemDescriptionTypeUseTypeDetail.getItemDescriptionTypeUseTypeName();
            var isDefault = itemDescriptionTypeUseTypeDetail.getIsDefault();
            var sortOrder = itemDescriptionTypeUseTypeDetail.getSortOrder();
            var description = itemControl.getBestItemDescriptionTypeUseTypeDescription(itemDescriptionTypeUseType, getLanguage(userVisit));
            
            itemDescriptionTypeUseTypeTransfer = new ItemDescriptionTypeUseTypeTransfer(itemDescriptionTypeUseTypeName, isDefault, sortOrder, description);
            put(userVisit, itemDescriptionTypeUseType, itemDescriptionTypeUseTypeTransfer);
        }
        
        return itemDescriptionTypeUseTypeTransfer;
    }
    
}
