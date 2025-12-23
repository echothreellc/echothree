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

package com.echothree.ui.web.main.action.purchasing.itempurchasingcategory;

import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.result.EditItemPurchasingCategoryResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.EditMode;
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
    path = "/Purchasing/ItemPurchasingCategory/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemPurchasingCategoryEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/ItemPurchasingCategory/Main", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/itempurchasingcategory/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var originalItemPurchasingCategoryName = request.getParameter(ParameterConstants.ORIGINAL_ITEM_PURCHASING_CATEGORY_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (EditActionForm)form;
                var commandForm = VendorUtil.getHome().getEditItemPurchasingCategoryForm();
                var spec = VendorUtil.getHome().getItemPurchasingCategoryUniversalSpec();
                
                if(originalItemPurchasingCategoryName == null)
                    originalItemPurchasingCategoryName = actionForm.getOriginalItemPurchasingCategoryName();
                
                commandForm.setSpec(spec);
                spec.setItemPurchasingCategoryName(originalItemPurchasingCategoryName);
                
                if(wasPost(request)) {
                    var edit = VendorUtil.getHome().getItemPurchasingCategoryEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setItemPurchasingCategoryName(actionForm.getItemPurchasingCategoryName());
                    edit.setParentItemPurchasingCategoryName(actionForm.getParentItemPurchasingCategoryChoice());
                    edit.setIsDefault(actionForm.getIsDefault().toString());
                    edit.setSortOrder(actionForm.getSortOrder());
                    edit.setDescription(actionForm.getDescription());

                    var commandResult = VendorUtil.getHome().editItemPurchasingCategory(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            var result = (EditItemPurchasingCategoryResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = VendorUtil.getHome().editItemPurchasingCategory(getUserVisitPK(request), commandForm);
                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditItemPurchasingCategoryResult)executionResult.getResult();
                    
                    if(result != null) {
                        var edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setOriginalItemPurchasingCategoryName(edit.getItemPurchasingCategoryName());
                            actionForm.setItemPurchasingCategoryName(edit.getItemPurchasingCategoryName());
                            actionForm.setParentItemPurchasingCategoryChoice(edit.getParentItemPurchasingCategoryName());
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