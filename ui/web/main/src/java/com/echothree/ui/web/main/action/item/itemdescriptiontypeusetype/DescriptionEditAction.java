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
import com.echothree.control.user.item.common.edit.ItemDescriptionTypeUseTypeDescriptionEdit;
import com.echothree.control.user.item.common.form.EditItemDescriptionTypeUseTypeDescriptionForm;
import com.echothree.control.user.item.common.result.EditItemDescriptionTypeUseTypeDescriptionResult;
import com.echothree.control.user.item.common.spec.ItemDescriptionTypeUseTypeDescriptionSpec;
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
    path = "/Item/ItemDescriptionTypeUseType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ItemDescriptionTypeUseTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemDescriptionTypeUseType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemdescriptiontypeusetype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ItemDescriptionTypeUseTypeDescriptionSpec, ItemDescriptionTypeUseTypeDescriptionEdit, EditItemDescriptionTypeUseTypeDescriptionForm, EditItemDescriptionTypeUseTypeDescriptionResult> {
    
    @Override
    protected ItemDescriptionTypeUseTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemDescriptionTypeUseTypeDescriptionSpec();
        
        spec.setItemDescriptionTypeUseTypeName(findParameter(request, ParameterConstants.ITEM_DESCRIPTION_TYPE_USE_TYPE_NAME, actionForm.getItemDescriptionTypeUseTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ItemDescriptionTypeUseTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemDescriptionTypeUseTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditItemDescriptionTypeUseTypeDescriptionForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemDescriptionTypeUseTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditItemDescriptionTypeUseTypeDescriptionResult result, ItemDescriptionTypeUseTypeDescriptionSpec spec, ItemDescriptionTypeUseTypeDescriptionEdit edit) {
        actionForm.setItemDescriptionTypeUseTypeName(spec.getItemDescriptionTypeUseTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemDescriptionTypeUseTypeDescriptionForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemDescriptionTypeUseTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_DESCRIPTION_TYPE_USE_TYPE_NAME, actionForm.getItemDescriptionTypeUseTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditItemDescriptionTypeUseTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.ITEM_DESCRIPTION_TYPE_USE_TYPE_DESCRIPTION, result.getItemDescriptionTypeUseTypeDescription());
    }

}
