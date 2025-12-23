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

package com.echothree.ui.web.main.action.core.commandmessagetype;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.CommandMessageTypeDescriptionEdit;
import com.echothree.control.user.core.common.form.EditCommandMessageTypeDescriptionForm;
import com.echothree.control.user.core.common.result.EditCommandMessageTypeDescriptionResult;
import com.echothree.control.user.core.common.spec.CommandMessageTypeDescriptionSpec;
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
    path = "/Core/CommandMessageType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "CommandMessageTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/CommandMessageType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/core/commandmessagetype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, CommandMessageTypeDescriptionSpec, CommandMessageTypeDescriptionEdit, EditCommandMessageTypeDescriptionForm, EditCommandMessageTypeDescriptionResult> {
    
    @Override
    protected CommandMessageTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getCommandMessageTypeDescriptionSpec();
        
        spec.setCommandMessageTypeName(findParameter(request, ParameterConstants.COMMAND_MESSAGE_TYPE_NAME, actionForm.getCommandMessageTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected CommandMessageTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getCommandMessageTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditCommandMessageTypeDescriptionForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditCommandMessageTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditCommandMessageTypeDescriptionResult result, CommandMessageTypeDescriptionSpec spec, CommandMessageTypeDescriptionEdit edit) {
        actionForm.setCommandMessageTypeName(spec.getCommandMessageTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditCommandMessageTypeDescriptionForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editCommandMessageTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COMMAND_MESSAGE_TYPE_NAME, actionForm.getCommandMessageTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditCommandMessageTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.COMMAND_MESSAGE_TYPE_DESCRIPTION, result.getCommandMessageTypeDescription());
    }

}
