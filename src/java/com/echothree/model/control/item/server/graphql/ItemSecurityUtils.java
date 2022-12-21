// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.item.server.graphql;

import com.echothree.control.user.item.server.command.GetItemAliasChecksumTypeCommand;
import com.echothree.control.user.item.server.command.GetItemAliasChecksumTypesCommand;
import com.echothree.control.user.item.server.command.GetItemAliasCommand;
import com.echothree.control.user.item.server.command.GetItemAliasTypeCommand;
import com.echothree.control.user.item.server.command.GetItemAliasTypesCommand;
import com.echothree.control.user.item.server.command.GetItemAliasesCommand;
import com.echothree.control.user.item.server.command.GetItemCategoriesCommand;
import com.echothree.control.user.item.server.command.GetItemCategoryCommand;
import com.echothree.control.user.item.server.command.GetItemCommand;
import com.echothree.control.user.item.server.command.GetItemDeliveryTypeCommand;
import com.echothree.control.user.item.server.command.GetItemDeliveryTypesCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionTypeCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionTypeUseCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionTypeUseTypeCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionTypeUseTypesCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionTypeUsesCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionTypesCommand;
import com.echothree.control.user.item.server.command.GetItemDescriptionsCommand;
import com.echothree.control.user.item.server.command.GetItemImageTypeCommand;
import com.echothree.control.user.item.server.command.GetItemImageTypesCommand;
import com.echothree.control.user.item.server.command.GetItemInventoryTypeCommand;
import com.echothree.control.user.item.server.command.GetItemInventoryTypesCommand;
import com.echothree.control.user.item.server.command.GetItemPriceCommand;
import com.echothree.control.user.item.server.command.GetItemPriceTypeCommand;
import com.echothree.control.user.item.server.command.GetItemPriceTypesCommand;
import com.echothree.control.user.item.server.command.GetItemPricesCommand;
import com.echothree.control.user.item.server.command.GetItemTypeCommand;
import com.echothree.control.user.item.server.command.GetItemTypesCommand;
import com.echothree.control.user.item.server.command.GetItemUnitOfMeasureTypeCommand;
import com.echothree.control.user.item.server.command.GetItemUnitOfMeasureTypesCommand;
import com.echothree.control.user.item.server.command.GetItemUseTypeCommand;
import com.echothree.control.user.item.server.command.GetItemUseTypesCommand;
import com.echothree.control.user.item.server.command.GetItemsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public final class ItemSecurityUtils
        extends BaseGraphQl {

    private static class ItemSecurityUtilsHolder {
        static ItemSecurityUtils instance = new ItemSecurityUtils();
    }
    
    public static ItemSecurityUtils getInstance() {
        return ItemSecurityUtilsHolder.instance;
    }

    public boolean getHasItemAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemCommand.class);
    }

    public boolean getHasItemsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemsCommand.class);
    }

    public boolean getHasItemUnitOfMeasureTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemUnitOfMeasureTypeCommand.class);
    }

    public boolean getHasItemUnitOfMeasureTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemUnitOfMeasureTypesCommand.class);
    }

    public boolean getHasItemDescriptionAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemDescriptionCommand.class);
    }

    public boolean getHasItemDescriptionsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemDescriptionsCommand.class);
    }

    public boolean getHasItemPriceAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemPriceCommand.class);
    }

    public boolean getHasItemPricesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemPricesCommand.class);
    }

    public boolean getHasItemTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemTypeCommand.class);
    }

    public boolean getHasItemTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemTypesCommand.class);
    }

    public boolean getHasItemAliasChecksumTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemAliasChecksumTypeCommand.class);
    }

    public boolean getHasItemAliasChecksumTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemAliasChecksumTypesCommand.class);
    }

    public boolean getHasItemAliasTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemAliasTypeCommand.class);
    }

    public boolean getHasItemAliasTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemAliasTypesCommand.class);
    }

    public boolean getHasItemAliasAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemAliasCommand.class);
    }

    public boolean getHasItemAliasesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemAliasesCommand.class);
    }

    public boolean getHasItemPriceTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemPriceTypeCommand.class);
    }

    public boolean getHasItemPriceTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemPriceTypesCommand.class);
    }

    public boolean getHasItemDeliveryTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemDeliveryTypeCommand.class);
    }

    public boolean getHasItemDeliveryTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemDeliveryTypesCommand.class);
    }

    public boolean getHasItemUseTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemUseTypeCommand.class);
    }

    public boolean getHasItemUseTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemUseTypesCommand.class);
    }

    public boolean getHasItemInventoryTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemInventoryTypeCommand.class);
    }

    public boolean getHasItemInventoryTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemInventoryTypesCommand.class);
    }

    public boolean getHasItemCategoryAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemCategoryCommand.class);
    }

    public boolean getHasItemCategoriesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemCategoriesCommand.class);
    }

    public boolean getHasItemDescriptionTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemDescriptionTypeCommand.class);
    }

    public boolean getHasItemDescriptionTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemDescriptionTypesCommand.class);
    }

    public boolean getHasItemImageTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemImageTypeCommand.class);
    }

    public boolean getHasItemImageTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemImageTypesCommand.class);
    }

    public boolean getHasItemDescriptionTypeUseTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemDescriptionTypeUseTypeCommand.class);
    }

    public boolean getHasItemDescriptionTypeUseTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemDescriptionTypeUseTypesCommand.class);
    }

    public boolean getHasItemDescriptionTypeUseAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemDescriptionTypeUseCommand.class);
    }

    public boolean getHasItemDescriptionTypeUsesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemDescriptionTypeUsesCommand.class);
    }

}
