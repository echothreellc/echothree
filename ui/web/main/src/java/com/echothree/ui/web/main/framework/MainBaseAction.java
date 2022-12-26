// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.framework;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.GetCompanyForm;
import com.echothree.control.user.party.common.result.GetCompanyResult;
import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.form.GetUserSessionForm;
import com.echothree.control.user.user.common.result.GetUserSessionResult;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.CompanyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.user.common.transfer.UserSessionTransfer;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.WebConstants;
import com.echothree.view.client.web.struts.BaseAction;
import com.echothree.view.client.web.struts.CustomActionForward;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class MainBaseAction<A extends ActionForm>
        extends BaseAction {
    
    protected Log log = LogFactory.getLog(this.getClass());
    private boolean partyRequired;
    private boolean forceChangeCheck;
    
    private void init(boolean partyRequired, boolean forceChangeCheck) {
        this.partyRequired = partyRequired;
        this.forceChangeCheck = forceChangeCheck;
    }
    
    protected MainBaseAction() {
        super();
        
        init(true, true);
    }
    
    protected MainBaseAction(boolean partyRequired, boolean forceChangeCheck) {
        super();
        
        init(partyRequired, forceChangeCheck);
    }
    
    protected String getFormForward(A actionForm)
            throws NamingException {
        return ForwardConstants.FORM;
    }

    protected String getDisplayForward(A actionForm, HttpServletRequest request)
            throws NamingException {
        return ForwardConstants.DISPLAY;
    }

    protected void setupForwardParameters(A actionForm, Map<String, String> parameters)
            throws NamingException {
        // Optional, possibly nothing.
    }
    
    protected void setupTransfer(A actionForm, HttpServletRequest request)
            throws NamingException {
        // Optional, possibly nothing.
    }
    
    protected ActionForward getActionForward(ActionMapping mapping, String forwardKey, A actionForm, HttpServletRequest request)
            throws NamingException {
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey == null ? getDisplayForward(actionForm, request) : forwardKey));
        
        if(forwardKey == null || forwardKey.equals(ForwardConstants.CANCEL)) {
            // Sending them to 'Display'
            Map<String, String> parameters = new HashMap<>();

            setupForwardParameters(actionForm, parameters);
            customActionForward.setParameters(parameters);
        } else {
            // Sending them to 'Form'
            setupTransfer(actionForm, request);
        }

        return customActionForward;
    }
    
    protected GetUserSessionResult getUserSessionResult(HttpServletRequest request)
            throws NamingException {
        GetUserSessionResult result = null;
        GetUserSessionForm commandForm = UserUtil.getHome().getGetUserSessionForm();
        
        Set<String> options = new HashSet<>();
        options.add(PartyOptions.PartyIncludeUserLogin);
        commandForm.setOptions(options);
        
        CommandResult commandResult = UserUtil.getHome().getUserSession(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            
            result = (GetUserSessionResult)executionResult.getResult();
        }
        
        return result;
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        GetUserSessionResult getuserSessionResult = getUserSessionResult(request);
        UserSessionTransfer userSession = getuserSessionResult == null ? null : getuserSessionResult.getUserSession();
        PartyTransfer party = userSession == null? null: userSession.getParty();
        ActionForward actionForward = null;
        String forwardKey = null;
        String returnUrl = null;
        
        if(partyRequired && (party == null || (party != null && userSession.getPasswordVerifiedTime() == null))) {
            if(request.getMethod().equals(WebConstants.Method_GET)) {
                String servletPath = request.getServletPath();
                String pathInfo = request.getPathInfo();

                StringBuilder returnUrlBuilder = new StringBuilder(servletPath).append(pathInfo);
                String queryString = request.getQueryString();

                if(queryString != null) {
                    returnUrlBuilder.append('?').append(queryString);
                }

                returnUrl = returnUrlBuilder.toString();
            }

            forwardKey = ForwardConstants.LOGIN;
        } else if(forceChangeCheck && (party != null && party.getUserLogin().getForceChange())) {
            forwardKey = ForwardConstants.PASSWORD;
        } else {
            if(userSession != null) {
                request.setAttribute(AttributeConstants.USER_SESSION, userSession);
                request.setAttribute(AttributeConstants.EMPLOYEE_AVAILABILITY, getuserSessionResult.getEmployeeAvailability());
            }
            
            try {
                actionForward = executeAction(mapping, (A)form, request, response);
            } catch (NamingException ne) {
                forwardKey = ForwardConstants.ERROR_500;
            }
        }

        if(forwardKey != null) {
            CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));

            if(returnUrl != null) {
                Map<String, String> parameters = new HashMap<>(2);

                parameters.put(ParameterConstants.RETURN_URL, returnUrl);
                customActionForward.setParameters(parameters);
            }

            actionForward = customActionForward;
        }

        return actionForward;
    }
    
    public UserSessionTransfer getUserSession(HttpServletRequest request) {
        return (UserSessionTransfer)request.getAttribute(AttributeConstants.USER_SESSION);
    }
    
    public CompanyTransfer getCompany(HttpServletRequest request)
            throws NamingException {
        GetCompanyForm commandForm = PartyUtil.getHome().getGetCompanyForm();
        UserSessionTransfer userSession = (UserSessionTransfer)request.getAttribute(AttributeConstants.USER_SESSION);
        String partyName = userSession.getPartyRelationship().getFromParty().getPartyName();
        
        commandForm.setPartyName(partyName);
        
        CommandResult commandResult = PartyUtil.getHome().getCompany(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetCompanyResult result = (GetCompanyResult)executionResult.getResult();
        
        return result.getCompany();
    }
    
    public String getCompanyName(HttpServletRequest request)
            throws NamingException {
        return getCompany(request).getCompanyName();
    }
    
    public String findParameter(HttpServletRequest request, String parameterName, String actionFormValue) {
        return actionFormValue == null ? request.getParameter(parameterName) : actionFormValue;
    }
    
    public abstract ActionForward executeAction(ActionMapping mapping, A form, HttpServletRequest request,
            HttpServletResponse response)
            throws Exception;
    
}
