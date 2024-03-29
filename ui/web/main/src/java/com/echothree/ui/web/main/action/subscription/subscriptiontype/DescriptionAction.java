// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.main.action.subscription.subscriptiontype;

import com.echothree.control.user.subscription.common.SubscriptionUtil;
import com.echothree.control.user.subscription.common.form.GetSubscriptionTypeDescriptionsForm;
import com.echothree.control.user.subscription.common.result.GetSubscriptionTypeDescriptionsResult;
import com.echothree.model.control.subscription.common.transfer.SubscriptionTypeTransfer;
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
    path = "/Subscription/SubscriptionType/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/subscription/subscriptiontype/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            String subscriptionKindName = request.getParameter(ParameterConstants.SUBSCRIPTION_KIND_NAME);
            String subscriptionTypeName = request.getParameter(ParameterConstants.SUBSCRIPTION_TYPE_NAME);
            GetSubscriptionTypeDescriptionsForm commandForm = SubscriptionUtil.getHome().getGetSubscriptionTypeDescriptionsForm();
            
            commandForm.setSubscriptionKindName(subscriptionKindName);
            commandForm.setSubscriptionTypeName(subscriptionTypeName);
            
            CommandResult commandResult = SubscriptionUtil.getHome().getSubscriptionTypeDescriptions(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetSubscriptionTypeDescriptionsResult result = (GetSubscriptionTypeDescriptionsResult)executionResult.getResult();
            SubscriptionTypeTransfer subscriptionType = result.getSubscriptionType();
            
            request.setAttribute("subscriptionKind", subscriptionType.getSubscriptionKind());
            request.setAttribute("subscriptionType", subscriptionType);
            request.setAttribute("subscriptionTypeDescriptions", result.getSubscriptionTypeDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}