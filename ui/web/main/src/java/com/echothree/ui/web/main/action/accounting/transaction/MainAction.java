// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.control.user.accounting.common.result.GetTransactionsResult;
import com.echothree.model.control.accounting.common.AccountingOptions;
import com.echothree.model.control.accounting.common.transfer.TransactionGroupTransfer;
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
    path = "/Accounting/Transaction/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/accounting/transaction/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        var commandForm = AccountingUtil.getHome().getGetTransactionsForm();
        var transactionGroupName = request.getParameter(ParameterConstants.TRANSACTION_GROUP_NAME);

        commandForm.setTransactionGroupName(transactionGroupName);
        
        Set<String> commandFormOptions = new HashSet<>();
        commandFormOptions.add(PartyOptions.PartyIncludeDescription);
        commandFormOptions.add(AccountingOptions.TransactionIncludeTransactionTimes);
        commandForm.setOptions(commandFormOptions);

        var commandResult = AccountingUtil.getHome().getTransactions(getUserVisitPK(request), commandForm);
        GetTransactionsResult result = null;
        TransactionGroupTransfer transactionGroup = null;
        
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            
            result = (GetTransactionsResult)executionResult.getResult();
            transactionGroup = result.getTransactionGroup();
        }
        
        if(transactionGroup == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.TRANSACTION_GROUP, transactionGroup);
            request.setAttribute(AttributeConstants.TRANSACTIONS, result.getTransactions());
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
