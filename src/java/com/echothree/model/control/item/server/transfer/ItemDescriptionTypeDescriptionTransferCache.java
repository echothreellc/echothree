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

import com.echothree.model.control.item.common.transfer.ItemDescriptionTypeDescriptionTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemDescriptionTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ItemDescriptionTypeDescriptionTransferCache
        extends BaseItemDescriptionTransferCache<ItemDescriptionTypeDescription, ItemDescriptionTypeDescriptionTransfer> {

    ItemControl itemControl = Session.getModelController(ItemControl.class);

    /** Creates a new instance of ItemDescriptionTypeDescriptionTransferCache */
    public ItemDescriptionTypeDescriptionTransferCache() {
        super();
    }
    
    @Override
    public ItemDescriptionTypeDescriptionTransfer getTransfer(UserVisit userVisit, ItemDescriptionTypeDescription itemDescriptionTypeDescription) {
        var itemDescriptionTypeDescriptionTransfer = get(itemDescriptionTypeDescription);
        
        if(itemDescriptionTypeDescriptionTransfer == null) {
            var itemDescriptionTypeTransfer = itemControl.getItemDescriptionTypeTransfer(userVisit, itemDescriptionTypeDescription.getItemDescriptionType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, itemDescriptionTypeDescription.getLanguage());
            
            itemDescriptionTypeDescriptionTransfer = new ItemDescriptionTypeDescriptionTransfer(languageTransfer, itemDescriptionTypeTransfer, itemDescriptionTypeDescription.getDescription());
            put(userVisit, itemDescriptionTypeDescription, itemDescriptionTypeDescriptionTransfer);
        }
        
        return itemDescriptionTypeDescriptionTransfer;
    }
    
}
