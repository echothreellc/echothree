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

package com.echothree.ui.web.main.action.configuration.recoveryquestion;

import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.result.GetRecoveryQuestionDescriptionsResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
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
    path = "/Configuration/RecoveryQuestion/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/configuration/recoveryquestion/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            var recoveryQuestionName = request.getParameter(ParameterConstants.RECOVERY_QUESTION_NAME);
            var commandForm = UserUtil.getHome().getGetRecoveryQuestionDescriptionsForm();
            
            commandForm.setRecoveryQuestionName(recoveryQuestionName);

            var commandResult = UserUtil.getHome().getRecoveryQuestionDescriptions(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetRecoveryQuestionDescriptionsResult)executionResult.getResult();
            var recoveryQuestionTransfer = result.getRecoveryQuestion();
            
            request.setAttribute(AttributeConstants.RECOVERY_QUESTION, recoveryQuestionTransfer);
            request.setAttribute(AttributeConstants.RECOVERY_QUESTION_NAME, recoveryQuestionTransfer.getRecoveryQuestionName());
            request.setAttribute(AttributeConstants.RECOVERY_QUESTION_DESCRIPTIONS, result.getRecoveryQuestionDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}