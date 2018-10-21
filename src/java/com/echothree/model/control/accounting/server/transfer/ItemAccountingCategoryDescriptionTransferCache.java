// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.remote.transfer.ItemAccountingCategoryDescriptionTransfer;
import com.echothree.model.control.accounting.remote.transfer.ItemAccountingCategoryTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategoryDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ItemAccountingCategoryDescriptionTransferCache
        extends BaseAccountingDescriptionTransferCache<ItemAccountingCategoryDescription, ItemAccountingCategoryDescriptionTransfer> {
    
    /** Creates a new instance of ItemAccountingCategoryDescriptionTransferCache */
    public ItemAccountingCategoryDescriptionTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
    }
    
    @Override
    public ItemAccountingCategoryDescriptionTransfer getTransfer(ItemAccountingCategoryDescription itemAccountingCategoryDescription) {
        ItemAccountingCategoryDescriptionTransfer itemAccountingCategoryDescriptionTransfer = get(itemAccountingCategoryDescription);
        
        if(itemAccountingCategoryDescriptionTransfer == null) {
            ItemAccountingCategoryTransferCache itemAccountingCategoryTransferCache = accountingControl.getAccountingTransferCaches(userVisit).getItemAccountingCategoryTransferCache();
            ItemAccountingCategoryTransfer itemAccountingCategoryTransfer = itemAccountingCategoryTransferCache.getTransfer(itemAccountingCategoryDescription.getItemAccountingCategory());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, itemAccountingCategoryDescription.getLanguage());
            
            itemAccountingCategoryDescriptionTransfer = new ItemAccountingCategoryDescriptionTransfer(languageTransfer, itemAccountingCategoryTransfer, itemAccountingCategoryDescription.getDescription());
            put(itemAccountingCategoryDescription, itemAccountingCategoryDescriptionTransfer);
        }
        
        return itemAccountingCategoryDescriptionTransfer;
    }
    
}
