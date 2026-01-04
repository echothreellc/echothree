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
import com.echothree.control.user.core.common.edit.EntityIntegerRangeEdit;
import com.echothree.control.user.core.common.form.EditEntityIntegerRangeForm;
import com.echothree.control.user.core.common.result.EditEntityIntegerRangeResult;
import com.echothree.control.user.core.common.spec.EntityIntegerRangeSpec;
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
    path = "/Core/EntityIntegerRange/Edit",
    mappingClass = SecureActionMapping.class,
    name = "EntityIntegerRangeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityIntegerRange/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/entityintegerrange/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, EntityIntegerRangeSpec, EntityIntegerRangeEdit, EditEntityIntegerRangeForm, EditEntityIntegerRangeResult> {
    
    @Override
    protected EntityIntegerRangeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getEntityIntegerRangeSpec();
        
        spec.setComponentVendorName(findParameter(request, ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName()));
        spec.setEntityTypeName(findParameter(request, ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName()));
        spec.setEntityAttributeName(findParameter(request, ParameterConstants.ENTITY_ATTRIBUTE_NAME, actionForm.getEntityAttributeName()));
        spec.setEntityIntegerRangeName(findParameter(request, ParameterConstants.ORIGINAL_ENTITY_INTEGER_RANGE_NAME, actionForm.getOriginalEntityIntegerRangeName()));
        
        return spec;
    }
    
    @Override
    protected EntityIntegerRangeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getEntityIntegerRangeEdit();

        edit.setEntityIntegerRangeName(actionForm.getEntityIntegerRangeName());
        edit.setMinimumIntegerValue(actionForm.getMinimumIntegerValue());
        edit.setMaximumIntegerValue(actionForm.getMaximumIntegerValue());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditEntityIntegerRangeForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditEntityIntegerRangeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditEntityIntegerRangeResult result, EntityIntegerRangeSpec spec, EntityIntegerRangeEdit edit) {
        actionForm.setComponentVendorName(spec.getComponentVendorName());
        actionForm.setEntityTypeName(spec.getEntityTypeName());
        actionForm.setEntityAttributeName(spec.getEntityAttributeName());
        actionForm.setOriginalEntityIntegerRangeName(spec.getEntityIntegerRangeName());
        actionForm.setEntityIntegerRangeName(edit.getEntityIntegerRangeName());
        actionForm.setMinimumIntegerValue(edit.getMinimumIntegerValue());
        actionForm.setMaximumIntegerValue(edit.getMaximumIntegerValue());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditEntityIntegerRangeForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editEntityIntegerRange(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName());
        parameters.put(ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName());
        parameters.put(ParameterConstants.ENTITY_ATTRIBUTE_NAME, actionForm.getEntityAttributeName());
    }
    
}
