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

package com.echothree.ui.web.main.action.accounting.glaccount;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.result.EditGlAccountResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.EditMode;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Accounting/GlAccount/Edit",
    mappingClass = SecureActionMapping.class,
    name = "GlAccountEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/GlAccount/Main", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/glaccount/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<EditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, EditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var originalGlAccountName = request.getParameter(ParameterConstants.ORIGINAL_GL_ACCOUNT_NAME);
        var commandForm = AccountingUtil.getHome().getEditGlAccountForm();
        var spec = AccountingUtil.getHome().getGlAccountUniversalSpec();
        
        if(originalGlAccountName == null)
            originalGlAccountName = actionForm.getOriginalGlAccountName();
        
        commandForm.setSpec(spec);
        spec.setGlAccountName(originalGlAccountName);
        
        if(wasPost(request)) {
            var edit = AccountingUtil.getHome().getGlAccountEdit();
            
            commandForm.setEditMode(EditMode.UPDATE);
            commandForm.setEdit(edit);
            
            edit.setGlAccountName(actionForm.getGlAccountName());
            edit.setParentGlAccountName(actionForm.getParentGlAccountChoice());
            edit.setGlAccountClassName(actionForm.getGlAccountClassChoice());
            edit.setGlAccountCategoryName(actionForm.getGlAccountCategoryChoice());
            edit.setGlResourceTypeName(actionForm.getGlResourceTypeChoice());
            edit.setIsDefault(actionForm.getIsDefault().toString());
            edit.setDescription(actionForm.getDescription());

            var commandResult = AccountingUtil.getHome().editGlAccount(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    var result = (EditGlAccountResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = AccountingUtil.getHome().editGlAccount(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (EditGlAccountResult)executionResult.getResult();
            
            if(result != null) {
                var edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setOriginalGlAccountName(edit.getGlAccountName());
                    actionForm.setGlAccountName(edit.getGlAccountName());
                    actionForm.setParentGlAccountChoice(edit.getParentGlAccountName());
                    actionForm.setGlAccountClassChoice(edit.getGlAccountClassName());
                    actionForm.setGlAccountCategoryChoice(edit.getGlAccountCategoryName());
                    actionForm.setGlResourceTypeChoice(edit.getGlResourceTypeName());
                    actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                    actionForm.setDescription(edit.getDescription());
                }
                
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }
            
            setCommandResultAttribute(request, commandResult);
            
            forwardKey = ForwardConstants.FORM;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}