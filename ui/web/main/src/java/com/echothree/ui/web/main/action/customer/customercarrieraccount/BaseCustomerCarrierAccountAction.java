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

package com.echothree.ui.web.main.action.customer.customercarrieraccount;

import com.echothree.control.user.carrier.common.CarrierUtil;
import com.echothree.control.user.carrier.common.result.GetPartyCarrierAccountResult;
import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerResult;
import com.echothree.model.control.carrier.common.transfer.PartyCarrierAccountTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;

public abstract class BaseCustomerCarrierAccountAction<A
        extends ActionForm>
        extends MainBaseAction<A> {

    public static void setupCustomer(HttpServletRequest request, String partyName)
            throws NamingException {
        var commandForm = CustomerUtil.getHome().getGetCustomerForm();

        commandForm.setPartyName(partyName);

        var commandResult = CustomerUtil.getHome().getCustomer(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCustomerResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CUSTOMER, result.getCustomer());
    }

    public void setupCustomer(HttpServletRequest request)
            throws NamingException {
        setupCustomer(request, request.getParameter(ParameterConstants.PARTY_NAME));
    }

    public static PartyCarrierAccountTransfer getPartyCarrierAccountTransfer(HttpServletRequest request, String partyName, String carrierName)
            throws NamingException {
        var commandForm = CarrierUtil.getHome().getGetPartyCarrierAccountForm();

        commandForm.setPartyName(partyName);
        commandForm.setCarrierName(carrierName);

        var commandResult = CarrierUtil.getHome().getPartyCarrierAccount(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPartyCarrierAccountResult)executionResult.getResult();

        return result.getPartyCarrierAccount();
    }

    public static void setupPartyCarrierAccountTransfer(HttpServletRequest request, String partyName, String carrierName)
            throws NamingException {
        request.setAttribute(AttributeConstants.PARTY_CARRIER_ACCOUNT, getPartyCarrierAccountTransfer(request, partyName, carrierName));
    }

}
