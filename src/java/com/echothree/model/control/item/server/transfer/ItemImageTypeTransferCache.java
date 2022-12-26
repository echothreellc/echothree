// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.item.common.transfer.ItemImageTypeTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.item.server.entity.ItemImageType;
import com.echothree.model.data.item.server.entity.ItemImageTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ItemImageTypeTransferCache
        extends BaseItemTransferCache<ItemImageType, ItemImageTypeTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    
    /** Creates a new instance of ItemImageTypeTransferCache */
    public ItemImageTypeTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public ItemImageTypeTransfer getTransfer(ItemImageType itemImageType) {
        ItemImageTypeTransfer itemImageTypeTransfer = get(itemImageType);
        
        if(itemImageTypeTransfer == null) {
            ItemImageTypeDetail itemImageTypeDetail = itemImageType.getLastDetail();
            String itemImageTypeName = itemImageTypeDetail.getItemImageTypeName();
            MimeType preferredMimeType = itemImageTypeDetail.getPreferredMimeType();
            MimeTypeTransfer preferredMimeTypeTransfer = preferredMimeType == null ? null : coreControl.getMimeTypeTransfer(userVisit, preferredMimeType);
            Integer quality = itemImageTypeDetail.getQuality();
            Boolean isDefault = itemImageTypeDetail.getIsDefault();
            Integer sortOrder = itemImageTypeDetail.getSortOrder();
            String description = itemControl.getBestItemImageTypeDescription(itemImageType, getLanguage());
            
            itemImageTypeTransfer = new ItemImageTypeTransfer(itemImageTypeName, preferredMimeTypeTransfer, quality, isDefault, sortOrder, description);
            put(itemImageType, itemImageTypeTransfer);
        }
        
        return itemImageTypeTransfer;
    }
    
}
