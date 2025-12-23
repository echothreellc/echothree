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

package com.echothree.ui.web.main.action.core.color;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.ColorDescriptionEdit;
import com.echothree.control.user.core.common.form.EditColorDescriptionForm;
import com.echothree.control.user.core.common.result.EditColorDescriptionResult;
import com.echothree.control.user.core.common.spec.ColorDescriptionSpec;
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
    path = "/Core/Color/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ColorDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/Color/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/color/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ColorDescriptionSpec, ColorDescriptionEdit, EditColorDescriptionForm, EditColorDescriptionResult> {
    
    @Override
    protected ColorDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getColorDescriptionSpec();
        
        spec.setColorName(findParameter(request, ParameterConstants.COLOR_NAME, actionForm.getColorName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ColorDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getColorDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditColorDescriptionForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditColorDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditColorDescriptionResult result, ColorDescriptionSpec spec, ColorDescriptionEdit edit) {
        actionForm.setColorName(spec.getColorName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditColorDescriptionForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editColorDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COLOR_NAME, actionForm.getColorName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditColorDescriptionResult result) {
        request.setAttribute(AttributeConstants.COLOR_DESCRIPTION, result.getColorDescription());
    }

}
