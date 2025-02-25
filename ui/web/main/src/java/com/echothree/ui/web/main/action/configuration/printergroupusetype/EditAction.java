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

package com.echothree.ui.web.main.action.configuration.printergroupusetype;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.control.user.printer.common.edit.PrinterGroupUseTypeEdit;
import com.echothree.control.user.printer.common.form.EditPrinterGroupUseTypeForm;
import com.echothree.control.user.printer.common.result.EditPrinterGroupUseTypeResult;
import com.echothree.control.user.printer.common.spec.PrinterGroupUseTypeSpec;
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
    path = "/Configuration/PrinterGroupUseType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "PrinterGroupUseTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/PrinterGroupUseType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/printergroupusetype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, PrinterGroupUseTypeSpec, PrinterGroupUseTypeEdit, EditPrinterGroupUseTypeForm, EditPrinterGroupUseTypeResult> {
    
    @Override
    protected PrinterGroupUseTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = PrinterUtil.getHome().getPrinterGroupUseTypeSpec();
        
        spec.setPrinterGroupUseTypeName(findParameter(request, ParameterConstants.ORIGINAL_PRINTER_GROUP_USE_TYPE_NAME, actionForm.getOriginalPrinterGroupUseTypeName()));
        
        return spec;
    }
    
    @Override
    protected PrinterGroupUseTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = PrinterUtil.getHome().getPrinterGroupUseTypeEdit();

        edit.setPrinterGroupUseTypeName(actionForm.getPrinterGroupUseTypeName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditPrinterGroupUseTypeForm getForm()
            throws NamingException {
        return PrinterUtil.getHome().getEditPrinterGroupUseTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditPrinterGroupUseTypeResult result, PrinterGroupUseTypeSpec spec, PrinterGroupUseTypeEdit edit) {
        actionForm.setOriginalPrinterGroupUseTypeName(spec.getPrinterGroupUseTypeName());
        actionForm.setPrinterGroupUseTypeName(edit.getPrinterGroupUseTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPrinterGroupUseTypeForm commandForm)
            throws Exception {
        return PrinterUtil.getHome().editPrinterGroupUseType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditPrinterGroupUseTypeResult result) {
        request.setAttribute(AttributeConstants.PRINTER_GROUP_USE_TYPE, result.getPrinterGroupUseType());
    }

}
