// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.control.user.vendor.common.spec.ItemPurchasingCategoryUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.vendor.common.exception.UnknownDefaultItemPurchasingCategoryException;
import com.echothree.model.control.vendor.common.exception.UnknownItemPurchasingCategoryNameException;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.vendor.server.entity.ItemPurchasingCategory;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ItemPurchasingCategoryLogic
        extends BaseLogic {

    protected ItemPurchasingCategoryLogic() {
        super();
    }

    public static ItemPurchasingCategoryLogic getInstance() {
        return CDI.current().select(ItemPurchasingCategoryLogic.class).get();
    }

    public ItemPurchasingCategory getItemPurchasingCategoryByName(final ExecutionErrorAccumulator eea, final String itemPurchasingCategoryName,
            final EntityPermission entityPermission) {
        var vendorControl = Session.getModelController(VendorControl.class);
        var itemPurchasingCategory = vendorControl.getItemPurchasingCategoryByName(itemPurchasingCategoryName, entityPermission);

        if(itemPurchasingCategory == null) {
            handleExecutionError(UnknownItemPurchasingCategoryNameException.class, eea, ExecutionErrors.UnknownItemPurchasingCategoryName.name(), itemPurchasingCategoryName);
        }

        return itemPurchasingCategory;
    }

    public ItemPurchasingCategory getItemPurchasingCategoryByName(final ExecutionErrorAccumulator eea, final String itemPurchasingCategoryName) {
        return getItemPurchasingCategoryByName(eea, itemPurchasingCategoryName, EntityPermission.READ_ONLY);
    }

    public ItemPurchasingCategory getItemPurchasingCategoryByNameForUpdate(final ExecutionErrorAccumulator eea, final String itemPurchasingCategoryName) {
        return getItemPurchasingCategoryByName(eea, itemPurchasingCategoryName, EntityPermission.READ_WRITE);
    }

    public ItemPurchasingCategory getItemPurchasingCategoryByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemPurchasingCategoryUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        ItemPurchasingCategory itemPurchasingCategory = null;
        var vendorControl = Session.getModelController(VendorControl.class);
        var itemPurchasingCategoryName = universalSpec.getItemPurchasingCategoryName();
        var parameterCount = (itemPurchasingCategoryName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    itemPurchasingCategory = vendorControl.getDefaultItemPurchasingCategory(entityPermission);

                    if(itemPurchasingCategory == null) {
                        handleExecutionError(UnknownDefaultItemPurchasingCategoryException.class, eea, ExecutionErrors.UnknownDefaultItemPurchasingCategory.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(itemPurchasingCategoryName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ItemPurchasingCategory.name());

                    if(!eea.hasExecutionErrors()) {
                        itemPurchasingCategory = vendorControl.getItemPurchasingCategoryByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    itemPurchasingCategory = getItemPurchasingCategoryByName(eea, itemPurchasingCategoryName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return itemPurchasingCategory;
    }

    public ItemPurchasingCategory getItemPurchasingCategoryByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemPurchasingCategoryUniversalSpec universalSpec, boolean allowDefault) {
        return getItemPurchasingCategoryByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ItemPurchasingCategory getItemPurchasingCategoryByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ItemPurchasingCategoryUniversalSpec universalSpec, boolean allowDefault) {
        return getItemPurchasingCategoryByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    private long countItemsByItemPurchasingCategoryChildren(final VendorControl vendorControl, final ItemControl itemControl,
            final ItemPurchasingCategory parentItemPurchasingCategory) {
        var itemPurchasingCategoryChildren = vendorControl.getItemPurchasingCategoriesByParentItemPurchasingCategory(parentItemPurchasingCategory);
        var total = itemControl.countItemsByItemPurchasingCategory(parentItemPurchasingCategory);

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
