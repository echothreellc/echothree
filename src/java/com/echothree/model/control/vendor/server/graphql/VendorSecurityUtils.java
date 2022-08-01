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

package com.echothree.model.control.vendor.server.graphql;

import com.echothree.control.user.vendor.server.command.GetItemPurchasingCategoriesCommand;
import com.echothree.control.user.vendor.server.command.GetItemPurchasingCategoryCommand;
import com.echothree.control.user.vendor.server.command.GetVendorCommand;
import com.echothree.control.user.vendor.server.command.GetVendorsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public final class VendorSecurityUtils
        extends BaseGraphQl {

    private static class AccountingSecurityUtilsHolder {
        static VendorSecurityUtils instance = new VendorSecurityUtils();
    }
    
    public static VendorSecurityUtils getInstance() {
        return AccountingSecurityUtilsHolder.instance;
    }

    public boolean getHasItemPurchasingCategoryAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemPurchasingCategoryCommand.class);
    }

    public boolean getHasItemPurchasingCategoriesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemPurchasingCategoriesCommand.class);
    }

    public boolean getHasVendorAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetVendorCommand.class);
    }

    public boolean getHasVendorsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetVendorsCommand.class);
    }

}
