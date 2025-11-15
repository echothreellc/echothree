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

import com.echothree.model.control.item.common.transfer.ItemDescriptionTypeUseTypeDescriptionTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeUseTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ItemDescriptionTypeUseTypeDescriptionTransferCache
        extends BaseItemDescriptionTransferCache<ItemDescriptionTypeUseTypeDescription, ItemDescriptionTypeUseTypeDescriptionTransfer> {

    ItemControl itemControl = Session.getModelController(ItemControl.class);

    /** Creates a new instance of ItemDescriptionTypeUseTypeDescriptionTransferCache */
    public ItemDescriptionTypeUseTypeDescriptionTransferCache() {
        super();
    }
    
    @Override
    public ItemDescriptionTypeUseTypeDescriptionTransfer getTransfer(UserVisit userVisit, ItemDescriptionTypeUseTypeDescription itemDescriptionTypeUseTypeDescription) {
        var itemDescriptionTypeUseTypeDescriptionTransfer = get(itemDescriptionTypeUseTypeDescription);
        
        if(itemDescriptionTypeUseTypeDescriptionTransfer == null) {
            var itemDescriptionTypeUseTypeTransfer = itemControl.getItemDescriptionTypeUseTypeTransfer(userVisit, itemDescriptionTypeUseTypeDescription.getItemDescriptionTypeUseType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, itemDescriptionTypeUseTypeDescription.getLanguage());
            
            itemDescriptionTypeUseTypeDescriptionTransfer = new ItemDescriptionTypeUseTypeDescriptionTransfer(languageTransfer, itemDescriptionTypeUseTypeTransfer, itemDescriptionTypeUseTypeDescription.getDescription());
            put(userVisit, itemDescriptionTypeUseTypeDescription, itemDescriptionTypeUseTypeDescriptionTransfer);
        }
        
        return itemDescriptionTypeUseTypeDescriptionTransfer;
    }
    
}
