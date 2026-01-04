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

package com.echothree.ui.web.main.action.configuration.server;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.ServerDescriptionEdit;
import com.echothree.control.user.core.common.form.EditServerDescriptionForm;
import com.echothree.control.user.core.common.result.EditServerDescriptionResult;
import com.echothree.control.user.core.common.spec.ServerDescriptionSpec;
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
    path = "/Configuration/Server/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ServerDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/Server/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/server/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ServerDescriptionSpec, ServerDescriptionEdit, EditServerDescriptionForm, EditServerDescriptionResult> {
    
    @Override
    protected ServerDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getServerDescriptionSpec();
        
        spec.setServerName(findParameter(request, ParameterConstants.SERVER_NAME, actionForm.getServerName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ServerDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getServerDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditServerDescriptionForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditServerDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditServerDescriptionResult result, ServerDescriptionSpec spec, ServerDescriptionEdit edit) {
        actionForm.setServerName(spec.getServerName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditServerDescriptionForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editServerDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SERVER_NAME, actionForm.getServerName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditServerDescriptionResult result) {
        request.setAttribute(AttributeConstants.SERVER_DESCRIPTION, result.getServerDescription());
    }

}
