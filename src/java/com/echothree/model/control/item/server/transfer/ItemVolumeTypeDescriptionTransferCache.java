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

import com.echothree.model.control.item.common.transfer.ItemVolumeTypeDescriptionTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemVolumeTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ItemVolumeTypeDescriptionTransferCache
        extends BaseItemDescriptionTransferCache<ItemVolumeTypeDescription, ItemVolumeTypeDescriptionTransfer> {

    ItemControl itemControl = Session.getModelController(ItemControl.class);

    /** Creates a new instance of ItemVolumeTypeDescriptionTransferCache */
    protected ItemVolumeTypeDescriptionTransferCache() {
        super();
    }
    
    @Override
    public ItemVolumeTypeDescriptionTransfer getTransfer(UserVisit userVisit, ItemVolumeTypeDescription itemVolumeTypeDescription) {
        var itemVolumeTypeDescriptionTransfer = get(itemVolumeTypeDescription);
        
        if(itemVolumeTypeDescriptionTransfer == null) {
            var itemVolumeTypeTransferCache = itemControl.getItemTransferCaches().getItemVolumeTypeTransferCache();
            var itemVolumeTypeTransfer = itemVolumeTypeTransferCache.getTransfer(userVisit, itemVolumeTypeDescription.getItemVolumeType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, itemVolumeTypeDescription.getLanguage());
            
            itemVolumeTypeDescriptionTransfer = new ItemVolumeTypeDescriptionTransfer(languageTransfer, itemVolumeTypeTransfer, itemVolumeTypeDescription.getDescription());
            put(userVisit, itemVolumeTypeDescription, itemVolumeTypeDescriptionTransfer);
        }
        
        return itemVolumeTypeDescriptionTransfer;
    }
    
}
