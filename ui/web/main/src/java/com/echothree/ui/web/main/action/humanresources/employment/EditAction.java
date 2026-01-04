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

package com.echothree.ui.web.main.action.humanresources.employment;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.edit.EmploymentEdit;
import com.echothree.control.user.employee.common.form.EditEmploymentForm;
import com.echothree.control.user.employee.common.result.EditEmploymentResult;
import com.echothree.control.user.employee.common.spec.EmploymentSpec;
import com.echothree.ui.web.main.action.humanresources.employee.EmployeeUtils;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
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
    path = "/HumanResources/Employment/Edit",
    mappingClass = SecureActionMapping.class,
    name = "EmploymentEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/Employment/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/employment/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, EmploymentSpec, EmploymentEdit, EditEmploymentForm, EditEmploymentResult> {
    
    @Override
    protected EmploymentSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = EmployeeUtil.getHome().getEmploymentSpec();
        
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        spec.setEmploymentName(findParameter(request, ParameterConstants.EMPLOYMENT_NAME, actionForm.getEmploymentName()));
        
        return spec;
    }
    
    @Override
    protected EmploymentEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = EmployeeUtil.getHome().getEmploymentEdit();

        edit.setCompanyName(actionForm.getCompanyChoice());
        edit.setStartTime(actionForm.getStartTime());
        edit.setEndTime(actionForm.getEndTime());
        edit.setTerminationTypeName(actionForm.getTerminationTypeChoice());
        edit.setTerminationReasonName(actionForm.getTerminationReasonChoice());

        return edit;
    }
    
    @Override
    protected EditEmploymentForm getForm()
            throws NamingException {
        return EmployeeUtil.getHome().getEditEmploymentForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditEmploymentResult result, EmploymentSpec spec, EmploymentEdit edit) {
        actionForm.setEmploymentName(spec.getEmploymentName());
        actionForm.setCompanyChoice(edit.getCompanyName());
        actionForm.setStartTime(edit.getStartTime());
        actionForm.setEndTime(edit.getEndTime());
        actionForm.setTerminationTypeChoice(edit.getTerminationTypeName());
        actionForm.setTerminationReasonChoice(edit.getTerminationReasonName());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditEmploymentForm commandForm)
            throws Exception {
        return EmployeeUtil.getHome().editEmployment(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditEmploymentResult result)
            throws NamingException {
        var employment = result.getEmployment();
        
        request.setAttribute(AttributeConstants.EMPLOYMENT, employment);
        request.setAttribute(AttributeConstants.EMPLOYEE, EmployeeUtils.getInstance().getEmployee(getUserVisitPK(request), employment.getParty().getPartyName(),
                null));
    }

}
