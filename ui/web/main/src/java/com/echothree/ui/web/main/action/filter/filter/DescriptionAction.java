// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.web.main.action.filter.filter;

import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.common.form.GetFilterDescriptionsForm;
import com.echothree.control.user.filter.common.result.GetFilterDescriptionsResult;
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
    path = "/Filter/Filter/Description",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/filter/filter/description.jsp")
    }
)
public class DescriptionAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        
        try {
            String filterKindName = request.getParameter(ParameterConstants.FILTER_KIND_NAME);
            String filterTypeName = request.getParameter(ParameterConstants.FILTER_TYPE_NAME);
            String filterName = request.getParameter(ParameterConstants.FILTER_NAME);
            GetFilterDescriptionsForm getFilterDescriptionsForm = FilterUtil.getHome().getGetFilterDescriptionsForm();
            
            getFilterDescriptionsForm.setFilterKindName(filterKindName);
            getFilterDescriptionsForm.setFilterTypeName(filterTypeName);
            getFilterDescriptionsForm.setFilterName(filterName);
            
            CommandResult commandResult = FilterUtil.getHome().getFilterDescriptions(getUserVisitPK(request), getFilterDescriptionsForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetFilterDescriptionsResult getFilterDescriptionsResult = (GetFilterDescriptionsResult)executionResult.getResult();
            
            request.setAttribute("filterKind", getFilterDescriptionsResult.getFilterKind());
            request.setAttribute("filterType", getFilterDescriptionsResult.getFilterType());
            request.setAttribute("filter", getFilterDescriptionsResult.getFilter());
            request.setAttribute("filterDescriptions", getFilterDescriptionsResult.getFilterDescriptions());
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}