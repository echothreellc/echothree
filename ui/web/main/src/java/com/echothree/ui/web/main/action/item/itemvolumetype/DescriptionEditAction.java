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

package com.echothree.ui.web.main.action.item.itemvolumetype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemVolumeTypeDescriptionEdit;
import com.echothree.control.user.item.common.form.EditItemVolumeTypeDescriptionForm;
import com.echothree.control.user.item.common.result.EditItemVolumeTypeDescriptionResult;
import com.echothree.control.user.item.common.spec.ItemVolumeTypeDescriptionSpec;
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
    path = "/Item/ItemVolumeType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ItemVolumeTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemVolumeType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemvolumetype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ItemVolumeTypeDescriptionSpec, ItemVolumeTypeDescriptionEdit, EditItemVolumeTypeDescriptionForm, EditItemVolumeTypeDescriptionResult> {
    
    @Override
    protected ItemVolumeTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemVolumeTypeDescriptionSpec();
        
        spec.setItemVolumeTypeName(findParameter(request, ParameterConstants.ITEM_VOLUME_TYPE_NAME, actionForm.getItemVolumeTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ItemVolumeTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemVolumeTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditItemVolumeTypeDescriptionForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemVolumeTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditItemVolumeTypeDescriptionResult result, ItemVolumeTypeDescriptionSpec spec, ItemVolumeTypeDescriptionEdit edit) {
        actionForm.setItemVolumeTypeName(spec.getItemVolumeTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemVolumeTypeDescriptionForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemVolumeTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_VOLUME_TYPE_NAME, actionForm.getItemVolumeTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditItemVolumeTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.ITEM_VOLUME_TYPE_DESCRIPTION, result.getItemVolumeTypeDescription());
    }

}
