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

package com.echothree.ui.web.main.action.customer.customertypepaymentmethod;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.edit.CustomerTypePaymentMethodEdit;
import com.echothree.control.user.customer.common.form.EditCustomerTypePaymentMethodForm;
import com.echothree.control.user.customer.common.result.EditCustomerTypePaymentMethodResult;
import com.echothree.control.user.customer.common.spec.CustomerTypePaymentMethodSpec;
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
    path = "/Customer/CustomerTypePaymentMethod/Edit",
    mappingClass = SecureActionMapping.class,
    name = "CustomerTypePaymentMethodEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/CustomerTypePaymentMethod/Main", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customertypepaymentmethod/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, CustomerTypePaymentMethodSpec, CustomerTypePaymentMethodEdit, EditCustomerTypePaymentMethodForm, EditCustomerTypePaymentMethodResult> {
    
    @Override
    protected CustomerTypePaymentMethodSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = CustomerUtil.getHome().getCustomerTypePaymentMethodSpec();
        
        spec.setCustomerTypeName(findParameter(request, ParameterConstants.CUSTOMER_TYPE_NAME, actionForm.getCustomerTypeName()));
        spec.setPaymentMethodName(findParameter(request, ParameterConstants.PAYMENT_METHOD_NAME, actionForm.getPaymentMethodName()));
        
        return spec;
    }
    
    @Override
    protected CustomerTypePaymentMethodEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = CustomerUtil.getHome().getCustomerTypePaymentMethodEdit();

        edit.setDefaultSelectionPriority(actionForm.getDefaultSelectionPriority());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());

        return edit;
    }
    
    @Override
    protected EditCustomerTypePaymentMethodForm getForm()
            throws NamingException {
        return CustomerUtil.getHome().getEditCustomerTypePaymentMethodForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditCustomerTypePaymentMethodResult result, CustomerTypePaymentMethodSpec spec, CustomerTypePaymentMethodEdit edit) {
        actionForm.setCustomerTypeName(spec.getCustomerTypeName());
        actionForm.setPaymentMethodName(spec.getPaymentMethodName());
        actionForm.setDefaultSelectionPriority(edit.getDefaultSelectionPriority());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditCustomerTypePaymentMethodForm commandForm)
            throws Exception {
        return CustomerUtil.getHome().editCustomerTypePaymentMethod(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CUSTOMER_TYPE_NAME, actionForm.getCustomerTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditCustomerTypePaymentMethodResult result) {
        request.setAttribute(AttributeConstants.CUSTOMER_TYPE_PAYMENT_METHOD, result.getCustomerTypePaymentMethod());
    }

}
