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

package com.echothree.ui.web.main.action.humanresources.partytrainingclass;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.result.GetPartyTrainingClassResult;
import com.echothree.control.user.training.common.result.SetPartyTrainingClassStatusResult;
import com.echothree.ui.web.main.action.humanresources.employee.EmployeeUtils;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseStatusAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import static com.echothree.view.client.web.struts.BaseAction.getUserVisitPK;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/HumanResources/PartyTrainingClass/Status",
    mappingClass = SecureActionMapping.class,
    name = "PartyTrainingClassStatus",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/PartyTrainingClass/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/partytrainingclass/status.jsp")
    }
)
public class StatusAction
        extends MainBaseStatusAction<StatusActionForm, SetPartyTrainingClassStatusResult> {
    
    @Override
    public void setupParameters(StatusActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        actionForm.setPartyTrainingClassName(findParameter(request, ParameterConstants.PARTY_TRAINING_CLASS_NAME, actionForm.getPartyTrainingClassName()));
    }
    
   @Override
    public void setupTransfer(StatusActionForm actionForm, HttpServletRequest request)
            throws NamingException {
       var commandForm = TrainingUtil.getHome().getGetPartyTrainingClassForm();
        
        commandForm.setPartyTrainingClassName(actionForm.getPartyTrainingClassName());

       var commandResult = TrainingUtil.getHome().getPartyTrainingClass(getUserVisitPK(request), commandForm);
       var executionResult = commandResult.getExecutionResult();
       var result = (GetPartyTrainingClassResult)executionResult.getResult();
       var partyTrainingClass = result.getPartyTrainingClass();
        
        request.setAttribute(AttributeConstants.PARTY_TRAINING_CLASS, partyTrainingClass);
        request.setAttribute(AttributeConstants.EMPLOYEE, EmployeeUtils.getInstance().getEmployee(getUserVisitPK(request),
                partyTrainingClass.getParty().getPartyName(), null));
    }
    
    @Override
    public CommandResult doStatus(StatusActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = TrainingUtil.getHome().getSetPartyTrainingClassStatusForm();

        commandForm.setPartyTrainingClassName(actionForm.getPartyTrainingClassName());
        commandForm.setPartyTrainingClassStatusChoice(actionForm.getPartyTrainingClassStatusChoice());

        return TrainingUtil.getHome().setPartyTrainingClassStatus(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(StatusActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
}
