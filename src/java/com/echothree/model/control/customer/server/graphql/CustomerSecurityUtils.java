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

package com.echothree.model.control.customer.server.graphql;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.server.command.GetCustomerCommand;
import com.echothree.control.user.customer.server.command.GetCustomerTypeCommand;
import com.echothree.control.user.customer.server.command.GetCustomerTypesCommand;
import com.echothree.control.user.customer.server.command.GetCustomersCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.form.BaseForm;
import graphql.schema.DataFetchingEnvironment;
import javax.naming.NamingException;

public final class CustomerSecurityUtils
        extends BaseGraphQl {

    private static class AccountingSecurityUtilsHolder {
        static CustomerSecurityUtils instance = new CustomerSecurityUtils();
    }
    
    public static CustomerSecurityUtils getInstance() {
        return AccountingSecurityUtilsHolder.instance;
    }

    public boolean getHasCustomerTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetCustomerTypeCommand.class);
    }

    public boolean getHasCustomerTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetCustomerTypesCommand.class);
    }

    public boolean getHasCustomerAccess(final DataFetchingEnvironment env, final Party party) {
        var partyDetail = party.getLastDetail();
        BaseForm baseForm;

        // GetCustomerCommand has a security() function that needs the form to be available.
        try {
            var commandForm = CustomerUtil.getHome().getGetCustomerForm();

            commandForm.setPartyName(partyDetail.getPartyName());
            baseForm = commandForm;
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return getGraphQlExecutionContext(env).hasAccess(GetCustomerCommand.class, baseForm);
    }

    public boolean getHasCustomersAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetCustomersCommand.class);
    }

}
