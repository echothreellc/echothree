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
import com.echothree.control.user.item.common.edit.ItemWeightTypeDescriptionEdit;
import com.echothree.control.user.item.common.form.EditItemWeightTypeDescriptionForm;
import com.echothree.control.user.item.common.result.EditItemWeightTypeDescriptionResult;
import com.echothree.control.user.item.common.spec.ItemWeightTypeDescriptionSpec;
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
    path = "/Item/ItemWeightType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ItemWeightTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemWeightType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemweighttype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ItemWeightTypeDescriptionSpec, ItemWeightTypeDescriptionEdit, EditItemWeightTypeDescriptionForm, EditItemWeightTypeDescriptionResult> {
    
    @Override
    protected ItemWeightTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemWeightTypeDescriptionSpec();
        
        spec.setItemWeightTypeName(findParameter(request, ParameterConstants.ITEM_WEIGHT_TYPE_NAME, actionForm.getItemWeightTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ItemWeightTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemWeightTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditItemWeightTypeDescriptionForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemWeightTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditItemWeightTypeDescriptionResult result, ItemWeightTypeDescriptionSpec spec, ItemWeightTypeDescriptionEdit edit) {
        actionForm.setItemWeightTypeName(spec.getItemWeightTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemWeightTypeDescriptionForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemWeightTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ITEM_WEIGHT_TYPE_NAME, actionForm.getItemWeightTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditItemWeightTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.ITEM_WEIGHT_TYPE_DESCRIPTION, result.getItemWeightTypeDescription());
    }

}
