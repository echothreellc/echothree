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

package com.echothree.ui.web.main.action.configuration.partytypepasswordstringpolicy;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.EditPartyTypePasswordStringPolicyResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.EditMode;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Configuration/PartyTypePasswordStringPolicy/Edit",
    mappingClass = SecureActionMapping.class,
    name = "PartyTypePasswordStringPolicyEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/PartyType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/partytypepasswordstringpolicy/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var partyTypeName = request.getParameter(ParameterConstants.PARTY_TYPE_NAME);
        var actionForm = (EditActionForm)form;
        var commandForm = PartyUtil.getHome().getEditPartyTypePasswordStringPolicyForm();
        var spec = PartyUtil.getHome().getPartyTypeSpec();
        
        if(partyTypeName == null)
            partyTypeName = actionForm.getPartyTypeName();
        
        commandForm.setSpec(spec);
        spec.setPartyTypeName(partyTypeName);
        
        if(wasPost(request)) {
            var wasCanceled = wasCanceled(request);
            
            if(wasCanceled) {
                commandForm.setEditMode(EditMode.ABANDON);
            } else {
                var edit = PartyUtil.getHome().getPartyTypePasswordStringPolicyEdit();

                commandForm.setEditMode(EditMode.UPDATE);
                commandForm.setEdit(edit);

                edit.setForceChangeAfterCreate(actionForm.getForceChangeAfterCreate().toString());
                edit.setForceChangeAfterReset(actionForm.getForceChangeAfterReset().toString());
                edit.setAllowChange(actionForm.getAllowChange().toString());
                edit.setPasswordHistory(actionForm.getPasswordHistory());
                edit.setMinimumPasswordLifetime(actionForm.getMinimumPasswordLifetime());
                edit.setMinimumPasswordLifetimeUnitOfMeasureTypeName(actionForm.getMinimumPasswordLifetimeUnitOfMeasureTypeChoice());
                edit.setMaximumPasswordLifetime(actionForm.getMaximumPasswordLifetime());
                edit.setMaximumPasswordLifetimeUnitOfMeasureTypeName(actionForm.getMaximumPasswordLifetimeUnitOfMeasureTypeChoice());
                edit.setExpirationWarningTime(actionForm.getExpirationWarningTime());
                edit.setExpirationWarningTimeUnitOfMeasureTypeName(actionForm.getExpirationWarningTimeUnitOfMeasureTypeChoice());
                edit.setExpiredLoginsPermitted(actionForm.getExpiredLoginsPermitted());
                edit.setMinimumLength(actionForm.getMinimumLength());
                edit.setMaximumLength(actionForm.getMaximumLength());
                edit.setRequiredDigitCount(actionForm.getRequiredDigitCount());
                edit.setRequiredLetterCount(actionForm.getRequiredLetterCount());
                edit.setRequiredUpperCaseCount(actionForm.getRequiredUpperCaseCount());
                edit.setRequiredLowerCaseCount(actionForm.getRequiredLowerCaseCount());
                edit.setMaximumRepeated(actionForm.getMaximumRepeated());
                edit.setMinimumCharacterTypes(actionForm.getMinimumCharacterTypes());
            }

            var commandResult = PartyUtil.getHome().editPartyTypePasswordStringPolicy(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors() && !wasCanceled) {
                var executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    var result = (EditPartyTypePasswordStringPolicyResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PartyUtil.getHome().editPartyTypePasswordStringPolicy(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (EditPartyTypePasswordStringPolicyResult)executionResult.getResult();
            
            if(result != null) {
                var edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setPartyTypeName(partyTypeName);
                    actionForm.setForceChangeAfterCreate(Boolean.valueOf(edit.getForceChangeAfterCreate()));
                    actionForm.setForceChangeAfterReset(Boolean.valueOf(edit.getForceChangeAfterReset()));
                    actionForm.setAllowChange(Boolean.valueOf(edit.getAllowChange()));
                    actionForm.setPasswordHistory(edit.getPasswordHistory());
                    actionForm.setMinimumPasswordLifetime(edit.getMinimumPasswordLifetime());
                    actionForm.setMinimumPasswordLifetimeUnitOfMeasureTypeChoice(edit.getMinimumPasswordLifetimeUnitOfMeasureTypeName());
                    actionForm.setMaximumPasswordLifetime(edit.getMaximumPasswordLifetime());
                    actionForm.setMaximumPasswordLifetimeUnitOfMeasureTypeChoice(edit.getMaximumPasswordLifetimeUnitOfMeasureTypeName());
                    actionForm.setExpirationWarningTime(edit.getExpirationWarningTime());
                    actionForm.setExpirationWarningTimeUnitOfMeasureTypeChoice(edit.getExpirationWarningTimeUnitOfMeasureTypeName());
                    actionForm.setExpiredLoginsPermitted(edit.getExpiredLoginsPermitted());
                    actionForm.setMinimumLength(edit.getMinimumLength());
                    actionForm.setMaximumLength(edit.getMaximumLength());
                    actionForm.setRequiredDigitCount(edit.getRequiredDigitCount());
                    actionForm.setRequiredLetterCount(edit.getRequiredLetterCount());
                    actionForm.setRequiredUpperCaseCount(edit.getRequiredUpperCaseCount());
                    actionForm.setRequiredLowerCaseCount(edit.getRequiredLowerCaseCount());
                    actionForm.setMaximumRepeated(edit.getMaximumRepeated());
                    actionForm.setMinimumCharacterTypes(edit.getMinimumCharacterTypes());
                }
                
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }
            
            setCommandResultAttribute(request, commandResult);
            
            forwardKey = ForwardConstants.FORM;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
