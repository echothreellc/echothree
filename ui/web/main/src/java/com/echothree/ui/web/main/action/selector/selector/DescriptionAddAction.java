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

package com.echothree.ui.web.main.action.selector.selector;

import com.echothree.control.user.selector.common.SelectorUtil;
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
    path = "/Selector/Selector/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "SelectorDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Selector/Selector/Description", redirect = true),
        @SproutForward(name = "Form", path = "/selector/selector/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var selectorKindName = request.getParameter(ParameterConstants.SELECTOR_KIND_NAME);
        var selectorTypeName = request.getParameter(ParameterConstants.SELECTOR_TYPE_NAME);
        var selectorName = request.getParameter(ParameterConstants.SELECTOR_NAME);
        
        try {
            if(forwardKey == null) {
                if(wasPost(request)) {
                    var descriptionAddActionForm = (DescriptionAddActionForm)form;

                    var createSelectorDescriptionForm = SelectorUtil.getHome().getCreateSelectorDescriptionForm();
                    
                    if(selectorKindName == null)
                        selectorKindName = descriptionAddActionForm.getSelectorKindName();
                    if(selectorTypeName == null)
                        selectorTypeName = descriptionAddActionForm.getSelectorTypeName();
                    if(selectorName == null)
                        selectorName = descriptionAddActionForm.getSelectorName();
                    
                    createSelectorDescriptionForm.setSelectorKindName(selectorKindName);
                    createSelectorDescriptionForm.setSelectorTypeName(selectorTypeName);
                    createSelectorDescriptionForm.setSelectorName(selectorName);
                    createSelectorDescriptionForm.setLanguageIsoName(descriptionAddActionForm.getLanguageChoice());
                    createSelectorDescriptionForm.setDescription(descriptionAddActionForm.getDescription());

                    var commandResult = SelectorUtil.getHome().createSelectorDescription(getUserVisitPK(request), createSelectorDescriptionForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else
                    forwardKey = ForwardConstants.FORM;
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM) || forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(3);
            parameters.put(ParameterConstants.SELECTOR_KIND_NAME, selectorKindName);
            parameters.put(ParameterConstants.SELECTOR_TYPE_NAME, selectorTypeName);
            parameters.put(ParameterConstants.SELECTOR_NAME, selectorName);
            customActionForward.setParameters(parameters);
            
            request.setAttribute("selectorKindName", selectorKindName); // TODO: not encoded
            request.setAttribute("selectorTypeName", selectorTypeName); // TODO: not encoded
            request.setAttribute("selectorName", selectorName); // TODO: not encoded
        }
        
        return customActionForward;
    }
    
}