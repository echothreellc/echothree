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

package com.echothree.ui.web.main.action.configuration.printergroup;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.control.user.printer.common.edit.PrinterGroupEdit;
import com.echothree.control.user.printer.common.form.EditPrinterGroupForm;
import com.echothree.control.user.printer.common.result.EditPrinterGroupResult;
import com.echothree.control.user.printer.common.spec.PrinterGroupSpec;
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
    path = "/Configuration/PrinterGroup/Edit",
    mappingClass = SecureActionMapping.class,
    name = "PrinterGroupEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/PrinterGroup/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/printergroup/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, PrinterGroupSpec, PrinterGroupEdit, EditPrinterGroupForm, EditPrinterGroupResult> {

    @Override
    protected PrinterGroupSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = PrinterUtil.getHome().getPrinterGroupSpec();
        var originalPrinterGroupName = request.getParameter(ParameterConstants.ORIGINAL_PRINTER_GROUP_NAME);

        if(originalPrinterGroupName == null) {
            originalPrinterGroupName = actionForm.getOriginalPrinterGroupName();
        }

        spec.setPrinterGroupName(originalPrinterGroupName);

        return spec;
    }

    @Override
    protected PrinterGroupEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = PrinterUtil.getHome().getPrinterGroupEdit();

        edit.setPrinterGroupName(actionForm.getPrinterGroupName());
        edit.setKeepPrintedJobsTime(actionForm.getKeepPrintedJobsTime());
        edit.setKeepPrintedJobsTimeUnitOfMeasureTypeName(actionForm.getKeepPrintedJobsTimeUnitOfMeasureTypeChoice());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }

    @Override
    protected EditPrinterGroupForm getForm()
            throws NamingException {
        return PrinterUtil.getHome().getEditPrinterGroupForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditPrinterGroupResult result, PrinterGroupSpec spec, PrinterGroupEdit edit) {
        actionForm.setOriginalPrinterGroupName(spec.getPrinterGroupName());
        actionForm.setPrinterGroupName(edit.getPrinterGroupName());
        actionForm.setKeepPrintedJobsTime(edit.getKeepPrintedJobsTime());
        actionForm.setKeepPrintedJobsTimeUnitOfMeasureTypeChoice(edit.getKeepPrintedJobsTimeUnitOfMeasureTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPrinterGroupForm commandForm)
            throws Exception {
        return PrinterUtil.getHome().editPrinterGroup(getUserVisitPK(request), commandForm);
    }

}
