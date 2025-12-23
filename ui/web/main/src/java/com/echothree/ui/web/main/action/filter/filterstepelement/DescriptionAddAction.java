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

package com.echothree.ui.web.main.action.filter.filterstepelement;

import com.echothree.control.user.filter.common.FilterUtil;
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
    path = "/Filter/FilterStepElement/DescriptionAdd",
    mappingClass = SecureActionMapping.class,
    name = "FilterStepElementDescriptionAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Filter/FilterStepElement/Description", redirect = true),
        @SproutForward(name = "Form", path = "/filter/filterstepelement/descriptionAdd.jsp")
    }
)
public class DescriptionAddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var filterKindName = request.getParameter(ParameterConstants.FILTER_KIND_NAME);
        var filterTypeName = request.getParameter(ParameterConstants.FILTER_TYPE_NAME);
        var filterName = request.getParameter(ParameterConstants.FILTER_NAME);
        var filterStepName = request.getParameter(ParameterConstants.FILTER_STEP_NAME);
        var filterStepElementName = request.getParameter(ParameterConstants.FILTER_STEP_ELEMENT_NAME);
        
        try {
            if(forwardKey == null) {
                var descriptionAddActionForm = (DescriptionAddActionForm)form;
                    
                if(wasPost(request)) {
                    var createFilterStepElementDescriptionForm = FilterUtil.getHome().getCreateFilterStepElementDescriptionForm();
                    
                    if(filterKindName == null)
                        filterKindName = descriptionAddActionForm.getFilterKindName();
                    if(filterTypeName == null)
                        filterTypeName = descriptionAddActionForm.getFilterTypeName();
                    if(filterName == null)
                        filterName = descriptionAddActionForm.getFilterName();
                    if(filterStepName == null)
                        filterStepName = descriptionAddActionForm.getFilterStepName();
                    if(filterStepElementName == null)
                        filterStepElementName = descriptionAddActionForm.getFilterStepElementName();
                    
                    createFilterStepElementDescriptionForm.setFilterKindName(filterKindName);
                    createFilterStepElementDescriptionForm.setFilterTypeName(filterTypeName);
                    createFilterStepElementDescriptionForm.setFilterName(filterName);
                    createFilterStepElementDescriptionForm.setFilterStepName(filterStepName);
                    createFilterStepElementDescriptionForm.setFilterStepElementName(filterStepElementName);
                    createFilterStepElementDescriptionForm.setLanguageIsoName(descriptionAddActionForm.getLanguageChoice());
                    createFilterStepElementDescriptionForm.setDescription(descriptionAddActionForm.getDescription());

                    var commandResult = FilterUtil.getHome().createFilterStepElementDescription(getUserVisitPK(request), createFilterStepElementDescriptionForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    descriptionAddActionForm.setFilterKindName(filterKindName);
                    descriptionAddActionForm.setFilterTypeName(filterTypeName);
                    descriptionAddActionForm.setFilterName(filterName);
                    descriptionAddActionForm.setFilterStepName(filterStepName);
                    descriptionAddActionForm.setFilterStepElementName(filterStepElementName);
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.FILTER_KIND_NAME, filterKindName);
            request.setAttribute(AttributeConstants.FILTER_TYPE_NAME, filterTypeName);
            request.setAttribute(AttributeConstants.FILTER_NAME, filterName);
            request.setAttribute(AttributeConstants.FILTER_STEP_NAME, filterStepName);
            request.setAttribute(AttributeConstants.FILTER_STEP_ELEMENT_NAME, filterStepElementName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(5);
            
            parameters.put(ParameterConstants.FILTER_KIND_NAME, filterKindName);
            parameters.put(ParameterConstants.FILTER_TYPE_NAME, filterTypeName);
            parameters.put(ParameterConstants.FILTER_NAME, filterName);
            parameters.put(ParameterConstants.FILTER_STEP_NAME, filterStepName);
            parameters.put(ParameterConstants.FILTER_STEP_ELEMENT_NAME, filterStepElementName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}