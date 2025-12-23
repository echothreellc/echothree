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

package com.echothree.ui.web.main.action.accounting.transaction;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetTransactionResult;
import com.echothree.model.control.accounting.common.AccountingOptions;
import com.echothree.model.control.accounting.common.transfer.TransactionTransfer;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Accounting/Transaction/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/accounting/transaction/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = AccountingUtil.getHome().getGetTransactionForm();
        var transactionName = request.getParameter(ParameterConstants.TRANSACTION_NAME);

        commandForm.setTransactionName(transactionName);
        
        Set<String> commandFormOptions = new HashSet<>();
        commandFormOptions.add(AccountingOptions.TransactionIncludeTransactionGlEntries);
        commandFormOptions.add(AccountingOptions.TransactionIncludeTransactionEntityRoles);
        commandFormOptions.add(AccountingOptions.TransactionIncludeTransactionTimes);
        commandFormOptions.add(CoreOptions.EntityInstanceIncludeNames);
        commandFormOptions.add(PartyOptions.PartyIncludeDescription);
        commandForm.setOptions(commandFormOptions);

        var commandResult = AccountingUtil.getHome().getTransaction(getUserVisitPK(request), commandForm);
        TransactionTransfer transaction = null;
        
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetTransactionResult)executionResult.getResult();
            
            transaction = result.getTransaction();
        }
        
        if(transaction == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.TRANSACTION, transaction);
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
