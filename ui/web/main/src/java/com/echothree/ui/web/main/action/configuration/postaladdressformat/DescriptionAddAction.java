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

package com.echothree.ui.web.main.action.configuration.postaladdressformat;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Configuration/PostalAddressFormat/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "PostalAddressFormatDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/PostalAddressFormat/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/postaladdressformat/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var postalAddressFormatName = request.getParameter(ParameterConstants.POSTAL_ADDRESS_FORMAT_NAME);
        
        try {
            if(forwardKey == null) {
                var descriptionAddActionForm = (DescriptionAddActionForm)form;
                
                if(wasPost(request)) {
                    var commandForm = ContactUtil.getHome().getCreatePostalAddressFormatDescriptionForm();
                    
                    if(postalAddressFormatName == null)
                        postalAddressFormatName = descriptionAddActionForm.getPostalAddressFormatName();
                    
                    commandForm.setPostalAddressFormatName(postalAddressFormatName);
                    commandForm.setLanguageIsoName(descriptionAddActionForm.getLanguageChoice());
                    commandForm.setDescription(descriptionAddActionForm.getDescription());

                    var commandResult = ContactUtil.getHome().createPostalAddressFormatDescription(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    descriptionAddActionForm.setPostalAddressFormatName(postalAddressFormatName);
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.POSTAL_ADDRESS_FORMAT_NAME, postalAddressFormatName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.POSTAL_ADDRESS_FORMAT_NAME, postalAddressFormatName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}