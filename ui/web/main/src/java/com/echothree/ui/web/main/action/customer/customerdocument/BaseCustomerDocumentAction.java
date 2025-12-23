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

package com.echothree.ui.web.main.action.customer.customerdocument;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.result.GetCustomerResult;
import com.echothree.control.user.document.common.DocumentUtil;
import com.echothree.control.user.document.common.result.GetPartyDocumentResult;
import com.echothree.model.control.customer.common.transfer.CustomerTransfer;
import com.echothree.model.control.document.common.transfer.PartyDocumentTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;

public abstract class BaseCustomerDocumentAction<A
        extends ActionForm>
        extends MainBaseAction<A> {

    public static CustomerTransfer setupCustomer(HttpServletRequest request, String partyName)
            throws NamingException {
        var commandForm = CustomerUtil.getHome().getGetCustomerForm();
        CustomerTransfer customer = null;

        commandForm.setPartyName(partyName);

        var commandResult = CustomerUtil.getHome().getCustomer(getUserVisitPK(request), commandForm);

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCustomerResult)executionResult.getResult();

            customer = result.getCustomer();

            if(customer != null) {
                request.setAttribute(AttributeConstants.CUSTOMER, customer);
            }
        }

        return customer;
    }

    public static CustomerTransfer setupCustomer(HttpServletRequest request)
            throws NamingException {
        return setupCustomer(request, request.getParameter(ParameterConstants.PARTY_NAME));
    }

    public static PartyDocumentTransfer setupPartyDocumentTransfer(HttpServletRequest request, String documentName, Set<String> options)
            throws NamingException {
        var commandForm = DocumentUtil.getHome().getGetPartyDocumentForm();
        PartyDocumentTransfer partyDocument = null;

        commandForm.setDocumentName(documentName);

        if(options != null) {
            commandForm.setOptions(options);
        }

        var commandResult = DocumentUtil.getHome().getPartyDocument(getUserVisitPK(request), commandForm);

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPartyDocumentResult)executionResult.getResult();

            partyDocument = result.getPartyDocument();

            if(partyDocument != null) {
                request.setAttribute(AttributeConstants.PARTY_DOCUMENT, partyDocument);
            }
        }

        return partyDocument;
    }

    public static PartyDocumentTransfer setupPartyDocumentTransfer(HttpServletRequest request, Set<String> options)
            throws NamingException {
        return setupPartyDocumentTransfer(request, request.getParameter(ParameterConstants.DOCUMENT_NAME), options);
    }

}
