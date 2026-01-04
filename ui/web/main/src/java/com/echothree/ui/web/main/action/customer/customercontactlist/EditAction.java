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

package com.echothree.ui.web.main.action.customer.customercontactlist;

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.edit.PartyContactListEdit;
import com.echothree.control.user.contactlist.common.form.EditPartyContactListForm;
import com.echothree.control.user.contactlist.common.result.EditPartyContactListResult;
import com.echothree.control.user.contactlist.common.spec.PartyContactListSpec;
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
    path = "/Customer/CustomerContactList/Edit",
    mappingClass = SecureActionMapping.class,
    name = "CustomerContactListEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Customer/CustomerContactList/Main", redirect = true),
        @SproutForward(name = "Form", path = "/customer/customercontactlist/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, PartyContactListSpec, PartyContactListEdit, EditPartyContactListForm, EditPartyContactListResult> {
    
    @Override
    protected PartyContactListSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ContactListUtil.getHome().getPartyContactListSpec();

        spec.setPartyName(findParameter(request, ParameterConstants.PARTY_NAME, actionForm.getPartyName()));
        spec.setContactListName(findParameter(request, ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName()));

        return spec;
    }
    
    @Override
    protected PartyContactListEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ContactListUtil.getHome().getPartyContactListEdit();

        edit.setPreferredContactMechanismPurposeName(actionForm.getPreferredContactMechanismPurposeChoice());

        return edit;
    }
    
    @Override
    protected EditPartyContactListForm getForm()
            throws NamingException {
        return ContactListUtil.getHome().getEditPartyContactListForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditPartyContactListResult result, PartyContactListSpec spec, PartyContactListEdit edit) {
        actionForm.setPartyName(request.getParameter(ParameterConstants.PARTY_NAME));
        actionForm.setContactListName(spec.getContactListName());
        actionForm.setPreferredContactMechanismPurposeChoice(edit.getPreferredContactMechanismPurposeName());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPartyContactListForm commandForm)
            throws Exception {
        var commandResult = ContactListUtil.getHome().editPartyContactList(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (EditPartyContactListResult)executionResult.getResult();
        var partyAlias = result.getPartyContactList();

        if(partyAlias != null) {
            request.setAttribute(AttributeConstants.PARTY_CONTACT_LIST, partyAlias);
        }
        
        return commandResult;
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.PARTY_NAME, actionForm.getPartyName());
    }
    
    @Override
    public void setupTransfer(EditActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        CustomerContactListUtil.getInstance().setupCustomer(request, actionForm.getPartyName());
    }
    
}