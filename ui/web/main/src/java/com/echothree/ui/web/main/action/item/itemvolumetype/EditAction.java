// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.item.itemvolumetype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemVolumeTypeEdit;
import com.echothree.control.user.item.common.form.EditItemVolumeTypeForm;
import com.echothree.control.user.item.common.result.EditItemVolumeTypeResult;
import com.echothree.control.user.item.common.spec.ItemVolumeTypeSpec;
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
    path = "/Item/ItemVolumeType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemVolumeTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemVolumeType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemvolumetype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemVolumeTypeSpec, ItemVolumeTypeEdit, EditItemVolumeTypeForm, EditItemVolumeTypeResult> {
    
    @Override
    protected ItemVolumeTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemVolumeTypeUniversalSpec();
        
        spec.setItemVolumeTypeName(findParameter(request, ParameterConstants.ORIGINAL_ITEM_VOLUME_TYPE_NAME, actionForm.getOriginalItemVolumeTypeName()));
        
        return spec;
    }
    
    @Override
    protected ItemVolumeTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemVolumeTypeEdit();

        edit.setItemVolumeTypeName(actionForm.getItemVolumeTypeName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditItemVolumeTypeForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemVolumeTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemVolumeTypeResult result, ItemVolumeTypeSpec spec, ItemVolumeTypeEdit edit) {
        actionForm.setOriginalItemVolumeTypeName(spec.getItemVolumeTypeName());
        actionForm.setItemVolumeTypeName(edit.getItemVolumeTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemVolumeTypeForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemVolumeType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemVolumeTypeResult result) {
        request.setAttribute(AttributeConstants.ITEM_VOLUME_TYPE, result.getItemVolumeType());
    }

}
