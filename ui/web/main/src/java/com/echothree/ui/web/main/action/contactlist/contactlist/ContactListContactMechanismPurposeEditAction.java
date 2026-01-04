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
import com.echothree.control.user.contactlist.common.edit.ContactListContactMechanismPurposeEdit;
import com.echothree.control.user.contactlist.common.form.EditContactListContactMechanismPurposeForm;
import com.echothree.control.user.contactlist.common.result.EditContactListContactMechanismPurposeResult;
import com.echothree.control.user.contactlist.common.spec.ContactListContactMechanismPurposeSpec;
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
    path = "/ContactList/ContactList/ContactListContactMechanismPurposeEdit",
    mappingClass = SecureActionMapping.class,
    name = "ContactListContactMechanismPurposeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactList/ContactListContactMechanismPurpose", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlist/contactListContactMechanismPurposeEdit.jsp")
    }
)
public class ContactListContactMechanismPurposeEditAction
        extends MainBaseEditAction<ContactListContactMechanismPurposeEditActionForm, ContactListContactMechanismPurposeSpec, ContactListContactMechanismPurposeEdit, EditContactListContactMechanismPurposeForm, EditContactListContactMechanismPurposeResult> {
    
    @Override
    protected ContactListContactMechanismPurposeSpec getSpec(HttpServletRequest request, ContactListContactMechanismPurposeEditActionForm actionForm)
            throws NamingException {
        var spec = ContactListUtil.getHome().getContactListContactMechanismPurposeSpec();
        
        spec.setContactListName(findParameter(request, ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName()));
        spec.setContactMechanismPurposeName(findParameter(request, ParameterConstants.CONTACT_MECHANISM_PURPOSE_NAME, actionForm.getContactMechanismPurposeName()));
        
        return spec;
    }
    
    @Override
    protected ContactListContactMechanismPurposeEdit getEdit(HttpServletRequest request, ContactListContactMechanismPurposeEditActionForm actionForm)
            throws NamingException {
        var edit = ContactListUtil.getHome().getContactListContactMechanismPurposeEdit();

        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());

        return edit;
    }
    
    @Override
    protected EditContactListContactMechanismPurposeForm getForm()
            throws NamingException {
        return ContactListUtil.getHome().getEditContactListContactMechanismPurposeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, ContactListContactMechanismPurposeEditActionForm actionForm, EditContactListContactMechanismPurposeResult result, ContactListContactMechanismPurposeSpec spec, ContactListContactMechanismPurposeEdit edit) {
        actionForm.setContactListName(spec.getContactListName());
        actionForm.setContactMechanismPurposeName(spec.getContactMechanismPurposeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditContactListContactMechanismPurposeForm commandForm)
            throws Exception {
        return ContactListUtil.getHome().editContactListContactMechanismPurpose(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(ContactListContactMechanismPurposeEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTACT_LIST_NAME, actionForm.getContactListName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, ContactListContactMechanismPurposeEditActionForm actionForm, EditContactListContactMechanismPurposeResult result) {
        request.setAttribute(AttributeConstants.CONTACT_LIST_CONTACT_MECHANISM_PURPOSE, result.getContactListContactMechanismPurpose());
    }

}
