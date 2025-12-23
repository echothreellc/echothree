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
import com.echothree.control.user.contactlist.common.result.GetPartyTypeContactListResult;
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
    path = "/ContactList/ContactList/PartyTypeContactListDelete",
    mappingClass = SecureActionMapping.class,
    name = "PartyTypeContactListDelete",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactList/PartyTypeContactList", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlist/partyTypeContactListDelete.jsp")
    }
)
public class PartyTypeContactListDeleteAction
        extends MainBaseDeleteAction<PartyTypeContactListDeleteActionForm> {

    @Override
    public String getEntityTypeName() {
        return EntityTypes.PartyTypeContactList.name();
    }
    
    @Override
    public void setupParameters(PartyTypeContactListDeleteActionForm actionForm, HttpServletRequest request) {
        actionForm.setContactListName(findParameter(request, ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName()));
        actionForm.setPartyTypeName(findParameter(request, ParameterConstants.PARTY_TYPE_NAME, actionForm.getPartyTypeName()));
    }
    
    @Override
    public void setupTransfer(PartyTypeContactListDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContactListUtil.getHome().getGetPartyTypeContactListForm();
        
        commandForm.setContactListName(actionForm.getContactListName());
        commandForm.setPartyTypeName(actionForm.getPartyTypeName());

        var commandResult = ContactListUtil.getHome().getPartyTypeContactList(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPartyTypeContactListResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.PARTY_TYPE_CONTACT_LIST, result.getPartyTypeContactList());
        }
    }
    
    @Override
    public CommandResult doDelete(PartyTypeContactListDeleteActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ContactListUtil.getHome().getDeletePartyTypeContactListForm();

        commandForm.setContactListName(actionForm.getContactListName());
        commandForm.setPartyTypeName(actionForm.getPartyTypeName());

        return ContactListUtil.getHome().deletePartyTypeContactList(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(PartyTypeContactListDeleteActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName());
    }
    
}
