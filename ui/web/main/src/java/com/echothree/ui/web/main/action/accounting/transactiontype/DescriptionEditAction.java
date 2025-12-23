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

package com.echothree.ui.web.main.action.accounting.transactiontype;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.edit.TransactionTypeDescriptionEdit;
import com.echothree.control.user.accounting.common.form.EditTransactionTypeDescriptionForm;
import com.echothree.control.user.accounting.common.result.EditTransactionTypeDescriptionResult;
import com.echothree.control.user.accounting.common.result.GetTransactionTypeResult;
import com.echothree.control.user.accounting.common.spec.TransactionTypeDescriptionSpec;
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
    path = "/Accounting/TransactionType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "TransactionTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/TransactionType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/transactiontype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, TransactionTypeDescriptionSpec, TransactionTypeDescriptionEdit, EditTransactionTypeDescriptionForm, EditTransactionTypeDescriptionResult> {
    
    @Override
    protected TransactionTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = AccountingUtil.getHome().getTransactionTypeDescriptionSpec();
        
        spec.setTransactionTypeName(findParameter(request, ParameterConstants.TRANSACTION_TYPE_NAME, actionForm.getTransactionTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected TransactionTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = AccountingUtil.getHome().getTransactionTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditTransactionTypeDescriptionForm getForm()
            throws NamingException {
        return AccountingUtil.getHome().getEditTransactionTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditTransactionTypeDescriptionResult result, TransactionTypeDescriptionSpec spec, TransactionTypeDescriptionEdit edit) {
        actionForm.setTransactionTypeName(spec.getTransactionTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditTransactionTypeDescriptionForm commandForm)
            throws Exception {
        var commandResult = AccountingUtil.getHome().editTransactionTypeDescription(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditTransactionTypeDescriptionResult)executionResult.getResult();

        var transactionTypeDescription = result.getTransactionTypeDescription();
        if(transactionTypeDescription != null) {
            request.setAttribute(AttributeConstants.TRANSACTION_TYPE, transactionTypeDescription.getTransactionType());
        }
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRANSACTION_TYPE_NAME, actionForm.getTransactionTypeName());
    }
    
    @Override
    public void setupTransfer(DescriptionEditActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = AccountingUtil.getHome().getGetTransactionTypeForm();

        commandForm.setTransactionTypeName(actionForm.getTransactionTypeName());

        var commandResult = AccountingUtil.getHome().getTransactionType(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetTransactionTypeResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.TRANSACTION_TYPE, result.getTransactionType());
        }
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditTransactionTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.TRANSACTION_TYPE_DESCRIPTION, result.getTransactionTypeDescription());
    }

}
