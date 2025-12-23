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
import com.echothree.ui.web.main.action.humanresources.employee.EmployeeUtils;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
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
    path = "/HumanResources/PartyTrainingClass/Add",
    mappingClass = SecureActionMapping.class,
    name = "PartyTrainingClassAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/PartyTrainingClass/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/partytrainingclass/add.jsp")
    }
)
public class AddAction
        extends MainBaseAddAction<AddActionForm> {
    
    @Override
    public void setupParameters(AddActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
    }
    
    @Override
    public void setupTransfer(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        request.setAttribute(AttributeConstants.EMPLOYEE, EmployeeUtils.getInstance().getEmployee(getUserVisitPK(request), actionForm.getPartyName(), null));
    }
    
    @Override
    public CommandResult doAdd(AddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = TrainingUtil.getHome().getCreatePartyTrainingClassForm();

        commandForm.setPartyName(actionForm.getPartyName());
        commandForm.setTrainingClassName(actionForm.getTrainingClassChoice());
        commandForm.setCompletedTime(actionForm.getCompletedTime());
        commandForm.setValidUntilTime(actionForm.getValidUntilTime());

        return TrainingUtil.getHome().createPartyTrainingClass(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(AddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
}
