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

package com.echothree.ui.web.main.action.customer.customercarrier;

import com.echothree.control.user.carrier.common.CarrierUtil;
import com.echothree.control.user.carrier.common.form.GetPartyCarrierForm;
import com.echothree.control.user.carrier.common.result.GetPartyCarrierResult;
import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.form.GetCustomerForm;
import com.echothree.control.user.customer.common.result.GetCustomerResult;
import com.echothree.model.control.carrier.common.transfer.PartyCarrierTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;

public abstract class BaseCustomerCarrierAction<A
        extends ActionForm>
        extends MainBaseAction<A> {

    public static void setupCustomer(HttpServletRequest request, String partyName)
            throws NamingException {
        GetCustomerForm commandForm = CustomerUtil.getHome().getGetCustomerForm();

        commandForm.setPartyName(partyName);

        CommandResult commandResult = CustomerUtil.getHome().getCustomer(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetCustomerResult result = (GetCustomerResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CUSTOMER, result.getCustomer());
    }

    public void setupCustomer(HttpServletRequest request)
            throws NamingException {
        setupCustomer(request, request.getParameter(ParameterConstants.PARTY_NAME));
    }

    public static PartyCarrierTransfer getPartyCarrierTransfer(HttpServletRequest request, String partyName, String carrierName)
            throws NamingException {
        GetPartyCarrierForm commandForm = CarrierUtil.getHome().getGetPartyCarrierForm();

        commandForm.setPartyName(partyName);
        commandForm.setCarrierName(carrierName);

        CommandResult commandResult = CarrierUtil.getHome().getPartyCarrier(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetPartyCarrierResult result = (GetPartyCarrierResult)executionResult.getResult();

        return result.getPartyCarrier();
    }

    public static void setupPartyCarrierTransfer(HttpServletRequest request, String partyName, String carrierName)
            throws NamingException {
        request.setAttribute(AttributeConstants.PARTY_CARRIER, getPartyCarrierTransfer(request, partyName, carrierName));
    }

}
