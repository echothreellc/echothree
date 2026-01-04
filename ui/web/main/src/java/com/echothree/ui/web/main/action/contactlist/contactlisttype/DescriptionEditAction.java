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

package com.echothree.ui.web.main.action.contactlist.contactlisttype;

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.edit.ContactListTypeDescriptionEdit;
import com.echothree.control.user.contactlist.common.form.EditContactListTypeDescriptionForm;
import com.echothree.control.user.contactlist.common.result.EditContactListTypeDescriptionResult;
import com.echothree.control.user.contactlist.common.spec.ContactListTypeDescriptionSpec;
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
    path = "/ContactList/ContactListType/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "ContactListTypeDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/ContactList/ContactListType/Description", redirect = true),
        @SproutForward(name = "Form", path = "/contactlist/contactlisttype/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, ContactListTypeDescriptionSpec, ContactListTypeDescriptionEdit, EditContactListTypeDescriptionForm, EditContactListTypeDescriptionResult> {
    
    @Override
    protected ContactListTypeDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = ContactListUtil.getHome().getContactListTypeDescriptionSpec();
        
        spec.setContactListTypeName(findParameter(request, ParameterConstants.CONTACT_LIST_TYPE_NAME, actionForm.getContactListTypeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected ContactListTypeDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = ContactListUtil.getHome().getContactListTypeDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditContactListTypeDescriptionForm getForm()
            throws NamingException {
        return ContactListUtil.getHome().getEditContactListTypeDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditContactListTypeDescriptionResult result, ContactListTypeDescriptionSpec spec, ContactListTypeDescriptionEdit edit) {
        actionForm.setContactListTypeName(spec.getContactListTypeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditContactListTypeDescriptionForm commandForm)
            throws Exception {
        return ContactListUtil.getHome().editContactListTypeDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.CONTACT_LIST_TYPE_NAME, actionForm.getContactListTypeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditContactListTypeDescriptionResult result) {
        request.setAttribute(AttributeConstants.CONTACT_LIST_TYPE_DESCRIPTION, result.getContactListTypeDescription());
    }

}
