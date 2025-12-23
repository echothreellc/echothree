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

package com.echothree.ui.web.main.action.filter.filteradjustmentpercent;

import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.common.result.EditFilterAdjustmentPercentResult;
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
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Filter/FilterAdjustmentPercent/Edit",
    mappingClass = SecureActionMapping.class,
    name = "FilterAdjustmentPercentEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Filter/FilterAdjustmentPercent/Main", redirect = true),
        @SproutForward(name = "Form", path = "/filter/filteradjustmentpercent/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String forwardKey = null;
        var filterKindName = request.getParameter(ParameterConstants.FILTER_KIND_NAME);
        var filterAdjustmentName = request.getParameter(ParameterConstants.FILTER_ADJUSTMENT_NAME);
        var unitOfMeasureName = request.getParameter(ParameterConstants.UNIT_OF_MEASURE_NAME);
        var currencyIsoName = request.getParameter(ParameterConstants.CURRENCY_ISO_NAME);
        
        try {
            if(forwardKey == null) {
                var actionForm = (EditActionForm)form;
                var commandForm = FilterUtil.getHome().getEditFilterAdjustmentPercentForm();
                var spec = FilterUtil.getHome().getFilterAdjustmentPercentSpec();
                
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
                    var edit = FilterUtil.getHome().getFilterAdjustmentPercentEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    
                    edit.setPercent(actionForm.getPercent());

                    var commandResult = FilterUtil.getHome().editFilterAdjustmentPercent(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            var result = (EditFilterAdjustmentPercentResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = FilterUtil.getHome().editFilterAdjustmentPercent(getUserVisitPK(request), commandForm);
                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditFilterAdjustmentPercentResult)executionResult.getResult();
                    
                    if(result != null) {
                        var edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setFilterKindName(filterKindName);
                            actionForm.setFilterAdjustmentName(filterAdjustmentName);
                            actionForm.setUnitOfMeasureChoice(unitOfMeasureName);
                            actionForm.setCurrencyChoice(currencyIsoName);
                            actionForm.setPercent(edit.getPercent());
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

        var customActionForward = new CustomActionForward(mapping.findForward(forwardKey));
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