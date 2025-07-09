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

package com.echothree.ui.web.main.action.customer.customertype;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.ui.web.main.framework.ForwardConstants;
import com.echothree.ui.web.main.framework.MainBaseAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Customer/CustomerType/Add",
    mappingClass = SecureActionMapping.class,
    name = "CustomerTypeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/CustomerType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customertype/add.jsp")
    }
)
public class AddAction
        extends MainBaseAction<ActionForm> {
    
    @Override
    public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        String forwardKey = null;
        
        try {
            if(forwardKey == null) {
                var actionForm = (AddActionForm)form;
                
                if(wasPost(request)) {
                    var commandForm = CustomerUtil.getHome().getCreateCustomerTypeForm();
                    
                    commandForm.setCustomerTypeName(actionForm.getCustomerTypeName());
                    commandForm.setCustomerSequenceName(actionForm.getCustomerSequenceChoice());
                    commandForm.setDefaultSourceName(actionForm.getDefaultSourceChoice());
                    commandForm.setDefaultTermName(actionForm.getDefaultTermChoice());
                    commandForm.setDefaultFreeOnBoardName(actionForm.getDefaultFreeOnBoardChoice());
                    commandForm.setDefaultCancellationPolicyName(actionForm.getDefaultCancellationPolicyChoice());
                    commandForm.setDefaultReturnPolicyName(actionForm.getDefaultReturnPolicyChoice());
                    commandForm.setDefaultCustomerStatusChoice(actionForm.getDefaultCustomerStatusChoice());
                    commandForm.setDefaultCustomerCreditStatusChoice(actionForm.getDefaultCustomerCreditStatusChoice());
                    commandForm.setDefaultArGlAccountName(actionForm.getDefaultArGlAccountChoice());
                    commandForm.setDefaultHoldUntilComplete(actionForm.getDefaultHoldUntilComplete().toString());
                    commandForm.setDefaultAllowBackorders(actionForm.getDefaultAllowBackorders().toString());
                    commandForm.setDefaultAllowSubstitutions(actionForm.getDefaultAllowSubstitutions().toString());
                    commandForm.setDefaultAllowCombiningShipments(actionForm.getDefaultAllowCombiningShipments().toString());
                    commandForm.setDefaultRequireReference(actionForm.getDefaultRequireReference().toString());
                    commandForm.setDefaultAllowReferenceDuplicates(actionForm.getDefaultAllowReferenceDuplicates().toString());
                    commandForm.setDefaultReferenceValidationPattern(actionForm.getDefaultReferenceValidationPattern());
                    commandForm.setDefaultTaxable(actionForm.getDefaultTaxable().toString());
                    commandForm.setAllocationPriorityName(actionForm.getAllocationPriorityChoice());
                    commandForm.setIsDefault(actionForm.getIsDefault().toString());
                    commandForm.setSortOrder(actionForm.getSortOrder());
                    commandForm.setDescription(actionForm.getDescription());

                    var commandResult = CustomerUtil.getHome().createCustomerType(getUserVisitPK(request), commandForm);
                    
                    if(commandResult.hasErrors()) {
                        setCommandResultAttribute(request, commandResult);
                        forwardKey = ForwardConstants.FORM;
                    } else {
                        forwardKey = ForwardConstants.DISPLAY;
                    }
                } else {
                    actionForm.setDefaultAllowBackorders(true);
                    actionForm.setDefaultAllowSubstitutions(true);
                    actionForm.setDefaultAllowCombiningShipments(true);
                    actionForm.setDefaultTaxable(true);
                    actionForm.setSortOrder("1");
                    forwardKey = ForwardConstants.FORM;
                }
            }
        } catch (NamingException ne) {
            forwardKey = ForwardConstants.ERROR_500;
        }
        
        return mapping.findForward(forwardKey);
    }
    
}