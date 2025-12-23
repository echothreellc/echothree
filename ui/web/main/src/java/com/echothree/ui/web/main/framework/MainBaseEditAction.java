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

import com.echothree.util.common.command.BaseEditResult;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.form.BaseEdit;
import com.echothree.util.common.form.BaseEditForm;
import com.echothree.util.common.form.BaseSpec;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class MainBaseEditAction<A extends ActionForm, S extends BaseSpec, E extends BaseEdit, F extends BaseEditForm, R extends BaseEditResult>
        extends MainBaseAction<A> {
    
    protected abstract S getSpec(HttpServletRequest request, A actionForm)
            throws NamingException;
    
    protected abstract E getEdit(HttpServletRequest request, A actionForm)
            throws NamingException;
    
    protected abstract F getForm()
            throws NamingException;
    
    protected abstract void setupActionForm(HttpServletRequest request, A actionForm, R result, S spec, E edit)
            throws NamingException;
    
    protected abstract CommandResult doEdit(HttpServletRequest request, F commandForm)
            throws Exception;
    
    @Override
    protected void setupForwardParameters(A actionForm, Map<String, String> parameters) {
        // Optional, possibly nothing.
    }
    
    @Override
    protected void setupTransfer(A actionForm, HttpServletRequest request)
            throws NamingException {
        // Optional, possibly nothing.
        // Override this if you need to get a TO that is different than the one returned in the *EditResult.
    }
    
    protected void setLockAttribute(HttpServletRequest request, BaseEditResult result) {
        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
    }
    
    // This is a hook to test for specific types of errors that may occur that shuold keep the form
    // from being displayed on the page.
    protected boolean displayForm(ExecutionResult executionResult) {
        return true;
    }
    
    protected void setDisplayFormAttribute(HttpServletRequest request, ExecutionResult executionResult) {
        request.setAttribute(AttributeConstants.DISPLAY_FORM, executionResult == null ? false : !executionResult.getHasLockErrors() && displayForm(executionResult));
    }
    
    private void setFormAttributes(HttpServletRequest request, CommandResult commandResult) {
        setCommandResultAttribute(request, commandResult);
        setDisplayFormAttribute(request, commandResult.getExecutionResult());
    }
    
    protected void setupTransferForForm(HttpServletRequest request, A actionForm, R result)
            throws NamingException {
        // Optional, possibly nothing.
        // Override this if you want to copy the TO from the *EditResult to the request.
    }

    protected String handleUpdateOrAbandonResult(A actionForm, HttpServletRequest request, boolean wasCanceled, CommandResult commandResult)
            throws NamingException {
        String forwardKey = null;

        if(commandResult.hasErrors() && !wasCanceled) {
            var executionResult = commandResult.getExecutionResult();

            if(executionResult != null) {
                var result = (R)executionResult.getResult();

                setLockAttribute(request, (BaseEditResult)executionResult.getResult());
                setupTransferForForm(request, actionForm, result);
            }

            setFormAttributes(request, commandResult);
            forwardKey = getFormForward(actionForm);
        }
        
        return forwardKey;
    }
    
    @Override
    public final ActionForward executeAction(ActionMapping mapping, A actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = getForm();
        var spec = getSpec(request, actionForm);
        
        commandForm.setSpec(spec);

        if(wasPost(request)) {
            if(isTokenValid(request, true)) {
                var wasCanceled = wasCanceled(request);

                if(wasCanceled) {
                    commandForm.setEditMode(EditMode.ABANDON);
                } else {
                    var edit = getEdit(request, actionForm);

                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);
                }

                forwardKey = handleUpdateOrAbandonResult(actionForm, request, wasCanceled, doEdit(request, commandForm));
            } else {
                forwardKey = getFormForward(actionForm);
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = doEdit(request, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (R)executionResult.getResult();

            if(result != null) {
                var edit = (E)result.getEdit();

                setLockAttribute(request, result);

                if(edit != null) {
                    setupActionForm(request, actionForm, result, spec, edit);
                }

                setupTransferForForm(request, actionForm, result);
            }

            setFormAttributes(request, commandResult);
            forwardKey = getFormForward(actionForm);
        }

        return getActionForward(mapping, forwardKey, actionForm, request);
    }

}
