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

package com.echothree.ui.web.main.action.item.itemdescriptiontypeusetype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemDescriptionTypeUseTypeEdit;
import com.echothree.control.user.item.common.form.EditItemDescriptionTypeUseTypeForm;
import com.echothree.control.user.item.common.result.EditItemDescriptionTypeUseTypeResult;
import com.echothree.control.user.item.common.spec.ItemDescriptionTypeUseTypeSpec;
import com.echothree.control.user.item.common.spec.ItemDescriptionTypeUseTypeUniversalSpec;
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
    path = "/Item/ItemDescriptionTypeUseType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemDescriptionTypeUseTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemDescriptionTypeUseType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemdescriptiontypeusetype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemDescriptionTypeUseTypeSpec, ItemDescriptionTypeUseTypeEdit, EditItemDescriptionTypeUseTypeForm, EditItemDescriptionTypeUseTypeResult> {
    
    @Override
    protected ItemDescriptionTypeUseTypeUniversalSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemDescriptionTypeUseTypeUniversalSpec();
        
        spec.setItemDescriptionTypeUseTypeName(findParameter(request, ParameterConstants.ORIGINAL_ITEM_DESCRIPTION_TYPE_USE_TYPE_NAME, actionForm.getOriginalItemDescriptionTypeUseTypeName()));
        
        return spec;
    }
    
    @Override
    protected ItemDescriptionTypeUseTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemDescriptionTypeUseTypeEdit();

        edit.setItemDescriptionTypeUseTypeName(actionForm.getItemDescriptionTypeUseTypeName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditItemDescriptionTypeUseTypeForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemDescriptionTypeUseTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemDescriptionTypeUseTypeResult result, ItemDescriptionTypeUseTypeSpec spec, ItemDescriptionTypeUseTypeEdit edit) {
        actionForm.setOriginalItemDescriptionTypeUseTypeName(spec.getItemDescriptionTypeUseTypeName());
        actionForm.setItemDescriptionTypeUseTypeName(edit.getItemDescriptionTypeUseTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemDescriptionTypeUseTypeForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemDescriptionTypeUseType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemDescriptionTypeUseTypeResult result) {
        request.setAttribute(AttributeConstants.ITEM_DESCRIPTION_TYPE_USE_TYPE, result.getItemDescriptionTypeUseType());
    }

}
