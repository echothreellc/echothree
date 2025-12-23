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
import com.echothree.control.user.contactlist.common.edit.ContactListGroupEdit;
import com.echothree.control.user.contactlist.common.form.EditContactListGroupForm;
import com.echothree.control.user.contactlist.common.result.EditContactListGroupResult;
import com.echothree.control.user.contactlist.common.spec.ContactListGroupSpec;
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
    path = "/ContactList/ContactListGroup/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ContactListGroupEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactListGroup/Main", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlistgroup/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ContactListGroupSpec, ContactListGroupEdit, EditContactListGroupForm, EditContactListGroupResult> {
    
    @Override
    protected ContactListGroupSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ContactListUtil.getHome().getContactListGroupSpec();
        
        spec.setContactListGroupName(findParameter(request, ParameterConstants.ORIGINAL_CONTACT_LIST_GROUP_NAME, actionForm.getOriginalContactListGroupName()));
        
        return spec;
    }
    
    @Override
    protected ContactListGroupEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ContactListUtil.getHome().getContactListGroupEdit();

        edit.setContactListGroupName(actionForm.getContactListGroupName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditContactListGroupForm getForm()
            throws NamingException {
        return ContactListUtil.getHome().getEditContactListGroupForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditContactListGroupResult result, ContactListGroupSpec spec, ContactListGroupEdit edit) {
        actionForm.setOriginalContactListGroupName(spec.getContactListGroupName());
        actionForm.setContactListGroupName(edit.getContactListGroupName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditContactListGroupForm commandForm)
            throws Exception {
        return ContactListUtil.getHome().editContactListGroup(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditContactListGroupResult result) {
        request.setAttribute(AttributeConstants.CONTACT_LIST_GROUP, result.getContactListGroup());
    }

}
