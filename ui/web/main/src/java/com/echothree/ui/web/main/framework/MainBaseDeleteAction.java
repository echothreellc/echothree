// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.PartyEntityTypeEdit;
import com.echothree.control.user.core.common.form.EditPartyEntityTypeForm;
import com.echothree.control.user.core.common.form.GetPartyEntityTypeForm;
import com.echothree.control.user.core.common.result.EditPartyEntityTypeResult;
import com.echothree.control.user.core.common.result.GetPartyEntityTypeResult;
import com.echothree.control.user.core.common.spec.PartyEntityTypeSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.transfer.PartyEntityTypeTransfer;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class MainBaseDeleteAction<A extends MainBaseDeleteActionForm>
        extends MainBaseAction<A> {
    
    protected String getComponentVendorName() {
        return ComponentVendors.ECHOTHREE.name();
    }
    
    protected abstract String getEntityTypeName();
    
    protected abstract void setupParameters(A actionForm, HttpServletRequest request);
    
    protected abstract CommandResult doDelete(A actionForm, HttpServletRequest request)
            throws NamingException;
    
    @Override
    protected void setupForwardParameters(A actionForm, Map<String, String> parameters) {
        // Optional, possibly nothing.
    }
    
    protected PartyEntityTypeTransfer setupPartyEntityType(A actionForm, HttpServletRequest request)
            throws NamingException {
        GetPartyEntityTypeForm commandForm = CoreUtil.getHome().getGetPartyEntityTypeForm();
        
        commandForm.setComponentVendorName(getComponentVendorName());
        commandForm.setEntityTypeName(getEntityTypeName());
        
        CommandResult commandResult = CoreUtil.getHome().getPartyEntityType(getUserVisitPK(request), commandForm);
        ExecutionResult executionResult = commandResult.getExecutionResult();
        GetPartyEntityTypeResult result = (GetPartyEntityTypeResult)executionResult.getResult();
        PartyEntityTypeTransfer partyEntityType = result.getPartyEntityType();
        
        request.setAttribute(AttributeConstants.PARTY_ENTITY_TYPE, partyEntityType);
        
        actionForm.setConfirmDelete(partyEntityType.getConfirmDelete());
        
        return partyEntityType;
    }
    
    protected void clearConfirmDelete(HttpServletRequest request)
            throws NamingException {
        EditPartyEntityTypeForm commandForm = CoreUtil.getHome().getEditPartyEntityTypeForm();
        PartyEntityTypeSpec spec = CoreUtil.getHome().getPartyEntityTypeSpec();

        commandForm.setSpec(spec);
        commandForm.setEditMode(EditMode.LOCK);

        spec.setComponentVendorName(getComponentVendorName());
        spec.setEntityTypeName(getEntityTypeName());

        CommandResult commandResult = CoreUtil.getHome().editPartyEntityType(getUserVisitPK(request), commandForm);
        
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditPartyEntityTypeResult result = (EditPartyEntityTypeResult)executionResult.getResult();

            if(result != null) {
                PartyEntityTypeEdit edit = result.getEdit();

                if(edit != null) {
                    edit.setConfirmDelete(Boolean.FALSE.toString());

                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    commandResult = CoreUtil.getHome().editPartyEntityType(getUserVisitPK(request), commandForm);
                }
            }
        }
    }
    
    @Override
    public final ActionForward executeAction(ActionMapping mapping, A actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;

        setupParameters(actionForm, request);

        if(wasPost(request)) {
            if(!wasCancelled(request)) {
                CommandResult commandResult = doDelete(actionForm, request);

                if(commandResult.hasErrors()) {
                    setupPartyEntityType(actionForm, request);
                    setCommandResultAttribute(request, commandResult);
                    forwardKey = getFormForward(actionForm);
                } else {
                    Boolean confirmDelete = actionForm.getConfirmDelete();

                    if(confirmDelete == null || (confirmDelete != null && !confirmDelete)) {
                        clearConfirmDelete(request);
                    }
                }
            }
        } else {
            setupPartyEntityType(actionForm, request);

            if(actionForm.getConfirmDelete()) {
                forwardKey = getFormForward(actionForm);
            } else {
                CommandResult commandResult = doDelete(actionForm, request);

                if(commandResult.hasErrors()) {
                    setCommandResultAttribute(request, commandResult);
                    forwardKey = getFormForward(actionForm);
                }
            }
        }

        return getActionForward(mapping, forwardKey, actionForm, request);
    }

}
