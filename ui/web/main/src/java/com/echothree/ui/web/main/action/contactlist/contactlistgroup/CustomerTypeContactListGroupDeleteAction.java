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
import com.echothree.control.user.contactlist.common.result.GetCustomerTypeContactListGroupResult;
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
    path = "/ContactList/ContactListGroup/CustomerTypeContactListGroupDelete",
    mappingClass = SecureActionMapping.class,
    name = "CustomerTypeContactListGroupDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactListGroup/CustomerTypeContactListGroup", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlistgroup/customerTypeContactListGroupDelete.jsp")
    }
)
public class CustomerTypeContactListGroupDeleteAction
        extends MainBaseDeleteAction<CustomerTypeContactListGroupDeleteActionForm> {

    @Override
    public String getEntityTypeName(final CustomerTypeContactListGroupDeleteActionForm actionForm) {
        return EntityTypes.CustomerTypeContactListGroup.name();
    }
    
    @Override
    public void setupParameters(CustomerTypeContactListGroupDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setContactListGroupName(findParameter(request, ParameterConstants.CONTACT_LIST_GROUP_NAME, actionForm.getContactListGroupName()));
        actionForm.setCustomerTypeName(findParameter(request, ParameterConstants.CUSTOMER_TYPE_NAME, actionForm.getCustomerTypeName()));
    }
    
    @Override
    public void setupTransfer(CustomerTypeContactListGroupDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContactListUtil.getHome().getGetCustomerTypeContactListGroupForm();
        
        commandForm.setContactListGroupName(actionForm.getContactListGroupName());
        commandForm.setCustomerTypeName(actionForm.getCustomerTypeName());

        var commandResult = ContactListUtil.getHome().getCustomerTypeContactListGroup(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetCustomerTypeContactListGroupResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.CUSTOMER_TYPE_CONTACT_LIST_GROUP, result.getCustomerTypeContactListGroup());
        }
    }
    
    @Override
    public CommandResult doDelete(CustomerTypeContactListGroupDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContactListUtil.getHome().getDeleteCustomerTypeContactListGroupForm();

        commandForm.setContactListGroupName(actionForm.getContactListGroupName());
        commandForm.setCustomerTypeName(actionForm.getCustomerTypeName());

        return ContactListUtil.getHome().deleteCustomerTypeContactListGroup(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(CustomerTypeContactListGroupDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTACT_LIST_GROUP_NAME, actionForm.getContactListGroupName());
    }
    
}
