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

package com.echothree.ui.web.main.action.humanresources.partytrainingclasssession;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.result.GetPartyTrainingClassSessionResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.action.humanresources.employee.EmployeeUtils;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/HumanResources/PartyTrainingClassSession/Delete",
    mappingClass = SecureActionMapping.class,
    name = "PartyTrainingClassSessionDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/PartyTrainingClass/Review", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/partytrainingclasssession/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {

    @Override
    public String getEntityTypeName(final DeleteActionForm actionForm) {
        return EntityTypes.PartyTrainingClassSession.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyTrainingClassName(findParameter(request, ParameterConstants.PARTY_TRAINING_CLASS_NAME, actionForm.getPartyTrainingClassName()));
        actionForm.setPartyTrainingClassSessionSequence(findParameter(request, ParameterConstants.PARTY_TRAINING_CLASS_SESSION_SEQUENCE, actionForm.getPartyTrainingClassSessionSequence()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = TrainingUtil.getHome().getGetPartyTrainingClassSessionForm();
        
        commandForm.setPartyTrainingClassName(actionForm.getPartyTrainingClassName());
        commandForm.setPartyTrainingClassSessionSequence(actionForm.getPartyTrainingClassSessionSequence());

        var commandResult = TrainingUtil.getHome().getPartyTrainingClassSession(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPartyTrainingClassSessionResult)executionResult.getResult();
        var partyTrainingClassSession = result.getPartyTrainingClassSession();
        
        request.setAttribute(AttributeConstants.PARTY_TRAINING_CLASS_SESSION, result.getPartyTrainingClassSession());
        request.setAttribute(AttributeConstants.EMPLOYEE, EmployeeUtils.getInstance().getEmployee(getUserVisitPK(request),
                partyTrainingClassSession.getPartyTrainingClass().getParty().getPartyName(), null));
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = TrainingUtil.getHome().getDeletePartyTrainingClassSessionForm();

        commandForm.setPartyTrainingClassName(actionForm.getPartyTrainingClassName());
        commandForm.setPartyTrainingClassSessionSequence(actionForm.getPartyTrainingClassSessionSequence());

        return TrainingUtil.getHome().deletePartyTrainingClassSession(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_TRAINING_CLASS_NAME, actionForm.getPartyTrainingClassName());
    }
    
}
