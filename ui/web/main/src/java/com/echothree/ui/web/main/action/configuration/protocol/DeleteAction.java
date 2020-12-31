// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.control.user.core.common.form.DeleteProtocolForm;
import com.echothree.control.user.core.common.form.GetProtocolForm;
import com.echothree.control.user.core.common.result.GetProtocolResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/Protocol/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ProtocolDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/Protocol/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/protocol/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.Protocol.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setProtocolName(findParameter(request, ParameterConstants.PROTOCOL_NAME, actionForm.getProtocolName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        GetProtocolForm commandForm = CoreUtil.getHome().getGetProtocolForm();
        
        commandForm.setProtocolName(actionForm.getProtocolName());
        
        CommandResult commandResult = CoreUtil.getHome().getProtocol(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetProtocolResult result = (GetProtocolResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.PROTOCOL, result.getProtocol());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        DeleteProtocolForm commandForm = CoreUtil.getHome().getDeleteProtocolForm();

        commandForm.setProtocolName(actionForm.getProtocolName());

        return CoreUtil.getHome().deleteProtocol(getUserVisitPK(request), commandForm);
    }
    
}
