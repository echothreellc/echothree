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

package com.echothree.ui.web.main.action.accounting.companyprintergroupuse;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/Accounting/CompanyPrinterGroupUse/Delete",
    mappingClass = SecureActionMapping.class,
    name = "CompanyPrinterGroupUseDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/CompanyPrinterGroupUse/Main", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/companyprintergroupuse/delete.jsp")
    }
)
public class DeleteAction
        extends MainBaseDeleteAction<DeleteActionForm> {
    
    @Override
    public String getEntityTypeName(final DeleteActionForm actionForm) {
        return EntityTypes.PartyPrinterGroupUse.name();
    }
    
    @Override
    public void setupParameters(DeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        actionForm.setPrinterGroupUseTypeName(findParameter(request, ParameterConstants.PRINTER_GROUP_USE_TYPE_NAME, actionForm.getPrinterGroupUseTypeName()));
    }
    
    @Override
    public void setupTransfer(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        BaseCompanyPrinterGroupUseAction.setupPartyPrinterGroupUseTransfer(request, actionForm.getPartyName(), actionForm.getPrinterGroupUseTypeName());
        BaseCompanyPrinterGroupUseAction.setupCompany(request, actionForm.getPartyName());
    }
    
    @Override
    public CommandResult doDelete(DeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = PrinterUtil.getHome().getDeletePartyPrinterGroupUseForm();

        commandForm.setPartyName(actionForm.getPartyName());
        commandForm.setPrinterGroupUseTypeName(actionForm.getPrinterGroupUseTypeName());

        return PrinterUtil.getHome().deletePartyPrinterGroupUse(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
}
