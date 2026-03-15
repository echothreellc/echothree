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
import com.echothree.control.user.accounting.common.result.GetTransactionGlAccountCategoryDescriptionResult;
import com.echothree.control.user.accounting.common.spec.TransactionGlAccountCategoryDescriptionSpec;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/Accounting/TransactionGlAccountCategory/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "TransactionGlAccountCategoryDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/TransactionGlAccountCategory/Description", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/transactionglaccountcategory/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName(final DescriptionDeleteActionForm actionForm) {
        return EntityTypes.TransactionGlAccountCategoryDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setTransactionTypeName(findParameter(request, ParameterConstants.TRANSACTION_TYPE_NAME, actionForm.getTransactionTypeName()));
        actionForm.setTransactionGlAccountCategoryName(findParameter(request, ParameterConstants.TRANSACTION_GL_ACCOUNT_CATEGORY_NAME, actionForm.getTransactionGlAccountCategoryName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    private void setupSpec(DescriptionDeleteActionForm actionForm, TransactionGlAccountCategoryDescriptionSpec spec) {
        spec.setTransactionTypeName(actionForm.getTransactionTypeName());
        spec.setTransactionGlAccountCategoryName(actionForm.getTransactionGlAccountCategoryName());
        spec.setLanguageIsoName(actionForm.getLanguageIsoName());
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = AccountingUtil.getHome().getGetTransactionGlAccountCategoryDescriptionForm();
        
        setupSpec(actionForm, commandForm);

        var commandResult = AccountingUtil.getHome().getTransactionGlAccountCategoryDescription(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetTransactionGlAccountCategoryDescriptionResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.TRANSACTION_GL_ACCOUNT_CATEGORY_DESCRIPTION, result.getTransactionGlAccountCategoryDescription());
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = AccountingUtil.getHome().getDeleteTransactionGlAccountCategoryDescriptionForm();

        setupSpec(actionForm, commandForm);

        return AccountingUtil.getHome().deleteTransactionGlAccountCategoryDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRANSACTION_TYPE_NAME, actionForm.getTransactionTypeName());
        parameters.put(ParameterConstants.TRANSACTION_GL_ACCOUNT_CATEGORY_NAME, actionForm.getTransactionGlAccountCategoryName());
    }
    
}
