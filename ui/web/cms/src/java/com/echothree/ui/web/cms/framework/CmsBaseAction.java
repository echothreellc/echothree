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

package com.echothree.ui.web.cms.framework;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetApplicationResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetCompanyResult;
import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.result.GetUserSessionResult;
import com.echothree.model.control.core.common.transfer.ApplicationTransfer;
import com.echothree.model.control.party.common.transfer.CompanyTransfer;
import com.echothree.model.control.user.common.transfer.UserSessionTransfer;
import com.echothree.view.client.web.struts.BaseAction;
import com.echothree.view.client.web.struts.CustomActionForward;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class CmsBaseAction<A extends ActionForm>
        extends BaseAction {
    
    protected Log log = LogFactory.getLog(this.getClass());
    private boolean applicationRequired;
    private boolean partyRequired;
    
    private void init(boolean applicationRequired, boolean partyRequired) {
        this.applicationRequired = applicationRequired;
        this.partyRequired = partyRequired;
    }
    
    protected CmsBaseAction(boolean applicationRequired, boolean partyRequired) {
        super();
        
        init(applicationRequired, partyRequired);
    }
    
    protected String getFormForward(A actionForm) {
        return ForwardConstants.FORM;
    }
    
    protected String getDisplayForward(A actionForm) {
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
    
    protected CustomActionForward getCustomActionForward(ActionMapping mapping, String forwardKey, A actionForm, HttpServletRequest request)
            throws NamingException {
        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey == null ? getDisplayForward(actionForm) : forwardKey));
        
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

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ActionForward actionForward = null;
        String forwardKey = null;
        
        if(applicationRequired ? doesApplicationExist(request) : true) {
            var commandResult = UserUtil.getHome().getUserSession(getUserVisitPK(request), null);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetUserSessionResult)executionResult.getResult();
            var userSession = result.getUserSession();
            var party = userSession == null? null: userSession.getParty();

            if(partyRequired && (party == null || (party != null && userSession.getIdentityVerifiedTime() == null))) {
                forwardKey = ForwardConstants.LOGIN;
            } else {
                if(userSession != null) {
                    request.setAttribute(AttributeConstants.USER_SESSION, userSession);
                    request.setAttribute(AttributeConstants.EMPLOYEE_AVAILABILITY, result.getEmployeeAvailability());
                }

                try {
                    actionForward = executeAction(mapping, (A)form, request, response);

                    if(actionForward != null && ForwardConstants.FORM.equals(actionForward.getName())) {
                        saveToken(request);
                    }
                } catch (NamingException ne) {
                    forwardKey = ForwardConstants.ERROR_500;
                }
            }
        } else {
            forwardKey = ForwardConstants.ERROR_404;
        }
        
        return forwardKey == null? actionForward: mapping.findForward(forwardKey);
    }
    
    public UserSessionTransfer getUserSession(HttpServletRequest request) {
        return (UserSessionTransfer)request.getAttribute(AttributeConstants.USER_SESSION);
    }
    
    public CompanyTransfer getCompany(HttpServletRequest request)
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
    
    public boolean doesApplicationExist(HttpServletRequest request)
            throws NamingException {
        return getApplication(request) != null;
    }
    
    public ApplicationTransfer getApplication(HttpServletRequest request)
            throws NamingException {
        ApplicationTransfer application = null;
        var commandForm = CoreUtil.getHome().getGetApplicationForm();
        
        commandForm.setApplicationName(request.getParameter(ParameterConstants.APPLICATION_NAME));
        commandForm.setEntityRef(request.getParameter(ParameterConstants.ENTITY_REF));
        commandForm.setUuid(request.getParameter(ParameterConstants.UUID));

        var commandResult = CoreUtil.getHome().getApplication(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetApplicationResult)executionResult.getResult();
            
            application = result.getApplication();
        }
        
        return application;
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
