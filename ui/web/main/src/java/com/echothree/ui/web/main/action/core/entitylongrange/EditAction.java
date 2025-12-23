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
import com.echothree.control.user.core.common.edit.EntityLongRangeEdit;
import com.echothree.control.user.core.common.form.EditEntityLongRangeForm;
import com.echothree.control.user.core.common.result.EditEntityLongRangeResult;
import com.echothree.control.user.core.common.spec.EntityLongRangeSpec;
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
    path = "/Core/EntityLongRange/Edit",
    mappingClass = SecureActionMapping.class,
    name = "EntityLongRangeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityLongRange/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/entitylongrange/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, EntityLongRangeSpec, EntityLongRangeEdit, EditEntityLongRangeForm, EditEntityLongRangeResult> {
    
    @Override
    protected EntityLongRangeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getEntityLongRangeSpec();
        
        spec.setComponentVendorName(findParameter(request, ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName()));
        spec.setEntityTypeName(findParameter(request, ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName()));
        spec.setEntityAttributeName(findParameter(request, ParameterConstants.ENTITY_ATTRIBUTE_NAME, actionForm.getEntityAttributeName()));
        spec.setEntityLongRangeName(findParameter(request, ParameterConstants.ORIGINAL_ENTITY_LONG_RANGE_NAME, actionForm.getOriginalEntityLongRangeName()));
        
        return spec;
    }
    
    @Override
    protected EntityLongRangeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getEntityLongRangeEdit();

        edit.setEntityLongRangeName(actionForm.getEntityLongRangeName());
        edit.setMinimumLongValue(actionForm.getMinimumLongValue());
        edit.setMaximumLongValue(actionForm.getMaximumLongValue());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditEntityLongRangeForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditEntityLongRangeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditEntityLongRangeResult result, EntityLongRangeSpec spec, EntityLongRangeEdit edit) {
        actionForm.setComponentVendorName(spec.getComponentVendorName());
        actionForm.setEntityTypeName(spec.getEntityTypeName());
        actionForm.setEntityAttributeName(spec.getEntityAttributeName());
        actionForm.setOriginalEntityLongRangeName(spec.getEntityLongRangeName());
        actionForm.setEntityLongRangeName(edit.getEntityLongRangeName());
        actionForm.setMinimumLongValue(edit.getMinimumLongValue());
        actionForm.setMaximumLongValue(edit.getMaximumLongValue());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditEntityLongRangeForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editEntityLongRange(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName());
        parameters.put(ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName());
        parameters.put(ParameterConstants.ENTITY_ATTRIBUTE_NAME, actionForm.getEntityAttributeName());
    }
    
}
