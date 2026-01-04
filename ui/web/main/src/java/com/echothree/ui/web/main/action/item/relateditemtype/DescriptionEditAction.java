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

package com.echothree.ui.web.main.action.item.relateditemtype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.RelatedItemTypeDescriptionEdit;
import com.echothree.control.user.item.common.form.EditRelatedItemTypeDescriptionForm;
import com.echothree.control.user.item.common.result.EditRelatedItemTypeDescriptionResult;
import com.echothree.control.user.item.common.spec.RelatedItemTypeDescriptionSpec;
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
    path = "/Item/RelatedItemType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "RelatedItemTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/RelatedItemType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/item/relateditemtype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, RelatedItemTypeDescriptionSpec, RelatedItemTypeDescriptionEdit, EditRelatedItemTypeDescriptionForm, EditRelatedItemTypeDescriptionResult> {
    
    @Override
    protected RelatedItemTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getRelatedItemTypeDescriptionSpec();
        
        spec.setRelatedItemTypeName(findParameter(request, ParameterConstants.RELATED_ITEM_TYPE_NAME, actionForm.getRelatedItemTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected RelatedItemTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getRelatedItemTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditRelatedItemTypeDescriptionForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditRelatedItemTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditRelatedItemTypeDescriptionResult result, RelatedItemTypeDescriptionSpec spec, RelatedItemTypeDescriptionEdit edit) {
        actionForm.setRelatedItemTypeName(spec.getRelatedItemTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditRelatedItemTypeDescriptionForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editRelatedItemTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.RELATED_ITEM_TYPE_NAME, actionForm.getRelatedItemTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditRelatedItemTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.RELATED_ITEM_TYPE_DESCRIPTION, result.getRelatedItemTypeDescription());
    }

}
