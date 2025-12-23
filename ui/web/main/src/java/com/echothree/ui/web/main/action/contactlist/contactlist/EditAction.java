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
import com.echothree.control.user.contactlist.common.edit.ContactListEdit;
import com.echothree.control.user.contactlist.common.form.EditContactListForm;
import com.echothree.control.user.contactlist.common.result.EditContactListResult;
import com.echothree.control.user.contactlist.common.spec.ContactListSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/ContactList/ContactList/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ContactListEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactList/Main", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlist/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ContactListSpec, ContactListEdit, EditContactListForm, EditContactListResult> {
    
    @Override
    protected ContactListSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ContactListUtil.getHome().getContactListSpec();
        
        spec.setContactListName(findParameter(request, ParameterConstants.ORIGINAL_CONTACT_LIST_NAME, actionForm.getOriginalContactListName()));
        
        return spec;
    }
    
    @Override
    protected ContactListEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ContactListUtil.getHome().getContactListEdit();

        edit.setContactListName(actionForm.getContactListName());
        edit.setContactListGroupName(actionForm.getContactListGroupChoice());
        edit.setContactListTypeName(actionForm.getContactListTypeChoice());
        edit.setContactListFrequencyName(actionForm.getContactListFrequencyChoice());
        edit.setDefaultPartyContactListStatusChoice(actionForm.getDefaultPartyContactListStatusChoice());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditContactListForm getForm()
            throws NamingException {
        return ContactListUtil.getHome().getEditContactListForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditContactListResult result, ContactListSpec spec, ContactListEdit edit) {
        actionForm.setOriginalContactListName(spec.getContactListName());
        actionForm.setContactListName(edit.getContactListName());
        actionForm.setContactListGroupChoice(edit.getContactListGroupName());
        actionForm.setContactListTypeChoice(edit.getContactListTypeName());
        actionForm.setContactListFrequencyChoice(edit.getContactListFrequencyName());
        actionForm.setDefaultPartyContactListStatusChoice(edit.getDefaultPartyContactListStatusChoice());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditContactListForm commandForm)
            throws Exception {
        return ContactListUtil.getHome().editContactList(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditContactListResult result) {
        request.setAttribute(AttributeConstants.CONTACT_LIST, result.getContactList());
    }

}
