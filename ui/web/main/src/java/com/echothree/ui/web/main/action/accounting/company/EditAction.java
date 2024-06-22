// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.main.action.accounting.company;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.edit.CompanyEdit;
import com.echothree.control.user.party.common.form.EditCompanyForm;
import com.echothree.control.user.party.common.result.EditCompanyResult;
import com.echothree.control.user.party.common.spec.CompanySpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Accounting/Company/Edit",
    mappingClass = SecureActionMapping.class,
    name = "CompanyEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/Company/Main", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/company/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String originalCompanyName = request.getParameter(ParameterConstants.ORIGINAL_COMPANY_NAME);
        EditActionForm actionForm = (EditActionForm)form;
        EditCompanyForm commandForm = PartyUtil.getHome().getEditCompanyForm();
        CompanySpec spec = PartyUtil.getHome().getCompanySpec();
        
        if(originalCompanyName == null)
            originalCompanyName = actionForm.getOriginalCompanyName();
        
        commandForm.setSpec(spec);
        spec.setCompanyName(originalCompanyName);
        
        if(wasPost(request)) {
            boolean wasCanceled = wasCanceled(request);
            
            if(wasCanceled) {
                commandForm.setEditMode(EditMode.ABANDON);
            } else {
                CompanyEdit edit = PartyUtil.getHome().getCompanyEdit();

                commandForm.setEditMode(EditMode.UPDATE);
                commandForm.setEdit(edit);

                edit.setCompanyName(actionForm.getCompanyName());
                edit.setName(actionForm.getName());
                edit.setPreferredLanguageIsoName(actionForm.getLanguageChoice());
                edit.setPreferredCurrencyIsoName(actionForm.getCurrencyChoice());
                edit.setPreferredJavaTimeZoneName(actionForm.getTimeZoneChoice());
                edit.setPreferredDateTimeFormatName(actionForm.getDateTimeFormatChoice());
                edit.setIsDefault(actionForm.getIsDefault().toString());
                edit.setSortOrder(actionForm.getSortOrder());
            }
            
            CommandResult commandResult = PartyUtil.getHome().editCompany(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors() && !wasCanceled) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    EditCompanyResult result = (EditCompanyResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = PartyUtil.getHome().editCompany(getUserVisitPK(request), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            EditCompanyResult result = (EditCompanyResult)executionResult.getResult();
            
            if(result != null) {
                CompanyEdit edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setOriginalCompanyName(edit.getCompanyName());
                    actionForm.setCompanyName(edit.getCompanyName());
                    actionForm.setName(edit.getName());
                    actionForm.setLanguageChoice(edit.getPreferredLanguageIsoName());
                    actionForm.setCurrencyChoice(edit.getPreferredCurrencyIsoName());
                    actionForm.setTimeZoneChoice(edit.getPreferredJavaTimeZoneName());
                    actionForm.setDateTimeFormatChoice(edit.getPreferredDateTimeFormatName());
                    actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                    actionForm.setSortOrder(edit.getSortOrder());
                }
                
                request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
            }
            
            setCommandResultAttribute(request, commandResult);
            
            forwardKey = ForwardConstants.FORM;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}
