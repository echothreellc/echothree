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

package com.echothree.ui.web.main.action.associate.associatepartycontactmechanism;

import com.echothree.control.user.associate.common.AssociateUtil;
import com.echothree.control.user.associate.common.form.GetAssociatePartyContactMechanismForm;
import com.echothree.control.user.associate.common.result.GetAssociatePartyContactMechanismResult;
import com.echothree.model.control.associate.common.transfer.AssociatePartyContactMechanismTransfer;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Associate/AssociatePartyContactMechanism/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/associate/associatepartycontactmechanism/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        
        try {
            String associateProgramName = request.getParameter(ParameterConstants.ASSOCIATE_PROGRAM_NAME);
            String associateName = request.getParameter(ParameterConstants.ASSOCIATE_NAME);
            String associatePartyContactMechanismName = request.getParameter(ParameterConstants.ASSOCIATE_PARTY_CONTACT_MECHANISM_NAME);
            GetAssociatePartyContactMechanismForm commandForm = AssociateUtil.getHome().getGetAssociatePartyContactMechanismForm();
            
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);
            
            CommandResult commandResult = AssociateUtil.getHome().getAssociatePartyContactMechanism(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetAssociatePartyContactMechanismResult result = (GetAssociatePartyContactMechanismResult)executionResult.getResult();
            AssociatePartyContactMechanismTransfer associatePartyContactMechanismTransfer = result.getAssociatePartyContactMechanism();
            
            request.setAttribute(AttributeConstants.ASSOCIATE_PARTY_CONTACT_MECHANISM, associatePartyContactMechanismTransfer);
            forwardKey = ForwardConstants.DISPLAY;
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
