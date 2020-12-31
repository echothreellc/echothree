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

package com.echothree.ui.web.main.action.returnpolicy.returnreason;

import com.echothree.control.user.returnpolicy.common.ReturnPolicyUtil;
import com.echothree.control.user.returnpolicy.common.form.GetReturnReasonDescriptionsForm;
import com.echothree.control.user.returnpolicy.common.result.GetReturnReasonDescriptionsResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/ReturnPolicy/ReturnReason/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/returnpolicy/returnreason/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            String returnKindName = request.getParameter(ParameterConstants.RETURN_KIND_NAME);
            String returnReasonName = request.getParameter(ParameterConstants.RETURN_REASON_NAME);
            GetReturnReasonDescriptionsForm commandForm = ReturnPolicyUtil.getHome().getGetReturnReasonDescriptionsForm();
            
            commandForm.setReturnKindName(returnKindName);
            commandForm.setReturnReasonName(returnReasonName);
            
            CommandResult commandResult = ReturnPolicyUtil.getHome().getReturnReasonDescriptions(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetReturnReasonDescriptionsResult result = (GetReturnReasonDescriptionsResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.RETURN_REASON, result.getReturnReason());
            request.setAttribute(AttributeConstants.RETURN_REASON_DESCRIPTIONS, result.getReturnReasonDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}