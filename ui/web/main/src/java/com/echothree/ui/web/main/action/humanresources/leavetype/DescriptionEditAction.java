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

package com.echothree.ui.web.main.action.humanresources.leavetype;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.edit.LeaveTypeDescriptionEdit;
import com.echothree.control.user.employee.common.form.EditLeaveTypeDescriptionForm;
import com.echothree.control.user.employee.common.result.EditLeaveTypeDescriptionResult;
import com.echothree.control.user.employee.common.spec.LeaveTypeDescriptionSpec;
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
    path = "/HumanResources/LeaveType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "LeaveTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/LeaveType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/leavetype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, LeaveTypeDescriptionSpec, LeaveTypeDescriptionEdit, EditLeaveTypeDescriptionForm, EditLeaveTypeDescriptionResult> {
    
    @Override
    protected LeaveTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = EmployeeUtil.getHome().getLeaveTypeDescriptionSpec();
        
        spec.setLeaveTypeName(findParameter(request, ParameterConstants.LEAVE_TYPE_NAME, actionForm.getLeaveTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected LeaveTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = EmployeeUtil.getHome().getLeaveTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditLeaveTypeDescriptionForm getForm()
            throws NamingException {
        return EmployeeUtil.getHome().getEditLeaveTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditLeaveTypeDescriptionResult result, LeaveTypeDescriptionSpec spec, LeaveTypeDescriptionEdit edit) {
        actionForm.setLeaveTypeName(spec.getLeaveTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditLeaveTypeDescriptionForm commandForm)
            throws Exception {
        return EmployeeUtil.getHome().editLeaveTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.LEAVE_TYPE_NAME, actionForm.getLeaveTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditLeaveTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.LEAVE_TYPE_DESCRIPTION, result.getLeaveTypeDescription());
    }

}
