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

package com.echothree.ui.web.main.action.content.contentwebaddress;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.form.GetContentWebAddressDescriptionsForm;
import com.echothree.control.user.content.common.result.GetContentWebAddressDescriptionsResult;
import com.echothree.model.control.content.common.transfer.ContentWebAddressTransfer;
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
    path = "/Content/ContentWebAddress/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/content/contentwebaddress/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        
        try {
            String contentWebAddressName = request.getParameter(ParameterConstants.CONTENT_WEB_ADDRESS_NAME);
            GetContentWebAddressDescriptionsForm commandForm = ContentUtil.getHome().getGetContentWebAddressDescriptionsForm();
            
            commandForm.setContentWebAddressName(contentWebAddressName);
            
            CommandResult commandResult = ContentUtil.getHome().getContentWebAddressDescriptions(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetContentWebAddressDescriptionsResult result = (GetContentWebAddressDescriptionsResult)executionResult.getResult();
            ContentWebAddressTransfer contentWebAddressTransfer = result.getContentWebAddress();
            
            request.setAttribute(AttributeConstants.CONTENT_WEB_ADDRESS, contentWebAddressTransfer);
            request.setAttribute(AttributeConstants.CONTENT_WEB_ADDRESS_NAME, contentWebAddressTransfer.getContentWebAddressName());
            request.setAttribute(AttributeConstants.CONTENT_WEB_ADDRESS_DESCRIPTIONS, result.getContentWebAddressDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}