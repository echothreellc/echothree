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

package com.echothree.ui.web.main.action.item.itemaliastype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemAliasTypeDescriptionEdit;
import com.echothree.control.user.item.common.form.EditItemAliasTypeDescriptionForm;
import com.echothree.control.user.item.common.result.EditItemAliasTypeDescriptionResult;
import com.echothree.control.user.item.common.spec.ItemAliasTypeDescriptionSpec;
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
    path = "/Item/ItemAliasType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ItemAliasTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemAliasType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemaliastype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ItemAliasTypeDescriptionSpec, ItemAliasTypeDescriptionEdit, EditItemAliasTypeDescriptionForm, EditItemAliasTypeDescriptionResult> {
    
    @Override
    protected ItemAliasTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemAliasTypeDescriptionSpec();
        
        spec.setItemAliasTypeName(findParameter(request, ParameterConstants.ITEM_ALIAS_TYPE_NAME, actionForm.getItemAliasTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ItemAliasTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemAliasTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditItemAliasTypeDescriptionForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemAliasTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditItemAliasTypeDescriptionResult result, ItemAliasTypeDescriptionSpec spec, ItemAliasTypeDescriptionEdit edit) {
        actionForm.setItemAliasTypeName(spec.getItemAliasTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemAliasTypeDescriptionForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemAliasTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_ALIAS_TYPE_NAME, actionForm.getItemAliasTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditItemAliasTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.ITEM_ALIAS_TYPE_DESCRIPTION, result.getItemAliasTypeDescription());
    }

}
