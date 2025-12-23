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

package com.echothree.ui.web.main.action.content.contentwebaddress;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.result.GetContentWebAddressDescriptionResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/Content/ContentWebAddress/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    name = "ContentWebAddressDescriptionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Content/ContentWebAddress/Description", redirect = true),
        @SproutForward(name = "Form", path = "/content/contentwebaddress/descriptionDelete.jsp")
    }
)
public class DescriptionDeleteAction
        extends MainBaseDeleteAction<DescriptionDeleteActionForm> {
    
    @Override
    public String getEntityTypeName() {
        return EntityTypes.ContentWebAddressDescription.name();
    }

    @Override
    public void setupParameters(DescriptionDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setContentWebAddressName(findParameter(request, ParameterConstants.CONTENT_WEB_ADDRESS_NAME, actionForm.getContentWebAddressName()));
        actionForm.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
    }

    @Override
    public void setupTransfer(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContentUtil.getHome().getGetContentWebAddressDescriptionForm();

        commandForm.setContentWebAddressName(actionForm.getContentWebAddressName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        var commandResult = ContentUtil.getHome().getContentWebAddressDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetContentWebAddressDescriptionResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.CONTENT_WEB_ADDRESS_DESCRIPTION, result.getContentWebAddressDescription());
        }
    }

    @Override
    public CommandResult doDelete(DescriptionDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContentUtil.getHome().getDeleteContentWebAddressDescriptionForm();

        commandForm.setContentWebAddressName(actionForm.getContentWebAddressName());
        commandForm.setLanguageIsoName(actionForm.getLanguageIsoName());

        return ContentUtil.getHome().deleteContentWebAddressDescription(getUserVisitPK(request), commandForm);
    }

    @Override
    public void setupForwardParameters(DescriptionDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTENT_WEB_ADDRESS_NAME, actionForm.getContentWebAddressName());
    }
    
}
