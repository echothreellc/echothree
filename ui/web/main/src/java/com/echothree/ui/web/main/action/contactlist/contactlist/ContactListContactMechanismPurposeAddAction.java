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
    path = "/ContactList/ContactList/ContactListContactMechanismPurposeAdd",
    mappingClass = SecureActionMapping.class,
    name = "ContactListContactMechanismPurposeAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactList/ContactListContactMechanismPurpose", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlist/contactListContactMechanismPurposeAdd.jsp")
    }
)
public class ContactListContactMechanismPurposeAddAction
        extends MainBaseAddAction<ContactListContactMechanismPurposeAddActionForm> {

    @Override
    public void setupParameters(ContactListContactMechanismPurposeAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setContactListName(findParameter(request, ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName()));
    }
    
    @Override
    public void setupDefaults(ContactListContactMechanismPurposeAddActionForm actionForm)
            throws NamingException {
        actionForm.setSortOrder("1");
    }
    
    @Override
    public void setupTransfer(ContactListContactMechanismPurposeAddActionForm actionForm, HttpServletRequest request)
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
    public CommandResult doAdd(ContactListContactMechanismPurposeAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContactListUtil.getHome().getCreateContactListContactMechanismPurposeForm();

        commandForm.setContactListName( actionForm.getContactListName());
        commandForm.setContactMechanismPurposeName(actionForm.getContactMechanismPurposeChoice());
        commandForm.setIsDefault(actionForm.getIsDefault().toString());
        commandForm.setSortOrder(actionForm.getSortOrder());

        return ContactListUtil.getHome().createContactListContactMechanismPurpose(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(ContactListContactMechanismPurposeAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName());
    }
    
}
