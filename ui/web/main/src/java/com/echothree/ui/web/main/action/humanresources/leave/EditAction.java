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

package com.echothree.ui.web.main.action.humanresources.leave;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.edit.LeaveEdit;
import com.echothree.control.user.employee.common.form.EditLeaveForm;
import com.echothree.control.user.employee.common.result.EditLeaveResult;
import com.echothree.control.user.employee.common.spec.LeaveSpec;
import com.echothree.ui.web.main.action.humanresources.employee.EmployeeUtils;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.message.Messages;
import static com.echothree.view.client.web.struts.BaseAction.getUserVisitPK;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/HumanResources/Leave/Edit",
    mappingClass = SecureActionMapping.class,
    name = "LeaveEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/Leave/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/leave/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, LeaveSpec, LeaveEdit, EditLeaveForm, EditLeaveResult> {
    
    @Override
    protected LeaveSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = EmployeeUtil.getHome().getLeaveSpec();
        
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        spec.setLeaveName(findParameter(request, ParameterConstants.LEAVE_NAME, actionForm.getLeaveName()));
        
        return spec;
    }
    
    @Override
    protected LeaveEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = EmployeeUtil.getHome().getLeaveEdit();

        edit.setCompanyName(actionForm.getCompanyChoice());
        edit.setLeaveTypeName(actionForm.getLeaveTypeChoice());
        edit.setLeaveReasonName(actionForm.getLeaveReasonChoice());
        edit.setStartTime(actionForm.getStartTime());
        edit.setEndTime(actionForm.getEndTime());
        edit.setTotalTime(actionForm.getTotalTime());
        edit.setTotalTimeUnitOfMeasureTypeName(actionForm.getTotalTimeUnitOfMeasureTypeChoice());

        return edit;
    }
    
    @Override
    protected EditLeaveForm getForm()
            throws NamingException {
        return EmployeeUtil.getHome().getEditLeaveForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditLeaveResult result, LeaveSpec spec, LeaveEdit edit) {
        actionForm.setLeaveName(spec.getLeaveName());
        actionForm.setCompanyChoice(edit.getCompanyName());
        actionForm.setLeaveTypeChoice(edit.getLeaveTypeName());
        actionForm.setLeaveReasonChoice(edit.getLeaveReasonName());
        actionForm.setStartTime(edit.getStartTime());
        actionForm.setEndTime(edit.getEndTime());
        actionForm.setTotalTime(edit.getTotalTime());
        actionForm.setTotalTimeUnitOfMeasureTypeChoice(edit.getTotalTimeUnitOfMeasureTypeName());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditLeaveForm commandForm)
            throws Exception {
        return EmployeeUtil.getHome().editLeave(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditLeaveResult result)
            throws NamingException {
        var leave = result.getLeave();
        
        request.setAttribute(AttributeConstants.LEAVE, leave);
        request.setAttribute(AttributeConstants.EMPLOYEE, EmployeeUtils.getInstance().getEmployee(getUserVisitPK(request), leave.getParty().getPartyName(),
                null));
    }

    @Override
    protected boolean displayForm(ExecutionResult executionResult) {
        var executionErrors = executionResult.getExecutionErrors();
        
        return executionErrors == null ? true
                : !executionErrors.containsKeys(Messages.EXECUTION_ERROR, ExecutionErrors.InvalidLeaveStatus.name());
    }
    
}
