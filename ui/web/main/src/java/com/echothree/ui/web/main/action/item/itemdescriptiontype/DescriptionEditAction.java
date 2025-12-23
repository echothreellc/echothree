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

package com.echothree.ui.web.main.action.item.itemdescriptiontype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemDescriptionTypeDescriptionEdit;
import com.echothree.control.user.item.common.form.EditItemDescriptionTypeDescriptionForm;
import com.echothree.control.user.item.common.result.EditItemDescriptionTypeDescriptionResult;
import com.echothree.control.user.item.common.spec.ItemDescriptionTypeDescriptionSpec;
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
    path = "/Item/ItemDescriptionType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ItemDescriptionTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemDescriptionType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemdescriptiontype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ItemDescriptionTypeDescriptionSpec, ItemDescriptionTypeDescriptionEdit, EditItemDescriptionTypeDescriptionForm, EditItemDescriptionTypeDescriptionResult> {
    
    @Override
    protected ItemDescriptionTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemDescriptionTypeDescriptionSpec();
        
        spec.setItemDescriptionTypeName(findParameter(request, ParameterConstants.ITEM_DESCRIPTION_TYPE_NAME, actionForm.getItemDescriptionTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ItemDescriptionTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemDescriptionTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditItemDescriptionTypeDescriptionForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemDescriptionTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditItemDescriptionTypeDescriptionResult result, ItemDescriptionTypeDescriptionSpec spec, ItemDescriptionTypeDescriptionEdit edit) {
        actionForm.setItemDescriptionTypeName(spec.getItemDescriptionTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemDescriptionTypeDescriptionForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemDescriptionTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_DESCRIPTION_TYPE_NAME, actionForm.getItemDescriptionTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditItemDescriptionTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.ITEM_DESCRIPTION_TYPE_DESCRIPTION, result.getItemDescriptionTypeDescription());
    }

}
