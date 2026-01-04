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

package com.echothree.ui.web.main.action.item.itemimagetype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemImageTypeEdit;
import com.echothree.control.user.item.common.form.EditItemImageTypeForm;
import com.echothree.control.user.item.common.result.EditItemImageTypeResult;
import com.echothree.control.user.item.common.spec.ItemImageTypeUniversalSpec;
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
    path = "/Item/ItemImageType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemImageTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemImageType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemimagetype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemImageTypeUniversalSpec, ItemImageTypeEdit, EditItemImageTypeForm, EditItemImageTypeResult> {
    
    @Override
    protected ItemImageTypeUniversalSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemImageTypeUniversalSpec();
        
        spec.setItemImageTypeName(findParameter(request, ParameterConstants.ORIGINAL_ITEM_IMAGE_TYPE_NAME, actionForm.getOriginalItemImageTypeName()));
        
        return spec;
    }
    
    @Override
    protected ItemImageTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemImageTypeEdit();

        edit.setItemImageTypeName(actionForm.getItemImageTypeName());
        edit.setPreferredMimeTypeName(actionForm.getPreferredMimeTypeChoice());
        edit.setQuality(actionForm.getQuality());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditItemImageTypeForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemImageTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemImageTypeResult result, ItemImageTypeUniversalSpec spec, ItemImageTypeEdit edit) {
        actionForm.setOriginalItemImageTypeName(spec.getItemImageTypeName());
        actionForm.setItemImageTypeName(edit.getItemImageTypeName());
        actionForm.setPreferredMimeTypeChoice(edit.getPreferredMimeTypeName());
        actionForm.setQuality(edit.getQuality());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemImageTypeForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemImageType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemImageTypeResult result) {
        request.setAttribute(AttributeConstants.ITEM_IMAGE_TYPE, result.getItemImageType());
    }

}
