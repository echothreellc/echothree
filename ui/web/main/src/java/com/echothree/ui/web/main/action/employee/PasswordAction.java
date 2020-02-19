// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.ui.web.main.action.employee;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.authentication.common.form.SetPasswordForm;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.GetPartyTypeForm;
import com.echothree.control.user.party.common.result.GetPartyTypeResult;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.common.transfer.PartyTypePasswordStringPolicyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Employee/Password",
    mappingClass = SecureActionMapping.class,
    name = "Password",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Form", path = "/employee/password.jsp")
    }
)
public class PasswordAction
        extends MainBaseAction<ActionForm> {
    
    /** Creates a new instance of PasswordAction */
    public PasswordAction() {
        super(true, false);
    }
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        PasswordActionForm actionForm = (PasswordActionForm)form;
        
        if(wasPost(request)) {
            if(!wasCancelled(request)) {
                SetPasswordForm commandForm = AuthenticationUtil.getHome().getSetPasswordForm();

                commandForm.setOldPassword(actionForm.getOldPassword());
                commandForm.setNewPassword1(actionForm.getNewPassword1());
                commandForm.setNewPassword2(actionForm.getNewPassword2());

                CommandResult commandResult = AuthenticationUtil.getHome().setPassword(getUserVisitPK(request), commandForm);

                if(commandResult.hasErrors()) {
                    setCommandResultAttribute(request, commandResult);
                } else {
                    forwardKey = ForwardConstants.PORTAL;
                }
            } else {
                forwardKey = ForwardConstants.PORTAL;
            }
        }
        
        if(forwardKey == null) {
            GetPartyTypeForm commandForm = PartyUtil.getHome().getGetPartyTypeForm();
            
            commandForm.setPartyTypeName(PartyTypes.EMPLOYEE.name());
            
            Set<String> options = new HashSet<>();
            options.add(PartyOptions.PartyTypeIncludePasswordStringPolicy);
            commandForm.setOptions(options);
            
            CommandResult commandResult = PartyUtil.getHome().getPartyType(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetPartyTypeResult result = (GetPartyTypeResult)executionResult.getResult();
            PartyTypeTransfer partyType = result.getPartyType();
            PartyTypePasswordStringPolicyTransfer partyTypePasswordStringPolicy = partyType == null? null: partyType.getPartyTypePasswordStringPolicy();
            
            if(partyTypePasswordStringPolicy != null) {
                request.setAttribute(AttributeConstants.PARTY_TYPE_PASSWORD_STRING_POLICY, partyTypePasswordStringPolicy);
            }
            
            forwardKey = ForwardConstants.FORM;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
