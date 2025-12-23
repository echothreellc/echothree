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

package com.echothree.ui.web.main.action.customer.customertypeshippingmethod;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.customer.common.edit.CustomerTypeShippingMethodEdit;
import com.echothree.control.user.customer.common.form.EditCustomerTypeShippingMethodForm;
import com.echothree.control.user.customer.common.result.EditCustomerTypeShippingMethodResult;
import com.echothree.control.user.customer.common.spec.CustomerTypeShippingMethodSpec;
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
    path = "/Customer/CustomerTypeShippingMethod/Edit",
    mappingClass = SecureActionMapping.class,
    name = "CustomerTypeShippingMethodEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/CustomerTypeShippingMethod/Main", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customertypeshippingmethod/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, CustomerTypeShippingMethodSpec, CustomerTypeShippingMethodEdit, EditCustomerTypeShippingMethodForm, EditCustomerTypeShippingMethodResult> {
    
    @Override
    protected CustomerTypeShippingMethodSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = CustomerUtil.getHome().getCustomerTypeShippingMethodSpec();
        
        spec.setCustomerTypeName(findParameter(request, ParameterConstants.CUSTOMER_TYPE_NAME, actionForm.getCustomerTypeName()));
        spec.setShippingMethodName(findParameter(request, ParameterConstants.SHIPPING_METHOD_NAME, actionForm.getShippingMethodName()));
        
        return spec;
    }
    
    @Override
    protected CustomerTypeShippingMethodEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = CustomerUtil.getHome().getCustomerTypeShippingMethodEdit();

        edit.setDefaultSelectionPriority(actionForm.getDefaultSelectionPriority());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());

        return edit;
    }
    
    @Override
    protected EditCustomerTypeShippingMethodForm getForm()
            throws NamingException {
        return CustomerUtil.getHome().getEditCustomerTypeShippingMethodForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditCustomerTypeShippingMethodResult result, CustomerTypeShippingMethodSpec spec, CustomerTypeShippingMethodEdit edit) {
        actionForm.setCustomerTypeName(spec.getCustomerTypeName());
        actionForm.setShippingMethodName(spec.getShippingMethodName());
        actionForm.setDefaultSelectionPriority(edit.getDefaultSelectionPriority());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditCustomerTypeShippingMethodForm commandForm)
            throws Exception {
        return CustomerUtil.getHome().editCustomerTypeShippingMethod(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CUSTOMER_TYPE_NAME, actionForm.getCustomerTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditCustomerTypeShippingMethodResult result) {
        request.setAttribute(AttributeConstants.CUSTOMER_TYPE_SHIPPING_METHOD, result.getCustomerTypeShippingMethod());
    }

}
