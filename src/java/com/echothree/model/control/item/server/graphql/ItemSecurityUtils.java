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
import com.echothree.control.user.item.server.command.GetRelatedItemCommand;
import com.echothree.control.user.item.server.command.GetRelatedItemTypeCommand;
import com.echothree.control.user.item.server.command.GetRelatedItemTypesCommand;
import com.echothree.control.user.item.server.command.GetRelatedItemsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface ItemSecurityUtils {

    static boolean getHasItemAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemCommand.class);
    }

    static boolean getHasItemsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemsCommand.class);
    }

    static boolean getHasItemUnitOfMeasureTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemUnitOfMeasureTypeCommand.class);
    }

    static boolean getHasItemUnitOfMeasureTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemUnitOfMeasureTypesCommand.class);
    }

    static boolean getHasItemDescriptionAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemDescriptionCommand.class);
    }

    static boolean getHasItemDescriptionsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemDescriptionsCommand.class);
    }

    static boolean getHasItemPriceAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemPriceCommand.class);
    }

    static boolean getHasItemPricesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemPricesCommand.class);
    }

    static boolean getHasItemTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemTypeCommand.class);
    }

    static boolean getHasItemTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemTypesCommand.class);
    }

    static boolean getHasItemAliasChecksumTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemAliasChecksumTypeCommand.class);
    }

    static boolean getHasItemAliasChecksumTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemAliasChecksumTypesCommand.class);
    }

    static boolean getHasItemAliasTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemAliasTypeCommand.class);
    }

    static boolean getHasItemAliasTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemAliasTypesCommand.class);
    }

    static boolean getHasItemAliasAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemAliasCommand.class);
    }

    static boolean getHasItemAliasesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemAliasesCommand.class);
    }

    static boolean getHasItemPriceTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemPriceTypeCommand.class);
    }

    static boolean getHasItemPriceTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemPriceTypesCommand.class);
    }

    static boolean getHasItemDeliveryTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemDeliveryTypeCommand.class);
    }

    static boolean getHasItemDeliveryTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemDeliveryTypesCommand.class);
    }

    static boolean getHasItemUseTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemUseTypeCommand.class);
    }

    static boolean getHasItemUseTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemUseTypesCommand.class);
    }

    static boolean getHasItemInventoryTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemInventoryTypeCommand.class);
    }

    static boolean getHasItemInventoryTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemInventoryTypesCommand.class);
    }

    static boolean getHasItemCategoryAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemCategoryCommand.class);
    }

    static boolean getHasItemCategoriesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemCategoriesCommand.class);
    }

    static boolean getHasItemDescriptionTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemDescriptionTypeCommand.class);
    }

    static boolean getHasItemDescriptionTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemDescriptionTypesCommand.class);
    }

    static boolean getHasItemImageTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemImageTypeCommand.class);
    }

    static boolean getHasItemImageTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemImageTypesCommand.class);
    }

    static boolean getHasItemDescriptionTypeUseTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemDescriptionTypeUseTypeCommand.class);
    }

    static boolean getHasItemDescriptionTypeUseTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemDescriptionTypeUseTypesCommand.class);
    }

    static boolean getHasItemDescriptionTypeUseAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemDescriptionTypeUseCommand.class);
    }

    static boolean getHasItemDescriptionTypeUsesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemDescriptionTypeUsesCommand.class);
    }

    static boolean getHasRelatedItemTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetRelatedItemTypeCommand.class);
    }

    static boolean getHasRelatedItemTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetRelatedItemTypesCommand.class);
    }

    static boolean getHasRelatedItemAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetRelatedItemCommand.class);
    }

    static boolean getHasRelatedItemsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetRelatedItemsCommand.class);
    }

}
