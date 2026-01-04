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

package com.echothree.ui.web.main.action.humanresources.employee;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.result.GetEmployeeResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetPartyTypeResult;
import com.echothree.model.control.comment.common.CommentOptions;
import com.echothree.model.control.contact.common.ContactOptions;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.employee.common.EmployeeOptions;
import com.echothree.model.control.party.common.PartyOptions;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.string.ContactPostalAddressUtils;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashSet;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/HumanResources/Employee/Review",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/humanresources/employee/review.jsp")
    }
)
public class ReviewAction
        extends MainBaseAction<ActionForm> {

    public void setupPartyType(HttpServletRequest request)
            throws NamingException {
        var commandForm = PartyUtil.getHome().getGetPartyTypeForm();

        commandForm.setPartyTypeName(PartyTypes.EMPLOYEE.name());

        Set<String> options = new HashSet<>();
        options.add(PartyOptions.PartyTypeIncludeLockoutPolicy);
        commandForm.setOptions(options);

        var commandResult = PartyUtil.getHome().getPartyType(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPartyTypeResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.PARTY_TYPE, result.getPartyType());
    }

    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var commandForm = EmployeeUtil.getHome().getGetEmployeeForm();
        
        commandForm.setEmployeeName(request.getParameter(ParameterConstants.EMPLOYEE_NAME));
        commandForm.setPartyName(request.getParameter(ParameterConstants.PARTY_NAME));
        
        Set<String> options = new HashSet<>();
        options.add(PartyOptions.PartyIncludePartyAliases);
        options.add(PartyOptions.PartyIncludePartyDocuments);
        options.add(PartyOptions.PartyIncludePartyContactLists);
        options.add(PartyOptions.PartyIncludePartyContactMechanisms);
        options.add(PartyOptions.PartyIncludePartyPrinterGroupUses);
        options.add(PartyOptions.PartyIncludePartyScaleUses);
        options.add(PartyOptions.PartyIncludeUserLogin);
        options.add(ContactOptions.PartyContactMechanismIncludePartyContactMechanismPurposes);
        options.add(ContactOptions.PartyContactMechanismIncludePartyContactMechanismRelationshipsByFromPartyContactMechanism);
        options.add(EmployeeOptions.EmployeeIncludePartyResponsibilities);
        options.add(EmployeeOptions.EmployeeIncludePartyTrainingClasses);
        options.add(EmployeeOptions.EmployeeIncludePartySkills);
        options.add(EmployeeOptions.EmployeeIncludeEntityAttributeGroups);
        options.add(EmployeeOptions.EmployeeIncludeTagScopes);
        options.add(CoreOptions.EntityAttributeGroupIncludeEntityAttributes);
        options.add(CoreOptions.EntityAttributeIncludeValue);
        options.add(CoreOptions.EntityStringAttributeIncludeString);
        options.add(CoreOptions.EntityInstanceIncludeNames);
        options.add(CommentOptions.CommentIncludeClob);
        commandForm.setOptions(ContactPostalAddressUtils.getInstance().addOptions(options));

        var commandResult = EmployeeUtil.getHome().getEmployee(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetEmployeeResult)executionResult.getResult();
        var employee = result.getEmployee();

        if(employee == null) {
            forwardKey = ForwardConstants.ERROR_404;
        } else {
            request.setAttribute(AttributeConstants.EMPLOYEE, employee);
            setupPartyType(request);
            forwardKey = ForwardConstants.DISPLAY;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}