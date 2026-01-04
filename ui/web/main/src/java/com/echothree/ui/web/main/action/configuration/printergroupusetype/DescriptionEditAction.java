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

package com.echothree.ui.web.main.action.configuration.printergroupusetype;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.control.user.printer.common.edit.PrinterGroupUseTypeDescriptionEdit;
import com.echothree.control.user.printer.common.form.EditPrinterGroupUseTypeDescriptionForm;
import com.echothree.control.user.printer.common.result.EditPrinterGroupUseTypeDescriptionResult;
import com.echothree.control.user.printer.common.spec.PrinterGroupUseTypeDescriptionSpec;
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
    path = "/Configuration/PrinterGroupUseType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "PrinterGroupUseTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/PrinterGroupUseType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/printergroupusetype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, PrinterGroupUseTypeDescriptionSpec, PrinterGroupUseTypeDescriptionEdit, EditPrinterGroupUseTypeDescriptionForm, EditPrinterGroupUseTypeDescriptionResult> {
    
    @Override
    protected PrinterGroupUseTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = PrinterUtil.getHome().getPrinterGroupUseTypeDescriptionSpec();
        
        spec.setPrinterGroupUseTypeName(findParameter(request, ParameterConstants.PRINTER_GROUP_USE_TYPE_NAME, actionForm.getPrinterGroupUseTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected PrinterGroupUseTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = PrinterUtil.getHome().getPrinterGroupUseTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditPrinterGroupUseTypeDescriptionForm getForm()
            throws NamingException {
        return PrinterUtil.getHome().getEditPrinterGroupUseTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditPrinterGroupUseTypeDescriptionResult result, PrinterGroupUseTypeDescriptionSpec spec, PrinterGroupUseTypeDescriptionEdit edit) {
        actionForm.setPrinterGroupUseTypeName(spec.getPrinterGroupUseTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPrinterGroupUseTypeDescriptionForm commandForm)
            throws Exception {
        return PrinterUtil.getHome().editPrinterGroupUseTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PRINTER_GROUP_USE_TYPE_NAME, actionForm.getPrinterGroupUseTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditPrinterGroupUseTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.PRINTER_GROUP_USE_TYPE_DESCRIPTION, result.getPrinterGroupUseTypeDescription());
    }

}
