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

package com.echothree.ui.web.main.action.core.entityattributegroup;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.EntityAttributeGroupDescriptionEdit;
import com.echothree.control.user.core.common.form.EditEntityAttributeGroupDescriptionForm;
import com.echothree.control.user.core.common.result.EditEntityAttributeGroupDescriptionResult;
import com.echothree.control.user.core.common.spec.EntityAttributeGroupDescriptionSpec;
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
    path = "/Core/EntityAttributeGroup/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "EntityAttributeGroupDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityAttributeGroup/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/entityattributegroup/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, EntityAttributeGroupDescriptionSpec, EntityAttributeGroupDescriptionEdit, EditEntityAttributeGroupDescriptionForm, EditEntityAttributeGroupDescriptionResult> {
    
    @Override
    protected EntityAttributeGroupDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getEntityAttributeGroupDescriptionSpec();
        
        spec.setEntityAttributeGroupName(findParameter(request, ParameterConstants.ENTITY_ATTRIBUTE_GROUP_NAME, actionForm.getEntityAttributeGroupName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected EntityAttributeGroupDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getEntityAttributeGroupDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditEntityAttributeGroupDescriptionForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditEntityAttributeGroupDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditEntityAttributeGroupDescriptionResult result, EntityAttributeGroupDescriptionSpec spec, EntityAttributeGroupDescriptionEdit edit) {
        actionForm.setEntityAttributeGroupName(spec.getEntityAttributeGroupName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditEntityAttributeGroupDescriptionForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editEntityAttributeGroupDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.ENTITY_ATTRIBUTE_GROUP_NAME, actionForm.getEntityAttributeGroupName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditEntityAttributeGroupDescriptionResult result) {
        request.setAttribute(AttributeConstants.ENTITY_ATTRIBUTE_GROUP_DESCRIPTION, result.getEntityAttributeGroupDescription());
    }

}
