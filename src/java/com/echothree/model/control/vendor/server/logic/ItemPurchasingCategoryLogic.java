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

package com.echothree.model.control.vendor.server.logic;

import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class ItemPurchasingCategoryLogic {
    
    private ItemPurchasingCategoryLogic() {
        super();
    }
    
    private static class ItemPurchasingCategoryLogicHolder {
        static ItemPurchasingCategoryLogic instance = new ItemPurchasingCategoryLogic();
    }
    
    public static ItemPurchasingCategoryLogic getInstance() {
        return ItemPurchasingCategoryLogicHolder.instance;
    }

    private long countItemsByItemPurchasingCategoryChildren(final VendorControl vendorControl, final ItemControl itemControl,
            final ItemPurchasingCategory parentItemPurchasingCategory) {
        List<ItemPurchasingCategory> itemPurchasingCategoryChildren = vendorControl.getItemPurchasingCategoriesByParentItemPurchasingCategory(parentItemPurchasingCategory);
        long total = itemControl.countItemsByItemPurchasingCategory(parentItemPurchasingCategory);

        total = itemPurchasingCategoryChildren.stream().map((childItemPurchasingCategory) -> countItemsByItemPurchasingCategoryChildren(vendorControl, itemControl, childItemPurchasingCategory)).reduce(total, (accumulator, _item) -> accumulator + _item);

        return total;
    }

    public void checkDeleteItemPurchasingCategory(final ExecutionErrorAccumulator ema, final ItemPurchasingCategory itemPurchasingCategory) {
        var vendorControl = Session.getModelController(VendorControl.class);
        var itemControl = Session.getModelController(ItemControl.class);
        
        if(countItemsByItemPurchasingCategoryChildren(vendorControl, itemControl, itemPurchasingCategory) != 0) {
            ema.addExecutionError(ExecutionErrors.CannotDeleteItemPurchasingCategoryInUse.name(), itemPurchasingCategory.getLastDetail().getItemPurchasingCategoryName());
        }
    }

    public void deleteItemPurchasingCategory(final ItemPurchasingCategory itemPurchasingCategory, final BasePK deletedBy) {
        var vendorControl = Session.getModelController(VendorControl.class);

        vendorControl.deleteItemPurchasingCategory(itemPurchasingCategory, deletedBy);
    }

}
