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

package com.echothree.ui.web.main.action.customer.customertypecreditlimit;

import com.echothree.control.user.term.common.TermUtil;
import com.echothree.control.user.term.common.result.EditCustomerTypeCreditLimitResult;
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
    path = "/Customer/CustomerTypeCreditLimit/Edit",
    mappingClass = SecureActionMapping.class,
    name = "CustomerTypeCreditLimitEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/CustomerTypeCreditLimit/Main", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customertypecreditlimit/edit.jsp")
    }
)
public class EditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var customerTypeName = request.getParameter(ParameterConstants.CUSTOMER_TYPE_NAME);
        
        try {
            var currencyIsoName = request.getParameter(ParameterConstants.CURRENCY_ISO_NAME);
            
            if(forwardKey == null) {
                var actionForm = (EditActionForm)form;
                var commandForm = TermUtil.getHome().getEditCustomerTypeCreditLimitForm();
                var spec = TermUtil.getHome().getCustomerTypeCreditLimitSpec();
                
                if(customerTypeName == null)
                    customerTypeName = actionForm.getCustomerTypeName();
                if(currencyIsoName == null)
                    currencyIsoName = actionForm.getCurrencyIsoName();
                
                commandForm.setSpec(spec);
                spec.setCustomerTypeName(customerTypeName);
                spec.setCurrencyIsoName(currencyIsoName);
                
                if(wasPost(request)) {
                    var edit = TermUtil.getHome().getCustomerTypeCreditLimitEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setPotentialCreditLimit(actionForm.getPotentialCreditLimit());
                    edit.setCreditLimit(actionForm.getCreditLimit());

                    var commandResult = TermUtil.getHome().editCustomerTypeCreditLimit(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            var result = (EditCustomerTypeCreditLimitResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = TermUtil.getHome().editCustomerTypeCreditLimit(getUserVisitPK(request), commandForm);
                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditCustomerTypeCreditLimitResult)executionResult.getResult();
                    
                    if(result != null) {
                        var edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setCustomerTypeName(customerTypeName);
                            actionForm.setCurrencyIsoName(currencyIsoName);
                            actionForm.setPotentialCreditLimit(edit.getPotentialCreditLimit());
                            actionForm.setCreditLimit(edit.getCreditLimit());
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
            request.setAttribute(AttributeConstants.CUSTOMER_TYPE_NAME, customerTypeName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.CUSTOMER_TYPE_NAME, customerTypeName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}