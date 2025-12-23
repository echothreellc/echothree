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
import com.echothree.control.user.training.common.result.GetPartyTrainingClassesResult;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.training.common.transfer.PartyTrainingClassTransfer;
import com.echothree.model.control.training.common.transfer.TrainingClassTransfer;
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
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/HumanResources/PartyTrainingClass/Main",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/humanresources/partytrainingclass/main.jsp")
    }
)
public class MainAction
        extends MainBaseAction<ActionForm> {

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = TrainingUtil.getHome().getGetPartyTrainingClassesForm();

        commandForm.setPartyName(request.getParameter(ParameterConstants.PARTY_NAME));
        commandForm.setTrainingClassName(request.getParameter(ParameterConstants.TRAINING_CLASS_NAME));

        Set<String> options = new HashSet<>();
        options.add(PartyOptions.PartyIncludeDescription);
        commandForm.setOptions(options);

        var commandResult = TrainingUtil.getHome().getPartyTrainingClasses(getUserVisitPK(request), commandForm);
        PartyTransfer party = null;
        TrainingClassTransfer trainingClass = null;
        List<PartyTrainingClassTransfer> partyTrainingClasses = null;

        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPartyTrainingClassesResult)executionResult.getResult();
            
            party = result.getParty();
            trainingClass = result.getTrainingClass();
            partyTrainingClasses = result.getPartyTrainingClasses();
        }

        if(partyTrainingClasses == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            if(party != null) {
                request.setAttribute(AttributeConstants.PARTY, party);
                request.setAttribute(AttributeConstants.EMPLOYEE, EmployeeUtils.getInstance().getEmployee(getUserVisitPK(request), party.getPartyName(), null));
            }
            
            if(trainingClass != null) {
                request.setAttribute(AttributeConstants.TRAINING_CLASS, trainingClass);
            }

            request.setAttribute(AttributeConstants.PARTY_TRAINING_CLASSES, partyTrainingClasses);
            forwardKey = ForwardConstants.DISPLAY;
        }

        return mapping.findForward(forwardKey);
    }

}
