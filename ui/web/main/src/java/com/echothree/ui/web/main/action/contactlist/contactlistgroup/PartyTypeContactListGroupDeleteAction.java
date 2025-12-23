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
import com.echothree.control.user.contactlist.common.result.GetPartyTypeContactListGroupResult;
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
    path = "/ContactList/ContactListGroup/PartyTypeContactListGroupDelete",
    mappingClass = SecureActionMapping.class,
    name = "PartyTypeContactListGroupDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactListGroup/PartyTypeContactListGroup", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlistgroup/partyTypeContactListGroupDelete.jsp")
    }
)
public class PartyTypeContactListGroupDeleteAction
        extends MainBaseDeleteAction<PartyTypeContactListGroupDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.PartyTypeContactListGroup.name();
    }
    
    @Override
    public void setupParameters(PartyTypeContactListGroupDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setContactListGroupName(findParameter(request, ParameterConstants.CONTACT_LIST_GROUP_NAME, actionForm.getContactListGroupName()));
        actionForm.setPartyTypeName(findParameter(request, ParameterConstants.PARTY_TYPE_NAME, actionForm.getPartyTypeName()));
    }
    
    @Override
    public void setupTransfer(PartyTypeContactListGroupDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContactListUtil.getHome().getGetPartyTypeContactListGroupForm();
        
        commandForm.setContactListGroupName(actionForm.getContactListGroupName());
        commandForm.setPartyTypeName(actionForm.getPartyTypeName());

        var commandResult = ContactListUtil.getHome().getPartyTypeContactListGroup(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPartyTypeContactListGroupResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.PARTY_TYPE_CONTACT_LIST_GROUP, result.getPartyTypeContactListGroup());
        }
    }
    
    @Override
    public CommandResult doDelete(PartyTypeContactListGroupDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContactListUtil.getHome().getDeletePartyTypeContactListGroupForm();

        commandForm.setContactListGroupName(actionForm.getContactListGroupName());
        commandForm.setPartyTypeName(actionForm.getPartyTypeName());

        return ContactListUtil.getHome().deletePartyTypeContactListGroup(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(PartyTypeContactListGroupDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTACT_LIST_GROUP_NAME, actionForm.getContactListGroupName());
    }
    
}
