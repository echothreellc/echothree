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

package com.echothree.model.control.item.server.logic;

import com.echothree.model.control.item.common.exception.MissingDefaultItemCategoryException;
import com.echothree.model.control.item.common.exception.UnknownItemCategoryNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ItemCategoryLogic
        extends BaseLogic {

    protected ItemCategoryLogic() {
        super();
    }

    public static ItemCategoryLogic getInstance() {
        return CDI.current().select(ItemCategoryLogic.class).get();
    }

    public ItemCategory getItemCategoryByName(final ExecutionErrorAccumulator eea, final String itemCategoryName, EntityPermission entityPermission) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemCategory = itemControl.getItemCategoryByName(itemCategoryName, entityPermission);

        if(itemCategory == null) {
            handleExecutionError(UnknownItemCategoryNameException.class, eea, ExecutionErrors.UnknownItemCategoryName.name(), itemCategoryName);
        }

        return itemCategory;
    }

    public ItemCategory getItemCategoryByName(final ExecutionErrorAccumulator eea, final String itemCategoryName) {
        return getItemCategoryByName(eea, itemCategoryName, EntityPermission.READ_ONLY);
    }

    public ItemCategory getItemCategoryByNameForUpdate(final ExecutionErrorAccumulator eea, final String itemCategoryName) {
        return getItemCategoryByName(eea, itemCategoryName, EntityPermission.READ_WRITE);
    }

    private long countItemsByItemCategoryChildren(final ItemControl itemControl, final ItemCategory parentItemCategory) {
        var itemCategoryChildren = itemControl.getItemCategoriesByParentItemCategory(parentItemCategory);
        var total = itemControl.countItemsByItemCategory(parentItemCategory);

        total = itemCategoryChildren.stream().map((childItemCategory) -> countItemsByItemCategoryChildren(itemControl, childItemCategory)).reduce(total, (accumulator, _item) -> accumulator + _item);

        return total;
    }

    public void checkDeleteItemCategory(final ExecutionErrorAccumulator ema, final ItemCategory itemCategory) {
        var itemControl = Session.getModelController(ItemControl.class);
        
        if(countItemsByItemCategoryChildren(itemControl, itemCategory) != 0) {
            ema.addExecutionError(ExecutionErrors.CannotDeleteItemCategoryInUse.name(), itemCategory.getLastDetail().getItemCategoryName());
        }
    }


    public ItemCategory getDefaultItemCategory(final ExecutionErrorAccumulator eea) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemCategory = itemControl.getDefaultItemCategory();

        if(itemCategory == null) {
            handleExecutionError(MissingDefaultItemCategoryException.class, eea, ExecutionErrors.MissingDefaultItemCategory.name());
        }
        
        return itemCategory;
    }
    
    public void deleteItemCategory(final ItemCategory itemCategory, final BasePK deletedBy) {
        var itemControl = Session.getModelController(ItemControl.class);

        itemControl.deleteItemCategory(itemCategory, deletedBy);
    }

}
