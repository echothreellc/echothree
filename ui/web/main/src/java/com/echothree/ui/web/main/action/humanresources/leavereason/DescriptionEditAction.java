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
import com.echothree.control.user.employee.common.edit.LeaveReasonDescriptionEdit;
import com.echothree.control.user.employee.common.form.EditLeaveReasonDescriptionForm;
import com.echothree.control.user.employee.common.result.EditLeaveReasonDescriptionResult;
import com.echothree.control.user.employee.common.spec.LeaveReasonDescriptionSpec;
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
    path = "/HumanResources/LeaveReason/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "LeaveReasonDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/HumanResources/LeaveReason/Description", redirect = true),
        @SproutForward(name = "Form", path = "/humanresources/leavereason/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, LeaveReasonDescriptionSpec, LeaveReasonDescriptionEdit, EditLeaveReasonDescriptionForm, EditLeaveReasonDescriptionResult> {
    
    @Override
    protected LeaveReasonDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = EmployeeUtil.getHome().getLeaveReasonDescriptionSpec();
        
        spec.setLeaveReasonName(findParameter(request, ParameterConstants.LEAVE_REASON_NAME, actionForm.getLeaveReasonName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected LeaveReasonDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = EmployeeUtil.getHome().getLeaveReasonDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditLeaveReasonDescriptionForm getForm()
            throws NamingException {
        return EmployeeUtil.getHome().getEditLeaveReasonDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditLeaveReasonDescriptionResult result, LeaveReasonDescriptionSpec spec, LeaveReasonDescriptionEdit edit) {
        actionForm.setLeaveReasonName(spec.getLeaveReasonName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditLeaveReasonDescriptionForm commandForm)
            throws Exception {
        return EmployeeUtil.getHome().editLeaveReasonDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.LEAVE_REASON_NAME, actionForm.getLeaveReasonName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditLeaveReasonDescriptionResult result) {
        request.setAttribute(AttributeConstants.LEAVE_REASON_DESCRIPTION, result.getLeaveReasonDescription());
    }

}
