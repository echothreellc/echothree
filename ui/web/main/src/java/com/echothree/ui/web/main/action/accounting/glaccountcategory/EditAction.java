// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.web.main.action.accounting.glaccountcategory;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.common.edit.GlAccountCategoryEdit;
import com.echothree.control.user.accounting.common.form.EditGlAccountCategoryForm;
import com.echothree.control.user.accounting.common.result.EditGlAccountCategoryResult;
import com.echothree.control.user.accounting.common.spec.GlAccountCategorySpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Accounting/GlAccountCategory/Edit",
    mappingClass = SecureActionMapping.class,
    name = "GlAccountCategoryEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/GlAccountCategory/Main", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/glaccountcategory/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String originalGlAccountCategoryName = request.getParameter(ParameterConstants.ORIGINAL_GL_ACCOUNT_CATEGORY_NAME);
        
        try {
            if(forwardKey == null) {
                EditActionForm actionForm = (EditActionForm)form;
                EditGlAccountCategoryForm commandForm = AccountingUtil.getHome().getEditGlAccountCategoryForm();
                GlAccountCategorySpec spec = AccountingUtil.getHome().getGlAccountCategorySpec();
                
                if(originalGlAccountCategoryName == null)
                    originalGlAccountCategoryName = actionForm.getOriginalGlAccountCategoryName();
                
                commandForm.setSpec(spec);
                spec.setGlAccountCategoryName(originalGlAccountCategoryName);
                
                if(wasPost(request)) {
                    GlAccountCategoryEdit edit = AccountingUtil.getHome().getGlAccountCategoryEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setGlAccountCategoryName(actionForm.getGlAccountCategoryName());
                    edit.setParentGlAccountCategoryName(actionForm.getParentGlAccountCategoryChoice());
                    edit.setIsDefault(actionForm.getIsDefault().toString());
                    edit.setSortOrder(actionForm.getSortOrder());
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = AccountingUtil.getHome().editGlAccountCategory(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditGlAccountCategoryResult result = (EditGlAccountCategoryResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = AccountingUtil.getHome().editGlAccountCategory(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditGlAccountCategoryResult result = (EditGlAccountCategoryResult)executionResult.getResult();
                    
                    if(result != null) {
                        GlAccountCategoryEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setOriginalGlAccountCategoryName(edit.getGlAccountCategoryName());
                            actionForm.setGlAccountCategoryName(edit.getGlAccountCategoryName());
                            actionForm.setParentGlAccountCategoryChoice(edit.getParentGlAccountCategoryName());
                            actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            actionForm.setSortOrder(edit.getSortOrder());
                            actionForm.setDescription(edit.getDescription());
                        }
                        
                        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                    }
                    
                    setCommandResultAttribute(request, commandResult);
                    
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}