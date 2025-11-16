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

import com.echothree.model.control.accounting.common.transfer.ItemAccountingCategoryTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ItemAccountingCategoryTransferCache
        extends BaseAccountingTransferCache<ItemAccountingCategory, ItemAccountingCategoryTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);

    /** Creates a new instance of ItemAccountingCategoryTransferCache */
    protected ItemAccountingCategoryTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public ItemAccountingCategoryTransfer getTransfer(UserVisit userVisit, ItemAccountingCategory itemAccountingCategory) {
        var itemAccountingCategoryTransfer = get(itemAccountingCategory);
        
        if(itemAccountingCategoryTransfer == null) {
            var itemAccountingCategoryDetail = itemAccountingCategory.getLastDetail();
            var itemAccountingCategoryName = itemAccountingCategoryDetail.getItemAccountingCategoryName();
            var parentItemAccountingCategory = itemAccountingCategoryDetail.getParentItemAccountingCategory();
            var parentItemAccountingCategoryTransfer = parentItemAccountingCategory == null ? null : getTransfer(userVisit, parentItemAccountingCategory);
            var inventoryGlAccount = itemAccountingCategoryDetail.getInventoryGlAccount();
            var inventoryGlAccountTransfer = inventoryGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, inventoryGlAccount);
            var salesGlAccount = itemAccountingCategoryDetail.getSalesGlAccount();
            var salesGlAccountTransfer = salesGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, salesGlAccount);
            var returnsGlAccount = itemAccountingCategoryDetail.getReturnsGlAccount();
            var returnsGlAccountTransfer = returnsGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, returnsGlAccount);
            var cogsGlAccount = itemAccountingCategoryDetail.getCogsGlAccount();
            var cogsGlAccountTransfer = cogsGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, cogsGlAccount);
            var returnsCogsGlAccount = itemAccountingCategoryDetail.getReturnsCogsGlAccount();
            var returnsCogsGlAccountTransfer = returnsCogsGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, returnsCogsGlAccount);
            var isDefault = itemAccountingCategoryDetail.getIsDefault();
            var sortOrder = itemAccountingCategoryDetail.getSortOrder();
            var description = accountingControl.getBestItemAccountingCategoryDescription(itemAccountingCategory, getLanguage(userVisit));
            
            itemAccountingCategoryTransfer = new ItemAccountingCategoryTransfer(itemAccountingCategoryName,
                    parentItemAccountingCategoryTransfer, inventoryGlAccountTransfer, salesGlAccountTransfer,
                    returnsGlAccountTransfer, cogsGlAccountTransfer, returnsCogsGlAccountTransfer, isDefault, sortOrder,
                    description);
            put(userVisit, itemAccountingCategory, itemAccountingCategoryTransfer);
        }
        
        return itemAccountingCategoryTransfer;
    }
    
}
