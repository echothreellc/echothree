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

package com.echothree.ui.web.main.action.customer.customercontactlist;

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.result.GetContactListResult;
import com.echothree.control.user.contactlist.common.result.GetPartyContactListResult;
import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

public class CustomerContactListUtil {

    private CustomerContactListUtil() {
        super();
    }

    private static class CustomerContactListUtilHolder {
        static CustomerContactListUtil instance = new CustomerContactListUtil();
    }

    public static CustomerContactListUtil getInstance() {
        return CustomerContactListUtilHolder.instance;
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

    public void setupContactListTransfer(HttpServletRequest request, String contactListName)
            throws NamingException {
        var commandForm = ContactListUtil.getHome().getGetContactListForm();

        commandForm.setContactListName(contactListName);

        var commandResult = ContactListUtil.getHome().getContactList(MainBaseAction.getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetContactListResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CONTACT_LIST, result.getContactList());
    }

    public void setupPartyContactListTransfer(HttpServletRequest request, String partyName, String contactListName)
            throws NamingException {
        var commandForm = ContactListUtil.getHome().getGetPartyContactListForm();

        commandForm.setPartyName(partyName);
        commandForm.setContactListName(contactListName);

        var commandResult = ContactListUtil.getHome().getPartyContactList(MainBaseAction.getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPartyContactListResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.PARTY_CONTACT_LIST, result.getPartyContactList());
    }

}
