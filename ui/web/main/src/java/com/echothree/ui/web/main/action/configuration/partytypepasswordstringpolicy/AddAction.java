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
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
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
    path = "/Configuration/PartyTypePasswordStringPolicy/Add",
    mappingClass = SecureActionMapping.class,
    name = "PartyTypePasswordStringPolicyAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/PartyType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/partytypepasswordstringpolicy/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var partyTypeName = request.getParameter(ParameterConstants.PARTY_TYPE_NAME);
        var actionForm = (AddActionForm)form;
        
        if(wasPost(request)) {
            var commandForm = PartyUtil.getHome().getCreatePartyTypePasswordStringPolicyForm();
            
            if(partyTypeName == null)
                partyTypeName = actionForm.getPartyTypeName();
            
            commandForm.setPartyTypeName(partyTypeName);
            commandForm.setForceChangeAfterCreate(actionForm.getForceChangeAfterCreate().toString());
            commandForm.setForceChangeAfterReset(actionForm.getForceChangeAfterReset().toString());
            commandForm.setAllowChange(actionForm.getAllowChange().toString());
            commandForm.setPasswordHistory(actionForm.getPasswordHistory());
            commandForm.setMinimumPasswordLifetime(actionForm.getMinimumPasswordLifetime());
            commandForm.setMinimumPasswordLifetimeUnitOfMeasureTypeName(actionForm.getMinimumPasswordLifetimeUnitOfMeasureTypeChoice());
            commandForm.setMaximumPasswordLifetime(actionForm.getMaximumPasswordLifetime());
            commandForm.setMaximumPasswordLifetimeUnitOfMeasureTypeName(actionForm.getMaximumPasswordLifetimeUnitOfMeasureTypeChoice());
            commandForm.setExpirationWarningTime(actionForm.getExpirationWarningTime());
            commandForm.setExpirationWarningTimeUnitOfMeasureTypeName(actionForm.getExpirationWarningTimeUnitOfMeasureTypeChoice());
            commandForm.setExpiredLoginsPermitted(actionForm.getExpiredLoginsPermitted());
            commandForm.setMinimumLength(actionForm.getMinimumLength());
            commandForm.setMaximumLength(actionForm.getMaximumLength());
            commandForm.setRequiredDigitCount(actionForm.getRequiredDigitCount());
            commandForm.setRequiredLetterCount(actionForm.getRequiredLetterCount());
            commandForm.setRequiredUpperCaseCount(actionForm.getRequiredUpperCaseCount());
            commandForm.setRequiredLowerCaseCount(actionForm.getRequiredLowerCaseCount());
            commandForm.setMaximumRepeated(actionForm.getMaximumRepeated());
            commandForm.setMinimumCharacterTypes(actionForm.getMinimumCharacterTypes());

            var commandResult = PartyUtil.getHome().createPartyTypePasswordStringPolicy(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors()) {
                setCommandResultAttribute(request, commandResult);
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            actionForm.setPartyTypeName(partyTypeName);
            forwardKey = ForwardConstants.FORM;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}