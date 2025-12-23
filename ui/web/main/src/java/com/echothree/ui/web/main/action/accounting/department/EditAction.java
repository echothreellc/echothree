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

package com.echothree.ui.web.main.action.accounting.department;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.EditDepartmentResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.EditMode;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Accounting/Department/Edit",
    mappingClass = SecureActionMapping.class,
    name = "DepartmentEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Accounting/Department/Main", redirect = true),
        @SproutForward(name = "Form", path = "/accounting/department/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<EditActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, EditActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey;
        var companyName = request.getParameter(ParameterConstants.COMPANY_NAME);
        var divisionName = request.getParameter(ParameterConstants.DIVISION_NAME);
        var originalDepartmentName = request.getParameter(ParameterConstants.ORIGINAL_DEPARTMENT_NAME);
        var commandForm = PartyUtil.getHome().getEditDepartmentForm();
        var spec = PartyUtil.getHome().getDepartmentSpec();
        
        if(companyName == null)
            companyName = actionForm.getCompanyName();
        if(divisionName == null)
            divisionName = actionForm.getDivisionName();
        if(originalDepartmentName == null)
            originalDepartmentName = actionForm.getOriginalDepartmentName();
        
        commandForm.setSpec(spec);
        spec.setCompanyName(companyName);
        spec.setDivisionName(divisionName);
        spec.setDepartmentName(originalDepartmentName);
        
        if(wasPost(request)) {
            var wasCanceled = wasCanceled(request);
            
            if(wasCanceled) {
                commandForm.setEditMode(EditMode.ABANDON);
            } else {
                var edit = PartyUtil.getHome().getDepartmentEdit();

                commandForm.setEditMode(EditMode.UPDATE);
                commandForm.setEdit(edit);

                edit.setDepartmentName(actionForm.getDepartmentName());
                edit.setName(actionForm.getName());
                edit.setPreferredLanguageIsoName(actionForm.getLanguageChoice());
                edit.setPreferredCurrencyIsoName(actionForm.getCurrencyChoice());
                edit.setPreferredJavaTimeZoneName(actionForm.getTimeZoneChoice());
                edit.setPreferredDateTimeFormatName(actionForm.getDateTimeFormatChoice());
                edit.setIsDefault(actionForm.getIsDefault().toString());
                edit.setSortOrder(actionForm.getSortOrder());
            }

            var commandResult = PartyUtil.getHome().editDepartment(getUserVisitPK(request), commandForm);
            
            if(commandResult.hasErrors() && !wasCanceled) {
                var executionResult = commandResult.getExecutionResult();
                
                if(executionResult != null) {
                    var result = (EditDepartmentResult)executionResult.getResult();
                    
                    request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                }
                
                setCommandResultAttribute(request, commandResult);
                
                forwardKey = ForwardConstants.FORM;
            } else {
                forwardKey = ForwardConstants.DISPLAY;
            }
        } else {
            commandForm.setEditMode(EditMode.LOCK);

            var commandResult = PartyUtil.getHome().editDepartment(getUserVisitPK(request), commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (EditDepartmentResult)executionResult.getResult();
            
            if(result != null) {
                var edit = result.getEdit();
                
                if(edit != null) {
                    actionForm.setCompanyName(companyName);
                    actionForm.setDivisionName(divisionName);
                    actionForm.setOriginalDepartmentName(edit.getDepartmentName());
                    actionForm.setDepartmentName(edit.getDepartmentName());
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

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.COMPANY_NAME, companyName);
            request.setAttribute(AttributeConstants.DIVISION_NAME, divisionName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.COMPANY_NAME, companyName);
            parameters.put(ParameterConstants.DIVISION_NAME, divisionName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}