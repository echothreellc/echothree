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

package com.echothree.ui.web.main.action.contactlist.contactlistgroup;

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.edit.CustomerTypeContactListGroupEdit;
import com.echothree.control.user.contactlist.common.form.EditCustomerTypeContactListGroupForm;
import com.echothree.control.user.contactlist.common.result.EditCustomerTypeContactListGroupResult;
import com.echothree.control.user.contactlist.common.spec.CustomerTypeContactListGroupSpec;
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
    path = "/ContactList/ContactListGroup/CustomerTypeContactListGroupEdit",
    mappingClass = SecureActionMapping.class,
    name = "CustomerTypeContactListGroupEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactListGroup/CustomerTypeContactListGroup", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlistgroup/customerTypeContactListGroupEdit.jsp")
    }
)
public class CustomerTypeContactListGroupEditAction
        extends MainBaseEditAction<CustomerTypeContactListGroupEditActionForm, CustomerTypeContactListGroupSpec, CustomerTypeContactListGroupEdit, EditCustomerTypeContactListGroupForm, EditCustomerTypeContactListGroupResult> {
    
    @Override
    protected CustomerTypeContactListGroupSpec getSpec(HttpServletRequest request, CustomerTypeContactListGroupEditActionForm actionForm)
            throws NamingException {
        var spec = ContactListUtil.getHome().getCustomerTypeContactListGroupSpec();
        
        spec.setContactListGroupName(findParameter(request, ParameterConstants.CONTACT_LIST_GROUP_NAME, actionForm.getContactListGroupName()));
        spec.setCustomerTypeName(findParameter(request, ParameterConstants.CUSTOMER_TYPE_NAME, actionForm.getCustomerTypeName()));
        
        return spec;
    }
    
    @Override
    protected CustomerTypeContactListGroupEdit getEdit(HttpServletRequest request, CustomerTypeContactListGroupEditActionForm actionForm)
            throws NamingException {
        var edit = ContactListUtil.getHome().getCustomerTypeContactListGroupEdit();

        edit.setAddWhenCreated(actionForm.getAddWhenCreated().toString());

        return edit;
    }
    
    @Override
    protected EditCustomerTypeContactListGroupForm getForm()
            throws NamingException {
        return ContactListUtil.getHome().getEditCustomerTypeContactListGroupForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, CustomerTypeContactListGroupEditActionForm actionForm, EditCustomerTypeContactListGroupResult result, CustomerTypeContactListGroupSpec spec, CustomerTypeContactListGroupEdit edit) {
        actionForm.setContactListGroupName(spec.getContactListGroupName());
        actionForm.setCustomerTypeName(spec.getCustomerTypeName());
        actionForm.setAddWhenCreated(Boolean.valueOf(edit.getAddWhenCreated()));
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditCustomerTypeContactListGroupForm commandForm)
            throws Exception {
        return ContactListUtil.getHome().editCustomerTypeContactListGroup(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(CustomerTypeContactListGroupEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTACT_LIST_GROUP_NAME, actionForm.getContactListGroupName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, CustomerTypeContactListGroupEditActionForm actionForm, EditCustomerTypeContactListGroupResult result) {
        request.setAttribute(AttributeConstants.CUSTOMER_TYPE_CONTACT_LIST_GROUP, result.getCustomerTypeContactListGroup());
    }

}
