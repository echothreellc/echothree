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

package com.echothree.ui.web.main.action.humanresources.leavereason;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.edit.LeaveReasonEdit;
import com.echothree.control.user.employee.common.form.EditLeaveReasonForm;
import com.echothree.control.user.employee.common.result.EditLeaveReasonResult;
import com.echothree.control.user.employee.common.spec.LeaveReasonSpec;
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
    path = "/HumanResources/LeaveReason/Edit",
    mappingClass = SecureActionMapping.class,
    name = "LeaveReasonEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/LeaveReason/Main", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/leavereason/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, LeaveReasonSpec, LeaveReasonEdit, EditLeaveReasonForm, EditLeaveReasonResult> {
    
    @Override
    protected LeaveReasonSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = EmployeeUtil.getHome().getLeaveReasonSpec();
        
        spec.setLeaveReasonName(findParameter(request, ParameterConstants.ORIGINAL_LEAVE_REASON_NAME, actionForm.getOriginalLeaveReasonName()));
        
        return spec;
    }
    
    @Override
    protected LeaveReasonEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = EmployeeUtil.getHome().getLeaveReasonEdit();

        edit.setLeaveReasonName(actionForm.getLeaveReasonName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditLeaveReasonForm getForm()
            throws NamingException {
        return EmployeeUtil.getHome().getEditLeaveReasonForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditLeaveReasonResult result, LeaveReasonSpec spec, LeaveReasonEdit edit) {
        actionForm.setOriginalLeaveReasonName(spec.getLeaveReasonName());
        actionForm.setLeaveReasonName(edit.getLeaveReasonName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditLeaveReasonForm commandForm)
            throws Exception {
        return EmployeeUtil.getHome().editLeaveReason(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditLeaveReasonResult result) {
        request.setAttribute(AttributeConstants.LEAVE_REASON, result.getLeaveReason());
    }

}
