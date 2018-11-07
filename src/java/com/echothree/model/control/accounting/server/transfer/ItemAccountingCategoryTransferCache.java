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

import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.common.transfer.ItemAccountingCategoryTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategoryDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ItemAccountingCategoryTransferCache
        extends BaseAccountingTransferCache<ItemAccountingCategory, ItemAccountingCategoryTransfer> {
    
    /** Creates a new instance of ItemAccountingCategoryTransferCache */
    public ItemAccountingCategoryTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public ItemAccountingCategoryTransfer getTransfer(ItemAccountingCategory itemAccountingCategory) {
        ItemAccountingCategoryTransfer itemAccountingCategoryTransfer = get(itemAccountingCategory);
        
        if(itemAccountingCategoryTransfer == null) {
            ItemAccountingCategoryDetail itemAccountingCategoryDetail = itemAccountingCategory.getLastDetail();
            String itemAccountingCategoryName = itemAccountingCategoryDetail.getItemAccountingCategoryName();
            ItemAccountingCategory parentItemAccountingCategory = itemAccountingCategoryDetail.getParentItemAccountingCategory();
            ItemAccountingCategoryTransfer parentItemAccountingCategoryTransfer = parentItemAccountingCategory == null? null: getTransfer(parentItemAccountingCategory);
            GlAccount inventoryGlAccount = itemAccountingCategoryDetail.getInventoryGlAccount();
            GlAccountTransfer inventoryGlAccountTransfer = inventoryGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, inventoryGlAccount);
            GlAccount salesGlAccount = itemAccountingCategoryDetail.getSalesGlAccount();
            GlAccountTransfer salesGlAccountTransfer = salesGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, salesGlAccount);
            GlAccount returnsGlAccount = itemAccountingCategoryDetail.getReturnsGlAccount();
            GlAccountTransfer returnsGlAccountTransfer = returnsGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, returnsGlAccount);
            GlAccount cogsGlAccount = itemAccountingCategoryDetail.getCogsGlAccount();
            GlAccountTransfer cogsGlAccountTransfer = cogsGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, cogsGlAccount);
            GlAccount returnsCogsGlAccount = itemAccountingCategoryDetail.getReturnsCogsGlAccount();
            GlAccountTransfer returnsCogsGlAccountTransfer = returnsCogsGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, returnsCogsGlAccount);
            Boolean isDefault = itemAccountingCategoryDetail.getIsDefault();
            Integer sortOrder = itemAccountingCategoryDetail.getSortOrder();
            String description = accountingControl.getBestItemAccountingCategoryDescription(itemAccountingCategory, getLanguage());
            
            itemAccountingCategoryTransfer = new ItemAccountingCategoryTransfer(itemAccountingCategoryName,
                    parentItemAccountingCategoryTransfer, inventoryGlAccountTransfer, salesGlAccountTransfer,
                    returnsGlAccountTransfer, cogsGlAccountTransfer, returnsCogsGlAccountTransfer, isDefault, sortOrder,
                    description);
            put(itemAccountingCategory, itemAccountingCategoryTransfer);
        }
        
        return itemAccountingCategoryTransfer;
    }
    
}
