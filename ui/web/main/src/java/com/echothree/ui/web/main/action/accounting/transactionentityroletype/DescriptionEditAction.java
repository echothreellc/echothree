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

package com.echothree.ui.web.main.action.accounting.transactionentityroletype;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.edit.TransactionEntityRoleTypeDescriptionEdit;
import com.echothree.control.user.accounting.common.form.EditTransactionEntityRoleTypeDescriptionForm;
import com.echothree.control.user.accounting.common.result.EditTransactionEntityRoleTypeDescriptionResult;
import com.echothree.control.user.accounting.common.result.GetTransactionEntityRoleTypeResult;
import com.echothree.control.user.accounting.common.spec.TransactionEntityRoleTypeDescriptionSpec;
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
    path = "/Accounting/TransactionEntityRoleType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "TransactionEntityRoleTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/TransactionEntityRoleType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/transactionentityroletype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, TransactionEntityRoleTypeDescriptionSpec, TransactionEntityRoleTypeDescriptionEdit, EditTransactionEntityRoleTypeDescriptionForm, EditTransactionEntityRoleTypeDescriptionResult> {
    
    @Override
    protected TransactionEntityRoleTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = AccountingUtil.getHome().getTransactionEntityRoleTypeDescriptionSpec();
        
        spec.setTransactionTypeName(findParameter(request, ParameterConstants.TRANSACTION_TYPE_NAME, actionForm.getTransactionTypeName()));
        spec.setTransactionEntityRoleTypeName(findParameter(request, ParameterConstants.TRANSACTION_ENTITY_ROLE_TYPE_NAME, actionForm.getTransactionEntityRoleTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected TransactionEntityRoleTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = AccountingUtil.getHome().getTransactionEntityRoleTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditTransactionEntityRoleTypeDescriptionForm getForm()
            throws NamingException {
        return AccountingUtil.getHome().getEditTransactionEntityRoleTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditTransactionEntityRoleTypeDescriptionResult result, TransactionEntityRoleTypeDescriptionSpec spec, TransactionEntityRoleTypeDescriptionEdit edit) {
        actionForm.setTransactionTypeName(spec.getTransactionTypeName());
        actionForm.setTransactionEntityRoleTypeName(spec.getTransactionEntityRoleTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditTransactionEntityRoleTypeDescriptionForm commandForm)
            throws Exception {
        var commandResult = AccountingUtil.getHome().editTransactionEntityRoleTypeDescription(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditTransactionEntityRoleTypeDescriptionResult)executionResult.getResult();

        var transactionEntityRoleTypeDescription = result.getTransactionEntityRoleTypeDescription();
        if(transactionEntityRoleTypeDescription != null) {
            request.setAttribute(AttributeConstants.TRANSACTION_ENTITY_ROLE_TYPE, transactionEntityRoleTypeDescription.getTransactionEntityRoleType());
        }
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRANSACTION_TYPE_NAME, actionForm.getTransactionTypeName());
        parameters.put(ParameterConstants.TRANSACTION_ENTITY_ROLE_TYPE_NAME, actionForm.getTransactionEntityRoleTypeName());
    }
    
    @Override
    public void setupTransfer(DescriptionEditActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = AccountingUtil.getHome().getGetTransactionEntityRoleTypeForm();

        commandForm.setTransactionTypeName(actionForm.getTransactionTypeName());
        commandForm.setTransactionEntityRoleTypeName(actionForm.getTransactionEntityRoleTypeName());

        var commandResult = AccountingUtil.getHome().getTransactionEntityRoleType(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetTransactionEntityRoleTypeResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.TRANSACTION_ENTITY_ROLE_TYPE, result.getTransactionEntityRoleType());
        }
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditTransactionEntityRoleTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.TRANSACTION_ENTITY_ROLE_TYPE_DESCRIPTION, result.getTransactionEntityRoleTypeDescription());
    }

}
