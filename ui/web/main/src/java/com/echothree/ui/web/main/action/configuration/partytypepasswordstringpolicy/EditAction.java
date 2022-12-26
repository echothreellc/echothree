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

package com.echothree.ui.web.main.action.configuration.partytypepasswordstringpolicy;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.edit.PartyTypePasswordStringPolicyEdit;
import com.echothree.control.user.party.common.form.EditPartyTypePasswordStringPolicyForm;
import com.echothree.control.user.party.common.result.EditPartyTypePasswordStringPolicyResult;
import com.echothree.control.user.party.common.spec.PartyTypeSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
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
        String forwardKey = null;
        String partyTypeName = request.getParameter(ParameterConstants.PARTY_TYPE_NAME);
        EditActionForm actionForm = (EditActionForm)form;
        EditPartyTypePasswordStringPolicyForm commandForm = PartyUtil.getHome().getEditPartyTypePasswordStringPolicyForm();
        PartyTypeSpec spec = PartyUtil.getHome().getPartyTypeSpec();
        
        if(partyTypeName == null)
            partyTypeName = actionForm.getPartyTypeName();
        
        commandForm.setSpec(spec);
        spec.setPartyTypeName(partyTypeName);
        
        if(wasPost(request)) {
            boolean wasCanceled = wasCanceled(request);
            
            if(wasCanceled) {
                commandForm.setEditMode(EditMode.ABANDON);
            } else {
                PartyTypePasswordStringPolicyEdit edit = PartyUtil.getHome().getPartyTypePasswordStringPolicyEdit();

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
            
            CommandResult commandResult = PartyUtil.getHome().editPartyTypePasswordStringPolicy(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors() && !wasCanceled) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    EditPartyTypePasswordStringPolicyResult result = (EditPartyTypePasswordStringPolicyResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = PartyUtil.getHome().editPartyTypePasswordStringPolicy(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditPartyTypePasswordStringPolicyResult result = (EditPartyTypePasswordStringPolicyResult)executionResult.getResult();
            
            if(result != null) {
                PartyTypePasswordStringPolicyEdit edit = result.getEdit();
                
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
