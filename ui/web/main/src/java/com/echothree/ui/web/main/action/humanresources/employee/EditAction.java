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

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.edit.EmployeeEdit;
import com.echothree.control.user.party.common.form.EditEmployeeForm;
import com.echothree.control.user.party.common.result.EditEmployeeResult;
import com.echothree.control.user.party.common.spec.EmployeeSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
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
    path = "/HumanResources/Employee/Edit",
    mappingClass = SecureActionMapping.class,
    name = "EmployeeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/Employee/Review", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/employee/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, EmployeeSpec, EmployeeEdit, EditEmployeeForm, EditEmployeeResult> {
    
    @Override
    protected EmployeeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = PartyUtil.getHome().getEmployeeSpec();
        var employeeName = request.getParameter(ParameterConstants.EMPLOYEE_NAME);

        if(employeeName == null) {
            employeeName = actionForm.getEmployeeName();
        }

        spec.setEmployeeName(employeeName);
        
        return spec;
    }
    
    @Override
    protected EmployeeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = PartyUtil.getHome().getEmployeeEdit();

        edit.setEmployeeTypeName(actionForm.getEmployeeTypeChoice());
        edit.setPersonalTitleId(actionForm.getPersonalTitleChoice());
        edit.setFirstName(actionForm.getFirstName());
        edit.setMiddleName(actionForm.getMiddleName());
        edit.setLastName(actionForm.getLastName());
        edit.setNameSuffixId(actionForm.getNameSuffixChoice());
        edit.setPreferredLanguageIsoName(actionForm.getLanguageChoice());
        edit.setPreferredCurrencyIsoName(actionForm.getCurrencyChoice());
        edit.setPreferredJavaTimeZoneName(actionForm.getTimeZoneChoice());
        edit.setPreferredDateTimeFormatName(actionForm.getDateTimeFormatChoice());
        edit.setPartySecurityRoleTemplateName(actionForm.getPartySecurityRoleTemplateChoice());

        return edit;
    }
    
    @Override
    protected EditEmployeeForm getForm()
            throws NamingException {
        return PartyUtil.getHome().getEditEmployeeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditEmployeeResult result, EmployeeSpec spec, EmployeeEdit edit) {
        actionForm.setEmployeeName(spec.getEmployeeName());
        actionForm.setEmployeeTypeChoice(edit.getEmployeeTypeName());
        actionForm.setPersonalTitleChoice(edit.getPersonalTitleId());
        actionForm.setFirstName(edit.getFirstName());
        actionForm.setMiddleName(edit.getMiddleName());
        actionForm.setLastName(edit.getLastName());
        actionForm.setNameSuffixChoice(edit.getNameSuffixId());
        actionForm.setLanguageChoice(edit.getPreferredLanguageIsoName());
        actionForm.setCurrencyChoice(edit.getPreferredCurrencyIsoName());
        actionForm.setTimeZoneChoice(edit.getPreferredJavaTimeZoneName());
        actionForm.setDateTimeFormatChoice(edit.getPreferredDateTimeFormatName());
        actionForm.setPartySecurityRoleTemplateChoice(edit.getPartySecurityRoleTemplateName());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditEmployeeForm commandForm)
            throws Exception {
        var commandResult = PartyUtil.getHome().editEmployee(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditEmployeeResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.EMPLOYEE, result.getEmployee());
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.EMPLOYEE_NAME, actionForm.getEmployeeName());
    }
    
}
