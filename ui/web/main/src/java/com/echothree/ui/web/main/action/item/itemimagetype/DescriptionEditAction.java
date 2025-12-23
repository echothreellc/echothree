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
import com.echothree.control.user.item.common.edit.ItemImageTypeDescriptionEdit;
import com.echothree.control.user.item.common.form.EditItemImageTypeDescriptionForm;
import com.echothree.control.user.item.common.result.EditItemImageTypeDescriptionResult;
import com.echothree.control.user.item.common.spec.ItemImageTypeDescriptionSpec;
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
    path = "/Item/ItemImageType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ItemImageTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemImageType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemimagetype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ItemImageTypeDescriptionSpec, ItemImageTypeDescriptionEdit, EditItemImageTypeDescriptionForm, EditItemImageTypeDescriptionResult> {
    
    @Override
    protected ItemImageTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemImageTypeDescriptionSpec();
        
        spec.setItemImageTypeName(findParameter(request, ParameterConstants.ITEM_IMAGE_TYPE_NAME, actionForm.getItemImageTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ItemImageTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemImageTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditItemImageTypeDescriptionForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemImageTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditItemImageTypeDescriptionResult result, ItemImageTypeDescriptionSpec spec, ItemImageTypeDescriptionEdit edit) {
        actionForm.setItemImageTypeName(spec.getItemImageTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemImageTypeDescriptionForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemImageTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_IMAGE_TYPE_NAME, actionForm.getItemImageTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditItemImageTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.ITEM_IMAGE_TYPE_DESCRIPTION, result.getItemImageTypeDescription());
    }

}
