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

package com.echothree.view.client.web.struts;

import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.result.CheckSecurityRolesResult;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.WebConstants;
import com.echothree.view.client.web.taglib.TagConstants;
import com.google.common.base.Splitter;
import java.util.HashSet;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.Action;
import org.apache.struts.taglib.html.Constants;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

public abstract class BaseAction
        extends Action {
    
    public static UserVisitPK getUserVisitPK(HttpServletRequest request) {
        var httpSession = request.getSession(true);
        
        return (UserVisitPK)httpSession.getAttribute(WebConstants.Session_USER_VISIT);
    }
    
    protected CommandResult setCommandResultAttribute(HttpServletRequest request, CommandResult commandResult) {
        request.setAttribute(TagConstants.CommandResultName, commandResult);
        
        return commandResult;
    }
    
    public static boolean wasOptions(HttpServletRequest request) {
        return request.getMethod().equals(WebConstants.Method_OPTIONS);
    }
    
    public static boolean wasGet(HttpServletRequest request) {
        return request.getMethod().equals(WebConstants.Method_GET);
    }
    
    public static boolean wasPost(HttpServletRequest request) {
        return request.getMethod().equals(WebConstants.Method_POST);
    }
    
    public static boolean wasCanceled(HttpServletRequest request) {
        var submitButtonValue = request.getParameter(WebConstants.Parameter_SUBMIT_BUTTON);
        var wasCanceled = false;
        
        if(submitButtonValue != null) {
            if(submitButtonValue.equals(Constants.CANCEL_PROPERTY)) {
                wasCanceled = true;
            }
        }
        
        return wasCanceled;
    }
    
    public void setupDtAttributes(HttpServletRequest request, String dtIdAttribute) {
        var paramEncoder = new ParamEncoder(dtIdAttribute);
        
        request.setAttribute(WebConstants.Attribute_DT_ID_ATTRIBUTE, dtIdAttribute);
        request.setAttribute(WebConstants.Attribute_DT_SORT_PARAMETER, request.getParameter(paramEncoder.encodeParameterName(TableTagParameters.PARAMETER_SORT)));
        request.setAttribute(WebConstants.Attribute_DT_PAGE_PARAMETER, request.getParameter(paramEncoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
        request.setAttribute(WebConstants.Attribute_DT_ORDER_PARAMETER, request.getParameter(paramEncoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER)));
    }
    
    public void setupDtParameters(Map<String, String> parameters, BaseActionForm actionForm) {
        var paramEncoder = new ParamEncoder(actionForm.getDtIdAttribute());
        var dtSortParameter = actionForm.getDtSortParameter();
        var dtPageParameter = actionForm.getDtPageParameter();
        var dtOrderParameter = actionForm.getDtOrderParameter();
        
        if(dtSortParameter != null && dtSortParameter.length() > 0) {
            parameters.put(paramEncoder.encodeParameterName(TableTagParameters.PARAMETER_SORT), dtSortParameter);
        }
        
        if(dtPageParameter != null && dtPageParameter.length() > 0) {
            parameters.put(paramEncoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), dtPageParameter);
        }
        
        if(dtOrderParameter != null && dtOrderParameter.length() > 0) {
            parameters.put(paramEncoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER), dtOrderParameter);
        }
    }

    public void checkSecurityRoles(HttpServletRequest request, String securityRoles)
            throws NamingException {
        String newSecurityRoles = null;
        var pageSecurityRoles = (HashSet<String>)request.getAttribute(WebConstants.Attribute_SECURITY_ROLES);
        var commandForm = SecurityUtil.getHome().getCheckSecurityRolesForm();

        commandForm.setSecurityRoles(securityRoles);

        var commandResult = SecurityUtil.getHome().checkSecurityRoles(getUserVisitPK(request), commandForm);

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var checkSecurityRolesResult = (CheckSecurityRolesResult)executionResult.getResult();
            newSecurityRoles = checkSecurityRolesResult.getSecurityRoles();
        }

        if(newSecurityRoles != null) {
            var newSecurityRolesArray = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(newSecurityRoles).toArray(new String[0]);
            var newSecurityRolesArrayLength = newSecurityRolesArray.length;

            if(pageSecurityRoles == null) {
                pageSecurityRoles = new HashSet<>(newSecurityRolesArrayLength);
            }

            for(var i = 0; i < newSecurityRolesArrayLength; i++) {
                pageSecurityRoles.add(newSecurityRolesArray[i]);
            }
        }

        request.setAttribute(WebConstants.Attribute_SECURITY_ROLES, pageSecurityRoles);
    }

    public boolean hasSecurityRole(HttpServletRequest request, String securityRoles) {
        var pageSecurityRoles = (HashSet<String>)request.getAttribute(WebConstants.Attribute_SECURITY_ROLES);
        var securityRoleFound = false;

        if(pageSecurityRoles != null && securityRoles != null) {
            var securityRolesToCheck = Splitter.on(':').trimResults().omitEmptyStrings().splitToList(securityRoles).toArray(new String[0]);
            var securityRolesToCheckLength = securityRolesToCheck.length;

            for(var i = 0; i < securityRolesToCheckLength; i++) {
                if(pageSecurityRoles.contains(securityRolesToCheck[i])) {
                    securityRoleFound = true;
                    break;
                }
            }
        }

        return securityRoleFound;
    }
    
}
