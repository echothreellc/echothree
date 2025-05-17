// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.employee.employeeapplicationeditoruse;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.action.humanresources.employeeapplicationeditoruse.BaseEmployeeApplicationEditorUseAction;
import com.echothree.ui.web.main.action.humanresources.employeeapplicationeditoruse.DeleteActionForm;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Employee/EmployeeApplicationEditorUse/Delete",
    mappingClass = SecureActionMapping.class,
    name = "EmployeeApplicationEditorUseDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Employee/EmployeeApplicationEditorUse/Main", redirect = true),
        @SproutForward(name = "Form", path = "/employee/employeeapplicationeditoruse/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {
    
    @Override
    public String getEntityTypeName() {
        return EntityTypes.PartyApplicationEditorUse.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setApplicationName(findParameter(request, ParameterConstants.APPLICATION_NAME, actionForm.getApplicationName()));
        actionForm.setApplicationEditorUseName(findParameter(request, ParameterConstants.APPLICATION_EDITOR_USE_NAME, actionForm.getApplicationEditorUseName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        BaseEmployeeApplicationEditorUseAction.setupPartyApplicationEditorUseTransfer(request, null, actionForm.getApplicationName(),
                actionForm.getApplicationEditorUseName());
        BaseEmployeeApplicationEditorUseAction.setupEmployee(request, null);
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = PartyUtil.getHome().getDeletePartyApplicationEditorUseForm();

        commandForm.setApplicationName(actionForm.getApplicationName());
        commandForm.setApplicationEditorUseName(actionForm.getApplicationEditorUseName());

        return PartyUtil.getHome().deletePartyApplicationEditorUse(getUserVisitPK(request), commandForm);
    }
    
}
