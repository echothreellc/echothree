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

package com.echothree.ui.web.main.action.accounting.transactionglaccountcategory;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.GetTransactionGlAccountCategoryResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Accounting/TransactionGlAccountCategory/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "TransactionGlAccountCategoryDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/TransactionGlAccountCategory/Description", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/transactionglaccountcategory/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAddAction<DescriptionAddActionForm> {

    @Override
    public void setupParameters(DescriptionAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setTransactionTypeName(findParameter(request, ParameterConstants.TRANSACTION_TYPE_NAME, actionForm.getTransactionTypeName()));
        actionForm.setTransactionGlAccountCategoryName(findParameter(request, ParameterConstants.TRANSACTION_GL_ACCOUNT_CATEGORY_NAME, actionForm.getTransactionGlAccountCategoryName()));
    }
    
    @Override
    public void setupTransfer(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = AccountingUtil.getHome().getGetTransactionGlAccountCategoryForm();

        commandForm.setTransactionTypeName(actionForm.getTransactionTypeName());
        commandForm.setTransactionGlAccountCategoryName(actionForm.getTransactionGlAccountCategoryName());

        var commandResult = AccountingUtil.getHome().getTransactionGlAccountCategory(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetTransactionGlAccountCategoryResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.TRANSACTION_GL_ACCOUNT_CATEGORY, result.getTransactionGlAccountCategory());
        }
    }
    
    @Override
    public CommandResult doAdd(DescriptionAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = AccountingUtil.getHome().getCreateTransactionGlAccountCategoryDescriptionForm();

        commandForm.setTransactionTypeName( actionForm.getTransactionTypeName());
        commandForm.setTransactionGlAccountCategoryName( actionForm.getTransactionGlAccountCategoryName());
        commandForm.setLanguageIsoName(actionForm.getLanguageChoice());
        commandForm.setDescription(actionForm.getDescription());

        return AccountingUtil.getHome().createTransactionGlAccountCategoryDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRANSACTION_TYPE_NAME, actionForm.getTransactionTypeName());
        parameters.put(ParameterConstants.TRANSACTION_GL_ACCOUNT_CATEGORY_NAME, actionForm.getTransactionGlAccountCategoryName());
    }
    
}
