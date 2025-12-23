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
import com.echothree.control.user.core.common.edit.ServerEdit;
import com.echothree.control.user.core.common.form.EditServerForm;
import com.echothree.control.user.core.common.result.EditServerResult;
import com.echothree.control.user.core.common.spec.ServerSpec;
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
    path = "/Configuration/Server/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ServerEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/Server/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/server/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ServerSpec, ServerEdit, EditServerForm, EditServerResult> {
    
    @Override
    protected ServerSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getServerSpec();
        
        spec.setServerName(findParameter(request, ParameterConstants.ORIGINAL_SERVER_NAME, actionForm.getOriginalServerName()));
        
        return spec;
    }
    
    @Override
    protected ServerEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getServerEdit();

        edit.setServerName(actionForm.getServerName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditServerForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditServerForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditServerResult result, ServerSpec spec, ServerEdit edit) {
        actionForm.setOriginalServerName(spec.getServerName());
        actionForm.setServerName(edit.getServerName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditServerForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editServer(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditServerResult result) {
        request.setAttribute(AttributeConstants.SERVER, result.getServer());
    }

}
