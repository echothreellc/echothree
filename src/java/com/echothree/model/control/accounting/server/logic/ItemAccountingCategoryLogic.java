// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.accounting.server.logic;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class ItemAccountingCategoryLogic {
    
    private ItemAccountingCategoryLogic() {
        super();
    }
    
    private static class ItemAccountingCategoryLogicHolder {
        static ItemAccountingCategoryLogic instance = new ItemAccountingCategoryLogic();
    }
    
    public static ItemAccountingCategoryLogic getInstance() {
        return ItemAccountingCategoryLogicHolder.instance;
    }

    private long countItemsByItemAccountingCategoryChildren(final AccountingControl accountingControl, final ItemControl itemControl,
            final ItemAccountingCategory parentItemAccountingCategory) {
        List<ItemAccountingCategory> itemAccountingCategoryChildren = accountingControl.getItemAccountingCategoriesByParentItemAccountingCategory(parentItemAccountingCategory);
        long total = itemControl.countItemsByItemAccountingCategory(parentItemAccountingCategory);

        total = itemAccountingCategoryChildren.stream().map((childItemAccountingCategory) -> countItemsByItemAccountingCategoryChildren(accountingControl, itemControl, childItemAccountingCategory)).reduce(total, (accumulator, _item) -> accumulator + _item);

        return total;
    }

    public void checkDeleteItemAccountingCategory(final ExecutionErrorAccumulator ema, final ItemAccountingCategory itemAccountingCategory) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var itemControl = Session.getModelController(ItemControl.class);
        
        if(countItemsByItemAccountingCategoryChildren(accountingControl, itemControl, itemAccountingCategory) != 0) {
            ema.addExecutionError(ExecutionErrors.CannotDeleteItemAccountingCategoryInUse.name(), itemAccountingCategory.getLastDetail().getItemAccountingCategoryName());
        }
    }

    public void deleteItemAccountingCategory(final ItemAccountingCategory itemAccountingCategory, final BasePK deletedBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);

        accountingControl.deleteItemAccountingCategory(itemAccountingCategory, deletedBy);
    }

}
