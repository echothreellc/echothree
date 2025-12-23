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
import com.echothree.control.user.accounting.common.edit.TransactionGlAccountCategoryDescriptionEdit;
import com.echothree.control.user.accounting.common.form.EditTransactionGlAccountCategoryDescriptionForm;
import com.echothree.control.user.accounting.common.result.EditTransactionGlAccountCategoryDescriptionResult;
import com.echothree.control.user.accounting.common.result.GetTransactionGlAccountCategoryResult;
import com.echothree.control.user.accounting.common.spec.TransactionGlAccountCategoryDescriptionSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
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
    path = "/Accounting/TransactionGlAccountCategory/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "TransactionGlAccountCategoryDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/TransactionGlAccountCategory/Description", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/transactionglaccountcategory/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, TransactionGlAccountCategoryDescriptionSpec, TransactionGlAccountCategoryDescriptionEdit, EditTransactionGlAccountCategoryDescriptionForm, EditTransactionGlAccountCategoryDescriptionResult> {
    
    @Override
    protected TransactionGlAccountCategoryDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = AccountingUtil.getHome().getTransactionGlAccountCategoryDescriptionSpec();
        
        spec.setTransactionTypeName(findParameter(request, ParameterConstants.TRANSACTION_TYPE_NAME, actionForm.getTransactionTypeName()));
        spec.setTransactionGlAccountCategoryName(findParameter(request, ParameterConstants.TRANSACTION_GL_ACCOUNT_CATEGORY_NAME, actionForm.getTransactionGlAccountCategoryName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected TransactionGlAccountCategoryDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = AccountingUtil.getHome().getTransactionGlAccountCategoryDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditTransactionGlAccountCategoryDescriptionForm getForm()
            throws NamingException {
        return AccountingUtil.getHome().getEditTransactionGlAccountCategoryDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditTransactionGlAccountCategoryDescriptionResult result, TransactionGlAccountCategoryDescriptionSpec spec, TransactionGlAccountCategoryDescriptionEdit edit) {
        actionForm.setTransactionTypeName(spec.getTransactionTypeName());
        actionForm.setTransactionGlAccountCategoryName(spec.getTransactionGlAccountCategoryName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditTransactionGlAccountCategoryDescriptionForm commandForm)
            throws Exception {
        var commandResult = AccountingUtil.getHome().editTransactionGlAccountCategoryDescription(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditTransactionGlAccountCategoryDescriptionResult)executionResult.getResult();

        var transactionGlAccountCategoryDescription = result.getTransactionGlAccountCategoryDescription();
        if(transactionGlAccountCategoryDescription != null) {
            request.setAttribute(AttributeConstants.TRANSACTION_GL_ACCOUNT_CATEGORY, transactionGlAccountCategoryDescription.getTransactionGlAccountCategory());
        }
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRANSACTION_TYPE_NAME, actionForm.getTransactionTypeName());
        parameters.put(ParameterConstants.TRANSACTION_GL_ACCOUNT_CATEGORY_NAME, actionForm.getTransactionGlAccountCategoryName());
    }
    
    @Override
    public void setupTransfer(DescriptionEditActionForm actionForm, HttpServletRequest request)
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
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditTransactionGlAccountCategoryDescriptionResult result) {
        request.setAttribute(AttributeConstants.TRANSACTION_GL_ACCOUNT_CATEGORY_DESCRIPTION, result.getTransactionGlAccountCategoryDescription());
    }

}
