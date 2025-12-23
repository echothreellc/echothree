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

package com.echothree.ui.web.main.action.contactlist.contactlist;

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.edit.CustomerTypeContactListEdit;
import com.echothree.control.user.contactlist.common.form.EditCustomerTypeContactListForm;
import com.echothree.control.user.contactlist.common.result.EditCustomerTypeContactListResult;
import com.echothree.control.user.contactlist.common.spec.CustomerTypeContactListSpec;
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
    path = "/ContactList/ContactList/CustomerTypeContactListEdit",
    mappingClass = SecureActionMapping.class,
    name = "CustomerTypeContactListEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactList/CustomerTypeContactList", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlist/customerTypeContactListEdit.jsp")
    }
)
public class CustomerTypeContactListEditAction
        extends MainBaseEditAction<CustomerTypeContactListEditActionForm, CustomerTypeContactListSpec, CustomerTypeContactListEdit, EditCustomerTypeContactListForm, EditCustomerTypeContactListResult> {
    
    @Override
    protected CustomerTypeContactListSpec getSpec(HttpServletRequest request, CustomerTypeContactListEditActionForm actionForm)
            throws NamingException {
        var spec = ContactListUtil.getHome().getCustomerTypeContactListSpec();
        
        spec.setContactListName(findParameter(request, ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName()));
        spec.setCustomerTypeName(findParameter(request, ParameterConstants.CUSTOMER_TYPE_NAME, actionForm.getCustomerTypeName()));
        
        return spec;
    }
    
    @Override
    protected CustomerTypeContactListEdit getEdit(HttpServletRequest request, CustomerTypeContactListEditActionForm actionForm)
            throws NamingException {
        var edit = ContactListUtil.getHome().getCustomerTypeContactListEdit();

        edit.setAddWhenCreated(actionForm.getAddWhenCreated().toString());

        return edit;
    }
    
    @Override
    protected EditCustomerTypeContactListForm getForm()
            throws NamingException {
        return ContactListUtil.getHome().getEditCustomerTypeContactListForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, CustomerTypeContactListEditActionForm actionForm, EditCustomerTypeContactListResult result, CustomerTypeContactListSpec spec, CustomerTypeContactListEdit edit) {
        actionForm.setContactListName(spec.getContactListName());
        actionForm.setCustomerTypeName(spec.getCustomerTypeName());
        actionForm.setAddWhenCreated(Boolean.valueOf(edit.getAddWhenCreated()));
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditCustomerTypeContactListForm commandForm)
            throws Exception {
        return ContactListUtil.getHome().editCustomerTypeContactList(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(CustomerTypeContactListEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, CustomerTypeContactListEditActionForm actionForm, EditCustomerTypeContactListResult result) {
        request.setAttribute(AttributeConstants.CUSTOMER_TYPE_CONTACT_LIST, result.getCustomerTypeContactList());
    }

}
