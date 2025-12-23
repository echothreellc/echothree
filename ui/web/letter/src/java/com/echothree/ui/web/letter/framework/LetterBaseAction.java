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

package com.echothree.ui.web.letter.framework;

import com.echothree.view.client.web.struts.BaseAction;
import com.echothree.view.client.web.struts.CustomActionForward;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class LetterBaseAction<A extends ActionForm>
        extends BaseAction {
    
    protected Log log = LogFactory.getLog(this.getClass());
    
    protected LetterBaseAction() {
        super();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ActionForward actionForward = null;
        String forwardKey = null;
        
        try {
            actionForward = executeAction(mapping, (A)form, request, response);

            if(actionForward != null && ForwardConstants.FORM.equals(actionForward.getName())) {
                saveToken(request);
            }
        } catch (NamingException ne) {
            log.error(ne);
            forwardKey = ForwardConstants.ERROR_500;
        }

        if(forwardKey != null) {
            actionForward = new CustomActionForward(mapping.findForward(forwardKey));
        }

        return actionForward;
    }
    
    public String findParameter(HttpServletRequest request, String parameterName, String actionFormValue) {
        return actionFormValue == null ? request.getParameter(parameterName) : actionFormValue;
    }
    
    public abstract ActionForward executeAction(ActionMapping mapping, A form, HttpServletRequest request,
            HttpServletResponse response)
            throws Exception;
    
}
