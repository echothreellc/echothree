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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.common.transfer.ItemAccountingCategoryDescriptionTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategoryDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ItemAccountingCategoryDescriptionTransferCache
        extends BaseAccountingDescriptionTransferCache<ItemAccountingCategoryDescription, ItemAccountingCategoryDescriptionTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);

    /** Creates a new instance of ItemAccountingCategoryDescriptionTransferCache */
    public ItemAccountingCategoryDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    @Override
    public ItemAccountingCategoryDescriptionTransfer getTransfer(ItemAccountingCategoryDescription itemAccountingCategoryDescription) {
        var itemAccountingCategoryDescriptionTransfer = get(itemAccountingCategoryDescription);
        
        if(itemAccountingCategoryDescriptionTransfer == null) {
            var itemAccountingCategoryTransferCache = accountingControl.getAccountingTransferCaches(userVisit).getItemAccountingCategoryTransferCache();
            var itemAccountingCategoryTransfer = itemAccountingCategoryTransferCache.getTransfer(itemAccountingCategoryDescription.getItemAccountingCategory());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, itemAccountingCategoryDescription.getLanguage());
            
            itemAccountingCategoryDescriptionTransfer = new ItemAccountingCategoryDescriptionTransfer(languageTransfer, itemAccountingCategoryTransfer, itemAccountingCategoryDescription.getDescription());
            put(itemAccountingCategoryDescription, itemAccountingCategoryDescriptionTransfer);
        }
        
        return itemAccountingCategoryDescriptionTransfer;
    }
    
}
