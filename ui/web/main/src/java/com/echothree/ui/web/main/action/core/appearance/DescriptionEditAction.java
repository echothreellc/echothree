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

package com.echothree.ui.web.main.action.core.appearance;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.AppearanceDescriptionEdit;
import com.echothree.control.user.core.common.form.EditAppearanceDescriptionForm;
import com.echothree.control.user.core.common.result.EditAppearanceDescriptionResult;
import com.echothree.control.user.core.common.spec.AppearanceDescriptionSpec;
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
    path = "/Core/Appearance/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "AppearanceDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/Appearance/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/appearance/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, AppearanceDescriptionSpec, AppearanceDescriptionEdit, EditAppearanceDescriptionForm, EditAppearanceDescriptionResult> {
    
    @Override
    protected AppearanceDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getAppearanceDescriptionSpec();
        
        spec.setAppearanceName(findParameter(request, ParameterConstants.APPEARANCE_NAME, actionForm.getAppearanceName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected AppearanceDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getAppearanceDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditAppearanceDescriptionForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditAppearanceDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditAppearanceDescriptionResult result, AppearanceDescriptionSpec spec, AppearanceDescriptionEdit edit) {
        actionForm.setAppearanceName(spec.getAppearanceName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditAppearanceDescriptionForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editAppearanceDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.APPEARANCE_NAME, actionForm.getAppearanceName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditAppearanceDescriptionResult result) {
        request.setAttribute(AttributeConstants.APPEARANCE_DESCRIPTION, result.getAppearanceDescription());
    }

}
