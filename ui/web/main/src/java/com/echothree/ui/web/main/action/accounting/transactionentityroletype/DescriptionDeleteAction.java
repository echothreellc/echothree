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
import com.echothree.control.user.accounting.common.result.GetTransactionEntityRoleTypeDescriptionResult;
import com.echothree.control.user.accounting.common.spec.TransactionEntityRoleTypeDescriptionSpec;
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
    path = "/Accounting/TransactionEntityRoleType/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "TransactionEntityRoleTypeDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/TransactionEntityRoleType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/transactionentityroletype/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {

    @Override
    public String getEntityTypeName(final DescriptionDeleteActionForm actionForm) {
        return EntityTypes.TransactionEntityRoleTypeDescription.name();
    }
    
    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setTransactionTypeName(findParameter(request, ParameterConstants.TRANSACTION_TYPE_NAME, actionForm.getTransactionTypeName()));
        actionForm.setTransactionEntityRoleTypeName(findParameter(request, ParameterConstants.TRANSACTION_ENTITY_ROLE_TYPE_NAME, actionForm.getTransactionEntityRoleTypeName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }
    
    private void setupSpec(DescriptionDeleteActionForm actionForm, TransactionEntityRoleTypeDescriptionSpec spec) {
        spec.setTransactionTypeName(actionForm.getTransactionTypeName());
        spec.setTransactionEntityRoleTypeName(actionForm.getTransactionEntityRoleTypeName());
        spec.setLanguageIsoName(actionForm.getLanguageIsoName());
    }
    
    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = AccountingUtil.getHome().getGetTransactionEntityRoleTypeDescriptionForm();
        
        setupSpec(actionForm, commandForm);

        var commandResult = AccountingUtil.getHome().getTransactionEntityRoleTypeDescription(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetTransactionEntityRoleTypeDescriptionResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.TRANSACTION_ENTITY_ROLE_TYPE_DESCRIPTION, result.getTransactionEntityRoleTypeDescription());
    }
    
    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = AccountingUtil.getHome().getDeleteTransactionEntityRoleTypeDescriptionForm();

        setupSpec(actionForm, commandForm);

        return AccountingUtil.getHome().deleteTransactionEntityRoleTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.TRANSACTION_TYPE_NAME, actionForm.getTransactionTypeName());
        parameters.put(ParameterConstants.TRANSACTION_ENTITY_ROLE_TYPE_NAME, actionForm.getTransactionEntityRoleTypeName());
    }
    
}
