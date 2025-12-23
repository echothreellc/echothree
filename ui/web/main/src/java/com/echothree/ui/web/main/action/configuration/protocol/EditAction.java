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

package com.echothree.ui.web.main.action.configuration.protocol;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.ProtocolEdit;
import com.echothree.control.user.core.common.form.EditProtocolForm;
import com.echothree.control.user.core.common.result.EditProtocolResult;
import com.echothree.control.user.core.common.spec.ProtocolSpec;
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
    path = "/Configuration/Protocol/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ProtocolEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/Protocol/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/protocol/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ProtocolSpec, ProtocolEdit, EditProtocolForm, EditProtocolResult> {
    
    @Override
    protected ProtocolSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getProtocolSpec();
        
        spec.setProtocolName(findParameter(request, ParameterConstants.ORIGINAL_PROTOCOL_NAME, actionForm.getOriginalProtocolName()));
        
        return spec;
    }
    
    @Override
    protected ProtocolEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getProtocolEdit();

        edit.setProtocolName(actionForm.getProtocolName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditProtocolForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditProtocolForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditProtocolResult result, ProtocolSpec spec, ProtocolEdit edit) {
        actionForm.setOriginalProtocolName(spec.getProtocolName());
        actionForm.setProtocolName(edit.getProtocolName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditProtocolForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editProtocol(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditProtocolResult result) {
        request.setAttribute(AttributeConstants.PROTOCOL, result.getProtocol());
    }

}
