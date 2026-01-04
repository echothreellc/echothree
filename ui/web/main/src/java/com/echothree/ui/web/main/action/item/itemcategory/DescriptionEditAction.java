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

package com.echothree.ui.web.main.action.item.itemcategory;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemCategoryDescriptionEdit;
import com.echothree.control.user.item.common.form.EditItemCategoryDescriptionForm;
import com.echothree.control.user.item.common.result.EditItemCategoryDescriptionResult;
import com.echothree.control.user.item.common.spec.ItemCategoryDescriptionSpec;
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
    path = "/Item/ItemCategory/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ItemCategoryDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemCategory/Description", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemcategory/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ItemCategoryDescriptionSpec, ItemCategoryDescriptionEdit, EditItemCategoryDescriptionForm, EditItemCategoryDescriptionResult> {
    
    @Override
    protected ItemCategoryDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemCategoryDescriptionSpec();
        
        spec.setItemCategoryName(findParameter(request, ParameterConstants.ITEM_CATEGORY_NAME, actionForm.getItemCategoryName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ItemCategoryDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemCategoryDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditItemCategoryDescriptionForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemCategoryDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditItemCategoryDescriptionResult result, ItemCategoryDescriptionSpec spec, ItemCategoryDescriptionEdit edit) {
        actionForm.setItemCategoryName(spec.getItemCategoryName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemCategoryDescriptionForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemCategoryDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_CATEGORY_NAME, actionForm.getItemCategoryName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditItemCategoryDescriptionResult result) {
        request.setAttribute(AttributeConstants.ITEM_CATEGORY_DESCRIPTION, result.getItemCategoryDescription());
    }

}
