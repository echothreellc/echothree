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

package com.echothree.ui.web.main.framework;

import com.echothree.control.user.user.remote.result.GetUserSessionResult;
import com.echothree.model.control.user.remote.transfer.UserSessionTransfer;
import com.echothree.model.control.workflow.remote.transfer.WorkflowEntityStatusTransfer;
import com.google.common.net.MediaType;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class MainBaseGraphQlAction
        extends MainBaseAction<ActionForm> {

    protected static final int DEFAULT_BUFFER_SIZE = 4096;
    
    protected static final MediaType JSON = MediaType.JSON_UTF_8.withoutParameters();
    protected static final MediaType GRAPHQL_UTF_8 = MediaType.parse("application/graphql;charset=utf-8");
    protected static final MediaType GRAPHQL = GRAPHQL_UTF_8.withoutParameters();

    public MainBaseGraphQlAction() {
        super(false, false);
    }
    
    protected int getBufferSize() {
        return DEFAULT_BUFFER_SIZE;
    }
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ActionForward actionForward = null;
        String forwardKey = null;
        
        try {
            actionForward = executeAction(mapping, form, request, response);
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }

        if(forwardKey != null) {
            GetUserSessionResult getuserSessionResult = getUserSessionResult(request);

            if(getuserSessionResult != null) {
                UserSessionTransfer userSession = getuserSessionResult.getUserSession();
                WorkflowEntityStatusTransfer employeeAvailability = getuserSessionResult.getEmployeeAvailability();
                
                if(userSession != null) {
                    request.setAttribute(AttributeConstants.USER_SESSION, userSession);
                }
                
                if(employeeAvailability != null) {
                    request.setAttribute(AttributeConstants.EMPLOYEE_AVAILABILITY, employeeAvailability);
                }
            }
            
            actionForward = new ActionForward(mapping.findForward(forwardKey));
        }

        return actionForward;
    }
}
