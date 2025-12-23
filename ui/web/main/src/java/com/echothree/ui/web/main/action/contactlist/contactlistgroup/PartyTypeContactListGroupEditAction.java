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
import com.echothree.control.user.contactlist.common.edit.PartyTypeContactListGroupEdit;
import com.echothree.control.user.contactlist.common.form.EditPartyTypeContactListGroupForm;
import com.echothree.control.user.contactlist.common.result.EditPartyTypeContactListGroupResult;
import com.echothree.control.user.contactlist.common.spec.PartyTypeContactListGroupSpec;
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
    path = "/ContactList/ContactListGroup/PartyTypeContactListGroupEdit",
    mappingClass = SecureActionMapping.class,
    name = "PartyTypeContactListGroupEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactListGroup/PartyTypeContactListGroup", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlistgroup/partyTypeContactListGroupEdit.jsp")
    }
)
public class PartyTypeContactListGroupEditAction
        extends MainBaseEditAction<PartyTypeContactListGroupEditActionForm, PartyTypeContactListGroupSpec, PartyTypeContactListGroupEdit, EditPartyTypeContactListGroupForm, EditPartyTypeContactListGroupResult> {
    
    @Override
    protected PartyTypeContactListGroupSpec getSpec(HttpServletRequest request, PartyTypeContactListGroupEditActionForm actionForm)
            throws NamingException {
        var spec = ContactListUtil.getHome().getPartyTypeContactListGroupSpec();
        
        spec.setContactListGroupName(findParameter(request, ParameterConstants.CONTACT_LIST_GROUP_NAME, actionForm.getContactListGroupName()));
        spec.setPartyTypeName(findParameter(request, ParameterConstants.PARTY_TYPE_NAME, actionForm.getPartyTypeName()));
        
        return spec;
    }
    
    @Override
    protected PartyTypeContactListGroupEdit getEdit(HttpServletRequest request, PartyTypeContactListGroupEditActionForm actionForm)
            throws NamingException {
        var edit = ContactListUtil.getHome().getPartyTypeContactListGroupEdit();

        edit.setAddWhenCreated(actionForm.getAddWhenCreated().toString());

        return edit;
    }
    
    @Override
    protected EditPartyTypeContactListGroupForm getForm()
            throws NamingException {
        return ContactListUtil.getHome().getEditPartyTypeContactListGroupForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, PartyTypeContactListGroupEditActionForm actionForm, EditPartyTypeContactListGroupResult result, PartyTypeContactListGroupSpec spec, PartyTypeContactListGroupEdit edit) {
        actionForm.setContactListGroupName(spec.getContactListGroupName());
        actionForm.setPartyTypeName(spec.getPartyTypeName());
        actionForm.setAddWhenCreated(Boolean.valueOf(edit.getAddWhenCreated()));
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditPartyTypeContactListGroupForm commandForm)
            throws Exception {
        return ContactListUtil.getHome().editPartyTypeContactListGroup(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(PartyTypeContactListGroupEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTACT_LIST_GROUP_NAME, actionForm.getContactListGroupName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, PartyTypeContactListGroupEditActionForm actionForm, EditPartyTypeContactListGroupResult result) {
        request.setAttribute(AttributeConstants.PARTY_TYPE_CONTACT_LIST_GROUP, result.getPartyTypeContactListGroup());
    }

}
