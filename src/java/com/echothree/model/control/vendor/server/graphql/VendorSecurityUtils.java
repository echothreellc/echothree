// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.server.command.GetItemPurchasingCategoriesCommand;
import com.echothree.control.user.vendor.server.command.GetItemPurchasingCategoryCommand;
import com.echothree.control.user.vendor.server.command.GetVendorCommand;
import com.echothree.control.user.vendor.server.command.GetVendorItemCommand;
import com.echothree.control.user.vendor.server.command.GetVendorItemCostCommand;
import com.echothree.control.user.vendor.server.command.GetVendorItemCostsCommand;
import com.echothree.control.user.vendor.server.command.GetVendorItemsCommand;
import com.echothree.control.user.vendor.server.command.GetVendorTypeCommand;
import com.echothree.control.user.vendor.server.command.GetVendorTypesCommand;
import com.echothree.control.user.vendor.server.command.GetVendorsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.form.BaseForm;
import graphql.schema.DataFetchingEnvironment;
import javax.naming.NamingException;

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

    public boolean getHasVendorTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetVendorTypeCommand.class);
    }

    public boolean getHasVendorTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetVendorTypesCommand.class);
    }

    public boolean getHasVendorAccess(final DataFetchingEnvironment env, final Party party) {
        var partyDetail = party.getLastDetail();
        BaseForm baseForm;

        // GetVendorCommand has a security() function that needs the form to be available.
        try {
            var commandForm = VendorUtil.getHome().getGetVendorForm();

            commandForm.setPartyName(partyDetail.getPartyName());
            baseForm = commandForm;
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return getGraphQlExecutionContext(env).hasAccess(GetVendorCommand.class, baseForm);
    }

    public boolean getHasVendorsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetVendorsCommand.class);
    }

    public boolean getHasVendorItemAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetVendorItemCommand.class);
    }

    public boolean getHasVendorItemsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetVendorItemsCommand.class);
    }

    public boolean getHasVendorItemCostAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetVendorItemCostCommand.class);
    }

    public boolean getHasVendorItemCostsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetVendorItemCostsCommand.class);
    }

}
