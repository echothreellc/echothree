// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.control.user.vendor.common.edit.ItemPurchasingCategoryDescriptionEdit;
import com.echothree.control.user.vendor.common.form.EditItemPurchasingCategoryDescriptionForm;
import com.echothree.control.user.vendor.common.result.EditItemPurchasingCategoryDescriptionResult;
import com.echothree.control.user.vendor.common.spec.ItemPurchasingCategoryDescriptionSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Purchasing/ItemPurchasingCategory/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ItemPurchasingCategoryDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Purchasing/ItemPurchasingCategory/Description", redirect = true),
        @SproutForward(name = "Form", path = "/purchasing/itempurchasingcategory/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        String itemPurchasingCategoryName = request.getParameter(ParameterConstants.ITEM_PURCHASING_CATEGORY_NAME);
        String languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                DescriptionEditActionForm actionForm = (DescriptionEditActionForm)form;
                EditItemPurchasingCategoryDescriptionForm commandForm = VendorUtil.getHome().getEditItemPurchasingCategoryDescriptionForm();
                ItemPurchasingCategoryDescriptionSpec spec = VendorUtil.getHome().getItemPurchasingCategoryDescriptionSpec();
                
                if(itemPurchasingCategoryName == null)
                    itemPurchasingCategoryName = actionForm.getItemPurchasingCategoryName();
                if(languageIsoName == null)
                    languageIsoName = actionForm.getLanguageIsoName();
                
                commandForm.setSpec(spec);
                spec.setItemPurchasingCategoryName(itemPurchasingCategoryName);
                spec.setLanguageIsoName(languageIsoName);
                
                if(wasPost(request)) {
                    ItemPurchasingCategoryDescriptionEdit edit = VendorUtil.getHome().getItemPurchasingCategoryDescriptionEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setDescription(actionForm.getDescription());
                    
                    CommandResult commandResult = VendorUtil.getHome().editItemPurchasingCategoryDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditItemPurchasingCategoryDescriptionResult result = (EditItemPurchasingCategoryDescriptionResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = VendorUtil.getHome().editItemPurchasingCategoryDescription(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditItemPurchasingCategoryDescriptionResult result = (EditItemPurchasingCategoryDescriptionResult)executionResult.getResult();
                    
                    if(result != null) {
                        ItemPurchasingCategoryDescriptionEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setItemPurchasingCategoryName(itemPurchasingCategoryName);
                            actionForm.setLanguageIsoName(languageIsoName);
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
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.ITEM_PURCHASING_CATEGORY_NAME, itemPurchasingCategoryName);
            request.setAttribute(AttributeConstants.LANGUAGE_ISO_NAME, languageIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.ITEM_PURCHASING_CATEGORY_NAME, itemPurchasingCategoryName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}