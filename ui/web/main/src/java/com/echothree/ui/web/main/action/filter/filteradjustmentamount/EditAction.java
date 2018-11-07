// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.web.main.action.filter.filteradjustmentamount;

import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.common.edit.FilterAdjustmentAmountEdit;
import com.echothree.control.user.filter.common.form.EditFilterAdjustmentAmountForm;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentAmountResult;
import com.echothree.control.user.filter.common.spec.FilterAdjustmentAmountSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.CustomActionForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Filter/FilterAdjustmentAmount/Edit",
    mappingClass = SecureActionMapping.class,
    name = "FilterAdjustmentAmountEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Filter/FilterAdjustmentAmount/Main", redirect = true),
        @SproutForward(name = "Form", path = "/filter/filteradjustmentamount/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        String filterKindName = request.getParameter(ParameterConstants.FILTER_KIND_NAME);
        String filterAdjustmentName = request.getParameter(ParameterConstants.FILTER_ADJUSTMENT_NAME);
        String unitOfMeasureName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_NAME);
        String currencyIsoName = request.getParameter(ParameterConstants.CURRENCY_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                EditActionForm actionForm = (EditActionForm)form;
                EditFilterAdjustmentAmountForm commandForm = FilterUtil.getHome().getEditFilterAdjustmentAmountForm();
                FilterAdjustmentAmountSpec spec = FilterUtil.getHome().getFilterAdjustmentAmountSpec();
                
                if(filterKindName == null)
                    filterKindName = actionForm.getFilterKindName();
                if(filterAdjustmentName == null)
                    filterAdjustmentName = actionForm.getFilterAdjustmentName();
                if(unitOfMeasureName == null)
                    unitOfMeasureName = actionForm.getUnitOfMeasureChoice();
                if(currencyIsoName == null)
                    currencyIsoName = actionForm.getCurrencyChoice();
                
                commandForm.setSpec(spec);
                spec.setFilterKindName(filterKindName);
                spec.setFilterAdjustmentName(filterAdjustmentName);
                spec.setUnitOfMeasureName(unitOfMeasureName);
                spec.setCurrencyIsoName(currencyIsoName);
                
                if(wasPost(request)) {
                    FilterAdjustmentAmountEdit edit = FilterUtil.getHome().getFilterAdjustmentAmountEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setAmount(actionForm.getAmount());
                    
                    CommandResult commandResult = FilterUtil.getHome().editFilterAdjustmentAmount(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        ExecutionResult executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            EditFilterAdjustmentAmountResult result = (EditFilterAdjustmentAmountResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);
                    
                    CommandResult commandResult = FilterUtil.getHome().editFilterAdjustmentAmount(getUserVisitPK(request), commandForm);
                    ExecutionResult executionResult = commandResult.getExecutionResult();
                    EditFilterAdjustmentAmountResult result = (EditFilterAdjustmentAmountResult)executionResult.getResult();
                    
                    if(result != null) {
                        FilterAdjustmentAmountEdit edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setFilterKindName(filterKindName);
                            actionForm.setFilterAdjustmentName(filterAdjustmentName);
                            actionForm.setUnitOfMeasureChoice(unitOfMeasureName);
                            actionForm.setCurrencyChoice(currencyIsoName);
                            actionForm.setAmount(edit.getAmount());
                        }
                        
                        request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                    }
                    
                    setCommandResultAttribute(request, commandResult);
                    
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        CustomActionForward customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
        if(forwardKey.equals(ForwardConstants.FORM)) {
            request.setAttribute(AttributeConstants.FILTER_KIND_NAME, filterKindName);
            request.setAttribute(AttributeConstants.FILTER_ADJUSTMENT_NAME, filterAdjustmentName);
            request.setAttribute(AttributeConstants.UNIT_OF_MEASURE_NAME, unitOfMeasureName);
            request.setAttribute(AttributeConstants.CURRENCY_ISO_NAME, currencyIsoName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(2);
            
            parameters.put(ParameterConstants.FILTER_KIND_NAME, filterKindName);
            parameters.put(ParameterConstants.FILTER_ADJUSTMENT_NAME, filterAdjustmentName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}