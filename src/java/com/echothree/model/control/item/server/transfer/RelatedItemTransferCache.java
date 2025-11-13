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

import com.echothree.model.control.item.common.transfer.RelatedItemTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.RelatedItem;
import com.echothree.model.data.user.server.entity.UserVisit;

public class RelatedItemTransferCache
        extends BaseItemTransferCache<RelatedItem, RelatedItemTransfer> {
    
    /** Creates a new instance of RelatedItemTransferCache */
    public RelatedItemTransferCache(ItemControl itemControl) {
        super(itemControl);

        setIncludeEntityInstance(true);
    }
    
    @Override
    public RelatedItemTransfer getTransfer(UserVisit userVisit, RelatedItem relatedItem) {
        var relatedItemTransfer = get(relatedItem);
        
        if(relatedItemTransfer == null) {
            var relatedItemDetail = relatedItem.getLastDetail();
            var relatedItemType = itemControl.getRelatedItemTypeTransfer(userVisit, relatedItemDetail.getRelatedItemType());
            var fromItem = itemControl.getItemTransfer(userVisit, relatedItemDetail.getFromItem());
            var toItem = itemControl.getItemTransfer(userVisit, relatedItemDetail.getToItem());
            var sortOrder = relatedItemDetail.getSortOrder();
            
            relatedItemTransfer = new RelatedItemTransfer(relatedItemType, fromItem, toItem, sortOrder);
            put(userVisit, relatedItem, relatedItemTransfer);
        }
        
        return relatedItemTransfer;
    }
    
}
