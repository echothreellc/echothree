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

package com.echothree.model.control.accounting.server.logic;

import com.echothree.control.user.accounting.common.spec.ItemAccountingCategoryUniversalSpec;
import com.echothree.model.control.accounting.common.exception.DuplicateItemAccountingCategoryNameException;
import com.echothree.model.control.accounting.common.exception.UnknownDefaultItemAccountingCategoryException;
import com.echothree.model.control.accounting.common.exception.UnknownItemAccountingCategoryNameException;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.accounting.server.value.ItemAccountingCategoryDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ItemAccountingCategoryLogic
        extends BaseLogic {

    protected ItemAccountingCategoryLogic() {
        super();
    }

    public static ItemAccountingCategoryLogic getInstance() {
        return CDI.current().select(ItemAccountingCategoryLogic.class).get();
    }

    public ItemAccountingCategory createItemAccountingCategory(final ExecutionErrorAccumulator eea, final String itemAccountingCategoryName,
            final ItemAccountingCategory parentItemAccountingCategory, final GlAccount inventoryGlAccount, final GlAccount salesGlAccount,
            final GlAccount returnsGlAccount, final GlAccount cogsGlAccount, final GlAccount returnsCogsGlAccount, final Boolean isDefault,
            final Integer sortOrder, final Language language, final String description, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var itemAccountingCategory = accountingControl.getItemAccountingCategoryByName(itemAccountingCategoryName);

        if(itemAccountingCategory == null) {
            itemAccountingCategory = accountingControl.createItemAccountingCategory(itemAccountingCategoryName,
                    parentItemAccountingCategory, inventoryGlAccount, salesGlAccount, returnsGlAccount, cogsGlAccount,
                    returnsCogsGlAccount, isDefault, sortOrder, createdBy);

            if(description != null) {
                accountingControl.createItemAccountingCategoryDescription(itemAccountingCategory, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateItemAccountingCategoryNameException.class, eea, ExecutionErrors.DuplicateItemAccountingCategoryName.name(),
                    itemAccountingCategoryName);
        }

        return itemAccountingCategory;
    }

    public ItemAccountingCategory getItemAccountingCategoryByName(final ExecutionErrorAccumulator eea, final String itemAccountingCategoryName,
            final EntityPermission entityPermission) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var itemAccountingCategory = accountingControl.getItemAccountingCategoryByName(itemAccountingCategoryName, entityPermission);

        if(itemAccountingCategory == null) {
            handleExecutionError(UnknownItemAccountingCategoryNameException.class, eea, ExecutionErrors.UnknownItemAccountingCategoryName.name(), itemAccountingCategoryName);
        }

        return itemAccountingCategory;
    }

    public ItemAccountingCategory getItemAccountingCategoryByName(final ExecutionErrorAccumulator eea, final String itemAccountingCategoryName) {
        return getItemAccountingCategoryByName(eea, itemAccountingCategoryName, EntityPermission.READ_ONLY);
    }

    public ItemAccountingCategory getItemAccountingCategoryByNameForUpdate(final ExecutionErrorAccumulator eea, final String itemAccountingCategoryName) {
        return getItemAccountingCategoryByName(eea, itemAccountingCategoryName, EntityPermission.READ_WRITE);
    }

    public ItemAccountingCategory getItemAccountingCategoryByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemAccountingCategoryUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        ItemAccountingCategory itemAccountingCategory = null;
        var accountingControl = Session.getModelController(AccountingControl.class);
        var itemAccountingCategoryName = universalSpec.getItemAccountingCategoryName();
        var parameterCount = (itemAccountingCategoryName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    itemAccountingCategory = accountingControl.getDefaultItemAccountingCategory(entityPermission);

                    if(itemAccountingCategory == null) {
                        handleExecutionError(UnknownDefaultItemAccountingCategoryException.class, eea, ExecutionErrors.UnknownDefaultItemAccountingCategory.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(itemAccountingCategoryName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.ItemAccountingCategory.name());

                    if(!eea.hasExecutionErrors()) {
                        itemAccountingCategory = accountingControl.getItemAccountingCategoryByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    itemAccountingCategory = getItemAccountingCategoryByName(eea, itemAccountingCategoryName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return itemAccountingCategory;
    }

    public ItemAccountingCategory getItemAccountingCategoryByUniversalSpec(final ExecutionErrorAccumulator eea,
            final ItemAccountingCategoryUniversalSpec universalSpec, boolean allowDefault) {
        return getItemAccountingCategoryByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public ItemAccountingCategory getItemAccountingCategoryByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final ItemAccountingCategoryUniversalSpec universalSpec, boolean allowDefault) {
        return getItemAccountingCategoryByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateItemAccountingCategoryFromValue(ItemAccountingCategoryDetailValue itemAccountingCategoryDetailValue, BasePK updatedBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);

        accountingControl.updateItemAccountingCategoryFromValue(itemAccountingCategoryDetailValue, updatedBy);
    }

    private long countItemsByItemAccountingCategoryChildren(final AccountingControl accountingControl, final ItemControl itemControl,
            final ItemAccountingCategory parentItemAccountingCategory) {
        var itemAccountingCategoryChildren = accountingControl.getItemAccountingCategoriesByParentItemAccountingCategory(parentItemAccountingCategory);
        var total = itemControl.countItemsByItemAccountingCategory(parentItemAccountingCategory);

        total = itemAccountingCategoryChildren.stream().map((childItemAccountingCategory) ->
                countItemsByItemAccountingCategoryChildren(accountingControl, itemControl, childItemAccountingCategory)).reduce(total, Long::sum);

        return total;
    }

    private void checkDeleteItemAccountingCategory(final ExecutionErrorAccumulator eea, final ItemAccountingCategory itemAccountingCategory) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var itemControl = Session.getModelController(ItemControl.class);
        
        if(countItemsByItemAccountingCategoryChildren(accountingControl, itemControl, itemAccountingCategory) != 0) {
            eea.addExecutionError(ExecutionErrors.CannotDeleteItemAccountingCategoryInUse.name(),
                    itemAccountingCategory.getLastDetail().getItemAccountingCategoryName());
        }
    }

    public void deleteItemAccountingCategory(final ExecutionErrorAccumulator eea, final ItemAccountingCategory itemAccountingCategory, final BasePK deletedBy) {
        checkDeleteItemAccountingCategory(eea, itemAccountingCategory);

        if(!eea.hasExecutionErrors()) {
            var accountingControl = Session.getModelController(AccountingControl.class);

            accountingControl.deleteItemAccountingCategory(itemAccountingCategory, deletedBy);
        }
    }

}
