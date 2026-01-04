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

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.edit.CustomerEdit;
import com.echothree.control.user.customer.common.form.EditCustomerForm;
import com.echothree.control.user.customer.common.result.EditCustomerResult;
import com.echothree.control.user.customer.common.spec.CustomerSpec;
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
    path = "/Customer/Customer/CustomerEdit",
    mappingClass = SecureActionMapping.class,
    name = "CustomerEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/Customer/Review", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customer/customerEdit.jsp")
    }
)
public class CustomerEditAction
        extends MainBaseEditAction<CustomerEditActionForm, CustomerSpec, CustomerEdit, EditCustomerForm, EditCustomerResult> {
    
    @Override
    protected CustomerSpec getSpec(HttpServletRequest request, CustomerEditActionForm actionForm)
            throws NamingException {
        var spec = CustomerUtil.getHome().getCustomerSpec();
        var customerName = request.getParameter(ParameterConstants.CUSTOMER_NAME);

        if(customerName == null) {
            customerName = actionForm.getCustomerName();
        }

        spec.setCustomerName(customerName);
        
        return spec;
    }
    
    @Override
    protected CustomerEdit getEdit(HttpServletRequest request, CustomerEditActionForm actionForm)
            throws NamingException {
        var edit = CustomerUtil.getHome().getCustomerEdit();

        edit.setCustomerTypeName(actionForm.getCustomerTypeChoice());
        edit.setPersonalTitleId(actionForm.getPersonalTitleChoice());
        edit.setFirstName(actionForm.getFirstName());
        edit.setMiddleName(actionForm.getMiddleName());
        edit.setLastName(actionForm.getLastName());
        edit.setNameSuffixId(actionForm.getNameSuffixChoice());
        edit.setName(actionForm.getName());
        edit.setPreferredLanguageIsoName(actionForm.getLanguageChoice());
        edit.setPreferredCurrencyIsoName(actionForm.getCurrencyChoice());
        edit.setPreferredJavaTimeZoneName(actionForm.getTimeZoneChoice());
        edit.setPreferredDateTimeFormatName(actionForm.getDateTimeFormatChoice());
        edit.setCancellationPolicyName(actionForm.getCancellationPolicyChoice());
        edit.setReturnPolicyName(actionForm.getReturnPolicyChoice());
        edit.setArGlAccountName(actionForm.getArGlAccountChoice());
        edit.setHoldUntilComplete(actionForm.getHoldUntilComplete().toString());
        edit.setAllowBackorders(actionForm.getAllowBackorders().toString());
        edit.setAllowSubstitutions(actionForm.getAllowSubstitutions().toString());
        edit.setAllowCombiningShipments(actionForm.getAllowCombiningShipments().toString());
        edit.setRequireReference(actionForm.getRequireReference().toString());
        edit.setAllowReferenceDuplicates(actionForm.getAllowReferenceDuplicates().toString());
        edit.setReferenceValidationPattern(actionForm.getReferenceValidationPattern());

        return edit;
    }
    
    @Override
    protected EditCustomerForm getForm()
            throws NamingException {
        return CustomerUtil.getHome().getEditCustomerForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, CustomerEditActionForm actionForm, EditCustomerResult result, CustomerSpec spec, CustomerEdit edit) {
        actionForm.setCustomerName(spec.getCustomerName());
        actionForm.setCustomerTypeChoice(edit.getCustomerTypeName());
        actionForm.setPersonalTitleChoice(edit.getPersonalTitleId());
        actionForm.setFirstName(edit.getFirstName());
        actionForm.setMiddleName(edit.getMiddleName());
        actionForm.setLastName(edit.getLastName());
        actionForm.setNameSuffixChoice(edit.getNameSuffixId());
        actionForm.setName(edit.getName());
        actionForm.setLanguageChoice(edit.getPreferredLanguageIsoName());
        actionForm.setCurrencyChoice(edit.getPreferredCurrencyIsoName());
        actionForm.setTimeZoneChoice(edit.getPreferredJavaTimeZoneName());
        actionForm.setDateTimeFormatChoice(edit.getPreferredDateTimeFormatName());
        actionForm.setCancellationPolicyChoice(edit.getCancellationPolicyName());
        actionForm.setReturnPolicyChoice(edit.getReturnPolicyName());
        actionForm.setArGlAccountChoice(edit.getArGlAccountName());
        actionForm.setHoldUntilComplete(Boolean.valueOf(edit.getHoldUntilComplete()));
        actionForm.setAllowBackorders(Boolean.valueOf(edit.getAllowBackorders()));
        actionForm.setAllowSubstitutions(Boolean.valueOf(edit.getAllowSubstitutions()));
        actionForm.setAllowCombiningShipments(Boolean.valueOf(edit.getAllowCombiningShipments()));
        actionForm.setRequireReference(Boolean.valueOf(edit.getRequireReference()));
        actionForm.setAllowReferenceDuplicates(Boolean.valueOf(edit.getAllowReferenceDuplicates()));
        actionForm.setReferenceValidationPattern(edit.getReferenceValidationPattern());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditCustomerForm commandForm)
            throws Exception {
        var commandResult = CustomerUtil.getHome().editCustomer(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditCustomerResult)executionResult.getResult();

        request.setAttribute(AttributeConstants.CUSTOMER, result.getCustomer());
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(CustomerEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CUSTOMER_NAME, actionForm.getCustomerName());
    }
    
}
