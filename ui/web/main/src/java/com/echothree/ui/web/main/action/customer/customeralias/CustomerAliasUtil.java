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

package com.echothree.ui.web.main.action.customer.customeralias;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetPartyAliasResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

public class CustomerAliasUtil {

    private CustomerAliasUtil() {
        super();
    }

    private static class CustomerAliasUtilHolder {
        static CustomerAliasUtil instance = new CustomerAliasUtil();
    }

    public static CustomerAliasUtil getInstance() {
        return CustomerAliasUtilHolder.instance;
    }

    public void setupCustomer(HttpServletRequest request, String partyName)
            throws NamingException {
        var commandForm = CustomerUtil.getHome().getGetCustomerForm();

        commandForm.setPartyName(partyName);

        var commandResult = CustomerUtil.getHome().getCustomer(MainBaseAction.getUserVisitPK(request), commandForm);

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCustomerResult)executionResult.getResult();
            var customer = result.getCustomer();

            if(customer != null) {
                request.setAttribute(AttributeConstants.CUSTOMER, customer);
            }
        }
    }

    public void setupCustomer(HttpServletRequest request)
            throws NamingException {
        setupCustomer(request, request.getParameter(ParameterConstants.PARTY_NAME));
    }

    public void setupPartyAliasTransfer(HttpServletRequest request, String partyName, String partyAliasTypeName)
            throws NamingException {
        var commandForm = PartyUtil.getHome().getGetPartyAliasForm();

        commandForm.setPartyName(partyName);
        commandForm.setPartyAliasTypeName(partyAliasTypeName);

        var commandResult = PartyUtil.getHome().getPartyAlias(MainBaseAction.getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPartyAliasResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.PARTY_ALIAS, result.getPartyAlias());
    }

}
