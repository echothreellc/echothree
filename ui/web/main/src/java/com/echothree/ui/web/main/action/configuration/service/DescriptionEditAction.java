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

package com.echothree.ui.web.main.action.configuration.service;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.ServiceDescriptionEdit;
import com.echothree.control.user.core.common.form.EditServiceDescriptionForm;
import com.echothree.control.user.core.common.result.EditServiceDescriptionResult;
import com.echothree.control.user.core.common.spec.ServiceDescriptionSpec;
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
    path = "/Configuration/Service/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ServiceDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/Service/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/service/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ServiceDescriptionSpec, ServiceDescriptionEdit, EditServiceDescriptionForm, EditServiceDescriptionResult> {
    
    @Override
    protected ServiceDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getServiceDescriptionSpec();
        
        spec.setServiceName(findParameter(request, ParameterConstants.SERVICE_NAME, actionForm.getServiceName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ServiceDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getServiceDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditServiceDescriptionForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditServiceDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditServiceDescriptionResult result, ServiceDescriptionSpec spec, ServiceDescriptionEdit edit) {
        actionForm.setServiceName(spec.getServiceName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditServiceDescriptionForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editServiceDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.SERVICE_NAME, actionForm.getServiceName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditServiceDescriptionResult result) {
        request.setAttribute(AttributeConstants.SERVICE_DESCRIPTION, result.getServiceDescription());
    }

}
