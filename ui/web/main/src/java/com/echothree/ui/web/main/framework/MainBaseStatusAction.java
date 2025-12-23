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

import com.echothree.util.common.command.BaseSetStatusResult;
import com.echothree.util.common.command.CommandResult;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class MainBaseStatusAction<A extends ActionForm, R extends BaseSetStatusResult>
        extends MainBaseAction<A> {
    
    public void setupParameters(A actionForm, HttpServletRequest request) {
        // Optional, possibly nothing.
    }
    
    protected void setupDefaults(A actionForm)
            throws NamingException {
        // Optional, possibly nothing.
    }
    
    protected abstract CommandResult doStatus(A actionForm, HttpServletRequest request)
            throws NamingException;
    
    protected void setLockAttribute(HttpServletRequest request, BaseSetStatusResult result) {
        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
    }
    
    @Override
    public final ActionForward executeAction(ActionMapping mapping, A actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        
        setupParameters(actionForm, request);

        if(wasPost(request)) {
            if(isTokenValid(request, true)) {
                if(!wasCanceled(request)) {
                    var commandResult = doStatus(actionForm, request);
                    var executionResult = commandResult.getExecutionResult();
                    var result = (R)executionResult.getResult();

                    if(result != null) {
                        setLockAttribute(request, result);
                    }

                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = getFormForward(actionForm);
                    }
                }
            } else {
                forwardKey = getFormForward(actionForm);
            }
        } else {
            setupDefaults(actionForm);
            forwardKey = getFormForward(actionForm);
        }

        return getActionForward(mapping, forwardKey, actionForm, request);
    }

}
