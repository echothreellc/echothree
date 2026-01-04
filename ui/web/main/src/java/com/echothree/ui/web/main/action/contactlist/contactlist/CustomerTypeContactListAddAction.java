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
import com.echothree.control.user.contactlist.common.result.GetContactListResult;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
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
    path = "/ContactList/ContactList/CustomerTypeContactListAdd",
    mappingClass = SecureActionMapping.class,
    name = "CustomerTypeContactListAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactList/CustomerTypeContactList", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlist/customerTypeContactListAdd.jsp")
    }
)
public class CustomerTypeContactListAddAction
        extends MainBaseAddAction<CustomerTypeContactListAddActionForm> {

    @Override
    public void setupParameters(CustomerTypeContactListAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setContactListName(findParameter(request, ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName()));
    }
    
    @Override
    public void setupTransfer(CustomerTypeContactListAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContactListUtil.getHome().getGetContactListForm();

        commandForm.setContactListName(actionForm.getContactListName());

        var commandResult = ContactListUtil.getHome().getContactList(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetContactListResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.CONTACT_LIST, result.getContactList());
        }
    }
    
    @Override
    public CommandResult doAdd(CustomerTypeContactListAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContactListUtil.getHome().getCreateCustomerTypeContactListForm();

        commandForm.setContactListName( actionForm.getContactListName());
        commandForm.setCustomerTypeName(actionForm.getCustomerTypeChoice());
        commandForm.setAddWhenCreated(actionForm.getAddWhenCreated().toString());

        return ContactListUtil.getHome().createCustomerTypeContactList(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(CustomerTypeContactListAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName());
    }
    
}
