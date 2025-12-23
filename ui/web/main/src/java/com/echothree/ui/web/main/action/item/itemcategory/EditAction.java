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
import com.echothree.control.user.item.common.edit.ItemCategoryEdit;
import com.echothree.control.user.item.common.form.EditItemCategoryForm;
import com.echothree.control.user.item.common.result.EditItemCategoryResult;
import com.echothree.control.user.item.common.spec.ItemCategoryUniversalSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
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
    path = "/Item/ItemCategory/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemCategoryEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemCategory/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemcategory/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemCategoryUniversalSpec, ItemCategoryEdit, EditItemCategoryForm, EditItemCategoryResult> {
    
    @Override
    protected ItemCategoryUniversalSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemCategoryUniversalSpec();
        
        spec.setItemCategoryName(findParameter(request, ParameterConstants.ORIGINAL_ITEM_CATEGORY_NAME, actionForm.getOriginalItemCategoryName()));
        
        return spec;
    }
    
    @Override
    protected ItemCategoryEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemCategoryEdit();

        edit.setItemCategoryName(actionForm.getItemCategoryName());
        edit.setParentItemCategoryName(actionForm.getParentItemCategoryChoice());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditItemCategoryForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemCategoryForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemCategoryResult result, ItemCategoryUniversalSpec spec, ItemCategoryEdit edit) {
        actionForm.setOriginalItemCategoryName(spec.getItemCategoryName());
        actionForm.setItemCategoryName(edit.getItemCategoryName());
        actionForm.setParentItemCategoryChoice(edit.getParentItemCategoryName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemCategoryForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemCategory(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemCategoryResult result) {
        request.setAttribute(AttributeConstants.ITEM_CATEGORY, result.getItemCategory());
    }

}
