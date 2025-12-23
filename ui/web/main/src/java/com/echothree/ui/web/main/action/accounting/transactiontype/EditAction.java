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
import com.echothree.control.user.accounting.common.edit.TransactionTypeEdit;
import com.echothree.control.user.accounting.common.form.EditTransactionTypeForm;
import com.echothree.control.user.accounting.common.result.EditTransactionTypeResult;
import com.echothree.control.user.accounting.common.spec.TransactionTypeSpec;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Accounting/TransactionType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "TransactionTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/TransactionType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/transactiontype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, TransactionTypeSpec, TransactionTypeEdit, EditTransactionTypeForm, EditTransactionTypeResult> {
    
    @Override
    protected TransactionTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = AccountingUtil.getHome().getTransactionTypeSpec();
        var originalTransactionTypeName = request.getParameter(ParameterConstants.ORIGINAL_TRANSACTION_TYPE_NAME);

        if(originalTransactionTypeName == null) {
            originalTransactionTypeName = actionForm.getOriginalTransactionTypeName();
        }

        spec.setTransactionTypeName(originalTransactionTypeName);
        
        return spec;
    }
    
    @Override
    protected TransactionTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = AccountingUtil.getHome().getTransactionTypeEdit();

        edit.setTransactionTypeName(actionForm.getTransactionTypeName());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditTransactionTypeForm getForm()
            throws NamingException {
        return AccountingUtil.getHome().getEditTransactionTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditTransactionTypeResult result, TransactionTypeSpec spec, TransactionTypeEdit edit) {
        actionForm.setOriginalTransactionTypeName(spec.getTransactionTypeName());
        actionForm.setTransactionTypeName(edit.getTransactionTypeName());
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditTransactionTypeForm commandForm)
            throws Exception {
        return AccountingUtil.getHome().editTransactionType(getUserVisitPK(request), commandForm);
    }
    
}