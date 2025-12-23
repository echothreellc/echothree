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
import com.echothree.control.user.core.common.edit.CommandMessageTypeEdit;
import com.echothree.control.user.core.common.form.EditCommandMessageTypeForm;
import com.echothree.control.user.core.common.result.EditCommandMessageTypeResult;
import com.echothree.control.user.core.common.spec.CommandMessageTypeSpec;
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
    path = "/Core/CommandMessageType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "CommandMessageTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/CommandMessageType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/commandmessagetype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, CommandMessageTypeSpec, CommandMessageTypeEdit, EditCommandMessageTypeForm, EditCommandMessageTypeResult> {
    
    @Override
    protected CommandMessageTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getCommandMessageTypeSpec();
        
        spec.setCommandMessageTypeName(findParameter(request, ParameterConstants.ORIGINAL_COMMAND_MESSAGE_TYPE_NAME, actionForm.getOriginalCommandMessageTypeName()));
        
        return spec;
    }
    
    @Override
    protected CommandMessageTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getCommandMessageTypeEdit();

        edit.setCommandMessageTypeName(actionForm.getCommandMessageTypeName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditCommandMessageTypeForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditCommandMessageTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditCommandMessageTypeResult result, CommandMessageTypeSpec spec, CommandMessageTypeEdit edit) {
        actionForm.setOriginalCommandMessageTypeName(spec.getCommandMessageTypeName());
        actionForm.setCommandMessageTypeName(edit.getCommandMessageTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditCommandMessageTypeForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editCommandMessageType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditCommandMessageTypeResult result) {
        request.setAttribute(AttributeConstants.COMMAND_MESSAGE_TYPE, result.getCommandMessageType());
    }

}
