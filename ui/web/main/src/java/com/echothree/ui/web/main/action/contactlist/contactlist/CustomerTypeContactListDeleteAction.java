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
import com.echothree.control.user.contactlist.common.result.GetCustomerTypeContactListResult;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseDeleteAction;
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
    path = "/ContactList/ContactList/CustomerTypeContactListDelete",
    mappingClass = SecureActionMapping.class,
    name = "CustomerTypeContactListDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactList/CustomerTypeContactList", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlist/customerTypeContactListDelete.jsp")
    }
)
public class CustomerTypeContactListDeleteAction
        extends MainBaseDeleteAction<CustomerTypeContactListDeleteActionForm> {

    @Override
    public String getEntityTypeName(final CustomerTypeContactListDeleteActionForm actionForm) {
        return EntityTypes.CustomerTypeContactList.name();
    }
    
    @Override
    public void setupParameters(CustomerTypeContactListDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setContactListName(findParameter(request, ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName()));
        actionForm.setCustomerTypeName(findParameter(request, ParameterConstants.CUSTOMER_TYPE_NAME, actionForm.getCustomerTypeName()));
    }
    
    @Override
    public void setupTransfer(CustomerTypeContactListDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContactListUtil.getHome().getGetCustomerTypeContactListForm();
        
        commandForm.setContactListName(actionForm.getContactListName());
        commandForm.setCustomerTypeName(actionForm.getCustomerTypeName());

        var commandResult = ContactListUtil.getHome().getCustomerTypeContactList(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCustomerTypeContactListResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.CUSTOMER_TYPE_CONTACT_LIST, result.getCustomerTypeContactList());
        }
    }
    
    @Override
    public CommandResult doDelete(CustomerTypeContactListDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContactListUtil.getHome().getDeleteCustomerTypeContactListForm();

        commandForm.setContactListName(actionForm.getContactListName());
        commandForm.setCustomerTypeName(actionForm.getCustomerTypeName());

        return ContactListUtil.getHome().deleteCustomerTypeContactList(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(CustomerTypeContactListDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName());
    }
    
}
