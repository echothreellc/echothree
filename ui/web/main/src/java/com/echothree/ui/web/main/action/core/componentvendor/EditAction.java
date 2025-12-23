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

package com.echothree.ui.web.main.action.core.componentvendor;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.ComponentVendorEdit;
import com.echothree.control.user.core.common.form.EditComponentVendorForm;
import com.echothree.control.user.core.common.result.EditComponentVendorResult;
import com.echothree.control.user.core.common.spec.ComponentVendorSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Core/ComponentVendor/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ComponentVendorEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/ComponentVendor/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/componentvendor/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ComponentVendorSpec, ComponentVendorEdit, EditComponentVendorForm, EditComponentVendorResult> {
    
    @Override
    protected ComponentVendorSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getComponentVendorSpec();
        
        spec.setComponentVendorName(findParameter(request, ParameterConstants.ORIGINAL_COMPONENT_VENDOR_NAME, actionForm.getOriginalComponentVendorName()));
        
        return spec;
    }
    
    @Override
    protected ComponentVendorEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getComponentVendorEdit();

        edit.setComponentVendorName(actionForm.getComponentVendorName());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditComponentVendorForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditComponentVendorForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditComponentVendorResult result, ComponentVendorSpec spec, ComponentVendorEdit edit) {
        actionForm.setOriginalComponentVendorName(spec.getComponentVendorName());
        actionForm.setComponentVendorName(edit.getComponentVendorName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditComponentVendorForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editComponentVendor(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditComponentVendorResult result) {
        request.setAttribute(AttributeConstants.COMPONENT_VENDOR, result.getComponentVendor());
    }

}
