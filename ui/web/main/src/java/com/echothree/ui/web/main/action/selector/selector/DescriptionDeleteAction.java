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
    path = "/Selector/Selector/DescriptionDelete",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Selector/Selector/Description", redirect = true)
    }
)
public class DescriptionDeleteAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey;
        var selectorKindName = request.getParameter(ParameterConstants.SELECTOR_KIND_NAME);
        var selectorTypeName = request.getParameter(ParameterConstants.SELECTOR_TYPE_NAME);
        var selectorName = request.getParameter(ParameterConstants.SELECTOR_NAME);
        
        try {
            var languageIsoName = request.getParameter(ParameterConstants.LANGUAGE_ISO_NAME);
            var deleteSelectorDescriptionForm = SelectorUtil.getHome().getDeleteSelectorDescriptionForm();
            
            deleteSelectorDescriptionForm.setSelectorKindName(selectorKindName);
            deleteSelectorDescriptionForm.setSelectorTypeName(selectorTypeName);
            deleteSelectorDescriptionForm.setSelectorName(selectorName);
            deleteSelectorDescriptionForm.setLanguageIsoName(languageIsoName);
            
            SelectorUtil.getHome().deleteSelectorDescription(getUserVisitPK(request), deleteSelectorDescriptionForm);
            
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.SELECTOR_KIND_NAME, selectorKindName);
            parameters.put(ParameterConstants.SELECTOR_TYPE_NAME, selectorTypeName);
            parameters.put(ParameterConstants.SELECTOR_NAME, selectorName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}