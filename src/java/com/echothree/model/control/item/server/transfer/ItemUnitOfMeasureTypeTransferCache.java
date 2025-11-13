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

import com.echothree.model.control.item.common.transfer.ItemUnitOfMeasureTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.item.server.entity.ItemUnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ItemUnitOfMeasureTypeTransferCache
        extends BaseItemTransferCache<ItemUnitOfMeasureType, ItemUnitOfMeasureTypeTransfer> {
    
    UomControl uomControl;
    
    /** Creates a new instance of ItemUnitOfMeasureTypeTransferCache */
    public ItemUnitOfMeasureTypeTransferCache(ItemControl itemControl) {
        super(itemControl);
        
        uomControl = Session.getModelController(UomControl.class);
    }
    
    @Override
    public ItemUnitOfMeasureTypeTransfer getTransfer(UserVisit userVisit, ItemUnitOfMeasureType itemUnitOfMeasureType) {
        var itemUnitOfMeasureTypeTransfer = get(itemUnitOfMeasureType);
        
        if(itemUnitOfMeasureTypeTransfer == null) {
            var item = itemControl.getItemTransfer(userVisit, itemUnitOfMeasureType.getItem());
            var unitOfMeasureType = uomControl.getUnitOfMeasureTypeTransfer(userVisit, itemUnitOfMeasureType.getUnitOfMeasureType());
            var isDefault = itemUnitOfMeasureType.getIsDefault();
            var sortOrder = itemUnitOfMeasureType.getSortOrder();
            
            itemUnitOfMeasureTypeTransfer = new ItemUnitOfMeasureTypeTransfer(item, unitOfMeasureType, isDefault, sortOrder);
            put(userVisit, itemUnitOfMeasureType, itemUnitOfMeasureTypeTransfer);
        }
        
        return itemUnitOfMeasureTypeTransfer;
    }
    
}
