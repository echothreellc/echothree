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

package com.echothree.ui.web.main.action.contactlist.contactlistfrequency;

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.edit.ContactListFrequencyEdit;
import com.echothree.control.user.contactlist.common.form.EditContactListFrequencyForm;
import com.echothree.control.user.contactlist.common.result.EditContactListFrequencyResult;
import com.echothree.control.user.contactlist.common.spec.ContactListFrequencySpec;
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
    path = "/ContactList/ContactListFrequency/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ContactListFrequencyEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactListFrequency/Main", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlistfrequency/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ContactListFrequencySpec, ContactListFrequencyEdit, EditContactListFrequencyForm, EditContactListFrequencyResult> {
    
    @Override
    protected ContactListFrequencySpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ContactListUtil.getHome().getContactListFrequencySpec();
        
        spec.setContactListFrequencyName(findParameter(request, ParameterConstants.ORIGINAL_CONTACT_LIST_FREQUENCY_NAME, actionForm.getOriginalContactListFrequencyName()));
        
        return spec;
    }
    
    @Override
    protected ContactListFrequencyEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ContactListUtil.getHome().getContactListFrequencyEdit();

        edit.setContactListFrequencyName(actionForm.getContactListFrequencyName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditContactListFrequencyForm getForm()
            throws NamingException {
        return ContactListUtil.getHome().getEditContactListFrequencyForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditContactListFrequencyResult result, ContactListFrequencySpec spec, ContactListFrequencyEdit edit) {
        actionForm.setOriginalContactListFrequencyName(spec.getContactListFrequencyName());
        actionForm.setContactListFrequencyName(edit.getContactListFrequencyName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditContactListFrequencyForm commandForm)
            throws Exception {
        return ContactListUtil.getHome().editContactListFrequency(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditContactListFrequencyResult result) {
        request.setAttribute(AttributeConstants.CONTACT_LIST_FREQUENCY, result.getContactListFrequency());
    }

}
