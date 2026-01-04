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
import com.echothree.control.user.contactlist.common.edit.PartyTypeContactListEdit;
import com.echothree.control.user.contactlist.common.form.EditPartyTypeContactListForm;
import com.echothree.control.user.contactlist.common.result.EditPartyTypeContactListResult;
import com.echothree.control.user.contactlist.common.spec.PartyTypeContactListSpec;
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
    path = "/ContactList/ContactList/PartyTypeContactListEdit",
    mappingClass = SecureActionMapping.class,
    name = "PartyTypeContactListEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactList/PartyTypeContactList", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlist/partyTypeContactListEdit.jsp")
    }
)
public class PartyTypeContactListEditAction
        extends MainBaseEditAction<PartyTypeContactListEditActionForm, PartyTypeContactListSpec, PartyTypeContactListEdit, EditPartyTypeContactListForm, EditPartyTypeContactListResult> {
    
    @Override
    protected PartyTypeContactListSpec getSpec(HttpServletRequest request, PartyTypeContactListEditActionForm actionForm)
            throws NamingException {
        var spec = ContactListUtil.getHome().getPartyTypeContactListSpec();
        
        spec.setContactListName(findParameter(request, ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName()));
        spec.setPartyTypeName(findParameter(request, ParameterConstants.PARTY_TYPE_NAME, actionForm.getPartyTypeName()));
        
        return spec;
    }
    
    @Override
    protected PartyTypeContactListEdit getEdit(HttpServletRequest request, PartyTypeContactListEditActionForm actionForm)
            throws NamingException {
        var edit = ContactListUtil.getHome().getPartyTypeContactListEdit();

        edit.setAddWhenCreated(actionForm.getAddWhenCreated().toString());

        return edit;
    }
    
    @Override
    protected EditPartyTypeContactListForm getForm()
            throws NamingException {
        return ContactListUtil.getHome().getEditPartyTypeContactListForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, PartyTypeContactListEditActionForm actionForm, EditPartyTypeContactListResult result, PartyTypeContactListSpec spec, PartyTypeContactListEdit edit) {
        actionForm.setContactListName(spec.getContactListName());
        actionForm.setPartyTypeName(spec.getPartyTypeName());
        actionForm.setAddWhenCreated(Boolean.valueOf(edit.getAddWhenCreated()));
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPartyTypeContactListForm commandForm)
            throws Exception {
        return ContactListUtil.getHome().editPartyTypeContactList(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(PartyTypeContactListEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, PartyTypeContactListEditActionForm actionForm, EditPartyTypeContactListResult result) {
        request.setAttribute(AttributeConstants.PARTY_TYPE_CONTACT_LIST, result.getPartyTypeContactList());
    }

}
