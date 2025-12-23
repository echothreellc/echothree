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

package com.echothree.ui.web.main.action.customer.customer;

import com.echothree.control.user.term.common.TermUtil;
import com.echothree.control.user.term.common.result.EditPartyCreditLimitResult;
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
    path = "/Customer/Customer/PartyCreditLimitEdit",
    mappingClass = SecureActionMapping.class,
    name = "PartyCreditLimitEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/Customer/Review", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customer/partyCreditLimitEdit.jsp")
    }
)
public class PartyCreditLimitEditAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        var customerName = request.getParameter(ParameterConstants.CUSTOMER_NAME);
        
        try {
            var partyName = request.getParameter(ParameterConstants.PARTY_NAME);
            var currencyIsoName = request.getParameter(ParameterConstants.CURRENCY_ISO_NAME);
            
            if(forwardKey == null) {
                var actionForm = (PartyCreditLimitEditActionForm)form;
                var commandForm = TermUtil.getHome().getEditPartyCreditLimitForm();
                var spec = TermUtil.getHome().getPartyCreditLimitSpec();
                
                if(partyName == null)
                    partyName = actionForm.getPartyName();
                if(customerName == null)
                    customerName = actionForm.getCustomerName();
                if(currencyIsoName == null)
                    currencyIsoName = actionForm.getCurrencyIsoName();
                
                commandForm.setSpec(spec);
                spec.setPartyName(partyName);
                spec.setCurrencyIsoName(currencyIsoName);
                
                if(wasPost(request)) {
                    var edit = TermUtil.getHome().getPartyCreditLimitEdit();
                    
                    commandForm.setEditMode(EditMode.UPDATE);
                    commandForm.setEdit(edit);
                    edit.setPotentialCreditLimit(actionForm.getPotentialCreditLimit());
                    edit.setCreditLimit(actionForm.getCreditLimit());

                    var commandResult = TermUtil.getHome().editPartyCreditLimit(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        
                        if(executionResult != null) {
                            var result = (EditPartyCreditLimitResult)executionResult.getResult();
                            
                            request.setAttribute(AttributeConstants.ENTITY_LOCK, result.getEntityLock());
                        }
                        
                        setCommandResultAttribute(request, commandResult);
                        
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = TermUtil.getHome().editPartyCreditLimit(getUserVisitPK(request), commandForm);
                    var executionResult = commandResult.getExecutionResult();
                    var result = (EditPartyCreditLimitResult)executionResult.getResult();
                    
                    if(result != null) {
                        var edit = result.getEdit();
                        
                        if(edit != null) {
                            actionForm.setPartyName(partyName);
                            actionForm.setCustomerName(customerName);
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
            request.setAttribute(AttributeConstants.CUSTOMER_NAME, customerName);
        } else if(forwardKey.equals(ForwardConstants.DISPLAY)) {
            Map<String, String> parameters = new HashMap<>(1);
            
            parameters.put(ParameterConstants.CUSTOMER_NAME, customerName);
            customActionForward.setParameters(parameters);
        }
        
        return customActionForward;
    }
    
}