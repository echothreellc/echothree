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

package com.echothree.ui.web.main.action.employee.employeeprintergroupuse;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.control.user.printer.common.edit.PartyPrinterGroupUseEdit;
import com.echothree.control.user.printer.common.form.EditPartyPrinterGroupUseForm;
import com.echothree.control.user.printer.common.result.EditPartyPrinterGroupUseResult;
import com.echothree.control.user.printer.common.spec.PartyPrinterGroupUseSpec;
import com.echothree.ui.web.main.action.humanresources.employeeprintergroupuse.BaseEmployeePrinterGroupUseAction;
import com.echothree.ui.web.main.action.humanresources.employeeprintergroupuse.EditActionForm;
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
    path = "/Employee/EmployeePrinterGroupUse/Edit",
    mappingClass = SecureActionMapping.class,
    name = "EmployeePrinterGroupUseEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Employee/EmployeePrinterGroupUse/Main", redirect = true),
        @SproutForward(name = "Form", path = "/employee/employeeprintergroupuse/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, PartyPrinterGroupUseSpec, PartyPrinterGroupUseEdit, EditPartyPrinterGroupUseForm, EditPartyPrinterGroupUseResult> {

    @Override
    protected PartyPrinterGroupUseSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = PrinterUtil.getHome().getPartyPrinterGroupUseSpec();

        spec.setPrinterGroupUseTypeName(findParameter(request, ParameterConstants.PRINTER_GROUP_USE_TYPE_NAME, actionForm.getPrinterGroupUseTypeName()));

        return spec;
    }

    @Override
    protected PartyPrinterGroupUseEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = PrinterUtil.getHome().getPartyPrinterGroupUseEdit();

        edit.setPrinterGroupName(actionForm.getPrinterGroupChoice());

        return edit;
    }

    @Override
    protected EditPartyPrinterGroupUseForm getForm()
            throws NamingException {
        return PrinterUtil.getHome().getEditPartyPrinterGroupUseForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditPartyPrinterGroupUseResult result, PartyPrinterGroupUseSpec spec, PartyPrinterGroupUseEdit edit)
            throws NamingException {
        actionForm.setPrinterGroupUseTypeName(spec.getPrinterGroupUseTypeName());
        actionForm.setPrinterGroupChoice(edit.getPrinterGroupName());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPartyPrinterGroupUseForm commandForm)
            throws Exception {
        return PrinterUtil.getHome().editPartyPrinterGroupUse(getUserVisitPK(request), commandForm);
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditPartyPrinterGroupUseResult result)
            throws NamingException {
        request.setAttribute(AttributeConstants.PARTY_PRINTER_GROUP_USE, result.getPartyPrinterGroupUse());
        BaseEmployeePrinterGroupUseAction.setupEmployee(request, null);
    }
    
}