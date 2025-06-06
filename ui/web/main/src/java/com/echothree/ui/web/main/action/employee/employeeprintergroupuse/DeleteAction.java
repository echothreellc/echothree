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

package com.echothree.ui.web.main.action.employee.employeeprintergroupuse;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.action.humanresources.employeeprintergroupuse.BaseEmployeePrinterGroupUseAction;
import com.echothree.ui.web.main.action.humanresources.employeeprintergroupuse.DeleteActionForm;
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
    path = "/Employee/EmployeePrinterGroupUse/Delete",
    mappingClass = SecureActionMapping.class,
    name = "EmployeePrinterGroupUseDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Employee/EmployeePrinterGroupUse/Main", redirect = true),
        @SproutForward(name = "Form", path = "/employee/employeeprintergroupuse/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {
    
    @Override
    public String getEntityTypeName() {
        return EntityTypes.PartyPrinterGroupUse.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setPrinterGroupUseTypeName(findParameter(request, ParameterConstants.PRINTER_GROUP_USE_TYPE_NAME, actionForm.getPrinterGroupUseTypeName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        BaseEmployeePrinterGroupUseAction.setupPartyPrinterGroupUseTransfer(request, null, actionForm.getPrinterGroupUseTypeName());
        BaseEmployeePrinterGroupUseAction.setupEmployee(request, null);
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = PrinterUtil.getHome().getDeletePartyPrinterGroupUseForm();

        commandForm.setPrinterGroupUseTypeName(actionForm.getPrinterGroupUseTypeName());

        return PrinterUtil.getHome().deletePartyPrinterGroupUse(getUserVisitPK(request), commandForm);
    }
    
}
