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

package com.echothree.ui.web.main.action.core.entityintegerrange;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.EntityIntegerRangeDescriptionEdit;
import com.echothree.control.user.core.common.form.EditEntityIntegerRangeDescriptionForm;
import com.echothree.control.user.core.common.result.EditEntityIntegerRangeDescriptionResult;
import com.echothree.control.user.core.common.spec.EntityIntegerRangeDescriptionSpec;
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
    path = "/Core/EntityIntegerRange/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "EntityIntegerRangeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityIntegerRange/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/entityintegerrange/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, EntityIntegerRangeDescriptionSpec, EntityIntegerRangeDescriptionEdit, EditEntityIntegerRangeDescriptionForm, EditEntityIntegerRangeDescriptionResult> {
    
    @Override
    protected EntityIntegerRangeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getEntityIntegerRangeDescriptionSpec();
        
        spec.setComponentVendorName(findParameter(request, ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName()));
        spec.setEntityTypeName(findParameter(request, ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName()));
        spec.setEntityAttributeName(findParameter(request, ParameterConstants.ENTITY_ATTRIBUTE_NAME, actionForm.getEntityAttributeName()));
        spec.setEntityIntegerRangeName(findParameter(request, ParameterConstants.ENTITY_INTEGER_RANGE_NAME, actionForm.getEntityIntegerRangeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected EntityIntegerRangeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getEntityIntegerRangeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditEntityIntegerRangeDescriptionForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditEntityIntegerRangeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditEntityIntegerRangeDescriptionResult result, EntityIntegerRangeDescriptionSpec spec, EntityIntegerRangeDescriptionEdit edit) {
        actionForm.setComponentVendorName(spec.getComponentVendorName());
        actionForm.setEntityTypeName(spec.getEntityTypeName());
        actionForm.setEntityAttributeName(spec.getEntityAttributeName());
        actionForm.setEntityIntegerRangeName(spec.getEntityIntegerRangeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditEntityIntegerRangeDescriptionForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editEntityIntegerRangeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName());
        parameters.put(ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName());
        parameters.put(ParameterConstants.ENTITY_ATTRIBUTE_NAME, actionForm.getEntityAttributeName());
        parameters.put(ParameterConstants.ENTITY_INTEGER_RANGE_NAME, actionForm.getEntityIntegerRangeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditEntityIntegerRangeDescriptionResult result) {
        request.setAttribute(AttributeConstants.ENTITY_INTEGER_RANGE_DESCRIPTION, result.getEntityIntegerRangeDescription());
    }

}
