// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.control.user.party.common.result.EditPartyEntityTypeResult;
import com.echothree.control.user.party.common.result.GetPartyEntityTypeResult;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.party.common.transfer.PartyEntityTypeTransfer;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class MainBaseDeleteAction<A extends MainBaseDeleteActionForm>
        extends MainBaseAction<A> {
    
    protected String getComponentVendorName() {
        return ComponentVendors.ECHO_THREE.name();
    }
    
    protected abstract String getEntityTypeName();
    
    protected abstract void setupParameters(final A actionForm, final HttpServletRequest request);
    
    protected abstract CommandResult doDelete(final A actionForm, final HttpServletRequest request)
            throws NamingException;
    
    @Override
    protected void setupForwardParameters(final A actionForm, final Map<String, String> parameters) {
        // Optional, possibly nothing.
    }
    
    protected PartyEntityTypeTransfer setupPartyEntityType(final A actionForm, final HttpServletRequest request)
            throws NamingException {
        var commandForm = PartyUtil.getHome().getGetPartyEntityTypeForm();
        
        commandForm.setComponentVendorName(getComponentVendorName());
        commandForm.setEntityTypeName(getEntityTypeName());

        var commandResult = PartyUtil.getHome().getPartyEntityType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPartyEntityTypeResult)executionResult.getResult();
        var partyEntityType = result.getPartyEntityType();
        
        request.setAttribute(AttributeConstants.PARTY_ENTITY_TYPE, partyEntityType);
        
        actionForm.setConfirmDelete(partyEntityType.getConfirmDelete());
        
        return partyEntityType;
    }
    
    protected void clearConfirmDelete(final HttpServletRequest request)
            throws NamingException {
        var commandForm = PartyUtil.getHome().getEditPartyEntityTypeForm();
        var spec = PartyUtil.getHome().getPartyEntityTypeSpec();

        commandForm.setSpec(spec);
        commandForm.setEditMode(EditMode.LOCK);

        spec.setComponentVendorName(getComponentVendorName());
        spec.setEntityTypeName(getEntityTypeName());

        var commandResult = PartyUtil.getHome().editPartyEntityType(getUserVisitPK(request), commandForm);
        
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (EditPartyEntityTypeResult)executionResult.getResult();

            if(result != null) {
                var edit = result.getEdit();

                if(edit != null) {
                    edit.setConfirmDelete(String.valueOf(false));

                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);

                    PartyUtil.getHome().editPartyEntityType(getUserVisitPK(request), commandForm);
                }
            }
        }
    }
    
    @Override
    public final ActionForward executeAction(final ActionMapping mapping, final A actionForm, final HttpServletRequest request,
            final HttpServletResponse response)
            throws Exception {
        String forwardKey = null;

        setupParameters(actionForm, request);

        if(wasPost(request)) {
            if(isTokenValid(request, true)) {
                if(!wasCanceled(request)) {
                    var commandResult = doDelete(actionForm, request);

                    if(commandResult.hasErrors()) {
                        setupPartyEntityType(actionForm, request);
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = getFormForward(actionForm);
                    } else {
                        var confirmDelete = actionForm.getConfirmDelete();

                        if(confirmDelete == null || (confirmDelete != null && !confirmDelete)) {
                            clearConfirmDelete(request);
                        }
                    }
                }
            } else {
                forwardKey = getFormForward(actionForm);
            }
        } else {
            setupPartyEntityType(actionForm, request);

            if(actionForm.getConfirmDelete()) {
                forwardKey = getFormForward(actionForm);
            } else {
                var commandResult = doDelete(actionForm, request);

                if(commandResult.hasErrors()) {
                    setCommandResultAttribute(request, commandResult);
                    forwardKey = getFormForward(actionForm);
                }
            }
        }

        return getActionForward(mapping, forwardKey, actionForm, request);
    }

}
