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

package com.echothree.ui.web.main.action.core.commandmessage;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.CommandMessageTranslationEdit;
import com.echothree.control.user.core.common.form.EditCommandMessageTranslationForm;
import com.echothree.control.user.core.common.result.EditCommandMessageTranslationResult;
import com.echothree.control.user.core.common.spec.CommandMessageTranslationSpec;
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
    path = "/Core/CommandMessage/TranslationEdit",
    mappingClass = SecureActionMapping.class,
    name = "CommandMessageTranslationEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/CommandMessage/Translation", redirect = true),
        @SproutForward(name = "Form", path = "/core/commandmessage/translationEdit.jsp")
    }
)
public class TranslationEditAction
        extends MainBaseEditAction<TranslationEditActionForm, CommandMessageTranslationSpec, CommandMessageTranslationEdit, EditCommandMessageTranslationForm, EditCommandMessageTranslationResult> {
    
    @Override
    protected CommandMessageTranslationSpec getSpec(HttpServletRequest request, TranslationEditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getCommandMessageTranslationSpec();
        
        spec.setCommandMessageTypeName(findParameter(request, ParameterConstants.COMMAND_MESSAGE_TYPE_NAME, actionForm.getCommandMessageTypeName()));
        spec.setCommandMessageKey(findParameter(request, ParameterConstants.COMMAND_MESSAGE_KEY, actionForm.getCommandMessageKey()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected CommandMessageTranslationEdit getEdit(HttpServletRequest request, TranslationEditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getCommandMessageTranslationEdit();

        edit.setTranslation(actionForm.getTranslation());

        return edit;
    }
    
    @Override
    protected EditCommandMessageTranslationForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditCommandMessageTranslationForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, TranslationEditActionForm actionForm, EditCommandMessageTranslationResult result, CommandMessageTranslationSpec spec, CommandMessageTranslationEdit edit) {
        actionForm.setCommandMessageTypeName(spec.getCommandMessageTypeName());
        actionForm.setCommandMessageKey(spec.getCommandMessageKey());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setTranslation(edit.getTranslation());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditCommandMessageTranslationForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editCommandMessageTranslation(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(TranslationEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COMMAND_MESSAGE_TYPE_NAME, actionForm.getCommandMessageTypeName());
        parameters.put(ParameterConstants.COMMAND_MESSAGE_KEY, actionForm.getCommandMessageKey());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, TranslationEditActionForm actionForm, EditCommandMessageTranslationResult result) {
        request.setAttribute(AttributeConstants.COMMAND_MESSAGE_TRANSLATION, result.getCommandMessageTranslation());
    }

}
