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
import com.echothree.model.control.training.common.TrainingOptions;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassSessionTransfer;
import com.echothree.ui.web.main.action.humanresources.employee.EmployeeUtils;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import static com.echothree.view.client.web.struts.BaseAction.getUserVisitPK;
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
    path = "/HumanResources/PartyTrainingClassSession/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/humanresources/partytrainingclasssession/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = TrainingUtil.getHome().getGetPartyTrainingClassSessionForm();

        commandForm.setPartyTrainingClassName(request.getParameter(ParameterConstants.PARTY_TRAINING_CLASS_NAME));
        commandForm.setPartyTrainingClassSessionSequence(request.getParameter(ParameterConstants.PARTY_TRAINING_CLASS_SESSION_SEQUENCE));
        
        Set<String> options = new HashSet<>();
        options.add(TrainingOptions.PartyTrainingClassSessionIncludePartyTrainingClassSessionPages);
        options.add(TrainingOptions.PartyTrainingClassSessionIncludePartyTrainingClassSessionQuestions);
        commandForm.setOptions(options);

        var commandResult = TrainingUtil.getHome().getPartyTrainingClassSession(getUserVisitPK(request), commandForm);
        PartyTrainingClassSessionTransfer partyTrainingClassSession = null;
        
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPartyTrainingClassSessionResult)executionResult.getResult();
            
            partyTrainingClassSession = result.getPartyTrainingClassSession();
        }
        
        if(partyTrainingClassSession == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.PARTY_TRAINING_CLASS_SESSION, partyTrainingClassSession);
            request.setAttribute(AttributeConstants.EMPLOYEE, EmployeeUtils.getInstance().getEmployee(getUserVisitPK(request),
                    partyTrainingClassSession.getPartyTrainingClass().getParty().getPartyName(), null));
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
