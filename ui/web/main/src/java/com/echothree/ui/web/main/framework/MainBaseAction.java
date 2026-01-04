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

package com.echothree.ui.web.main.framework;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetCompanyResult;
import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.result.GetUserSessionResult;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.CompanyTransfer;
import com.echothree.model.control.user.common.transfer.UserSessionTransfer;
import com.echothree.view.client.web.WebConstants;
import com.echothree.view.client.web.struts.BaseAction;
import com.echothree.view.client.web.struts.CustomActionForward;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    
    private void init(final boolean partyRequired, final boolean forceChangeCheck) {
        this.partyRequired = partyRequired;
        this.forceChangeCheck = forceChangeCheck;
    }
    
    protected MainBaseAction() {
        super();
        
        init(true, true);
    }
    
    protected MainBaseAction(final boolean partyRequired, final boolean forceChangeCheck) {
        super();
        
        init(partyRequired, forceChangeCheck);
    }
    
    protected String getFormForward(final A actionForm)
            throws NamingException {
        return ForwardConstants.FORM;
    }

    protected String getDisplayForward(final A actionForm, final HttpServletRequest request)
            throws NamingException {
        return ForwardConstants.DISPLAY;
    }

    protected void setupForwardParameters(final A actionForm, final Map<String, String> parameters)
            throws NamingException {
        // Optional, possibly nothing.
    }
    
    protected void setupTransfer(final A actionForm, final HttpServletRequest request)
            throws NamingException {
        // Optional, possibly nothing.
    }
    
    protected ActionForward getActionForward(final ActionMapping mapping, final String forwardKey, final A actionForm,
            final HttpServletRequest request)
            throws NamingException {
        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey == null ? getDisplayForward(actionForm, request) : forwardKey));
        
        if(forwardKey == null || forwardKey.equals(ForwardConstants.CANCEL)) {
            // Sending them to 'Display'
            var parameters = new HashMap<String, String>();

            setupForwardParameters(actionForm, parameters);
            customActionForward.setParameters(parameters);
        } else {
            // Sending them to 'Form'
            setupTransfer(actionForm, request);
        }

        return customActionForward;
    }
    
    protected GetUserSessionResult getUserSessionResult(final HttpServletRequest request)
            throws NamingException {
        GetUserSessionResult result = null;
        var commandForm = UserUtil.getHome().getGetUserSessionForm();

        var options = new HashSet<String>();
        options.add(PartyOptions.PartyIncludeUserLogin);
        commandForm.setOptions(options);

        var commandResult = UserUtil.getHome().getUserSession(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            
            result = (GetUserSessionResult)executionResult.getResult();
        }
        
        return result;
    }

    @Override
    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response)
            throws Exception {
        var getuserSessionResult = getUserSessionResult(request);
        var userSession = getuserSessionResult == null ? null : getuserSessionResult.getUserSession();
        var party = userSession == null ? null: userSession.getParty();
        ActionForward actionForward = null;
        String forwardKey = null;
        String returnUrl = null;
        
        if(partyRequired && (party == null || (party != null && userSession.getIdentityVerifiedTime() == null))) {
            if(request.getMethod().equals(WebConstants.Method_GET)) {
                var servletPath = request.getServletPath();
                var pathInfo = request.getPathInfo();

                var returnUrlBuilder = new StringBuilder(servletPath).append(pathInfo);
                var queryString = request.getQueryString();

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

                if(actionForward != null && getFormForward((A)form).equals(actionForward.getName())) {
                    saveToken(request);
                }
            } catch (NamingException ne) {
                forwardKey = ForwardConstants.ERROR_500;
            }
        }

        if(forwardKey != null) {
            var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));

            if(returnUrl != null) {
                var parameters = new HashMap<String, String>(2);

                parameters.put(ParameterConstants.RETURN_URL, returnUrl);
                customActionForward.setParameters(parameters);
            }

            actionForward = customActionForward;
        }

        return actionForward;
    }
    
    public UserSessionTransfer getUserSession(final HttpServletRequest request) {
        return (UserSessionTransfer)request.getAttribute(AttributeConstants.USER_SESSION);
    }
    
    public CompanyTransfer getCompany(final HttpServletRequest request)
            throws NamingException {
        var commandForm = PartyUtil.getHome().getGetCompanyForm();
        var userSession = (UserSessionTransfer)request.getAttribute(AttributeConstants.USER_SESSION);
        var partyName = userSession.getPartyRelationship().getFromParty().getPartyName();
        
        commandForm.setPartyName(partyName);

        var commandResult = PartyUtil.getHome().getCompany(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetCompanyResult)executionResult.getResult();
        
        return result.getCompany();
    }
    
    public String getCompanyName(final HttpServletRequest request)
            throws NamingException {
        return getCompany(request).getCompanyName();
    }
    
    public String findParameter(final HttpServletRequest request, final String parameterName, final String actionFormValue) {
        return actionFormValue == null ? request.getParameter(parameterName) : actionFormValue;
    }
    
    public abstract ActionForward executeAction(final ActionMapping mapping, final A form, final HttpServletRequest request,
            final HttpServletResponse response)
            throws Exception;
    
}
