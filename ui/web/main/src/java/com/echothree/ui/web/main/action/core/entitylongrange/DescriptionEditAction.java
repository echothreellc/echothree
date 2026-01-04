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

package com.echothree.ui.web.main.action.core.entitylongrange;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.EntityLongRangeDescriptionEdit;
import com.echothree.control.user.core.common.form.EditEntityLongRangeDescriptionForm;
import com.echothree.control.user.core.common.result.EditEntityLongRangeDescriptionResult;
import com.echothree.control.user.core.common.spec.EntityLongRangeDescriptionSpec;
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
    path = "/Core/EntityLongRange/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "EntityLongRangeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityLongRange/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/entitylongrange/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, EntityLongRangeDescriptionSpec, EntityLongRangeDescriptionEdit, EditEntityLongRangeDescriptionForm, EditEntityLongRangeDescriptionResult> {
    
    @Override
    protected EntityLongRangeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getEntityLongRangeDescriptionSpec();
        
        spec.setComponentVendorName(findParameter(request, ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName()));
        spec.setEntityTypeName(findParameter(request, ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName()));
        spec.setEntityAttributeName(findParameter(request, ParameterConstants.ENTITY_ATTRIBUTE_NAME, actionForm.getEntityAttributeName()));
        spec.setEntityLongRangeName(findParameter(request, ParameterConstants.ENTITY_LONG_RANGE_NAME, actionForm.getEntityLongRangeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected EntityLongRangeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getEntityLongRangeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditEntityLongRangeDescriptionForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditEntityLongRangeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditEntityLongRangeDescriptionResult result, EntityLongRangeDescriptionSpec spec, EntityLongRangeDescriptionEdit edit) {
        actionForm.setComponentVendorName(spec.getComponentVendorName());
        actionForm.setEntityTypeName(spec.getEntityTypeName());
        actionForm.setEntityAttributeName(spec.getEntityAttributeName());
        actionForm.setEntityLongRangeName(spec.getEntityLongRangeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditEntityLongRangeDescriptionForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editEntityLongRangeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName());
        parameters.put(ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName());
        parameters.put(ParameterConstants.ENTITY_ATTRIBUTE_NAME, actionForm.getEntityAttributeName());
        parameters.put(ParameterConstants.ENTITY_LONG_RANGE_NAME, actionForm.getEntityLongRangeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditEntityLongRangeDescriptionResult result) {
        request.setAttribute(AttributeConstants.ENTITY_LONG_RANGE_DESCRIPTION, result.getEntityLongRangeDescription());
    }

}
