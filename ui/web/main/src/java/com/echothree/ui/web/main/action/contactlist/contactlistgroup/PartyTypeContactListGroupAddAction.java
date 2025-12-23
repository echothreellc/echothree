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
import com.echothree.control.user.contactlist.common.result.GetContactListGroupResult;
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
    path = "/ContactList/ContactListGroup/PartyTypeContactListGroupAdd",
    mappingClass = SecureActionMapping.class,
    name = "PartyTypeContactListGroupAdd",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactListGroup/PartyTypeContactListGroup", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlistgroup/partyTypeContactListGroupAdd.jsp")
    }
)
public class PartyTypeContactListGroupAddAction
        extends MainBaseAddAction<PartyTypeContactListGroupAddActionForm> {

    @Override
    public void setupParameters(PartyTypeContactListGroupAddActionForm actionForm, HttpServletRequest request) {
        actionForm.setContactListGroupName(findParameter(request, ParameterConstants.CONTACT_LIST_GROUP_NAME, actionForm.getContactListGroupName()));
    }
    
    @Override
    public void setupTransfer(PartyTypeContactListGroupAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContactListUtil.getHome().getGetContactListGroupForm();

        commandForm.setContactListGroupName(actionForm.getContactListGroupName());

        var commandResult = ContactListUtil.getHome().getContactListGroup(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetContactListGroupResult)executionResult.getResult();
            
            request.setAttribute(AttributeConstants.CONTACT_LIST_GROUP, result.getContactListGroup());
        }
    }
    
    @Override
    public CommandResult doAdd(PartyTypeContactListGroupAddActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContactListUtil.getHome().getCreatePartyTypeContactListGroupForm();

        commandForm.setContactListGroupName( actionForm.getContactListGroupName());
        commandForm.setPartyTypeName(actionForm.getPartyTypeChoice());
        commandForm.setAddWhenCreated(actionForm.getAddWhenCreated().toString());

        return ContactListUtil.getHome().createPartyTypeContactListGroup(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(PartyTypeContactListGroupAddActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTACT_LIST_GROUP_NAME, actionForm.getContactListGroupName());
    }
    
}
