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

package com.echothree.ui.web.main.action.configuration.scaletype;

import com.echothree.control.user.scale.common.ScaleUtil;
import com.echothree.control.user.scale.common.result.GetScaleTypeResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/ScaleType/Delete",
    mappingClass = SecureActionMapping.class,
    name = "ScaleTypeDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/ScaleType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/scaletype/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.ScaleType.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setScaleTypeName(findParameter(request, ParameterConstants.SCALE_TYPE_NAME, actionForm.getScaleTypeName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ScaleUtil.getHome().getGetScaleTypeForm();
        
        commandForm.setScaleTypeName(actionForm.getScaleTypeName());

        var commandResult = ScaleUtil.getHome().getScaleType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetScaleTypeResult)executionResult.getResult();
        
        request.setAttribute(AttributeConstants.SCALE_TYPE, result.getScaleType());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ScaleUtil.getHome().getDeleteScaleTypeForm();

        commandForm.setScaleTypeName(actionForm.getScaleTypeName());

        return ScaleUtil.getHome().deleteScaleType(getUserVisitPK(request), commandForm);
    }
    
}
