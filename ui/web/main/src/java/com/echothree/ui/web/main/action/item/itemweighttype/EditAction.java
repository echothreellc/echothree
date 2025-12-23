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

package com.echothree.ui.web.main.action.item.itemweighttype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemWeightTypeEdit;
import com.echothree.control.user.item.common.form.EditItemWeightTypeForm;
import com.echothree.control.user.item.common.result.EditItemWeightTypeResult;
import com.echothree.control.user.item.common.spec.ItemWeightTypeSpec;
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
    path = "/Item/ItemWeightType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemWeightTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemWeightType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemweighttype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemWeightTypeSpec, ItemWeightTypeEdit, EditItemWeightTypeForm, EditItemWeightTypeResult> {
    
    @Override
    protected ItemWeightTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemWeightTypeUniversalSpec();
        
        spec.setItemWeightTypeName(findParameter(request, ParameterConstants.ORIGINAL_ITEM_WEIGHT_TYPE_NAME, actionForm.getOriginalItemWeightTypeName()));
        
        return spec;
    }
    
    @Override
    protected ItemWeightTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemWeightTypeEdit();

        edit.setItemWeightTypeName(actionForm.getItemWeightTypeName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditItemWeightTypeForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemWeightTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemWeightTypeResult result, ItemWeightTypeSpec spec, ItemWeightTypeEdit edit) {
        actionForm.setOriginalItemWeightTypeName(spec.getItemWeightTypeName());
        actionForm.setItemWeightTypeName(edit.getItemWeightTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemWeightTypeForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemWeightType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemWeightTypeResult result) {
        request.setAttribute(AttributeConstants.ITEM_WEIGHT_TYPE, result.getItemWeightType());
    }

}
