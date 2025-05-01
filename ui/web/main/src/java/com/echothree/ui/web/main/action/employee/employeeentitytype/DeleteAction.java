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

package com.echothree.ui.web.main.action.employee.employeeentitytype;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.action.humanresources.employeeentitytype.BaseEmployeeEntityTypeAction;
import com.echothree.ui.web.main.action.humanresources.employeeentitytype.DeleteActionForm;
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
    path = "/Employee/EmployeeEntityType/Delete",
    mappingClass = SecureActionMapping.class,
    name = "EmployeeEntityTypeDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Employee/EmployeeEntityType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/employee/employeeentitytype/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {
    
    @Override
    public String getEntityTypeName() {
        return EntityTypes.PartyEntityType.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setComponentVendorName(findParameter(request, ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName()));
        actionForm.setEntityTypeName(findParameter(request, ParameterConstants.ENTITY_TYPE_NAME, actionForm.getEntityTypeName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        BaseEmployeeEntityTypeAction.setupPartyEntityTypeTransfer(request, null, actionForm.getComponentVendorName(),
                actionForm.getEntityTypeName());
        BaseEmployeeEntityTypeAction.setupEmployee(request, null);
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = PartyUtil.getHome().getDeletePartyEntityTypeForm();

        commandForm.setComponentVendorName(actionForm.getComponentVendorName());
        commandForm.setEntityTypeName(actionForm.getEntityTypeName());

        return PartyUtil.getHome().deletePartyEntityType(getUserVisitPK(request), commandForm);
    }
    
}
