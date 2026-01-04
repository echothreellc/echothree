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
import com.echothree.control.user.employee.common.edit.LeaveTypeEdit;
import com.echothree.control.user.employee.common.form.EditLeaveTypeForm;
import com.echothree.control.user.employee.common.result.EditLeaveTypeResult;
import com.echothree.control.user.employee.common.spec.LeaveTypeSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/HumanResources/LeaveType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "LeaveTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/LeaveType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/leavetype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, LeaveTypeSpec, LeaveTypeEdit, EditLeaveTypeForm, EditLeaveTypeResult> {
    
    @Override
    protected LeaveTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = EmployeeUtil.getHome().getLeaveTypeSpec();
        
        spec.setLeaveTypeName(findParameter(request, ParameterConstants.ORIGINAL_LEAVE_TYPE_NAME, actionForm.getOriginalLeaveTypeName()));
        
        return spec;
    }
    
    @Override
    protected LeaveTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = EmployeeUtil.getHome().getLeaveTypeEdit();

        edit.setLeaveTypeName(actionForm.getLeaveTypeName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditLeaveTypeForm getForm()
            throws NamingException {
        return EmployeeUtil.getHome().getEditLeaveTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditLeaveTypeResult result, LeaveTypeSpec spec, LeaveTypeEdit edit) {
        actionForm.setOriginalLeaveTypeName(spec.getLeaveTypeName());
        actionForm.setLeaveTypeName(edit.getLeaveTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditLeaveTypeForm commandForm)
            throws Exception {
        return EmployeeUtil.getHome().editLeaveType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditLeaveTypeResult result) {
        request.setAttribute(AttributeConstants.LEAVE_TYPE, result.getLeaveType());
    }

}
